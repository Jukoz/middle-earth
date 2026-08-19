package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerUtil {
    private static final TagKey<Block> CLIMBING_ATTRIBUTE_UNALLOWED_BLOCKS =
            TagKey.create(Registries.BLOCK, MiddleEarth.of("climbing_attribute_unallowed_blocks"));

    public static boolean isAgainstWall(Player entity) {
        return entity.horizontalCollision && checkIfBlockIsAllowed(entity.level(), entity);
    }

    private static boolean checkIfBlockIsAllowed(Level world, Player player) {
        BlockState blockstate = world.getBlockState(player.blockPosition().relative(player.getDirection()));
        boolean isSolid = blockstate.isRedstoneConductor(world, player.blockPosition());
        boolean isAllowed = !blockstate.is(CLIMBING_ATTRIBUTE_UNALLOWED_BLOCKS);
        return isSolid && isAllowed;
    }

    public static boolean isOfRace(@NotNull Player entity, @NotNull RaceType type){
        PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.level(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return raceType == type;
            }
        }
        return false;
    }

    public static boolean isOfRace(@NotNull Player entity, @NotNull List<RaceType> types){
        PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.level(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return types.contains(raceType);
            }
        }
        return false;
    }

    public static Faction fetchFaction(@NotNull Player entity){
        PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(entity);
        if(data != null && data.getFaction() != null){
            try {
                return FactionLookup.getFactionById(entity.level(), data.getFaction());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
    public static SpawnData fetchSpawn(@NotNull Player entity){
        PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(entity);
        if(data != null && data.getFaction() != null && data.getSpawn() != null){
            try {
                Faction faction = FactionLookup.getFactionById(entity.level(), data.getFaction());
                return faction.getSpawnData().findSpawn(data.getSpawn());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
}
