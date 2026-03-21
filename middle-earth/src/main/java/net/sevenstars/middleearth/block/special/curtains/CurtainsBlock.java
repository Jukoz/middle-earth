package net.sevenstars.middleearth.block.special.curtains;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;

public class CurtainsBlock extends HorizontalFacingBlock {
    public static final BooleanProperty OPEN;
    public static final MapCodec<CurtainsBlock> CODEC = createCodec(CurtainsBlock::new);

    public CurtainsBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{OPEN, FACING});
    }

    static {
        OPEN = Properties.OPEN;
    }
}
