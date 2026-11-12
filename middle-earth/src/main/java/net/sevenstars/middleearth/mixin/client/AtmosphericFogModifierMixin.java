package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.AtmosphericFogModifier;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.world.biomes.BiomeAmbianceME;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "applyStartEndModifier", at = @At("TAIL"))
    private void applyBiomeFog(FogData data, Entity cameraEntity, BlockPos cameraPos, ClientWorld world, float viewDistance, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(!DimensionRegistryME.isInMiddleEarth(world)) {
            blend = 0;
            return;
        }

        float deltaTicks = tickCounter.getDynamicDeltaTicks();
        Optional<RegistryKey<Biome>> biome = world.getBiome(cameraPos).getKey();
        BiomeAmbianceME ambiance = biome.map(BiomeAmbianceME::of).orElse(null);
        BiomeAmbianceME.Fog biomeFog = ambiance == null ? null : ambiance.getFog();
        if(biomeFog == null) {
            blend = approach(blend, 0, deltaTicks * BLEND_SPEED);
        } else {
            if(blend <= 0) {
                fogStartMultiplier = biomeFog.start();
                fogEndMultiplier = biomeFog.end();
            } else {
                fogStartMultiplier = approach(fogStartMultiplier, biomeFog.start(), deltaTicks * TRANSITION_SPEED);
                fogEndMultiplier = approach(fogEndMultiplier, biomeFog.end(), deltaTicks * TRANSITION_SPEED);
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
        data.environmentalStart = MathHelper.lerp(blend, data.environmentalStart, biomeFogStart);
        data.environmentalEnd = MathHelper.lerp(blend, data.environmentalEnd, biomeFogEnd);
    }

    @Unique
    private static float approach(float value, float target, float step) {
        if(value < target)
            return Math.min(value + step, target);
        return Math.max(value - step, target);
    }
}
