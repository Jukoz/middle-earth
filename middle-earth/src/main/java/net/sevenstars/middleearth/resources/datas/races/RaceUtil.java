package net.sevenstars.middleearth.resources.datas.races;

import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.races.data.AttributeData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void updateRace(PlayerEntity player, Race race, boolean shouldHeal){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);

        boolean havePreviousRace = data.getRace() != null;
        boolean raceExists = race != null;

        // [RESET]
        if(havePreviousRace){
            RaceLookup.getRace(player.getWorld(), data.getRace()).reverseAttributes(player);
            data.setRace(null);
        }

        reset(player);

        // [SET]
        if(raceExists){
            race.applyAttributes(player);
            data.setRace(race.getId());
        }

        if(shouldHeal)
            player.heal(player.getMaxHealth());
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

    public static void initializeRace(ServerPlayerEntity player) {
        Race race = getRace(player);
        updateRace(player, race, false);
    }

    public static void reset(PlayerEntity player) {
        AttributeData.reset(player);
    }
}
