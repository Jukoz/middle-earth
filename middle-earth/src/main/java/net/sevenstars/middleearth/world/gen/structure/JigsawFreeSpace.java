package net.sevenstars.middleearth.world.gen.structure;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;

// Stands in for vanilla's jigsaw free space: a region less the boxes already placed in it.
// Vanilla cuts every piece out of one voxel shape, which gets slower with each piece
public final class JigsawFreeSpace extends VoxelShape {
    private final Box region;
    private final List<Box> taken = new ArrayList<>();

    public JigsawFreeSpace(Box region) {
        super(full());
        this.region = region;
    }

    private static BitSetVoxelSet full() {
        BitSetVoxelSet voxels = new BitSetVoxelSet(1, 1, 1);
        voxels.set(0, 0, 0);
        return voxels;
    }

    public void take(Box box) {
        taken.add(box);
    }

    // Same test as vanilla: all of the candidate inside the region and none of it in a placed box
    public boolean fits(Box candidate) {
        if(candidate.minX < region.minX || candidate.minY < region.minY || candidate.minZ < region.minZ
                || candidate.maxX > region.maxX || candidate.maxY > region.maxY || candidate.maxZ > region.maxZ)
            return false;
        for(Box box : taken) {
            if(box.intersects(candidate))
                return false;
        }
        return true;
    }

    @Override
    public DoubleList getPointPositions(Direction.Axis axis) {
        return DoubleArrayList.wrap(new double[]{region.getMin(axis), region.getMax(axis)});
    }
}
