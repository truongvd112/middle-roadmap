package com.example.middle_roadmap.service;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.entity.BankAccount;
import com.example.middle_roadmap.exception.CustomRuntimeException;
import com.example.middle_roadmap.repository.BankAccountRepository;
import com.example.middle_roadmap.utils.ThreadPoolExecutorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    public BaseResponse bankTransfer(long userId1, long userId2) {
        BankAccount userA = bankAccountRepository.getReferenceById(String.valueOf(userId1));
        BankAccount userB = bankAccountRepository.getReferenceById(String.valueOf(userId2));
        //
        Object lock = new Object();

        transferMoney(userA, userB, 10000L, lock);
        transferMoney(userB, userA, 20000L, lock);
        return BaseResponse.simpleSuccess("success");
    }

    public BaseResponse threadExample() {
        Object lock = new Object();
        AtomicInteger x = new AtomicInteger(1);
        countAndPrint(x, lock, 1);
        countAndPrint(x, lock, 2);
        countAndPrint(x, lock, 3);
        return BaseResponse.simpleSuccess("success");
    }

    private void countAndPrint(AtomicInteger x, Object lock, int threadNumber){
        ExecutorService executor = ThreadPoolExecutorUtil.createFixedThreadPool();
        try{
            executor.execute(() -> {
                while (x.get() <= 30) {
                    synchronized (lock) {
                        if (x.get() % 3 == threadNumber % 3) {
                            System.out.println("Thread " + threadNumber + " prints: " + x);
                            x.getAndAdd(1);
                            lock.notifyAll();
                        } else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            });
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CustomRuntimeException(ex);
        } finally {
            executor.shutdown();
        }
    }

    private void transferMoney(BankAccount user1, BankAccount user2, Long money, Object lock){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try{
            AtomicInteger times = new AtomicInteger(30);
            executor.execute(() -> {
                while(times.get() > 0){
                    synchronized (lock) {
                        user1.withdraw(money);
                        user2.deposit(money);
                        times.getAndDecrement();
                    }
                }
            });
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CustomRuntimeException(ex);
        } finally {
            executor.shutdown();
        }
    }
}
