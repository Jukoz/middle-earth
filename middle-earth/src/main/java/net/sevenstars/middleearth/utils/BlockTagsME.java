package net.sevenstars.middleearth.utils;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.sevenstars.middleearth.MiddleEarth;

public class BlockTagsME {
    public static TagKey<Block> CURTAINS = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("curtains"));
    public static TagKey<Block> SMALL_CURTAINS = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("small_curtains"));
    public static TagKey<Block> FARMLANDS = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("farmlands"));
    public static TagKey<Block> COBWEBS = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("cobwebs"));
    public static TagKey<Block> NOT_MINEABLE_WITH_PICKAXE = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("not_mineable_with_pickaxe"));
}
