package net.sevenstars.middleearth.block.special.curtains;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class CurtainsBlock extends MultifaceBlock {
    public static final MapCodec<CurtainsBlock> CODEC = createCodec(CurtainsBlock::new);
    public static final BooleanProperty TIP;

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
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState returnState = (BlockState) Arrays.stream(ctx.getPlacementDirections()).map((direction) -> {
            return this.withDirection(blockState, world, blockPos, direction);
        }).filter(Objects::nonNull).findFirst().orElse((BlockState) null);
        if(returnState != null) returnState = returnState.with(TIP,
                !world.getBlockState(blockPos.down()).isIn(BlockTagsME.CURTAINS));
        return returnState;
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
        boolean placeVertically = false;
        if(context.getSide() == Direction.UP) {
            if(!context.getStack().isOf(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getWorld().getBlockState(context.getBlockPos().up());
                if(context.getStack().isOf(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        } else if(context.getSide() == Direction.DOWN) {
            if(!context.getStack().isOf(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getWorld().getBlockState(context.getBlockPos().down());
                if(context.getStack().isOf(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        }
        if(sneaking) placeVertically = !placeVertically;
        return isNotFullBlock(state) && !placeVertically && context.getStack().isOf(this.asItem());
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state.with(TIP, !world.getBlockState(pos.down()).isIn(BlockTagsME.CURTAINS));
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
        Map<Direction, VoxelShape> map = VoxelShapes.createFacingShapeMap(Block.createCuboidZShape(16.0, 0.0, 3.0));
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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{TIP});
        super.appendProperties(builder);
    }

    static {
        TIP = Properties.TIP;
    }
}
