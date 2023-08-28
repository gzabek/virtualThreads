package com.capgemini.gzabek.e06;

import lombok.SneakyThrows;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.capgemini.gzabek.Util.log;
import static java.lang.Thread.sleep;

public class PinnedVirtualThreads {
    static Bathroom bathroom = new Bathroom();

    public static void main(String[] args) throws InterruptedException {

        var workingHard = workingHard();
        var takeABreak = takeABreak();
        workingHard.join();
        takeABreak.join();
//
//        var jan = goToTheToilet();
//        var daniel = takeABreak();
//        jan.join();
//        daniel.join();

    }

    static Thread goToTheToilet() {
        return Thread.startVirtualThread(
                () -> bathroom.useTheToilet());
    }

    @SneakyThrows
    static Thread takeABreak() {
        return Thread.startVirtualThread(
                () -> {
                    log("I'm going to take a break", PinnedVirtualThreads.class);
                    try {
                        sleep(Duration.ofSeconds(1L));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log("I'm done with the break", PinnedVirtualThreads.class);
                });
    }

    static Thread workingHard() {
        return Thread.ofVirtual()
                .name("Working hard")
                .start(() -> {
                    log("I'm working hard", PinnedVirtualThreads.class);
                    while (alwaysTrue()) {
                        // Do nothing
                    }
                    log("I'm done with working hard", PinnedVirtualThreads.class);
                });
    }

    static boolean alwaysTrue() {
        return true;
    }

    static class Bathroom {
        @SneakyThrows
        synchronized void useTheToilet() {
            log("I'm going to use the toilet", PinnedVirtualThreads.class);
            sleep(Duration.ofSeconds(1L));
            log("I'm done with the toilet", PinnedVirtualThreads.class);
        }

        @SneakyThrows
        public void useTheToiletWithLock() {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                try {
                    log("I'm going to use the toilet", PinnedVirtualThreads.class);
                    sleep(Duration.ofSeconds(1L));
                    log("I'm done with the toilet", PinnedVirtualThreads.class);
                } finally {
                    lock.unlock();
                }
            }
        }
        private final Lock lock = new ReentrantLock();
    }
}
