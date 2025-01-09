package net.jukoz.me.resources.datas.races;

import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.datas.races.data.AttributeData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

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
