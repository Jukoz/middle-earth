package net.sevenstars.middleearth.resources.datas.races;

import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class RaceUtil {

    public static void updateRace(Player player, Race race, boolean shouldHeal){
        Race previousRace = PlayerDataService.getPlayerRace(player, player.level());
        boolean havePreviousRace =  previousRace != null;
        boolean raceExists = race != null;

        // [RESET]
        if(havePreviousRace){
            previousRace.reverseAttributes(player);
            PlayerDataService.setRace(player, player.level(), null);
        }

        reset(player);

        // [SET]
        if(raceExists){
            PlayerDataService.setRace(player, player.level(), race.getId());
        }

        if(shouldHeal)
            player.heal(player.getMaxHealth());
    }


    public static RaceType getRaceType(Player player){
        Race race = PlayerDataService.getPlayerRace(player, player.level());
        if(race != null)
            return race.getRaceType();
        else
            return null;
    }

    public static void initializeRace(ServerPlayer player) {
        Race race = PlayerDataService.getPlayerRace(player, player.level());
        updateRace(player, race, false);
    }

    public static void reset(Player player) {
        AttributePool.reverse(player);
    }
}
