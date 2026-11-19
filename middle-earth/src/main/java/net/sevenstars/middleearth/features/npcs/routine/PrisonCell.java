package net.sevenstars.middleearth.features.npcs.routine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.NpcDoor;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

// A cell's floor from above. Only x and z, both ends inclusive
public record PrisonCell(int minX, int minZ, int maxX, int maxZ) {
    public static final Codec<PrisonCell> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("min_x").forGetter(PrisonCell::minX),
            Codec.INT.fieldOf("min_z").forGetter(PrisonCell::minZ),
            Codec.INT.fieldOf("max_x").forGetter(PrisonCell::maxX),
            Codec.INT.fieldOf("max_z").forGetter(PrisonCell::maxZ)
    ).apply(instance, PrisonCell::new));

    // A bigger room isn't a cell, so hold the prisoner to the blocks round its spawn instead
    private static final int MAXIMUM_FLOOR = 64;
    private static final int OPEN_CELL_RADIUS = 1;

    public boolean contains(BlockPos pos) {
        return pos.getX() >= minX && pos.getX() <= maxX && pos.getZ() >= minZ && pos.getZ() <= maxZ;
    }

    // The room it was born in, bounded by anything solid and by doors
    public static PrisonCell around(World world, BlockPos start) {
        Set<BlockPos> floor = new HashSet<>();
        ArrayDeque<BlockPos> open = new ArrayDeque<>();
        floor.add(start);
        open.add(start);
        while(!open.isEmpty()) {
            BlockPos at = open.removeFirst();
            for(Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos next = at.offset(direction);
                if(floor.contains(next) || !isFloor(world, next))
                    continue;
                if(floor.size() >= MAXIMUM_FLOOR)
                    return new PrisonCell(start.getX() - OPEN_CELL_RADIUS, start.getZ() - OPEN_CELL_RADIUS, start.getX() + OPEN_CELL_RADIUS, start.getZ() + OPEN_CELL_RADIUS);
                floor.add(next);
                open.add(next);
            }
        }
        return new PrisonCell(floor.stream().mapToInt(BlockPos::getX).min().orElse(start.getX()), floor.stream().mapToInt(BlockPos::getZ).min().orElse(start.getZ()),
                floor.stream().mapToInt(BlockPos::getX).max().orElse(start.getX()), floor.stream().mapToInt(BlockPos::getZ).max().orElse(start.getZ()));
    }

    private static boolean isFloor(World world, BlockPos pos) {
        return isOpen(world, pos) && isOpen(world, pos.up()) && !world.getBlockState(pos.down()).getCollisionShape(world, pos.down()).isEmpty();
    }

    private static boolean isOpen(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof DoorBlock || state.getBlock() instanceof FenceGateBlock || state.getBlock() instanceof TrapdoorBlock || state.getBlock() instanceof NpcDoor)
            return false;
        return state.getCollisionShape(world, pos).isEmpty();
    }

    // In blocks, 0 inside
    public int distanceTo(BlockPos pos) {
        int x = Math.max(Math.max(minX - pos.getX(), pos.getX() - maxX), 0);
        int z = Math.max(Math.max(minZ - pos.getZ(), pos.getZ() - maxZ), 0);
        return Math.max(x, z);
    }

    public double centerX() {
        return (minX + maxX + 1) / 2.0;
    }

    public double centerZ() {
        return (minZ + maxZ + 1) / 2.0;
    }
}
