package net.sevenstars.middleearth.utils;

import net.minecraft.nbt.NbtCompound;

public class PlayerMovementData {
    public static final String KEY = "player_afk_data";
    public static final int MAX_AFK_TIME = 100;

    public static void addAFKTime(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        if(nbt.getInt(KEY).isEmpty()) return;
        int movement = nbt.getInt(KEY).get();

        movement = Math.min(MAX_AFK_TIME, movement + amount);
        movement = Math.max(0, movement);

        nbt.putInt(KEY, movement);
    }

    public static int readAFK(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getInt(KEY).get();
    }

    public static void resetAFK(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(KEY, 0);
    }
}
