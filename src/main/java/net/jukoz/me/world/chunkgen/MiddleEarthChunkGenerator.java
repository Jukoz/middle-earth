package net.jukoz.me.world.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.utils.noises.SimplexNoise;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.biomes.surface.ModBiomeSource;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
import java.util.concurrent.Executor;

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

    private static final int CAVE_STRETCH_H = 60;
    private static final int CAVE_STRETCH_V = 50;
    private static float minNoise = 10000;
    private static float maxNoise = -10000;
    RegistryEntryLookup<Biome> biomeRegistry;
    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(MiddleEarthChunkGenerator::new)));

    public MiddleEarthChunkGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new ModBiomeSource(
                new ArrayList<>(Arrays.asList(
                    biomeRegistry.getOrThrow(MEBiomeKeys.OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANDUIN_VALES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BARROW_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CORSAIR_COASTS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_MIRKWOOD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_MIRKWOOD_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_ANDUIN_VALES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOL_GULDUR ),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERIADOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ETHIR_ANDUIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FORODWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD_DESERT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HILLS_OF_EVENDIM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_FRONTIER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LAMEDON),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_LAKE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LORIEN_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN),
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
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NINDALF),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTH_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_DUNLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_WASTELANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_SEA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OCEAN_COAST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SEA_OF_RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHEAST_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_DUNLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_FOROCHEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_ANGLE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_OLD_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WOLD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOLFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TROLLSHAWS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UMBAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WASTE_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_REALM),
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
                MEBiome meBiome = null;

                if(middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
                    RegistryEntry<Biome> biome = region.getBiome(new BlockPos(posX, chunk.getTopY(), posZ));
                    meBiome = MEBiomesData.getBiomeByKey(biome);
                    if(meBiome == null) {
                        meBiome = MEBiomesData.defaultBiome;
                    }
                } else {
                    meBiome = MEBiomesData.defaultBiome;
                }

                float height = MiddleEarthHeightMap.getHeight(posX, posZ);
                float caveBlendNoise = (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) posX / 24,  (double) posZ / 24)) - CAVE_NOISE);

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
                for(int y = DEEPSLATE_LEVEL + (int) caveBlendNoise; y < (dirtHeight / 2); y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), meBiome.stoneBlock.getDefaultState());
                }
                for(int y = (int) (dirtHeight / 2); y < dirtHeight; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), meBiome.upperStoneBlock.getDefaultState());
                }

                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (HEIGHT + height - 1), z), meBiome.stoneBlock.getDefaultState(), false);
                for(int y = (int) (HEIGHT + height); y < DIRT_HEIGHT + height; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), meBiome.underSurfaceBlock.getDefaultState(), false);
                }

                BlockState surfaceBlock = meBiome.surfaceBlock.getDefaultState();

                if(DIRT_HEIGHT + height < WATER_HEIGHT && meBiome.surfaceBlock == Blocks.GRASS_BLOCK) {
                    surfaceBlock = Blocks.DIRT.getDefaultState();
                }

                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (DIRT_HEIGHT + height), z), surfaceBlock, false);

                for(int y = (int) (DIRT_HEIGHT + height + 1); y <= WATER_HEIGHT; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.WATER.getDefaultState(), false);
                }
            }
        }

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
