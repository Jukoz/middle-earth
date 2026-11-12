package net.sevenstars.middleearth.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleTypes;

/** The glint of a hidden gem: the small spark particle, tinted the gem's colour. Client only. */
@Environment(EnvType.CLIENT)
public final class GemSparks {
    private GemSparks() {
    }

    public static void spark(double x, double y, double z, int rgb) {
        Particle particle = MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, 0, 0, 0);
        if (particle != null) {
            particle.setColor(((rgb >> 16) & 0xFF) / 255F, ((rgb >> 8) & 0xFF) / 255F, (rgb & 0xFF) / 255F);
        }
    }
}
