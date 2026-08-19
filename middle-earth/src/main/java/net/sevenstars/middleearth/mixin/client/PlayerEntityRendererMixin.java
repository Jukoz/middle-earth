package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomCrossbowWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(
            at = @At("TAIL"),
            method = "getArmPose(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;",
            cancellable = true
    )
    private static void useCustomCrossbowPose(
            AbstractClientPlayer player,
            InteractionHand hand,
            CallbackInfoReturnable<HumanoidModel.ArmPose> cir
    ) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof CustomCrossbowWeaponItem && CrossbowItem.isCharged(itemStack)) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
