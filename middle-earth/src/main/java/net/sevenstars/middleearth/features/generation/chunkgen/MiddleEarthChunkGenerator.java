package net.sevenstars.middleearth.features.generation.chunkgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.StructureWeightSampler;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.features.generation.caves.MistyCaveDecorator;
import net.sevenstars.middleearth.features.generation.caves.MistyCaves;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.features.generation.noise.BlendedNoise;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;
import net.sevenstars.middleearth.features.generation.biomes.BlocksLayeringData;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.*;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    public static final int mapMultiplier = (int) Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION + MiddleEarthMapConfigs.PIXEL_WEIGHT - 2);
    public static final Vec2f mountDoom = new Vec2f(2131.5f, 1715.2f).multiply(mapMultiplier);
    private static final int CAVE_STRETCH_H = 60;
    private static final int SPAGHETTI_CAVE_STRETCH_H = 90;
    private static final int CAVE_STRETCH_V = 50;

    RegistryEntryLookup<Biome> biomeRegistry;
    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(MiddleEarthChunkGenerator::new)));

    public MiddleEarthChunkGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new BiomeSourceME(
                new ArrayList<>(Arrays.asList(
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
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.BASALT_CAVE),
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.MAGMA_CAVE),
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.DRY_CAVE),
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.ICE_CAVE)
                ))
            )
        );
        this.biomeRegistry = biomeRegistry;

        this.middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
        this.middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();

    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk) {

    }

    private static final int STRUCTURE_MARGIN_ADAPT = 10;
    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        int bottomY = chunk.getBottomY();
        long seed = region.getSeed();
        List<StructureStart> structureStarts = structures.getStructureStarts(chunk.getPos(), s -> true);
        CaveSampler caves = new CaveSampler(chunk.getPos().getStartX(), chunk.getPos().getStartZ(), bottomY, chunk.getTopYInclusive(), seed);
        ColumnWriter out = new ColumnWriter(chunk);
        BlockPos.Mutable biomePos = new BlockPos.Mutable();
        MiddleEarthHeightMap.ChunkHeights heights = MiddleEarthHeightMap.getChunkHeights(chunk.getPos().x, chunk.getPos().z);

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int posX = (chunk.getPos().x * 16) + x;
                int posZ = (chunk.getPos().z * 16) + z;
                 MapBasedCustomBiome customHeightBiomeHeightData = null;
                if(middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
                    RegistryEntry<Biome> biome = region.getBiome(biomePos.set(posX, chunk.getTopYInclusive(), posZ));
                    customHeightBiomeHeightData = MapBasedBiomePool.getBiome(biome, posX, posZ);
                }
                if(customHeightBiomeHeightData == null) {
                    customHeightBiomeHeightData = MapBasedBiomePool.defaultBiome;
                }

                float height = heights.at(x, z);

                float caveBlendNoise = (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) (posX + seed) / 24f,  (double) (posZ + seed) / 24f)) - CAVE_NOISE);
                float slopeAngle = getTerrainSlope(heights, x, z);
                int waterHeight = customHeightBiomeHeightData.getWaterHeight();

                RegistryKey<Biome> biomeRegistryKey = customHeightBiomeHeightData.getBiomeKey();

                float newHeight = height;
                float bestInfluence = 0f;
                for (StructureStart structureStart : structureStarts) {
                    Structure structure = structureStart.getStructure();
                    StructureTerrainAdaptation adaptation = structure.getTerrainAdaptation();
                    if (adaptation == StructureTerrainAdaptation.BEARD_BOX) {
                        for (StructurePiece piece : structureStart.getChildren()) {
                            if (piece instanceof PoolStructurePiece poolPiece) {
                                StructurePoolElement element = poolPiece.getPoolElement();
                                StructurePool.Projection projection = element.getProjection();
                                if (projection == StructurePool.Projection.RIGID) {
                                    float minStructureHeight = poolPiece.getBoundingBox().getMinY();
                                    BlockBox expandedBox = poolPiece.getBoundingBox().expand(STRUCTURE_MARGIN_ADAPT + 1, STRUCTURE_MARGIN_ADAPT + 1, STRUCTURE_MARGIN_ADAPT + 1);
                                    if(expandedBox.contains(posX,(int)(DIRT_HEIGHT + height), posZ)) {
                                        int minX = poolPiece.getBoundingBox().getMinX();
                                        int maxX = poolPiece.getBoundingBox().getMaxX();
                                        int minZ = poolPiece.getBoundingBox().getMinZ();
                                        int maxZ = poolPiece.getBoundingBox().getMaxZ();

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
                                                newHeight = MathHelper.lerp(influence, height, minStructureHeight - DIRT_HEIGHT);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                height = newHeight;

                out.set(x, bottomY, z, Blocks.BEDROCK.getDefaultState());
                for(int y = bottomY + 1; y <= LAVA_HEIGHT; y++) {
                    out.set(x, y, z, Blocks.LAVA.getDefaultState());
                }

                for(int y = bottomY + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(caves, out, x, y, z, posX, posZ, StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState());
                }
                if(Math.random() < 0.5f) out.set(x, chunk.getBottomY() + 1, z, Blocks.BEDROCK.getDefaultState());

                for(int y = MEDGON_LEVEL + (int) caveBlendNoise; y < NURGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(caves, out, x, y, z, posX, posZ, StoneBlockSetRegistryME.NURGON_SET.baseBlocks.base().getDefaultState());
                }
                for(int y = NURGON_LEVEL + (int) caveBlendNoise; y < DEEPSLATE_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(caves, out, x, y, z, posX, posZ, Blocks.DEEPSLATE.getDefaultState());
                }

                float dirtHeight = HEIGHT + height - 1;
                int currentHeight = DEEPSLATE_LEVEL + (int) caveBlendNoise;
                int totalLayersHeight = (int) (dirtHeight - currentHeight);
                for(BlocksLayeringData.LayerData layerData : customHeightBiomeHeightData.getBiome().getBlocksLayering().layers) {
                    int blocks = (int) (totalLayersHeight * layerData.percentage);
                    for(int y = 0; y <= blocks; y++) {
                        trySetBlock(caves, out, x, currentHeight++, z, posX, posZ, layerData.block.getDefaultState());
                    }
                }
                caves.setSurface(posX, posZ, (int) (DIRT_HEIGHT + height));
                boolean riftMouth = caves.mouthOpen(posX, posZ, (int) (DIRT_HEIGHT + height));
                if(!riftMouth) out.set(x, (int) (HEIGHT + height - 2), z, customHeightBiomeHeightData.getBiome().getBlocksLayering().layers.getLast().block.getDefaultState());
                BlockState surfaceBlock = customHeightBiomeHeightData.getBiome().getSlopeMap().slopeDatas.getFirst().block.getDefaultState();
                BlockState underSurfaceBlock;

                if(DIRT_HEIGHT + height < waterHeight && surfaceBlock == Blocks.GRASS_BLOCK.getDefaultState()) {
                    surfaceBlock = Blocks.DIRT.getDefaultState();
                    underSurfaceBlock = surfaceBlock;
                } else if(DIRT_HEIGHT + height < waterHeight && surfaceBlock == BlockRegistryME.CHALKSOIL_GRASS_BLOCK.getDefaultState()) {
                    surfaceBlock = BlockRegistryME.CHALKSOIL.getDefaultState();
                    underSurfaceBlock = surfaceBlock;
                }else if(DIRT_HEIGHT + height < waterHeight && surfaceBlock == BlockRegistryME.LOAM_GRASS_BLOCK.getDefaultState()) {
                    surfaceBlock = BlockRegistryME.LOAM.getDefaultState();
                    underSurfaceBlock = surfaceBlock;
                } else if(DIRT_HEIGHT + height < waterHeight && surfaceBlock == BlockRegistryME.PEAT_GRASS_BLOCK.getDefaultState()) {
                    surfaceBlock = BlockRegistryME.PEAT.getDefaultState();
                    underSurfaceBlock = surfaceBlock;
                } else if(DIRT_HEIGHT + height < waterHeight && surfaceBlock == BlockRegistryME.SILT_GRASS_BLOCK.getDefaultState()) {
                    surfaceBlock = BlockRegistryME.SILT.getDefaultState();
                    underSurfaceBlock = surfaceBlock;
                } else {
                    surfaceBlock = customHeightBiomeHeightData.getBiome().getSlopeMap().getBlockAtAngle(slopeAngle).getDefaultState();
                    if(surfaceBlock == Blocks.GRASS_BLOCK.getDefaultState() || surfaceBlock == BlockRegistryME.SNOWY_GRASS_BLOCK.getDefaultState()) {
                        underSurfaceBlock = Blocks.DIRT.getDefaultState();
                    } else if(surfaceBlock == BlockRegistryME.CHALKSOIL_GRASS_BLOCK.getDefaultState()) {
                        underSurfaceBlock = BlockRegistryME.CHALKSOIL.getDefaultState();
                    }else if(surfaceBlock == BlockRegistryME.LOAM_GRASS_BLOCK.getDefaultState()) {
                        underSurfaceBlock = BlockRegistryME.LOAM.getDefaultState();
                    } else if(surfaceBlock == BlockRegistryME.PEAT_GRASS_BLOCK.getDefaultState()) {
                        underSurfaceBlock = BlockRegistryME.PEAT.getDefaultState();
                    } else if(surfaceBlock == BlockRegistryME.SILT_GRASS_BLOCK.getDefaultState()) {
                        underSurfaceBlock = BlockRegistryME.SILT.getDefaultState();
                    }
                    else underSurfaceBlock = surfaceBlock;
                }

                if(!riftMouth) {
                    out.set(x, (int) (HEIGHT + height - 1), z, underSurfaceBlock);
                    for(int y = (int) (HEIGHT + height); y < DIRT_HEIGHT + height; y++) {
                        out.set(x, y, z, underSurfaceBlock);
                    }
                    out.set(x, (int) (DIRT_HEIGHT + height), z, surfaceBlock);
                }

                if(biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM || biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM_PIT) {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= 100; y++) {
                        out.set(x, y, z, Blocks.LAVA.getDefaultState());
                    }
                    if(DIRT_HEIGHT + height < 110) {
                        out.set(x, (int) (DIRT_HEIGHT + height), z, Blocks.MAGMA_BLOCK.getDefaultState());
                    }
                } else {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= waterHeight; y++) {
                        out.set(x, y, z, Blocks.WATER.getDefaultState());
                    }
                }

                if(ServerConfigME.ENABLE_PROCEDURAL_STRUCTURES) {
                    ProceduralStructures.generateStructures(customHeightBiomeHeightData, chunk, posX, (int) (DIRT_HEIGHT + height), posZ);
                }
            }
        }
        out.finish();
        caves.decorate(chunk);
    }

    private float getTerrainSlope(MiddleEarthHeightMap.ChunkHeights heights, int x, int z) {
        int offset = 3;
        float height = heights.at(x, z);
        float eastHeight = heights.at(x + offset, z);
        float southHeight = heights.at(x, z + offset);

        float eastSlope = Math.abs((eastHeight - height) / offset);
        float southSlope = Math.abs((southHeight - height) / offset);
        float highestSlope = (eastSlope + southSlope) / 2;

        return (float) Math.toDegrees(Math.atan(highestSlope));
    }

    public double getStructureWeightAt(StructureAccessor structures, Chunk chunk, int x, int y, int z) {
        StructureWeightSampler sampler = StructureWeightSampler.createStructureWeightSampler(structures, chunk.getPos());
        DensityFunction.UnblendedNoisePos unblendedNoisePos = new DensityFunction.UnblendedNoisePos(x, y, z);
        return sampler.sample(unblendedNoisePos);
    }

    private void trySetBlock(CaveSampler caves, ColumnWriter out, int x, int y, int z, int posX, int posZ, BlockState blockState) {
        if(out.outOfHeightLimit(y)) return;
        int result = caves.sample(posX, y, posZ);
        if(result == CaveSampler.STONE) {
            out.set(x, y, z, blockState);
        } else if(result == CaveSampler.WATER) {
            out.set(x, y, z, Blocks.WATER.getDefaultState());
        }
    }

    // Writes the column blocks straight into the chunk sections. ProtoChunk.setBlockState does the same plus light and
    // heightmap bookkeeping; light is not tracked before the INITIALIZE_LIGHT status, and the heightmaps are rebuilt
    // in finish() the way vanilla does after its noise fill. Other chunk types keep the regular path.
    private static final class ColumnWriter {
        private final Chunk chunk;
        private final boolean direct;
        private final ChunkSection[] sections;
        private final int startX;
        private final int startZ;
        private final BlockPos.Mutable pos = new BlockPos.Mutable();

        ColumnWriter(Chunk chunk) {
            this.chunk = chunk;
            this.direct = chunk.getClass() == ProtoChunk.class && !chunk.getStatus().isAtLeast(ChunkStatus.INITIALIZE_LIGHT);
            this.sections = new ChunkSection[chunk.countVerticalSections()];
            this.startX = chunk.getPos().getStartX();
            this.startZ = chunk.getPos().getStartZ();
        }

        boolean outOfHeightLimit(int y) {
            return chunk.isOutOfHeightLimit(y);
        }

        void set(int x, int y, int z, BlockState state) {
            if (chunk.isOutOfHeightLimit(y)) return;
            if (!direct) {
                chunk.setBlockState(pos.set(startX + x, y, startZ + z), state);
                return;
            }
            int index = chunk.getSectionIndex(y);
            ChunkSection section = sections[index];
            if (section == null) section = sections[index] = chunk.getSection(index);
            if (section.isEmpty() && state.isOf(Blocks.AIR)) return;
            section.setBlockState(x, y & 15, z, state, false);
        }

        void finish() {
            if (direct) Heightmap.populateHeightmaps(chunk, chunk.getStatus().getHeightmapTypes());
        }
    }

    // answers what buildSurface would leave solid, for structure code; one sampler per chunk, kept only for the life of the probe
    public static final class CaveProbe {
        private final long seed;
        private final int bottomY;
        private final int topY;
        private final Map<Long, CaveSampler> samplers = new HashMap<>();

        public CaveProbe(long seed, HeightLimitView world) {
            this.seed = seed;
            this.bottomY = world.getBottomY();
            this.topY = world.getTopYInclusive();
        }

        public boolean solid(int x, int y, int z) {
            if (y < bottomY) return true;
            if (y > topY) return false;
            CaveSampler sampler = samplers.computeIfAbsent(ChunkPos.toLong(x >> 4, z >> 4), key -> new CaveSampler(x & ~15, z & ~15, bottomY, topY, seed));
            return sampler.sample(x, y, z) == CaveSampler.STONE;
        }
    }

    // Cave noise sampled every 2 blocks and blended in between, like vanilla's noise cells.
    // Corners sit on fixed world positions, so neighbouring chunks share their edges.
    private static final class CaveSampler {
        private static final int CELL = 2;
        private static final int CORNERS = 16 / CELL + 1;
        private static final int VALUES = 6;
        private static final int MISTY_LATTICE = 8;
        static final int AIR = 0;
        static final int STONE = 1;
        static final int WATER = 2;

        private final int startX;
        private final int startZ;
        private final int minCellY;
        private static final int PAGE_LAYERS = 16;
        private static final int LAYER_SIZE = CORNERS * CORNERS * VALUES;
        private static final int MISTY_LAYER_SIZE = CORNERS * CORNERS * MistyCaves.VALUES;
        private final int layerCount;
        private final float[][] layerPages;
        private final boolean[] layerDone;
        private final MistyCaves misty;
        private final float[] mistyWeight; // null when no corner of the chunk is Misty
        private final float[] riftScale;
        private final float[][] mistyPages;
        private final boolean[] mistyDone;
        private final MistyCaves.Shaft[] shafts;
        private final MistyCaves.Lake[] lakes;
        private final MistyCaves.Road[] roads;
        private final float[][] lakeWobble;
        private final int[] surfaceY;

        // the blend weights and corner blends of the column being sampled; a stamp marks a slot as filled for that column
        private int columnX = -1;
        private int columnZ = -1;
        private int cellX;
        private int cellZ;
        private float fx;
        private float fz;
        private int stamp;
        private int weightStamp;
        private float columnWeight;
        private final int[] baseStamp;
        private final float[] baseBlend;
        private int[] mistyStamp;
        private float[] mistyBlend;

        CaveSampler(int startX, int startZ, int bottomY, int topY, long seed) {
            this.startX = startX;
            this.startZ = startZ;
            this.minCellY = Math.floorDiv(bottomY, CELL);
    this.layerCount = Math.floorDiv(topY, CELL) - minCellY + 2;
            this.layerPages = new float[(layerCount + PAGE_LAYERS - 1) / PAGE_LAYERS][];
            this.layerDone = new boolean[layerCount];
            this.baseStamp = new int[layerCount * VALUES];
            this.baseBlend = new float[layerCount * VALUES];

            // weights sit on a world-fixed 8 block lattice and are interpolated to the corners
            int lattice = 16 / MISTY_LATTICE + 1;
            MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
            float[] latticeWeights = new float[lattice * lattice];
            boolean any = false;
            for (int i = 0; i < lattice; i++) {
                for (int j = 0; j < lattice; j++) {
                    float w = MistyCaves.weight(map, startX + i * MISTY_LATTICE, startZ + j * MISTY_LATTICE);
                    latticeWeights[i * lattice + j] = w;
                    any |= w > 0;
                }
            }
            if (!any) {
                this.misty = null;
                this.mistyWeight = null;
                this.riftScale = null;
                this.mistyPages = null;
                this.mistyDone = null;
                this.shafts = null;
                this.lakes = null;
                this.roads = null;
                this.lakeWobble = null;
                this.surfaceY = null;
                return;
            }
            this.misty = new MistyCaves(seed);
            this.mistyWeight = new float[CORNERS * CORNERS];
            this.riftScale = new float[CORNERS * CORNERS];
            this.mistyPages = new float[layerPages.length][];
            this.mistyDone = new boolean[layerCount];
            this.mistyStamp = new int[layerCount * MistyCaves.VALUES];
            this.mistyBlend = new float[layerCount * MistyCaves.VALUES];
            this.shafts = misty.shaftsNear(map, startX, startZ);
            this.lakes = misty.lakesNear(map, startX, startZ);
            this.roads = misty.roadsNear(map, startX, startZ);
            this.lakeWobble = new float[lakes.length][16 * 16 * 2];
            for (int i = 0; i < lakes.length; i++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) misty.lakeColumn(lakes[i], startX + x, startZ + z, lakeWobble[i], (x * 16 + z) * 2);
                }
            }
            this.surfaceY = new int[16 * 16];
            int ratio = MISTY_LATTICE / CELL;
            for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                    int i = Math.min(cornerX / ratio, lattice - 2);
                    int j = Math.min(cornerZ / ratio, lattice - 2);
                    float fx = (float) (cornerX - i * ratio) / ratio;
                    float fz = (float) (cornerZ - j * ratio) / ratio;
                    float c00 = latticeWeights[i * lattice + j];
                    float c10 = latticeWeights[(i + 1) * lattice + j];
                    float c01 = latticeWeights[i * lattice + j + 1];
                    float c11 = latticeWeights[(i + 1) * lattice + j + 1];
                    float top = c00 + (c10 - c00) * fx;
                    float bottom = c01 + (c11 - c01) * fx;
                    float w = top + (bottom - top) * fz;
                    int corner = cornerX * CORNERS + cornerZ;
                    mistyWeight[corner] = w;
                    if (w > 0) riftScale[corner] = w * misty.riftCluster(startX + cornerX * CELL, startZ + cornerZ * CELL);
                }
            }
        }

        private void selectColumn(int localX, int localZ) {
            if (localX == columnX && localZ == columnZ) return;
            columnX = localX;
            columnZ = localZ;
            cellX = localX / CELL;
            cellZ = localZ / CELL;
            fx = (float) (localX % CELL) / CELL;
            fz = (float) (localZ % CELL) / CELL;
            stamp++;
        }

        private float weight() {
            if (weightStamp != stamp) {
                columnWeight = bilerp2(mistyWeight, cellX, cellZ, fx, fz);
                weightStamp = stamp;
            }
            return columnWeight;
        }

        int sample(int x, int y, int z) {
            selectColumn(x - startX, z - startZ);
            if (misty == null) return baseSolid(x, y, z) ? STONE : AIR;
            if (lakes.length > 0 && MistyCaves.usesLake(y)) {
                int column = ((x - startX) * 16 + (z - startZ)) * 2;
                for (int i = 0; i < lakes.length; i++) {
                    int state = MistyCaves.lakeState(lakes[i], lakeWobble[i][column], lakeWobble[i][column + 1], x, y, z);
                    if (state == MistyCaves.LAKE_STONE) return STONE;
                    if (state == MistyCaves.LAKE_WATER) return WATER;
                    if (state == MistyCaves.LAKE_AIR) return AIR;
                }
                for (MistyCaves.Lake lake : lakes) {
                    if (MistyCaves.inLakeTunnel(lake, x, y, z)) return AIR;
                }
            }
            if (roads.length > 0 && MistyCaves.usesRoad(y)) {
                int road = roadAt(x, y, z);
                if (road == STONE) return STONE;
                if (road == AIR) return AIR;
            }
            return baseSolid(x, y, z) && mistySolid(x, y, z) ? STONE : AIR;
        }

        // STONE for the apron at a road mouth, AIR inside a road, anything else when no road is involved
        private int roadAt(int x, int y, int z) {
            float weight = weight();
            if (weight <= 0) return -1;
            for (MistyCaves.Road road : roads) {
                if (misty.roadApron(road, x, y, z, weight)) return STONE;
            }
            for (MistyCaves.Road road : roads) {
                if (misty.roadOpen(road, x, y, z)) return AIR;
            }
            return -1;
        }

        void setSurface(int x, int z, int y) {
            if (surfaceY != null) surfaceY[(x - startX) * 16 + (z - startZ)] = y;
        }

        void decorate(Chunk chunk) {
            if (misty == null) return;
            float[] weights = new float[16 * 16];
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    weights[x * 16 + z] = bilerp2(mistyWeight, x / CELL, z / CELL, (float) (x % CELL) / CELL, (float) (z % CELL) / CELL);
                }
            }
            MistyCaveDecorator.decorate(chunk, misty, lakes, surfaceY, weights);
        }

        private boolean mistySolid(int x, int y, int z) {
            boolean rift = MistyCaves.usesRift(y);
            boolean chamber = MistyCaves.usesChamber(y);
            boolean shaft = shafts.length > 0 && MistyCaves.usesShaft(y);
            boolean stream = MistyCaves.usesStream(y);
            if (!rift && !chamber && !shaft && !stream) return true;
            if (weight() <= 0) return true;
            int cellY = Math.floorDiv(y, CELL) - minCellY;
            float fy = (float) Math.floorMod(y, CELL) / CELL;
            if (rift && lerp3m(cellY, MistyCaves.RIFT, fy) > 0) return false;
            if (chamber && lerp3m(cellY, MistyCaves.CHAMBER, fy) > 0) return false;
            if (shaft && lerp3m(cellY, MistyCaves.SHAFT, fy) > 0) return false;
            if (stream && lerp3m(cellY, MistyCaves.STREAM, fy) > 0) return false;
            return true;
        }

        // true where a rift breaks through the surface skin
        boolean mouthOpen(int x, int z, int surfaceY) {
            if (misty == null) return false;
            int localX = x - startX;
            int localZ = z - startZ;
            float w = bilerp2(mistyWeight, localX / CELL, localZ / CELL, (float) (localX % CELL) / CELL, (float) (localZ % CELL) / CELL);
            return misty.mouthOpen(x, z, w, surfaceY);
        }

        private static float bilerp2(float[] grid, int cellX, int cellZ, float fx, float fz) {
            float c00 = grid[cellX * CORNERS + cellZ];
            float c10 = grid[(cellX + 1) * CORNERS + cellZ];
            float c01 = grid[cellX * CORNERS + cellZ + 1];
            float c11 = grid[(cellX + 1) * CORNERS + cellZ + 1];
            float top = c00 + (c10 - c00) * fx;
            float bottom = c01 + (c11 - c01) * fx;
            return top + (bottom - top) * fz;
        }

        private float lerp3m(int cellY, int v, float fy) {
            float b = mistyBlend(cellY, v);
            float a = mistyBlend(cellY + 1, v);
            return b + (a - b) * fy;
        }

        private float mistyBlend(int layer, int v) {
            int slot = layer * MistyCaves.VALUES + v;
            if (mistyStamp[slot] == stamp) return mistyBlend[slot];
            float[] page = mistyLayer(layer);
            int base = (layer % PAGE_LAYERS) * MISTY_LAYER_SIZE;
            float c00 = page[base + (cellX * CORNERS + cellZ) * MistyCaves.VALUES + v];
            float c10 = page[base + ((cellX + 1) * CORNERS + cellZ) * MistyCaves.VALUES + v];
            float c01 = page[base + (cellX * CORNERS + cellZ + 1) * MistyCaves.VALUES + v];
            float c11 = page[base + ((cellX + 1) * CORNERS + cellZ + 1) * MistyCaves.VALUES + v];
            float top = c00 + (c10 - c00) * fx;
            float bottom = c01 + (c11 - c01) * fx;
            float blend = top + (bottom - top) * fz;
            mistyBlend[slot] = blend;
            mistyStamp[slot] = stamp;
            return blend;
        }

        private float[] mistyLayer(int cellY) {
            int pageIndex = cellY / PAGE_LAYERS;
            float[] page = mistyPages[pageIndex];
            if (page == null) page = mistyPages[pageIndex] = new float[PAGE_LAYERS * MISTY_LAYER_SIZE];
            if (!mistyDone[cellY]) {
                float[] layer = page;
                int layerBase = (cellY % PAGE_LAYERS) * MISTY_LAYER_SIZE;
                int y = (cellY + minCellY) * CELL;
                boolean rift = MistyCaves.usesRift(y);
                boolean chamber = MistyCaves.usesChamber(y);
                boolean shaft = shafts.length > 0 && MistyCaves.usesShaft(y);
                boolean stream = MistyCaves.usesStream(y);
                float[] profiles = null;
                if (shaft) {
                    profiles = new float[shafts.length * 3];
                    for (int i = 0; i < shafts.length; i++) misty.shaftProfile(shafts[i], y, profiles, i * 3);
                }
                for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                    for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                        int corner = cornerX * CORNERS + cornerZ;
                        int offset = layerBase + corner * MistyCaves.VALUES;
                        int x = startX + cornerX * CELL;
                        int z = startZ + cornerZ * CELL;
                        float w = mistyWeight[corner];
                        layer[offset + MistyCaves.RIFT] = rift && riftScale[corner] > 0 ? misty.riftMargin(x, y, z, riftScale[corner]) : -1;
                        layer[offset + MistyCaves.CHAMBER] = chamber && w > 0 ? misty.chamberMargin(x, y, z, w) : -1;
                        float shaftMargin = -1;
                        if (shaft && w > 0) {
                            for (int i = 0; i < shafts.length; i++) {
                                if (profiles[i * 3] <= 0) continue;
                                shaftMargin = Math.max(shaftMargin, MistyCaves.shaftMargin(shafts[i], profiles[i * 3], profiles[i * 3 + 1], profiles[i * 3 + 2], x, y, z, w));
                            }
                        }
                        layer[offset + MistyCaves.SHAFT] = shaftMargin;
                        layer[offset + MistyCaves.STREAM] = stream && w > 0 ? misty.streamMargin(x, y, z, w) : -1;
                    }
                }
                mistyDone[cellY] = true;
            }
            return page;
        }

        private boolean baseSolid(int x, int y, int z) {
            int cellY = Math.floorDiv(y, CELL) - minCellY;
            float fy = (float) Math.floorMod(y, CELL) / CELL;

            float noise = y < WATER_HEIGHT ? lerp3(cellY, 0, fy) : 0;
            if (noise >= 0.4f
                    || lerp3(cellY, 1, fy) >= 0.75f
                    || lerp3(cellY, 2, fy) >= 0.8f) return false;
            // the spaghetti noises are blended signed and only then made absolute, which keeps the thin tunnels
            float spaghetti = Math.abs(lerp3(cellY, 3, fy))
                    + Math.abs(lerp3(cellY, 4, fy))
                    + Math.abs(lerp3(cellY, 5, fy));
            return spaghetti / 3 > 0.09f;
        }

        private float lerp3(int cellY, int v, float fy) {
            float b = baseBlend(cellY, v);
            float a = baseBlend(cellY + 1, v);
            return b + (a - b) * fy;
        }

        private float baseBlend(int layer, int v) {
            int slot = layer * VALUES + v;
            if (baseStamp[slot] == stamp) return baseBlend[slot];
            float[] page = layer(layer);
            int base = (layer % PAGE_LAYERS) * LAYER_SIZE;
            float c00 = page[base + index(cellX, cellZ, v)];
            float c10 = page[base + index(cellX + 1, cellZ, v)];
            float c01 = page[base + index(cellX, cellZ + 1, v)];
            float c11 = page[base + index(cellX + 1, cellZ + 1, v)];
            float top = c00 + (c10 - c00) * fx;
            float bottom = c01 + (c11 - c01) * fx;
            float blend = top + (bottom - top) * fz;
            baseBlend[slot] = blend;
            baseStamp[slot] = stamp;
            return blend;
        }

        private static int index(int cornerX, int cornerZ, int v) {
            return (cornerX * CORNERS + cornerZ) * VALUES + v;
        }

        private float[] layer(int cellY) {
            int pageIndex = cellY / PAGE_LAYERS;
            float[] page = layerPages[pageIndex];
            if (page == null) page = layerPages[pageIndex] = new float[PAGE_LAYERS * LAYER_SIZE];
            if (!layerDone[cellY]) {
                int layerBase = (cellY % PAGE_LAYERS) * LAYER_SIZE;
                int y = (cellY + minCellY) * CELL;
                // everything that depends on y alone is evaluated once per layer
                double tanY = Math.tan((float) y / CAVE_STRETCH_V);
                float tanYf = (float) Math.tan((float) y / CAVE_STRETCH_V);
                float y1 = (float) y / (CAVE_STRETCH_V * 0.5f);
                float y2 = (float) y / 60;
                float y3 = (float) y / 30;
                float y4 = (float) y / CAVE_STRETCH_V;
                for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                    for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                        int x = startX + cornerX * CELL;
                        int z = startZ + cornerZ * CELL;
                        int offset = layerBase + index(cornerX, cornerZ, 0);
                        float noise = (float) SimplexNoise.noise(
                                (float) x / CAVE_STRETCH_H, tanY, (float) z / CAVE_STRETCH_H);
                        noise += 0.5f * (float) SimplexNoise.noise(
                                (float) x / (CAVE_STRETCH_H * 0.5f), y1, (float) z / (CAVE_STRETCH_H * 0.5f));
                        noise = noise / (1 + 0.5f);

                        float noise3 = (float) SimplexNoise.noise((float) x / 90, y2, (float) z / 90);
                        float miniNoise = (float) SimplexNoise.noise((float) x / 40, y3, (float) z / 40);

                        page[offset] = noise;
                        page[offset + 1] = noise3;
                        page[offset + 2] = miniNoise;
                        page[offset + 3] = (float) SimplexNoise.noise(
                                (float) x / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), tanYf, (float) z / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), 57142);
                        page[offset + 4] = (float) SimplexNoise.noise(
                                (float) (98153 + z) / SPAGHETTI_CAVE_STRETCH_H, y4, (float) x / SPAGHETTI_CAVE_STRETCH_H, 0);
                        page[offset + 5] = (float) SimplexNoise.noise(
                                (float) (1243624 + z) / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), y4, (float) x / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), 0);
                    }
                }
                layerDone[cellY] = true;
            }
            return page;
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
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
        super.generateFeatures(world, chunk, structureAccessor);
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
        return 512;
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
