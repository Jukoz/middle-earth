package net.sevenstars.middleearth.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public final class VariantHolderUtils {
    private static final String VARIANT_KEY = "variant";

    private VariantHolderUtils() {}

    public static <T> Holder<T> getDefaultOrAny(RegistryAccess registryAccess, ResourceKey<T> defaultKey) {
        Registry<T> registry = registryAccess.registryOrThrow(defaultKey.registryKey());
        return registry.getHolder(defaultKey)
                .map(Holder.class::cast)
                .orElseGet(() -> registry.holders().findFirst()
                        .orElseThrow(() -> new IllegalStateException("Empty variant registry: " + defaultKey.registry())));
    }

    public static <T> void writeVariant(CompoundTag tag, Holder<T> variant) {
        variant.unwrapKey().ifPresent(key -> tag.putString(VARIANT_KEY, key.location().toString()));
    }

    public static <T> Optional<Holder<T>> readVariant(
            CompoundTag tag,
            RegistryAccess registryAccess,
            ResourceKey<? extends Registry<T>> registryKey) {
        if (!tag.contains(VARIANT_KEY, Tag.TAG_STRING)) {
            return Optional.empty();
        }
        ResourceLocation id = ResourceLocation.tryParse(tag.getString(VARIANT_KEY));
        if (id == null) {
            return Optional.empty();
        }
        return registryAccess.registryOrThrow(registryKey)
                .getHolder(ResourceKey.create(registryKey, id))
                .map(Holder.class::cast);
    }
}
