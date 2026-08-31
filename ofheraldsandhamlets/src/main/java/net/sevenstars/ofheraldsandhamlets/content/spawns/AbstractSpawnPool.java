package net.sevenstars.ofheraldsandhamlets.content.spawns;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.sevenstars.ofheraldsandhamlets.dtos.spawn.Spawn;

public class AbstractSpawnPool {
    public static Spawn create(Vec3d pos, RegistryKey<DimensionType> dimensionType) {
        return new Spawn(pos, dimensionType);
    }
    public static Spawn createDynamic(Vec3d pos, RegistryKey<DimensionType> dimensionType) {
        return new Spawn(pos, true, dimensionType);
    }
}