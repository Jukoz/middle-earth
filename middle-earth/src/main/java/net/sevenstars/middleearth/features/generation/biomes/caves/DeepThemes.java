package net.sevenstars.middleearth.features.generation.biomes.caves;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.noise.BlendedNoise;

// which deep biome a column belongs to, biome source and chunk generation both ask, so they agree
public final class DeepThemes {
    public enum Theme {
        WET(BiomeKeyRegistryME.NAMELESS_DEEPS),
        GNAWED(BiomeKeyRegistryME.GNAWED_TUNNELS),
        GROTTO(BiomeKeyRegistryME.GLOWWORM_GROTTO);

        public final RegistryKey<Biome> biome;

        Theme(RegistryKey<Biome> biome) {
            this.biome = biome;
        }
    }

    // thresholds of noise that is evenly spread between -1 and 1: the top tenth is grotto, a third of the rest gnawed
    private static final double REGION_SCALE = 420;
    private static final double GROTTO_SCALE = 260;
    private static final double GROTTO_FROM = 0.42;
    private static final double GNAWED_FROM = 0.125;
    private static final long GROTTO_OFFSET = 71003;
    private static final long REGION_OFFSET = 9000;
    private static final int WALL = 4; // blocks either side of a border

    private static final double PASSAGE_SCALE = 48;
    private static final double PASSAGE_FROM = 0.62;
    private static final long PASSAGE_OFFSET = 33517;
    private static final int PASSAGE_HEIGHT = 4;
    private static final int PASSAGE_CLEARANCE = 10; // above the floor and below the top of the deeps

    private static final double ISLAND_SCALE = 22;
    private static final double ISLAND_FROM = 0.35;
    private static final long ISLAND_OFFSET = 12907;

    private DeepThemes() {
    }

    public static Theme at(int x, int z, long seed) {
        if(BlendedNoise.noise((x + seed + GROTTO_OFFSET) / GROTTO_SCALE, (z + seed + GROTTO_OFFSET) / GROTTO_SCALE) > GROTTO_FROM) return Theme.GROTTO;
        if(BlendedNoise.noise((x + seed + REGION_OFFSET) / REGION_SCALE, (z + seed + REGION_OFFSET) / REGION_SCALE) > GNAWED_FROM) return Theme.GNAWED;
        return Theme.WET;
    }

    // true within a few blocks of the border between two themes
    public static boolean border(int x, int z, long seed) {
        Theme here = at(x, z, seed);
        return at(x + WALL, z, seed) != here || at(x - WALL, z, seed) != here || at(x, z + WALL, seed) != here || at(x, z - WALL, seed) != here;
    }

    // sparse slit through a border's rock wall, at a height that follows the column's own deeps
    public static boolean passage(int x, int y, int z, DepthBands.Edges edges, long seed) {
        if(BlendedNoise.noise((x + seed + PASSAGE_OFFSET) / PASSAGE_SCALE, (z + seed + PASSAGE_OFFSET) / PASSAGE_SCALE) <= PASSAGE_FROM) return false;
        int room = edges.top(DepthBands.Band.DEEPS) - edges.floor() - 2 * PASSAGE_CLEARANCE;
        if(room <= 0) return false;
        double level = BlendedNoise.noise((x + seed + PASSAGE_OFFSET) / (PASSAGE_SCALE * 2), (z + seed + PASSAGE_OFFSET + 500) / (PASSAGE_SCALE * 2)) * 0.5 + 0.5;
        int low = edges.floor() + PASSAGE_CLEARANCE + (int) (level * room);
        return y >= low && y < low + PASSAGE_HEIGHT;
    }

    // stone showing through the shallow water of the grotto
    public static boolean island(int x, int z, long seed) {
        return BlendedNoise.noise((x + seed + ISLAND_OFFSET) / ISLAND_SCALE, (z + seed + ISLAND_OFFSET) / ISLAND_SCALE) > ISLAND_FROM;
    }
}
