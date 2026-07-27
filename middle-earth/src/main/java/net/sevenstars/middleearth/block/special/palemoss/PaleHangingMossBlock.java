package net.sevenstars.middleearth.block.special.palemoss;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.special.hangingstuff.CustomHangingBlock;

public final class PaleHangingMossBlock extends CustomHangingBlock {
    public static final MapCodec<PaleHangingMossBlock> CODEC = simpleCodec(PaleHangingMossBlock::new);

    public PaleHangingMossBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<PaleHangingMossBlock> codec() {
        return CODEC;
    }
}
