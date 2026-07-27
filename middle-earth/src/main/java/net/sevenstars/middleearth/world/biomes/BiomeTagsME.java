package net.sevenstars.middleearth.world.biomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;

public class BiomeTagsME {
    public static TagKey<Biome> SPRING = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "is_spring"));
    public static TagKey<Biome> AUTUMN = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "is_autumn"));
    public static TagKey<Biome> WINTER = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "is_winter"));
    public static TagKey<Biome> DEAD = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "is_dead"));
}
