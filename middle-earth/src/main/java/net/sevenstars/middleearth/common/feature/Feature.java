package net.sevenstars.middleearth.common.feature;

import java.lang.invoke.MethodHandles;

public interface Feature {
    void register();

    // Registries register in their static initializers, so loading the class is enough
    static void load(Class<?>... classes) {
        for (Class<?> c : classes) {
            try {
                MethodHandles.lookup().ensureInitialized(c);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
