package net.sevenstars.api.registries;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class DynamicRegistries {

    public static <T> T register(Registerable<T> context, RegistryEntryLookup<T> registryEntryLookup, RegistryKey<T> registryKey, T element) {
        Optional<RegistryEntry.Reference<T>> optionalRegistryEntry = registryEntryLookup.getOptional(registryKey);
        optionalRegistryEntry.ifPresent(reference -> context.register(registryKey, element));
        return element;
    }
    public static <T> RegistryKey<T> of(RegistryKey<Registry<T>> key, Identifier id) {
        return RegistryKey.of(key, id);
    }
}
