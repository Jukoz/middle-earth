package net.sevenstars.middleearth.common.feature;

import java.lang.invoke.MethodHandles;

public interface Feature {
    void register();

    // Most registration happens in static initializers, so a class registers its content when it loads.
    // This loads the given classes now, in the order given.
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
