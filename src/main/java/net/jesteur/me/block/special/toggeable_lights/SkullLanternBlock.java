package net.jesteur.me.block.special.toggeable_lights;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class SkullLanternBlock extends AbstractToggeableLightBlock {
    public static final BooleanProperty HANGING;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final VoxelShape STANDING_SHAPE, HANGING_SHAPE;

    public SkullLanternBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(HANGING, false).with(WATERLOGGED, false).with(LEVEL_15, 15));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add (HANGING);
        super.appendProperties(builder);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction[] var3 = ctx.getPlacementDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && !state.get(LIT)) {
            world.setBlockState(hit.getBlockPos(), state.with(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    protected Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    static {
        HANGING = Properties.HANGING;
        STATE_TO_LUMINANCE = (state) -> { return state.get(LIT) ? state.get(LEVEL_15) : 0; };

        STANDING_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 7.0, 11.0),
                Block.createCuboidShape(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
        HANGING_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0, 1.0, 5.0, 11.0, 8.0, 11.0),
                Block.createCuboidShape(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));
    }
}
