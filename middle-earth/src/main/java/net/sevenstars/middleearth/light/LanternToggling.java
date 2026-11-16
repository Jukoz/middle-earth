package net.sevenstars.middleearth.light;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.item.DataComponentTypesME;

/** Using a lantern or lamp from the off hand shades or lights it instead of placing it. */
public final class LanternToggling {
    private LanternToggling() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> use(player, world, hand));
        UseItemCallback.EVENT.register(LanternToggling::use);
    }

    private static ActionResult use(PlayerEntity player, World world, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.isSpectator() || hand != Hand.OFF_HAND
            || !(HeldLights.of(world, stack) instanceof HeldLight.Glow glow) || !glow.toggled()) {
            return ActionResult.PASS;
        }
        boolean on = !HeldLights.isOn(stack);
        if (!world.isClient) {
            stack.set(DataComponentTypesME.LAMP_ON, on);
            world.playSound(null, player.getBlockPos(), on ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.BLOCK_CANDLE_EXTINGUISH,
                SoundCategory.PLAYERS, 0.6F, 1.3F);
        }
        return ActionResult.SUCCESS;
    }
}
