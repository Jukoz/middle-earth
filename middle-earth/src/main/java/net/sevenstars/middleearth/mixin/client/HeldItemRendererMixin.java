package net.sevenstars.middleearth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomCrossbowWeaponItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
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


    // The mace has a super long cooldown so remove the weird weapon raising animation.
    @WrapOperation(method = "updateHeldItems", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
    private float updateHeldItems(ClientPlayerEntity player, float baseTime, Operation<Float> original) {
        if(player.getVehicle() instanceof CaveTrollEntity troll && troll.getControllingPassenger() == player) {
            return 1.0f;
        }
        float progress = original.call(player, baseTime);
        float cooldownTicks = player.getAttackCooldownProgressPerTick();
        return cooldownTicks > 20.0f ? MathHelper.clamp(progress * cooldownTicks / 20.0f, 0.0f, 1.0f) : progress;
    }

    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean renderFirstPersonItem(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.getItem() instanceof CustomCrossbowWeaponItem;
    }
}
