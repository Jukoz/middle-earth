package net.sevenstars.middleearth.block.special;

import net.minecraft.block.*;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TorchOfOrthancBlock extends TorchBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 18.0, 11.0);
    public static final BooleanProperty LIT = Properties.LIT;

    public TorchOfOrthancBlock(Settings settings, SimpleParticleType particleEffect) {
        super(particleEffect, settings);
        this.setDefaultState((((this.stateManager.getDefaultState().with(LIT, true)))));
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
        builder.add(new Property[]{LIT});
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(state.get(LIT)){
            double d = (double)pos.getX() + 0.5;
            double e = (double)pos.getY() + 1.4;
            double f = (double)pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
