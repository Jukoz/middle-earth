package net.sevenstars.middleearth.block.special.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.TintedParticleLeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;

public class BerryHollyLeavesBlock extends TintedParticleLeavesBlock {

    public BerryHollyLeavesBlock(float f, Settings settings) {
        super(f, settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.BONE_MEAL)) {
            world.setBlockState(pos, ModNatureBlocks.BERRY_HOLLY_LEAVES.getDefaultState());
            return ActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}