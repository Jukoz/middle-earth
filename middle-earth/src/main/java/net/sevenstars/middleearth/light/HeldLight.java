package net.sevenstars.middleearth.light;

/**
 * The light an item gives off while held in the off hand. Register items in {@link HeldLights}.
 */
public sealed interface HeldLight permits HeldLight.Beam, HeldLight.Glow {
    /** Whether it can be shaded and lit again in the off hand ({@link HeldLights#isOn}). */
    boolean toggled();

    /** How it is held up (third person). */
    Hold hold();

    /** How a held light is carried: out along the gaze, hanging from the fist; or held up before the holder, a little below level. */
    enum Hold {
        GAZE, RAISED
    }

    /**
     * A focused lamp: lights the spot its beam lands on, up to {@code range} blocks away, and glows a little at
     * the holder. {@code color} is the beam's RGB.
     */
    record Beam(int range, int spotLevel, int glowLevel, int color, boolean toggled) implements HeldLight {
        @Override
        public Hold hold() {
            return Hold.GAZE;
        }
    }

    /**
     * Light all round the holder, at {@code level} (as the item gives when placed).
     *
     * @param flame   a naked flame (torch, candle, oil lamp): its light flickers a little below {@code level}
     * @param toggled it can be shaded and lit again in the off hand (lanterns, lamps)
     * @param hold    how it is held up
     */
    record Glow(int level, boolean flame, boolean toggled, Hold hold) implements HeldLight {
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
    }
}
