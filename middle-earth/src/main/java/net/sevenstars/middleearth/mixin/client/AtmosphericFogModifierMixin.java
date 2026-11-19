package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.AtmosphericFogModifier;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.features.generation.biomes.BiomeAmbianceME;
import net.sevenstars.middleearth.features.generation.dimension.DimensionRegistryME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Environment(EnvType.CLIENT)
@Mixin(AtmosphericFogModifier.class)
public class AtmosphericFogModifierMixin {
    // Change of the fog multipliers per tick, so the fog shifts gradually between two foggy biomes
    @Unique
    private static final float TRANSITION_SPEED = 0.004f;
    // Change of the blend per tick, so the biome fog fades in and out of the vanilla fog
    @Unique
    private static final float BLEND_SPEED = 0.005f;
    @Unique
    private static float fogStartMultiplier = 1;
    @Unique
    private static float fogEndMultiplier = 1;
    // 0 is the vanilla fog only, 1 is the biome fog only
    @Unique
    private static float blend = 0;
    // Change of the underground fog per tick, a couple of seconds from the surface to the dark
    @Unique
    private static final float UNDERGROUND_BLEND_SPEED = 0.02f;
    @Unique
    private static float undergroundBlend = 0;

    @Inject(method = "applyStartEndModifier", at = @At("TAIL"))
    private void applyBiomeFog(FogData data, Camera camera, ClientWorld world, float viewDistance, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(!DimensionRegistryME.isInMiddleEarth(world)) {
            blend = 0;
            undergroundBlend = 0;
            return;
        }

        float deltaTicks = tickCounter.getDynamicDeltaTicks();
        Optional<RegistryKey<Biome>> biome = world.getBiome(camera.getBlockPos()).getKey();
        undergroundBlend = approach(undergroundBlend, biome.isPresent() && BiomeAmbianceME.hasUndergroundFog(biome.get()) ? 1 : 0, deltaTicks * UNDERGROUND_BLEND_SPEED);
        if(undergroundBlend > 0) {
            float undergroundEnd = Math.min(BiomeAmbianceME.UNDERGROUND_FOG_END, viewDistance);
            float vanillaEnd = Math.min(data.environmentalEnd, viewDistance);
            data.environmentalStart = MathHelper.lerp(undergroundBlend, Math.min(data.environmentalStart, vanillaEnd), Math.min(BiomeAmbianceME.UNDERGROUND_FOG_START, undergroundEnd * 0.5f));
            data.environmentalEnd = MathHelper.lerp(undergroundBlend, vanillaEnd, undergroundEnd);
        }
        BiomeAmbianceME ambiance = biome.map(BiomeAmbianceME::of).orElse(null);
        BiomeAmbianceME.Fog biomeFog = ambiance == null ? null : ambiance.getFog();
        // Rain/Snow thickens the fog
        float wetness = world.getRainGradient(tickCounter.getTickProgress(false));
        float targetStart = biomeFog == null ? 0 : MathHelper.lerp(wetness, biomeFog.clearStart(), biomeFog.start());
        float targetEnd = biomeFog == null ? 0 : MathHelper.lerp(wetness, biomeFog.clearEnd(), biomeFog.end());
        if(biomeFog == null) {
            blend = approach(blend, 0, deltaTicks * BLEND_SPEED);
        } else {
            if(blend <= 0) {
                fogStartMultiplier = targetStart;
                fogEndMultiplier = targetEnd;
            } else {
                fogStartMultiplier = approach(fogStartMultiplier, targetStart, deltaTicks * TRANSITION_SPEED);
                fogEndMultiplier = approach(fogEndMultiplier, targetEnd, deltaTicks * TRANSITION_SPEED);
            }
            blend = approach(blend, 1, deltaTicks * BLEND_SPEED);
        }
        if(blend <= 0)
            return;

        float renderDistanceFogLength = MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
        float biomeFogStart = (viewDistance - renderDistanceFogLength) * fogStartMultiplier;
        float biomeFogEnd = viewDistance * fogEndMultiplier;
        // Rain may already bring the vanilla fog closer than the biome does
        if(biomeFogEnd >= data.environmentalEnd)
            return;
        // Vanilla fog ends at 1024 blocks, past the view distance
        float vanillaFogEnd = Math.min(data.environmentalEnd, viewDistance);
        data.environmentalStart = MathHelper.lerp(blend, Math.min(data.environmentalStart, vanillaFogEnd), biomeFogStart);
        data.environmentalEnd = MathHelper.lerp(blend, vanillaFogEnd, biomeFogEnd);
    }

    // Vanilla leans the fog colour towards the sky and the sunrise by where the camera looks, which means nothing underground:
    // there the colour is the biome's own, eased in and out with the underground fog. The renderer takes its clear colour from here too.
    @Inject(method = "getFogColor", at = @At("RETURN"), cancellable = true)
    private void undergroundFogColor(ClientWorld world, Camera camera, int viewDistance, float tickProgress, CallbackInfoReturnable<Integer> cir) {
        if(undergroundBlend <= 0)
            return;
        int biomeColor = camera.getEnvironmentAttributeInterpolator().get(EnvironmentAttributes.FOG_COLOR_VISUAL, tickProgress);
        cir.setReturnValue(ColorHelper.lerp(undergroundBlend, cir.getReturnValueI(), biomeColor));
    }

    @Unique
    private static float approach(float value, float target, float step) {
        if(value < target)
            return Math.min(value + step, target);
        return Math.max(value - step, target);
    }
}
