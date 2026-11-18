package net.sevenstars.middleearth.common.feature;

// Kept apart from Feature so a dedicated server never loads client classes.
public interface ClientFeature {
    void registerClient();
}
