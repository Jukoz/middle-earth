package net.jukoz.me.world.map;

import net.jukoz.me.MiddleEarth;

public class MiddleEarthMapConfigs {
    public static final boolean FORCE_GENERATION = false;
    /**
     * Map Iteration Explanation :
     * 1 : 6000 (24,000);
     * 2 : 12 000 (48,000);
     * 3 : 24 000 (96,000);
     * 4 : 48 000 (192,000);
     * 5 : 96 000 (384,000);
     * 6 : 192 000 (768,000);
     * 7 : 384 000 (1,536,000);
     */
    public static final int MAP_ITERATION = 3;

    private static final String MOD_DATA = "data/me/%s".formatted(MiddleEarth.MOD_VERSION);
    public static final String BIOME_PATH = "%s/biomes/i_%s".formatted(MOD_DATA, "%s");
    public static final String HEIGHT_PATH = "%s/heights/i_%s".formatted(MOD_DATA, MAP_ITERATION);
    public static final String BASE_HEIGHT_PATH = "%s/heights/base".formatted(MOD_DATA);
    public static final String BASE_HEIGHT_IMAGE_NAME = "/base_height.png";
    public static final String IMAGE_NAME = "/%s_%s.png";
    public static final String INITIAL_IMAGE = "assets/%s/textures/map.png".formatted(MiddleEarth.MOD_ID);;

    /**
     *  Current image is 3000 x 3000, should be a common divider;
     */
    public static final int REGION_SIZE = 3000;
    public static final int PIXEL_WEIGHT = 4;
    public static final int THREAD_POOL_SIZE = 16;
    public static final int BIOME_VALIDATION_TICK_DELAY = 1000;
    public static final int BIOME_VALIDATION_BLOCK_DELAY = 5000;
    public final static int BIOME_VALIDATION_DIST_CHECK = 2000;
}
