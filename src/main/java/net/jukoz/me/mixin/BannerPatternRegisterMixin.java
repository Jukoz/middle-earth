package net.jukoz.me.mixin;

import net.jukoz.me.item.utils.ModBannerPatterns;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BannerPatterns.class)
public class BannerPatternRegisterMixin {

    @Inject(
            method = "registerAndGetDefault",
            at = @At(
                    value = "TAIL",
                    shift = At.Shift.BEFORE
            )
    )

    private static void registerModBannerPatterns(
            Registry<BannerPattern> registry,
            CallbackInfoReturnable<BannerPattern> cir
    ) {
        ModBannerPatterns.register(registry);
    }
}
