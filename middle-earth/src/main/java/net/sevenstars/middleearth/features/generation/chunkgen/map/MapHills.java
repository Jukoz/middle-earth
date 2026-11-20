package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.HashMap;
import java.util.Map;

// named hills set by hand on top of the map height; positions in initial map pixels, sizes in blocks
public final class MapHills {
    private static final int CELL = 512;
    private static final float WOBBLE = 0.12f; // how far the outline strays from an ellipse
    private static final double WOBBLE_SCALE = 48;
    private static final int WOBBLE_SALT = 0x7f4a7c15;
    private static final float WATER_FADE = 10; // blue of the baked height map at which a hill is gone, rivers stay rivers

    // plateau: share of the radius that is flat on top; sharpness above 1 gives steep crowns and long skirts, below 1 round
    // domes; stretch lengthens the hill along angle (degrees from east, clockwise); level pulls the top flat instead of
    // carrying the rolling noise up
    private static final class Hill {
        final float x;
        final float z;
        final float radius;
        final float height;
        float plateau;
        float sharpness = 1;
        float stretch = 1;
        float cos = 1;
        float sin;
        float level;
        boolean island; // stands in water, so the river fade is skipped
        private volatile CentreBase centre;

        Hill(float mapX, float mapZ, float radius, float height) {
            this.x = mapX * MiddleEarthChunkGenerator.mapMultiplier;
            this.z = mapZ * MiddleEarthChunkGenerator.mapMultiplier;
            this.radius = radius;
            this.height = height;
        }

        Hill plateau(float plateau) { this.plateau = plateau; return this; }
        Hill sharpness(float sharpness) { this.sharpness = sharpness; return this; }
        Hill level(float level) { this.level = level; return this; }
        Hill island() { this.island = true; return this; }

        Hill stretch(float stretch, float degrees) {
            this.stretch = stretch;
            this.cos = (float) Math.cos(Math.toRadians(degrees));
            this.sin = (float) Math.sin(Math.toRadians(degrees));
            return this;
        }

        float centreBase(long seed) {
            CentreBase known = centre;
            if (known == null || known.seed != seed) {
                known = new CentreBase(seed, MiddleEarthHeightMap.getUnraisedHeight((int) x, (int) z));
                centre = known;
            }
            return known.height;
        }
    }

    private record CentreBase(long seed, float height) {
    }

    private static final Map<Long, Hill[]> BY_CELL = new HashMap<>();

    static {
        // THE SHIRE
        add(new Hill(930, 882, 130, 24).sharpness(0.8f).stretch(1.3f, 0)); // the Hill, Bag End
        add(new Hill(922, 930, 130, 20).sharpness(0.8f)); // Great Smials, Tuckborough
        add(new Hill(966, 876, 90, 18).sharpness(1.3f)); // Hills of Scary
        add(new Hill(974, 868, 80, 15).sharpness(1.3f));
        add(new Hill(958, 868, 70, 13).sharpness(1.3f));
        add(new Hill(1003, 960, 80, 16).sharpness(0.8f).stretch(1.4f, 90)); // Buck Hill, Brandy Hall

        // EMYN BERAID, Elostirion the tallest and westernmost
        add(new Hill(776, 898, 170, 42).sharpness(1.2f).plateau(0.08f).level(0.6f));
        add(new Hill(786, 905, 150, 32).sharpness(1.1f));
        add(new Hill(796, 911, 140, 28).sharpness(1.1f));

        // BREE-LAND
        add(new Hill(1085, 900, 260, 36).stretch(1.3f, 20)); // Bree-hill
        add(new Hill(1046, 940, 50, 10).sharpness(0.7f)); // Tom Bombadil's knoll

        // WEATHER HILLS, Weathertop standing a little apart at the south end
        add(new Hill(1166, 906, 230, 72).sharpness(1.7f).plateau(0.14f).level(1));
        add(new Hill(1160, 887, 210, 36).sharpness(1.3f).stretch(1.6f, 100));
        add(new Hill(1156, 870, 240, 44).sharpness(1.3f).stretch(1.7f, 95));
        add(new Hill(1151, 852, 220, 38).sharpness(1.3f).stretch(1.6f, 105));
        add(new Hill(1146, 834, 200, 34).sharpness(1.3f).stretch(1.5f, 100));

        // NEN HITHOEL
        add(new Hill(1792, 1585, 130, 70).sharpness(1.5f).plateau(0.12f).level(1)); // Amon Hen
        add(new Hill(1823, 1582, 130, 70).sharpness(1.5f).plateau(0.12f).level(1)); // Amon Lhaw
        add(new Hill(1812, 1596, 40, 46).sharpness(2.4f).island()); // Tol Brandir

        add(new Hill(1614, 1215, 70, 14).sharpness(0.7f).plateau(0.2f).level(0.8f)); // Cerin Amroth

        // ROHAN
        add(new Hill(1516, 1589, 110, 26).sharpness(1.2f).plateau(0.25f).level(1)); // Edoras
        add(new Hill(1600, 1660, 110, 20).sharpness(1.1f).plateau(0.2f).level(1)); // Aldburg
        add(new Hill(1398, 1497, 130, 30).sharpness(0.9f)); // Dol Baran
        for (int i = 0; i < 4; i++) { // the Barrowfield, two rows of mounds beside the road out of Edoras
            add(new Hill(1520.2f + i * 0.55f, 1586.6f - i * 0.3f, 7, 4).sharpness(0.6f));
            add(new Hill(1520.8f + i * 0.55f, 1587.6f - i * 0.3f, 7, 4).sharpness(0.6f));
        }

        // THE BEACONS, from the Rohan border east to Minas Tirith
        add(new Hill(1745, 1715, 200, 66).sharpness(1.2f).plateau(0.06f)); // Halifirien
        add(new Hill(1770, 1728, 110, 34).sharpness(1.1f)); // Calenhad
        add(new Hill(1805, 1745, 150, 54).sharpness(1.8f)); // Min-Rimmon
        add(new Hill(1840, 1750, 110, 30).sharpness(1.1f)); // Erelas
        add(new Hill(1870, 1755, 150, 48).sharpness(1.4f).stretch(1.5f, 0)); // Nardol
        add(new Hill(1893, 1758, 120, 50).sharpness(2)); // Eilenach
        add(new Hill(1912, 1752, 140, 44).sharpness(1.6f)); // Amon Dîn

        // GONDOR
        add(new Hill(1515, 1722, 100, 26).sharpness(1.1f).plateau(0.15f).level(1)); // Hill of Erech
        add(new Hill(1975, 1820, 150, 34).sharpness(1.1f)); // Emyn Arnen
        add(new Hill(1986, 1832, 130, 28).sharpness(1.1f));
        add(new Hill(1972, 1836, 110, 24).sharpness(1.1f));
    }

    private MapHills() {
    }

    private static void add(Hill hill) {
        int reach = (int) Math.ceil(hill.radius * Math.max(1, hill.stretch) * (1 + WOBBLE));
        for (int cx = Math.floorDiv((int) hill.x - reach, CELL); cx <= Math.floorDiv((int) hill.x + reach, CELL); cx++) {
            for (int cz = Math.floorDiv((int) hill.z - reach, CELL); cz <= Math.floorDiv((int) hill.z + reach, CELL); cz++) {
                BY_CELL.merge(key(cx, cz), new Hill[]{hill}, (a, b) -> {
                    Hill[] joined = new Hill[a.length + 1];
                    System.arraycopy(a, 0, joined, 0, a.length);
                    joined[a.length] = b[0];
                    return joined;
                });
            }
        }
    }

    private static long key(int cx, int cz) {
        return ((long) cx << 32) | (cz & 0xFFFFFFFFL);
    }

    static float raise(int x, int z, float height) {
        Hill[] hills = BY_CELL.get(key(Math.floorDiv(x, CELL), Math.floorDiv(z, CELL)));
        if (hills == null) return height;
        long seed = MiddleEarthHeightMap.getSeed();
        float base = height;
        float water = Float.NaN;
        for (Hill hill : hills) {
            float dx = x - hill.x;
            float dz = z - hill.z;
            float along = (dx * hill.cos + dz * hill.sin) / hill.stretch;
            float across = -dx * hill.sin + dz * hill.cos;
            float distance = (float) Math.sqrt(along * along + across * across) / hill.radius;
            if (distance >= 1 + WOBBLE) continue;
            distance *= 1 + WOBBLE * (float) TerrainRidges.noise(x / WOBBLE_SCALE, z / WOBBLE_SCALE, WOBBLE_SALT);
            if (distance >= 1) continue;

            float u = Math.max(0, (distance - hill.plateau) / (1 - hill.plateau));
            float s = 1 - u;
            float profile = (float) Math.pow(s * s * (3 - 2 * s), hill.sharpness);
            if (!hill.island) {
                if (Float.isNaN(water)) water = MiddleEarthMapRuntime.getInstance().getHeightRgb(x, z) & 0xFF;
                profile *= Math.max(0, 1 - water / WATER_FADE);
                if (profile <= 0) continue;
            }
            float raised = hill.height;
            if (hill.level > 0) raised += hill.level * (hill.centreBase(seed) - base);
            height += profile * raised;
        }
        return height;
    }
}
