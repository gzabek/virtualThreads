package com.capgemini.gzabek.e05;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.capgemini.gzabek.Util.log;

@Slf4j
public class VirtualThreadsExecutor {

    public static void main(String[] args) {

        //ExecutorService fixed = Executors.newFixedThreadPool(4);
        //ExecutorService cached = Executors.newCachedThreadPool();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 4_000; i++) {
                executor.submit(() -> {
                    try {
                        log("", VirtualThreadsExecutor.class);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                });
            }
        }
    }
}
