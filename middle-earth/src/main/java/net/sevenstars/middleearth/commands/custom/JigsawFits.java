package net.sevenstars.middleearth.commands.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Finds which plugs a jigsaw block could be joined to, the way the game would join them. A plug that asks for a piece
// from a pool and names a target fits the plugs with that name on the pieces of that pool, rotated to meet it. It
// searches the pieces of the loaded structure, or for a plug in none of them, the nearby jigsaw blocks of any piece
public class JigsawFits {
    public static final int MAXIMUM_FITS = 512;
    private static final Identifier EMPTY = Identifier.ofVanilla("empty");
    // In chunks, the search radius around a plug that is in no structure being edited
    private static final int NEAR_CHUNKS = 4;

    private record Plug(BlockPos pos, @Nullable Identifier template, RegistryKey<StructurePool> pool, Identifier name, Identifier target, Direction facing) {
        boolean asks() {
            return !pool.equals(StructurePools.EMPTY);
        }
    }

    public static List<BlockPos> of(ServerWorld world, ServerPlayerEntity player, BlockPos pos) {
        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        List<StructureEditSession.Piece> pieces = session == null || !session.getDimension().equals(world.getRegistryKey()) ? List.of() : session.getPieces();
        Plug clicked = plugAt(world, pos, pieces);
        if(clicked == null)
            return List.of();
        Registry<StructurePool> pools = world.getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL);
        List<BlockPos> fits = new ArrayList<>();
        for(Plug other : plugs(world, pos, pieces)) {
            if(other.pos().equals(pos) || !meets(clicked.facing(), other.facing()))
                continue;
            if((joins(pools, clicked, other) || joins(pools, other, clicked)) && fits.size() < MAXIMUM_FITS)
                fits.add(other.pos());
        }
        return fits;
    }

    // The asking plug picks a piece from its pool and joins it at the plug its target names
    private static boolean joins(Registry<StructurePool> pools, Plug asking, Plug joined) {
        if(!asking.asks() || joined.name().equals(EMPTY) || !joined.name().equals(asking.target()))
            return false;
        if(joined.template() == null)
            return true;
        StructurePool pool = pools.get(asking.pool());
        return pool != null && templatesOf(pool).contains(joined.template());
    }

    private static Set<Identifier> templatesOf(StructurePool pool) {
        Set<Identifier> templates = new HashSet<>();
        pool.getElementWeights().forEach(element -> templates.addAll(StructureEditSession.templatesOf(element.getFirst())));
        return templates;
    }

    // Up meets down. Sideways plugs meet in any horizontal direction, because the piece can be rotated to fit
    private static boolean meets(Direction facing, Direction other) {
        return facing.getAxis().isVertical() ? other == facing.getOpposite() : other.getAxis().isHorizontal();
    }

    private static @Nullable Plug plugAt(ServerWorld world, BlockPos pos, List<StructureEditSession.Piece> pieces) {
        BlockState state = world.getBlockState(pos);
        if(!state.isOf(Blocks.JIGSAW) || !(world.getBlockEntity(pos) instanceof JigsawBlockEntity jigsaw))
            return null;
        Identifier template = null;
        for(StructureEditSession.Piece piece : pieces) {
            if(piece.box().contains(pos))
                template = piece.template();
        }
        return new Plug(pos.toImmutable(), template, jigsaw.getPool(), jigsaw.getName(), jigsaw.getTarget(), state.get(JigsawBlock.ORIENTATION).getFacing());
    }

    private static List<Plug> plugs(ServerWorld world, BlockPos around, List<StructureEditSession.Piece> pieces) {
        Set<ChunkPos> chunks = new HashSet<>();
        if(pieces.stream().anyMatch(piece -> piece.box().contains(around))) {
            for(StructureEditSession.Piece piece : pieces) {
                BlockBox box = piece.box();
                ChunkPos.stream(new ChunkPos(box.getMinX() >> 4, box.getMinZ() >> 4), new ChunkPos(box.getMaxX() >> 4, box.getMaxZ() >> 4)).forEach(chunks::add);
            }
        } else {
            pieces = List.of();
            ChunkPos.stream(new ChunkPos(around), NEAR_CHUNKS).forEach(chunks::add);
        }
        List<Plug> plugs = new ArrayList<>();
        for(ChunkPos chunk : chunks) {
            if(!world.isChunkLoaded(chunk.x, chunk.z))
                continue;
            for(BlockEntity blockEntity : world.getChunk(chunk.x, chunk.z).getBlockEntities().values()) {
                Plug plug = blockEntity instanceof JigsawBlockEntity ? plugAt(world, blockEntity.getPos(), pieces) : null;
                if(plug != null && (pieces.isEmpty() || plug.template() != null))
                    plugs.add(plug);
            }
        }
        return plugs;
    }
}
