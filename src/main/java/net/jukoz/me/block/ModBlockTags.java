package net.jukoz.me.block;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> MIRKWOOD_ROOTS_CAN_GROW_THROUGH = of("mirkwood_roots_can_grow_through");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, id));
    }
}
