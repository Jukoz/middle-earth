package net.sevenstars.middleearth.mixin;

import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin {

    @Inject(method = "trySpawnEntity", at = @At("HEAD"), cancellable = true)
    private void abortGolemSpawn(World world, BlockPos pos, CallbackInfo ci) {
        if (ModDimensions.isInMiddleEarth(world)) {
            ci.cancel();
        }
    }
}