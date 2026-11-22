package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.features.generation.noise.SaltedSimplexNoise;

// Helm's Deep: the gorge from the Deeping-coomb to the Glittering Caves. It only lowers the ground.
public final class HelmsDeep {
    private static final float[][] LINE = {{46790, 50228}, {46802, 50290}, {46826, 50360}, {46842, 50430}, {46850, 50492}};
    private static final float RISE = 34;
    private static final float MOUTH_HALF_WIDTH = 20;
    private static final float HEAD_HALF_WIDTH = 9;
    private static final float WALL = 7;
    private static final float FLARE = 0.18f;
    private static final float WOBBLE = 0.35f;
    private static final double WOBBLE_SCALE = 34;
    private static final int WOBBLE_SALT = 0x2d7a91c3;
    private static final float JAG = 7;
    private static final double JAG_SCALE = 9;
    private static final float FLOOR_ROUGH = 1.5f;
    private static final float STREAM_HALF_WIDTH = 0.9f;
    private static final float REACH = MOUTH_HALF_WIDTH * (1 + 1.5f * WOBBLE) + 300 / (WALL * 0.3f);

    private static final float LENGTH;
    private static final float[] START = new float[LINE.length]; // distance along the line per point
    private static final int MIN_X, MAX_X, MIN_Z, MAX_Z;

    static {
        float total = 0;
        for (int i = 1; i < LINE.length; i++) {
            total += (float) Math.hypot(LINE[i][0] - LINE[i - 1][0], LINE[i][1] - LINE[i - 1][1]);
            START[i] = total;
        }
        LENGTH = total;
        float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE, minZ = Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;
        for (float[] p : LINE) {
            minX = Math.min(minX, p[0]);
            maxX = Math.max(maxX, p[0]);
            minZ = Math.min(minZ, p[1]);
            maxZ = Math.max(maxZ, p[1]);
        }
        MIN_X = (int) (minX - REACH);
        MAX_X = (int) (maxX + REACH);
        MIN_Z = (int) (minZ - REACH);
        MAX_Z = (int) (maxZ + REACH);
    }

    // the ground at the mouth before the gorge
    private static final PerSeedHeight MOUTH_GROUND = new PerSeedHeight(() -> MiddleEarthHeightMap.getRaisedHeight((int) LINE[0][0], (int) LINE[0][1]));

    private HelmsDeep() {
    }

    public static boolean stream(int x, int z) {
        if (x < MIN_X || x > MAX_X || z < MIN_Z || z > MAX_Z) return false;
        float[] near = nearest(x, z);
        float meander = 1.2f * (float) SaltedSimplexNoise.noise(near[1] / 11.0, 0.5, WOBBLE_SALT + 4);
        return Math.abs(near[2] - meander) < STREAM_HALF_WIDTH;
    }

    // {distance to the line, distance along it, signed distance}
    private static float[] nearest(int x, int z) {
        float best = Float.MAX_VALUE;
        float along = 0;
        float side = 0;
        for (int i = 1; i < LINE.length; i++) {
            float ax = LINE[i - 1][0], az = LINE[i - 1][1];
            float sx = LINE[i][0] - ax, sz = LINE[i][1] - az;
            float span = sx * sx + sz * sz;
            float t = MathHelper.clamp(((x - ax) * sx + (z - az) * sz) / span, 0f, 1f);
            float d = (float) Math.hypot(x - (ax + sx * t), z - (az + sz * t));
            if (d < best) {
                best = d;
                along = START[i - 1] + t * (START[i] - START[i - 1]);
                side = Math.signum((x - ax) * sz - (z - az) * sx) * d;
            }
        }
        return new float[]{best, along, side};
    }

    static float carve(int x, int z, float height) {
        if (x < MIN_X || x > MAX_X || z < MIN_Z || z > MAX_Z) return height;
        float[] near = nearest(x, z);
        float best = near[0];
        float along = near[1];
        float share = along / LENGTH;
        float wobble = 1 + WOBBLE * (float) (SaltedSimplexNoise.noise(x / WOBBLE_SCALE, z / WOBBLE_SCALE, WOBBLE_SALT)
                + 0.5 * SaltedSimplexNoise.noise(x / (WOBBLE_SCALE / 2.5), z / (WOBBLE_SCALE / 2.5), WOBBLE_SALT + 2));
        float halfWidth = MathHelper.lerp(share, MOUTH_HALF_WIDTH, HEAD_HALF_WIDTH) * wobble;
        float floor = MOUTH_GROUND.get() + RISE * (float) Math.pow(share, 1.3)
                + FLOOR_ROUGH * (float) SaltedSimplexNoise.noise(x / 9.0, z / 9.0, WOBBLE_SALT + 1);
        float steep = WALL * MathHelper.clamp(share / FLARE, 0.3f, 1f);
        float out = Math.max(0, best - halfWidth);
        float jag = JAG * (float) SaltedSimplexNoise.noise(x / JAG_SCALE, z / JAG_SCALE, WOBBLE_SALT + 3) * Math.min(1, out / 4);
        return Math.min(height, floor + out * steep + jag);
    }
}
