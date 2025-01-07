package net.jukoz.me.resources.datas;

import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.entity.player.PlayerEntity;

public class DispositionUtil {
    public static Disposition getDisposition(PlayerEntity player){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data == null) return null;
        return data.getCurrentDisposition();
    }
}
