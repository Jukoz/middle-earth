package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import org.jetbrains.annotations.Nullable;

public class MistweedPlantBlock extends CustomPlantBlock implements Fertilizable {
    public static final MapCodec<CustomPlantBlock> CODEC = createCodec(MistweedPlantBlock::new);
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public MistweedPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, true));
    }

    @Override
    protected MapCodec<CustomPlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ACTIVE) && random.nextDouble() <= 0.45) {
            double d = (double)pos.getX() + random.nextDouble() * 16.0 - 8.0;
            double e = (double)pos.getY() + random.nextDouble() * 5.0;
            double f = (double)pos.getZ() + random.nextDouble() * 16.0 - 8.0;
            world.addImportantParticleClient(ModParticleTypes.BIOME_FOG_PARTICLE, true, d, e, f, 0.0, 0.0, 0.0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!state.get(ACTIVE) && itemStack.isIn(ItemTags.AXES)) {
                activateState(null, state, world, pos);
            } else if (state.get(ACTIVE) && itemStack.isOf(Items.HONEYCOMB)) {
                deactivateState(null, state, world, pos);
            }
        }
        return ActionResult.SUCCESS;
    }

    protected static void activateState(@Nullable PlayerEntity player, BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(ACTIVE, true), 11);
        world.playSound(null, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        ParticleUtil.spawnParticlesAround(world, pos, 3, 3.0, 1.0, true, ParticleTypes.WAX_OFF);
    }

    protected static void deactivateState(@Nullable PlayerEntity player, BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(ACTIVE, false), 11);
        world.playSound(null, pos, SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        ParticleUtil.spawnParticlesAround(world, pos, 3, 3.0, 1.0, true, ParticleTypes.WAX_ON);
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return Fertilizable.canSpread(world, pos, state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Fertilizable.findPosToSpreadTo(world, pos, state).ifPresent((blockPos) -> {
            world.setBlockState(blockPos, this.getDefaultState());
        });
    }
}
