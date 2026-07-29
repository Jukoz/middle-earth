package net.sevenstars.middleearth.mixin;

import net.minecraft.world.level.chunk.ChunkGenerator;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {
    private static final int MIDDLE_EARTH_BIOME_FEATURE_RADIUS = 4;

    @ModifyArg(
            method = "applyBiomeDecoration",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/ChunkPos;rangeClosed(Lnet/minecraft/world/level/ChunkPos;I)Ljava/util/stream/Stream;"
            ),
            index = 1
    )
    private int middleEarth$restoreAuthoredBiomeFeatureRadius(int radius) {
        return (Object) this instanceof MiddleEarthChunkGenerator
                ? MIDDLE_EARTH_BIOME_FEATURE_RADIUS
                : radius;
    }
}
