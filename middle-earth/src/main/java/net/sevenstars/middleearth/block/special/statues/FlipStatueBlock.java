package net.sevenstars.middleearth.block.special.statues;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class FlipStatueBlock extends StatueBlock {
    public static final BooleanProperty FLIP = BooleanProperty.of("flip");

    public FlipStatueBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false).with(FLIP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLIP);
        super.appendProperties(builder);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            world.setBlockState(pos, state.cycle(FLIP));
            BlockState updatedState = world.getBlockState(pos);
            if(state.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState otherState = world.getBlockState(pos.up());
                world.setBlockState(pos.up(), otherState.with(FLIP, updatedState.get(FLIP)));
            } else {
                BlockState otherState = world.getBlockState(pos.down());
                world.setBlockState(pos.down(), otherState.with(FLIP, updatedState.get(FLIP)));
            }
        }
        return ActionResult.SUCCESS;
    }
}
