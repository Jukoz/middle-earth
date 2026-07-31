package net.sevenstars.middleearth.world.chunkgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.phys.Vec2;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.utils.noises.BlendedNoise;
import net.sevenstars.middleearth.utils.noises.SimplexNoise;
import net.sevenstars.middleearth.world.biomes.BlocksLayeringData;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.sevenstars.middleearth.world.biomes.surface.*;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.world.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.world.map.MiddleEarthMapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MiddleEarthChunkGenerator extends ChunkGenerator {
    public static final int MEDGON_LEVEL = -32;
    public static final int NURGON_LEVEL = 0;
    public static final int DEEPSLATE_LEVEL = 32;
    public static final int STONE_HEIGHT = 36;
    public static final int WATER_HEIGHT = 64;
    public static final int LAVA_HEIGHT = -60;
    public static final int HEIGHT = 27 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;
    public static final int CAVE_NOISE = 5;

    MiddleEarthMapUtils middleEarthMapUtils;
    MiddleEarthMapRuntime middleEarthMapRuntime;
    private final ModBiomeSource middleEarthBiomeSource;

    public static final int mapMultiplier = (int) Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION + MiddleEarthMapConfigs.PIXEL_WEIGHT - 2);
    public static final Vec2 mountDoom = new Vec2(2131.5f, 1715.2f).scale(mapMultiplier);
    private static final int CAVE_STRETCH_H = 60;
    private static final int SPAGHETTI_CAVE_STRETCH_H = 90;
    private static final int CAVE_STRETCH_V = 50;
    private static final int CHUNK_WIDTH = 16;
    private static final int QUARTS_PER_CHUNK = QuartPos.fromBlock(CHUNK_WIDTH - 1) + 1;
    private static final int FUZZY_QUART_WIDTH = QUARTS_PER_CHUNK + 2;
    private static final int FUZZY_QUART_Y_SAMPLES = 2;
    private static final int TERRAIN_SLOPE_OFFSET = 3;
    private static final int HEIGHT_CACHE_SIZE = CHUNK_WIDTH + TERRAIN_SLOPE_OFFSET;
    // Placement anchors use the upstream generator minimum; terrain uses the dimension bounds.
    private static final int MIN_Y = -4;
    private static final int TERRAIN_MIN_Y = -64;
    private static final int GEN_DEPTH = 384;
    private static final VerticalNoiseCoordinates DEFAULT_VERTICAL_NOISE =
            new VerticalNoiseCoordinates(TERRAIN_MIN_Y, TERRAIN_MIN_Y + GEN_DEPTH);

    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                            .forGetter(MiddleEarthChunkGenerator::getBiomeSource)
            )
                    .apply(instance, instance.stable(MiddleEarthChunkGenerator::new)));

    public static List<Holder<Biome>> createBiomeList(HolderGetter<Biome> biomeRegistry) {
        return new ArrayList<>(Arrays.asList(
                    biomeRegistry.getOrThrow(MEBiomeKeys.OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANDUIN_VALES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANDUIN_VALES_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN_RIVERSIDE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BARROW_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELERIAND_ISLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_BEACH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLACKROOT_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLACKROOT_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_HIGH_LANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BROWN_LANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CARADHRAS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CARADHRAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CARADHRAS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CELEBDIL_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CELEBDIL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CELEBDIL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CORSAIR_COASTS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_MEADOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_CITY),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DAGORLAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_RIVERSIDE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_MIRKWOOD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_MIRKWOOD_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_ANDUIN_VALES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DEAD_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DEAD_MARSHES_WATER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DESOLATED_LANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOL_GULDUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOL_GULDUR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION_LAVENDER_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EAST_BIGHT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_NURN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_RHOVANION_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_CLIFFS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ETHIR_ANDUIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ETHIR_ANDUIN_RIVER_DELTA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREAT_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GUNDABAD_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GUNDABAD_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FORODWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_SHORES_CLIFFS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH_ASHEN_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH_DELTA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_ASHEN_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD_DESERT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HILLS_OF_EVENDIM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_RHOVANION_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_RHOVANION_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ISENGARD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ISENGARD_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_WASTES_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LAMEDON),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LAMEDON_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_HIDDEN_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_MEADOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_LAKE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_LAKE_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LORIEN_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_CHERRY_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_RED),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_GREEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OASIS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MANGROVE_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MINHIRIATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MINHIRIATH_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_SWAMP),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_ASHEN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERED_LITHUI),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERED_LITHUI_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERED_LITHUI_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_DOOM_PIT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_DOOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NAN_CURUNIR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_RAPIDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NINDALF),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTH_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_DUNLAND_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_WASTELANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_EDGE_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_SEA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OCEAN_COAST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR_COLD_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OSGILIATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.PELENNOR_FIELDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.PELENNOR_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RIVER_RUNNING),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN_HIDDEN_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SEA_OF_RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SARN_GEBIR_WILDLANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SARN_GEBIR_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHEAST_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHEAST_RHOVANION_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRUWAITH_IAUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_FOROCHEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EPHEL_DUATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EPHEL_DUATH_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EPHEL_DUATH_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_ANGLE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_OLD_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WOLD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WOLD_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WHITE_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOLFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOROGWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TROLLSHAWS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UDUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UMBAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UMBAR_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WASTE_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WEBBED_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WEBBED_DARK_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WITHERED_HEATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_REALM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.AUTUMN_WOODLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_HILLS),

                    biomeRegistry.getOrThrow(MEBiomeKeys.BASIC_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LUSH_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRIPSTONE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOLOMITE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GALONN_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GILDED_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IZHERABAN_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LIMESTONE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNTAIN_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MUD_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FUNGUS_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MITHRIL_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BASALT_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MAGMA_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRY_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ICE_CAVE)
        ));
    }

    public MiddleEarthChunkGenerator(HolderGetter<Biome> biomeRegistry) {
        this(new ModBiomeSource(createBiomeList(biomeRegistry)));
    }

    private MiddleEarthChunkGenerator(BiomeSource biomeSource) {
        super(requireMiddleEarthBiomeSource(biomeSource));

        this.middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
        this.middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
        this.middleEarthBiomeSource = (ModBiomeSource) biomeSource;
    }

    private static BiomeSource requireMiddleEarthBiomeSource(BiomeSource biomeSource) {
        if (!(biomeSource instanceof ModBiomeSource)) {
            throw new IllegalArgumentException(
                    "Middle-earth chunk generator requires the Middle-earth biome source"
            );
        }
        return biomeSource;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> structureSets,
                                                    RandomState randomState, long seed) {
        middleEarthBiomeSource.bindWorldSeed(seed);
        return super.createState(structureSets, randomState, seed);
    }

    @Override
    public void applyCarvers(WorldGenRegion chunkRegion, long seed, RandomState noiseConfig,
                             BiomeManager biomeAccess, StructureManager structureAccessor,
                             ChunkAccess chunk, GenerationStep.Carving carverStep) {

    }

    private static final int STRUCTURE_MARGIN_ADAPT = 10;

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structures, RandomState noiseConfig, ChunkAccess chunk) {
    }

    private void generateTerrain(ChunkAccess chunk, StructureManager structures, long seed,
                                 Climate.Sampler biomeSampler, boolean generateProceduralStructures) {
        int bottomY = chunk.getMinBuildHeight();
        List<StructureStart> structureStarts = structures.startsForStructure(chunk.getPos(), s -> true);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int minBlockX = chunk.getPos().getMinBlockX();
        int minBlockZ = chunk.getPos().getMinBlockZ();
        float[][] heightCache = createHeightCache(minBlockX, minBlockZ, seed);
        Holder<Biome>[] surfaceBiomeCache =
                createSurfaceBiomeCache(chunk, seed, biomeSampler);
        VerticalNoiseCoordinates verticalNoiseCoordinates =
                getVerticalNoiseCoordinates(bottomY, chunk.getMaxBuildHeight());
        assert caveDecisionsMatchReference(minBlockX, minBlockZ, verticalNoiseCoordinates);
        List<List<StructureTerrainPiece>> structureTerrainPieces =
                createStructureTerrainPieces(structureStarts);

        for(int x = 0; x < CHUNK_WIDTH; x++) {
            for(int z = 0; z < CHUNK_WIDTH; z++) {
                int posX = minBlockX + x;
                int posZ = minBlockZ + z;
                ColumnNoiseCoordinates columnNoiseCoordinates = new ColumnNoiseCoordinates(posX, posZ);
                MapBasedCustomBiome customHeightBiomeHeightData =
                        MapBasedBiomePool.getBiome(
                                surfaceBiomeCache[x * CHUNK_WIDTH + z],
                                posX,
                                posZ
                        );
                BiomeData biomeData = customHeightBiomeHeightData.getBiome();

                float height = heightCache[x][z];

                float caveBlendNoise = getCaveBlendNoise(posX, posZ, seed);
                float slopeAngle = getTerrainSlope(
                        height,
                        heightCache[x + TERRAIN_SLOPE_OFFSET][z],
                        heightCache[x][z + TERRAIN_SLOPE_OFFSET]);
                int waterHeight = customHeightBiomeHeightData.getWaterHeight();

                ResourceKey<Biome> biomeRegistryKey = customHeightBiomeHeightData.getBiomeKey();
                height = adjustMappedTerrainHeight(posX, posZ, height, biomeRegistryKey, seed);
                TerrainColumnSample columnSample = new TerrainColumnSample(
                        posX,
                        posZ,
                        customHeightBiomeHeightData,
                        biomeData,
                        biomeRegistryKey,
                        height,
                        slopeAngle,
                        caveBlendNoise,
                        waterHeight,
                        columnNoiseCoordinates
                );

                float newHeight = height;
                float bestInfluence = 0f;
                for (List<StructureTerrainPiece> structurePieces : structureTerrainPieces) {
                    for (StructureTerrainPiece piece : structurePieces) {
                        float minStructureHeight = piece.minStructureHeight();
                        if(piece.expandedBox().isInside(posX,(int)(DIRT_HEIGHT + height), posZ)) {
                            int minX = piece.minX();
                            int maxX = piece.maxX();
                            int minZ = piece.minZ();
                            int maxZ = piece.maxZ();

                            if (posX >= minX && posX <= maxX && posZ >= minZ && posZ <= maxZ) {
                                bestInfluence = 1.0f;
                                newHeight = minStructureHeight - DIRT_HEIGHT;
                                break;
                            } else {
                                double dx = Math.max(0, Math.max(minX - posX, posX - maxX));
                                double dz = Math.max(0, Math.max(minZ - posZ, posZ - maxZ));
                                float distanceToEdge = (float) Math.sqrt(dx * dx + dz * dz);

                                float influence = 1.0f - Math.min(1.0f, distanceToEdge / STRUCTURE_MARGIN_ADAPT);
                                if(influence > bestInfluence) {
                                    bestInfluence = influence;
                                    newHeight = Mth.lerp(influence, height, minStructureHeight - DIRT_HEIGHT);
                                }
                            }
                        }
                    }
                }
                height = newHeight;

                composeColumn(
                        (y, state) -> {
                            if (y >= bottomY && y < chunk.getMaxBuildHeight()) {
                                chunk.setBlockState(mutablePos.set(posX, y, posZ), state, false);
                            }
                        },
                        verticalNoiseCoordinates,
                        columnSample,
                        height,
                        Math.random() < 0.5f
                );

                if(generateProceduralStructures && ModServerConfigs.ENABLE_PROCEDURAL_STRUCTURES) {
                    ProceduralStructures.generateStructures(customHeightBiomeHeightData, chunk, posX, (int) (DIRT_HEIGHT + height), posZ);
                }
            }
        }
    }

    private static List<List<StructureTerrainPiece>> createStructureTerrainPieces(
            List<StructureStart> structureStarts
    ) {
        List<List<StructureTerrainPiece>> terrainPiecesByStructure = new ArrayList<>();
        for (StructureStart structureStart : structureStarts) {
            Structure structure = structureStart.getStructure();
            TerrainAdjustment adaptation = structure.terrainAdaptation();
            if (adaptation != TerrainAdjustment.BEARD_BOX) {
                continue;
            }

            List<StructureTerrainPiece> terrainPieces = new ArrayList<>();
            for (StructurePiece piece : structureStart.getPieces()) {
                if (piece instanceof PoolElementStructurePiece poolPiece) {
                    StructurePoolElement element = poolPiece.getElement();
                    StructureTemplatePool.Projection projection = element.getProjection();
                    if (projection == StructureTemplatePool.Projection.RIGID) {
                        BoundingBox box = poolPiece.getBoundingBox();
                        terrainPieces.add(new StructureTerrainPiece(
                                box.minY(),
                                box.inflatedBy(
                                        STRUCTURE_MARGIN_ADAPT + 1,
                                        STRUCTURE_MARGIN_ADAPT + 1,
                                        STRUCTURE_MARGIN_ADAPT + 1
                                ),
                                box.minX(),
                                box.maxX(),
                                box.minZ(),
                                box.maxZ()
                        ));
                    }
                }
            }
            terrainPiecesByStructure.add(terrainPieces);
        }
        return terrainPiecesByStructure;
    }

    private static float[][] createHeightCache(int minBlockX, int minBlockZ, long seed) {
        float[][] heights = new float[HEIGHT_CACHE_SIZE][HEIGHT_CACHE_SIZE];
        for(int x = 0; x < HEIGHT_CACHE_SIZE; x++) {
            int zLimit = x < CHUNK_WIDTH ? HEIGHT_CACHE_SIZE : CHUNK_WIDTH;
            for(int z = 0; z < zLimit; z++) {
                heights[x][z] = MiddleEarthHeightMap.getHeight(minBlockX + x, minBlockZ + z, seed);
            }
        }
        return heights;
    }

    private Holder<Biome>[] createSurfaceBiomeCache(
            ChunkAccess chunk,
            long seed,
            Climate.Sampler biomeSampler
    ) {
        int minBlockX = chunk.getPos().getMinBlockX();
        int minBlockZ = chunk.getPos().getMinBlockZ();
        int minQuartX = QuartPos.fromBlock(minBlockX);
        int minQuartZ = QuartPos.fromBlock(minBlockZ);
        int topY = chunk.getMaxBuildHeight() - 1;
        int firstFuzzyQuartY = QuartPos.fromBlock(topY - 2);
        // WorldGenRegion routes these lookups through ChunkAccess, including its top-quart clamp.
        int minQuartY = QuartPos.fromBlock(chunk.getMinBuildHeight());
        int maxQuartY = minQuartY + QuartPos.fromBlock(chunk.getHeight()) - 1;
        Holder<Biome>[] candidateBiomes = newBiomeHolderArray(
                FUZZY_QUART_WIDTH * FUZZY_QUART_Y_SAMPLES * FUZZY_QUART_WIDTH
        );
        BiomeManager biomeManager = new BiomeManager((quartX, quartY, quartZ) -> {
            int cacheX = quartX - (minQuartX - 1);
            int cacheY = quartY - firstFuzzyQuartY;
            int cacheZ = quartZ - (minQuartZ - 1);
            int cacheIndex = (cacheX * FUZZY_QUART_Y_SAMPLES + cacheY)
                    * FUZZY_QUART_WIDTH + cacheZ;
            Holder<Biome> cachedBiome = candidateBiomes[cacheIndex];
            if (cachedBiome != null) {
                return cachedBiome;
            }

            int clampedQuartY = Mth.clamp(quartY, minQuartY, maxQuartY);
            boolean isCurrentChunkQuart = quartX >= minQuartX
                    && quartX < minQuartX + QUARTS_PER_CHUNK
                    && quartZ >= minQuartZ
                    && quartZ < minQuartZ + QUARTS_PER_CHUNK;
            Holder<Biome> resolvedBiome = isCurrentChunkQuart
                    ? chunk.getNoiseBiome(quartX, clampedQuartY, quartZ)
                    : middleEarthBiomeSource.getNoiseBiome(
                            quartX, clampedQuartY, quartZ, biomeSampler);
            candidateBiomes[cacheIndex] = resolvedBiome;
            return resolvedBiome;
        }, BiomeManager.obfuscateSeed(seed));

        Holder<Biome>[] surfaceBiomes = newBiomeHolderArray(CHUNK_WIDTH * CHUNK_WIDTH);
        BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < CHUNK_WIDTH; x++) {
            for (int z = 0; z < CHUNK_WIDTH; z++) {
                surfaceBiomes[x * CHUNK_WIDTH + z] = biomeManager.getBiome(
                        samplePos.set(minBlockX + x, topY, minBlockZ + z)
                );
            }
        }
        return surfaceBiomes;
    }

    @SuppressWarnings("unchecked")
    private static Holder<Biome>[] newBiomeHolderArray(int size) {
        return (Holder<Biome>[]) new Holder<?>[size];
    }

    private Holder<Biome> sampleTopBiome(
            int x,
            int z,
            LevelHeightAccessor world,
            long seed,
            Climate.Sampler biomeSampler
    ) {
        int minQuartY = QuartPos.fromBlock(world.getMinBuildHeight());
        int maxQuartY = minQuartY + QuartPos.fromBlock(world.getHeight()) - 1;
        BiomeManager biomeManager = new BiomeManager(
                (quartX, quartY, quartZ) -> middleEarthBiomeSource.getNoiseBiome(
                        quartX,
                        Mth.clamp(quartY, minQuartY, maxQuartY),
                        quartZ,
                        biomeSampler
                ),
                BiomeManager.obfuscateSeed(seed)
        );
        return biomeManager.getBiome(new BlockPos(x, world.getMaxBuildHeight() - 1, z));
    }

    private TerrainColumnSample sampleNaturalColumn(
            int x,
            int z,
            long seed,
            LevelHeightAccessor world,
            Climate.Sampler biomeSampler
    ) {
        float rawHeight = MiddleEarthHeightMap.getHeight(x, z, seed);
        Holder<Biome> sampledBiome = sampleTopBiome(x, z, world, seed, biomeSampler);
        MapBasedCustomBiome colorBasedBiome = middleEarthMapRuntime.getBiome(x, z);
        MapBasedCustomBiome mapBiome = sampledBiome.unwrapKey()
                .map(key -> MapBasedBiomePool.getBiome(key, colorBasedBiome))
                .orElse(MapBasedBiomePool.defaultBiome);
        float eastHeight = MiddleEarthHeightMap.getHeight(x + TERRAIN_SLOPE_OFFSET, z, seed);
        float southHeight = MiddleEarthHeightMap.getHeight(x, z + TERRAIN_SLOPE_OFFSET, seed);
        float naturalHeight = adjustMappedTerrainHeight(
                x, z, rawHeight, mapBiome.getBiomeKey(), seed);
        return new TerrainColumnSample(
                x,
                z,
                mapBiome,
                mapBiome.getBiome(),
                mapBiome.getBiomeKey(),
                naturalHeight,
                getTerrainSlope(rawHeight, eastHeight, southHeight),
                getCaveBlendNoise(x, z, seed),
                mapBiome.getWaterHeight(),
                new ColumnNoiseCoordinates(x, z)
        );
    }

    private float adjustMappedTerrainHeight(int x, int z, float height,
                                            ResourceKey<Biome> biomeKey, long seed) {
        SubBiome subBiome = SubBiomes.getSubBiomeFromChild(biomeKey);
        if (subBiome != null) {
            double noise = ModBiomeSource.getSubBiomeNoise(x, z, subBiome.getFrequency(), seed);
            double additionalHeight = Math.max(subBiome.getAdditionalHeight((float) noise) - 1, 0);
            return height + (float) (additionalHeight * middleEarthMapRuntime.getEdge(x, z));
        }
        if (biomeKey == MEBiomeKeys.MOUNT_DOOM || biomeKey == MEBiomeKeys.MOUNT_DOOM_PIT) {
            float percentage = (float) Math.sqrt(mountDoom.distanceToSqr(new Vec2(x, z))) / 42;
            percentage = Mth.clamp(percentage, 0.0f, 1.0f);
            percentage = (float) Math.pow(percentage, 2.47f);
            return height * percentage - (1 - percentage) * getNoisyHeight(x, z) * 8;
        }
        if (biomeKey == MEBiomeKeys.DEAD_MARSHES || biomeKey == MEBiomeKeys.DEAD_MARSHES_WATER) {
            float marshHeight = getMarshesHeight(x, z, height);
            float percentage = Math.min(MiddleEarthHeightMap.getImageNoiseModifier(x, z), 0.3f) / 0.3f;
            return MiddleEarthHeightMap.lerp(marshHeight, height, percentage);
        }
        return height;
    }

    private static float getCaveBlendNoise(int x, int z, long seed) {
        return (float) ((2 * CAVE_NOISE
                * BlendedNoise.noise((double) (x + seed) / 24f, (double) (z + seed) / 24f))
                - CAVE_NOISE);
    }

    private static float getTerrainSlope(float height, float eastHeight, float southHeight) {
        float eastSlope = Math.abs((eastHeight - height) / TERRAIN_SLOPE_OFFSET);
        float southSlope = Math.abs((southHeight - height) / TERRAIN_SLOPE_OFFSET);
        float highestSlope = (eastSlope + southSlope) / 2;

        return (float) Math.toDegrees(Math.atan(highestSlope));
    }

    private static SurfaceBlocks resolveSurfaceBlocks(BiomeData biomeData, float slopeAngle,
                                                      float surfaceHeight, int waterHeight) {
        BlockState surface = biomeData.getSlopeMap().slopeDatas.getFirst().block.defaultBlockState();
        BlockState underSurface;

        if (surfaceHeight < waterHeight && surface.is(Blocks.GRASS_BLOCK)) {
            surface = Blocks.DIRT.defaultBlockState();
            underSurface = surface;
        } else if (surfaceHeight < waterHeight && surface.is(ModBlocks.CHALKSOIL_GRASS_BLOCK)) {
            surface = ModBlocks.CHALKSOIL.defaultBlockState();
            underSurface = surface;
        } else if (surfaceHeight < waterHeight && surface.is(ModBlocks.LOAM_GRASS_BLOCK)) {
            surface = ModBlocks.LOAM.defaultBlockState();
            underSurface = surface;
        } else if (surfaceHeight < waterHeight && surface.is(ModBlocks.PEAT_GRASS_BLOCK)) {
            surface = ModBlocks.PEAT.defaultBlockState();
            underSurface = surface;
        } else if (surfaceHeight < waterHeight && surface.is(ModBlocks.SILT_GRASS_BLOCK)) {
            surface = ModBlocks.SILT.defaultBlockState();
            underSurface = surface;
        } else {
            surface = biomeData.getSlopeMap().getBlockAtAngle(slopeAngle).defaultBlockState();
            if (surface.is(Blocks.GRASS_BLOCK) || surface.is(ModBlocks.SNOWY_GRASS_BLOCK)) {
                underSurface = Blocks.DIRT.defaultBlockState();
            } else if (surface.is(ModBlocks.CHALKSOIL_GRASS_BLOCK)) {
                underSurface = ModBlocks.CHALKSOIL.defaultBlockState();
            } else if (surface.is(ModBlocks.LOAM_GRASS_BLOCK)) {
                underSurface = ModBlocks.LOAM.defaultBlockState();
            } else if (surface.is(ModBlocks.PEAT_GRASS_BLOCK)) {
                underSurface = ModBlocks.PEAT.defaultBlockState();
            } else if (surface.is(ModBlocks.SILT_GRASS_BLOCK)) {
                underSurface = ModBlocks.SILT.defaultBlockState();
            } else {
                underSurface = surface;
            }
        }
        return new SurfaceBlocks(surface, underSurface);
    }

    private static void composeColumn(ColumnWriter writer,
                                       VerticalNoiseCoordinates vertical,
                                       TerrainColumnSample sample,
                                       float finalHeight,
                                       boolean extraBedrock) {
        writer.set(vertical.minY, Blocks.BEDROCK.defaultBlockState());
        writeRange(writer, vertical, vertical.minY + 1, LAVA_HEIGHT, Blocks.LAVA.defaultBlockState());

        int medgonEnd = (int) Math.ceil(MEDGON_LEVEL + sample.caveBlendNoise()) - 1;
        for (int y = vertical.minY + 1; y <= medgonEnd; y++) {
            writeCaveBlock(
                    writer,
                    vertical,
                    sample.noiseCoordinates(),
                    y,
                    StoneBlockSets.MEDGON_SET.baseBlocks.base().defaultBlockState()
            );
        }
        if (extraBedrock) {
            writer.set(vertical.minY + 1, Blocks.BEDROCK.defaultBlockState());
        }

        int nurgonStart = MEDGON_LEVEL + (int) sample.caveBlendNoise();
        int nurgonEnd = (int) Math.ceil(NURGON_LEVEL + sample.caveBlendNoise()) - 1;
        for (int y = nurgonStart; y <= nurgonEnd; y++) {
            writeCaveBlock(
                    writer,
                    vertical,
                    sample.noiseCoordinates(),
                    y,
                    StoneBlockSets.NURGON_SET.baseBlocks.base().defaultBlockState()
            );
        }
        int deepslateStart = NURGON_LEVEL + (int) sample.caveBlendNoise();
        int deepslateEnd = (int) Math.ceil(DEEPSLATE_LEVEL + sample.caveBlendNoise()) - 1;
        for (int y = deepslateStart; y <= deepslateEnd; y++) {
            writeCaveBlock(
                    writer,
                    vertical,
                    sample.noiseCoordinates(),
                    y,
                    Blocks.DEEPSLATE.defaultBlockState()
            );
        }

        float dirtHeight = HEIGHT + finalHeight - 1;
        int currentHeight = DEEPSLATE_LEVEL + (int) sample.caveBlendNoise();
        int totalLayersHeight = (int) (dirtHeight - currentHeight);
        for (BlocksLayeringData.LayerData layerData : sample.biomeData().getBlocksLayering().layers) {
            int blocks = (int) (totalLayersHeight * layerData.percentage);
            for (int count = 0; count <= blocks; count++) {
                writeCaveBlock(
                        writer,
                        vertical,
                        sample.noiseCoordinates(),
                        currentHeight++,
                        layerData.block.defaultBlockState()
                );
            }
        }

        BlockState finalLayer = sample.biomeData().getBlocksLayering().layers
                .getLast().block.defaultBlockState();
        writer.set((int) (HEIGHT + finalHeight - 2), finalLayer);
        SurfaceBlocks surfaceBlocks = resolveSurfaceBlocks(
                sample.biomeData(),
                sample.slopeAngle(),
                DIRT_HEIGHT + finalHeight,
                sample.waterHeight()
        );
        writer.set((int) (HEIGHT + finalHeight - 1), surfaceBlocks.underSurface());
        writeRange(
                writer,
                vertical,
                (int) (HEIGHT + finalHeight),
                (int) Math.ceil(DIRT_HEIGHT + finalHeight) - 1,
                surfaceBlocks.underSurface()
        );
        int surfaceY = (int) (DIRT_HEIGHT + finalHeight);
        int liquidStartY = (int) (DIRT_HEIGHT + finalHeight + 1);
        writer.set(surfaceY, surfaceBlocks.surface());

        if (sample.isMountDoom()) {
            writeRange(writer, vertical, liquidStartY, 100, Blocks.LAVA.defaultBlockState());
            if (DIRT_HEIGHT + finalHeight < 110) {
                writer.set(surfaceY, Blocks.MAGMA_BLOCK.defaultBlockState());
            }
        } else {
            writeRange(
                    writer,
                    vertical,
                    liquidStartY,
                    sample.waterHeight(),
                    Blocks.WATER.defaultBlockState()
            );
        }
    }

    private static void writeRange(ColumnWriter writer, VerticalNoiseCoordinates vertical,
                                   int firstY, int lastY, BlockState state) {
        int start = Math.max(firstY, vertical.minY);
        int end = Math.min(lastY, vertical.maxY - 1);
        for (int y = start; y <= end; y++) {
            writer.set(y, state);
        }
    }

    private static void writeCaveBlock(ColumnWriter writer,
                                       VerticalNoiseCoordinates vertical,
                                       ColumnNoiseCoordinates column,
                                       int y,
                                       BlockState state) {
        if (y >= vertical.minY && y < vertical.maxY && shouldSetCaveBlock(column, vertical, y)) {
            writer.set(y, state);
        }
    }

    private static VerticalNoiseCoordinates getVerticalNoiseCoordinates(int minY, int maxY) {
        if (minY == DEFAULT_VERTICAL_NOISE.minY && maxY == DEFAULT_VERTICAL_NOISE.maxY) {
            return DEFAULT_VERTICAL_NOISE;
        }
        return new VerticalNoiseCoordinates(minY, maxY);
    }

    public double getStructureWeightAt(StructureManager structures, ChunkAccess chunk, int x, int y, int z) {
        Beardifier sampler = Beardifier.forStructuresInChunk(structures, chunk.getPos());
        DensityFunction.SinglePointContext unblendedNoisePos = new DensityFunction.SinglePointContext(x, y, z);
        return sampler.compute(unblendedNoisePos);
    }

    private static boolean shouldSetCaveBlock(ColumnNoiseCoordinates column,
                                              VerticalNoiseCoordinates vertical,
                                              int y) {
        int yIndex = vertical.index(y);
        if(y < WATER_HEIGHT) {
            float noise = (float) SimplexNoise.noise(
                    column.caveX, vertical.caveTangent[yIndex], column.caveZ);
            noise += 0.5f * (float) SimplexNoise.noise(
                    column.denseCaveX, vertical.denseCaveY[yIndex], column.denseCaveZ);
            noise = noise / (1 + 0.5f);
            if(!(noise < 0.4f)) {
                return false;
            }
        }
        float noise3 = (float) SimplexNoise.noise(
                column.noise3X, vertical.noise3Y[yIndex], column.noise3Z);
        if(!(noise3 < 0.75f)) {
            return false;
        }
        float miniNoise = (float) SimplexNoise.noise(
                column.miniNoiseX, vertical.miniNoiseY[yIndex], column.miniNoiseZ);
        if(!(miniNoise < 0.8f)) {
            return false;
        }

        float spaghettiNoise = Math.abs((float) SimplexNoise.noise(
                column.spaghettiX, (float) vertical.caveTangent[yIndex], column.spaghettiZ, 57142));
        float spaghettiNoise2 = Math.abs((float) SimplexNoise.noise(
                column.spaghettiOffsetZ, vertical.caveY[yIndex], column.noise3X, 0));
        float spaghettiNoise3 = Math.abs((float) SimplexNoise.noise(
                column.denseSpaghettiOffsetZ, vertical.caveY[yIndex], column.denseSpaghettiX, 0));
        float combinedSpaghettiNoise = spaghettiNoise + spaghettiNoise2 + spaghettiNoise3;
        combinedSpaghettiNoise /= 3;

        return combinedSpaghettiNoise > 0.09f;
    }

    private static boolean caveDecisionsMatchReference(int minBlockX, int minBlockZ,
                                                       VerticalNoiseCoordinates vertical) {
        int[] horizontalOffsets = {0, CHUNK_WIDTH / 2, CHUNK_WIDTH - 1};
        int[] ySamples = {
                vertical.minY + 1, LAVA_HEIGHT, MEDGON_LEVEL - 1, MEDGON_LEVEL,
                NURGON_LEVEL - 1, NURGON_LEVEL, DEEPSLATE_LEVEL,
                WATER_HEIGHT - 1, WATER_HEIGHT, WATER_HEIGHT + 1,
                vertical.maxY - 1
        };
        for(int xOffset : horizontalOffsets) {
            for(int zOffset : horizontalOffsets) {
                int x = minBlockX + xOffset;
                int z = minBlockZ + zOffset;
                ColumnNoiseCoordinates column = new ColumnNoiseCoordinates(x, z);
                for(int y : ySamples) {
                    if(y >= vertical.minY && y < vertical.maxY
                            && shouldSetCaveBlock(column, vertical, y) != shouldSetCaveBlockReference(x, y, z)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean shouldSetCaveBlockReference(int x, int y, int z) {
        float caveY = (float) y / CAVE_STRETCH_V;
        double caveTangent = Math.tan(caveY);
        float noise = 0;
        if(y < WATER_HEIGHT) {
            noise = (float) SimplexNoise.noise(
                    (float) x / CAVE_STRETCH_H, caveTangent, (float) z / CAVE_STRETCH_H);
            noise += 0.5f * (float) SimplexNoise.noise(
                    (float) x / (CAVE_STRETCH_H * 0.5f),
                    (float) y / (CAVE_STRETCH_V * 0.5f),
                    (float) z / (CAVE_STRETCH_H * 0.5f));
            noise = noise / (1 + 0.5f);
        }
        float noise3 = (float) SimplexNoise.noise((float) x / 90, (float) y / 60, (float) z / 90);
        float miniNoise = (float) SimplexNoise.noise((float) x / 40, (float) y / 30, (float) z / 40);
        float spaghettiNoise = Math.abs((float) SimplexNoise.noise(
                (float) x / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), (float) caveTangent,
                (float) z / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), 57142));
        float spaghettiNoise2 = Math.abs((float) SimplexNoise.noise(
                (float) (98153 + z) / SPAGHETTI_CAVE_STRETCH_H, caveY,
                (float) x / SPAGHETTI_CAVE_STRETCH_H, 0));
        float spaghettiNoise3 = Math.abs((float) SimplexNoise.noise(
                (float) (1243624 + z) / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), caveY,
                (float) x / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), 0));
        float combinedSpaghettiNoise = spaghettiNoise + spaghettiNoise2 + spaghettiNoise3;
        combinedSpaghettiNoise /= 3;
        return noise < 0.4f && noise3 < 0.75f && miniNoise < 0.8f && combinedSpaghettiNoise > 0.09f;
    }

    @FunctionalInterface
    private interface ColumnWriter {
        void set(int y, BlockState state);
    }

    private record SurfaceBlocks(BlockState surface, BlockState underSurface) {
    }

    private record StructureTerrainPiece(
            float minStructureHeight,
            BoundingBox expandedBox,
            int minX,
            int maxX,
            int minZ,
            int maxZ
    ) {
    }

    private record TerrainColumnSample(
            int x,
            int z,
            MapBasedCustomBiome mapBiome,
            BiomeData biomeData,
            ResourceKey<Biome> biomeKey,
            float naturalHeight,
            float slopeAngle,
            float caveBlendNoise,
            int waterHeight,
            ColumnNoiseCoordinates noiseCoordinates
    ) {
        private boolean isMountDoom() {
            return biomeKey == MEBiomeKeys.MOUNT_DOOM || biomeKey == MEBiomeKeys.MOUNT_DOOM_PIT;
        }
    }

    private static final class ColumnNoiseCoordinates {
        private final float caveX;
        private final float caveZ;
        private final float denseCaveX;
        private final float denseCaveZ;
        private final float noise3X;
        private final float noise3Z;
        private final float miniNoiseX;
        private final float miniNoiseZ;
        private final float spaghettiX;
        private final float spaghettiZ;
        private final float spaghettiOffsetZ;
        private final float denseSpaghettiX;
        private final float denseSpaghettiOffsetZ;

        private ColumnNoiseCoordinates(int x, int z) {
            caveX = (float) x / CAVE_STRETCH_H;
            caveZ = (float) z / CAVE_STRETCH_H;
            denseCaveX = (float) x / (CAVE_STRETCH_H * 0.5f);
            denseCaveZ = (float) z / (CAVE_STRETCH_H * 0.5f);
            noise3X = (float) x / 90;
            noise3Z = (float) z / 90;
            miniNoiseX = (float) x / 40;
            miniNoiseZ = (float) z / 40;
            spaghettiX = (float) x / (SPAGHETTI_CAVE_STRETCH_H * 1.5f);
            spaghettiZ = (float) z / (SPAGHETTI_CAVE_STRETCH_H * 1.5f);
            spaghettiOffsetZ = (float) (98153 + z) / SPAGHETTI_CAVE_STRETCH_H;
            denseSpaghettiX = (float) x / (SPAGHETTI_CAVE_STRETCH_H * 0.5f);
            denseSpaghettiOffsetZ = (float) (1243624 + z) / (SPAGHETTI_CAVE_STRETCH_H * 0.5f);
        }
    }

    private static final class VerticalNoiseCoordinates {
        private final int minY;
        private final int maxY;
        private final float[] caveY;
        private final double[] caveTangent;
        private final float[] denseCaveY;
        private final float[] noise3Y;
        private final float[] miniNoiseY;

        private VerticalNoiseCoordinates(int minY, int maxY) {
            this.minY = minY;
            this.maxY = maxY;
            int size = maxY - minY;
            caveY = new float[size];
            caveTangent = new double[size];
            denseCaveY = new float[size];
            noise3Y = new float[size];
            miniNoiseY = new float[size];
            for(int y = minY; y < maxY; y++) {
                int index = y - minY;
                caveY[index] = (float) y / CAVE_STRETCH_V;
                caveTangent[index] = Math.tan(caveY[index]);
                denseCaveY[index] = (float) y / (CAVE_STRETCH_V * 0.5f);
                noise3Y[index] = (float) y / 60;
                miniNoiseY[index] = (float) y / 30;
            }
        }

        private int index(int y) {
            return y - minY;
        }
    }

    public static float getMarshesHeight(int x, int z, float height) {
        height = -2 + (2.0f * (float) BlendedNoise.noise((double) x / 19,  (double) z / 19));
        height += (float) BlendedNoise.noise((double) x / 11,  (double) z / 11);
        return height;
    }

    public static float getNoisyHeight(int x, int z) {
        float height = -2 + (4.0f * (float) BlendedNoise.noise((double) x / 8,  (double) z / 8));
        height += 2 * (float) BlendedNoise.noise((double) x / 4,  (double) z / 4);
        return height;
    }
    
    @Override
    public void applyBiomeDecoration(WorldGenLevel world, ChunkAccess chunk, StructureManager structureAccessor) {
        super.applyBiomeDecoration(world, chunk, structureAccessor);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        ChunkPos chunkPos = region.getCenter();
        Holder<Biome> registryEntry = region.getBiome(chunkPos.getWorldPosition().atY(region.getHeight(Heightmap.Types.WORLD_SURFACE_WG, chunkPos.getMinBlockX(), chunkPos.getMinBlockZ()) - 1));
        WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
        chunkRandom.setDecorationSeed(region.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, registryEntry, chunkPos, chunkRandom);
    }

    @Override
    public int getGenDepth() {
        return GEN_DEPTH;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState noiseConfig, StructureManager structureAccessor, ChunkAccess chunk) {
        generateTerrain(
                chunk,
                structureAccessor,
                middleEarthBiomeSource.getWorldSeed(),
                noiseConfig.sampler(),
                true
        );
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return WATER_HEIGHT;
    }

    @Override
    public int getMinY() {
        return MIN_Y;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightmap, LevelHeightAccessor world, RandomState noiseConfig) {
        float worldHeight = 1 + DIRT_HEIGHT
                + MiddleEarthHeightMap.getHeight(x, z, middleEarthBiomeSource.getWorldSeed());
        return Math.max(WATER_HEIGHT, (int) worldHeight);
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor world, RandomState noiseConfig) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> text, RandomState noiseConfig, BlockPos pos) {

    }
}
