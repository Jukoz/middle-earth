package net.jukoz.me.mixin;

import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "tickChunk", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), cancellable = true)
    private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if(ModDimensions.isInMiddleEarth(chunk.getWorld())) {
            ci.cancel();
        }
    }
}
