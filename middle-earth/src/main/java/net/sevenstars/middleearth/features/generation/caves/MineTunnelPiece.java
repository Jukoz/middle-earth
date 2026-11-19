package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.sevenstars.middleearth.MiddleEarth;

// A plain tunnel: the box is carved to air, and the floor under it is laid on rock. It joins two parts of a mine, or leads to a bridge.
public final class MineTunnelPiece extends StructurePiece {
    public static final StructurePieceType TYPE = (context, nbt) -> new MineTunnelPiece(nbt);

    public MineTunnelPiece(BlockBox box) {
        super(TYPE, 0, box);
    }

    private MineTunnelPiece(NbtCompound nbt) {
        super(TYPE, nbt);
    }

    public static void register() {
        Registry.register(Registries.STRUCTURE_PIECE, MiddleEarth.id("mine_tunnel"), TYPE);
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = Math.max(boundingBox.getMinX(), chunkBox.getMinX()); x <= Math.min(boundingBox.getMaxX(), chunkBox.getMaxX()); x++) {
            for (int z = Math.max(boundingBox.getMinZ(), chunkBox.getMinZ()); z <= Math.min(boundingBox.getMaxZ(), chunkBox.getMaxZ()); z++) {
                for (int y = boundingBox.getMinY() + 1; y <= boundingBox.getMaxY(); y++) {
                    world.setBlockState(pos.set(x, y, z), Blocks.AIR.getDefaultState(), 2);
                }
                // the lowest layer of the box is the floor: laid on rock, a dip of one block filled
                pos.set(x, boundingBox.getMinY(), z);
                boolean solid = !world.getBlockState(pos).isAir();
                if (solid || !world.getBlockState(pos.down()).isAir()) world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 2);
            }
        }
    }
}
