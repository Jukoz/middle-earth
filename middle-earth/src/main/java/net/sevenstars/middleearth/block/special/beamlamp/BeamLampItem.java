package net.sevenstars.middleearth.block.special.beamlamp;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.light.HeldLights;

/**
 * A focused lamp as an item: placed from the main hand like a lantern; in the off hand it is a hand lamp,
 * and using it there lights or shades it (it never places from the off hand).
 */
public class BeamLampItem extends BlockItem {
    public BeamLampItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getHand() == Hand.OFF_HAND) {
            toggle(context.getStack(), context.getWorld(), context.getPlayer());
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.OFF_HAND) {
            toggle(user.getStackInHand(hand), world, user);
            return ActionResult.SUCCESS;
        }
        return super.use(world, user, hand);
    }

    private static void toggle(ItemStack stack, World world, PlayerEntity player) {
        boolean on = !HeldLights.isOn(stack);
        stack.set(DataComponentTypesME.LAMP_ON, on);
        if (!world.isClient && player != null) {
            world.playSound(null, player.getBlockPos(), on ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.BLOCK_CANDLE_EXTINGUISH,
                SoundCategory.PLAYERS, 0.6F, 1.3F);
        }
    }
}
