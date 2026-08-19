package net.sevenstars.middleearth.block.special.forge;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends BaseEntityBlock implements EntityBlock {
    public static final EnumProperty<ForgePart> PART = EnumProperty.create("part", ForgePart.class);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ForgeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(((this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(LIT, false).setValue(PART, ForgePart.BOTTOM));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ForgeBlock::new);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ForgeBlockEntity forgeBlockEntity) {
                if (state.getValue(PART) == ForgePart.BOTTOM) {
                    MetalTypes metal = forgeBlockEntity.getCurrentMetal();
                    int storage = forgeBlockEntity.getStorage();

                    if (metal != MetalTypes.EMPTY) {
                        ItemStack ingotStack = new ItemStack(metal.getIngot(), storage / 144);
                        ingotStack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(1000));
                        ItemStack nuggetStack = new ItemStack(metal.getNugget(), storage % 144 / 16);
                        nuggetStack.set(DataComponentTypesME.TEMPERATURE_DATA, new TemperatureDataComponent(1000));

                        Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ingotStack);
                        Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), nuggetStack);
                    }
                    Containers.dropContents(world, pos, forgeBlockEntity);
                }
            }
            super.onRemove(state, world, pos, newState, moved);
            world.updateNeighbourForOutputSignal(pos, this);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos().offset(0,1,0);
        Level world = ctx.getLevel();
        Direction direction = ctx.getHorizontalDirection().getOpposite();

        return world.getBlockState(pos).canBeReplaced(ctx) && world.getWorldBorder().isWithinBounds(pos) ? (BlockState)this.defaultBlockState().setValue(FACING, direction).setValue(PART, ForgePart.BOTTOM) : null;
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            BlockPos blockPos = pos.offset(0,1,0);
            world.setBlock(blockPos, (BlockState)state.setValue(PART, ForgePart.TOP), 3);
            world.updateNeighborsAt(pos, Blocks.AIR);
            state.updateNeighbourShapes(world, pos, 3);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide) {
            ForgePart forgePart = (ForgePart)state.getValue(PART);
            ForgePart forgePartOpposite = (ForgePart)state.getValue(PART).getOpposite(state.getValue(PART));
            BlockPos blockPos;
            if(forgePart == ForgePart.BOTTOM){
                blockPos = pos.offset(0,1,0);
            } else {
                blockPos = pos.offset(0,-1,0);
            }
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(this) && blockState.getValue(PART) == forgePartOpposite) {
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockPos, Block.getId(blockState));
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(LIT);
        builder.add(PART);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if(state.getValue(PART) == ForgePart.BOTTOM){
                MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                if(screenHandlerFactory != null) {
                    if (screenHandlerFactory instanceof net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME extendedProvider) {
                        net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME.open(player, extendedProvider);
                    } else {
                        player.openMenu(screenHandlerFactory);
                    }
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, ModBlockEntities.FORGE);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ForgeBlockEntity> expectedType) {
        BlockEntityTicker ticker;
        if (world instanceof ServerLevel serverWorld) {
            ticker = createTickerHelper(givenType, expectedType, (worldx, pos, state, blockEntity) -> {
                ForgeBlockEntity.tick(serverWorld, pos, state, blockEntity);
            });
        } else {
            ticker = null;
        }

        return ticker;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT).booleanValue()) {
            return;
        }
        if (state.getValue(PART) == ForgePart.BOTTOM){
            double d = (double)pos.getX() + 0.5;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playLocalSound(d, e, f, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double g = 0.52;
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : h;
            double j = random.nextDouble() * 6.0 / 16.0;
            double k = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        } else {
            SimpleParticleType simpleParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            world.addAlwaysVisibleParticle(simpleParticleType, true, (double)pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        }
    }
}
