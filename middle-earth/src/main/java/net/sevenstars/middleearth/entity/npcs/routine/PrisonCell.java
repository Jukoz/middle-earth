package net.sevenstars.middleearth.entity.npcs.routine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// The floor of a cell as seen from above: only x and z matter, both ends included
public record PrisonCell(int minX, int minZ, int maxX, int maxZ) {
    public static final Codec<PrisonCell> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("min_x").forGetter(PrisonCell::minX),
            Codec.INT.fieldOf("min_z").forGetter(PrisonCell::minZ),
            Codec.INT.fieldOf("max_x").forGetter(PrisonCell::maxX),
            Codec.INT.fieldOf("max_z").forGetter(PrisonCell::maxZ)
    ).apply(instance, PrisonCell::new));

    public static final int CORNERS = 4;

    public boolean contains(BlockPos pos) {
        return pos.getX() >= minX && pos.getX() <= maxX && pos.getZ() >= minZ && pos.getZ() <= maxZ;
    }

    // The corners that stand closest to each other belong to the same cell. The last group is short when corners are missing
    public static List<List<BlockPos>> group(List<BlockPos> corners) {
        List<BlockPos> left = new ArrayList<>(corners);
        List<List<BlockPos>> groups = new ArrayList<>();
        while(!left.isEmpty()) {
            BlockPos first = left.removeFirst();
            left.sort(Comparator.comparingDouble(corner -> corner.getSquaredDistance(first)));
            List<BlockPos> group = new ArrayList<>();
            group.add(first);
            while(group.size() < CORNERS && !left.isEmpty())
                group.add(left.removeFirst());
            groups.add(group);
        }
        return groups;
    }

    // Only whole groups of corners make a cell
    public static List<PrisonCell> cells(List<BlockPos> corners) {
        List<PrisonCell> cells = new ArrayList<>();
        for(List<BlockPos> group : group(corners)) {
            if(group.size() != CORNERS)
                continue;
            cells.add(new PrisonCell(group.stream().mapToInt(BlockPos::getX).min().orElse(0), group.stream().mapToInt(BlockPos::getZ).min().orElse(0),
                    group.stream().mapToInt(BlockPos::getX).max().orElse(0), group.stream().mapToInt(BlockPos::getZ).max().orElse(0)));
        }
        return cells;
    }

    public double centerX() {
        return (minX + maxX + 1) / 2.0;
    }

    public double centerZ() {
        return (minZ + maxZ + 1) / 2.0;
    }
}
