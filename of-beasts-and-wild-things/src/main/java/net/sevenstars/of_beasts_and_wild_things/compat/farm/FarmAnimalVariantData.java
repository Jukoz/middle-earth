package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public final class FarmAnimalVariantData {
    private static final EntityDataSerializer<ResourceLocation> SERIALIZER =
            EntityDataSerializer.forValueType(ResourceLocation.STREAM_CODEC);
    private static EntityDataAccessor<ResourceLocation> chicken;
    private static EntityDataAccessor<ResourceLocation> cow;
    private static EntityDataAccessor<ResourceLocation> pig;

    private FarmAnimalVariantData() {
    }

    public static void registerSerializer() {
        RegistrationBridge.register(
                NeoForgeRegistries.ENTITY_DATA_SERIALIZERS,
                OfBeastsAndWildThings.of("farm_animal_variant"),
                SERIALIZER
        );
    }

    public static EntityDataSerializer<ResourceLocation> serializer() {
        return SERIALIZER;
    }

    public static synchronized EntityDataAccessor<ResourceLocation> register(
            FarmAnimalKind kind,
            EntityDataAccessor<ResourceLocation> accessor
    ) {
        EntityDataAccessor<ResourceLocation> current = accessor(kind);
        if (current != null && current != accessor) {
            throw new IllegalStateException("Farm animal variant accessor already registered for " + kind);
        }
        switch (kind) {
            case CHICKEN -> chicken = accessor;
            case COW -> cow = accessor;
            case PIG -> pig = accessor;
        }
        return accessor;
    }

    public static EntityDataAccessor<ResourceLocation> accessor(FarmAnimalKind kind) {
        return switch (kind) {
            case CHICKEN -> chicken;
            case COW -> cow;
            case PIG -> pig;
        };
    }
}
