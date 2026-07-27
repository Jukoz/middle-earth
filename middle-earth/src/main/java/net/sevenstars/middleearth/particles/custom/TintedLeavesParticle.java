package net.sevenstars.middleearth.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;

public final class TintedLeavesParticle extends TextureSheetParticle {
    private static final int LIFETIME = 300;
    private static final float ACCELERATION_SCALE = 0.0025F;

    private float angularVelocity;
    private final float angularAcceleration;
    private final float windStrength;
    private final double windX;
    private final double windZ;
    private final double swirlPhase;

    private TintedLeavesParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSprite(sprites.get(this.random.nextInt(12), 12));
        this.angularVelocity = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        float direction = this.random.nextFloat();
        this.angularAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.windStrength = 10.0F;
        this.lifetime = LIFETIME;
        this.gravity = 0.07F * 1.2F * ACCELERATION_SCALE;
        float size = 2.0F * (this.random.nextBoolean() ? 0.05F : 0.075F);
        this.quadSize = size;
        this.setSize(size, size);
        this.friction = 1.0F;
        this.yd = -0.021F;
        this.windX = Math.cos(Math.toRadians(direction * 60.0F)) * this.windStrength;
        this.windZ = Math.sin(Math.toRadians(direction * 60.0F)) * this.windStrength;
        this.swirlPhase = Math.toRadians(1000.0F + direction * 3000.0F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }
        if (this.removed) {
            return;
        }

        float elapsed = LIFETIME - this.lifetime;
        float progress = Math.min(elapsed / LIFETIME, 1.0F);
        double curve = progress * progress;
        double accelerationX = curve * Math.cos(curve * this.swirlPhase) * this.windStrength;
        double accelerationZ = curve * Math.sin(curve * this.swirlPhase) * this.windStrength;
        this.xd += accelerationX * ACCELERATION_SCALE;
        this.zd += accelerationZ * ACCELERATION_SCALE;
        this.yd -= this.gravity;
        this.angularVelocity += this.angularAcceleration / 20.0F;
        this.oRoll = this.roll;
        this.roll += this.angularVelocity / 20.0F;
        this.move(this.xd, this.yd, this.zd);
        if (this.onGround || this.lifetime < LIFETIME - 1 && (this.xd == 0.0 || this.zd == 0.0)) {
            this.remove();
        }
        if (!this.removed) {
            this.xd *= this.friction;
            this.yd *= this.friction;
            this.zd *= this.friction;
        }
    }

    public static final class Factory implements ParticleProvider<ColorParticleOption> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                ColorParticleOption option,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            TintedLeavesParticle particle = new TintedLeavesParticle(level, x, y, z, this.sprites);
            particle.setColor(option.getRed(), option.getGreen(), option.getBlue());
            return particle;
        }
    }
}
