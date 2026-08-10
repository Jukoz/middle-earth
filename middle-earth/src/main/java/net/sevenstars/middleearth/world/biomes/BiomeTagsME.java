package net.sevenstars.middleearth.world.biomes;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;

public class BiomeTagsME {
    public static TagKey<Biome> SPRING = TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_spring"));
    public static TagKey<Biome> AUTUMN = TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_autumn"));
    public static TagKey<Biome> WINTER = TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_winter"));
    public static TagKey<Biome> DEAD = TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_dead"));
}
