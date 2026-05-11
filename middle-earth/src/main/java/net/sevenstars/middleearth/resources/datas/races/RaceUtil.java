package net.sevenstars.middleearth.resources.datas.races;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class RaceUtil {

    public static void updateRace(PlayerEntity player, Race race, boolean shouldHeal){
        Race previousRace = PlayerDataService.getPlayerRace(player, player.getWorld());
        boolean havePreviousRace =  previousRace != null;
        boolean raceExists = race != null;

        // [RESET]
        if(havePreviousRace){
            previousRace.reverseAttributes(player);
            PlayerDataService.setRace(player, player.getWorld(), null);
        }

        reset(player);

        // [SET]
        if(raceExists){
            PlayerDataService.setRace(player, player.getWorld(), race.getId());
        }

        if(shouldHeal)
            player.heal(player.getMaxHealth());
    }


    public static RaceType getRaceType(PlayerEntity player){
        Race race = PlayerDataService.getPlayerRace(player, player.getWorld());
        if(race != null)
            return race.getRaceType();
        else
            return null;
    }

    public static void initializeRace(ServerPlayerEntity player) {
        Race race = PlayerDataService.getPlayerRace(player, player.getWorld());
        updateRace(player, race, false);
    }

    public static void reset(PlayerEntity player) {
        AttributePool.reverse(player);
    }
}
