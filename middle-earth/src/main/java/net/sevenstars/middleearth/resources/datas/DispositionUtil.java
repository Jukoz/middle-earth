package net.sevenstars.middleearth.resources.datas;

import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.minecraft.entity.player.PlayerEntity;

public class DispositionUtil {
    public static Disposition getDisposition(PlayerEntity player){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data == null) return null;
        return data.getCurrentDisposition();
    }
}
