package com.capgemini.gzabek.e01;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MaxPlatformThreads {

    public static void main(String[] args) throws InterruptedException {

        var threads = IntStream.range(0, 50_000)
                .mapToObj(i -> new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                })).toList();

        var begin = Instant.now();

        for (var thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        var end = Instant.now();
        System.out.println("# cores = " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time = " + Duration.between(begin, end).toMillis() + "ms");
    }


}
