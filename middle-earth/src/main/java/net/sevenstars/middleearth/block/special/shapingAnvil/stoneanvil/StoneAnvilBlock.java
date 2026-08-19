package net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil;

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

public class StoneAnvilBlock extends AbstractShapingAnvilBlock {

    public StoneAnvilBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StoneAnvilBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 10, 15),
                        Block.box(2, 10, 3, 14, 16, 14),
                        Block.box(3, 10, 1, 10, 14, 3)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case EAST -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 10, 15),
                        Block.box(2, 10, 2, 13, 16, 14),
                        Block.box(13, 10, 3, 15, 14, 10)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }
            case SOUTH -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 10, 15),
                        Block.box(2, 10, 2, 14, 16, 13),
                        Block.box(6, 10, 13, 13, 14, 15)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }

            case WEST -> {
                return Stream.of(
                        Block.box(1, 0, 1, 15, 10, 15),
                        Block.box(3, 10, 2, 14, 16, 14),
                        Block.box(1, 10, 6, 3, 14, 13)
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
        return new StoneAnvilBlockEntity(ModBlockEntities.STONE_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return StoneAnvilBlock.validateTicker(world, type, ModBlockEntities.STONE_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<StoneAnvilBlockEntity> expectedType) {
        return world.isClientSide ? null : StoneAnvilBlock.createTickerHelper(givenType, expectedType, StoneAnvilBlockEntity::tick);
    }
}
