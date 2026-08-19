package net.sevenstars.api.registries;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DynamicRegistries {

    public static <T> T register(BootstrapContext<T> context, HolderGetter<T> registryEntryLookup, ResourceKey<T> registryKey, T element) {
        Optional<Holder.Reference<T>> optionalRegistryEntry = registryEntryLookup.get(registryKey);
        optionalRegistryEntry.ifPresent(reference -> context.register(registryKey, element));
        return element;
    }
    public static <T> ResourceKey<T> of(ResourceKey<Registry<T>> key, ResourceLocation id) {
        return ResourceKey.create(key, id);
    }
}
