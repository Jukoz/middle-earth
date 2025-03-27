package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;

public class PlateBlock extends HorizontalFacingBlock {

    public static final BooleanProperty UTENSILS = BooleanProperty.of("utensils");

    public PlateBlock(Settings settings) {
        super(settings);

        this.setDefaultState(((this.stateManager.getDefaultState())
                .with(UTENSILS, false))
                .with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }
}
