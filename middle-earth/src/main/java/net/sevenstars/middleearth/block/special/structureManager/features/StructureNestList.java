package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.ArrayList;
import java.util.List;

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

    // Nests that a manager keeps from before its data was what it is, or that came with a template of another one. Nests of
    // people with no bed and no marker are from before beds were tagged, and would respawn the whole pool beside those born in beds
    public int removeStale(StructureManagerData data) {
        int before = managers.size();
        managers = new ArrayList<>(managers.stream().filter(nest -> {
            SpawnNestNodeData known = data.findSpawnNest(nest.getId());
            return known != null && !(known.isPeople() && !nest.isMarked());
        }).toList());
        return before - managers.size();
    }

    public void addNest(SpawnNestManager spawnNestManager) {
        this.managers.add(spawnNestManager);
    }
}
