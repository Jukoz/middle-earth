package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntity.class)
public class PigEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "onStruckByLightning", cancellable = true)
    private void onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo callBackInfo) {
        if(ModDimensions.isInMiddleEarth(world)) {
            callBackInfo.cancel();
        }
    }
}
