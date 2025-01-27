package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Shadow @Final
    private MinecraftServer server;

    @Inject(method = "tickChunk", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), cancellable = true)
    private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if(ModDimensions.isInMiddleEarth(chunk.getWorld())) {
            ci.cancel();
        }
    }

    // Reason of addition
    // MC-188578 - Sleeping in a bed in a custom dimension doesn't set time to day
    // Link : https://bugs.mojang.com/browse/MC-188578
    @Inject(method = "wakeSleepingPlayers", at = @At(value = "INVOKE"))
    private void wakeSleepingPlayers(CallbackInfo ci) {
        // The math is like vanilla (ServerWorld lines ~[310-319])
        if (server.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            long currentTimeOfDay = server.getWorld(World.OVERWORLD).getTimeOfDay() + 24000L;
            server.getWorld(World.OVERWORLD).setTimeOfDay(currentTimeOfDay - currentTimeOfDay % 24000L);
        }
    }
}
