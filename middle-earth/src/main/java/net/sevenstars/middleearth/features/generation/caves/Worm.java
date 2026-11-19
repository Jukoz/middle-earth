package net.sevenstars.middleearth.features.generation.caves;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;

// One worm-bored tunnel of the gnawed deeps: a straight axis with two slow waves across it, a round bore around that
public final class Worm {
    static final int WORM_CELL_XZ = 64;
    static final int WORM_CELL_Y = 48;
    static final int WORMS_PER_CELL = 5;
    static final int WORM_HALF = 45; // half the length of a worm
    static final double WORM_PITCH = 0.45; // radians, so they run mostly level
    static final float WORM_MIN_RADIUS = 2.4f; // 5 to 7 blocks across, a little less where a wave is steep
    static final float WORM_MAX_RADIUS = 3.6f;
    static final float WORM_WAVE = 9;
    static final float WORM_WAVELENGTH = 55;

    final float cx, cy, cz; // middle of the axis
    final float dx, dy, dz; // along it
    final float ux, uy, uz; // the two directions across it
    final float vx, vy, vz;
    final float radius;
    final float a1, f1, p1;
    final float a2, f2, p2;
    final float reach;

    Worm(float cx, float cy, float cz, int h) {
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        double yaw = (mix(h, 1, 0) & 0xFFFF) / 65536.0 * Math.PI * 2;
        double pitch = ((mix(h, 2, 0) & 0xFFFF) / 32768.0 - 1) * WORM_PITCH;
        dx = (float) (Math.cos(pitch) * Math.cos(yaw));
        dy = (float) Math.sin(pitch);
        dz = (float) (Math.cos(pitch) * Math.sin(yaw));
        ux = (float) -Math.sin(yaw);
        uy = 0;
        uz = (float) Math.cos(yaw);
        vx = (float) (-Math.sin(pitch) * Math.cos(yaw));
        vy = (float) Math.cos(pitch);
        vz = (float) (-Math.sin(pitch) * Math.sin(yaw));
        radius = WORM_MIN_RADIUS + (mix(h, 3, 0) & 0xFF) / 255f * (WORM_MAX_RADIUS - WORM_MIN_RADIUS);
        a1 = WORM_WAVE * (0.4f + 0.6f * (mix(h, 4, 0) & 0xFF) / 255f);
        f1 = (float) (Math.PI * 2 / (WORM_WAVELENGTH * (0.7 + 0.6 * (mix(h, 5, 0) & 0xFF) / 255f)));
        p1 = (mix(h, 6, 0) & 0xFF) / 255f * 6.28f;
        a2 = WORM_WAVE * (0.4f + 0.6f * (mix(h, 7, 0) & 0xFF) / 255f);
        f2 = (float) (Math.PI * 2 / (WORM_WAVELENGTH * (0.7 + 0.6 * (mix(h, 8, 0) & 0xFF) / 255f)));
        p2 = (mix(h, 9, 0) & 0xFF) / 255f * 6.28f;
        reach = WORM_HALF + a1 + a2 + radius;
    }
}
