package net.jukoz.me.mixin.client;

import net.jukoz.me.item.items.weapons.ranged.CustomCrossbowWeaponItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(at = @At("HEAD"), method = "getHandRenderType", cancellable = true)
    private static void getHandRenderType(ClientPlayerEntity player, CallbackInfoReturnable<HeldItemRenderer.HandRenderType> cir) {
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        Hand hand = player.getActiveHand();
        boolean bl = itemStack.getItem() instanceof CustomCrossbowWeaponItem || itemStack2.getItem() instanceof CustomCrossbowWeaponItem
                || itemStack.isOf(Items.CROSSBOW) || itemStack2.isOf(Items.CROSSBOW);
        if (!bl) {
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_BOTH_HANDS);
        } else if (player.isUsingItem()){
            cir.setReturnValue(HeldItemRenderer.HandRenderType.shouldOnlyRender(hand));
        } else {
            cir.setReturnValue(isChargedCrossbow(itemStack) ? HeldItemRenderer.HandRenderType.RENDER_MAIN_HAND_ONLY : HeldItemRenderer.HandRenderType.RENDER_BOTH_HANDS);
        }
    }

    @Inject(at = @At("HEAD"), method = "getUsingItemHandRenderType", cancellable = true)
    private static void getUsingItemHandRenderType(ClientPlayerEntity player, CallbackInfoReturnable<HeldItemRenderer.HandRenderType> cir) {
        ItemStack itemStack = player.getActiveItem();
        Hand hand = player.getActiveHand();
        if (!itemStack.isOf(Items.BOW) && !(itemStack.getItem() instanceof CustomCrossbowWeaponItem) && !itemStack.isOf(Items.CROSSBOW)) {
            cir.setReturnValue(hand == Hand.MAIN_HAND && isChargedCrossbow(player.getOffHandStack()) ? HeldItemRenderer.HandRenderType.RENDER_MAIN_HAND_ONLY : HeldItemRenderer.HandRenderType.RENDER_BOTH_HANDS);
        } else {
            cir.setReturnValue(HeldItemRenderer.HandRenderType.shouldOnlyRender(hand));
        }
    }

    @Unique
    private static boolean isChargedCrossbow(ItemStack stack) {
        return (stack.getItem() instanceof CustomCrossbowWeaponItem || stack.isOf(Items.CROSSBOW)) && CrossbowItem.isCharged(stack);
    }
}
