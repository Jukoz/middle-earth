package net.sevenstars.api.entity;

public interface SleepingEntity {
    boolean isSleeping();
    void setSleeping(boolean isSleeping);
    void startSleeping();
    void stopSleeping();
}
