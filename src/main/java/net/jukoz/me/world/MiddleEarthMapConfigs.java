package net.jukoz.me.world;

public class MiddleEarthMapConfigs {
    public static final String BIOME_PATH = "data/me/biomes/i_%s";
    public static final String HEIGHT_PATH = "data/me/heights";
    public static final String IMAGE_NAME = "/%s_%s.png";
    public static final int REGION_SIZE = 3000;
    public static final int PIXEL_WEIGHT = 4;
    public static final int THREAD_POOL_SIZE = 12;

    // 1 : 6000 (24,000)
    // 2 : 12 000 (48,000)
    // 3 : 24 000 (96,000)
    // 4 : 48 000 (192,000)
    // 5 : 96 000 (384,000)
    // 6 : 192 000 (768,000)
    // 7 : 384 000 (1,536,000)
    public static final int MAP_ITERATION = 2;
    public static final boolean FORCE_GENERATION = false;

}
