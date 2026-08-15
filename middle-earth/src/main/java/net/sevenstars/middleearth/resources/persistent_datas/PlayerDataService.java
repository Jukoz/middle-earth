package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.map.MiddleEarthMapUtils;

public class PlayerDataService {
    private static PlayerData getPlayerData(Player player){
        return StateSaverAndLoader.getPlayerState(player);
    }

    private static PlayerData getPlayerDataReadOnly(Player player) {
        return StateSaverAndLoader.getPlayerStateReadOnly(player);
    }

    public static boolean clearPlayerData(Player player){
        PlayerData data = getPlayerData(player);
        if (data == null) {
            return false;
        }
        data.assignNewRace(null);
        data.assignNewFactionInformation(null, null);
        data.assignNewOrigin(null, null);
        data.assignMiddleEarthReturnPos(null);
        return true;
    }
    public static boolean playerPassedOnboarding(Player player){
        PlayerData playerData = getPlayerDataReadOnly(player);
        if(playerData == null) return false;
        return !(playerData.getFaction() == null || playerData.getSpawn() == null);
    }
    public static Faction getPlayerFaction(Player player, Level world){
        PlayerData playerData = getPlayerDataReadOnly(player);
        if(playerData == null) return null;
        ResourceLocation factionId = playerData.getFaction();
        if(factionId == null) return null;
        try{
            return FactionLookup.getFactionById(world, factionId);
        } catch (FactionIdentifierException exception){
            return null;
        }
    }
    public static boolean setNewFactionInformation(Player player, Level world, ResourceLocation factionId){
        try{
            Faction faction = FactionLookup.getFactionById(world, factionId);
            setNewFactionInformation(player, world, factionId, faction.getSpawnData().getDefaultSpawn());
            return true;
        } catch (FactionIdentifierException exception){
            return false;
        }
    }
    public static boolean setNewFactionInformation(Player player, Level world, ResourceLocation factionId, ResourceLocation spawnId){
        PlayerData playerData = getPlayerData(player);
        if (playerData == null) {
            return false;
        }
        playerData.assignNewFactionInformation(factionId, spawnId);
        return true;
    }
    public static DispositionType getPlayerDisposition(Player player, Level world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return DispositionType.NEUTRAL;
        return faction.getDisposition();
    }
    public static Race getPlayerRace(Player player, Level world){
        PlayerData playerData = getPlayerDataReadOnly(player);
        if(playerData == null) return null;
        ResourceLocation raceId = playerData.getRace();
        if(raceId == null) return null;
        try{
            return RaceLookup.getRace(world, raceId);
        } catch (Exception exception){
            return null;
        }
    }
    public static boolean setRace(Player player, Level world, ResourceLocation raceId){
        PlayerData playerData = getPlayerData(player);
        if (playerData == null) {
            return false;
        }
        if (raceId == null) {
            playerData.assignNewRace(null);
            return true;
        }
        Race newRace = RaceLookup.getRace(world, raceId);
        if(newRace == null) return false;
        playerData.assignNewRace(raceId);
        newRace.applyPlayerAttributes(player);
        return true;
    }
    public static SpawnData getPlayerSpawnData(Player player, Level world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return null;
        PlayerData playerData = getPlayerDataReadOnly(player);
        if(playerData == null) return null;
        ResourceLocation spawnId = playerData.getSpawn();
        if(spawnId == null) return null;
        return faction.getSpawnData().findSpawn(spawnId);
    }
    public static boolean setSpawn(ServerPlayer player, Level world, ResourceLocation spawnId) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        SpawnData configuredSpawn = faction.getSpawnData().findSpawn(spawnId);
        if (configuredSpawn == null) {
            return false;
        }

        BlockPos safeSpawn = null;
        if(ModDimensions.isInMiddleEarth(player.level())){
            var middleEarth = player.getServer().getLevel(ModDimensions.ME_WORLD_KEY);
            if (middleEarth == null) {
                return false;
            }
            var safeTarget = ModDimensions.findSafeMiddleEarthTeleportTarget(
                    middleEarth,
                    configuredSpawn.getBlockPos().getCenter(),
                    player
            );
            if (safeTarget.isEmpty()) {
                return false;
            }
            safeSpawn = BlockPos.containing(safeTarget.get());
        }

        PlayerData data = getPlayerData(player);
        if (data == null) {
            return false;
        }
        data.assignNewFactionInformation(faction.getId(), spawnId);
        if (safeSpawn != null) {
            player.setRespawnPosition(
                    ModDimensions.ME_WORLD_KEY,
                    safeSpawn,
                    0,
                    true,
                    true
            );
        }
        return true;
    }
    public static boolean resetSpawn(ServerPlayer player, Level world) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        SpawnDataHandler spawnDataHandler= faction.getSpawnData();
        if(spawnDataHandler == null) return false;
        return setSpawn(player, world, spawnDataHandler.getDefaultSpawn());
    }
    public static OriginAggregate getOriginAggregateOrDefault(Player player, Level world){
        PlayerData playerData = getPlayerDataReadOnly(player);
        if(playerData == null) return getDefaultOriginAggregate(world);
        BlockPos originPos = playerData.getOriginPos();
        ResourceLocation dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return getDefaultOriginAggregate(world);
        }
        if(dimensionId == null){
            dimensionId = BuiltinDimensionTypes.OVERWORLD.location();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static OriginAggregate getOriginAggregate(Player player, Level world){
        PlayerData playerData = getPlayerDataReadOnly(player);
        if(playerData == null) return null;
        BlockPos originPos = playerData.getOriginPos();
        ResourceLocation dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return null;
        }
        if(dimensionId == null){
            dimensionId = BuiltinDimensionTypes.OVERWORLD.location();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static boolean setOrigin(ServerPlayer player, Level world, ResourceLocation dimensionId, BlockPos originPos) {
        if (player == null || world == null || dimensionId == null || originPos == null) {
            return false;
        }
        var registry = world.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE);
        if(registry.get(dimensionId) != null){
            PlayerData data = getPlayerData(player);
            if (data == null) {
                return false;
            }
            data.assignNewOrigin(dimensionId, originPos);
            return true;
        }
        return false;
    }
    public static boolean resetOrigin(ServerPlayer player, Level world) {
        if (player == null || world == null) {
            return false;
        }
        OriginAggregate newOrigin = getDefaultOriginAggregate(world);
        PlayerData data = getPlayerData(player);
        if (data == null) {
            return false;
        }
        data.assignNewOrigin(newOrigin.dimensionId, newOrigin.origin);
        return true;
    }

    public static BlockPos getMiddleEarthReturnPos(Player player) {
        PlayerData data = getPlayerDataReadOnly(player);
        return data == null ? null : data.getMiddleEarthReturnPos();
    }

    public static boolean setMiddleEarthReturnPos(ServerPlayer player, BlockPos returnPos) {
        if (player == null || !isValidMiddleEarthReturnPos(returnPos)) {
            return false;
        }
        PlayerData data = getPlayerData(player);
        if (data == null) {
            return false;
        }
        data.assignMiddleEarthReturnPos(returnPos.immutable());
        return true;
    }

    public static boolean isValidMiddleEarthReturnPos(BlockPos returnPos) {
        return returnPos != null
                && Level.isInSpawnableBounds(returnPos)
                && MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(
                        returnPos.getX(),
                        returnPos.getZ()
                );
    }

    private static OriginAggregate getDefaultOriginAggregate(Level world){
        return new OriginAggregate(
                BuiltinDimensionTypes.OVERWORLD.location(),
                world.getServer().overworld().getSharedSpawnPos()
        );
    }

    public record OriginAggregate(ResourceLocation dimensionId, BlockPos origin){

    }
}
