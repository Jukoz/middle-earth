package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import net.minecraft.resources.ResourceLocation;

public interface FarmAnimalVariantHolder {
    ResourceLocation wildThings$getFarmVariant();

    void wildThings$setFarmVariant(ResourceLocation variant);
}
