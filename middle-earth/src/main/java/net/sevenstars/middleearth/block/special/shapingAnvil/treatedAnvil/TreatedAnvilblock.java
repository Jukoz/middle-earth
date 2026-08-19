package net.sevenstars.middleearth.block.special.shapingAnvil.treatedAnvil;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.shapingAnvil.AbstractShapingAnvilBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class TreatedAnvilblock extends AbstractShapingAnvilBlock {
    public TreatedAnvilblock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 6, 15),
                        Block.box(1, 12, 4, 15, 16, 12),
                        Block.box(4, 6, 5, 12, 12, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 6, 15),
                        Block.box(4, 12, 1, 12, 16, 15),
                        Block.box(5, 6, 4, 11, 12, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            default -> {
                return Block.box(1, 0, 1, 15, 16, 15);
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TreatedAnvilblock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShapingAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return TreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClientSide ? null : TreatedAnvilblock.createTickerHelper(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
