package net.sevenstars.middleearth.mixin;

import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldGenRegion.class)
public abstract class WorldGenRegionMixin {
    private static final int MIDDLE_EARTH_FEATURE_WRITE_RADIUS = 2;

    @Shadow
    @Final
    @Mutable
    private ChunkStep generatingStep;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void middleEarth$allowAuthoredFeatureFootprints(
            ServerLevel level,
            StaticCache2D<GenerationChunkHolder> cache,
            ChunkStep step,
            ChunkAccess center,
            CallbackInfo ci
    ) {
        if (!level.dimension().equals(ModDimensions.ME_WORLD_KEY)
                || step.targetStatus() != ChunkStatus.FEATURES
                || step.blockStateWriteRadius() >= MIDDLE_EARTH_FEATURE_WRITE_RADIUS) {
            return;
        }

        this.generatingStep = new ChunkStep(
                step.targetStatus(),
                step.directDependencies(),
                step.accumulatedDependencies(),
                MIDDLE_EARTH_FEATURE_WRITE_RADIUS,
                step.task()
        );
    }
}
