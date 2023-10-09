package net.jesteur.me.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.world.biomes.MEBiomeFogData;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Environment(EnvType.CLIENT)
@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private static void applyFog(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();

        if (entity instanceof ClientPlayerEntity clientPlayerEntity) {
            Optional<RegistryKey<Biome>> biomeRegistry = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos()).getKey();
            if(biomeRegistry.isPresent() && MEBiomeFogData.DATA.containsKey(biomeRegistry.get())){
                MEBiomeFogData fogData = MEBiomeFogData.DATA.get(biomeRegistry.get());
                RenderSystem.setShaderFogColor(fogData.rgba.x, fogData.rgba.y, fogData.rgba.z, fogData.rgba.w);
            }
        }
    }

    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();

        if (entity instanceof ClientPlayerEntity clientPlayerEntity) {
            Optional<RegistryKey<Biome>> biomeRegistry = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos()).getKey();
            if(biomeRegistry.isPresent() && MEBiomeFogData.DATA.containsKey(biomeRegistry.get())){
                MEBiomeFogData fogData = MEBiomeFogData.DATA.get(biomeRegistry.get());
                RenderSystem.setShaderFogStart(fogData.fogStart);
                RenderSystem.setShaderFogEnd(fogData.fogEnd);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
            }
        }
    }
}
