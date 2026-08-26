package net.sevenstars.middleearth.world.biomes;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.StoneBlockSetRegistryME;
import net.minecraft.block.Blocks;

public class MEBiomeDataConfigs {
    // region Slopes
    // defines the surface blocks (4 blocks depth)
    private static final int MAX_ANGLE = 90;

    public static SlopeMap ashenDirt = new SlopeMap().addSlopeData(25, BlockRegistryME.ASHEN_DIRT)
            .addSlopeData(32, BlockRegistryME.ASHEN_GRAVEL)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base());

    public static SlopeMap blueMountainsBase = new SlopeMap()
            .addSlopeData(30, BlockRegistryME.PEAT_GRASS_BLOCK)
            .addSlopeData(33, BlockRegistryME.COARSE_PEAT)
            .addSlopeData(34, Blocks.GRAVEL)
            .addSlopeData(37, Blocks.ANDESITE)
            .addSlopeData(40, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(45, Blocks.TUFF)
            .addSlopeData(47, StoneBlockSetRegistryME.BLUE_TUFF_SET.baseBlocks.base())
            .addSlopeData(51, StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base());
    public static SlopeMap blueMountains = new SlopeMap()
            .addSlopeData(30, BlockRegistryME.PEAT_GRASS_BLOCK)
            .addSlopeData(33, BlockRegistryME.COARSE_PEAT)
            .addSlopeData(34, Blocks.GRAVEL)
            .addSlopeData(37, Blocks.ANDESITE)
            .addSlopeData(40, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(45, Blocks.TUFF)
            .addSlopeData(55, StoneBlockSetRegistryME.BLUE_TUFF_SET.baseBlocks.base())
            .addSlopeData(67, StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base());
    public static SlopeMap blueMountainHighLands = new SlopeMap()
            .addSlopeData(23, BlockRegistryME.PEAT_GRASS_BLOCK)
            .addSlopeData(28, BlockRegistryME.COARSE_PEAT)
            .addSlopeData(31, Blocks.GRAVEL)
            .addSlopeData(35, Blocks.ANDESITE)
            .addSlopeData(37, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(42, Blocks.TUFF)
            .addSlopeData(49, StoneBlockSetRegistryME.BLUE_TUFF_SET.baseBlocks.base())
            .addSlopeData(61, StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base());
    public static SlopeMap blueMountainsPeaks = new SlopeMap()
            .addSlopeData(25, Blocks.SNOW_BLOCK)
            .addSlopeData(33, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(35, Blocks.ANDESITE)
            .addSlopeData(42, Blocks.TUFF)
            .addSlopeData(50, StoneBlockSetRegistryME.BLUE_TUFF_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base());

    public static SlopeMap emynMuil = new SlopeMap().addSlopeData(30, Blocks.GRASS_BLOCK)
            .addSlopeData(35, Blocks.COARSE_DIRT)
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(60, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.GRANITE);

    public static SlopeMap mordor = new SlopeMap().addSlopeData(30, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base())
            .addSlopeData(36, BlockRegistryME.ASH_BLOCK)
            .addSlopeData(44, Blocks.BASALT)
            .addSlopeData(MAX_ANGLE, Blocks.BLACKSTONE);
    public static SlopeMap mordorGrass = new SlopeMap().addSlopeData(25, BlockRegistryME.PEAT_GRASS_BLOCK)
            .addSlopeData(32, BlockRegistryME.ASHEN_DIRT)
            .addSlopeData(37, BlockRegistryME.ASHEN_GRAVEL)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base());

    public static SlopeMap greyMountains = new SlopeMap().addSlopeData(21, Blocks.GRAVEL)
            .addSlopeData(28, Blocks.STONE)
            .addSlopeData(40, Blocks.TUFF)
            .addSlopeData(54, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);
    public static SlopeMap greyMountainPeaks = new SlopeMap().addSlopeData(32, Blocks.SNOW_BLOCK)
            .addSlopeData(41, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(47, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);
    public static SlopeMap mountGundabad = new SlopeMap().addSlopeData(28, Blocks.GRASS_BLOCK)
            .addSlopeData(32, Blocks.COARSE_DIRT)
            .addSlopeData(45, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);

    public static SlopeMap mountGundabadPeaks = new SlopeMap().addSlopeData(32, Blocks.SNOW_BLOCK)
            .addSlopeData(41, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(47, Blocks.STONE)
            .addSlopeData(56, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.SMOOTH_BASALT);

    public static SlopeMap mirkwoodMountains = new SlopeMap().addSlopeData(29, Blocks.GRASS_BLOCK)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(45, Blocks.STONE)
            .addSlopeData(54, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base());
    public static SlopeMap mirkwoodMountainPeaks = new SlopeMap().addSlopeData(32, Blocks.SNOW_BLOCK)
            .addSlopeData(41, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(47, Blocks.STONE)
            .addSlopeData(54, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base());

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
            .addSlopeData(36, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(60, Blocks.TUFF)
            .addSlopeData(MAX_ANGLE, Blocks.DEEPSLATE);

    public static SlopeMap ironHills = new SlopeMap().addSlopeData(32, Blocks.GRASS_BLOCK)
            .addSlopeData(35, Blocks.COARSE_DIRT)
            .addSlopeData(38, Blocks.STONE)
            .addSlopeData(40, Blocks.TUFF)
            .addSlopeData(51, StoneBlockSetRegistryME.IRONSTONE_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.HEMATITE_SET.baseBlocks.base());

    public static SlopeMap limeStoneHills = new SlopeMap().addSlopeData(31, BlockRegistryME.LOAM_GRASS_BLOCK)
            .addSlopeData(35, BlockRegistryME.COARSE_LOAM)
            .addSlopeData(41, StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base())
            .addSlopeData(48, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base());
    public static SlopeMap limeStoneMountains = new SlopeMap().addSlopeData(13, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(32, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base());

    public static SlopeMap mistyMountainsBase = new SlopeMap().addSlopeData(13, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(22, Blocks.TUFF)
            .addSlopeData(30, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(38, Blocks.STONE)
            .addSlopeData(50, StoneBlockSetRegistryME.GNEISS_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.base());
    public static SlopeMap mistyMountains = new SlopeMap().addSlopeData(14, BlockRegistryME.SNOWY_GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(22, Blocks.TUFF)
            .addSlopeData(30, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(38, Blocks.STONE)
            .addSlopeData(50, StoneBlockSetRegistryME.GNEISS_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.base());
    public static SlopeMap mistiesPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.base());
    public static SlopeMap caradhras = new SlopeMap().addSlopeData(12, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(21, Blocks.TUFF)
            .addSlopeData(29, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(37, Blocks.STONE)
            .addSlopeData(49, Blocks.GRANITE)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.HEMATITE_SET.baseBlocks.base());
    public static SlopeMap caradhrasPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.HEMATITE_SET.baseBlocks.base());
    public static SlopeMap celebdil = new SlopeMap().addSlopeData(12, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(21, StoneBlockSetRegistryME.GNEISS_SET.baseBlocks.base())
            .addSlopeData(35, StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.base())
            .addSlopeData(48, StoneBlockSetRegistryME.IZHERABAN_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base());
    public static SlopeMap celebdilPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base());
    public static SlopeMap fanuidhol = new SlopeMap().addSlopeData(12, Blocks.GRASS_BLOCK)
            .addSlopeData(16, Blocks.COARSE_DIRT)
            .addSlopeData(21, Blocks.TUFF)
            .addSlopeData(29, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(39, StoneBlockSetRegistryME.IZHERABAN_SET.baseBlocks.base())
            .addSlopeData(48, StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base());
    public static SlopeMap fanuidholPeaks = new SlopeMap().addSlopeData(36, Blocks.SNOW_BLOCK)
            .addSlopeData(45, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base());

    public static SlopeMap mordorMountains = new SlopeMap().addSlopeData(25, BlockRegistryME.ASHEN_DIRT)
            .addSlopeData(30, BlockRegistryME.ASHEN_GRAVEL)
            .addSlopeData(41, Blocks.SMOOTH_BASALT)
            .addSlopeData(55, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, Blocks.BLACKSTONE);
    public static SlopeMap mordorMountainsPeaks = new SlopeMap().addSlopeData(26, BlockRegistryME.ASHEN_GRAVEL)
            .addSlopeData(40, Blocks.SMOOTH_BASALT)
            .addSlopeData(54, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, Blocks.BLACKSTONE);

    public static SlopeMap stoneHills = new SlopeMap().addSlopeData(33, Blocks.GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(60, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TUFF);

    public static SlopeMap whiteMountains = new SlopeMap()
            .addSlopeData(21f, Blocks.GRASS_BLOCK)
            .addSlopeData(24, Blocks.COARSE_DIRT)
            .addSlopeData(32, Blocks.STONE)
            .addSlopeData(39, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(48, Blocks.DIORITE)
            .addSlopeData(MAX_ANGLE, Blocks.CALCITE);
    public static SlopeMap whitePeaks = new SlopeMap()
            .addSlopeData(24, Blocks.SNOW_BLOCK)
            .addSlopeData(36, Blocks.DEEPSLATE)
            .addSlopeData(41, Blocks.TUFF)
            .addSlopeData(46, StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
            .addSlopeData(52, Blocks.DIORITE)
            .addSlopeData(MAX_ANGLE, Blocks.CALCITE);

    public static SlopeMap dunland = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.LOAM_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.COARSE_LOAM)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base());

    public static SlopeMap trollshaws = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.PEAT_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.COARSE_PEAT)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base());

    public static SlopeMap dolGuldur = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.LOAM_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.COARSE_LOAM)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base());

    public static SlopeMap chalkPlains = new SlopeMap()
            .addSlopeData(21, BlockRegistryME.CHALKSOIL_GRASS_BLOCK)
            .addSlopeData(24, BlockRegistryME.COARSE_CHALKSOIL)
            .addSlopeData(27, StoneBlockSetRegistryME.CHALK_SET.oldBlocks.base())
            .addSlopeData(41, StoneBlockSetRegistryME.CHALK_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, Blocks.CALCITE);
    public static SlopeMap grassPlains = new SlopeMap()
            .addSlopeData(25, Blocks.GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap loamPlains = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.LOAM_GRASS_BLOCK)
            .addSlopeData(35, BlockRegistryME.COARSE_LOAM)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap peatPlains = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.PEAT_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.COARSE_PEAT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap siltPlains = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.SILT_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.COARSE_SILT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap coarseLoam = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.COARSE_LOAM)
            .addSlopeData(36, BlockRegistryME.FOUL_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap snowyPlains = new SlopeMap()
            .addSlopeData(25, BlockRegistryME.SNOWY_GRASS_BLOCK)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap gravelPlains = new SlopeMap()
            .addSlopeData(25, Blocks.GRAVEL)
            .addSlopeData(35, Blocks.COBBLESTONE)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap sandShores = new SlopeMap()
            .addSlopeData(27, Blocks.SAND)
            .addSlopeData(30, Blocks.SANDSTONE)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap whiteSandShores = new SlopeMap()
            .addSlopeData(27, BlockRegistryME.WHITE_SAND)
            .addSlopeData(30, Blocks.SANDSTONE)
            .addSlopeData(36, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap river = new SlopeMap()
            .addSlopeData(30, BlockRegistryME.RIVER_SAND)
            .addSlopeData(36, Blocks.DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);

    public static SlopeMap gulfOfLhunShoresLayers = new SlopeMap()
            .addSlopeData(26, BlockRegistryME.WHITE_SAND)
            .addSlopeData(28, Blocks.SAND)
            .addSlopeData(33, Blocks.COARSE_DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.CLAY);

    public static SlopeMap gulfOfLhunShoreCliffsLayers = new SlopeMap()
            .addSlopeData(26, BlockRegistryME.CHALKSOIL_GRASS_BLOCK)
            .addSlopeData(28, Blocks.GRAVEL)
            .addSlopeData(33, BlockRegistryME.COARSE_CHALKSOIL)
            .addSlopeData(38, BlockRegistryME.COARSE_CHALKSOIL)
            .addSlopeData(47, StoneBlockSetRegistryME.CHALK_SET.baseBlocks.base())
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.CHALK_SET.oldBlocks.base());

    public static SlopeMap forodwaith = new SlopeMap().addSlopeData(30, Blocks.SNOW_BLOCK)
            .addSlopeData(MAX_ANGLE, StoneBlockSetRegistryME.SCHIST_SET.baseBlocks.base());

    public static SlopeMap mud = new SlopeMap().addSlopeData(25, Blocks.MUD)
            .addSlopeData(32, Blocks.DIRT)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap nearHarad = new SlopeMap().addSlopeData(32, BlockRegistryME.SILT_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.DRY_DIRT)
            .addSlopeData(44, Blocks.SANDSTONE)
            .addSlopeData(56, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TERRACOTTA);
    public static SlopeMap corsairCoasts = new SlopeMap().addSlopeData(32, BlockRegistryME.CHALKSOIL_GRASS_BLOCK)
            .addSlopeData(36, BlockRegistryME.COARSE_CHALKSOIL)
            .addSlopeData(44, Blocks.SANDSTONE)
            .addSlopeData(56, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TERRACOTTA);
    public static SlopeMap harad = new SlopeMap().addSlopeData(32, Blocks.SAND)
            .addSlopeData(45, Blocks.SANDSTONE)
            .addSlopeData(60, Blocks.STONE)
            .addSlopeData(MAX_ANGLE, Blocks.TERRACOTTA);
    public static SlopeMap ocean = new SlopeMap().addSlopeData(30, Blocks.GRAVEL)
            .addSlopeData(36, BlockRegistryME.RIVER_SAND)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap pond = new SlopeMap().addSlopeData(20, BlockRegistryME.MIRE)
            .addSlopeData(23, Blocks.GRASS_BLOCK)
            .addSlopeData(27, BlockRegistryME.RIVER_SAND)
            .addSlopeData(33, Blocks.GRAVEL)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    public static SlopeMap beach = new SlopeMap().addSlopeData(30, BlockRegistryME.SILT)
            .addSlopeData(37, Blocks.SANDSTONE)
            .addSlopeData(MAX_ANGLE, Blocks.STONE);
    // endregion

    // region Blocks Layering
    // excludes the surface blocks since it's handled by Slopes blocks

    public static BlocksLayeringData stoneLayers = new BlocksLayeringData()
            .addLayerData(0.33f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.67f, Blocks.STONE);

    public static BlocksLayeringData stoneGabbroLayers = new BlocksLayeringData()
            .addLayerData(0.30f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.1f, StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base())
            .addLayerData(0.60f, Blocks.STONE);

    public static BlocksLayeringData ashenStoneLayers = new BlocksLayeringData().addLayerData(1.0f, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base());

    public static BlocksLayeringData frozenLayers = new BlocksLayeringData().addLayerData(1.0f, Blocks.STONE);

    public static BlocksLayeringData gonluinLayers = new BlocksLayeringData()
            .addLayerData(0.34f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.1f, Blocks.STONE)
            .addLayerData(0.01f, StoneBlockSetRegistryME.BLUE_TUFF_SET.baseBlocks.base())
            .addLayerData(0.55f, StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base());

    public static BlocksLayeringData chalkLayers = new BlocksLayeringData()
            .addLayerData(0.34f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.1f, Blocks.STONE)
            .addLayerData(0.03f, StoneBlockSetRegistryME.CALCITE_SET.baseBlocks.base())
            .addLayerData(0.53f, StoneBlockSetRegistryME.CHALK_SET.baseBlocks.base());

    public static BlocksLayeringData limeStoneLayers = new BlocksLayeringData()
            .addLayerData(0.34f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.1f, Blocks.STONE)
            .addLayerData(0.03f, StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base())
            .addLayerData(0.53f, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base());

    public static BlocksLayeringData limeStoneTravertineLayers = new BlocksLayeringData()
            .addLayerData(0.35f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.1f, Blocks.STONE)
            .addLayerData(0.2f, StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base())
            .addLayerData(0.25f, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base())
            .addLayerData(0.1f, StoneBlockSetRegistryME.TRAVERTINE_SET.baseBlocks.base());

    public static BlocksLayeringData sandstoneLayers = new BlocksLayeringData()
            .addLayerData(0.1f, Blocks.SANDSTONE)
            .addLayerData(0.85f, Blocks.STONE)
            .addLayerData(0.05f, Blocks.SANDSTONE);

    public static BlocksLayeringData sandstoneTravertineLayers = new BlocksLayeringData()
            .addLayerData(0.1f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.55f, Blocks.STONE)
            .addLayerData(0.1f, StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base())
            .addLayerData(0.18f, StoneBlockSetRegistryME.TRAVERTINE_SET.baseBlocks.base())
            .addLayerData(0.07f, Blocks.SANDSTONE);

    public static BlocksLayeringData gondorLayers = new BlocksLayeringData()
            .addLayerData(0.55f, Blocks.CALCITE)
            .addLayerData(0.15f, Blocks.DIORITE)
            .addLayerData(0.3f, Blocks.STONE);

    public static BlocksLayeringData whiteMountainsLayers = new BlocksLayeringData()
            .addLayerData(0.3f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.35f, Blocks.CALCITE)
            .addLayerData(0.1f, Blocks.DIORITE)
            .addLayerData(0.25f, Blocks.STONE);

    public static BlocksLayeringData ironhills = new BlocksLayeringData()
            .addLayerData(0.55f, StoneBlockSetRegistryME.IRONSTONE_SET.baseBlocks.base())
            .addLayerData(0.15f, StoneBlockSetRegistryME.HEMATITE_SET.baseBlocks.base())
            .addLayerData(0.3f, Blocks.STONE);

    public static BlocksLayeringData greyMountainsLayers = new BlocksLayeringData()
            .addLayerData(0.03f, Blocks.SMOOTH_BASALT)
            .addLayerData(0.3f, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base())
            .addLayerData(0.02f, Blocks.SMOOTH_BASALT)
            .addLayerData(0.35f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.3f, Blocks.STONE);

    public static BlocksLayeringData mistyMountainsLayers = new BlocksLayeringData()
            .addLayerData(0.6f, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base())
            .addLayerData(0.4f, Blocks.STONE);

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
