package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomCrossbowWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {

    @Inject(
            at = @At("HEAD"),
            method = "evaluateWhichHandsToRender(Lnet/minecraft/client/player/LocalPlayer;)Lnet/minecraft/client/renderer/ItemInHandRenderer$HandRenderSelection;",
            cancellable = true
    )
    private static void getHandRenderType(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemStack = player.getMainHandItem();
        ItemStack itemStack2 = player.getOffhandItem();
        InteractionHand hand = player.getUsedItemHand();
        boolean bl = itemStack.getItem() instanceof CustomCrossbowWeaponItem || itemStack2.getItem() instanceof CustomCrossbowWeaponItem
                || itemStack.is(Items.CROSSBOW) || itemStack2.is(Items.CROSSBOW)
                || itemStack.is(Items.BOW) || itemStack2.is(Items.BOW);
        if (!bl) {
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        } else if (player.isUsingItem()){
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(hand));
        } else {
            cir.setReturnValue(isChargedCrossbow(itemStack) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "selectionUsingItemWhileHoldingBowLike(Lnet/minecraft/client/player/LocalPlayer;)Lnet/minecraft/client/renderer/ItemInHandRenderer$HandRenderSelection;",
            cancellable = true
    )
    private static void getUsingItemHandRenderType(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemStack = player.getUseItem();
        InteractionHand hand = player.getUsedItemHand();
        if (!itemStack.is(Items.BOW) && !(itemStack.getItem() instanceof CustomCrossbowWeaponItem) && !itemStack.is(Items.CROSSBOW)) {
            cir.setReturnValue(hand == InteractionHand.MAIN_HAND && isChargedCrossbow(player.getOffhandItem()) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        } else {
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(hand));
        }
    }

    @Unique
    private static boolean isChargedCrossbow(ItemStack stack) {
        return (stack.getItem() instanceof CustomCrossbowWeaponItem || stack.is(Items.CROSSBOW)) && CrossbowItem.isCharged(stack);
    }
}
