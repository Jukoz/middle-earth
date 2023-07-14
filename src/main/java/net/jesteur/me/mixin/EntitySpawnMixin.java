package net.jesteur.me.mixin;

import com.google.common.collect.ImmutableList;
import net.jesteur.me.world.spawners.SpawnerNPCs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerWorld.class)
public class EntitySpawnMixin {

    // We replace the first argument of type "List<Spawner>" (which is the 10th argument of the constructor)
    // By our own list that contains the vanilla list + the SpawnerNPCs
    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static List<Spawner> ServerWorld(List<Spawner> spawners) {
        List<Spawner> newSpawners = new ArrayList<>(spawners);
        newSpawners.add(new SpawnerNPCs());
        return ImmutableList.copyOf(newSpawners);
    }
}
