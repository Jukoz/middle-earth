package net.jesteur.me.utils;

import net.minecraft.nbt.NbtCompound;

public class HallucinationData {
    public static final String KEY = "hallucination";

    public static int addHallucination(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int hallucination = nbt.getInt(KEY);

        hallucination = Math.min(40, hallucination + amount);
        hallucination = Math.max(0, hallucination);

        nbt.putInt(KEY, hallucination);
        return hallucination;
    }

    public static int readHallucination(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int hallucination = nbt.getInt(KEY);

        return hallucination;
    }
}
