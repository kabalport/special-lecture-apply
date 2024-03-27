package com.tdd.speciallectureapply.speciallecture;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Component
public class LockHandler {
    // userId 기반으로 Lock 을 관리하는 ConcurrentHashMap
    private final ConcurrentHashMap<Long, Lock> lockMap = new ConcurrentHashMap<>();

    public <T> T executeOnLock(Long key, Supplier<T> block) {
        Lock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
        boolean acquired;
        try {
            acquired = lock.tryLock(5, TimeUnit.SECONDS);
            if (!acquired) throw new RuntimeException("Timeout 에러 발생");

            // 락을 획득
            try {
                return block.get();
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while trying to acquire the lock", e);
        }
    }
}
