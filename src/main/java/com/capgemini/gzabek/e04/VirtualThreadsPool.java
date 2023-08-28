package com.capgemini.gzabek.e04;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class VirtualThreadsPool {
    public static void main(String[] args) throws InterruptedException {

        Set<String> forkJoinPools = ConcurrentHashMap.newKeySet();
        Set<String> pThreads = ConcurrentHashMap.newKeySet();
        Pattern forkJoinPool = Pattern.compile("ForkJoinPool-[\\d?]");
        Pattern worker = Pattern.compile("worker-[\\d?]");

        var threads = IntStream.range(0, 50_000)
                .mapToObj(i -> Thread.ofVirtual()
                        .unstarted(() -> {
                            try {
                                TimeUnit.SECONDS.sleep(10);
                                String name = Thread.currentThread().toString();
                                Matcher poolMatcher = forkJoinPool.matcher(name);
                                if (poolMatcher.find()) {
                                    forkJoinPools.add(poolMatcher.group());
                                }
                                Matcher workerMatcher = worker.matcher(name);
                                if (workerMatcher.find()) {
                                    pThreads.add(workerMatcher.group());
                                }

                            } catch (InterruptedException e) {
                                throw new AssertionError(e);
                            }
                        }))
                .toList();
        Instant begin = Instant.now();
        for (var thread : threads) {
            thread.start();
        }
        for (var thread : threads) {
            thread.join();
        }
        Instant end = Instant.now();
        System.out.println("# cores = " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time = " + Duration.between(begin, end).toMillis() + "ms");
        System.out.println("Pools");
        forkJoinPools.forEach(System.out::println);
        System.out.println("Platform threads (" + pThreads.size() + ")");
        new TreeSet<>(pThreads).forEach(System.out::println);
    }
}
