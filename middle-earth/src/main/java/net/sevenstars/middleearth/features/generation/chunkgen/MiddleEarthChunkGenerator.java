package net.sevenstars.middleearth.features.generation.chunkgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import net.minecraft.world.chunk.PaletteProvider;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadableContainer;
import net.minecraft.util.collection.PackedIntegerArray;
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
import net.sevenstars.middleearth.features.generation.biomes.caves.DeepThemes;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.caves.BlockBuffer;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

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
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.NAMELESS_DEEPS),
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.GNAWED_TUNNELS),
                    biomeRegistry.getOrThrow(BiomeKeyRegistryME.GLOWWORM_GROTTO),
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

    // The structure starts the chunk references, like StructureAccessor.getStructureStarts, but a start chunk the region does not
    // reach (a big mine's can be 8 chunks away) is skipped: asking for it throws.
    private static List<StructureStart> reachableStarts(ChunkRegion region, Chunk chunk) {
        List<StructureStart> starts = new ArrayList<>();
        for (Map.Entry<Structure, it.unimi.dsi.fastutil.longs.LongSet> entry : chunk.getStructureReferences().entrySet()) {
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
        DepthBands.ChunkFloors floors = DepthBands.ChunkFloors.forChunk(middleEarthMapRuntime, chunk.getPos().getStartX(), chunk.getPos().getStartZ(), seed);
        if(floors == null) out.fillBedrock(DepthBands.SHALLOW_FLOOR);
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

                float caveBlendNoise = caveBlendNoise(posX, posZ, seed);
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

                int floor = floors == null ? DepthBands.SHALLOW_FLOOR : floors.floor(x, z);
                if(floor < DepthBands.SHALLOW_FLOOR) {
                    // The deeps have no lava. Their bottom is water, bare rock or shallow water, by the region, with a rock wall across the
                    // deeps where two regions meet and where the old lava sea of a shallow column lies next to them.
                    DeepThemes.Theme theme = DeepThemes.at(posX, posZ, seed);
                    DepthBands.Edges edges = DepthBands.Edges.of((int) (DIRT_HEIGHT + height), floor, caveBlendNoise);
                    boolean wall = DeepThemes.border(posX, posZ, seed);
                    int beside = floors.touchesShallow(x, z) ? LAVA_HEIGHT : Integer.MIN_VALUE;
                    int water = theme == DeepThemes.Theme.WET ? DepthBands.BOTTOM_BAND : theme == DeepThemes.Theme.GROTTO ? DepthBands.SHALLOW_WATER : 0;
                    // rock above this column's water, up to where a higher neighbour's water reaches, so none is left open to the side
                    int sealed = water > 0 ? floors.highestFloor(x, z) + water : Integer.MIN_VALUE;
                    boolean island = theme == DeepThemes.Theme.GROTTO && DeepThemes.island(posX, posZ, seed);
                    for(int y = bottomY; y <= floor; y++) {
                        out.set(x, y, z, Blocks.BEDROCK.getDefaultState());
                    }
                    for(int y = floor + 1; y <= floor + water; y++) {
                        out.set(x, y, z, Blocks.WATER.getDefaultState());
                    }
                    for(int y = floor + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
                        boolean solid = y <= beside
                                || (theme == DeepThemes.Theme.GNAWED && y <= floor + DepthBands.BOTTOM_BAND)
                                || (y > floor + water && y <= sealed)
                                || (island && y <= floor + DepthBands.SHALLOW_WATER)
                                || (wall && y < edges.top(DepthBands.Band.DEEPS) && !DeepThemes.passage(posX, y, posZ, edges, seed));
                        if(solid) {
                            out.set(x, y, z, StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState());
                        } else {
                            trySetBlock(caves, out, x, y, z, posX, posZ, StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState());
                        }
                    }
                } else {
                    if(floors != null) {
                        for(int y = bottomY; y < DepthBands.SHALLOW_FLOOR; y++) {
                            out.set(x, y, z, Blocks.BEDROCK.getDefaultState());
                        }
                    }
                    out.set(x, DepthBands.SHALLOW_FLOOR, z, Blocks.BEDROCK.getDefaultState());
                    for(int y = DepthBands.SHALLOW_FLOOR + 1; y <= LAVA_HEIGHT; y++) {
                        out.set(x, y, z, Blocks.LAVA.getDefaultState());
                    }

                    for(int y = DepthBands.SHALLOW_FLOOR + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
                        trySetBlock(caves, out, x, y, z, posX, posZ, StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState());
                    }
                    if(Math.random() < 0.5f) out.set(x, DepthBands.SHALLOW_FLOOR + 1, z, Blocks.BEDROCK.getDefaultState());
                }

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
                    // those write into the chunk directly, so what is buffered goes in first
                    if(ProceduralStructures.writes(customHeightBiomeHeightData)) out.sync();
                    ProceduralStructures.generateStructures(customHeightBiomeHeightData, chunk, posX, (int) (DIRT_HEIGHT + height), posZ);
                }
            }
        }
        // the decorations read and write the same buffers, so the sections are built once, after them
        caves.decorate(chunk, out);
        out.finish();
    }

    public static float caveBlendNoise(int x, int z, long seed) {
        return (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) (x + seed) / 24f,  (double) (z + seed) / 24f)) - CAVE_NOISE);
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

    // Writes the column blocks into the chunk sections. ProtoChunk.setBlockState does the same plus light and
    // heightmap bookkeeping; light is not tracked before the INITIALIZE_LIGHT status, and the heightmaps are rebuilt
    // in finish() the way vanilla does after its noise fill. Other chunk types keep the regular path.
    // Each section the fill touches is first collected as a plain array of block states and turned into a section in one go,
    // which spares the palette from growing block by block; the blocks are the same, only the palette order can differ.
    private static final class ColumnWriter implements BlockBuffer {
        private static final PaletteProvider<BlockState> BLOCK_PALETTE = PaletteProvider.forBlockStates(Block.STATE_IDS);
        private static final BlockState AIR = Blocks.AIR.getDefaultState();
        private static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
        private static final int SECTION_BLOCKS = 16 * 16 * 16;
        // the first builds are compared with their source, and a mismatch turns the shortcut off for good
        private static final int CHECKED_BUILDS = 64;
        private static final AtomicInteger builds = new AtomicInteger();
        private static volatile boolean shortcut = true;

        private static final class Pool {
            boolean busy;
            BlockState[][] arrays = new BlockState[0][];
            final int[] indices = new int[SECTION_BLOCKS];
        }

        private static final ThreadLocal<Pool> POOL = ThreadLocal.withInitial(Pool::new);

        private final Chunk chunk;
        private final boolean direct;
        private final ChunkSection[] sections;
        private final int startX;
        private final int startZ;
        private final BlockPos.Mutable pos = new BlockPos.Mutable();
        private final Pool pool;
        private final BlockState[][] active; // the section arrays in use, by section index
        private final BlockState[] fills; // what each active array started as
        private final boolean[] bedrockSections;
        private boolean buffered;

        ColumnWriter(Chunk chunk) {
            this.chunk = chunk;
            this.direct = chunk.getClass() == ProtoChunk.class && !chunk.getStatus().isAtLeast(ChunkStatus.INITIALIZE_LIGHT);
            this.sections = new ChunkSection[chunk.countVerticalSections()];
            this.startX = chunk.getPos().getStartX();
            this.startZ = chunk.getPos().getStartZ();
            Pool pool = direct && shortcut ? POOL.get() : null;
            if (pool != null && !pool.busy) {
                pool.busy = true;
                if (pool.arrays.length < sections.length) pool.arrays = Arrays.copyOf(pool.arrays, sections.length);
                this.pool = pool;
                this.buffered = true;
                this.active = new BlockState[sections.length][];
                this.fills = new BlockState[sections.length];
                this.bedrockSections = new boolean[sections.length];
            } else {
                this.pool = null;
                this.active = null;
                this.fills = null;
                this.bedrockSections = null;
            }
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
            if (buffered) {
                BlockState[] array = active[index];
                if (array == null) array = open(index);
                if (array != null) {
                    array[(y & 15) << 8 | z << 4 | x] = state;
                    return;
                }
            }
            ChunkSection section = sections[index];
            if (section == null) section = sections[index] = chunk.getSection(index);
            if (section.isEmpty() && state.isOf(Blocks.AIR)) return;
            section.setBlockState(x, y & 15, z, state, false);
        }

        @Override
        public BlockState get(int x, int y, int z) {
            if (chunk.isOutOfHeightLimit(y)) return Blocks.VOID_AIR.getDefaultState();
            if (!direct) return chunk.getBlockState(pos.set(x, y, z));
            int index = chunk.getSectionIndex(y);
            if (buffered) {
                BlockState[] array = active[index];
                if (array != null) return array[(y & 15) << 8 | (z - startZ) << 4 | (x - startX)];
            }
            ChunkSection section = sections[index] != null ? sections[index] : chunk.getSection(index);
            return section.getBlockState(x - startX, y & 15, z - startZ);
        }

        @Override
        public void put(int x, int y, int z, BlockState state) {
            set(x - startX, y, z - startZ, state);
        }

        // null for a section that did not start out empty or bedrock, which takes the block by block path
        private BlockState[] open(int index) {
            ChunkSection section = sections[index] != null ? sections[index] : chunk.getSection(index);
            BlockState fill;
            if (bedrockSections[index]) fill = BEDROCK;
            else if (section.isEmpty() && !section.hasAny(state -> state != AIR)) fill = AIR;
            else {
                sections[index] = section;
                return null;
            }
            BlockState[] array = pool.arrays[index];
            if (array == null) array = pool.arrays[index] = new BlockState[SECTION_BLOCKS];
            Arrays.fill(array, fill);
            active[index] = array;
            fills[index] = fill;
            return array;
        }

        // Every section below topY becomes one bedrock block, so each keeps a single palette entry
        void fillBedrock(int topY) {
            BlockState bedrock = Blocks.BEDROCK.getDefaultState();
            for (int index = 0; chunk.getBottomY() + (index + 1) * 16 <= topY; index++) {
                if (!direct) {
                    for (int y = chunk.getBottomY() + index * 16; y < chunk.getBottomY() + (index + 1) * 16; y++) {
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) chunk.setBlockState(pos.set(startX + x, y, startZ + z), bedrock);
                        }
                    }
                    continue;
                }
                ChunkSection section = new ChunkSection(new PalettedContainer<>(bedrock, BLOCK_PALETTE), chunk.getSection(index).getBiomeContainer());
                chunk.getSectionArray()[index] = section;
                sections[index] = section;
                if (bedrockSections != null) bedrockSections[index] = true;
            }
        }

        // everything buffered goes into the chunk, and the rest is written block by block
        void sync() {
            if (!buffered) return;
            flush();
            buffered = false;
        }

        private void flush() {
            for (int index = 0; index < active.length; index++) {
                BlockState[] array = active[index];
                if (array == null) continue;
                active[index] = null;
                ChunkSection old = sections[index] != null ? sections[index] : chunk.getSection(index);
                PalettedContainer<BlockState> container = build(array);
                if (container != null) {
                    ChunkSection section = new ChunkSection(container, old.getBiomeContainer());
                    chunk.getSectionArray()[index] = section;
                    sections[index] = section;
                } else {
                    sections[index] = old;
                    for (int i = 0; i < SECTION_BLOCKS; i++) {
                        if (array[i] != fills[index]) old.setBlockState(i & 15, i >> 8, i >> 4 & 15, array[i], false);
                    }
                }
            }
        }

        // null when the shortcut cannot be trusted
        private PalettedContainer<BlockState> build(BlockState[] array) {
            if (!shortcut) return null;
            List<BlockState> palette = new ArrayList<>();
            int[] indices = pool.indices;
            BlockState last = null;
            int lastIndex = 0;
            for (int i = 0; i < SECTION_BLOCKS; i++) {
                BlockState state = array[i];
                if (state != last) {
                    int found = palette.indexOf(state);
                    if (found < 0) {
                        found = palette.size();
                        palette.add(state);
                    }
                    last = state;
                    lastIndex = found;
                }
                indices[i] = lastIndex;
            }
            if (palette.size() > 256) return null;
            ReadableContainer.Serialized<BlockState> serialized;
            if (palette.size() == 1) {
                serialized = new ReadableContainer.Serialized<>(palette, Optional.empty());
            } else {
                int bits = Math.max(4, 32 - Integer.numberOfLeadingZeros(palette.size() - 1));
                long[] data = new PackedIntegerArray(bits, SECTION_BLOCKS, indices).getData();
                serialized = new ReadableContainer.Serialized<>(palette, Optional.of(LongStream.of(data)), bits);
            }
            PalettedContainer<BlockState> container = PalettedContainer.read(BLOCK_PALETTE, serialized).result().orElse(null);
            if (container == null) {
                shortcut = false;
                return null;
            }
            if (builds.getAndIncrement() < CHECKED_BUILDS) {
                for (int i = 0; i < SECTION_BLOCKS; i++) {
                    if (container.get(i & 15, i >> 8, i >> 4 & 15) != array[i]) {
                        shortcut = false;
                        return null;
                    }
                }
            }
            return container;
        }

        void finish() {
            if (direct) {
                if (buffered) flush();
                Heightmap.populateHeightmaps(chunk, chunk.getStatus().getHeightmapTypes());
            }
        }

        void release() {
            if (pool != null) pool.busy = false;
        }
    }

    // answers what buildSurface would leave solid, for structure code; it lives as long as one structure start and holds
    // bounded caches only: the per-chunk contexts of the most recent chunks, whose cave noise is evaluated one corner at a time
    public static final class CaveProbe {
        private static final int MAX_CHUNKS = 128;
        private static final int MAX_FLOORS = 1 << 16;

        private final long seed;
        private final int bottomY;
        private final int topY;
        private final Map<Long, CaveSampler> samplers = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, CaveSampler> eldest) {
                return size() > MAX_CHUNKS;
            }
        };
        private final Map<Long, Integer> floors = new HashMap<>();

        public CaveProbe(long seed, HeightLimitView world) {
            this.seed = seed;
            this.bottomY = world.getBottomY();
            this.topY = world.getTopYInclusive();
        }

        public boolean solid(int x, int y, int z) {
            if (y < bottomY) return true;
            if (y > topY) return false;
            if (y < DepthBands.SHALLOW_FLOOR && y <= deepFloor(x, z)) return true;
            CaveSampler sampler = samplers.computeIfAbsent(ChunkPos.toLong(x >> 4, z >> 4), key -> new CaveSampler(x & ~15, z & ~15, bottomY, topY, seed, true));
            return sampler.sample(x, y, z) == CaveSampler.STONE;
        }

        private int deepFloor(int x, int z) {
            if (floors.size() >= MAX_FLOORS) floors.clear();
            return floors.computeIfAbsent(ChunkPos.toLong(x, z), key -> DepthBands.deepFloor(MiddleEarthMapRuntime.getInstance(), x, z, seed));
        }
    }

    // Cave noise sampled every 2 blocks and blended in between, like vanilla's noise cells.
    // Corners sit on fixed world positions, so neighbouring chunks share their edges.
    private static final class CaveSampler {
        private static final int CELL = 2;
        private static final int CORNERS = 16 / CELL + 1;
        private static final int VALUES = 6;
        private static final int MISTY_LATTICE = 8;
        private static final float CULL_MARGIN = 1e-4f;
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
        private final PagePool pool;
        private final boolean[] layerDone;
        // per cell, 0 not decided yet, 1 every block of it is base-solid, 2 mixed or unknown; null for the probe
        private final byte[] cellVerdict;
        private final MistyCaves misty;
        private final float[] mistyWeight; // null when no corner of the chunk is Misty
        private final float[] riftScale;
        private final float[] streamGates; // the stream's 2D noise per corner, NaN until needed
        private final float[][] mistyPages;
        private final boolean[] mistyDone;
        private final MistyCaves.Shaft[] shafts;
        private final float[][] shaftColumns; // per ravine and corner: distance across, half width
        private final MistyCaves.Lake[] lakes;
        private final float[][] lakeWobble;
        private final int[] surfaceY;
        private final DepthBands.Edges[] cornerEdges; // null where the weight is 0
        private final DeepThemes.Theme[] cornerThemes;
        private final boolean[] cornerBorders;
        private MistyCaves.Worm[] worms; // only when a corner of the chunk lies in the gnawed deeps
        private int riftMinY = Integer.MAX_VALUE;
        private int chamberMinY = Integer.MAX_VALUE;
        private int chamberMaxY = Integer.MIN_VALUE;
        private int streamMinY = Integer.MAX_VALUE;
        private int streamMaxY = Integer.MIN_VALUE;
        private int shaftMinY;
        private int shaftMaxY;
        private int lakeMinY;

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
        // only for the probe: single corners evaluated on demand in place of whole layer pages, dropped when they get too many
        private static final int MAX_CORNERS = 1024;
        private final Map<Long, float[]> baseCorners;
        private final Map<Long, float[]> mistyCorners;

        CaveSampler(int startX, int startZ, int bottomY, int topY, long seed, boolean point) {
            this.cellVerdict = point ? null : new byte[(CORNERS - 1) * (CORNERS - 1) * (Math.floorDiv(topY, CELL) - Math.floorDiv(bottomY, CELL) + 2)];
            this.baseCorners = point ? new HashMap<>() : null;
            this.mistyCorners = point ? new HashMap<>() : null;
            this.startX = startX;
            this.startZ = startZ;
            this.minCellY = Math.floorDiv(bottomY, CELL);
    this.layerCount = Math.floorDiv(topY, CELL) - minCellY + 2;
            int pageCount = (layerCount + PAGE_LAYERS - 1) / PAGE_LAYERS;
            // a page is only read for layers marked done since this sampler began, so pages can be reused as they are
            PagePool pool = point ? null : PAGE_POOL.get();
            if (pool != null && !pool.busy) {
                pool.busy = true;
                this.pool = pool;
                if (pool.base.length < pageCount) pool.base = Arrays.copyOf(pool.base, pageCount);
                if (pool.misty.length < pageCount) pool.misty = Arrays.copyOf(pool.misty, pageCount);
                this.layerPages = pool.base;
            } else {
                this.pool = null;
                this.layerPages = new float[pageCount][];
            }
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
            // a lake tunnel runs out of the Misty region, and the chunks it passes through still carry it
            MistyCaves candidate = new MistyCaves(seed);
            MistyCaves.Lake[] tunnels = any ? null : candidate.lakesNear(map, startX, startZ);
            // and the gnawed deeps are bored by worms wherever the floor is deep, Misty or not
            boolean deepHere = false;
            for (int i = 0; i < lattice && !deepHere; i++) {
                for (int j = 0; j < lattice && !deepHere; j++) {
                    deepHere = DepthBands.deepFloor(map, startX + i * MISTY_LATTICE, startZ + j * MISTY_LATTICE, seed) < DepthBands.SHALLOW_FLOOR;
                }
            }
            if (!any && !deepHere && tunnels.length == 0) {
                this.misty = null;
                this.mistyWeight = null;
                this.riftScale = null;
                this.streamGates = null;
                this.mistyPages = null;
                this.mistyDone = null;
                this.shafts = null;
                this.shaftColumns = null;
                this.lakes = null;
                this.lakeWobble = null;
                this.surfaceY = null;
                this.cornerEdges = null;
                this.cornerThemes = null;
                this.cornerBorders = null;
                return;
            }
            this.misty = candidate;
            this.mistyWeight = new float[CORNERS * CORNERS];
            this.riftScale = new float[CORNERS * CORNERS];
            this.streamGates = new float[CORNERS * CORNERS];
            Arrays.fill(streamGates, Float.NaN);
            this.mistyPages = pool != null ? pool.misty : new float[pageCount][];
            this.mistyDone = new boolean[layerCount];
            this.mistyStamp = new int[layerCount * MistyCaves.VALUES];
            this.mistyBlend = new float[layerCount * MistyCaves.VALUES];
            this.shafts = misty.shaftsNear(map, startX, startZ);
            this.shaftColumns = new float[shafts.length][CORNERS * CORNERS * 2];
            this.lakes = any ? misty.lakesNear(map, startX, startZ) : tunnels;
            this.lakeWobble = new float[lakes.length][16 * 16 * 2];
            for (int i = 0; i < lakes.length; i++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) misty.lakeColumn(lakes[i], startX + x, startZ + z, lakeWobble[i], (x * 16 + z) * 2);
                }
            }
            this.surfaceY = new int[16 * 16];
            this.cornerEdges = new DepthBands.Edges[CORNERS * CORNERS];
            this.cornerThemes = new DeepThemes.Theme[CORNERS * CORNERS];
            this.cornerBorders = new boolean[CORNERS * CORNERS];
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
                    int cornerWorldX = startX + cornerX * CELL;
                    int cornerWorldZ = startZ + cornerZ * CELL;
                    int cornerFloor = DepthBands.deepFloor(map, cornerWorldX, cornerWorldZ, seed);
                    boolean deep = cornerFloor < DepthBands.SHALLOW_FLOOR;
                    if (w <= 0 && !deep) continue;
                    DepthBands.Edges edges = DepthBands.Edges.of((int) (DIRT_HEIGHT + MiddleEarthHeightMap.getCachedHeight(cornerWorldX, cornerWorldZ)),
                            cornerFloor, caveBlendNoise(cornerWorldX, cornerWorldZ, seed));
                    cornerEdges[corner] = edges;
                    cornerThemes[corner] = deep ? DeepThemes.at(cornerWorldX, cornerWorldZ, seed) : DeepThemes.Theme.WET;
                    cornerBorders[corner] = deep && DeepThemes.border(cornerWorldX, cornerWorldZ, seed);
                    if (cornerThemes[corner] == DeepThemes.Theme.GNAWED) {
                        if (worms == null) worms = misty.wormsNear(startX, startZ);
                        chamberMinY = Math.min(chamberMinY, MistyCaves.chamberBottom(edges));
                        chamberMaxY = Math.max(chamberMaxY, edges.top(DepthBands.Band.DEEPS));
                    }
                    if (w <= 0) continue;
                    riftScale[corner] = w * misty.riftCluster(cornerWorldX, cornerWorldZ);
                    if (riftScale[corner] > 0) riftMinY = Math.min(riftMinY, MistyCaves.riftBottom(edges));
                    chamberMinY = Math.min(chamberMinY, MistyCaves.chamberBottom(edges));
                    chamberMaxY = Math.max(chamberMaxY, edges.top(DepthBands.Band.DEEPS));
                    if (MistyCaves.streamBand(edges)) {
                        streamMinY = Math.min(streamMinY, edges.middleBottom() - 2);
                        streamMaxY = Math.max(streamMaxY, edges.top(DepthBands.Band.MIDDLE) + 2);
                    }
                }
            }
            if (shafts.length > 0) {
                for (int i = 0; i < shafts.length; i++) {
                    for (int corner = 0; corner < CORNERS * CORNERS; corner++) {
                        MistyCaves.shaftColumn(shafts[i], startX + corner / CORNERS * CELL, startZ + corner % CORNERS * CELL, shaftColumns[i], corner * 2);
                    }
                }
                int[] range = MistyCaves.shaftRange(shafts);
                shaftMinY = range[0];
                shaftMaxY = range[1];
            }
            if (lakes.length > 0) lakeMinY = MistyCaves.lakeMinY(lakes);
        }

        boolean misty() {
            return misty != null;
        }

        void release() {
            if (pool != null) pool.busy = false;
        }

        private static final class PagePool {
            boolean busy;
            float[][] base = new float[0][];
            float[][] misty = new float[0][];
        }

        private static final ThreadLocal<PagePool> PAGE_POOL = ThreadLocal.withInitial(PagePool::new);

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
            if (lakes.length > 0 && y >= lakeMinY && y <= MistyCaves.lakeMaxY()) {
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
            return (gnawedRock(y) || baseSolid(x, y, z)) && mistySolid(x, y, z) ? STONE : AIR;
        }

        void setSurface(int x, int z, int y) {
            if (surfaceY != null) surfaceY[(x - startX) * 16 + (z - startZ)] = y;
        }

        void decorate(Chunk chunk, BlockBuffer blocks) {
            if (misty == null) return;
            float[] weights = new float[16 * 16];
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    weights[x * 16 + z] = bilerp2(mistyWeight, x / CELL, z / CELL, (float) (x % CELL) / CELL, (float) (z % CELL) / CELL);
                }
            }
            MistyCaveDecorator.decorate(chunk, blocks, misty, lakes, surfaceY, weights);
        }

        private boolean mistySolid(int x, int y, int z) {
            boolean rift = y >= riftMinY;
            boolean chamber = y >= chamberMinY && y <= chamberMaxY;
            boolean shaft = shafts.length > 0 && y >= shaftMinY && y <= shaftMaxY;
            boolean stream = y >= streamMinY && y <= streamMaxY;
            if (!rift && !chamber && !shaft && !stream) return true;
            if (weight() <= 0 && !gnawedRock(y)) return true;
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
            float c00;
            float c10;
            float c01;
            float c11;
            if (mistyCorners == null) {
                float[] page = mistyLayer(layer);
                int base = (layer % PAGE_LAYERS) * MISTY_LAYER_SIZE;
                c00 = page[base + (cellX * CORNERS + cellZ) * MistyCaves.VALUES + v];
                c10 = page[base + ((cellX + 1) * CORNERS + cellZ) * MistyCaves.VALUES + v];
                c01 = page[base + (cellX * CORNERS + cellZ + 1) * MistyCaves.VALUES + v];
                c11 = page[base + ((cellX + 1) * CORNERS + cellZ + 1) * MistyCaves.VALUES + v];
            } else {
                c00 = mistyCorner(layer, cellX, cellZ)[v];
                c10 = mistyCorner(layer, cellX + 1, cellZ)[v];
                c01 = mistyCorner(layer, cellX, cellZ + 1)[v];
                c11 = mistyCorner(layer, cellX + 1, cellZ + 1)[v];
            }
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
                boolean rift = y >= riftMinY;
                boolean chamber = y >= chamberMinY && y <= chamberMaxY;
                boolean shaft = shafts.length > 0 && y >= shaftMinY && y <= shaftMaxY;
                boolean stream = y >= streamMinY && y <= streamMaxY;
                float[] wobbles = shaftWobbles(y, shaft);
                for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                    for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                        int corner = cornerX * CORNERS + cornerZ;
                        mistyCorner(corner, y, rift, chamber, shaft, stream, wobbles, layer, layerBase + corner * MistyCaves.VALUES);
                    }
                }
                mistyDone[cellY] = true;
            }
            return page;
        }

        private float[] shaftWobbles(int y, boolean shaft) {
            if (!shaft) return null;
            float[] wobbles = new float[shafts.length];
            for (int i = 0; i < shafts.length; i++) wobbles[i] = MistyCaves.shaftWobble(shafts[i], y);
            return wobbles;
        }

        // the Misty channel margins of one corner at one height, written to out
        private void mistyCorner(int corner, int y, boolean rift, boolean chamber, boolean shaft, boolean stream, float[] wobbles, float[] out, int offset) {
            int x = startX + corner / CORNERS * CELL;
            int z = startZ + corner % CORNERS * CELL;
            float w = mistyWeight[corner];
            out[offset + MistyCaves.RIFT] = rift && riftScale[corner] > 0 ? misty.riftMargin(x, y, z, riftScale[corner], cornerEdges[corner]) : -1;
            out[offset + MistyCaves.CHAMBER] = chamber && (w > 0 || cornerThemes[corner] == DeepThemes.Theme.GNAWED) ? chamberMargin(corner, x, y, z, w) : -1;
            float shaftMargin = -1;
            if (shaft && w > 0) {
                for (int i = 0; i < shafts.length; i++) {
                    float halfWidth = shaftColumns[i][corner * 2 + 1];
                    if (halfWidth <= 0) continue;
                    float scale = MistyCaves.shaftScale(shafts[i], y, cornerEdges[corner], wobbles[i]);
                    if (scale <= 0) continue;
                    shaftMargin = Math.max(shaftMargin, MistyCaves.shaftMargin(scale, shaftColumns[i][corner * 2], halfWidth, w));
                }
            }
            out[offset + MistyCaves.SHAFT] = shaftMargin;
            if (stream && w > 0 && Float.isNaN(streamGates[corner])) streamGates[corner] = misty.streamGate(x, z);
            out[offset + MistyCaves.STREAM] = stream && w > 0 ? misty.streamMargin(x, y, z, w, cornerEdges[corner], streamGates[corner]) : -1;
        }

        // probe only: the same corner, evaluated alone
        private float[] mistyCorner(int layer, int cornerX, int cornerZ) {
            long key = (long) layer * 256 + cornerX * 16 + cornerZ;
            float[] values = mistyCorners.get(key);
            if (values != null) return values;
            if (mistyCorners.size() >= MAX_CORNERS) mistyCorners.clear();
            int y = (layer + minCellY) * CELL;
            boolean rift = y >= riftMinY;
            boolean chamber = y >= chamberMinY && y <= chamberMaxY;
            boolean shaft = shafts.length > 0 && y >= shaftMinY && y <= shaftMaxY;
            boolean stream = y >= streamMinY && y <= streamMaxY;
            values = new float[MistyCaves.VALUES];
            mistyCorner(cornerX * CORNERS + cornerZ, y, rift, chamber, shaft, stream, shaftWobbles(y, shaft), values, 0);
            mistyCorners.put(key, values);
            return values;
        }

        // the base caves leave the gnawed deeps to the worms
        private boolean gnawedRock(int y) {
            int corner = (cellX + (fx >= 0.5f ? 1 : 0)) * CORNERS + cellZ + (fz >= 0.5f ? 1 : 0);
            return cornerThemes[corner] == DeepThemes.Theme.GNAWED && y < cornerEdges[corner].top(DepthBands.Band.DEEPS);
        }

        // the deep chambers of a region, its worm-bored tunnels, or nothing on a border
        private float chamberMargin(int corner, int x, int y, int z, float weight) {
            if (cornerBorders[corner]) return -1;
            return switch (cornerThemes[corner]) {
                case GNAWED -> MistyCaves.wormMargin(worms, x, y, z, 1f, cornerEdges[corner]); // whatever the Misty weight
                case GROTTO -> misty.chamberMargin(x, y, z, weight, cornerEdges[corner], true);
                default -> misty.chamberMargin(x, y, z, weight, cornerEdges[corner], false);
            };
        }

        private boolean baseSolid(int x, int y, int z) {
            int cellY = Math.floorDiv(y, CELL) - minCellY;
            if (cellVerdict != null) {
                int slot = (cellX * (CORNERS - 1) + cellZ) * layerCount + cellY;
                byte verdict = cellVerdict[slot];
                if (verdict == 0) cellVerdict[slot] = verdict = (byte) (cellAllSolid(cellY) ? 1 : 2);
                if (verdict == 1) return true;
            }
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

        // A blended value is a convex combination of the 8 corners of its cell, so it stays between their minimum and
        // maximum (up to a few ulps of float rounding, far below the margin). When every channel clears its threshold by
        // the margin at all corners, each block of the cell is solid whatever the blend; anything closer is left to baseSolid.
        private boolean cellAllSolid(int cellY) {
            float[] low = layer(cellY);
            float[] high = layer(cellY + 1);
            int lowBase = (cellY % PAGE_LAYERS) * LAYER_SIZE;
            int highBase = ((cellY + 1) % PAGE_LAYERS) * LAYER_SIZE;
            int i00 = index(cellX, cellZ, 0);
            int i10 = index(cellX + 1, cellZ, 0);
            int i01 = index(cellX, cellZ + 1, 0);
            int i11 = index(cellX + 1, cellZ + 1, 0);
            boolean water = (cellY + minCellY) * CELL < WATER_HEIGHT;
            float lowerBound = 0;
            for (int v = 0; v < VALUES; v++) {
                float min = low[lowBase + i00 + v];
                float max = min;
                for (int k = 0; k < 8; k++) {
                    float[] page = k < 4 ? low : high;
                    int base = k < 4 ? lowBase : highBase;
                    int corner = (k & 3) == 0 ? i00 : (k & 3) == 1 ? i10 : (k & 3) == 2 ? i01 : i11;
                    float value = page[base + corner + v];
                    if (value < min) min = value;
                    if (value > max) max = value;
                }
                if (v == 0) {
                    if (water && max >= 0.4f - CULL_MARGIN) return false;
                } else if (v == 1) {
                    if (max >= 0.75f - CULL_MARGIN) return false;
                } else if (v == 2) {
                    if (max >= 0.8f - CULL_MARGIN) return false;
                } else if (min > 0) {
                    lowerBound += min;
                } else if (max < 0) {
                    lowerBound -= max;
                }
            }
            return lowerBound / 3 > 0.09f + CULL_MARGIN;
        }

        private float lerp3(int cellY, int v, float fy) {
            float b = baseBlend(cellY, v);
            float a = baseBlend(cellY + 1, v);
            return b + (a - b) * fy;
        }

        private float baseBlend(int layer, int v) {
            int slot = layer * VALUES + v;
            if (baseStamp[slot] == stamp) return baseBlend[slot];
            float c00;
            float c10;
            float c01;
            float c11;
            if (baseCorners == null) {
                float[] page = layer(layer);
                int base = (layer % PAGE_LAYERS) * LAYER_SIZE;
                c00 = page[base + index(cellX, cellZ, v)];
                c10 = page[base + index(cellX + 1, cellZ, v)];
                c01 = page[base + index(cellX, cellZ + 1, v)];
                c11 = page[base + index(cellX + 1, cellZ + 1, v)];
            } else {
                c00 = baseCorner(layer, cellX, cellZ)[v];
                c10 = baseCorner(layer, cellX + 1, cellZ)[v];
                c01 = baseCorner(layer, cellX, cellZ + 1)[v];
                c11 = baseCorner(layer, cellX + 1, cellZ + 1)[v];
            }
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

        // tan blows up at y -78; below the old floor it carries on in a straight line from where it stood at -64
        private static double caveTan(int y) {
            if (y >= DepthBands.SHALLOW_FLOOR) return Math.tan((float) y / CAVE_STRETCH_V);
            double tan = Math.tan((float) DepthBands.SHALLOW_FLOOR / CAVE_STRETCH_V);
            return tan + (1 + tan * tan) * (y - DepthBands.SHALLOW_FLOOR) / CAVE_STRETCH_V;
        }

        private static final class LayerY {
            final double tanY;
            final float tanYf;
            final float y1;
            final float y2;
            final float y3;
            final float y4;

            // everything in a LayerY depends on y alone, so one per height is shared by all chunks
            private static final int TABLE_OFFSET = 2048;
            private static final LayerY[] TABLE = new LayerY[4096];

            static LayerY of(int y) {
                int slot = y + TABLE_OFFSET;
                if (slot < 0 || slot >= TABLE.length) return new LayerY(y);
                LayerY layerY = TABLE[slot];
                if (layerY == null) TABLE[slot] = layerY = new LayerY(y);
                return layerY;
            }

            private LayerY(int y) {
                this.tanY = caveTan(y);
                this.tanYf = (float) tanY;
                this.y1 = (float) y / (CAVE_STRETCH_V * 0.5f);
                this.y2 = (float) y / 60;
                this.y3 = (float) y / 30;
                this.y4 = (float) y / CAVE_STRETCH_V;
            }
        }

        // the six base noise values of one corner
        private static void baseCorner(LayerY layerY, int x, int z, float[] out, int offset) {
            float noise = (float) SimplexNoise.noise(
                    (float) x / CAVE_STRETCH_H, layerY.tanY, (float) z / CAVE_STRETCH_H);
            noise += 0.5f * (float) SimplexNoise.noise(
                    (float) x / (CAVE_STRETCH_H * 0.5f), layerY.y1, (float) z / (CAVE_STRETCH_H * 0.5f));
            noise = noise / (1 + 0.5f);

            float noise3 = (float) SimplexNoise.noise((float) x / 90, layerY.y2, (float) z / 90);
            float miniNoise = (float) SimplexNoise.noise((float) x / 40, layerY.y3, (float) z / 40);

            out[offset] = noise;
            out[offset + 1] = noise3;
            out[offset + 2] = miniNoise;
            out[offset + 3] = (float) SimplexNoise.noise(
                    (float) x / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), layerY.tanYf, (float) z / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), 57142);
            out[offset + 4] = (float) SimplexNoise.noise(
                    (float) (98153 + z) / SPAGHETTI_CAVE_STRETCH_H, layerY.y4, (float) x / SPAGHETTI_CAVE_STRETCH_H, 0);
            out[offset + 5] = (float) SimplexNoise.noise(
                    (float) (1243624 + z) / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), layerY.y4, (float) x / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), 0);
        }

        // probe only: the same corner, evaluated alone
        private float[] baseCorner(int layer, int cornerX, int cornerZ) {
            long key = (long) layer * 256 + cornerX * 16 + cornerZ;
            float[] values = baseCorners.get(key);
            if (values != null) return values;
            if (baseCorners.size() >= MAX_CORNERS) baseCorners.clear();
            values = new float[VALUES];
            baseCorner(LayerY.of((layer + minCellY) * CELL), startX + cornerX * CELL, startZ + cornerZ * CELL, values, 0);
            baseCorners.put(key, values);
            return values;
        }

        private float[] layer(int cellY) {
            int pageIndex = cellY / PAGE_LAYERS;
            float[] page = layerPages[pageIndex];
            if (page == null) page = layerPages[pageIndex] = new float[PAGE_LAYERS * LAYER_SIZE];
            if (!layerDone[cellY]) {
                int layerBase = (cellY % PAGE_LAYERS) * LAYER_SIZE;
                int y = (cellY + minCellY) * CELL;
                // everything that depends on y alone is evaluated once per layer
                LayerY layerY = LayerY.of(y);
                for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                    for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                        baseCorner(layerY, startX + cornerX * CELL, startZ + cornerZ * CELL, page, layerBase + index(cornerX, cornerZ, 0));
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
