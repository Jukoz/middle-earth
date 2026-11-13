package net.sevenstars.middleearth.light;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringIdentifiable;

/**
 * The light an item gives off while held in the off hand (or worn), as a {@code held_light} entry gives it
 * ({@link HeldLights}). In JSON: {@code "type": "glow"} or {@code "type": "beam"}, with the fields below.
 */
public sealed interface HeldLight permits HeldLight.Beam, HeldLight.Glow {
    /** An RGB colour: {@code "#RRGGBB"} or a plain number. */
    Codec<Integer> RGB = Codec.either(Codec.INT, Codec.STRING.comapFlatMap(text -> {
            try {
                return DataResult.success(Integer.parseInt(text.startsWith("#") ? text.substring(1) : text, 16) & 0xFFFFFF);
            } catch (NumberFormatException e) {
                return DataResult.error(() -> "Not an RGB colour: " + text);
            }
        }, rgb -> String.format("#%06X", rgb & 0xFFFFFF)))
        .xmap(either -> either.map(rgb -> rgb, rgb -> rgb), Either::right);

    /** The light's {@code "type"}. */
    Codec<String> TYPE = Codec.STRING.validate(type -> type.equals("glow") || type.equals("beam")
        ? DataResult.success(type) : DataResult.error(() -> "Unknown held light type " + type + " (glow or beam)"));

    /** Glow or beam, by its {@code "type"}. */
    Codec<HeldLight> CODEC = TYPE.dispatch("type",
        light -> light instanceof Beam ? "beam" : "glow",
        type -> type.equals("beam") ? Beam.MAP_CODEC : Glow.MAP_CODEC);

    /** Whether it can be shaded and lit again in the off hand ({@link HeldLights#isOn}). */
    boolean toggled();

    /** How it is held up (third person). */
    Hold hold();

    /** How a held light is carried: out along the gaze, hanging from the fist; or held up before the holder, a little below level. */
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

    /**
     * A focused lamp: lights the spot its beam lands on, up to {@code range} blocks away, and glows a little at
     * the holder. {@code color} is the beam's RGB.
     */
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

    /**
     * Light all round the holder, at {@code level}: in JSON a number, or {@code "placed"} for the brightness its
     * block gives when placed ({@link #PLACED} until resolved, see {@link HeldLights}).
     *
     * @param flame   a naked flame (torch, candle, oil lamp): its light flickers a little below {@code level}
     * @param toggled it can be shaded and lit again in the off hand (lanterns, lamps)
     * @param hold    how it is held up
     */
    record Glow(int level, boolean flame, boolean toggled, Hold hold) implements HeldLight {
        /** The level of a glow as bright as its item's block placed: resolved when the lights are loaded. */
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

        /** A lantern: steady, can be shaded, carried out along the gaze by its handle. */
        public static Glow lantern(int level) {
            return new Glow(level, false, true, Hold.GAZE);
        }

        /** A lamp without a handle: steady, can be shaded, held up by its side. */
        public static Glow lamp(int level) {
            return new Glow(level, false, true, Hold.RAISED);
        }

        /** A torch or candle: flickers, always lit, held up. */
        public static Glow flame(int level) {
            return new Glow(level, true, false, Hold.RAISED);
        }

        /** A lamp with a flame in it (an oil lamp): flickers, can be shaded, held up by its handle. */
        public static Glow oilLamp(int level) {
            return new Glow(level, true, true, Hold.RAISED);
        }

        /** This glow at {@code placed} where it was to be as bright as its block placed. */
        public Glow resolved(int placed) {
            return level == PLACED ? new Glow(placed, flame, toggled, hold) : this;
        }
    }
}
