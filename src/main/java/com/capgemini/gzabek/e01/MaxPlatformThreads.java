package com.capgemini.gzabek.e01;

import lombok.SneakyThrows;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MaxPlatformThreads {

    @SneakyThrows
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);

        var threads = IntStream.range(0, 10_000)
                .mapToObj(i -> new Thread(() -> {
                    try {
                        Thread.sleep(10_000_000);
                    } catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                })).toList();

        var begin = Instant.now();

        for (var thread : threads) {
            System.out.println(counter.getAndIncrement());
            thread.start();
        }

        for (var thread : threads) {
            thread.join();
        }

        var end = Instant.now();
        System.out.println("# cores = " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time = " + Duration.between(begin, end).toMillis() + "ms");
    }


}
