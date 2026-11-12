package net.sevenstars.middleearth.world.biomes;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.sevenstars.middleearth.particles.ParticleTypeRegistryME;
import net.sevenstars.middleearth.world.biomes.surface.MapBiomeData;
import org.jetbrains.annotations.Nullable;

// The mood of a biome. Each biome picks one of the shared moods below where it is defined,
// and can change a single part of it for itself: MIRKWOOD.withFog(0.05f, 0.45f)
public final class BiomeAmbianceME {
    public static final BiomeAmbianceME CLEAR = sky(0x78A7FF, 0xC0D8FF);
    public static final BiomeAmbianceME HIGHLAND = sky(0x7DA2FF, 0xC0D8FF);
    public static final BiomeAmbianceME WATER = sky(0x7BA4FF, 0xC0D8FF);
    public static final BiomeAmbianceME SOUTHERN = sky(0x6EB1FF, 0xC4DFFF);
    // The Shire & the downs around it
    public static final BiomeAmbianceME GENTLE = sky(0x84ACF5, 0xC0D4F2);
    // Lindon, the Blue Mountains, Eregion & Rhun
    public static final BiomeAmbianceME LUMINOUS = sky(0x86B0FE, 0xC2DFFF);
    public static final BiomeAmbianceME DALE = sky(0x7D97FF, 0xB9D2FF);
    public static final BiomeAmbianceME FROZEN = sky(0x83A7FF, 0xA1BDE6);
    public static final BiomeAmbianceME DUNLAND = sky(0x7290E9, 0xC5D1C6);
    // Empty lands of Enedwaith & Rhudaur
    public static final BiomeAmbianceME SUBDUED = sky(0x7290E9, 0xA5C1EE);
    // Shaded vales & the eastern plains
    public static final BiomeAmbianceME SOFT = sky(0x7590E4, 0xBCCDEC);
    // Emyn Muil & old forests
    public static final BiomeAmbianceME DUSKY = sky(0x6A82C8, 0x9EB8E0);
    // Angmar, Nurn & the northern wilds
    public static final BiomeAmbianceME COLD_HAZE = sky(0x88A0DD, 0xB3C4DF);
    // Lands spoiled by Mordor
    public static final BiomeAmbianceME BLIGHTED = sky(0x86A0CA, 0xA8B7C2);
    // Marshes, troll country & the pools of the wastes under cloud
    public static final BiomeAmbianceME OVERCAST = sky(0x9CA8AB, 0x8D999B)
            .withParticles(Particles.MIST);
    public static final BiomeAmbianceME SNOWSTORM = sky(0xB4C0CC, 0xC6D0D8).withFog(0.0f, 0.35f)
            .withParticles(Particles.SNOW_HEAVY);
    public static final BiomeAmbianceME GREY = sky(0xB9B9B9, 0x817770);
    public static final BiomeAmbianceME GUNDABAD = sky(0xACA9B1, 0xA5A5B1);
    public static final BiomeAmbianceME DEAD_MARSHES = sky(0xB5B5B3, 0x6C767A).withFog(0.0f, 0.45f)
            .withParticles(Particles.MIST_THICK)
            .withSounds(Sounds.HAUNTED);
    public static final BiomeAmbianceME LORIEN_GOLD = sky(0x78A7FF, 0xD1CA75);
    public static final BiomeAmbianceME LORIEN_EDGE = sky(0x78A7FF, 0xAECA81);
    public static final BiomeAmbianceME WOODLAND_REALM = sky(0x86AEFE, 0xBACEC5);
    public static final BiomeAmbianceME WOODLAND_AUTUMN = sky(0x86AEFE, 0x7E8B80);
    public static final BiomeAmbianceME MIRKWOOD_EDGE = sky(0x73A4B0, 0x78827C).withFog(0.15f, 0.8f)
            .withParticles(Particles.MOTES_SPARSE);
    public static final BiomeAmbianceME MIRKWOOD = sky(0x6883A6, 0x677468);
    public static final BiomeAmbianceME DARK_MIRKWOOD = sky(0x50698C, 0x49584B);
    public static final BiomeAmbianceME MIRKWOOD_MARSH = sky(0x6A87A0, 0x869CA2)
            .withParticles(Particles.MIST);
    public static final BiomeAmbianceME DOL_GULDUR = sky(0x383A70, 0x4D4955)
            .withParticles(Particles.CINDERS)
            .withSounds(Sounds.HAUNTED);
    public static final BiomeAmbianceME DAGORLAD = sky(0x6E76A0, 0x747581)
            .withParticles(Particles.DUST);
    // Brown Lands & the wastes before Mordor
    public static final BiomeAmbianceME WASTELAND = sky(0x535050, 0x4C4848);
    public static final BiomeAmbianceME MORGUL = sky(0x445A52, 0x38513D).withFog(0.0f, 0.5f)
            .withParticles(Particles.MIST_THICK)
            .withSounds(Sounds.HAUNTED);
    // Plains of Mordor
    public static final BiomeAmbianceME ASH = sky(0x292023, 0x1D151B)
            .withParticles(Particles.ASHFALL_LIGHT)
            .withSounds(Sounds.MORDOR);
    // Mountains of Mordor & Udun
    public static final BiomeAmbianceME ASH_MOUNTAINS = sky(0x3F3636, 0x2F2727)
            .withParticles(Particles.CINDERS)
            .withSounds(Sounds.MORDOR);

    private final int skyColor;
    private final int fogColor;
    @Nullable
    private final Fog fog;
    @Nullable
    private final Particles particles;
    @Nullable
    private final Sounds sounds;

    private BiomeAmbianceME(int skyColor, int fogColor, @Nullable Fog fog, @Nullable Particles particles, @Nullable Sounds sounds) {
        this.skyColor = requireColor("sky", skyColor);
        this.fogColor = requireColor("fog", fogColor);
        this.fog = fog;
        this.particles = particles;
        this.sounds = sounds;
    }

    public static BiomeAmbianceME sky(int skyColor, int fogColor) {
        return new BiomeAmbianceME(skyColor, fogColor, null, null, null);
    }

    public BiomeAmbianceME withSky(int skyColor, int fogColor) {
        return new BiomeAmbianceME(skyColor, fogColor, fog, particles, sounds);
    }

    // Both values are fractions of the view distance. A negative start thickens the fog at the camera
    public BiomeAmbianceME withFog(float start, float end) {
        return new BiomeAmbianceME(skyColor, fogColor, new Fog(start, end), particles, sounds);
    }

    public BiomeAmbianceME withoutFog() {
        return new BiomeAmbianceME(skyColor, fogColor, null, particles, sounds);
    }

    public BiomeAmbianceME withParticles(Particles particles) {
        return new BiomeAmbianceME(skyColor, fogColor, fog, particles, sounds);
    }

    public BiomeAmbianceME withoutParticles() {
        return new BiomeAmbianceME(skyColor, fogColor, fog, null, sounds);
    }

    public BiomeAmbianceME withSounds(Sounds sounds) {
        return new BiomeAmbianceME(skyColor, fogColor, fog, particles, sounds);
    }

    public BiomeAmbianceME withoutSounds() {
        return new BiomeAmbianceME(skyColor, fogColor, fog, particles, null);
    }

    public int getSkyColor() {
        return skyColor;
    }

    public int getFogColor() {
        return fogColor;
    }

    @Nullable
    public Fog getFog() {
        return fog;
    }

    @Nullable
    public Particles getParticles() {
        return particles;
    }

    @Nullable
    public Sounds getSounds() {
        return sounds;
    }

    // The fog distance is not part of the biome file, the fog mixin reads it through of()
    public void applyTo(BiomeEffects.Builder effects) {
        effects.skyColor(skyColor).fogColor(fogColor);
        if(particles != null)
            effects.particleConfig(new BiomeParticleConfig(particles.particle(), particles.probability()));
        if(sounds == null) {
            effects.moodSound(BiomeMoodSound.CAVE);
        } else {
            effects.loopSound(sounds.loop());
            effects.moodSound(new BiomeMoodSound(sounds.mood(), Sounds.MOOD_TICK_DELAY, Sounds.MOOD_BLOCK_SEARCH_EXTENT, Sounds.MOOD_OFFSET));
            effects.additionsSound(new BiomeAdditionsSound(sounds.additions(), Sounds.ADDITIONS_TICK_CHANCE));
        }
    }

    // Null for caves & biomes of other dimensions
    @Nullable
    public static BiomeAmbianceME of(RegistryKey<Biome> biome) {
        return MapBiomeData.findAmbiance(biome);
    }

    private static int requireColor(String name, int color) {
        if(color < 0 || color > 0xFFFFFF)
            throw new IllegalArgumentException("The %s colour 0x%X is not a RGB colour".formatted(name, color));
        return color;
    }

    public record Fog(float start, float end) {
        public Fog {
            if(!(end > 0 && end <= 1))
                throw new IllegalArgumentException("The fog end %s must be above 0 and at most 1".formatted(end));
            if(!(start < end && start >= -1))
                throw new IllegalArgumentException("The fog start %s must be at least -1 and below the end %s".formatted(start, end));
        }
    }

    // The probability is the chance for each air block around the player to spawn a particle, every tick
    public record Particles(ParticleEffect particle, float probability) {
        public static final Particles ASHFALL_HEAVY = new Particles(ParticleTypes.WHITE_ASH, 0.1f);
        public static final Particles ASHFALL = new Particles(ParticleTypes.WHITE_ASH, 0.045f);
        public static final Particles ASHFALL_LIGHT = new Particles(ParticleTypes.WHITE_ASH, 0.02f);
        public static final Particles CINDERS = new Particles(ParticleTypes.ASH, 0.012f);
        public static final Particles DUST = new Particles(ParticleTypes.ASH, 0.005f);

        public static final Particles MIST_THICK = new Particles(ParticleTypeRegistryME.BIOME_FOG_PARTICLE, 0.004f);
        public static final Particles MIST = new Particles(ParticleTypeRegistryME.BIOME_FOG_PARTICLE, 0.003f);

        public static final Particles MOTES_THICK = new Particles(ParticleTypes.MYCELIUM, 0.02f);
        public static final Particles MOTES = new Particles(ParticleTypes.MYCELIUM, 0.012f);
        public static final Particles MOTES_SPARSE = new Particles(ParticleTypes.MYCELIUM, 0.006f);
        public static final Particles STUFFY_AIR = new Particles(ParticleTypes.SPORE_BLOSSOM_AIR, 0.006f);

        public static final Particles SNOW_HEAVY = new Particles(ParticleTypes.SNOWFLAKE, 0.03f);
        public static final Particles SNOW = new Particles(ParticleTypes.SNOWFLAKE, 0.015f);

        public Particles {
            if(!(probability > 0 && probability <= 1))
                throw new IllegalArgumentException("The particle probability %s must be above 0 and at most 1".formatted(probability));
        }
    }

    public record Sounds(RegistryEntry<SoundEvent> loop, RegistryEntry<SoundEvent> mood, RegistryEntry<SoundEvent> additions) {
        private static final int MOOD_TICK_DELAY = 6000;
        private static final int MOOD_BLOCK_SEARCH_EXTENT = 8;
        private static final double MOOD_OFFSET = 2.0;
        private static final double ADDITIONS_TICK_CHANCE = 0.0111;

        public static final Sounds MOUNT_DOOM = new Sounds(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP, SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS);
        public static final Sounds MORDOR = new Sounds(SoundEvents.AMBIENT_NETHER_WASTES_LOOP, SoundEvents.AMBIENT_NETHER_WASTES_MOOD, SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS);
        public static final Sounds HAUNTED = new Sounds(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS);
        public static final Sounds MIRKWOOD = new Sounds(SoundEvents.AMBIENT_WARPED_FOREST_LOOP, SoundEvents.AMBIENT_WARPED_FOREST_MOOD, SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS);
        public static final Sounds SPIDER_NESTS = new Sounds(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP, SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS);
    }
}
