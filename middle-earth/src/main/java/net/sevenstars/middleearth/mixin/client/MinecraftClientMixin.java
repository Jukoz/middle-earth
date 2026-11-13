package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.sevenstars.middleearth.config.ClientConfigME;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final
    public GameOptions options;

    @Inject(method = "setWorld", at = @At("HEAD"))
    private void resetGlintInNether(ClientWorld world, CallbackInfo ci) {
        if (ClientConfigME.DISABLE_GLINT && world != null && DimensionRegistryME.isInMiddleEarth(world)) {
            this.options.getGlintStrength().setValue(0.0);
        }
    }
}
