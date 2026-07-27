package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.MiddleEarth;

public class BlockTagsME {
    public static TagKey<Block> CURTAINS = TagKey.create(Registries.BLOCK, MiddleEarth.of("curtains"));
    public static TagKey<Block> SMALL_CURTAINS = TagKey.create(Registries.BLOCK, MiddleEarth.of("small_curtains"));
    public static TagKey<Block> FARMLANDS = TagKey.create(Registries.BLOCK, MiddleEarth.of("farmlands"));
    public static TagKey<Block> COBWEBS = TagKey.create(Registries.BLOCK, MiddleEarth.of("cobwebs"));
}
