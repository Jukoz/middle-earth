package net.sevenstars.middleearth.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.sevenstars.middleearth.item.utils.TwoHandedItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    public void onPlayerAction(PlayerActionC2SPacket packet, CallbackInfo ci) {
        ItemStack mainHandStack = this.player.getStackInHand(Hand.MAIN_HAND);
        if (TwoHandedItems.isTwoHanded(mainHandStack)) {
            ci.cancel();
            return;
        }
        // The off hand reads as empty while it holds a two-handed item, so the vanilla swap would overwrite it
        PlayerInventory inventory = this.player.getInventory();
        ItemStack offHandStack = inventory.getStack(PlayerInventory.OFF_HAND_SLOT);
        if (TwoHandedItems.isTwoHanded(offHandStack)) {
            inventory.setStack(PlayerInventory.OFF_HAND_SLOT, mainHandStack);
            inventory.setSelectedStack(offHandStack);
            this.player.clearActiveItem();
            ci.cancel();
        }
    }
}
