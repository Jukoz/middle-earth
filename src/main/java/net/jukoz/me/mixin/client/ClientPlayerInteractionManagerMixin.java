package net.jukoz.me.mixin.client;

import net.jukoz.me.item.items.ReachWeaponItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method="getReachDistance", at=@At("HEAD"), cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> cir){
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null && player.getMainHandStack().getItem() instanceof ReachWeaponItem rangedWeaponItem){
            cir.setReturnValue(rangedWeaponItem.getRangeDistance());
        }
    }
}
