package net.sevenstars.middleearth.features.generation.chunkgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.caves.DeepThemes;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.features.generation.noise.BlendedNoise;
import net.sevenstars.middleearth.features.generation.biomes.BlocksLayeringData;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.SlopeMap;
import net.sevenstars.middleearth.features.generation.biomes.surface.*;
import net.sevenstars.middleearth.features.generation.chunkgen.map.HelmsDeep;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapUtils;

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
    private static final int CHUNK_SIZE = 16;
    private static final int SLOPE_SAMPLE_OFFSET = 4; // the height lattice
    private static final int CLIFF_REACH = 2; // columns each way whose ground a cliff face reaches down to
    private static final int MAX_CLIFF_FACE = 48;
    private static final int MOUNT_DOOM_LAVA_TOP = 100;
    private static final int MOUNT_DOOM_MAGMA_BELOW = 110;
    private static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
    private static final BlockState DEEPSLATE = Blocks.DEEPSLATE.getDefaultState();

    private final MiddleEarthMapUtils middleEarthMapUtils;
    private final MiddleEarthMapRuntime middleEarthMapRuntime;

    public static final int mapMultiplier = (int) Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION + MiddleEarthMapConfigs.PIXEL_WEIGHT - 2);
    public static final Vec2f mountDoom = new Vec2f(2131.5f, 1715.2f).multiply(mapMultiplier);

    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(MiddleEarthChunkGenerator::new)));

    public MiddleEarthChunkGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new BiomeSourceME(allBiomes(biomeRegistry)));

        this.middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
        this.middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    private static ArrayList<RegistryEntry<Biome>> allBiomes(RegistryEntryLookup<Biome> biomeRegistry) {
        return new ArrayList<>(Arrays.asList(
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OCEAN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ANDUIN_VALES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ANDUIN_VALES_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ANORIEN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ANORIEN_RIVERSIDE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ANORIEN_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BARROW_DOWNS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BELERIAND_ISLAND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BELFALAS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BELFALAS_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BELFALAS_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BELFALAS_BEACH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLACKROOT_VALE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLACKROOT_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLUE_MOUNTAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLUE_MOUNTAINS_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLUE_MOUNTAINS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLUE_MOUNTAINS_HIGH_LANDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLUE_MOUNTAINS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BROWN_LANDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CARADHRAS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CARADHRAS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CARADHRAS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CELEBDIL_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CELEBDIL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CELEBDIL_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.CORSAIR_COASTS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DALE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DALE_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DALE_MEADOW),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DALE_CITY),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DAGORLAD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DALE_RIVERSIDE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DARK_MIRKWOOD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DARK_MIRKWOOD_EDGE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DARK_ANDUIN_VALES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DEAD_MARSHES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DEAD_MARSHES_WATER),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DESOLATED_LANDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DOL_GULDUR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DOL_GULDUR_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DORWINION),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DORWINION_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DUNLAND_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DUNLAND_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EAST_BIGHT),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EASTERN_NURN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EASTERN_RHOVANION),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EMYN_MUIL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EMYN_MUIL_CLIFFS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EMYN_MUIL_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EMYN_MUIL_POND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ENEDWAITH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ENEDWAITH_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ENEDWAITH_WHEAT_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EREGION),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EREGION_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EREGION_GLADE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ETHIR_ANDUIN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ETHIR_ANDUIN_RIVER_DELTA),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FANGORN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FANGORN_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FANUIDHOL_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FANUIDHOL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FANUIDHOL_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_RIVER),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREAT_RIVER),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GUNDABAD_PLAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GUNDABAD_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FORODWAITH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FROZEN_OCEAN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FROZEN_POND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LINDON_SHORES_CLIFFS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LINDON_SHORES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GONDOR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GONDOR_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GONDOR_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GORGOROTH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GORGOROTH_DELTA),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREY_MOUNTAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREY_MOUNTAINS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREY_MOUNTAINS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREY_ASHEN_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREY_PLAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GREY_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HARAD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HARAD_DESERT),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HARAD_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HARONDOR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HILLS_OF_EVENDIM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.IRON_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.IRON_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.IRON_HILLS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.IRON_HILLS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.IRON_HILLS_PLAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTHERN_RHOVANION_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTHERN_RHOVANION_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ISENGARD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ISENGARD_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ITHILIEN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ITHILIEN_GLADE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ITHILIEN_WASTES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LAMEDON),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LAMEDON_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LEBENNIN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LEBENNIN_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LEBENNIN_SHORES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LINDON),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LINDON_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LINDON_HIDDEN_BLOSSOM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LINDON_MEADOW),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONELY_MOUNTAIN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONELY_MOUNTAIN_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONELY_MOUNTAIN_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONELY_MOUNTAIN_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONELY_MOUNTAIN_TAIGA),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONG_LAKE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONG_LAKE_SHORES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LONG_MARSHES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LORIEN_EDGE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH_CHERRY_BLOSSOM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH_VALLEY),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH_VALLEY_RED),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH_VALLEY_ORANGE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH_VALLEY_YELLOW),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOSSARNACH_VALLEY_GREEN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOTHLORIEN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOTHLORIEN_GLADE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LOTHLORIEN_BLOSSOM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OASIS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.POND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MANGROVE_POND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MINHIRIATH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MINHIRIATH_WHEAT_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_EDGE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_MARSHES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MIRKWOOD_SWAMP),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MISTY_MOUNTAINS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MISTY_MOUNTAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MISTY_MOUNTAINS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORDOR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORDOR_ASHEN_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORDOR_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ERED_LITHUI),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ERED_LITHUI_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ERED_LITHUI_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORDOR_WASTES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORGUL_VALE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORGUL_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MORGUL_RIVER),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MOUNT_GUNDABAD_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MOUNT_GUNDABAD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MOUNT_GUNDABAD_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MOUNT_DOOM_PIT),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MOUNT_DOOM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NAN_CURUNIR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NEN_HITHOEL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NEN_HITHOEL_RAPIDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NEN_HITHOEL_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NEN_HITHOEL_SHORES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NINDALF),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTH_DOWNS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DUNLAND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTHERN_DUNLAND_GLADE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_SWAMP),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NORTHERN_WASTELANDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN_EDGE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN_EDGE_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN_RIVER),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NURN_SEA),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OCEAN_COAST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ANGMAR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ANGMAR_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ANGMAR_COLD_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ANGMAR_FROZEN_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ARTHEDAIN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ARTHEDAIN_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ARTHEDAIN_MEADOW),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_ARTHEDAIN_FOOTHILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_CARDOLAN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_CARDOLAN_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_CARDOLAN_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_RHUDAUR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_RHUDAUR_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OLD_RHUDAUR_HILL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.OSGILIATH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.PELENNOR_FIELDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.PELENNOR_WHEAT_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.RIVER),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.RHUN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.RHUN_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.RHUN_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.RHUN_HIDDEN_BLOSSOM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HIGH_MOOR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HIGH_MOOR_VALE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.HIGH_MOOR_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ROHAN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ROHAN_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ROHAN_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SEA_OF_RHUN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SARN_GEBIR_WILDLANDS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SARN_GEBIR_SHORES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SHIRE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SHIRE_EDGE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SHIRE_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SHIRE_HILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SHIRE_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SOUTHEAST_RHOVANION),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SOUTHEAST_RHOVANION_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DRUWAITH_IAUR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.SOUTHERN_FOROCHEL),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EPHEL_DUATH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EPHEL_DUATH_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.EPHEL_DUATH_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.THE_ANGLE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.THE_OLD_FOREST),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.THE_WOLD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.THE_WHITE_DOWNS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.TOLFALAS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.TOROGWAITH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.TROLLSHAWS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.UDUN),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.UMBAR),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.UMBAR_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WASTE_POND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WEBBED_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WEBBED_DARK_WOODS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WITHERED_HEATH),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WHITE_MOUNTAINS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WHITE_MOUNTAINS_BASE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WHITE_MOUNTAINS_PEAKS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WOODLAND_REALM),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WOODLAND_FOOTHILLS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WOODLAND_GLADE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.AUTUMN_WOODLAND),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.WOODLAND_HILLS),

            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BASIC_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LUSH_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DRIPSTONE_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DOLOMITE_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GALONN_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GILDED_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.IZHERABAN_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LIMESTONE_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MOUNTAIN_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MUD_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.FUNGUS_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MITHRIL_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.NAMELESS_DEEPS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GNAWED_TUNNELS),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GLOWWORM_GROTTO),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.GLITTERING_CAVES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.LAVA_TUBES),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.BASALT_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.MAGMA_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.DRY_CAVE),
            biomeRegistry.getOrThrow(BiomeKeyRegistryME.ICE_CAVE)
        ));
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk) {

    }

    private static final int STRUCTURE_MARGIN_ADAPT = 10;

    // structure starts the chunk references, like StructureAccessor.getStructureStarts, but a start chunk the region does not
    // reach (a big mine's can be 8 chunks away) is skipped: asking for it throws
    private static List<StructureStart> reachableStarts(ChunkRegion region, Chunk chunk) {
        List<StructureStart> starts = new ArrayList<>();
        for (Map.Entry<Structure, LongSet> entry : chunk.getStructureReferences().entrySet()) {
            for (long packed : entry.getValue()) {
                int x = ChunkPos.getPackedX(packed);
                int z = ChunkPos.getPackedZ(packed);
                if (!region.isChunkLoaded(x, z)) continue;
                try {
                    Chunk startChunk = region.getChunk(x, z, ChunkStatus.STRUCTURE_STARTS, false);
                    StructureStart start = startChunk == null ? null : startChunk.getStructureStart(entry.getKey());
                    if (start != null && start.hasChildren()) starts.add(start);
                } catch (RuntimeException exception) {
                    // not available at this stage
                }
            }
        }
        return starts;
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        int bottomY = chunk.getBottomY();
        long seed = region.getSeed();
        List<StructureStart> structureStarts = reachableStarts(region, chunk);
        CaveSampler caves = new CaveSampler(chunk.getPos().getStartX(), chunk.getPos().getStartZ(), bottomY, chunk.getTopYInclusive(), seed, false);
        ColumnWriter out = new ColumnWriter(chunk);
        try {
            fillColumns(region, chunk, bottomY, seed, structureStarts, caves, out);
        } finally {
            caves.release();
            out.release();
        }
    }

    private void fillColumns(ChunkRegion region, Chunk chunk, int bottomY, long seed, List<StructureStart> structureStarts, CaveSampler caves, ColumnWriter out) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;
        DepthBands.ChunkFloors floors = DepthBands.ChunkFloors.forChunk(middleEarthMapRuntime, chunk.getPos().getStartX(), chunk.getPos().getStartZ(), seed);
        if(floors == null) out.fillBedrock(DepthBands.SHALLOW_FLOOR);
        BlockPos.Mutable biomePos = new BlockPos.Mutable();
        MiddleEarthHeightMap.ChunkHeights heights = MiddleEarthHeightMap.getChunkHeights(chunkX, chunkZ);
        BakedBoulders boulders = new BakedBoulders(getBiomeSource(), chunkX, chunkZ, seed, structureStarts);

        for(int x = 0; x < CHUNK_SIZE; x++) {
            for(int z = 0; z < CHUNK_SIZE; z++) {
                int posX = (chunkX * CHUNK_SIZE) + x;
                int posZ = (chunkZ * CHUNK_SIZE) + z;
                MapBasedCustomBiome biomeData = null;
                if(middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
                    RegistryEntry<Biome> biome = region.getBiome(biomePos.set(posX, chunk.getTopYInclusive(), posZ));
                    biomeData = MapBasedBiomePool.getBiome(biome, posX, posZ);
                }
                if(biomeData == null) {
                    biomeData = MapBasedBiomePool.defaultBiome;
                }

                float caveBlendNoise = caveBlendNoise(posX, posZ, seed);
                float slopeAngle = getTerrainSlope(heights, x, z);
                int waterHeight = biomeData.getWaterHeight();
                RegistryKey<Biome> biomeRegistryKey = biomeData.getBiomeKey();

                float height = adaptToStructures(structureStarts, posX, posZ, heights.at(x, z));
                int surfaceY = (int) (DIRT_HEIGHT + height);

                int floor = floors == null ? DepthBands.SHALLOW_FLOOR : floors.floor(x, z);
                if(floor < DepthBands.SHALLOW_FLOOR) {
                    fillDeepBottom(caves, out, floors, bottomY, x, z, posX, posZ, floor, surfaceY, caveBlendNoise, seed);
                } else {
                    fillShallowBottom(caves, out, floors != null, bottomY, x, z, posX, posZ, caveBlendNoise);
                }

                BlockState nurgonBlock = StoneBlockSetRegistryME.NURGON_SET.baseBlocks.base().getDefaultState();
                for(int y = MEDGON_LEVEL + (int) caveBlendNoise; y < NURGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(caves, out, x, y, z, posX, posZ, nurgonBlock);
                }
                for(int y = NURGON_LEVEL + (int) caveBlendNoise; y < DEEPSLATE_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(caves, out, x, y, z, posX, posZ, DEEPSLATE);
                }

                float dirtHeight = HEIGHT + height - 1;
                int currentHeight = DEEPSLATE_LEVEL + (int) caveBlendNoise;
                int totalLayersHeight = (int) (dirtHeight - currentHeight);
                var biomeType = biomeData.getBiome();
                var slopeMap = biomeType.getSlopeMap();
                List<BlocksLayeringData.LayerData> layers = biomeType.getBlocksLayering().layers;
                for(BlocksLayeringData.LayerData layerData : layers) {
                    int blocks = (int) (totalLayersHeight * layerData.percentage);
                    BlockState layerState = layerData.block.getDefaultState();
                    for(int y = 0; y <= blocks; y++) {
                        trySetBlock(caves, out, x, currentHeight++, z, posX, posZ, layerState);
                    }
                }
                caves.setSurface(posX, posZ, surfaceY);
                setSkin(caves, out, x, (int) (HEIGHT + height - 2), z, posX, posZ, layers.getLast().block.getDefaultState());

                Block surfaceBlock = slopeMap.slopeDatas.getFirst().block;
                Block soilBlock = soilBelow(surfaceBlock);
                boolean belowWater = DIRT_HEIGHT + height < waterHeight;
                Block underSurfaceBlock;
                if(belowWater && soilBlock != null) {
                    surfaceBlock = soilBlock;
                    underSurfaceBlock = soilBlock;
                } else {
                    surfaceBlock = slopeMap.getBlockAtAngle(slopeAngle);
                    soilBlock = soilBelow(surfaceBlock);
                    if(soilBlock != null) underSurfaceBlock = soilBlock;
                    else if(surfaceBlock == BlockRegistryME.SNOWY_GRASS_BLOCK) underSurfaceBlock = Blocks.DIRT;
                    else underSurfaceBlock = surfaceBlock;
                }

                BlockState underSurfaceState = underSurfaceBlock.getDefaultState();
                setSkin(caves, out, x, (int) (HEIGHT + height - 1), z, posX, posZ, underSurfaceState);
                for(int y = (int) (HEIGHT + height); y < DIRT_HEIGHT + height; y++) {
                    setSkin(caves, out, x, y, z, posX, posZ, underSurfaceState);
                }
                setSkin(caves, out, x, surfaceY, z, posX, posZ, surfaceBlock.getDefaultState());
                if(HelmsDeep.stream(posX, posZ) && !belowWater && !caves.opensBelow(posX, surfaceY, posZ)) {
                    out.set(x, surfaceY, z, WATER);
                    chunk.markBlockForPostProcessing(new BlockPos(posX, surfaceY, posZ));
                }
                // a cliff shows its face down to the ground beside it, so that face is all the cliff block rather
                // than a crust over the soil and the layers, which differ from column to column
                if(!belowWater && isCliff(slopeMap, slopeAngle)) {
                    BlockState cliffState = surfaceBlock.getDefaultState();
                    int bottom = surfaceY - Math.min(MAX_CLIFF_FACE, (int) Math.ceil(height - lowestNeighbour(heights, x, z)) + 1);
                    for(int y = surfaceY - 1; y >= bottom; y--) trySetBlock(caves, out, x, y, z, posX, posZ, cliffState);
                }

                if(biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM || biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM_PIT) {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= MOUNT_DOOM_LAVA_TOP; y++) {
                        out.set(x, y, z, LAVA);
                    }
                    if(DIRT_HEIGHT + height < MOUNT_DOOM_MAGMA_BELOW) {
                        out.set(x, surfaceY, z, MAGMA);
                    }
                } else {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= waterHeight; y++) {
                        out.set(x, y, z, WATER);
                    }
                }

                if(!boulders.isEmpty() && !caves.nearEntrance(posX, posZ)) boulders.write(out, x, z, posX, posZ);

                if(ServerConfigME.ENABLE_PROCEDURAL_STRUCTURES) {
                    // those write into the chunk directly, so what is buffered goes in first
                    if(ProceduralStructures.writes(biomeData)) out.sync();
                    ProceduralStructures.generateStructures(biomeData, chunk, posX, surfaceY, posZ);
                }
            }
        }
        // the decorations read and write the same buffers, so the sections are built once, after them
        caves.dressOpenings(chunk, out);
        caves.removeIslands(out);
        caves.decorate(chunk, out);
        out.finish();
    }

    // the soil under a grass block, null for any other block
    private static Block soilBelow(Block block) {
        if(block == Blocks.GRASS_BLOCK) return Blocks.DIRT;
        if(block == BlockRegistryME.CHALKSOIL_GRASS_BLOCK) return BlockRegistryME.CHALKSOIL;
        if(block == BlockRegistryME.LOAM_GRASS_BLOCK) return BlockRegistryME.LOAM;
        if(block == BlockRegistryME.PEAT_GRASS_BLOCK) return BlockRegistryME.PEAT;
        if(block == BlockRegistryME.SILT_GRASS_BLOCK) return BlockRegistryME.SILT;
        return null;
    }

    // pulls the terrain towards the rigid pieces of nearby structures
    private static float adaptToStructures(List<StructureStart> structureStarts, int posX, int posZ, float height) {
        float newHeight = height;
        float bestInfluence = 0f;
        int probeY = (int) (DIRT_HEIGHT + height);
        for (StructureStart structureStart : structureStarts) {
            if (structureStart.getStructure().getTerrainAdaptation() != StructureTerrainAdaptation.BEARD_BOX) continue;
            for (StructurePiece piece : structureStart.getChildren()) {
                if (!(piece instanceof PoolStructurePiece poolPiece)) continue;
                if (poolPiece.getPoolElement().getProjection() != StructurePool.Projection.RIGID) continue;
                BlockBox box = poolPiece.getBoundingBox();
                int minX = box.getMinX();
                int maxX = box.getMaxX();
                int minZ = box.getMinZ();
                int maxZ = box.getMaxZ();
                int reach = STRUCTURE_MARGIN_ADAPT + 1;
                boolean nearby = posX >= minX - reach && posX <= maxX + reach
                        && probeY >= box.getMinY() - reach && probeY <= box.getMaxY() + reach
                        && posZ >= minZ - reach && posZ <= maxZ + reach;
                if (!nearby) continue;
                float minStructureHeight = box.getMinY();
                if (posX >= minX && posX <= maxX && posZ >= minZ && posZ <= maxZ) {
                    bestInfluence = 1.0f;
                    newHeight = minStructureHeight - DIRT_HEIGHT;
                    break;
                }
                double dx = Math.max(0, Math.max(minX - posX, posX - maxX));
                double dz = Math.max(0, Math.max(minZ - posZ, posZ - maxZ));
                float distanceToEdge = (float) Math.sqrt(dx * dx + dz * dz);
                float influence = 1.0f - Math.min(1.0f, distanceToEdge / STRUCTURE_MARGIN_ADAPT);
                if (influence > bestInfluence) {
                    bestInfluence = influence;
                    newHeight = MathHelper.lerp(influence, height, minStructureHeight - DIRT_HEIGHT);
                }
            }
        }
        return newHeight;
    }

    // the deeps have no lava: the bottom is water, bare rock or shallow water by region, with a rock wall across the deeps
    // where two regions meet and where the old lava sea of a shallow column lies beside them
    private static void fillDeepBottom(CaveSampler caves, ColumnWriter out, DepthBands.ChunkFloors floors, int bottomY, int x, int z, int posX, int posZ,
                                       int floor, int surfaceY, float caveBlendNoise, long seed) {
        DeepThemes.Theme theme = DeepThemes.at(posX, posZ, seed);
        DepthBands.Edges edges = DepthBands.Edges.of(surfaceY, floor, caveBlendNoise);
        boolean wall = DeepThemes.border(posX, posZ, seed);
        int beside = floors.touchesShallow(x, z) ? LAVA_HEIGHT : Integer.MIN_VALUE;
        int water = theme == DeepThemes.Theme.WET ? DepthBands.BOTTOM_BAND : theme == DeepThemes.Theme.GROTTO ? DepthBands.SHALLOW_WATER : 0;
        // rock above this column's water, up to where a higher neighbour's water reaches, so none is left open to the side
        int sealed = water > 0 ? floors.highestFloor(x, z) + water : Integer.MIN_VALUE;
        boolean island = theme == DeepThemes.Theme.GROTTO && DeepThemes.island(posX, posZ, seed);
        for(int y = bottomY; y <= floor; y++) {
            out.set(x, y, z, BEDROCK);
        }
        for(int y = floor + 1; y <= floor + water; y++) {
            out.set(x, y, z, WATER);
        }
        BlockState medgon = StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState();
        for(int y = floor + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
            boolean solid = y <= beside
                    || (theme == DeepThemes.Theme.GNAWED && y <= floor + DepthBands.BOTTOM_BAND)
                    || (y > floor + water && y <= sealed)
                    || (island && y <= floor + DepthBands.SHALLOW_WATER)
                    || (wall && y <= floors.highestFloor(x, z) + DepthBands.BOTTOM_BAND)
                    || (wall && y < edges.top(DepthBands.Band.DEEPS) && !DeepThemes.passage(posX, y, posZ, edges, seed) && !caves.shaftOpen(posX, y, posZ));
            if(solid) {
                out.set(x, y, z, medgon);
            } else {
                trySetBlock(caves, out, x, y, z, posX, posZ, medgon);
            }
        }
    }

    private static void fillShallowBottom(CaveSampler caves, ColumnWriter out, boolean hasDeepFloors, int bottomY, int x, int z, int posX, int posZ, float caveBlendNoise) {
        if(hasDeepFloors) {
            for(int y = bottomY; y < DepthBands.SHALLOW_FLOOR; y++) {
                out.set(x, y, z, BEDROCK);
            }
        }
        out.set(x, DepthBands.SHALLOW_FLOOR, z, BEDROCK);
        for(int y = DepthBands.SHALLOW_FLOOR + 1; y <= LAVA_HEIGHT; y++) {
            out.set(x, y, z, LAVA);
        }

        BlockState medgon = StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState();
        for(int y = DepthBands.SHALLOW_FLOOR + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
            trySetBlock(caves, out, x, y, z, posX, posZ, medgon);
        }
        if(Math.random() < 0.5f) out.set(x, DepthBands.SHALLOW_FLOOR + 1, z, BEDROCK);
    }

    public static float caveBlendNoise(int x, int z, long seed) {
        return (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) (x + seed) / 24f,  (double) (z + seed) / 24f)) - CAVE_NOISE);
    }

    // from both sides of the column, a whole height lattice cell each way: closer samples step from cell to cell, and
    // where a slope sits on a biome's threshold that laid strips of stone and grass along it
    private static float getTerrainSlope(MiddleEarthHeightMap.ChunkHeights heights, int x, int z) {
        int offset = SLOPE_SAMPLE_OFFSET;
        float eastSlope = Math.abs((heights.at(x + offset, z) - heights.at(x - offset, z)) / (2 * offset));
        float southSlope = Math.abs((heights.at(x, z + offset) - heights.at(x, z - offset)) / (2 * offset));
        return (float) Math.toDegrees(Math.atan((eastSlope + southSlope) / 2));
    }

    // steep enough for the last block of the biome's slopes, its bare rock
    private static boolean isCliff(SlopeMap slopes, float angle) {
        List<SlopeMap.SlopeData> datas = slopes.slopeDatas;
        return datas.size() > 1 && angle > datas.get(datas.size() - 2).angle;
    }

    private static float lowestNeighbour(MiddleEarthHeightMap.ChunkHeights heights, int x, int z) {
        float lowest = Float.MAX_VALUE;
        for(int d = 1; d <= CLIFF_REACH; d++) {
            lowest = Math.min(lowest, Math.min(Math.min(heights.at(x + d, z), heights.at(x - d, z)), Math.min(heights.at(x, z + d), heights.at(x, z - d))));
        }
        return lowest;
    }

    // the top few blocks are rock whatever the caves below, except where an entrance or a chimney cuts in
    private static void setSkin(CaveSampler caves, ColumnWriter out, int x, int y, int z, int posX, int posZ, BlockState blockState) {
        if(!caves.openingAt(posX, y, posZ)) out.set(x, y, z, blockState);
    }

    private static void trySetBlock(CaveSampler caves, ColumnWriter out, int x, int y, int z, int posX, int posZ, BlockState blockState) {
        if(out.outOfHeightLimit(y)) return;
        int result = caves.sample(posX, y, posZ);
        if(result == CaveSampler.STONE) {
            out.set(x, y, z, blockState);
        } else if(result == CaveSampler.WATER) {
            out.set(x, y, z, WATER);
        }
    }

    public static float getMarshesHeight(int x, int z) {
        float height = -2 + (2.0f * (float) BlendedNoise.noise((double) x / 19,  (double) z / 19));
        height += (float) BlendedNoise.noise((double) x / 11,  (double) z / 11);
        return height;
    }

    public static float getNoisyHeight(int x, int z) {
        float height = -2 + (4.0f * (float) BlendedNoise.noise((double) x / 8,  (double) z / 8));
        height += 2 * (float) BlendedNoise.noise((double) x / 4,  (double) z / 4);
        return height;
    }
    
    @Override
    public void populateEntities(ChunkRegion region) {
        ChunkPos chunkPos = region.getCenterPos();
        RegistryEntry<Biome> registryEntry = region.getBiome(chunkPos.getStartPos().withY(region.getTopY(Heightmap.Type.WORLD_SURFACE_WG, chunkPos.getStartX(), chunkPos.getStartZ()) - 1));
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
        chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
        SpawnHelper.populateEntities(region, registryEntry, chunkPos, chunkRandom);
    }

    @Override
    public int getWorldHeight() {
        return 576;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return WATER_HEIGHT;
    }

    @Override
    public int getMinimumY() {
        return -4;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        float worldHeight = 1 + DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z);
        return Math.max(64, (int)worldHeight);
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }

    @Override
    public void appendDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {

    }
}
