package net.jukoz.me.mixin;

import net.minecraft.world.ChunkRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChunkRegion.class)
public class ChunkRegionMixin {

    /*@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static ChunkRegion ChunkRegion(ChunkRegion value) {
        if(placementRadius > 0) placementRadius++;
        else if (placementRadius < 0) placementRadius--;
        return placementRadius;
    }*/
}
