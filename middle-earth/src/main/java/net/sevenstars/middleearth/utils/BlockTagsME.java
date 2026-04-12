package net.sevenstars.middleearth.utils;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.sevenstars.middleearth.MiddleEarth;

public class BlockTagsME {
    public static TagKey<Block> CURTAINS = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.of("curtains"));
}
