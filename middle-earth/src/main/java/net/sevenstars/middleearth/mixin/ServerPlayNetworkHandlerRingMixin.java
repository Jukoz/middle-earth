package net.sevenstars.middleearth.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.middleearth.item.items.rings.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Vanilla only accepts creative-menu slots 1-45; this lets the creative inventory set the worn ring slot too. */
@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerRingMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onCreativeInventoryAction", cancellable = true, at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/server/world/ServerWorld;)V"))
    private void middleearth$creativeRingSlot(CreativeInventoryActionC2SPacket packet, CallbackInfo ci) {
        if (packet.slot() != RingSlot.SCREEN_SLOT || !player.isInCreativeMode()) {
            return;
        }
        ci.cancel();
        PlayerScreenHandler handler = player.playerScreenHandler;
        Slot ring = handler.getSlot(RingSlot.SCREEN_SLOT);
        ItemStack stack = packet.stack();
        if (stack.isEmpty() || ring.canInsert(stack)) {
            ring.setStack(stack);
            handler.setReceivedStack(RingSlot.SCREEN_SLOT, stack);
        }
        handler.sendContentUpdates();
    }
}
