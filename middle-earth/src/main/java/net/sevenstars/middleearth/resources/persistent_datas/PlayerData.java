package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class PlayerData {
    private ResourceLocation faction;
    private ResourceLocation spawn;
    private ResourceLocation race;
    private BlockPos posOrigin;
    private BlockPos middleEarthReturnPos;
    private ResourceLocation dimensionOrigin;
    private int delversFearCountInSeconds;
    private transient Runnable dirtyMarker = () -> {};

    public PlayerData() {
    }

    public PlayerData(CompoundTag nbt) {
        faction = readResourceLocation(nbt, "faction");
        spawn = readResourceLocation(nbt, "spawn");
        race = readResourceLocation(nbt, "race");
        dimensionOrigin = readResourceLocation(nbt, "dimensionOrigin");

        int[] origin = nbt.contains("origin_pos", Tag.TAG_INT_ARRAY)
                ? nbt.getIntArray("origin_pos")
                : nbt.getIntArray("posOrigin");
        if (origin.length == 3) {
            posOrigin = new BlockPos(origin[0], origin[1], origin[2]);
        }
        int[] middleEarthReturn = nbt.getIntArray("middle_earth_return_pos");
        if (middleEarthReturn.length == 3) {
            middleEarthReturnPos = new BlockPos(
                    middleEarthReturn[0],
                    middleEarthReturn[1],
                    middleEarthReturn[2]
            );
        }

        delversFearCountInSeconds = nbt.getInt("delversFearCountInSeconds");
    }

    public void setDirtyMarker(Runnable dirtyMarker) {
        this.dirtyMarker = dirtyMarker == null ? () -> {} : dirtyMarker;
    }

    private void markDirty() {
        dirtyMarker.run();
    }

    public CompoundTag createNbt() {
        CompoundTag nbt = new CompoundTag();
        if (faction != null) {
            nbt.putString("faction", faction.toString());
        }
        if (spawn != null) {
            nbt.putString("spawn", spawn.toString());
        }
        if (race != null) {
            nbt.putString("race", race.toString());
        }
        if (posOrigin != null) {
            nbt.putIntArray("origin_pos", new int[]{posOrigin.getX(), posOrigin.getY(), posOrigin.getZ()});
        }
        if (dimensionOrigin != null) {
            nbt.putString("dimensionOrigin", dimensionOrigin.toString());
        }
        if (middleEarthReturnPos != null) {
            nbt.putIntArray(
                    "middle_earth_return_pos",
                    new int[]{
                            middleEarthReturnPos.getX(),
                            middleEarthReturnPos.getY(),
                            middleEarthReturnPos.getZ()
                    }
            );
        }
        nbt.putInt("delversFearCountInSeconds", delversFearCountInSeconds);
        return nbt;
    }

    public boolean assignNewFactionInformation(ResourceLocation factionId, ResourceLocation spawnId) {
        if (Objects.equals(faction, factionId) && Objects.equals(spawn, spawnId)) {
            return true;
        }
        faction = factionId;
        spawn = spawnId;
        markDirty();
        return true;
    }

    public boolean assignNewRace(ResourceLocation raceId) {
        if (Objects.equals(race, raceId)) {
            return true;
        }
        race = raceId;
        markDirty();
        return true;
    }

    public boolean assignNewOrigin(ResourceLocation dimensionOrigin, BlockPos newBlockPos) {
        if (Objects.equals(this.dimensionOrigin, dimensionOrigin) && Objects.equals(posOrigin, newBlockPos)) {
            return true;
        }
        this.dimensionOrigin = dimensionOrigin;
        posOrigin = newBlockPos;
        markDirty();
        return true;
    }

    public boolean assignMiddleEarthReturnPos(BlockPos newBlockPos) {
        if (Objects.equals(this.middleEarthReturnPos, newBlockPos)) {
            return true;
        }
        this.middleEarthReturnPos = newBlockPos;
        markDirty();
        return true;
    }

    public ResourceLocation getFaction() {
        return faction;
    }

    public ResourceLocation getRace() {
        return race;
    }

    public ResourceLocation getSpawn() {
        return spawn;
    }

    public ResourceLocation getDimensionOrigin() {
        return dimensionOrigin;
    }

    public BlockPos getOriginPos() {
        return posOrigin;
    }

    public BlockPos getMiddleEarthReturnPos() {
        return middleEarthReturnPos;
    }

    public int getDelversFearCountInSeconds() {
        return delversFearCountInSeconds;
    }

    public void addToDelversFearCountInSeconds() {
        delversFearCountInSeconds++;
        markDirty();
    }

    public void resetDelversFearCount() {
        if (delversFearCountInSeconds != 0) {
            delversFearCountInSeconds = 0;
            markDirty();
        }
    }

    private static ResourceLocation readResourceLocation(CompoundTag nbt, String key) {
        String value = nbt.getString(key);
        return value.isEmpty() ? null : ResourceLocation.tryParse(value);
    }
}
