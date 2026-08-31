package net.sevenstars.ofhamletandheroes.content.spawns;

import net.minecraft.util.math.Vec3d;
import net.sevenstars.ofhamletandheroes.dtos.spawn.Spawn;

public class AbstractSpawnPool {
    public static Spawn create(Vec3d pos) {
        return new Spawn(pos);
    }
    public static Spawn createDynamic(Vec3d pos) {
        return new Spawn(pos, true);
    }
}