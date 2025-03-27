package net.sevenstars.middleearth.block.special.shapingAnvil;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.sevenstars.middleearth.item.items.SmithingHammerItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTreatedAnvilBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public AbstractTreatedAnvilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TreatedAnvilBlockEntity treatedAnvilBlockEntity) {
                ItemScatterer.spawn(world, pos, treatedAnvilBlockEntity);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!world.isClient) {
            if (player.getMainHandStack().isEmpty() && !blockEntity.getStack(0).isEmpty()) {
                player.equipStack(EquipmentSlot.MAINHAND, blockEntity.getStack(0));
                blockEntity.removeStack(0);
            } else {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.MAINHAND);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient) {
            if (stack.getItem() instanceof SmithingHammerItem hammer && player.getAttackCooldownProgress(0.5f) > 0.9f) {
                player.incrementStat(Stats.USED.getOrCreateStat(hammer));
                stack.use(world, player, player.getActiveHand());
                player.getStackInHand(player.getActiveHand()).damage(1, player, EquipmentSlot.MAINHAND);
                if (blockEntity instanceof TreatedAnvilBlockEntity shapingAnvilBlockEntity) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    shapingAnvilBlockEntity.bonk(shapingAnvilBlockEntity, serverWorld);
                }
            }
        }
    }
}
