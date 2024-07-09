package net.jukoz.me.mixin;

import net.jukoz.me.item.utils.ModBannerPatterns;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.Registerable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerPatterns.class)
public class BannerPatternRegisterMixin {

    @Inject(
            method = "bootstrap",
            at = @At(
                    value = "TAIL",
                    shift = At.Shift.BEFORE
            )
    )

    private static void registerModBannerPatterns(Registerable<BannerPattern> registry, CallbackInfo ci) {
        ModBannerPatterns.register(registry); // TODO fixme
    }
}