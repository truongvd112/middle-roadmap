package com.example.middle_roadmap.service.implement;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.dto.DataListResponse;
import com.example.middle_roadmap.entity.User;
import com.example.middle_roadmap.exception.CustomRuntimeException;
import com.example.middle_roadmap.repository.UserRepository;
import com.example.middle_roadmap.service.UserService;
import com.example.middle_roadmap.utils.ThreadPoolExecutorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Object lockGlobal = new Object();

    @Override
    public BaseResponse list() {
        List<User> users = userRepository.findAll();
        return DataListResponse.builder().dataList(users).build();
    }

    @Override
    public BaseResponse save(User user) {
        userRepository.save(user);
        return BaseResponse.simpleSuccess("success");
    }

    @Override
    public BaseResponse delete(long id) {
        userRepository.deleteById(String.valueOf(id));
        return BaseResponse.simpleSuccess("success");
    }

    @Override
    public BaseResponse threadExample() {
        Object lock = new Object();
        AtomicInteger x = new AtomicInteger(1);
        countAndPrint(x, lock, 1);
        countAndPrint(x, lock, 2);
        countAndPrint(x, lock, 3);
        return BaseResponse.simpleSuccess("success");
    }

    @Override
    public BaseResponse bankTransfer(long userId1, long userId2) {
        User userA = userRepository.getReferenceById(String.valueOf(userId1));
        User userB = userRepository.getReferenceById(String.valueOf(userId2));
        //
        Object lock = new Object();

        transferMoney(userA, userB, 10000L, lock);
        transferMoney(userB, userA, 20000L, lock);
//        depositMoney(userA, 1000L);
//        depositMoney(userB, 1000L);
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

    private void transferMoney(User user1, User user2, Long money, Object lock){
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

    private void depositMoney(User user, Long money){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try{
            AtomicInteger times = new AtomicInteger(500);
            executor.execute(() -> {
                while(times.get() > 0){
                    synchronized (lockGlobal) {
                        user.deposit(money);
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
