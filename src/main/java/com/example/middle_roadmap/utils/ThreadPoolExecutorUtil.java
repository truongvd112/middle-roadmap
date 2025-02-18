package com.example.middle_roadmap.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public final class ThreadPoolExecutorUtil {

    public static ExecutorService createFixedThreadPool() {
        int availableCores = Runtime.getRuntime().availableProcessors();
        // Adjust max threads, use 90% of available threads
        int threadPoolSize = (int) (availableCores * 2 * 0.9f);
        log.info("Available Cores: {} -  Pool Size : {}", availableCores, threadPoolSize);
        return Executors.newFixedThreadPool(threadPoolSize);
    }
}
