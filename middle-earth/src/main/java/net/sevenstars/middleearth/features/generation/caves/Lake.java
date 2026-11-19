package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.util.math.MathHelper;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

public final class Lake {
    static final int LAKE_CELL = 512; // multiple of 16, so a chunk sits in one cell
    static final int LAKE_EDGE = 114; // widest basin plus shell, so lakes never meet
    static final int LAKE_CHANCE = 100; // out of 256
    static final float LAKE_MIN_WEIGHT = 0.95f;
    static final float LAKE_RIM_WEIGHT = 0.5f;
    static final float LAKE_WOBBLE = 0.32f;
    static final float LAKE_DOME_WOBBLE = 0.25f;
    static final int LAKE_SHELL = 5;
    static final int LAKE_ABYSS_GAP = 12; // blocks of rock kept between a lake and an abyss
    static final int LAKE_WATER_MIN_Y = -95;
    static final int LAKE_WATER_MAX_Y = -66;
    static final int LAKE_FLOOR_CLEARANCE = 6; // shell bottom above the deep floor
    static final int LAKE_TUNNEL_MAX_Y = 45;
    static final int LAKE_TUNNEL_LENGTH = 130;
    static final int LAKE_TUNNEL_STEP = 8; // between fit checks along the tunnel
    static final float LAKE_TUNNEL_SLOPE = 0.8f; // steep enough to clear the rising floor at the edge of the deep region
    static final float LAKE_TUNNEL_RADIUS = 3.2f;
    static final int LAKE_SURFACE_CLEARANCE = 40;
    static final int LAKE_REACH = 256; // tunnel included

    final int x;
    final int z;
    final float rx;
    final float rz;
    final int waterTop; // highest water block
    final int depth;
    final int height;
    final float wobble;
    final int islands;
    final float[] islandX = new float[2];
    final float[] islandZ = new float[2];
    final float[] islandRadius = new float[2];
    final int pillars;
    final float[] pillarX = new float[8];
    final float[] pillarZ = new float[8];
    final float[] pillarRadius = new float[8];
    final float[] pillarPhase = new float[8];
    final float tunnelX;
    final float tunnelY;
    final float tunnelZ;
    final float tunnelDx;
    final float tunnelDz;

    Lake(MistyCaves caves, int x, int z, int h) {
        this.x = x;
        this.z = z;
        this.rx = 40 + (mix(h, 21, 0) & 0xFF) / 255f * 40;
        this.rz = 40 + (mix(h, 22, 0) & 0xFF) / 255f * 40;
        this.depth = 6 + (mix(h, 24, 0) & 0xFF) % 7;
        this.waterTop = LAKE_WATER_MIN_Y + (mix(h, 23, 0) & 0xFFFF) % (LAKE_WATER_MAX_Y - LAKE_WATER_MIN_Y + 1);
        this.height = 14 + (mix(h, 25, 0) & 0xFF) % 13;
        this.wobble = (mix(h, 26, 0) & 0xFFF);
        float minRadius = Math.min(rx, rz);
        int islandRoll = (mix(h, 27, 0) & 0xFF) % 8;
        this.islands = islandRoll < 3 ? 0 : islandRoll < 6 ? 1 : 2;
        for (int i = 0; i < islands; i++) {
            double angle = (mix(h, 28, i) & 0xFFFF) / 65536.0 * Math.PI * 2;
            float rho = 0.25f + 0.35f * (mix(h, 29, i) & 0xFF) / 255f;
            islandX[i] = x + (float) Math.cos(angle) * rho * rx;
            islandZ[i] = z + (float) Math.sin(angle) * rho * rz;
            islandRadius[i] = 4 + (mix(h, 30, i) & 0xFF) / 255f * 6;
        }
        this.pillars = 3 + (mix(h, 40, 0) & 0xFF) % 6;
        for (int i = 0; i < pillars; i++) {
            double angle = (mix(h, 41, i) & 0xFFFF) / 65536.0 * Math.PI * 2;
            float rho = 0.1f + 0.55f * (mix(h, 42, i) & 0xFF) / 255f;
            pillarX[i] = x + (float) Math.cos(angle) * rho * rx;
            pillarZ[i] = z + (float) Math.sin(angle) * rho * rz;
            pillarRadius[i] = 2 + (mix(h, 43, i) & 0xFF) / 255f * 3;
            pillarPhase[i] = (mix(h, 44, i) & 0xFF) / 255f * 6.28f;
        }
        double angle = (mix(h, 31, 0) & 0xFFFF) / 65536.0 * Math.PI * 2;
        this.tunnelDx = (float) Math.cos(angle);
        this.tunnelDz = (float) Math.sin(angle);
        // starts a little inside the rim, at the water line, so it opens onto the shore
        float rim = 1 / (float) Math.sqrt(sq(tunnelDx / rx) + sq(tunnelDz / rz));
        float start = rim * caves.outline(wobble, x + tunnelDx * rim, z + tunnelDz * rim) - 6;
        this.tunnelX = x + tunnelDx * start;
        this.tunnelZ = z + tunnelDz * start;
        this.tunnelY = waterTop + 3.5f;
    }

    float ceilingMax() {
        return height * (1 + LAKE_DOME_WOBBLE);
    }

    int lowestY() {
        return waterTop - depth - LAKE_SHELL;
    }

    boolean touches(int startX, int startZ) {
        int reach = (int) (Math.max(rx, rz) * (1 + LAKE_WOBBLE)) + LAKE_SHELL + 1;
        if (x + reach >= startX && x - reach <= startX + 15 && z + reach >= startZ && z - reach <= startZ + 15) return true;
        float cx = startX + 8 - tunnelX;
        float cz = startZ + 8 - tunnelZ;
        float t = MathHelper.clamp(cx * tunnelDx + cz * tunnelDz, 0, LAKE_TUNNEL_LENGTH);
        float pad = LAKE_TUNNEL_RADIUS + 13; // half a chunk diagonal
        return sq(cx - t * tunnelDx) + sq(cz - t * tunnelDz) <= pad * pad;
    }
}
