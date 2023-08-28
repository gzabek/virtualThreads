package com.capgemini.gzabek.e02;

import lombok.SneakyThrows;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.capgemini.gzabek.Util.log;
import static java.lang.Thread.sleep;

public class VirtualThreadsConcurrent {

    @SneakyThrows
    public static void main(String[] args) {
        var bathTime = bathTime();
        var boilingWater = boilingWater();
        bathTime.join();
        boilingWater.join();

        //concurrentMorningRoutineUsingExecutorsWithName();
    }

    static Thread bathTime() {
        return Thread.ofVirtual()
                .name("Bath time")
                .start(() -> {
                    log("I'm going to take a bath", VirtualThreadsConcurrent.class);
                    try {
                        sleep(Duration.ofMillis(500L));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log("I'm done with the bath", VirtualThreadsConcurrent.class);
                });
    }


    static Thread boilingWater() {
        return Thread.ofVirtual()
                .name("Boiling water")
                .start(() -> {
                    log("I'm going to boil some water", VirtualThreadsConcurrent.class);
                    try {
                        sleep(Duration.ofSeconds(1L));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log("I'm done with the water", VirtualThreadsConcurrent.class);
                });
    }

    @SneakyThrows
    static void concurrentMorningRoutineUsingExecutorsWithName() {
        final ThreadFactory factory = Thread.ofVirtual().name("daily-", 0).factory();
        try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
            var bathTime =
                    executor.submit(
                            () -> {
                                log("I'm going to take a bath", VirtualThreadsConcurrent.class);
                                try {
                                    sleep(Duration.ofMillis(500L));
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                log("I'm done with the bath", VirtualThreadsConcurrent.class);
                            });
            var boilingWater =
                    executor.submit(
                            () -> {
                                log("I'm going to boil some water", VirtualThreadsConcurrent.class);
                                try {
                                    sleep(Duration.ofSeconds(1L));
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                log("I'm done with the water", VirtualThreadsConcurrent.class);
                            });
            bathTime.get();
            boilingWater.get();
        }
    }

}
