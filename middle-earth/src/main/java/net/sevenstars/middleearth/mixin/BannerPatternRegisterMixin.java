package net.sevenstars.middleearth.mixin;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerPatterns.class)
public class BannerPatternRegisterMixin {

    @Inject(
            method = "bootstrap(Lnet/minecraft/data/worldgen/BootstrapContext;)V",
            at = @At(
                    value = "TAIL",
                    shift = At.Shift.BEFORE
            )
    )

    private static void registerModBannerPatterns(BootstrapContext<BannerPattern> registry, CallbackInfo ci) {
        BannerPatternsME.register(registry); // TODO fixme
    }
}
