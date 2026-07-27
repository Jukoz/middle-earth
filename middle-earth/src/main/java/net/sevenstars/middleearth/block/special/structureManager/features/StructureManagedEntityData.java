package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class StructureManagedEntityData extends SavedData {
    private static final String DATA_NAME = "middle_earth_structure_managed_entities";
    private static final String ENTRIES = "Entries";
    private static final String UUID_KEY = "Uuid";
    private static final String MANAGER_POS = "ManagerPos";
    private static final String LAST_POS = "LastPos";
    private static final String DEAD = "Dead";
    private static final Factory<StructureManagedEntityData> FACTORY =
            new Factory<>(StructureManagedEntityData::new, StructureManagedEntityData::load);

    private final Map<UUID, Entry> entries = new HashMap<>();

    public static StructureManagedEntityData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
    }

    private static StructureManagedEntityData load(
            CompoundTag tag, HolderLookup.Provider registries
    ) {
        StructureManagedEntityData data = new StructureManagedEntityData();
        ListTag entries = tag.getList(ENTRIES, Tag.TAG_COMPOUND);
        for (Tag rawEntry : entries) {
            if (!(rawEntry instanceof CompoundTag entryTag)
                    || !entryTag.hasUUID(UUID_KEY)
                    || !entryTag.contains(MANAGER_POS, Tag.TAG_LONG)
                    || !entryTag.contains(LAST_POS, Tag.TAG_LONG)) {
                continue;
            }
            UUID uuid = entryTag.getUUID(UUID_KEY);
            data.entries.put(uuid, new Entry(
                    BlockPos.of(entryTag.getLong(MANAGER_POS)),
                    BlockPos.of(entryTag.getLong(LAST_POS)),
                    entryTag.getBoolean(DEAD)
            ));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag savedEntries = new ListTag();
        entries.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    CompoundTag entryTag = new CompoundTag();
                    entryTag.putUUID(UUID_KEY, entry.getKey());
                    entryTag.putLong(MANAGER_POS, entry.getValue().managerPos().asLong());
                    entryTag.putLong(LAST_POS, entry.getValue().lastPos().asLong());
                    entryTag.putBoolean(DEAD, entry.getValue().dead());
                    savedEntries.add(entryTag);
                });
        tag.put(ENTRIES, savedEntries);
        return tag;
    }

    public void track(UUID uuid, BlockPos managerPos, BlockPos lastPos) {
        Entry replacement = new Entry(
                managerPos.immutable(), lastPos.immutable(), false);
        if (!replacement.equals(entries.put(uuid, replacement))) {
            setDirty();
        }
    }

    public void markDead(UUID uuid, BlockPos managerPos, BlockPos lastPos) {
        Entry replacement = new Entry(
                managerPos.immutable(), lastPos.immutable(), true);
        if (!replacement.equals(entries.put(uuid, replacement))) {
            setDirty();
        }
    }

    @Nullable
    public Entry get(UUID uuid) {
        return entries.get(uuid);
    }

    public List<UUID> deadEntitiesFor(BlockPos managerPos) {
        ArrayList<UUID> result = new ArrayList<>();
        for (Map.Entry<UUID, Entry> entry : entries.entrySet()) {
            if (entry.getValue().dead()
                    && entry.getValue().managerPos().equals(managerPos)) {
                result.add(entry.getKey());
            }
        }
        return List.copyOf(result);
    }

    public void remove(UUID uuid) {
        if (entries.remove(uuid) != null) {
            setDirty();
        }
    }

    public record Entry(BlockPos managerPos, BlockPos lastPos, boolean dead) {
    }
}
