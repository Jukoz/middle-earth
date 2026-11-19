package net.sevenstars.middleearth.features.generation.worldfeatures.boulder;

import net.sevenstars.middleearth.features.storage.StorageBlocks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.features.generation.worldfeatures.ReachableChunks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.structure.Structure;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;


public class BigBoulderFeature extends Feature<BigBoulderFeatureConfig> {
    private static final int READABLE_CHUNK_RADIUS = 1;
    private static final int VERTICAL_MARGIN = 3; // blocks of reach above and below the height
    private static final ImmutableList<Block> CANNOT_PLACE_ON_BLOCKS;
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS;

    public BigBoulderFeature(Codec<BigBoulderFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BigBoulderFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        BlockState underBlock = structureWorldAccess.getBlockState(blockPos.down());
        if(underBlock.isIn(BlockTags.LEAVES) || CANNOT_PLACE_ON_BLOCKS.contains(underBlock.getBlock())) {
            return false;
        }

        var config = context.getConfig();
        float length = (float) Math.ceil(config.length) + (random.nextFloat() * config.randomSize);
        float width = (float) Math.ceil(config.width) + (random.nextFloat() * config.randomSize);
        float baseHeight = context.getConfig().height + (random.nextFloat() * config.randomSize);

        float forSize = Math.max(length, width) * 2f;
        float angle = random.nextFloat() * 180;

        int reach = (int) forSize;
        int verticalReach = (int) baseHeight + VERTICAL_MARGIN;
        BlockBox bounds = new BlockBox(blockPos.getX() - reach, blockPos.getY() - verticalReach, blockPos.getZ() - reach,
                blockPos.getX() + reach, blockPos.getY() + verticalReach, blockPos.getZ() + reach);
        if(intersectsStructure(structureWorldAccess, blockPos, bounds)) {
            return false;
        }

        for(int x = (int) -forSize; x <= forSize; ++x) {
            for(int z = (int) -forSize; z <= forSize; ++z) {
                for(int y = (int) -baseHeight-3; y <= baseHeight+3; ++y) {
                    BlockPos offsetBlockPos = blockPos.mutableCopy().add(x, y, z);
                    BlockState mutableBlockState = structureWorldAccess.getBlockState(offsetBlockPos);
                    if(!mutableBlockState.isAir() && !mutableBlockState.isSolidBlock(structureWorldAccess, offsetBlockPos.mutableCopy().add(0, 1, 0)) ||
                            mutableBlockState.isIn(BlockTags.LOGS) || CANNOT_REPLACE_BLOCKS.contains(mutableBlockState.getBlock())) {
                        continue;
                    } else if (this.isPointInside(x, y, z, length, width, baseHeight, angle, config.randomness, random)) {
                        BlockState blockState = config.blockStates.get(random.nextBetween(0, config.blockStates.size() - 1));
                        this.setBlockState(structureWorldAccess, blockPos.mutableCopy().add(x, y, z), blockState);
                    }
                }
            }
        }

        return true;
    }

    // Boulders generate after structures and would otherwise fill houses and mines
    private static boolean intersectsStructure(StructureWorldAccess world, BlockPos origin, BlockBox bounds) {
        // Only the origin chunk and its neighbours are guaranteed to be readable while features generate
        ChunkPos center = new ChunkPos(origin);
        int minChunkX = Math.max(ChunkSectionPos.getSectionCoord(bounds.getMinX()), center.x - READABLE_CHUNK_RADIUS);
        int maxChunkX = Math.min(ChunkSectionPos.getSectionCoord(bounds.getMaxX()), center.x + READABLE_CHUNK_RADIUS);
        int minChunkZ = Math.max(ChunkSectionPos.getSectionCoord(bounds.getMinZ()), center.z - READABLE_CHUNK_RADIUS);
        int maxChunkZ = Math.min(ChunkSectionPos.getSectionCoord(bounds.getMaxZ()), center.z + READABLE_CHUNK_RADIUS);
        for(int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for(int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                Chunk chunk = ReachableChunks.get(world, chunkX, chunkZ, ChunkStatus.STRUCTURE_REFERENCES);
                if(chunk == null) continue;
                for(Map.Entry<Structure, LongSet> entry : chunk.getStructureReferences().entrySet()) {
                    if(startIntersects(world, entry.getKey(), entry.getValue(), bounds)) return true;
                }
            }
        }
        return false;
    }

    private static boolean startIntersects(StructureWorldAccess world, Structure structure, LongSet starts, BlockBox bounds) {
        for(long packed : starts) {
            // a start can be many chunks away, beyond what the generation region provides
            Chunk startChunk = ReachableChunks.get(world, ChunkPos.getPackedX(packed), ChunkPos.getPackedZ(packed), ChunkStatus.STRUCTURE_STARTS);
            if(startChunk == null) continue;
            StructureStart start = startChunk.getStructureStart(structure);
            if(start == null) continue;
            for(StructurePiece piece : start.getChildren()) {
                if(piece.getBoundingBox().intersects(bounds)) return true;
            }
        }
        return false;
    }

    private boolean isPointInside(int x, int y, int z, float length, float width, float baseHeight, float angle, float randomNoise, Random random) {
        float randomness = -randomNoise + (random.nextFloat() * randomNoise * 2);

        float squareLength = (length + randomness) * (length + randomness);
        float squareWidth = (width + randomness) * (width + randomness);
        float squareHeight = (baseHeight + randomness) * (baseHeight + randomness);

        float radians = (float) Math.toRadians(angle);

        float rotatedX = (float) (x * Math.cos(radians) - z * Math.sin(radians));
        float rotatedZ = (float) (x * Math.sin(radians) + z * Math.cos(radians));

        float deltaX = (float)(rotatedX*rotatedX) / squareLength;
        float deltaY = (float)(y*y) / squareHeight;
        float deltaZ = (float)(rotatedZ*rotatedZ) / squareWidth;

        return (deltaX + deltaY + deltaZ <= 1);
    }

    static {
        CANNOT_PLACE_ON_BLOCKS = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.WATER, StoneBlockSetRegistryME.NURGON_SET.cobblestoneBlocks.base(),
                StoneBlockSetRegistryME.MEDGON_SET.smoothBlocks.base(), Blocks.CHEST, Blocks.SPAWNER);
        CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.BEDROCK, StoneBlockSetRegistryME.NURGON_SET.cobblestoneBlocks.base(),
                StoneBlockSetRegistryME.MEDGON_SET.smoothBlocks.base(), Blocks.CHEST, Blocks.BARREL, Blocks.SPAWNER, DecorativeBlockRegistryME.STRUCTURE_MANAGER,
                DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER, DecorativeBlockRegistryME.STRUCTURE_NEST, StorageBlocks.THIN_BARREL, StorageBlocks.SMALL_CRATE);
    }
}
