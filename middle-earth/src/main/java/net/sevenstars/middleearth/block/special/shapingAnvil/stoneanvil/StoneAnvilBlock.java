package net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil;

import com.mojang.serialization.MapCodec;
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
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.shapingAnvil.AbstractShapingAnvilBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class StoneAnvilBlock extends AbstractShapingAnvilBlock {

    public StoneAnvilBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(StoneAnvilBlock::new);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 10, 15),
                        Block.createCuboidShape(2, 10, 3, 14, 16, 14),
                        Block.createCuboidShape(3, 10, 1, 10, 14, 3)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case EAST -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 10, 15),
                        Block.createCuboidShape(2, 10, 2, 13, 16, 14),
                        Block.createCuboidShape(13, 10, 3, 15, 14, 10)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 10, 15),
                        Block.createCuboidShape(2, 10, 2, 14, 16, 13),
                        Block.createCuboidShape(6, 10, 13, 13, 14, 15)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }

            case WEST -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 10, 15),
                        Block.createCuboidShape(3, 10, 2, 14, 16, 14),
                        Block.createCuboidShape(1, 10, 6, 3, 14, 13)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            default -> {
                return Block.createCuboidShape(1, 0, 1, 15, 16, 15);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneAnvilBlockEntity(ModBlockEntities.STONE_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return StoneAnvilBlock.validateTicker(world, type, ModBlockEntities.STONE_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<StoneAnvilBlockEntity> expectedType) {
        return world.isClient ? null : StoneAnvilBlock.validateTicker(givenType, expectedType, StoneAnvilBlockEntity::tick);
    }
}
