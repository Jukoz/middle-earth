package net.sevenstars.middleearth.block.special.shapingAnvil.elvenTreatedAnvil;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.special.shapingAnvil.AbstractTreatedAnvilBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.TreatedAnvilBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ElvenTreatedAnvilblock extends AbstractTreatedAnvilBlock {
    public ElvenTreatedAnvilblock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(12, 12, 6, 16, 16, 10),
                        Block.createCuboidShape(4, 13, 6, 12, 16, 10),
                        Block.createCuboidShape(0, 14, 7, 4, 16, 9),
                        Block.createCuboidShape(5, 5, 6, 11, 13, 10)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(0, 12, 6, 4, 16, 10),
                        Block.createCuboidShape(4, 13, 6, 12, 16, 10),
                        Block.createCuboidShape(12, 14, 7, 16, 16, 9),
                        Block.createCuboidShape(5, 5, 6, 11, 13, 10)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case EAST -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(6, 12, 12, 10, 16, 16),
                        Block.createCuboidShape(6, 13, 4, 10, 16, 12),
                        Block.createCuboidShape(7, 14, 0, 9, 16, 4),
                        Block.createCuboidShape(6, 5, 5, 10, 13, 11)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case WEST -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(6, 12, 0, 10, 16, 4),
                        Block.createCuboidShape(6, 13, 4, 10, 16, 12),
                        Block.createCuboidShape(7, 14, 12, 9, 16, 16),
                        Block.createCuboidShape(6, 5, 5, 10, 13, 11)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            default -> {
                return Block.createCuboidShape(1, 0, 1, 15, 16, 15);
            }
        }
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ElvenTreatedAnvilblock::new);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TreatedAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return ElvenTreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<TreatedAnvilBlockEntity> expectedType) {
        return world.isClient ? null : ElvenTreatedAnvilblock.validateTicker(givenType, expectedType, TreatedAnvilBlockEntity::tick);
    }
}
