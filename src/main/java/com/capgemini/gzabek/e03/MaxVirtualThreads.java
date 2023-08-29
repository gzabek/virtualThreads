package com.capgemini.gzabek.e03;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MaxVirtualThreads {

    public static void main(String[] args) throws InterruptedException {

        var threads = IntStream.range(0, 500_000)
                .mapToObj(index -> Thread.ofVirtual()
                        .name("virtual-", index)
                        .unstarted(() -> {
                            try {
                                TimeUnit.MILLISECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        })).toList();

        Instant begin = Instant.now();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        Instant end = Instant.now();
        System.out.println("Duration = " + Duration.between(begin, end).toMillis() + "ms");
    }
}
