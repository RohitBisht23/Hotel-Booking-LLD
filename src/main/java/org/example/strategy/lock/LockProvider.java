package org.example.strategy.lock;

import org.example.entities.Booking;

public interface LockProvider {
    boolean tryLock(String key, String userId, long TTLMs);
    void unlock(String key);
    boolean isLockExpired(String key);
    boolean isLockedBy(String key, String userId);
}
