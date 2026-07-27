package net.sevenstars.middleearth.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;

public class RegistryUtils {
    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return RegistrationBridge.register(
                registry,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name),
                entry
        );
    }
}
