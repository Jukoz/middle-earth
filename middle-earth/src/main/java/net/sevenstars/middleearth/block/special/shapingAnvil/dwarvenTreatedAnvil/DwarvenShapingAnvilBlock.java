package net.sevenstars.middleearth.block.special.shapingAnvil.dwarvenTreatedAnvil;

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

public class DwarvenShapingAnvilBlock extends AbstractShapingAnvilBlock {

    public DwarvenShapingAnvilBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DwarvenShapingAnvilBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 5, 15),
                        Block.box(2, 5, 3, 14, 12, 13),
                        Block.box(0, 12, 3, 16, 16, 13),
                        Block.box(0, 0, 5, 2, 7, 11),
                        Block.box(14, 0, 5, 16, 7, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 5, 15),
                        Block.box(3, 5, 2, 13, 12, 14),
                        Block.box(3, 12, 0, 13, 16, 16),
                        Block.box(5, 0, 14, 11, 7, 16),
                        Block.box(5, 0, 0, 11, 7, 2)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            default -> {
                return Block.box(1, 0, 1, 15, 16, 15);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShapingAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return DwarvenShapingAnvilBlock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClientSide ? null : DwarvenShapingAnvilBlock.createTickerHelper(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
