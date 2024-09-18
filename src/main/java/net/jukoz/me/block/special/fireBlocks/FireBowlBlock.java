package net.jukoz.me.block.special.fireBlocks;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FireBowlBlock extends AbstractToggleableFireBlock {

    public static final MapCodec<FireBowlBlock> CODEC = FireBowlBlock.createCodec(FireBowlBlock::new);

    protected static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 10, 15);
    public static final BooleanProperty LIT = Properties.LIT;

    public FireBowlBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends AbstractToggleableFireBlock> getCodec() {
        return CODEC;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            if (state.get(LIT)) {
                return AbstractToggleableFireBlock.validateTicker(type, ModBlockEntities.FIRE_BOWL, FireBowlBlockEntity::clientTick);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FireBowlBlockEntity(pos, state);
    }
}