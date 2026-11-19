package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

// Entry points other areas call. The work is in NestLookup, NestSpawner and HostIndex
public class StructureManagerService {
    public static void register() {
        HostIndex.register();
    }

    public static StructureManagerData getStructureManagerData(World world, Identifier structureManagerDataId){
        return NestLookup.getStructureManagerData(world, structureManagerDataId);
    }

    public static void registerHost(World world, BlockPos pos) {
        HostIndex.add(world, pos);
    }

    public static void unregisterHost(@Nullable World world, BlockPos pos) {
        HostIndex.remove(world, pos);
    }

    public static boolean isClose(World world, BlockPos pos, int radius) {
        return HostIndex.isClose(world, pos, radius);
    }
}
