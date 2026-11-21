package net.sevenstars.middleearth.world.features.boulder;

import net.sevenstars.middleearth.features.storage.StorageBlocks;

import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.mojang.serialization.Codec;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.structure.Structure;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;


public class BigBoulderFeature extends Feature<BigBoulderFeatureConfig> {
    private static final int START_READ_RADIUS = 8;
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
        int verticalReach = (int) baseHeight + 3;
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
        if(!(world instanceof ChunkRegion region)) return false;

        // Only the origin chunk and its neighbours are guaranteed to be readable while features generate
        ChunkPos center = region.getCenterPos();
        ChunkPos originChunk = new ChunkPos(origin);
        int minChunkX = Math.max(ChunkSectionPos.getSectionCoord(bounds.getMinX()), originChunk.x - 1);
        int maxChunkX = Math.min(ChunkSectionPos.getSectionCoord(bounds.getMaxX()), originChunk.x + 1);
        int minChunkZ = Math.max(ChunkSectionPos.getSectionCoord(bounds.getMinZ()), originChunk.z - 1);
        int maxChunkZ = Math.min(ChunkSectionPos.getSectionCoord(bounds.getMaxZ()), originChunk.z + 1);
        Set<StructureStart> checked = new HashSet<>();
        for(int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for(int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                var references = region.getChunk(chunkX, chunkZ, ChunkStatus.STRUCTURE_REFERENCES).getStructureReferences();
                // Most chunks touch no structure at all
                if(references.isEmpty()) continue;
                // Crash fix: If a structure is referenced from a chunk that is too far away, it is not readable
                for(LongSet starts : references.values()) {
                    for(long packed : starts) {
                        ChunkPos start = new ChunkPos(packed);
                        if(Math.max(Math.abs(start.x - center.x), Math.abs(start.z - center.z)) > START_READ_RADIUS) return true;
                    }
                }
                for(Map.Entry<Structure, LongSet> entry : references.entrySet()) {
                    for(long packed : entry.getValue()) {
                        ChunkPos startChunk = new ChunkPos(packed);
                        StructureStart start = region.getChunk(startChunk.x, startChunk.z, ChunkStatus.STRUCTURE_STARTS).getStructureStart(entry.getKey());
                        if(start == null || !start.hasChildren() || !checked.add(start) || !start.getBoundingBox().intersects(bounds)) continue;
                        for(StructurePiece piece : start.getChildren()) {
                            if(piece.getBoundingBox().intersects(bounds)) return true;
                        }
                    }
                }
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
                DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER, StorageBlocks.THIN_BARREL, StorageBlocks.SMALL_CRATE);
    }
}
