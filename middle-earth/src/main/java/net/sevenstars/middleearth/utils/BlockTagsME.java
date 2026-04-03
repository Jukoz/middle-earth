package net.sevenstars.middleearth.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class BlockTagsME {
    public static TagKey<Block> CURTAINS = TagKey.of(RegistryKeys.BLOCK, IdentifierUtil.create("curtains"));
    public static TagKey<Block> SMALL_CURTAINS = TagKey.of(RegistryKeys.BLOCK, IdentifierUtil.create("small_curtains"));
}
