package net.sevenstars.middleearth.utils;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class BlockTagsME {
    public static final TagKey<Block> CURTAINS = of("curtains");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
