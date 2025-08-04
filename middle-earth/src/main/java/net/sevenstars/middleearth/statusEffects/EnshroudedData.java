package net.sevenstars.middleearth.statusEffects;

import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.utils.IEntityDataSaver;

public class EnshroudedData {
    public static final String KEY = "enshrouded";
    public static final int STOPPING_TICK = 30;

    public static void addEffect(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int effect = readEffect(player);

        effect = Math.min(100, effect + amount);
        effect = Math.max(0, effect);

        nbt.putInt(KEY, effect);
    }

    public static int readEffect(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(KEY).isPresent() ? nbt.getInt(KEY).get() : 0;
    }
    public static void resetEffect(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(KEY, 0);
    }

    public static void stopEffect(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(KEY, STOPPING_TICK);
    }
}
