package net.jukoz.me.block.special.shapingAnvil.orcishTreatedAnvil;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.AbstractTreatedAnvilBlock;
import net.jukoz.me.block.special.shapingAnvil.TreatedAnvilBlockEntity;
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

public class OrcishTreatedAnvilblock extends AbstractTreatedAnvilBlock {
    public OrcishTreatedAnvilblock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(10, 7, 4, 12, 10, 12),
                        Block.createCuboidShape(4, 7, 4, 6, 10, 12),
                        Block.createCuboidShape(3, 0, 3, 13, 7, 13),
                        Block.createCuboidShape(5, 7, 5, 11, 13, 11),
                        Block.createCuboidShape(4, 13, 5, 12, 16, 11),
                        Block.createCuboidShape(1, 14, 7, 4, 16, 9),
                        Block.createCuboidShape(12, 14, 7, 15, 16, 9),
                        Block.createCuboidShape(7, 7, 4, 9, 11, 12)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.createCuboidShape(4, 7, 4, 12, 10, 6),
                        Block.createCuboidShape(4, 7, 10, 12, 10, 12),
                        Block.createCuboidShape(3, 0, 3, 13, 7, 13),
                        Block.createCuboidShape(5, 7, 5, 11, 13, 11),
                        Block.createCuboidShape(5, 13, 4, 11, 16, 12),
                        Block.createCuboidShape(7, 14, 12, 9, 16, 15),
                        Block.createCuboidShape(7, 14, 1, 9, 16, 4),
                        Block.createCuboidShape(4, 7, 7, 12, 11, 9)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            default -> {
                return Block.createCuboidShape(1, 0, 1, 15, 16, 15);
            }
        }
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(OrcishTreatedAnvilblock::new);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TreatedAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return OrcishTreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<TreatedAnvilBlockEntity> expectedType) {
        return world.isClient ? null : OrcishTreatedAnvilblock.validateTicker(givenType, expectedType, TreatedAnvilBlockEntity::tick);
    }
}
