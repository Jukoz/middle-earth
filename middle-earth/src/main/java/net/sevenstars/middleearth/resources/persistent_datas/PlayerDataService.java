package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class PlayerDataService {
    private static PlayerData getPlayerData(PlayerEntity player){
        return StateSaverAndLoader.getPlayerState(player);
    }

    public static boolean clearPlayerData(PlayerEntity player){
        PlayerData data = getPlayerData(player);
        data.assignNewRace(null);
        data.assignNewFactionInformation(null, null);
        data.assignNewOrigin(null, null);
        return true;
    }
    public static boolean playerPassedOnboarding(PlayerEntity player){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return false;
        return !(playerData.getFaction() == null || playerData.getSpawn() == null);
    }
    public static Faction getPlayerFaction(PlayerEntity player, World world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        Identifier factionId = playerData.getFaction();
        if(factionId == null) return null;
        try{
            return FactionLookup.getFactionById(world, factionId);
        } catch (FactionIdentifierException exception){
            return null;
        }
    }
    public static boolean setNewFactionInformation(PlayerEntity player, World world, Identifier factionId){
        PlayerData playerData = getPlayerData(player);
        try{
            Faction faction = FactionLookup.getFactionById(world, factionId);
            setNewFactionInformation(player, world, factionId, faction.getSpawnData().getDefaultSpawn());
            return true;
        } catch (FactionIdentifierException exception){
            return false;
        }
    }
    public static boolean setNewFactionInformation(PlayerEntity player, World world, Identifier factionId, Identifier spawnId){
        PlayerData playerData = getPlayerData(player);
        playerData.assignNewFactionInformation(factionId, spawnId);
        return true;
    }
    public static DispositionType getPlayerDisposition(PlayerEntity player, World world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return DispositionType.NEUTRAL;
        return faction.getDisposition();
    }
    public static Race getPlayerRace(PlayerEntity player, World world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        Identifier raceId = playerData.getRace();
        if(raceId == null) return null;
        try{
            return RaceLookup.getRace(world, raceId);
        } catch (Exception exception){
            return null;
        }
    }
    public static boolean setRace(PlayerEntity player, World world, Identifier raceId){
        Race newRace = RaceLookup.getRace(world, raceId);
        if(newRace == null) return false;
        PlayerData playerData = getPlayerData(player);
        playerData.assignNewRace(raceId);
        newRace.applyPlayerAttributes(player);
        return true;
    }
    public static SpawnData getPlayerSpawnData(PlayerEntity player, World world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return null;
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        Identifier spawnId = playerData.getSpawn();
        if(spawnId == null) return null;
        return faction.getSpawnData().findSpawn(spawnId);
    }
    public static boolean setSpawn(ServerPlayerEntity player, World world, Identifier spawnId) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        if(faction.getSpawnData().findSpawn(spawnId) != null){
            PlayerData data = getPlayerData(player);
            data.assignNewFactionInformation(faction.getId(), spawnId);

            if(ModDimensions.isInMiddleEarth(player.getWorld())){
                ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(ModDimensions.ME_WORLD_KEY, getPlayerSpawnData(player, world).getBlockPos(), 0, true);
                player.setSpawnPoint(respawn, true);
                return true;
            }

            return true;
        }
        return false;
    }
    public static boolean resetSpawn(ServerPlayerEntity player, World world) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        SpawnDataHandler spawnDataHandler= faction.getSpawnData();
        if(spawnDataHandler == null) return false;
        setSpawn(player, world, spawnDataHandler.getDefaultSpawn());
        return true;
    }
    public static OriginAggregate getOriginAggregateOrDefault(PlayerEntity player, World world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return getDefaultOriginAggregate(world);
        BlockPos originPos = playerData.getOriginPos();
        Identifier dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return getDefaultOriginAggregate(world);
        }
        if(dimensionId == null){
            dimensionId = DimensionTypes.OVERWORLD.getValue();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static OriginAggregate getOriginAggregate(PlayerEntity player, World world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        BlockPos originPos = playerData.getOriginPos();
        Identifier dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return null;
        }
        if(dimensionId == null){
            dimensionId = DimensionTypes.OVERWORLD.getValue();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static boolean setOrigin(ServerPlayerEntity player, World world, Identifier dimensionId, BlockPos originPos) {
        if(world.getRegistryManager().getOptional(RegistryKeys.DIMENSION_TYPE).get() instanceof Registry<DimensionType> registry){
            if(registry.get(dimensionId) == null){
                PlayerData data = getPlayerData(player);
                data.assignNewOrigin(dimensionId, originPos);
                return true;
            }
        }
        return false;
    }
    public static boolean resetOrigin(ServerPlayerEntity player, World world) {
        OriginAggregate newOrigin = getDefaultOriginAggregate(world);
        PlayerData data = getPlayerData(player);
        data.assignNewOrigin(newOrigin.dimensionId, newOrigin.origin);
        return true;
    }
    private static OriginAggregate getDefaultOriginAggregate(World world){
        return new OriginAggregate(
                DimensionTypes.OVERWORLD.getValue(),
                world.getServer().getOverworld().getSpawnPos()
        );
    }

    public record OriginAggregate(Identifier dimensionId, BlockPos origin){

    }
}
