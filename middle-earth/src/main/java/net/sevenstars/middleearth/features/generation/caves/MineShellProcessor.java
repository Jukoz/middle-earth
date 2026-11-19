package net.sevenstars.middleearth.features.generation.caves;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.ServerWorldAccess;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import org.jspecify.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Miners leave the rock as it is: the shell blocks of the templates are skipped, so natural rock and natural air stay.
// Only the floor row is laid, and only on rock. Supports, props and webs are kept only where something holds them.
public final class MineShellProcessor extends StructureProcessor {
    public static final MapCodec<MineShellProcessor> CODEC = MapCodec.unit(new MineShellProcessor());
    public static final StructureProcessorType<MineShellProcessor> TYPE = () -> CODEC;

    private static final Set<Block> SHELL = Set.of(Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
    private static final int WEB_CHANCE = 786; // out of 65536 air cells, about 1.2%

    private enum Role {
        NONE, POST, BEAM, FLOOR, HANGING, LADDER, SCONCE
    }

    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo original,
                                                                  StructureTemplate.StructureBlockInfo current, StructurePlacementData data) {
        BlockState state = current.state();
        if (state.isAir() || state.isOf(Blocks.JIGSAW)) return current;
        BlockPos at = current.pos();
        if (!world.isChunkLoaded(at.getX() >> 4, at.getZ() >> 4)) return current;
        BlockState existing = world.getBlockState(at);
        if (SHELL.contains(state.getBlock())) {
            // the floor row sits in the template's lowest layer; it also fills a dip of one block over rock
            return original.pos().getY() == 0 && (solid(existing) || solid(world.getBlockState(at.down()))) ? current : null;
        }
        Role role = role(state);
        if (role == Role.SCONCE) {
            // the wall behind has to be there
            Direction back = state.rotate(data.getRotation()).get(Properties.HORIZONTAL_FACING).getOpposite();
            BlockPos behind = at.offset(back);
            return world.isChunkLoaded(behind.getX() >> 4, behind.getZ() >> 4) && !solid(world.getBlockState(behind)) ? null : current;
        }
        // the rest of the supports are judged together afterwards
        if (role != Role.NONE || solid(existing)) return current;
        for (Direction direction : Direction.values()) {
            BlockPos next = at.offset(direction);
            if (!world.isChunkLoaded(next.getX() >> 4, next.getZ() >> 4) || solid(world.getBlockState(next))) return current;
        }
        return null;
    }

    // Supports bottom-up on what is under them, beams on a kept post or on rock, standing props on what is under them, hanging
    // things on what is above, ladders on the wall behind; webs where they are attached.
    @Override
    public List<StructureTemplate.StructureBlockInfo> reprocess(ServerWorldAccess world, BlockPos pos, BlockPos pivot, List<StructureTemplate.StructureBlockInfo> original,
                                                                List<StructureTemplate.StructureBlockInfo> current, StructurePlacementData data) {
        Map<Long, StructureTemplate.StructureBlockInfo> cells = new HashMap<>();
        for (StructureTemplate.StructureBlockInfo info : current) cells.put(info.pos().asLong(), info);
        Set<Long> removed = new HashSet<>();

        List<StructureTemplate.StructureBlockInfo> posts = new ArrayList<>();
        List<StructureTemplate.StructureBlockInfo> floors = new ArrayList<>();
        List<StructureTemplate.StructureBlockInfo> hanging = new ArrayList<>();
        List<StructureTemplate.StructureBlockInfo> beams = new ArrayList<>();
        List<StructureTemplate.StructureBlockInfo> ladders = new ArrayList<>();
        for (StructureTemplate.StructureBlockInfo info : current) {
            switch (role(info.state())) {
                case POST -> posts.add(info);
                case FLOOR -> floors.add(info);
                case HANGING -> hanging.add(info);
                case BEAM -> beams.add(info);
                case LADDER -> ladders.add(info);
                default -> {
                }
            }
        }
        Comparator<StructureTemplate.StructureBlockInfo> byHeight = Comparator.comparingInt(info -> info.pos().getY());
        posts.sort(byHeight);
        floors.sort(byHeight);
        hanging.sort(byHeight.reversed());
        for (StructureTemplate.StructureBlockInfo info : posts) {
            if (!holds(world, cells, removed, info.pos().down())) removed.add(info.pos().asLong());
        }
        for (StructureTemplate.StructureBlockInfo info : floors) {
            if (!holds(world, cells, removed, info.pos().down())) removed.add(info.pos().asLong());
        }
        for (StructureTemplate.StructureBlockInfo info : hanging) {
            // top-down: a chain or rope hangs from something solid or from a kept one above it, a lantern from either
            BlockPos above = info.pos().up();
            StructureTemplate.StructureBlockInfo there = removed.contains(above.asLong()) ? null : cells.get(above.asLong());
            boolean chained = there != null && role(there.state()) == Role.HANGING;
            if (!chained && !holds(world, cells, removed, above)) removed.add(info.pos().asLong());
        }
        for (StructureTemplate.StructureBlockInfo info : ladders) {
            Direction back = info.state().rotate(data.getRotation()).get(Properties.HORIZONTAL_FACING).getOpposite();
            if (!holds(world, cells, removed, info.pos().offset(back))) removed.add(info.pos().asLong());
        }

        // a beam stays if it touches a kept post or rock, or another beam that does
        Set<Long> kept = new HashSet<>();
        Deque<StructureTemplate.StructureBlockInfo> queue = new ArrayDeque<>();
        Set<Long> beamCells = new HashSet<>();
        for (StructureTemplate.StructureBlockInfo info : beams) beamCells.add(info.pos().asLong());
        for (StructureTemplate.StructureBlockInfo info : beams) {
            for (Direction direction : Direction.values()) {
                BlockPos next = info.pos().offset(direction);
                long key = next.asLong();
                StructureTemplate.StructureBlockInfo there = removed.contains(key) ? null : cells.get(key);
                boolean anchored = there != null ? role(there.state()) == Role.POST : worldSolid(world, next);
                if (anchored && kept.add(info.pos().asLong())) {
                    queue.add(info);
                    break;
                }
            }
        }
        while (!queue.isEmpty()) {
            BlockPos at = queue.poll().pos();
            for (Direction direction : Direction.values()) {
                long key = at.offset(direction).asLong();
                if (beamCells.contains(key) && kept.add(key)) queue.add(cells.get(key));
            }
        }
        for (StructureTemplate.StructureBlockInfo info : beams) {
            if (!kept.contains(info.pos().asLong())) removed.add(info.pos().asLong());
        }

        List<StructureTemplate.StructureBlockInfo> result = new ArrayList<>(current.size());
        for (StructureTemplate.StructureBlockInfo info : current) {
            if (removed.contains(info.pos().asLong())) continue;
            if (info.state().isAir() && web(world, cells, removed, info.pos())) {
                result.add(new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.COBWEB.getDefaultState(), null));
            } else {
                result.add(info);
            }
        }
        return result;
    }

    // a web in a corner or against a wall or the ceiling: two solid neighbours, one of them above or beside
    private static boolean web(ServerWorldAccess world, Map<Long, StructureTemplate.StructureBlockInfo> cells, Set<Long> removed, BlockPos at) {
        if ((MathHelper.hashCode(at.getX(), at.getY(), at.getZ()) & 0xFFFF) >= WEB_CHANCE) return false;
        int solid = 0;
        boolean upOrSide = false;
        for (Direction direction : Direction.values()) {
            if (!holds(world, cells, removed, at.offset(direction))) continue;
            solid++;
            upOrSide |= direction != Direction.DOWN;
        }
        return solid >= 2 && upOrSide;
    }

    // the block here, as it will be after the piece is placed, can hold something up
    private static boolean holds(ServerWorldAccess world, Map<Long, StructureTemplate.StructureBlockInfo> cells, Set<Long> removed, BlockPos pos) {
        long key = pos.asLong();
        StructureTemplate.StructureBlockInfo info = removed.contains(key) ? null : cells.get(key);
        if (info != null) {
            BlockState state = info.state();
            Role role = role(state);
            return solid(state) && role != Role.HANGING && role != Role.LADDER && role != Role.SCONCE && !state.isOf(Blocks.COBWEB);
        }
        return worldSolid(world, pos);
    }

    private static boolean worldSolid(ServerWorldAccess world, BlockPos pos) {
        return !world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4) || solid(world.getBlockState(pos));
    }

    private static Role role(BlockState state) {
        Block block = state.getBlock();
        String name = Registries.BLOCK.getId(block).getPath();
        if (block == DecorativeBlockRegistryME.WALL_SCONCE) return Role.SCONCE;
        return switch (name) {
            case "larch_log" -> state.contains(Properties.AXIS) && state.get(Properties.AXIS) != Direction.Axis.Y ? Role.BEAM : Role.POST;
            case "larch_fence", "stone_brick_wall", "chiseled_stone_bricks_wall", "chiseled_stone_bricks" -> Role.POST;
            case "larch_planks", "larch_slab" -> Role.BEAM;
            case "barrel", "furnace", "blast_furnace", "smithing_table", "anvil", "cauldron", "coal_block", "raw_iron_block" -> Role.FLOOR;
            case "dwarven_lantern", "lantern" -> state.contains(Properties.HANGING) && state.get(Properties.HANGING) ? Role.HANGING : Role.FLOOR;
            case "rope", "chain", "iron_chain" -> Role.HANGING;
            case "larch_ladder" -> Role.LADDER;
            default -> Role.NONE;
        };
    }

    private static boolean solid(BlockState state) {
        return !state.isAir() && state.getFluidState().isEmpty();
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
