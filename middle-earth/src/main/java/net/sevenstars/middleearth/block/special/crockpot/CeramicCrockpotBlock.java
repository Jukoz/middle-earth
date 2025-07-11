package net.sevenstars.middleearth.block.special.crockpot;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import org.jetbrains.annotations.Nullable;

public class CeramicCrockpotBlock extends CrockpotBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");

    public CeramicCrockpotBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CeramicCrockpotBlock.createCodec(CeramicCrockpotBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrockpotBlockEntity(pos, state, 0.8125f);
    }
}
