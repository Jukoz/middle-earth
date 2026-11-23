package net.sevenstars.middleearth.light;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringIdentifiable;

/** The light a held or worn item gives: a glow around the holder or a beam ({@code "type"} in JSON). */
public sealed interface HeldLight permits HeldLight.Beam, HeldLight.Glow {
    Codec<Integer> RGB = Codec.either(Codec.INT, Codec.STRING.comapFlatMap(text -> {
            try {
                return DataResult.success(Integer.parseInt(text.startsWith("#") ? text.substring(1) : text, 16) & 0xFFFFFF);
            } catch (NumberFormatException e) {
                return DataResult.error(() -> "Not an RGB colour: " + text);
            }
        }, rgb -> String.format("#%06X", rgb & 0xFFFFFF)))
        .xmap(either -> either.map(rgb -> rgb, rgb -> rgb), Either::right);

    Codec<String> TYPE = Codec.STRING.validate(type -> type.equals("glow") || type.equals("beam")
        ? DataResult.success(type) : DataResult.error(() -> "Unknown held light type " + type + " (glow or beam)"));

    Codec<HeldLight> CODEC = TYPE.dispatch("type",
        light -> light instanceof Beam ? "beam" : "glow",
        type -> type.equals("beam") ? Beam.MAP_CODEC : Glow.MAP_CODEC);

    boolean toggled();

    Hold hold();

    enum Hold implements StringIdentifiable {
        GAZE("gaze"), RAISED("raised");

        public static final Codec<Hold> CODEC = StringIdentifiable.createCodec(Hold::values);
        private final String name;

        Hold(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }

    record Beam(int range, int spotLevel, int glowLevel, int color, boolean toggled) implements HeldLight {
        static final MapCodec<Beam> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.intRange(1, 128).fieldOf("range").forGetter(Beam::range),
            Codec.intRange(0, 15).fieldOf("spot_level").forGetter(Beam::spotLevel),
            Codec.intRange(0, 15).fieldOf("glow_level").forGetter(Beam::glowLevel),
            RGB.fieldOf("color").forGetter(Beam::color),
            Codec.BOOL.optionalFieldOf("toggled", true).forGetter(Beam::toggled)
        ).apply(instance, Beam::new));

        @Override
        public Hold hold() {
            return Hold.GAZE;
        }
    }

    record Glow(int level, boolean flame, boolean toggled, Hold hold) implements HeldLight {
        public static final int PLACED = -1;

        private static final Codec<Integer> LEVEL = Codec.either(Codec.intRange(0, 15), Codec.STRING.comapFlatMap(
                name -> name.equals("placed") ? DataResult.success(PLACED) : DataResult.error(() -> "Not a light level: " + name),
                level -> "placed"))
            .xmap(either -> either.map(level -> level, level -> level),
                level -> level == PLACED ? Either.right(level) : Either.left(level));

        static final MapCodec<Glow> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LEVEL.fieldOf("level").forGetter(Glow::level),
            Codec.BOOL.optionalFieldOf("flame", false).forGetter(Glow::flame),
            Codec.BOOL.optionalFieldOf("toggled", false).forGetter(Glow::toggled),
            Hold.CODEC.optionalFieldOf("hold", Hold.RAISED).forGetter(Glow::hold)
        ).apply(instance, Glow::new));

        public static Glow lantern(int level) {
            return new Glow(level, false, true, Hold.GAZE);
        }

        public static Glow lamp(int level) {
            return new Glow(level, false, true, Hold.RAISED);
        }

        public static Glow flame(int level) {
            return new Glow(level, true, false, Hold.RAISED);
        }

        public static Glow oilLamp(int level) {
            return new Glow(level, true, true, Hold.RAISED);
        }

        public Glow resolved(int placed) {
            return level == PLACED ? new Glow(placed, flame, toggled, hold) : this;
        }
    }
}
