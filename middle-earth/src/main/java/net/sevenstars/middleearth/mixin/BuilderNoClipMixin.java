package net.sevenstars.middleearth.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class BuilderNoClipMixin {
    // The game sets this anew on every tick, to whether the player is a spectator
    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;noClip:Z", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void middleEarth$keepNoClip(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if(!StructureEditing.isNoClip(player))
            return;
        player.noClip = true;
        player.setOnGround(false);
        // With nothing to stand on, a builder who stopped flying would fall out of the world
        player.getAbilities().flying = true;
    }
}
