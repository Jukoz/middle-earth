package net.sevenstars.middleearth.common.feature;

// Separate from Feature so servers never load client code
public interface ClientFeature {
    void registerClient();
}
