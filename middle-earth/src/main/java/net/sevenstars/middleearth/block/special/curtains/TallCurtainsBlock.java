package net.sevenstars.middleearth.block.special.curtains;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class TallCurtainsBlock extends HorizontalFacingBlock {
    public static final MapCodec<TallCurtainsBlock> CODEC = createCodec(TallCurtainsBlock::new);
    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES;
    protected static final Direction[] HORIZONTAL_DIRECTIONS;

    public TallCurtainsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(this.asItem()) || isNotFullBlock(state);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        return (BlockState) Arrays.stream(ctx.getPlacementDirections()).map((direction) -> {
            return this.withDirection(blockState, world, blockPos, direction);
        }).filter(Objects::nonNull).findFirst().orElse((BlockState) null);
    }

    protected Function<BlockState, VoxelShape> createShapeFunction() {
        Map<Direction, VoxelShape> map = VoxelShapes.createFacingShapeMap(Block.createCuboidZShape(16.0, 0.0, 1.0));
        return this.createShapeFunction((state) -> {
            VoxelShape voxelShape = VoxelShapes.empty();
            Direction[] var3 = HORIZONTAL_DIRECTIONS;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                if (hasDirection(state, direction)) {
                    voxelShape = VoxelShapes.union(voxelShape, (VoxelShape)map.get(direction));
                }
            }

            return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
        }, new Property[]{});
    }

    public static boolean hasDirection(BlockState state, Direction direction) {
        BooleanProperty booleanProperty = getProperty(direction);
        return (Boolean)state.get(booleanProperty, false);
    }

    public static BooleanProperty getProperty(Direction direction) {
        return (BooleanProperty)FACING_PROPERTIES.get(direction);
    }

    public boolean canGrowWithDirection(BlockView world, BlockState state, BlockPos pos, Direction direction) {
        if ((!state.isOf(this) || !hasDirection(state, direction))) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    public BlockState withDirection(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!this.canGrowWithDirection(world, state, pos, direction)) {
            return null;
        } else {
            BlockState blockState;
            if (state.isOf(this)) {
                blockState = state;
            } else {
                blockState = this.getDefaultState();
            }

            return (BlockState)blockState.with(getProperty(direction), true);
        }
    }

    private static boolean isNotFullBlock(BlockState state) {
        Direction[] var1 = HORIZONTAL_DIRECTIONS;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Direction direction = var1[var3];
            if (!hasDirection(state, direction)) {
                return true;
            }
        }

        return false;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        Direction[] var2 = HORIZONTAL_DIRECTIONS;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Direction direction = var2[var4];
            builder.add(getProperty(direction));
        }
    }


    static {
        FACING_PROPERTIES = ImmutableMap.copyOf(Maps.newEnumMap(Map.of(Direction.NORTH, Properties.NORTH, Direction.EAST, Properties.EAST, Direction.SOUTH, Properties.SOUTH, Direction.WEST, Properties.WEST)));
        HORIZONTAL_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};
    }
}
