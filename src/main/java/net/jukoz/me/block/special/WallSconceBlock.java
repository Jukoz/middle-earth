package net.jukoz.me.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WallSconceBlock extends net.minecraft.block.WallTorchBlock {

    public static final BooleanProperty LIT = Properties.LIT;

    public WallSconceBlock(Settings settings, SimpleParticleType particleEffect) {
        super(particleEffect, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LIT, true).with(FACING, Direction.NORTH)))));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.isClient){
            return ActionResult.SUCCESS;
        } else {
            if(player.getStackInHand(player.getActiveHand()).isOf(Items.FLINT_AND_STEEL) && !state.get(LIT)){
                world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, 1.5F);
                world.setBlockState(pos, state.cycle(LIT));
            } else if (player.getStackInHand(player.getActiveHand()).isIn(ItemTags.SHOVELS) && state.get(LIT)) {
                world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, state.cycle(LIT));
            }
        }

        return super.onUse(state, world, pos, player, hit);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(state.get(LIT)){
            Direction direction = (Direction)state.get(FACING);
            double d = (double)pos.getX() + 0.5;
            double e = (double)pos.getY() + 0.9;
            double f = (double)pos.getZ() + 0.5;
            double g = 0.22;
            double h = 0.27;
            Direction direction2 = direction.getOpposite();
            world.addParticle(ParticleTypes.SMOKE, d + 0.27 * (double)direction2.getOffsetX(), e + 0.22, f + 0.27 * (double)direction2.getOffsetZ(), 0.0, 0.0, 0.0);
        }
    }
}
