package net.sevenstars.middleearth.utils;

import net.minecraft.nbt.NbtCompound;

public class HallucinationData {
    public static final String KEY = "hallucination";
    public static final int STOPPING_TICK = 30;

    public static int addHallucination(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int hallucination = nbt.getInt(KEY);

        hallucination = Math.min(100, hallucination + amount);
        hallucination = Math.max(0, hallucination);

        nbt.putInt(KEY, hallucination);
        return hallucination;
    }

    public static int readHallucination(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int hallucination = nbt.getInt(KEY);

        return hallucination;
    }
    public static void resetHallucination(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(KEY, 0);
    }

    public static void stopHallucination(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(KEY, STOPPING_TICK);
    }
}
