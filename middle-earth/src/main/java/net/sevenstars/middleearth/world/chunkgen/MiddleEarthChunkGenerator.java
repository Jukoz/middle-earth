package net.sevenstars.middleearth.world.chunkgen;

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
import net.sevenstars.middleearth.block.registration.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.utils.noises.BlendedNoise;
import net.sevenstars.middleearth.utils.noises.SimplexNoise;
import net.sevenstars.middleearth.world.biomes.BlocksLayeringData;
import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.world.biomes.surface.*;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.world.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.world.map.MiddleEarthMapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int posX = (chunk.getPos().x * 16) + x;
                int posZ = (chunk.getPos().z * 16) + z;
                 MapBasedCustomBiome customHeightBiomeHeightData = null;
                if(middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
                    RegistryEntry<Biome> biome = region.getBiome(new BlockPos(posX, chunk.getTopYInclusive(), posZ));
                    customHeightBiomeHeightData = MapBasedBiomePool.getBiome(biome, posX, posZ);
                }
                if(customHeightBiomeHeightData == null) {
                    customHeightBiomeHeightData = MapBasedBiomePool.defaultBiome;
                }

                float height = MiddleEarthHeightMap.getHeight(posX, posZ);

                float caveBlendNoise = (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) (posX + seed) / 24f,  (double) (posZ + seed) / 24f)) - CAVE_NOISE);
                float slopeAngle = getTerrainSlope(height, posX, posZ);
                int waterHeight = customHeightBiomeHeightData.getWaterHeight();

                RegistryKey<Biome> biomeRegistryKey = customHeightBiomeHeightData.getBiomeKey();
                if(SubBiomes.isSubBiome(biomeRegistryKey)) {
                    SubBiome subBiome = SubBiomes.getSubBiomeFromChild(biomeRegistryKey);
                    if(subBiome != null) {
                        double perlin = BiomeSourceME.getSubBiomeNoise(posX, posZ, subBiome.getFrequency());
                        double additionalHeight = Math.max(subBiome.getAdditionalHeight((float) perlin) - 1, 0);
                        additionalHeight *= MiddleEarthMapRuntime.getInstance().getEdge(posX, posZ);
                        height += (float) additionalHeight;
                    }
                } else if(biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM || biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM_PIT) {
                    float percentage = (float) Math.sqrt(mountDoom.distanceSquared(new Vec2f(posX, posZ))) / 42;
                    percentage = Math.min(1, Math.max(0.0f, percentage));
                    percentage = (float) Math.pow(percentage, 2.47f);
                    height = height * percentage;
                    height -= (1 - percentage) * getNoisyHeight(posX, posZ) * 8;
                } else if(biomeRegistryKey == BiomeKeyRegistryME.DEAD_MARSHES || biomeRegistryKey == BiomeKeyRegistryME.DEAD_MARSHES_WATER) {
                    float oldHeight = height;
                    height = getMarshesHeight(posX, posZ, height);
                    float percentage = Math.min(MiddleEarthHeightMap.getImageNoiseModifier(posX, posZ), 0.3f) / 0.3f;
                    height = MiddleEarthHeightMap.lerp(height, oldHeight, percentage);
                }

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

                chunk.setBlockState(chunk.getPos().getBlockPos(x, bottomY, z), Blocks.BEDROCK.getDefaultState(), 0);
                for(int y = bottomY + 1; y <= LAVA_HEIGHT; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.LAVA.getDefaultState(), 0);
                }

                for(int y = bottomY + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base().getDefaultState());
                }
                if(Math.random() < 0.5f) chunk.setBlockState(chunk.getPos().getBlockPos(x, chunk.getBottomY() + 1, z),
                        Blocks.BEDROCK.getDefaultState(), 0);

                for(int y = MEDGON_LEVEL + (int) caveBlendNoise; y < NURGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), StoneBlockSetRegistryME.NURGON_SET.baseBlocks.base().getDefaultState());
                }
                for(int y = NURGON_LEVEL + (int) caveBlendNoise; y < DEEPSLATE_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), Blocks.DEEPSLATE.getDefaultState());
                }

                float dirtHeight = HEIGHT + height - 1;
                int currentHeight = DEEPSLATE_LEVEL + (int) caveBlendNoise;
                int totalLayersHeight = (int) (dirtHeight - currentHeight);
                for(BlocksLayeringData.LayerData layerData : customHeightBiomeHeightData.getBiome().getBlocksLayering().layers) {
                    int blocks = (int) (totalLayersHeight * layerData.percentage);
                    for(int y = 0; y <= blocks; y++) {
                        trySetBlock(chunk, chunk.getPos().getBlockPos(x, currentHeight++, z), layerData.block.getDefaultState());
                    }
                }
                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (HEIGHT + height - 2), z), customHeightBiomeHeightData.getBiome().getBlocksLayering().layers.getLast().block.getDefaultState());
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

                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (HEIGHT + height - 1), z), underSurfaceBlock);
                for(int y = (int) (HEIGHT + height); y < DIRT_HEIGHT + height; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), underSurfaceBlock);
                }
                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (DIRT_HEIGHT + height), z), surfaceBlock);

                if(biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM || biomeRegistryKey == BiomeKeyRegistryME.MOUNT_DOOM_PIT) {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= 100; y++) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.LAVA.getDefaultState());
                    }
                    if(DIRT_HEIGHT + height < 110) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (DIRT_HEIGHT + height), z), Blocks.MAGMA_BLOCK.getDefaultState());
                    }
                } else {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= waterHeight; y++) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.WATER.getDefaultState());
                    }
                }

                if(ServerConfigME.ENABLE_PROCEDURAL_STRUCTURES) {
                    ProceduralStructures.generateStructures(customHeightBiomeHeightData, chunk, posX, (int) (DIRT_HEIGHT + height), posZ);
                }
            }
        }
    }

    private float getTerrainSlope(float height, int x, int z) {
        int offset = 3;
        float eastHeight = MiddleEarthHeightMap.getHeight(x + offset, z);
        float southHeight = MiddleEarthHeightMap.getHeight(x, z + offset);

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

    private void trySetBlock(Chunk chunk, BlockPos blockPos, BlockState blockState) {
        float noise = 0;
        if(blockPos.getY() < WATER_HEIGHT) {
            noise =(float) SimplexNoise.noise(
                    (float) blockPos.getX() / CAVE_STRETCH_H, Math.tan((float) blockPos.getY() / CAVE_STRETCH_V), (float) blockPos.getZ() / CAVE_STRETCH_H);
            noise += 0.5f * (float) SimplexNoise.noise(
                    (float) blockPos.getX() / (CAVE_STRETCH_H * 0.5f), (float) blockPos.getY() / (CAVE_STRETCH_V * 0.5f), (float) blockPos.getZ() / (CAVE_STRETCH_H * 0.5f));
            noise = noise / (1 + 0.5f);
        }
        float noise3 = (float) SimplexNoise.noise((float) blockPos.getX() / 90, (float) blockPos.getY() / 60, (float) blockPos.getZ() / 90);
        float miniNoise = (float) SimplexNoise.noise((float) blockPos.getX() / 40, (float) blockPos.getY() / 30, (float) blockPos.getZ() / 40);


        float spaghettiNoise = Math.abs ((float) SimplexNoise.noise(
                (float) blockPos.getX() / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), (float) Math.tan((float) blockPos.getY() / CAVE_STRETCH_V), (float) blockPos.getZ() / (SPAGHETTI_CAVE_STRETCH_H * 1.5f), 57142));
        float spaghettiNoise2 = Math.abs ((float) SimplexNoise.noise(
                (float) (98153 + blockPos.getZ()) / SPAGHETTI_CAVE_STRETCH_H, (float) blockPos.getY() / CAVE_STRETCH_V, (float) blockPos.getX() / SPAGHETTI_CAVE_STRETCH_H, 0));
        float spaghettiNoise3 = Math.abs ((float) SimplexNoise.noise(
                (float) (1243624 + blockPos.getZ()) / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), (float) blockPos.getY() / CAVE_STRETCH_V, (float) blockPos.getX() / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), 0));
        float combinedSpaghettiNoise = Math.abs(spaghettiNoise) + Math.abs(spaghettiNoise2) + Math.abs(spaghettiNoise3);
        combinedSpaghettiNoise /= 3;

        if(noise < 0.4f && noise3 < 0.75f && miniNoise < 0.8f && combinedSpaghettiNoise > 0.09f) {
            chunk.setBlockState(blockPos, blockState);
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
        return 384;
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
