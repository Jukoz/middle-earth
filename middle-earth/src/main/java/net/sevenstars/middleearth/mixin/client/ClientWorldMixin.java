package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.sevenstars.middleearth.world.biomes.BiomeAmbianceME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    // The lambda of randomBlockDisplayTick which spawns the biome particle at a random air block around the player
    @Inject(method = "method_24462", at = @At("HEAD"), cancellable = true)
    private void keepParticlesUnderCover(BlockPos.Mutable pos, BiomeParticleConfig config, CallbackInfo ci) {
        ClientWorld world = (ClientWorld) (Object) this;
        BiomeAmbianceME ambiance = world.getBiome(pos).getKey().map(BiomeAmbianceME::of).orElse(null);
        if(ambiance == null || ambiance.getParticles() == null || !ambiance.getParticles().underCover())
            return;
        if(pos.getY() >= world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()))
            ci.cancel();
    }
}
