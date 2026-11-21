package net.sevenstars.middleearth.commands.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.block.TrialSpawnerBlock;
import net.minecraft.block.VaultBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// A piece's blocks as the template file stores them, and the world as they are placed in it
class TemplateSnapshot {
    static final String PALETTE = "palette";
    static final String PALETTES = "palettes";
    static final String BLOCKS = "blocks";
    static final String SIZE = "size";
    private static final String POS = "pos";
    private static final String STATE = "state";
    private static final String NBT = "nbt";
    private static final String LOOT_TABLE_SEED = "LootTableSeed";
    private static final int DIFFERENCES_LOGGED = 6;
    private static final int DIFFERENCE_LENGTH = 90;

    private record Held(BlockState state, @Nullable NbtCompound data) {}

    static BlockBox boxOf(StructureTemplate template, BlockPos origin, BlockRotation rotation) {
        return template.calculateBoundingBox(origin, rotation, BlockPos.ORIGIN, BlockMirror.NONE);
    }

    // Unrotated size
    private static Vec3i sizeOf(BlockBox box, BlockRotation rotation) {
        boolean turned = rotation == BlockRotation.CLOCKWISE_90 || rotation == BlockRotation.COUNTERCLOCKWISE_90;
        return new Vec3i(turned ? box.getBlockCountZ() : box.getBlockCountX(), box.getBlockCountY(), turned ? box.getBlockCountX() : box.getBlockCountZ());
    }

    static void fill(ServerWorld world, BlockBox box, BlockState state) {
        for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            if(!world.isInBuildLimit(pos))
                continue;
            // Removing a chest spills its contents, rolling loot if it was never opened. Without its data there's nothing to drop
            if(world.getBlockState(pos).hasBlockEntity())
                world.removeBlockEntity(pos);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE | Block.SKIP_DROPS);
        }
    }

    static StructureEditSession.Piece place(ServerWorld world, StructureEditSession.Piece piece, boolean spaced) {
        StructureTemplate template = world.getStructureTemplateManager().getTemplate(piece.template()).orElseThrow();
        piece = new StructureEditSession.Piece(piece.template(), piece.origin(), piece.rotation(), boxOf(template, piece.origin(), piece.rotation()), piece.loaded());
        // Open space is filled with structure void, left out again when saving
        if(spaced)
            fill(world, piece.box(), Blocks.STRUCTURE_VOID.getDefaultState());
        // Placed as saved. Left to settle, a door at a piece's edge would fall and fences reach for missing neighbours
        StructurePlacementData placement = new StructurePlacementData().setRotation(piece.rotation()).setIgnoreEntities(true).setUpdateNeighbors(true);
        template.place(world, piece.origin(), piece.origin(), placement, world.getRandom(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        return new StructureEditSession.Piece(piece.template(), piece.origin(), piece.rotation(), piece.box(), spaced ? read(world, piece, null) : piece.loaded());
    }

    static NbtCompound withoutSeeds(NbtCompound template) {
        NbtList blocks = template.getListOrEmpty(BLOCKS);
        for(int i = 0; i < blocks.size(); i++)
            blocks.getCompoundOrEmpty(i).getCompound(NBT).ifPresent(data -> data.remove(LOOT_TABLE_SEED));
        return template;
    }

    private static Map<BlockPos, Held> heldIn(ServerWorld world, NbtCompound template) {
        Map<BlockPos, Held> held = new HashMap<>();
        NbtList palette = template.getListOrEmpty(PALETTE);
        NbtList blocks = template.getListOrEmpty(BLOCKS);
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            NbtList at = block.getListOrEmpty(POS);
            held.put(new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0)), new Held(
                    NbtHelper.toBlockState(world.getRegistryManager().getOrThrow(RegistryKeys.BLOCK), palette.getCompoundOrEmpty(block.getInt(STATE, 0))),
                    block.getCompound(NBT).orElse(null)));
        }
        return held;
    }

    private static boolean isWorldsDoing(BlockState held, BlockState now) {
        Block before = held.getBlock();
        Block after = now.getBlock();
        if(before == after)
            return before instanceof TrialSpawnerBlock || before instanceof VaultBlock || before instanceof SpawnerBlock;
        return isSoil(before) && isSoil(after);
    }

    private static boolean isSoil(Block block) {
        return block == Blocks.DIRT || block instanceof SpreadableBlock;
    }

    static String differences(NbtCompound before, NbtCompound now) {
        Map<BlockPos, String> old = described(before);
        Map<BlockPos, String> current = described(now);
        Set<BlockPos> all = new HashSet<>(old.keySet());
        all.addAll(current.keySet());
        List<BlockPos> differing = new ArrayList<>(all.stream().filter(pos -> !String.valueOf(old.get(pos)).equals(String.valueOf(current.get(pos)))).toList());
        differing.sort(Comparator.naturalOrder());
        StringBuilder out = new StringBuilder("%d blocks differ".formatted(differing.size()));
        for(int i = 0; i < Math.min(DIFFERENCES_LOGGED, differing.size()); i++) {
            BlockPos pos = differing.get(i);
            out.append(i == 0 ? ": " : "; ").append(pos.toShortString()).append(" ").append(shortened(old.get(pos))).append(" -> ").append(shortened(current.get(pos)));
        }
        return out.toString();
    }

    private static Map<BlockPos, String> described(NbtCompound template) {
        Map<BlockPos, String> described = new HashMap<>();
        NbtList palette = template.getListOrEmpty(PALETTE);
        NbtList blocks = template.getListOrEmpty(BLOCKS);
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            NbtList at = block.getListOrEmpty(POS);
            described.put(new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0)),
                    palette.getCompoundOrEmpty(block.getInt(STATE, 0)) + block.getCompound(NBT).map(NbtCompound::toString).orElse(""));
        }
        return described;
    }

    private static String shortened(@Nullable String text) {
        if(text == null)
            return "nothing";
        return text.length() > DIFFERENCE_LENGTH ? text.substring(0, DIFFERENCE_LENGTH) + "..." : text;
    }

    private record Found(BlockPos pos, BlockState state, @Nullable NbtCompound data, int order) {}

    // Blocks as the template stores them: rotated back, full blocks first, data last
    // Changes the world made since loading read as unchanged
    static NbtCompound read(ServerWorld world, StructureEditSession.Piece piece, @Nullable NbtCompound loaded) {
        Map<BlockPos, Held> held = loaded == null ? Map.of() : heldIn(world, loaded);
        Vec3i size = sizeOf(piece.box(), piece.rotation());
        BlockRotation back = switch (piece.rotation()) {
            case CLOCKWISE_90 -> BlockRotation.COUNTERCLOCKWISE_90;
            case COUNTERCLOCKWISE_90 -> BlockRotation.CLOCKWISE_90;
            default -> piece.rotation();
        };
        List<Found> found = new ArrayList<>();
        BlockBox box = piece.box();
        for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            BlockState state = world.getBlockState(pos);
            if(state.isOf(Blocks.STRUCTURE_VOID))
                continue;
            BlockPos local = StructureTemplate.transformAround(pos.subtract(piece.origin()), BlockMirror.NONE, back, BlockPos.ORIGIN);
            if(local.getX() < 0 || local.getY() < 0 || local.getZ() < 0 || local.getX() >= size.getX() || local.getY() >= size.getY() || local.getZ() >= size.getZ())
                continue;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            NbtCompound data = blockEntity == null ? null : blockEntity.createNbtWithIdentifyingData(world.getRegistryManager());
            if(data != null) {
                data.remove("x");
                data.remove("y");
                data.remove("z");
                // Chests get a new seed every time a template is placed
                data.remove(LOOT_TABLE_SEED);
            }
            BlockState turned = state.rotate(back);
            Held before = held.get(local);
            if(before != null && isWorldsDoing(before.state(), turned)) {
                turned = before.state();
                data = before.data();
            }
            int order = data != null ? 2 : !turned.getBlock().hasDynamicBounds() && turned.isFullCube(world, pos) ? 0 : 1;
            found.add(new Found(local.toImmutable(), turned, data, order));
        }
        found.sort(Comparator.<Found>comparingInt(Found::order).thenComparingInt(block -> block.pos().getY())
                .thenComparingInt(block -> block.pos().getX()).thenComparingInt(block -> block.pos().getZ()));
        return toTemplate(found, size);
    }

    private static NbtCompound toTemplate(List<Found> found, Vec3i size) {
        List<BlockState> palette = new ArrayList<>();
        NbtList blocks = new NbtList();
        for(Found block : found) {
            int index = palette.indexOf(block.state());
            if(index < 0) {
                index = palette.size();
                palette.add(block.state());
            }
            NbtList pos = new NbtList();
            pos.add(NbtInt.of(block.pos().getX()));
            pos.add(NbtInt.of(block.pos().getY()));
            pos.add(NbtInt.of(block.pos().getZ()));
            NbtCompound entry = new NbtCompound();
            entry.put(POS, pos);
            entry.putInt(STATE, index);
            if(block.data() != null)
                entry.put(NBT, block.data());
            blocks.add(entry);
        }
        NbtList states = new NbtList();
        for(BlockState state : palette)
            states.add(NbtHelper.fromBlockState(state));
        NbtList sizes = new NbtList();
        sizes.add(NbtInt.of(size.getX()));
        sizes.add(NbtInt.of(size.getY()));
        sizes.add(NbtInt.of(size.getZ()));
        NbtCompound read = new NbtCompound();
        read.put(SIZE, sizes);
        read.put(PALETTE, states);
        read.put(BLOCKS, blocks);
        return read;
    }
}
