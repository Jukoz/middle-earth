package net.sevenstars.middleearth.statusEffects;

import net.minecraft.nbt.CompoundTag;
import net.sevenstars.middleearth.utils.IEntityDataSaver;

public class EnshroudedData {
    public static final String KEY = "enshrouded";
    public static final int STOPPING_TICK = 30;

    public static void addEffect(IEntityDataSaver player, int amount) {
        CompoundTag nbt = player.getPersistentData();
        int effect = readEffect(player);

        int updatedEffect = Math.max(0, Math.min(100, effect + amount));
        if (updatedEffect != effect) {
            nbt.putInt(KEY, updatedEffect);
        }
    }

    public static int readEffect(IEntityDataSaver player) {
        return player.getPersistentData().getInt(KEY);
    }

    public static void resetEffect(IEntityDataSaver player){
        CompoundTag nbt = player.getPersistentData();
        if (nbt.getInt(KEY) != 0) {
            nbt.putInt(KEY, 0);
        }
    }

    public static void stopEffect(IEntityDataSaver player){
        CompoundTag nbt = player.getPersistentData();
        if (nbt.getInt(KEY) != STOPPING_TICK) {
            nbt.putInt(KEY, STOPPING_TICK);
        }
    }
}
