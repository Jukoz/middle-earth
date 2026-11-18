package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;

public class GeneratedStructures {
    // Template blocks loaded in an ordinary world (editing) aren't in a structure and must stay inert
    public static boolean contains(ServerWorld world, BlockPos pos) {
        StructureStart start = world.getStructureAccessor().getStructureContaining(pos, structure -> true);
        return start != null && start.hasChildren();
    }
}
