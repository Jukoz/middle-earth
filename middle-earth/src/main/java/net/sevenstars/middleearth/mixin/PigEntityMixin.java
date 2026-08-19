package net.sevenstars.middleearth.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Pig;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Pig.class)
public class PigEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "thunderHit(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LightningBolt;)V", cancellable = true)
    private void onStruckByLightning(ServerLevel world, LightningBolt lightning, CallbackInfo callBackInfo) {
        if(ModDimensions.isInMiddleEarth(world)) {
            callBackInfo.cancel();
        }
    }
}
