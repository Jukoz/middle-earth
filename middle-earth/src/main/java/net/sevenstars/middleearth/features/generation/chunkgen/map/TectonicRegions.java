package net.sevenstars.middleearth.features.generation.chunkgen.map;

// Tectonic's region landforms (Apollounknowndev, MIT), generated from its density function files under
// worldgen/density_function/region, terrain_spline/offset/depth_additive and region/diamond/dune; values are Tectonic's
// offsets, 128 blocks per unit. Regenerate rather than edit by hand
final class TectonicRegions {
    private TectonicRegions() {
    }

    interface Fn {
        double get(In in);
    }

    private static Fn c(float value) {
        return in -> value;
    }

    // what the splines read; the noises are filled in by TerrainRegions, the rest set per landform
    static final class In {
        double r;
        double rf;
        double t;
        double v;
        double sel;
        double e;
        double ef;
        double rc;
        double lowerTier;
        double upperTier;
        double badlands;
        double hills;
        double pillar;
        double wetland;
        double duneNoise;
        double offsetX;
        double offsetZ;
        int x;
        int z;
        long seed;

        // dune_base at xz_scale 1, moved by a shift in noise units
        double duneBase(double shiftX, double shiftZ) {
            return TectonicNoise.DUNE_BASE.sample(x + shiftX, z + shiftZ, seed);
        }
    }

    // vanilla's cubic spline: hermite between points, a straight line past the ends
    static final class Spline implements Fn {
        private final Fn coordinate;
        private final float[] locations;
        private final float[] derivatives;
        private final Fn[] values;

        Spline(Fn coordinate, float[] locations, float[] derivatives, Fn... values) {
            this.coordinate = coordinate;
            this.locations = locations;
            this.derivatives = derivatives;
            this.values = values;
        }

        @Override
        public double get(In in) {
            float x = (float) coordinate.get(in);
            int last = locations.length - 1;
            int i = -1;
            while (i < last && locations[i + 1] <= x) i++;
            if (i < 0) return values[0].get(in) + derivatives[0] * (x - locations[0]);
            if (i == last) return values[last].get(in) + derivatives[last] * (x - locations[last]);
            float x0 = locations[i];
            float x1 = locations[i + 1];
            float t = (x - x0) / (x1 - x0);
            float y0 = (float) values[i].get(in);
            float y1 = (float) values[i + 1].get(in);
            float p = derivatives[i] * (x1 - x0) - (y1 - y0);
            float q = -derivatives[i + 1] * (x1 - x0) + (y1 - y0);
            return lerp(t, y0, y1) + t * (1 - t) * lerp(t, p, q);
        }

        private static float lerp(float t, float a, float b) {
            return a + t * (b - a);
        }
    }

    private static final Spline S0 = new Spline(in -> in.lowerTier,
            new float[]{0.0f, 0.15f},
            new float[]{0.0f, 0.0f},
            c(0.1f), c(0.22f));
    private static final Spline S1 = new Spline(in -> in.lowerTier,
            new float[]{0.0f, 0.15f},
            new float[]{0.0f, 0.0f},
            c(0.135f), c(0.28f));
    private static final Spline S2 = new Spline(in -> in.r,
            new float[]{-0.5f, -0.4f, -0.3f, -0.1f, -0.03f, 0.03f, 0.1f, 0.3f, 0.4f, 0.5f},
            new float[]{-0.1f, 0.0f, -0.8f, 0.0f, -0.5f, 0.5f, 0.0f, 0.8f, 0.0f, 0.1f},
            c(0.25f), S0, c(0.05f), c(0.01f), c(-0.06f), c(-0.06f), c(0.01f), c(0.05f), S1, c(0.35f));
    private static final Spline S3 = new Spline(in -> S2.get(in),
            new float[]{0.0f},
            new float[]{1.0f},
            c(0.0f));
    private static final Spline S4 = new Spline(in -> in.r,
            new float[]{-0.5f, -0.4f, -0.3f, -0.1f, -0.03f, 0.03f, 0.075f, 0.14f, 0.2f, 0.3f, 0.45f},
            new float[]{-0.1f, 0.0f, -0.8f, 0.0f, -0.5f, 0.5f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f},
            c(0.25f), S0, c(0.05f), c(0.01f), c(-0.06f), c(-0.06f), c(0.01f), c(0.05f), S1, c(0.35f), c(0.03f));
    private static final Spline S5 = new Spline(in -> S4.get(in),
            new float[]{0.0f},
            new float[]{1.0f},
            c(0.0f));
    private static final Spline S6 = new Spline(in -> in.v,
            new float[]{1.0f, 2.0f},
            new float[]{0.0f, 0.0f},
            S5, S3);
    private static final Spline S7 = new Spline(in -> in.r,
            new float[]{-0.45f, -0.3f, -0.2f, -0.125f, -0.075f, -0.03f, 0.03f, 0.075f, 0.125f, 0.2f, 0.3f, 0.45f},
            new float[]{0.0f, 0.0f, 0.0f, -0.8f, 0.0f, -0.5f, 0.5f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f},
            c(0.03f), c(0.25f), S0, c(0.05f), c(0.01f), c(-0.06f), c(-0.06f), c(0.01f), c(0.05f), S1, c(0.35f), c(0.03f));
    private static final Spline S8 = new Spline(in -> S7.get(in),
            new float[]{0.0f},
            new float[]{1.0f},
            c(0.0f));
    private static final Spline S9 = new Spline(in -> in.v,
            new float[]{2.0f, 3.0f, 4.0f, 5.0f},
            new float[]{0.0f, 0.0f, 0.0f, 0.0f},
            S5, S3, S3, S8);
    private static final Spline S10 = new Spline(in -> in.badlands,
            new float[]{0.45f, 0.6f, 0.63f},
            new float[]{0.0f, 1.5f, 0.2f},
            c(0.05f), c(0.15f), c(0.185f));
    private static final Spline S11 = new Spline(in -> in.badlands,
            new float[]{0.45f, 0.6f, 0.63f},
            new float[]{0.0f, 1.5f, 0.2f},
            c(0.05f), c(0.185f), c(0.5f));
    private static final Spline S12 = new Spline(in -> in.rf,
            new float[]{0.0f, 0.1f, 0.2f, 0.3f},
            new float[]{2.5f, 0.0f, 0.0f, 0.0f},
            c(-0.15f), c(0.04f), S10, S11);
    private static final Spline S13 = new Spline(in -> in.t,
            new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f},
            new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
            S3, S6, S9, S3, S12);
    private static final Spline S14 = new Spline(in -> in.lowerTier,
            new float[]{0.0f, 0.15f},
            new float[]{0.0f, 0.0f},
            c(0.05f), c(0.11f));
    private static final Spline S15 = new Spline(in -> in.lowerTier,
            new float[]{0.0f, 0.15f},
            new float[]{0.0f, 0.0f},
            c(0.07f), c(0.14f));
    private static final Spline S16 = new Spline(in -> in.r,
            new float[]{-0.5f, -0.4f, -0.3f, -0.1f, -0.03f, 0.03f, 0.1f, 0.3f, 0.4f, 0.5f},
            new float[]{-0.1f, 0.0f, -0.8f, 0.0f, -0.5f, 0.5f, 0.0f, 0.8f, 0.0f, 0.1f},
            c(0.13f), S14, c(0.05f), c(0.01f), c(-0.06f), c(-0.06f), c(0.01f), c(0.05f), S15, c(0.17f));
    private static final Spline S17 = new Spline(in -> in.t,
            new float[]{4.0f, 5.0f},
            new float[]{0.0f, 0.0f},
            S16, S12);
    private static final Spline S18 = new Spline(in -> in.upperTier,
            new float[]{0.0f, 0.15f},
            new float[]{0.0f, 0.0f},
            c(0.25f), c(0.45f));
    private static final Spline S19 = new Spline(in -> in.rf,
            new float[]{0.03f, 0.1f, 0.3f, 0.4f, 0.55f, 0.6f, 0.64f},
            new float[]{0.5f, 0.0f, 0.8f, 0.0f, 1.0f, 0.0f, 0.0f},
            c(-0.06f), c(0.01f), c(0.05f), S0, c(0.25f), S18, c(0.5f));
    private static final Spline S20 = new Spline(in -> in.rf,
            new float[]{0.025f, 0.1f, 0.175f, 0.2f, 0.4f, 0.4f, 0.8f, 0.85f, 1.0f, 1.05f},
            new float[]{0.03f, 0.0f, 1.5f, 0.2f, 7.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f},
            c(-0.075f), c(0.05f), c(0.1f), c(0.225f), c(0.6f), c(0.6f), c(0.7f), c(0.4f), c(0.4f), c(0.04f));
    private static final Spline S21 = new Spline(in -> in.t,
            new float[]{4.0f, 5.0f},
            new float[]{0.0f, 0.0f},
            S19, S20);
    private static final Spline S22 = new Spline(in -> in.rf,
            new float[]{0.03f, 0.1f, 0.3f, 0.4f, 0.55f},
            new float[]{0.5f, 0.0f, 0.8f, 0.0f, 0.1f},
            c(-0.06f), c(0.01f), c(0.05f), c(0.22f), c(0.25f));
    private static final Spline S23 = new Spline(in -> in.rf,
            new float[]{0.025f, 0.1f, 0.175f, 0.2f, 0.4f, 0.4f, 0.8f, 0.85f, 1.0f, 1.05f},
            new float[]{0.03f, 0.0f, 1.5f, 0.2f, 2.5f, 0.15f, 0.0f, 0.0f, 0.0f, 0.0f},
            c(-0.075f), c(0.05f), c(0.1f), c(0.18f), c(0.3f), c(0.3f), c(0.3f), c(0.23f), c(0.23f), c(0.04f));
    private static final Spline S24 = new Spline(in -> in.t,
            new float[]{4.0f, 5.0f},
            new float[]{0.0f, 0.0f},
            S22, S23);
    private static final Spline S25 = new Spline(in -> in.hills,
            new float[]{0.0f},
            new float[]{0.225f},
            c(0.01f));
    private static final Spline S26 = new Spline(in -> in.rf,
            new float[]{0.03f, 0.1f, 0.2f},
            new float[]{0.5f, 0.1f, 0.0f},
            c(-0.06f), c(0.01f), S25);
    private static final Spline S27 = new Spline(in -> in.rf,
            new float[]{0.03f, 0.1f},
            new float[]{0.5f, 0.1f},
            c(-0.06f), c(0.01f));
    private static final Spline S28 = new Spline(in -> in.v,
            new float[]{2.0f, 3.0f},
            new float[]{0.0f, 0.0f},
            S26, S27);
    private static final Spline S29 = new Spline(in -> in.r,
            new float[]{-0.75f, -0.175f, -0.125f, -0.04f, 0.04f, 0.125f},
            new float[]{0.0f, 0.0f, 0.0f, -0.7f, 0.7f, 0.0f},
            c(0.05f), c(0.015f), c(0.01f), c(-0.06f), c(-0.06f), c(0.01f));
    private static final Spline S30 = new Spline(in -> in.rf,
            new float[]{0.04f, 0.125f, 0.175f, 0.75f, 0.8f},
            new float[]{0.7f, 0.0f, 0.0f, 0.0f, 0.0f},
            c(-0.06f), c(0.01f), c(0.015f), c(0.05f), c(0.6f));
    private static final Spline S31 = new Spline(in -> in.v,
            new float[]{3.0f, 4.0f, 5.0f},
            new float[]{0.0f, 0.0f, 0.0f},
            S27, S29, S30);
    private static final Spline S32 = new Spline(in -> in.rf,
            new float[]{0.03f, 0.1f},
            new float[]{0.5f, 0.15f},
            c(-0.06f), c(0.01f));
    private static final Spline S33 = new Spline(in -> in.t,
            new float[]{3.0f, 4.0f, 5.0f},
            new float[]{0.0f, 0.0f, 0.0f},
            S28, S31, S32);
    private static final Spline S34 = new Spline(in -> in.wetland,
            new float[]{-0.45f, -0.3f, 0.3f, 0.45f},
            new float[]{0.03f, 0.0f, 0.0f, -0.03f},
            c(-0.03f), c(0.005f), c(0.005f), c(-0.03f));
    private static final Spline S35 = new Spline(in -> in.rf,
            new float[]{0.03f, 0.1f, 0.15f},
            new float[]{0.5f, 0.0f, 0.0f},
            c(-0.06f), c(0.01f), S34);
    private static final Spline S36 = new Spline(in -> in.v,
            new float[]{2.0f, 3.0f},
            new float[]{0.0f, 0.0f},
            S27, S35);
    private static final Spline S37 = new Spline(in -> in.t,
            new float[]{4.0f, 5.0f},
            new float[]{0.0f, 0.0f},
            S36, S32);
    private static final Spline S38 = new Spline(in -> in.pillar,
            new float[]{0.05f, 0.2f, 0.5f},
            new float[]{0.0f, -0.2f, 0.05f},
            c(0.0f), c(0.4f), c(0.7f));
    private static final Spline S39 = new Spline(in -> in.pillar,
            new float[]{0.05f, 0.2f, 0.5f},
            new float[]{0.0f, -0.2f, 0.05f},
            c(0.0f), c(0.25f), c(0.5f));
    private static final Spline S40 = new Spline(in -> in.r,
            new float[]{-0.75f, -0.175f, -0.125f},
            new float[]{0.0f, 0.0f, 0.0f},
            S38, S39, c(0.0f));
    private static final Spline S41 = new Spline(in -> in.pillar,
            new float[]{0.05f, 0.2f, 0.5f},
            new float[]{0.0f, -0.2f, 0.05f},
            c(0.0f), c(0.45f), c(0.75f));
    private static final Spline S42 = new Spline(in -> in.rf,
            new float[]{0.125f, 0.175f, 0.75f, 0.8f},
            new float[]{0.0f, 0.0f, 0.0f, 0.0f},
            c(0.0f), S39, S41, c(0.0f));
    private static final Spline S43 = new Spline(in -> in.v,
            new float[]{3.0f, 4.0f, 5.0f},
            new float[]{0.0f, 0.0f, 0.0f},
            c(0.0f), S40, S42);
    private static final Spline S44 = new Spline(in -> in.t,
            new float[]{3.0f, 4.0f, 5.0f},
            new float[]{0.0f, 0.0f, 0.0f},
            c(0.0f), S43, c(0.0f));
    private static final Spline S45 = new Spline(in -> in.sel,
            new float[]{0.0f, 0.1f},
            new float[]{0.0f, 0.0f},
            c(0.0f), S44);
    private static final Spline S46 = new Spline(in -> in.e,
            new float[]{-0.875f, -0.825f, -0.425f, -0.375f},
            new float[]{0.0f, 0.0f, 0.0f, 0.0f},
            c(0.0f), S45, S45, c(0.0f));
    private static final Spline S47 = new Spline(in -> in.rc,
            new float[]{0.0f, 0.1f},
            new float[]{0.0f, 0.0f},
            c(0.0f), S46);
    private static final Spline S48 = new Spline(in -> in.rf,
            new float[]{0.0f, 0.25f},
            new float[]{0.0f, 0.0f},
            c(0.0f), c(6.0f));
    private static final Spline S49 = new Spline(in -> in.sel,
            new float[]{0.0f, 0.1f},
            new float[]{0.0f, 0.0f},
            c(0.0f), S48);
    private static final Spline S50 = new Spline(in -> in.t,
            new float[]{4.0f, 5.0f},
            new float[]{0.0f, 0.0f},
            c(0.0f), S49);
    private static final Spline S51 = new Spline(in -> in.ef,
            new float[]{0.4f, 0.45f, 0.8f, 0.85f},
            new float[]{0.0f, 0.0f, 0.0f, 0.0f},
            c(0.0f), S50, S50, c(0.0f));
    private static final Spline S52 = new Spline(in -> in.rc,
            new float[]{0.0f, 0.05f},
            new float[]{0.0f, 0.0f},
            c(0.0f), S51);
    private static final Spline S53 = new Spline(in -> (in.duneNoise + Math.abs(in.duneBase((16.0f * in.offsetZ), (-16.0f * in.offsetX)))),
            new float[]{0.0f, 0.1f},
            new float[]{0.0f, 0.0f},
            c(0.8f), c(1.0f));
    private static final Spline S54 = new Spline(in -> (in.duneNoise + Math.abs(in.duneBase((16.0f * in.offsetX), (16.0f * in.offsetZ)))),
            new float[]{0.0f, 0.3f},
            new float[]{0.0f, 0.0f},
            c(1.0f), c(0.8f));
    private static final Spline S55 = new Spline(in -> (((1.0f + in.duneNoise) * S53.get(in)) * S54.get(in)),
            new float[]{0.0f, 1.0f},
            new float[]{0.0f, 0.7f},
            c(-0.2f), c(0.2f));

    static double club(In in) {
        return S13.get(in);
    }

    static double clubWeak(In in) {
        return S17.get(in);
    }

    static double spade(In in) {
        return S21.get(in);
    }

    static double spadeWeak(In in) {
        return S24.get(in);
    }

    static double heart(In in) {
        return S33.get(in);
    }

    static double diamond(In in) {
        return S37.get(in);
    }

    static double pillars(In in) {
        return S47.get(in);
    }

    static double duneWeight(In in) {
        return S52.get(in);
    }

    static double duneShape(In in) {
        return S55.get(in);
    }

}
