package net.sevenstars.ofhallsandheralds.content.spawns;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionTypes;
import net.sevenstars.ofhallsandheralds.dtos.Spawn;
import net.sevenstars.ofhallsandheralds.registries.custom.SpawnRegistryHH;

import java.util.HashMap;
import java.util.Map;

public class SpawnDefault extends AbstractSpawnPool {
    public static final Spawn TEST = create(new Vec3d(0,0,0), DimensionTypes.OVERWORLD);

    public static Map<RegistryKey<Spawn>, Spawn> fetch() {
        Map<RegistryKey<Spawn>, Spawn> map = new HashMap<>();
        map.put(SpawnRegistryHH.TEST, TEST);
        return map;
    }
}
