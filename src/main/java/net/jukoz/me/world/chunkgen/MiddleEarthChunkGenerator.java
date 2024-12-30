package net.jukoz.me.world.chunkgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.utils.noises.SimplexNoise;
import net.jukoz.me.world.biomes.BlocksLayeringData;
import net.jukoz.me.world.biomes.surface.*;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;

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
    private static final int CAVE_STRETCH_V = 50;

    RegistryEntryLookup<Biome> biomeRegistry;
    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(MiddleEarthChunkGenerator::new)));

    public MiddleEarthChunkGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new ModBiomeSource(
                new ArrayList<>(Arrays.asList(
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS),
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EAST_BIGHT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_NURN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_CLIFFS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ETHIR_ANDUIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FOREST_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREAT_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GUNDABAD_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FORODWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GULF_OF_LHUN_CLIFFS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GULF_OF_LHUN_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH_ASHEN_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH_DELTA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD_DESERT),
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_LAKE),
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.MINHIRIATH),
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_DOOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NAN_CURUNIR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NINDALF),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTH_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_DUNLAND),
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SEA_OF_RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SARN_GEBIR_WILDLANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SARN_GEBIR_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHEAST_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_DUNLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_FOROCHEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_EPHEL_DUATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_ANGLE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_OLD_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WOLD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WHITE_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOLFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOROGWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TROLLSHAWS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UDUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UMBAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WASTE_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WEBBED_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WEBBED_DARK_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WITHERED_HEATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_REALM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_HILLS),

                    biomeRegistry.getOrThrow(MEBiomeKeys.BASIC_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LUSH_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRIPSTONE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MUD_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FUNGUS_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MITHRIL_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BASALT_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MAGMA_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRY_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ICE_CAVE)
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
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig,
                      BiomeAccess biomeAccess, StructureAccessor structureAccessor,
                      Chunk chunk2, GenerationStep.Carver carverStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        int bottomY = chunk.getBottomY();
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int posX = (chunk.getPos().x * 16) + x;
                int posZ = (chunk.getPos().z * 16) + z;
                 MapBasedCustomBiome customHeightBiomeHeightData = null;
                if(middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
                    RegistryEntry<Biome> biome = region.getBiome(new BlockPos(posX, chunk.getTopY(), posZ));
                    customHeightBiomeHeightData = MapBasedBiomePool.getBiome(biome, posX, posZ);
                }
                if(customHeightBiomeHeightData == null) {
                    customHeightBiomeHeightData = MapBasedBiomePool.defaultBiome;
                }

                float height = MiddleEarthHeightMap.getHeight(posX, posZ);

                float caveBlendNoise = (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) posX / 24,  (double) posZ / 24)) - CAVE_NOISE);
                float slopeAngle = getTerrainSlope(height, posX, posZ);
                int waterHeight = customHeightBiomeHeightData.getWaterHeight();

                if(SubBiomes.isSubBiome(customHeightBiomeHeightData.getBiomeKey())) {
                    SubBiome subBiome = SubBiomes.getSubBiomeFromChild(customHeightBiomeHeightData.getBiomeKey());
                    if(subBiome != null) {
                        double perlin = ModBiomeSource.getSubBiomeNoise(posX, posZ, subBiome.getFrequency());
                        double additionalHeight = Math.max(subBiome.getAdditionalHeight((float) perlin) - 1, 0);
                        additionalHeight *= MiddleEarthMapRuntime.getInstance().getEdge(posX, posZ);
                        height += (float) additionalHeight;
                    }
                } else if(customHeightBiomeHeightData.getBiomeKey() == MEBiomeKeys.MOUNT_DOOM) {
                    float percentage = (float) Math.sqrt(mountDoom.distanceSquared(new Vec2f(posX, posZ))) / 50;
                    percentage = Math.min(1, Math.max(0.0f, percentage));
                    percentage = (float) Math.pow(percentage, 2.45f);
                    height = height * percentage;
                } else if(customHeightBiomeHeightData.getBiomeKey() == MEBiomeKeys.DEAD_MARSHES || customHeightBiomeHeightData.getBiomeKey() == MEBiomeKeys.DEAD_MARSHES_WATER) {
                    float oldHeight = height;
                    height = getMarshesHeight(posX, posZ, height);
                    float percentage = Math.min(MiddleEarthHeightMap.getImageNoiseModifier(posX, posZ), 0.3f) / 0.3f;
                    height = MiddleEarthHeightMap.lerp(height, oldHeight, percentage);
                }

                chunk.setBlockState(chunk.getPos().getBlockPos(x, bottomY, z), Blocks.BEDROCK.getDefaultState(), false);
                for(int y = bottomY + 1; y <= LAVA_HEIGHT; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.LAVA.getDefaultState(), false);
                }

                for(int y = bottomY + 1; y < MEDGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), StoneBlockSets.MEDGON.base().getDefaultState());
                }
                if(Math.random() < 0.5f) chunk.setBlockState(chunk.getPos().getBlockPos(x, chunk.getBottomY() + 1, z),
                        Blocks.BEDROCK.getDefaultState(), false);

                for(int y = MEDGON_LEVEL + (int) caveBlendNoise; y < NURGON_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), StoneBlockSets.NURGON.base().getDefaultState());
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
                BlockState surfaceBlock = customHeightBiomeHeightData.getBiome().getSlopeMap().slopeDatas.getFirst().block.getDefaultState();
                BlockState underSurfaceBlock;


                if(DIRT_HEIGHT + height < waterHeight && surfaceBlock == Blocks.GRASS_BLOCK.getDefaultState()) {
                    surfaceBlock = Blocks.DIRT.getDefaultState();
                    underSurfaceBlock = surfaceBlock;
                } else {
                    surfaceBlock = customHeightBiomeHeightData.getBiome().getSlopeMap().getBlockAtAngle(slopeAngle).getDefaultState();
                    if(surfaceBlock == Blocks.GRASS_BLOCK.getDefaultState()) underSurfaceBlock = Blocks.DIRT.getDefaultState();
                    else underSurfaceBlock = surfaceBlock;
                }

                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (HEIGHT + height - 1), z), underSurfaceBlock, false);
                for(int y = (int) (HEIGHT + height); y < DIRT_HEIGHT + height; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), underSurfaceBlock, false);
                }
                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (DIRT_HEIGHT + height), z), surfaceBlock, false);

                if(customHeightBiomeHeightData.getBiomeKey() == MEBiomeKeys.MOUNT_DOOM) {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= 90; y++) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.LAVA.getDefaultState(), false);
                    }
                } else {
                    for(int y = (int) (DIRT_HEIGHT + height + 1); y <= waterHeight; y++) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.WATER.getDefaultState(), false);
                    }
                }


                ProceduralStructures.generateStructures(customHeightBiomeHeightData, chunk, posX, (int) (DIRT_HEIGHT + height), posZ);
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

        if(noise < 0.4f && noise3 < 0.75f && miniNoise < 0.8f) { //
            chunk.setBlockState(blockPos, blockState, false);
        }
    }

    public static float getMarshesHeight(int x, int z, float height) {
        height = -2 + (2.0f * (float) BlendedNoise.noise((double) x / 19,  (double) z / 19));
        height += (float) BlendedNoise.noise((double) x / 11,  (double) z / 11);
        return height;
    }
    
    @Override
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
        super.generateFeatures(world, chunk, structureAccessor);
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        ChunkPos chunkPos = region.getCenterPos();
        RegistryEntry<Biome> registryEntry = region.getBiome(chunkPos.getStartPos().withY(region.getTopY() - 1));
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
        return (int)worldHeight;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
    }
}
