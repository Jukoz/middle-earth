package net.sevenstars.middleearth.block.special.fireBlocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class GildedBrazierBlock extends AbstractToggleableFireBlock {

    public static final MapCodec<GildedBrazierBlock> CODEC = GildedBrazierBlock.simpleCodec(GildedBrazierBlock::new);


    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public GildedBrazierBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends AbstractToggleableFireBlock> codec() {
        return CODEC;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (world.isClientSide) {
            if (state.getValue(LIT)) {
                return AbstractToggleableFireBlock.createTickerHelper(type, ModBlockEntities.GILDED_BIG_BRAZIER, GildedBrazierBlockEntity::clientTick);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GildedBrazierBlockEntity(pos, state);
    }
}
