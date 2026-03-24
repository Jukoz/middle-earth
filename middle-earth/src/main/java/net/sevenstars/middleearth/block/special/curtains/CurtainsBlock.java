package net.sevenstars.middleearth.block.special.curtains;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.Map;
import java.util.function.Function;

public class CurtainsBlock extends MultifaceBlock {
    public static final MapCodec<CurtainsBlock> CODEC = createCodec(CurtainsBlock::new);
    private final Function<BlockState, VoxelShape> shapeFunction;

    public CurtainsBlock(Settings settings) {
        super(settings);
        shapeFunction = createShapeFunction();
    }

    @Override
    public MapCodec<? extends MultifaceBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canHaveDirection(Direction direction) {
        return direction != Direction.UP && direction != Direction.DOWN;
    }

    @Override
    public boolean canGrowWithDirection(BlockView world, BlockState state, BlockPos pos, Direction direction) {
        return canHaveDirection(direction);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        boolean sneaking = false;
        if(context.getPlayer() != null) sneaking = context.getPlayer().isSneaking();
        return isNotFullBlock(state) && !sneaking && context.getStack().isOf(this.asItem());
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if ((Boolean)state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    private static boolean isNotFullBlock(BlockState state) {
        Direction[] var1 = DIRECTIONS;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Direction direction = var1[var3];
            if (!hasDirection(state, direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape)this.shapeFunction.apply(state);
    }

    private Function<BlockState, VoxelShape> createShapeFunction() {
        Map<Direction, VoxelShape> map = VoxelShapes.createFacingShapeMap(Block.createCuboidZShape(16.0, 0.0, 4.0));
        return this.createShapeFunction((state) -> {
            VoxelShape voxelShape = VoxelShapes.empty();
            Direction[] var3 = DIRECTIONS;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                if (hasDirection(state, direction)) {
                    voxelShape = VoxelShapes.union(voxelShape, (VoxelShape)map.get(direction));
                }
            }

            return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
        }, new Property[]{WATERLOGGED});
    }
}
