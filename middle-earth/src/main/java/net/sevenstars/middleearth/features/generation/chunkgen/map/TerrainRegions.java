package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Tectonic's regions on the lowlands: each biome is given the region family and the temperature and vegetation bands
// that pick its landform in Tectonic, and the landforms are blended across biome borders on a world-fixed lattice
final class TerrainRegions {
    enum Landform {
        // Tectonic family, temperature band, vegetation band
        ROLLING_HILLS(Family.HEART, 2, 1),
        PLATEAU(Family.CLUB, 4, 3),
        COLD_PLATEAU(Family.CLUB, 1, 1),
        SINGLE_VALLEY(Family.CLUB, 3, 1),
        DOUBLE_VALLEY(Family.CLUB, 3, 5),
        BADLANDS(Family.CLUB, 5, 1),
        TIERS(Family.SPADE, 3, 3),
        HOT_RIDGES(Family.SPADE, 5, 1),
        WETLAND(Family.DIAMOND, 3, 4),
        DUNES(Family.DIAMOND, 5, 1),
        JUNGLE(Family.HEART, 4, 5);

        final Family family;
        final double t;
        final double v;

        Landform(Family family, int t, int v) {
            this.family = family;
            this.t = t;
            this.v = v;
        }
    }

    // club and spade are Tectonic's strong families, with a weak version and a flat neighbour picked by its selector
    enum Family { CLUB, SPADE, HEART, DIAMOND }

    private static final Map<RegistryKey<Biome>, Landform> LANDFORMS = new HashMap<>();

    static {
        assign(Landform.ROLLING_HILLS, BiomeKeyRegistryME.ROHAN, BiomeKeyRegistryME.ROHAN_FIELD, BiomeKeyRegistryME.THE_WOLD,
                BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD, BiomeKeyRegistryME.ENEDWAITH, BiomeKeyRegistryME.ENEDWAITH_FIELD,
                BiomeKeyRegistryME.ENEDWAITH_WHEAT_FIELD, BiomeKeyRegistryME.MINHIRIATH, BiomeKeyRegistryME.MINHIRIATH_WHEAT_FIELD,
                BiomeKeyRegistryME.ANORIEN, BiomeKeyRegistryME.OLD_ARTHEDAIN, BiomeKeyRegistryME.OLD_ARTHEDAIN_MEADOW,
                BiomeKeyRegistryME.OLD_CARDOLAN, BiomeKeyRegistryME.BARROW_DOWNS, BiomeKeyRegistryME.EASTERN_RHOVANION,
                BiomeKeyRegistryME.SOUTHEAST_RHOVANION, BiomeKeyRegistryME.SOUTHEAST_RHOVANION_FIELD, BiomeKeyRegistryME.RHUN,
                BiomeKeyRegistryME.RHUN_FIELD, BiomeKeyRegistryME.GREY_PLAINS, BiomeKeyRegistryME.GUNDABAD_PLAINS,
                BiomeKeyRegistryME.IRON_HILLS_PLAINS, BiomeKeyRegistryME.DORWINION, BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD,
                BiomeKeyRegistryME.DALE, BiomeKeyRegistryME.SHIRE, BiomeKeyRegistryME.SHIRE_EDGE, BiomeKeyRegistryME.EREGION,
                BiomeKeyRegistryME.EREGION_GLADE, BiomeKeyRegistryME.THE_ANGLE);
        assign(Landform.PLATEAU, BiomeKeyRegistryME.OLD_RHUDAUR, BiomeKeyRegistryME.GORGOROTH, BiomeKeyRegistryME.DESOLATED_LANDS);
        assign(Landform.COLD_PLATEAU, BiomeKeyRegistryME.FORODWAITH, BiomeKeyRegistryME.OLD_ANGMAR, BiomeKeyRegistryME.WITHERED_HEATH,
                BiomeKeyRegistryME.NORTHERN_WASTELANDS, BiomeKeyRegistryME.SOUTHERN_FOROCHEL);
        assign(Landform.SINGLE_VALLEY, BiomeKeyRegistryME.DUNLAND, BiomeKeyRegistryME.NORTHERN_DUNLAND_GLADE, BiomeKeyRegistryME.LAMEDON,
                BiomeKeyRegistryME.BLACKROOT_VALE, BiomeKeyRegistryME.TROLLSHAWS);
        assign(Landform.DOUBLE_VALLEY, BiomeKeyRegistryME.ITHILIEN, BiomeKeyRegistryME.ITHILIEN_GLADE, BiomeKeyRegistryME.ITHILIEN_WASTES,
                BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE);
        assign(Landform.BADLANDS, BiomeKeyRegistryME.HARONDOR, BiomeKeyRegistryME.HARAD, BiomeKeyRegistryME.TOROGWAITH);
        assign(Landform.TIERS, BiomeKeyRegistryME.LEBENNIN, BiomeKeyRegistryME.BELFALAS, BiomeKeyRegistryME.GONDOR,
                BiomeKeyRegistryME.LOSSARNACH, BiomeKeyRegistryME.DRUWAITH_IAUR, BiomeKeyRegistryME.NURN, BiomeKeyRegistryME.EASTERN_NURN,
                BiomeKeyRegistryME.NURN_EDGE);
        assign(Landform.HOT_RIDGES, BiomeKeyRegistryME.UMBAR, BiomeKeyRegistryME.MORDOR);
        assign(Landform.WETLAND, BiomeKeyRegistryME.NINDALF, BiomeKeyRegistryME.LONG_MARSHES, BiomeKeyRegistryME.MIRKWOOD_MARSHES,
                BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES, BiomeKeyRegistryME.NORTHERN_MIRKWOOD_SWAMP, BiomeKeyRegistryME.MIRKWOOD_SWAMP,
                BiomeKeyRegistryME.ETHIR_ANDUIN);
        assign(Landform.DUNES, BiomeKeyRegistryME.HARAD_DESERT);
        assign(Landform.JUNGLE, BiomeKeyRegistryME.HARAD_WOODS);
    }

    @SafeVarargs
    private static void assign(Landform landform, RegistryKey<Biome>... biomes) {
        for (RegistryKey<Biome> biome : biomes) LANDFORMS.put(biome, landform);
    }

    private static final Landform[] ALL = Landform.values();
    private static final int LATTICE = 32;
    private static final int SAMPLE_STEP = 24;
    private static final int SAMPLE_RADIUS = 3;
    private static final float MIN_SHARE = 0.001f;

    private static final double BLOCKS_PER_UNIT = 128;
    private static final double VERTICAL_SCALE = 1.125; // Tectonic's, on the offsets above sea level only
    private static final double FLAT = 0.01; // Tectonic's level land, which our map height already is
    private static final double RIVER_FLOOR = 0.1; // the channels Tectonic cuts where its ridge noise is 0 are left out, the map has its rivers
    private static final double FACTOR = 5.6; // Tectonic's squash outside the windswept band, dunes are added after it
    private static final double SEA = 63.52;
    private static final double SELECTOR_SKEW = 0; // Tectonic's 0.1 leaves half its regions flat, these are chosen per biome
    private static final float WETLAND_WATER_FADE = 3; // quicker for the wetlands
    private static final float WATER_FADE = 10;
    private static final double SHORE_START = 1; // blocks above the water where a landform starts to show
    private static final double SHORE_WIDTH = 6; // and over which it comes in fully // blue of the baked height map at which a landform is gone, as for the named hills

    private TerrainRegions() {
    }

    // the height with the landforms of the nearby lowland biomes added
    static float raise(int x, int z, float height) {
        float[] shares = SHARES.get();
        sharesAt(x, z, shares);
        boolean any = false;
        for (float share : shares) any |= share > MIN_SHARE;
        if (!any) return height;

        float water = MiddleEarthMapRuntime.getInstance().getHeightRgb(x, z) & 0xFF;
        float fade = Math.max(0, 1 - water / WATER_FADE);
        if (fade <= 0) return height;

        // and where the ground already lies at the water line: rivers and their banks keep their shape
        double surface = MiddleEarthChunkGenerator.DIRT_HEIGHT + height;
        int waterHeight = MiddleEarthMapRuntime.getInstance().getBiome(x, z).getWaterHeight();
        float shore = (float) Math.clamp((surface - waterHeight - SHORE_START) / SHORE_WIDTH, 0, 1);

        long seed = MiddleEarthHeightMap.getSeed();
        TectonicRegions.In in = INPUTS.get();
        fill(in, x, z, seed);
        double hm = Math.clamp(1.2 + 0.4 * TectonicNoise.HEIGHT_MULTIPLIER.at(x, z, seed), 0.8, 1.6);
        double change = 0;
        for (Landform landform : ALL) {
            float share = shares[landform.ordinal()];
            if (share <= MIN_SHARE) continue;
            in.t = landform.t;
            in.v = landform.v;
            double offset = offset(landform, in) * hm;
            if (offset > 0) offset *= VERTICAL_SCALE;
            double blocks;
            if (landform == Landform.WETLAND) {
                // set to Tectonic's own level, so its ponds sink below the water line
                blocks = SEA + BLOCKS_PER_UNIT * offset - surface;
            } else {
                blocks = BLOCKS_PER_UNIT * (offset - FLAT * hm * VERTICAL_SCALE);
            }
            if (landform == Landform.DUNES) blocks += BLOCKS_PER_UNIT * dunes(in) / FACTOR;
            if (landform == Landform.JUNGLE) blocks += BLOCKS_PER_UNIT * pillars(in);
            // wetlands belong at the water line but stay out of the rivers, where their ponds would break the river
            // into patches; everything else fades out towards the water line
            change += share * blocks * (landform == Landform.WETLAND ? Math.max(0, 1 - water / WETLAND_WATER_FADE) : shore);
        }
        return height + (float) (fade * change);
    }

    private static double offset(Landform landform, TectonicRegions.In in) {
        return switch (landform.family) {
            case CLUB -> select(in, TectonicRegions.club(in), TectonicRegions.clubWeak(in), TectonicRegions.heart(in));
            case SPADE -> select(in, TectonicRegions.spade(in), TectonicRegions.spadeWeak(in), TectonicRegions.diamond(in));
            case HEART -> TectonicRegions.heart(in);
            case DIAMOND -> TectonicRegions.diamond(in);
        };
    }

    // Tectonic's region selector: the strong landform below -0.1, the weak one at 0 and the flat neighbour from 0.1
    private static double select(TectonicRegions.In in, double strong, double weak, double flat) {
        double sel = in.sel;
        if (sel <= -0.1) return strong;
        if (sel <= 0) return lerp(smooth((sel + 0.1) / 0.1), strong, weak);
        if (sel <= 0.1) return lerp(smooth(sel / 0.1), weak, flat);
        return flat;
    }

    private static double smooth(double t) {
        return t * t * (3 - 2 * t);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // inland, in the erosion band of the regions and with the selector open, as Tectonic needs for dunes and pillars
    private static double dunes(TectonicRegions.In in) {
        double sel = in.sel;
        double ef = in.ef;
        in.rc = 1;
        in.ef = 0.6;
        in.sel = 0.1;
        double weight = TectonicRegions.duneWeight(in);
        double dune = Math.max(0, weight * TectonicRegions.duneShape(in));
        in.sel = sel;
        in.ef = ef;
        return dune;
    }

    private static double pillars(TectonicRegions.In in) {
        double sel = in.sel;
        in.rc = 1;
        in.e = -0.6;
        in.sel = 0.1;
        double pillars = TectonicRegions.pillars(in);
        in.sel = sel;
        return pillars;
    }

    private static final ThreadLocal<TectonicRegions.In> INPUTS = ThreadLocal.withInitial(TectonicRegions.In::new);

    private static void fill(TectonicRegions.In in, int x, int z, long seed) {
        in.x = x;
        in.z = z;
        in.seed = seed;
        double r = TectonicNoise.RIDGE.at(x, z, seed);
        in.r = Math.copySign(Math.max(Math.abs(r), RIVER_FLOOR), r);
        in.rf = Math.abs(in.r);
        in.sel = SELECTOR_SKEW + TectonicNoise.SELECTOR.at(x, z, seed);
        in.e = -0.6;
        in.ef = 0.6;
        in.rc = 1;
        in.lowerTier = Math.abs(TectonicNoise.LOWER_TIER.at(x, z, seed));
        in.upperTier = Math.abs(TectonicNoise.UPPER_TIER.at(x, z, seed));
        in.badlands = Math.abs(TectonicNoise.BADLANDS.at(x, z, seed));
        in.hills = Math.abs(TectonicNoise.ROLLING_HILLS.at(x, z, seed));
        in.pillar = 1.7 * (TectonicNoise.JUNGLE_PILLAR.at(x, z, seed) - 0.35);
        in.wetland = TectonicNoise.WETLAND.at(x, z, seed);
        in.duneNoise = -Math.abs(TectonicNoise.DUNE_BASE.sample(x * 0.76, z * 0.76, seed));
        double wind = TectonicNoise.DUNE_OFFSET.at(x, z, seed);
        in.offsetX = -Math.sin(Math.PI * wind);
        in.offsetZ = Math.cos(Math.PI * wind);
    }

    // shares of each landform from the map biomes nearby, on a world-fixed lattice and bilinear between its points, so
    // the landforms fade across biome borders and chunks agree
    private static final ThreadLocal<float[]> SHARES = ThreadLocal.withInitial(() -> new float[ALL.length]);

    private static final class Point {
        int ix = Integer.MIN_VALUE;
        int iz;
        final float[] shares = new float[ALL.length];
    }

    private static final int CACHE_SIZE = 256;
    private static final ThreadLocal<Point[]> CACHE = ThreadLocal.withInitial(() -> new Point[CACHE_SIZE]);

    private static void sharesAt(int x, int z, float[] out) {
        int ix = Math.floorDiv(x, LATTICE);
        int iz = Math.floorDiv(z, LATTICE);
        float fx = (x - ix * LATTICE) / (float) LATTICE;
        float fz = (z - iz * LATTICE) / (float) LATTICE;
        Point p00 = point(ix, iz);
        Point p10 = point(ix + 1, iz);
        Point p01 = point(ix, iz + 1);
        Point p11 = point(ix + 1, iz + 1);
        for (int i = 0; i < out.length; i++) {
            float top = p00.shares[i] + fx * (p10.shares[i] - p00.shares[i]);
            float bottom = p01.shares[i] + fx * (p11.shares[i] - p01.shares[i]);
            out[i] = top + fz * (bottom - top);
        }
    }

    private static Point point(int ix, int iz) {
        Point[] points = CACHE.get();
        int slot = (ix * 31 + iz * 17) & (CACHE_SIZE - 1);
        Point point = points[slot];
        if (point != null && point.ix == ix && point.iz == iz) return point;
        if (point == null) point = points[slot] = new Point();
        point.ix = ix;
        point.iz = iz;
        Arrays.fill(point.shares, 0);
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        int samples = (2 * SAMPLE_RADIUS + 1) * (2 * SAMPLE_RADIUS + 1);
        for (int i = -SAMPLE_RADIUS; i <= SAMPLE_RADIUS; i++) {
            for (int j = -SAMPLE_RADIUS; j <= SAMPLE_RADIUS; j++) {
                Landform landform = LANDFORMS.get(map.getBiome(ix * LATTICE + i * SAMPLE_STEP, iz * LATTICE + j * SAMPLE_STEP).getBiomeKey());
                if (landform != null) point.shares[landform.ordinal()] += 1f / samples;
            }
        }
        return point;
    }
}
