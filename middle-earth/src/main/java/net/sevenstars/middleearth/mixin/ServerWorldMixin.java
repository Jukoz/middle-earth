package net.sevenstars.middleearth.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.rule.GameRules;
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

    //TODO BROKY
    /*
    @Inject(method = "tickChunk", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), cancellable = true)
    private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if(ModDimensions.isInMiddleEarth(chunk.getWorld())) {
            ci.cancel();
        }
    }*/

    // Reason of addition
    // MC-188578 - Sleeping in a bed in a custom dimension doesn't set time to day
    // Link : https://bugs.mojang.com/browse/MC-188578
    @Inject(method = "wakeSleepingPlayers", at = @At("TAIL"))
    private void wakeSleepingPlayers(CallbackInfo ci) {
        ServerWorld overworld = server.getOverworld();
        // Vanilla already skips the night and clears the weather when the sleepers are in the Overworld
        if ((Object) this == overworld) return;

        // The math is like vanilla (ServerWorld lines ~[310-319])
        if (overworld.getGameRules().getValue(GameRules.ADVANCE_TIME)) {
            long currentTimeOfDay = overworld.getTimeOfDay() + 24000L;
            overworld.setTimeOfDay(currentTimeOfDay - currentTimeOfDay % 24000L);
        }
        if (overworld.getGameRules().getValue(GameRules.ADVANCE_WEATHER) && overworld.isRaining()) {
            overworld.resetWeather();
        }
    }
}
