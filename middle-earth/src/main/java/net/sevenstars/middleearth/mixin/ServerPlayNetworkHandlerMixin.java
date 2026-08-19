package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.utils.PlayerActionHandlingContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayer player;

    @WrapMethod(method = "handlePlayerAction(Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket;)V")
    private void wrapHandlePlayerAction(ServerboundPlayerActionPacket packet, Operation<Void> original) {
        if (packet.getAction() != ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND) {
            original.call(packet);
            return;
        }

        PlayerActionHandlingContext.enter();
        try {
            original.call(packet);
        } finally {
            PlayerActionHandlingContext.exit();
        }
    }

    @Inject(method = "handlePlayerAction(Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket;)V", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
            target = "Lnet/minecraft/server/level/ServerPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), cancellable = true)
    public void handlePlayerAction(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        if (packet.getAction() != ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND) {
            return;
        }

        ItemStack stackMainHand = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        if ((stackMainHand.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded)
                || stackMainHand.getItem() instanceof CustomSiegeShieldItem
                || stackMainHand.getItem() instanceof CustomLongbowWeaponItem) {
            ci.cancel();
        }
    }
}
