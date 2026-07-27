package net.sevenstars.middleearth.particles;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.particles.custom.AnvilBonkParticle;
import net.sevenstars.middleearth.particles.custom.BiomeFogParticle;
import net.sevenstars.middleearth.particles.custom.FireflyParticle;
import net.sevenstars.middleearth.particles.custom.TintedLeavesParticle;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID, value = Dist.CLIENT)
public final class ParticleClientEvents {
    private ParticleClientEvents() {
    }

    @SubscribeEvent
    private static void registerProviders(RegisterParticleProvidersEvent event) {
        LeafParticleColorResolver.install(
                (state, level, pos) -> Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0)
        );
        event.registerSpriteSet(ModParticleTypes.ANVIL_SPARK_PARTICLE, AnvilBonkParticle.Factory::new);
        event.registerSpriteSet(ModParticleTypes.BIOME_FOG_PARTICLE, BiomeFogParticle.Factory::new);
        event.registerSpriteSet(ModParticleTypes.FIREFLY_PARTICLE, FireflyParticle.Factory::new);
        event.registerSpriteSet(ModParticleTypes.TINTED_LEAVES_PARTICLE, TintedLeavesParticle.Factory::new);
    }
}
