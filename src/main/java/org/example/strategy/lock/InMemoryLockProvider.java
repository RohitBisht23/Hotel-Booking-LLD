package org.example.strategy.lock;

import java.util.concurrent.*;

public class InMemoryLockProvider implements LockProvider{

    private static class Expiry {
        final long deadline;
        final String onwer;

        public Expiry(long deadline, String onwer) {
            this.deadline = deadline;
            this.onwer = onwer;
        }

    }

    private final ConcurrentMap<String, Expiry> locks = new ConcurrentHashMap<>();

    //this remove all expired locks
    private final ScheduledExecutorService sweeper = Executors.newSingleThreadScheduledExecutor();


    public InMemoryLockProvider() {
        sweeper.scheduleAtFixedRate(this::sweep, 1, 1, TimeUnit.MINUTES);
    }

    //remove all expired locks
    private void sweep() {
        long now = System.currentTimeMillis();
        locks.entrySet().removeIf(e -> e.getValue().deadline <= now);
    }
    @Override
    public boolean tryLock(String key, String userId, long TTLMs) {
        long now = System.currentTimeMillis();
        Expiry expiry = new Expiry(now + TTLMs, userId);
        return locks.compute(key, (k, v)-> (v == null || v.deadline <= now)? expiry : v) == expiry;
    }

    @Override
    public void unlock(String key) {
        locks.remove(key);
    }

    public boolean isLockExpired(String key) {
        Expiry e = locks.get(key);
        return e != null &&
                e.deadline  < System.currentTimeMillis();
    }

    @Override
    public boolean isLockedBy(String key, String userId) {
        Expiry expiry = locks.get(key);
        return expiry != null && expiry.onwer.equalsIgnoreCase(userId);
    }

}
