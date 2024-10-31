package net.jukoz.me.block.special.bellows;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.TreatedAnvilBlockEntity;
import net.jukoz.me.block.special.shapingAnvil.treatedAnvil.TreatedAnvilblock;
import net.jukoz.me.sound.ModSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BellowsBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public BellowsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(BellowsBlock::new);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BellowsBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BellowsBlock.validateTicker(world, type, ModBlockEntities.BELLOWS);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<BellowsBlockEntity> expectedType) {
        return world.isClient ? null : BellowsBlock.validateTicker(givenType, expectedType, BellowsBlockEntity::tick);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(3, 0, 3, 13, 7, 13);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        BellowsBlockEntity bellowsBlockEntity = (BellowsBlockEntity) world.getBlockEntity(pos);
        Direction direction = (Direction)world.getBlockState(pos).get(FACING);

        if(bellowsBlockEntity != null) {
            bellowsBlockEntity.pumpBellows(state, world, pos, bellowsBlockEntity, direction);
            return ActionResult.CONSUME;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof BellowsBlockEntity bellowsBlockEntity && !world.isClient){
            if (entity instanceof LivingEntity){
                if (entity.getVelocity().y < -0.1f) {
                    Direction direction = (Direction)world.getBlockState(pos).get(FACING);

                    bellowsBlockEntity.pumpBellows(state, world, pos, bellowsBlockEntity, direction);
                    Random random = new Random();
                    world.playSound((PlayerEntity) null, pos, ModSounds.BELLOWS_PUSH, SoundCategory.BLOCKS, 0.95F + random.nextFloat() * 0.05F, 0.95F + random.nextFloat() * 0.05F);
                    world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
                }
            }
        }

        Vec3i direction = state.get(BellowsBlock.FACING).getVector();
        Vec3d center = pos.toCenterPos();

        world.addParticle(ParticleTypes.POOF,
                center.getX() + direction.getX() * 0.4f,
                center.getY() - 0.2f,
                center.getZ() + direction.getZ() * 0.4f,
                0, 0, 0);

    }
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.bounceEntity(entity);
        }
    }

    private void bounceEntity(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(vec3d.x, -vec3d.y * 0.6 * d, vec3d.z);
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
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }
}
