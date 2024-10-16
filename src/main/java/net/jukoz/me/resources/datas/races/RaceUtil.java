package net.jukoz.me.resources.datas.races;

import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void updateRace(ServerPlayerEntity player, Race race){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);

        boolean havePreviousRace = data.getRace() != null;
        boolean raceExists = race != null;

        // [RESET]
        if(havePreviousRace){
            RaceLookup.getRace(player.getWorld(), data.getRace()).reverseAttributes(player);
            data.setRace(null);
        }

        // [SET]
        if(raceExists){
            race.applyAttributes(player);
            data.setRace(race.getId());
        }
    }
}
