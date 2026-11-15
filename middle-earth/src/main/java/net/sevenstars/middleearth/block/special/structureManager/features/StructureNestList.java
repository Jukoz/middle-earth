package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StructureNestList {
    public static final Codec<StructureNestList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(SpawnNestManager.CODEC).fieldOf("Managers").forGetter(StructureNestList::getManagers)
    ).apply(instance, StructureNestList::new));

    private List<SpawnNestManager> managers;

    public StructureNestList() {
        this.managers = new ArrayList<>();
    }

    // The list a codec reads cannot be added to
    public StructureNestList(List<SpawnNestManager> managers) {
        this.managers = new ArrayList<>(managers);
    }

    public void computeDeath(LivingEntity entity) {
        for (SpawnNestManager nest : managers) {
            if(nest.removeEntity(entity))
                return;
        }
    }

    public List<SpawnNestManager> getManagers() {
        return managers;
    }

    // Nests there is no data for any more. And those of the nest blocks there were before beds were tagged and animals
    // marked, which would respawn beside what those spawn
    public int removeStale(World world) {
        int before = managers.size();
        managers.removeIf(nest -> StructureManagerService.findNest(world, nest.getId()) == null || !nest.isMarked());
        return before - managers.size();
    }

    public int removeBedsExcept(Set<BlockPos> beds) {
        int before = managers.size();
        managers.removeIf(nest -> nest.getBed() != null && !beds.contains(nest.getBed()));
        return before - managers.size();
    }

    public void addNest(SpawnNestManager spawnNestManager) {
        this.managers.add(spawnNestManager);
    }
}
