package net.jukoz.me.block.special.shapingAnvil.dwarvenTreatedAnvil;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.AbstractTreatedAnvilBlock;
import net.jukoz.me.block.special.shapingAnvil.TreatedAnvilBlockEntity;
import net.jukoz.me.block.special.shapingAnvil.treatedAnvil.TreatedAnvilblock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DwarvenShapingAnvilBlock extends AbstractTreatedAnvilBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public DwarvenShapingAnvilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(DwarvenShapingAnvilBlock::new);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 5, 15),
                        Block.createCuboidShape(2, 5, 3, 14, 12, 13),
                        Block.createCuboidShape(0, 12, 3, 16, 16, 13),
                        Block.createCuboidShape(0, 0, 5, 2, 7, 11),
                        Block.createCuboidShape(14, 0, 5, 16, 7, 11)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 5, 15),
                        Block.createCuboidShape(3, 5, 2, 13, 12, 14),
                        Block.createCuboidShape(3, 12, 0, 13, 16, 16),
                        Block.createCuboidShape(5, 0, 14, 11, 7, 16),
                        Block.createCuboidShape(5, 0, 0, 11, 7, 2)
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
        return new TreatedAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return DwarvenShapingAnvilBlock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<TreatedAnvilBlockEntity> expectedType) {
        return world.isClient ? null : DwarvenShapingAnvilBlock.validateTicker(givenType, expectedType, TreatedAnvilBlockEntity::tick);
    }
}
