package net.sevenstars.middleearth.mixin;

import com.google.common.collect.ImmutableList;
import net.sevenstars.middleearth.world.spawners.SpawnerNPCs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.SpecialSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerWorld.class)
public class EntitySpawnMixin {

    // We replace the first argument of type "List<Spawner>" (which is the 10th argument of the constructor)
    // By our own list that contains the vanilla list + the SpawnerNPCs
    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static List<SpecialSpawner> ServerWorld(List<SpecialSpawner> spawners) {
        List<SpecialSpawner> newSpawners = new ArrayList<>(spawners);
        newSpawners.add(new SpawnerNPCs());
        return ImmutableList.copyOf(newSpawners);
    }

    // Keep this method in case of CPR for spawners
    /*@Shadow private List<SpecialSpawner> spawners;
    @Inject(method = "tickSpawners", at = @At(value = "HEAD"))
    public void tickSpawners(boolean spawnMonsters, boolean spawnAnimals, CallbackInfo ci) {
        for(SpecialSpawner specialSpawner1 : spawners) {
            System.out.println("Tick Spawner: " + specialSpawner1.toString());
        }
    }*/
}
