package net.sevenstars.api.entity;

public interface SittingEntity {
    boolean isSitting();
    void setSitting(boolean isSitting);
    void startSitting();
    void stopSitting();
}
