package net.sevenstars.middleearth.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.features.generation.biomes.BiomeAmbianceME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    // Rolled for each biome particle at a random block near the player
    @ModifyExpressionValue(method = "randomBlockDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/AmbientParticle;shouldAddParticle(Lnet/minecraft/util/math/random/Random;)Z"))
    private boolean keepParticlesUnderCover(boolean shouldAdd, @Local(argsOnly = true) BlockPos.Mutable pos) {
        if(!shouldAdd)
            return false;
        ClientWorld world = (ClientWorld) (Object) this;
        BiomeAmbianceME ambiance = world.getBiome(pos).getKey().map(BiomeAmbianceME::of).orElse(null);
        if(ambiance == null || ambiance.getParticles() == null || !ambiance.getParticles().underCover())
            return true;
        return pos.getY() < world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ());
    }
}
