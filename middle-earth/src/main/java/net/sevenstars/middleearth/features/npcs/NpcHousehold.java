package net.sevenstars.middleearth.features.npcs;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NpcHousehold {
    private @Nullable BlockPos routineHome = null;
    // Set by its nest
    private @Nullable BlockPos assignedBed = null;
    private @Nullable BlockPos bedStand = null;
    private @Nullable BlockPos store = null;
    // Places the builder linked to its bed; the npc goes there first for work
    private List<BlockPos> links = List.of();

    public void set(@Nullable BlockPos bed, @Nullable BlockPos bedStand, @Nullable BlockPos store, List<BlockPos> links) {
        this.assignedBed = bed;
        this.bedStand = bedStand;
        this.store = store;
        this.links = links;
    }

    public void clear() {
        routineHome = null;
        set(null, null, null, List.of());
    }

    public @Nullable BlockPos getRoutineHome() {
        return routineHome;
    }

    public void setRoutineHome(@Nullable BlockPos routineHome) {
        this.routineHome = routineHome;
    }

    public @Nullable BlockPos getAssignedBed() {
        return assignedBed;
    }

    public @Nullable BlockPos getBedStand() {
        return bedStand;
    }

    public @Nullable BlockPos getStore() {
        return store;
    }

    public List<BlockPos> getLinks() {
        return links;
    }

    void write(WriteView view) {
        view.putNullable(NpcEntity.KeyStrings.ROUTINE_HOME, BlockPos.CODEC, routineHome);
        view.putNullable(NpcEntity.KeyStrings.ASSIGNED_BED, BlockPos.CODEC, assignedBed);
        view.putNullable(NpcEntity.KeyStrings.BED_STAND, BlockPos.CODEC, bedStand);
        view.putNullable(NpcEntity.KeyStrings.STORE, BlockPos.CODEC, store);
        view.put(NpcEntity.KeyStrings.LINKS, BlockPos.CODEC.listOf(), links);
    }

    void read(ReadView view) {
        routineHome = view.read(NpcEntity.KeyStrings.ROUTINE_HOME, BlockPos.CODEC).orElse(null);
        assignedBed = view.read(NpcEntity.KeyStrings.ASSIGNED_BED, BlockPos.CODEC).orElse(null);
        bedStand = view.read(NpcEntity.KeyStrings.BED_STAND, BlockPos.CODEC).orElse(null);
        store = view.read(NpcEntity.KeyStrings.STORE, BlockPos.CODEC).orElse(null);
        links = view.read(NpcEntity.KeyStrings.LINKS, BlockPos.CODEC.listOf()).orElse(List.of());
    }
}
