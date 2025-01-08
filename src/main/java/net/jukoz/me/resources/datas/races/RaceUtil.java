package net.jukoz.me.resources.datas.races;

import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void updateRace(PlayerEntity player, Race race){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);

        boolean havePreviousRace = data.getRace() != null;
        boolean raceExists = race != null;

        // [RESET]
        if(havePreviousRace){
            RaceLookup.getRace(player.getWorld(), data.getRace()).reverseAttributes(player);
            race.resetAttributes(player);
            data.setRace(null);
        }

        // [SET]
        if(raceExists){
            race.applyAttributes(player);
            data.setRace(race.getId());
        }
    }

    public static Race getRace(PlayerEntity player){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data == null) return null;
        return data.getRace(player.getWorld());
    }

    public static RaceType getRaceType(PlayerEntity player){
        Race race = getRace(player);
        if(race != null)
            return race.getRaceType();
        else
            return null;
    }
}
