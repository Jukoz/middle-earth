package net.jukoz.me.world.biomes;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.minecraft.block.Blocks;

public class MEBiomeDataConfigs {
    // region Slopes
    // defines the surface blocks (4 blocks depth)
    private static final int MAX_ANGLE = 90;

    public static SlopeMap ashenDirt = new SlopeMap().addSlopeData(25, ModBlocks.ASHEN_DIRT)
            .addSlopeData(32, ModBlocks.ASHEN_GRAVEL)
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ASHEN_STONE.base());

    public static SlopeMap blueMountainsBase = new SlopeMap()
            .addSlopeData(30, Blocks.GRASS_BLOCK)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(34, Blocks.GRAVEL)
            .addSlopeData(37, Blocks.ANDESITE)
            .addSlopeData(40, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(45, Blocks.TUFF)
            .addSlopeData(47, StoneBlockSets.BLUE_TUFF.base())
            .addSlopeData(51, StoneBlockSets.GONLUIN.base())
            .addSlopeData(MAX_ANGLE, Blocks.COBBLED_DEEPSLATE);
    public static SlopeMap blueMountains = new SlopeMap()
            .addSlopeData(30, Blocks.GRASS_BLOCK)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(34, Blocks.GRAVEL)
            .addSlopeData(37, Blocks.ANDESITE)
            .addSlopeData(40, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(45, Blocks.TUFF)
            .addSlopeData(55, StoneBlockSets.BLUE_TUFF.base())
            .addSlopeData(67, StoneBlockSets.GONLUIN.base())
            .addSlopeData(MAX_ANGLE, Blocks.COBBLED_DEEPSLATE);
    public static SlopeMap blueMountainHighLands = new SlopeMap()
            .addSlopeData(23, Blocks.GRASS_BLOCK)
            .addSlopeData(28, Blocks.COARSE_DIRT)
            .addSlopeData(31, Blocks.GRAVEL)
            .addSlopeData(35, Blocks.ANDESITE)
            .addSlopeData(37, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(42, Blocks.TUFF)
            .addSlopeData(49, StoneBlockSets.BLUE_TUFF.base())
            .addSlopeData(61, StoneBlockSets.GONLUIN.base())
            .addSlopeData(MAX_ANGLE, Blocks.COBBLED_DEEPSLATE);
    public static SlopeMap blueMountainsPeaks = new SlopeMap()
            .addSlopeData(25, Blocks.SNOW_BLOCK)
            .addSlopeData(33, StoneBlockSets.SCHIST.base())
            .addSlopeData(35, Blocks.ANDESITE)
            .addSlopeData(42, Blocks.TUFF)
            .addSlopeData(50, StoneBlockSets.BLUE_TUFF.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.GONLUIN.base());

    public static SlopeMap emynMuil = new SlopeMap().addSlopeData(30, Blocks.GRASS_BLOCK)
            .addSlopeData(35, Blocks.COARSE_DIRT)
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(60, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.GRANITE);

    public static SlopeMap mordor = new SlopeMap().addSlopeData(30, StoneBlockSets.ASHEN_STONE.base())
            .addSlopeData(36, ModBlocks.ASH_BLOCK)
            .addSlopeData(44, Blocks.BASALT)
            .addSlopeData(MAX_ANGLE, Blocks.BLACKSTONE);
    public static SlopeMap mordorGrass = new SlopeMap().addSlopeData(25, Blocks.GRASS_BLOCK)
            .addSlopeData(32, ModBlocks.ASHEN_DIRT)
            .addSlopeData(37, ModBlocks.ASHEN_GRAVEL)
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ASHEN_STONE.base());

    public static SlopeMap greyMountains = new SlopeMap().addSlopeData(28, Blocks.GRASS_BLOCK)
            .addSlopeData(32, Blocks.COARSE_DIRT)
            .addSlopeData(45, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);
    public static SlopeMap greyMountainPeaks = new SlopeMap().addSlopeData(32, Blocks.SNOW_BLOCK)
            .addSlopeData(41, StoneBlockSets.SCHIST.base())
            .addSlopeData(47, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);
    public static SlopeMap mountGundabad = new SlopeMap().addSlopeData(28, Blocks.GRASS_BLOCK)
            .addSlopeData(32, Blocks.COARSE_DIRT)
            .addSlopeData(45, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);

    public static SlopeMap mountGundabadPeaks = new SlopeMap().addSlopeData(32, Blocks.SNOW_BLOCK)
            .addSlopeData(41, StoneBlockSets.SCHIST.base())
            .addSlopeData(47, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);

    public static SlopeMap mirkwoodMountains = new SlopeMap().addSlopeData(29, Blocks.GRASS_BLOCK)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(45, Blocks.STONE)
            .addSlopeData(54, StoneBlockSets.LIMESTONE.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.GALONN.base());
    public static SlopeMap mirkwoodMountainPeaks = new SlopeMap().addSlopeData(32, Blocks.SNOW_BLOCK)
            .addSlopeData(41, StoneBlockSets.SCHIST.base())
            .addSlopeData(47, Blocks.STONE)
            .addSlopeData(54, StoneBlockSets.LIMESTONE.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.GALONN.base());

    public static SlopeMap lonelyMountainBase = new SlopeMap().addSlopeData(33, Blocks.GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(44, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.DEEPSLATE);
    public static SlopeMap lonelyMountain = new SlopeMap().addSlopeData(27, Blocks.GRASS_BLOCK)
            .addSlopeData(32, Blocks.COARSE_DIRT)
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(60, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.DEEPSLATE);
    public static SlopeMap lonelyMountainPeak = new SlopeMap().addSlopeData(24, Blocks.SNOW_BLOCK)
            .addSlopeData(36, StoneBlockSets.SCHIST.base())
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(60, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.DEEPSLATE);

    public static SlopeMap ironHills = new SlopeMap().addSlopeData(32, Blocks.GRASS_BLOCK)
            .addSlopeData(35, Blocks.COARSE_DIRT)
            .addSlopeData(38, Blocks.STONE)
            .addSlopeData(40, Blocks.TUFF)
            .addSlopeData(54, StoneBlockSets.IRONSTONE.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.HEMATITE.base());

    public static SlopeMap limeStoneHills = new SlopeMap().addSlopeData(34, Blocks.GRASS_BLOCK)
            .addSlopeData(37, Blocks.COARSE_DIRT)
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, StoneBlockSets.LIMESTONE.base());
    public static SlopeMap limeStoneMountains = new SlopeMap().addSlopeData(13, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(32, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, StoneBlockSets.LIMESTONE.base());

    public static SlopeMap mistyMountainsBase = new SlopeMap().addSlopeData(13, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(22, Blocks.TUFF)
            .addSlopeData(30, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(38, Blocks.STONE)
            .addSlopeData(50, StoneBlockSets.GNEISS.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ZIGILABAN.base());
    public static SlopeMap mistyMountains = new SlopeMap().addSlopeData(14, ModBlocks.SNOWY_GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(22, Blocks.TUFF)
            .addSlopeData(30, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(38, Blocks.STONE)
            .addSlopeData(50, StoneBlockSets.GNEISS.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ZIGILABAN.base());
    public static SlopeMap mistiesPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSets.SCHIST.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ZIGILABAN.base());
    public static SlopeMap caradhras = new SlopeMap().addSlopeData(12, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(21, Blocks.TUFF)
            .addSlopeData(29, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(37, Blocks.STONE)
            .addSlopeData(49, Blocks.GRANITE)
            .addSlopeData(MAX_ANGLE, StoneBlockSets.HEMATITE.base());
    public static SlopeMap caradhrasPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSets.SCHIST.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.HEMATITE.base());
    public static SlopeMap celebdil = new SlopeMap().addSlopeData(12, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(21, Blocks.TUFF)
            .addSlopeData(29, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(37, Blocks.STONE)
            .addSlopeData(48, StoneBlockSets.GNEISS.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ZIGILABAN.base());
    public static SlopeMap celebdilPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSets.SCHIST.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.ZIGILABAN.base());
    public static SlopeMap fanuidhol = new SlopeMap().addSlopeData(12, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(21, Blocks.TUFF)
            .addSlopeData(29, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(37, Blocks.DRIPSTONE_BLOCK)
            .addSlopeData(49, StoneBlockSets.LIMESTONE.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.GALONN.base());
    public static SlopeMap fanuidholPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSets.SCHIST.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.GALONN.base());

    public static SlopeMap mordorMountains = new SlopeMap().addSlopeData(25, ModBlocks.ASHEN_DIRT)
            .addSlopeData(30, ModBlocks.ASHEN_GRAVEL)
            .addSlopeData(41, Blocks.SMOOTH_BASALT)
            .addSlopeData(55, StoneBlockSets.ASHEN_STONE.base())
            .addSlopeData(MAX_ANGLE, Blocks.BLACKSTONE);
    public static SlopeMap mordorMountainsPeaks = new SlopeMap().addSlopeData(26, ModBlocks.ASHEN_GRAVEL)
            .addSlopeData(40, Blocks.SMOOTH_BASALT)
            .addSlopeData(54, StoneBlockSets.ASHEN_STONE.base())
            .addSlopeData(MAX_ANGLE, Blocks.BLACKSTONE);

    public static SlopeMap stoneHills = new SlopeMap().addSlopeData(33, Blocks.GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(60, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TUFF);

    public static SlopeMap whiteMountains = new SlopeMap()
            .addSlopeData(21f, Blocks.GRASS_BLOCK)
            .addSlopeData(24, Blocks.COARSE_DIRT)
            .addSlopeData(32, Blocks.STONE)
            .addSlopeData(39, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(48, Blocks.DIORITE)
            .addSlopeData(MAX_ANGLE, Blocks.CALCITE);
    public static SlopeMap whitePeaks = new SlopeMap()
            .addSlopeData(24, Blocks.SNOW_BLOCK)
            .addSlopeData(36, Blocks.DEEPSLATE)
            .addSlopeData(41, Blocks.TUFF)
            .addSlopeData(46, StoneBlockSets.DOLOMITE.base())
            .addSlopeData(52, Blocks.DIORITE)
            .addSlopeData(MAX_ANGLE, Blocks.CALCITE);

    public static SlopeMap grassPlains = new SlopeMap().addSlopeData(25, Blocks.GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap snowyPlains = new SlopeMap().addSlopeData(25, ModBlocks.SNOWY_GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap sandShores = new SlopeMap().addSlopeData(27, Blocks.SAND)
            .addSlopeData(30, Blocks.SANDSTONE)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap whiteSandShores = new SlopeMap().addSlopeData(27, ModBlocks.WHITE_SAND)
            .addSlopeData(30, Blocks.SANDSTONE)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap river = new SlopeMap().addSlopeData(30, ModBlocks.RIVER_SAND)
            .addSlopeData(36, Blocks.DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);

    public static SlopeMap gulfOfLhunShoresLayers = new SlopeMap()
            .addSlopeData(26, ModBlocks.WHITE_SAND)
            .addSlopeData(28, Blocks.SAND)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.CLAY);

    public static SlopeMap gulfOfLhunShoreCliffsLayers = new SlopeMap()
            .addSlopeData(26, Blocks.GRASS_BLOCK)
            .addSlopeData(28, Blocks.GRAVEL)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(38, Blocks.COARSE_DIRT)
            .addSlopeData(47, StoneBlockSets.GALONN.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSets.LIMESTONE.base());

    public static SlopeMap forodwaith = new SlopeMap().addSlopeData(30, Blocks.SNOW_BLOCK)
            .addSlopeData(MAX_ANGLE, StoneBlockSets.SCHIST.base());

    public static SlopeMap mud = new SlopeMap().addSlopeData(25, Blocks.MUD)
            .addSlopeData(32, Blocks.DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap nearHarad = new SlopeMap().addSlopeData(32, Blocks.GRASS_BLOCK)
            .addSlopeData(36, ModBlocks.DRY_DIRT)
            .addSlopeData(44, Blocks.SANDSTONE)
            .addSlopeData(56, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TERRACOTTA);
    public static SlopeMap harad = new SlopeMap().addSlopeData(32, Blocks.SAND)
            .addSlopeData(45, Blocks.SANDSTONE)
            .addSlopeData(60, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TERRACOTTA);
    public static SlopeMap ocean = new SlopeMap().addSlopeData(30, Blocks.GRAVEL)
            .addSlopeData(36, ModBlocks.RIVER_SAND)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap pond = new SlopeMap().addSlopeData(20, ModBlocks.MIRE)
            .addSlopeData(23, Blocks.GRASS_BLOCK)
            .addSlopeData(27, ModBlocks.RIVER_SAND)
            .addSlopeData(33, Blocks.GRAVEL)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap beach = new SlopeMap().addSlopeData(30, Blocks.SAND)
            .addSlopeData(36, ModBlocks.RIVER_SAND)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    // endregion

    // region Blocks Layering
    // excludes the surface blocks since it's handled by Slopes blocks

    public static BlocksLayeringData stoneLayers = new BlocksLayeringData().addLayerData(1.0f, Blocks.STONE);

    public static BlocksLayeringData ashenStoneLayers = new BlocksLayeringData().addLayerData(1.0f, StoneBlockSets.ASHEN_STONE.base());

    public static BlocksLayeringData frozenLayers = new BlocksLayeringData().addLayerData(1.0f, Blocks.STONE);

    public static BlocksLayeringData gonluinLayers = new BlocksLayeringData().addLayerData(0.4f, Blocks.STONE)
            .addLayerData(0.6f, StoneBlockSets.GONLUIN.base());

    public static BlocksLayeringData limeStoneLayers = new BlocksLayeringData().addLayerData(0.37f, Blocks.STONE)
            .addLayerData(0.63f, StoneBlockSets.LIMESTONE.base());

    public static BlocksLayeringData sandstoneLayers = new BlocksLayeringData().addLayerData(0.9f, Blocks.STONE)
            .addLayerData(0.1f, Blocks.SANDSTONE);

    public static BlocksLayeringData gondorLayers = new BlocksLayeringData().addLayerData(0.3f, Blocks.STONE)
            .addLayerData(0.15f, Blocks.DIORITE)
            .addLayerData(0.55f, Blocks.CALCITE);

    public static BlocksLayeringData ironhills = new BlocksLayeringData().addLayerData(0.35f, Blocks.STONE)
            .addLayerData(0.65f, StoneBlockSets.IRONSTONE.base());

    public static BlocksLayeringData greyMountainsLayers = new BlocksLayeringData().addLayerData(0.58f, Blocks.STONE)
            .addLayerData(0.02f, Blocks.SMOOTH_BASALT)
            .addLayerData(0.4f, StoneBlockSets.ASHEN_STONE.base());

    public static BlocksLayeringData mistyMountainsLayers = new BlocksLayeringData().addLayerData(1.0f, Blocks.STONE);

    // endregion

    // region Biome Generation Data
    // Expansion weights
    private static final byte[] RIVER_WEIGHT = {2, 2};
    private static final byte[] OCEAN_WEIGHT = {2, 3};
    private static final byte[] MOUNTAIN_WEIGHT = {1, 4};
    private static final byte[] LAND_WEIGHT = {1, 4};

    // Noise Modifiers
    private static final double WATER_NOISE_MODIFIER = 0.4f;
    private static final double PLAINS_NOISE_MODIFIER = 0.33f;
    private static final double FOOTHILL_NOISE_MODIFIER = 0.6f;
    private static final double MOUNTAIN_NOISE_MODIFIER = 0.82f;
    private static final double MOUNTAIN_PEAKS_NOISE_MODIFIER = 1.5f;

    // Height Base Modifiers
    private static final double WATER_HEIGHT_MODIFIER = 0.2f;
    private static final double LAND_HEIGHT_MODIFIER = 0.3f;
    private static final double FOOTHILL_HEIGHT_MODIFIER = 0.38f;
    private static final double MOUNTAIN_HEIGHT_MODIFIER = 0.46f;
    private static final double MOUNTAIN_PEAKS_HEIGHT_MODIFIER = 0.55f;

    public static BiomeGenerationData landModifier = new BiomeGenerationData().expansionWeight(LAND_WEIGHT).noiseModifier(0.46f).heightModifier(0.33f);
    public static BiomeGenerationData plainsModifier = new BiomeGenerationData().expansionWeight(LAND_WEIGHT).noiseModifier(PLAINS_NOISE_MODIFIER).heightModifier(LAND_HEIGHT_MODIFIER);
    public static BiomeGenerationData riverModifier = new BiomeGenerationData().expansionWeight(RIVER_WEIGHT).noiseModifier(WATER_NOISE_MODIFIER).heightModifier(WATER_HEIGHT_MODIFIER);
    public static BiomeGenerationData smallRiverModifier = new BiomeGenerationData().expansionWeight(RIVER_WEIGHT).noiseModifier(0.05f).heightModifier(0.05f);
    public static BiomeGenerationData oceanModifier = new BiomeGenerationData().expansionWeight(OCEAN_WEIGHT).noiseModifier(WATER_NOISE_MODIFIER).heightModifier(WATER_HEIGHT_MODIFIER);
    public static BiomeGenerationData longLakeModifier = new BiomeGenerationData().expansionWeight(OCEAN_WEIGHT).noiseModifier(WATER_NOISE_MODIFIER).heightModifier(WATER_HEIGHT_MODIFIER);
    public static BiomeGenerationData foothillModifier = new BiomeGenerationData().expansionWeight(MOUNTAIN_WEIGHT).noiseModifier(FOOTHILL_NOISE_MODIFIER).heightModifier(FOOTHILL_HEIGHT_MODIFIER);
    public static BiomeGenerationData shireModifier = new BiomeGenerationData().expansionWeight(LAND_WEIGHT).noiseModifier(FOOTHILL_NOISE_MODIFIER).heightModifier(FOOTHILL_HEIGHT_MODIFIER);
    public static BiomeGenerationData mountainModifier = new BiomeGenerationData().expansionWeight(MOUNTAIN_WEIGHT).noiseModifier(MOUNTAIN_NOISE_MODIFIER).heightModifier(MOUNTAIN_HEIGHT_MODIFIER);
    public static BiomeGenerationData woodlandModifier = new BiomeGenerationData().expansionWeight(LAND_WEIGHT).noiseModifier(0.7f).heightModifier(0.4f);
    public static BiomeGenerationData mordorMountainModifier = new BiomeGenerationData().expansionWeight(MOUNTAIN_WEIGHT).noiseModifier(1.47f).heightModifier(MOUNTAIN_HEIGHT_MODIFIER);
    public static BiomeGenerationData bmModifier = new BiomeGenerationData().expansionWeight(MOUNTAIN_WEIGHT).noiseModifier(0.7f).heightModifier(0.46f);
    public static BiomeGenerationData bmPeaksModifier = new BiomeGenerationData().expansionWeight(MOUNTAIN_WEIGHT).noiseModifier(1.47f).heightModifier(0.6f);
    public static BiomeGenerationData mirkwoodModifier = new BiomeGenerationData().expansionWeight(LAND_WEIGHT).noiseModifier(0.51f).heightModifier(0.4f);
    public static BiomeGenerationData emynMuilModifier = new BiomeGenerationData().expansionWeight(LAND_WEIGHT).noiseModifier(1.2f).heightModifier(0.67f);
}
