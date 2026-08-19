package net.sevenstars.middleearth.block.special.shapingAnvil.elvenTreatedAnvil;

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

public class ElvenTreatedAnvilblock extends AbstractShapingAnvilBlock {
    public ElvenTreatedAnvilblock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH -> {
                return Stream.of(
                        Block.box(2, 0, 2, 14, 5, 14),
                        Block.box(12, 12, 6, 16, 16, 10),
                        Block.box(4, 13, 6, 12, 16, 10),
                        Block.box(0, 14, 7, 4, 16, 9),
                        Block.box(5, 5, 6, 11, 13, 10)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case SOUTH -> {
                return Stream.of(
                        Block.box(2, 0, 2, 14, 5, 14),
                        Block.box(0, 12, 6, 4, 16, 10),
                        Block.box(4, 13, 6, 12, 16, 10),
                        Block.box(12, 14, 7, 16, 16, 9),
                        Block.box(5, 5, 6, 11, 13, 10)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case EAST -> {
                return Stream.of(
                        Block.box(2, 0, 2, 14, 5, 14),
                        Block.box(6, 12, 12, 10, 16, 16),
                        Block.box(6, 13, 4, 10, 16, 12),
                        Block.box(7, 14, 0, 9, 16, 4),
                        Block.box(6, 5, 5, 10, 13, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case WEST -> {
                return Stream.of(
                        Block.box(2, 0, 2, 14, 5, 14),
                        Block.box(6, 12, 0, 10, 16, 4),
                        Block.box(6, 13, 4, 10, 16, 12),
                        Block.box(7, 14, 12, 9, 16, 16),
                        Block.box(6, 5, 5, 10, 13, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            default -> {
                return Block.box(1, 0, 1, 15, 16, 15);
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ElvenTreatedAnvilblock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShapingAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return ElvenTreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClientSide ? null : ElvenTreatedAnvilblock.createTickerHelper(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
