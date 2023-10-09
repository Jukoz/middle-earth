package net.jesteur.me.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.jesteur.me.world.biomes.MEBiomeKeys;
import net.jesteur.me.world.biomes.ModBiomeSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.PlayerManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    float currentFogStart = 0.0f;
    float currentFogEnd = 0.0f;

    @Inject(method = "render", at = @At("TAIL"))
    private static void applyFog(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();

        if (entity instanceof ClientPlayerEntity clientPlayerEntity) {
            RegistryEntry<Biome> biomeRegistry = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos());
            float divider = 255F;
            float red = 0.0f;
            float blue = 0.0f;
            float green = 0.0f;

            if(biomeRegistry.getKey().isPresent()){
                if (biomeRegistry.getKey().get().equals(MEBiomeKeys.MISTY_MOUNTAINS)) {
                    red = 183F / divider;
                    green =  204F / divider;
                    blue = 237F / divider;
                } else if(biomeRegistry.getKey().get().equals(MEBiomeKeys.DUNLAND_FOOTHILLS)){
                    red = 223F / divider;
                    green = 232F / divider;
                    blue =  245F / divider;
                } else if(biomeRegistry.getKey().get().equals(MEBiomeKeys.NORTHERN_DUNLAND)){
                    red = 223F / divider;
                    green = 245F / divider;
                    blue =  232F / divider;
                }
            }

            RenderSystem.clearColor(red, green, blue, 0.0F);
        }
    }

    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();

        if (entity instanceof ClientPlayerEntity clientPlayerEntity) {
            RegistryEntry<Biome> biomeRegistry = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos());
            boolean modifyFog = false;
            float fogStartGoal = 0.0f;
            float fogEndGoal = 0.0f;
            FogShape fogShape = FogShape.SPHERE;

            if(biomeRegistry.getKey().isPresent()){
                if (biomeRegistry.getKey().get().equals(MEBiomeKeys.MISTY_MOUNTAINS)) {
                    fogStartGoal = -8.0F;
                    fogEndGoal = 24F;
                    modifyFog = true;
                } else if(biomeRegistry.getKey().get().equals(MEBiomeKeys.DUNLAND_FOOTHILLS)){
                    fogStartGoal = -8.0F;
                    fogEndGoal = 40F;
                    modifyFog = true;
                } else if(biomeRegistry.getKey().get().equals(MEBiomeKeys.NORTHERN_DUNLAND)){
                    fogStartGoal = 0.0F;
                    fogEndGoal = 96F;
                    modifyFog = true;
                } else {
                    fogStartGoal = 0.0F;
                    fogEndGoal = 150F;
                    modifyFog = true;
                }
            }

            if (fogEndGoal > viewDistance) {
                fogEndGoal = viewDistance;
                fogShape = FogShape.CYLINDER;
            }
            if(modifyFog){
                RenderSystem.setShaderFogStart(fogStartGoal);
                RenderSystem.setShaderFogEnd(fogEndGoal);
                RenderSystem.setShaderFogShape(fogShape);
            }
        }
    }
}
