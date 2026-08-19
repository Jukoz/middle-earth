package net.sevenstars.middleearth.block.special.palemoss;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.LeavesBlock;

public final class PaleOakLeavesBlock extends LeavesBlock {
    public static final MapCodec<PaleOakLeavesBlock> CODEC = simpleCodec(PaleOakLeavesBlock::new);

    public PaleOakLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<PaleOakLeavesBlock> codec() {
        return CODEC;
    }
}
