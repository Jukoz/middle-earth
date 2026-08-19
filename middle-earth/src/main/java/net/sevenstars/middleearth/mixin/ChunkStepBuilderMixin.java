package net.sevenstars.middleearth.mixin;

import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkStep.Builder.class)
public abstract class ChunkStepBuilderMixin {
    private static final int MIDDLE_EARTH_FEATURE_WRITE_RADIUS = 2;

    @Shadow
    @Final
    private ChunkStatus status;

    @Shadow
    private int blockStateWriteRadius;

    @Inject(method = "build", at = @At("HEAD"))
    private void middleEarth$restoreAuthoredFeatureWriteContract(
            CallbackInfoReturnable<ChunkStep> callback
    ) {
        if (this.status != ChunkStatus.FEATURES || this.blockStateWriteRadius != 1) {
            return;
        }

        ((ChunkStep.Builder) (Object) this)
                .addRequirement(ChunkStatus.CARVERS, MIDDLE_EARTH_FEATURE_WRITE_RADIUS)
                .blockStateWriteRadius(MIDDLE_EARTH_FEATURE_WRITE_RADIUS);
    }
}
