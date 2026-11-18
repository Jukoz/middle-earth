package net.sevenstars.middleearth.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.item.items.rings.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** The worn ring drops on death with the inventory. */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityRingMixin {
    @Inject(method = "dropInventory", at = @At("HEAD"))
    private void middleearth$dropWornRing(ServerWorld world, CallbackInfo ci) {
        RingSlot.dropOnDeath((PlayerEntity) (Object) this, world);
    }
}
