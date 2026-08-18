package net.sevenstars.api.entity;

public interface SleepingEntity {
    boolean isAsleep();
    void setSleeping(boolean isSleeping);
    void startSleeping();
    void stopSleeping();
}
