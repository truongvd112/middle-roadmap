package com.example.middle_roadmap;

import com.example.middle_roadmap.exception.CustomRuntimeException;
import com.example.middle_roadmap.utils.ThreadPoolExecutorUtil;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day1_2Task {
    public static void main(String[] args) {
        streamExample();
        callAbleExample();
        completableExample();
    }

    private static void streamExample() {
        // create a list of 1000 random integer
        Random random = new Random();
        List<Integer> list = IntStream.range(0, 1000)
                .map(_ -> random.nextInt(10000))
                .boxed()
                .collect(Collectors.toList());

        // filter the list to numbers < 100, sort, double
        List<Integer> newList = list.stream()
                .filter(x -> x < 100)
                .map(x -> x * 2)
                .sorted()
                .limit(4)
                .collect(Collectors.toList());
        System.out.println(newList);
    }

    private static void callAbleExample(){
        Callable<String> task = () -> generateRandomString(10);

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<String> future = executor.submit(task);
            String result = future.get();
            System.out.println("Result: " + result);
            executor.shutdown();
        } catch (Exception ex){
            throw new CustomRuntimeException(ex);
        }
    }

    private static void completableExample(){
        ExecutorService executor = ThreadPoolExecutorUtil.createFixedThreadPool();
        CompletableFuture<String> callMockApi1 = CompletableFuture.supplyAsync(() -> {
            try {
                return mockApi1();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor).exceptionally(ex -> {
            System.out.println("Xảy ra ngoại lệ: " + ex.getMessage());
            return "";
        });

        CompletableFuture<String> callMockApi2 = CompletableFuture.supplyAsync(() -> {
            try {
                return mockApi2();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor).exceptionally(ex -> {
            System.out.println("Xảy ra ngoại lệ: " + ex.getMessage());
            return "";
        });

        CompletableFuture<String> combinedFuture = callMockApi1.thenCombine(callMockApi2, (result1, result2) -> result1 + result2);

        System.out.println("Kết quả: " + combinedFuture.join());
    }

    private static String mockApi1() throws Exception {
        Random random = new Random();
        int sleepTime = random.nextInt(5) + 1; // 1 to 5 seconds
        TimeUnit.SECONDS.sleep(sleepTime);
        if (random.nextBoolean()) { // 50% chance to throw an exception
            throw new Exception("Mock API 1 failed");
        }
        return "String1";
    }

    private static String mockApi2() throws Exception {
        Random random = new Random();
        int sleepTime = random.nextInt(5) + 1; // 1 to 5 seconds
        TimeUnit.SECONDS.sleep(sleepTime);
        if (random.nextBoolean()) { // 50% chance to throw an exception
            throw new Exception("Mock API 2 failed");
        }
        return "String2";
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
