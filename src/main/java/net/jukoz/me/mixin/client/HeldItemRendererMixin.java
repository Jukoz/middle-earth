package net.jukoz.me.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jukoz.me.item.items.weapons.ranged.CustomCrossbowWeaponItem;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(at = @At("HEAD"), method = "getHandRenderType", cancellable = true)
    private static void getHandRenderType(ClientPlayerEntity player, CallbackInfoReturnable<HeldItemRenderer.HandRenderType> cir) {
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        Hand hand = player.getActiveHand();
        boolean bl = itemStack.getItem() instanceof CustomCrossbowWeaponItem || itemStack2.getItem() instanceof CustomCrossbowWeaponItem
                || itemStack.isOf(Items.CROSSBOW) || itemStack2.isOf(Items.CROSSBOW)
                || itemStack.isOf(Items.BOW) || itemStack2.isOf(Items.BOW);
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

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z",
            ordinal = 1),method = "renderFirstPersonItem")
    private boolean renderFirstPersonItem(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.getItem() instanceof CustomCrossbowWeaponItem;
    }
}
