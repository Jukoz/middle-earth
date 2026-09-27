package net.sevenstars.middleearth.utils;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegistryUtils {
    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return Registry.register(registry, MiddleEarth.id(name), entry);
    }
}
