package net.sevenstars.middleearth.commands.custom;

import net.minecraft.util.math.BlockBox;

import java.util.ArrayList;
import java.util.List;

class BoxParts {
    // The blocks of whole outside hole, as boxes that don't overlap
    static List<BlockBox> without(BlockBox whole, BlockBox hole) {
        List<BlockBox> parts = new ArrayList<>(6);
        if(!whole.intersects(hole)) {
            parts.add(whole);
            return parts;
        }
        int minX = Math.max(whole.getMinX(), hole.getMinX());
        int minY = Math.max(whole.getMinY(), hole.getMinY());
        int minZ = Math.max(whole.getMinZ(), hole.getMinZ());
        int maxX = Math.min(whole.getMaxX(), hole.getMaxX());
        int maxY = Math.min(whole.getMaxY(), hole.getMaxY());
        int maxZ = Math.min(whole.getMaxZ(), hole.getMaxZ());
        if(whole.getMinX() < minX)
            parts.add(new BlockBox(whole.getMinX(), whole.getMinY(), whole.getMinZ(), minX - 1, whole.getMaxY(), whole.getMaxZ()));
        if(whole.getMaxX() > maxX)
            parts.add(new BlockBox(maxX + 1, whole.getMinY(), whole.getMinZ(), whole.getMaxX(), whole.getMaxY(), whole.getMaxZ()));
        if(whole.getMinY() < minY)
            parts.add(new BlockBox(minX, whole.getMinY(), whole.getMinZ(), maxX, minY - 1, whole.getMaxZ()));
        if(whole.getMaxY() > maxY)
            parts.add(new BlockBox(minX, maxY + 1, whole.getMinZ(), maxX, whole.getMaxY(), whole.getMaxZ()));
        if(whole.getMinZ() < minZ)
            parts.add(new BlockBox(minX, minY, whole.getMinZ(), maxX, maxY, minZ - 1));
        if(whole.getMaxZ() > maxZ)
            parts.add(new BlockBox(minX, minY, maxZ + 1, maxX, maxY, whole.getMaxZ()));
        return parts;
    }

    static boolean covers(List<BlockBox> boxes, BlockBox whole) {
        List<BlockBox> left = List.of(whole);
        for(BlockBox box : boxes) {
            List<BlockBox> next = new ArrayList<>();
            for(BlockBox part : left)
                next.addAll(without(part, box));
            if(next.isEmpty())
                return true;
            left = next;
        }
        return false;
    }
}
