package net.sevenstars.of_beasts_and_wild_things.utils;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class BiomeTagsWT {
    public static TagKey<Biome> SNAIL_SPAWNS = TagKey.of(RegistryKeys.BIOME, OfBeastsAndWildThings.of("snail_spawns"));
    public static TagKey<Biome> PHEASANT_SPAWNS = TagKey.of(RegistryKeys.BIOME, OfBeastsAndWildThings.of("pheasant_spawns"));
    public static TagKey<Biome> DEER_SPAWNS = TagKey.of(RegistryKeys.BIOME, OfBeastsAndWildThings.of("deer_spawns"));

}
