package net.sevenstars.middleearth.mixin;

import net.minecraft.world.chunk.ChunkGenerationStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChunkGenerationStep.class)
public class ChunkGenerationStepMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int ChunkGenerationStep(int blockStateWriteRadius) {
        if(blockStateWriteRadius < 0) return  blockStateWriteRadius - 1;
        else return blockStateWriteRadius + 1;
    }
}
