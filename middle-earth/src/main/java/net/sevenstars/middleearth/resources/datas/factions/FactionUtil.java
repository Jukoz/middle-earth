package net.sevenstars.middleearth.resources.datas.factions;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.CommandUtils;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.exceptions.IdenticalFactionException;
import net.sevenstars.middleearth.exceptions.NoFactionException;
import net.sevenstars.middleearth.exceptions.SpawnIdentifierException;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.utils.ModColors;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FactionUtil {


    public static boolean updateFaction(ServerPlayer player, @Nullable Faction faction, @Nullable ResourceLocation spawnId) throws IdenticalFactionException, SpawnIdentifierException, FactionIdentifierException, NoFactionException {
        if(!assertUpdateFactionValues(player, faction, spawnId))
            return false;

        Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.level());

        // [CLEAR] If the next faction is null
        if(faction == null){
            return clearFaction(player);
        }

        // [REPLACE] If previous faction is not null and next faction is not null
        if(previousFaction != null){
            sendOnLeaveCommand(player, previousFaction);
            // Send leaving message to affected player
            MutableComponent targetText = Component.translatable("event.%s.leave.faction.success".formatted(MiddleEarth.MOD_ID), previousFaction.getFullName());
            player.sendSystemMessage(targetText.withColor(ModColors.WARNING.color));
        }

        // [JOIN] Add new affiliation data
        if(spawnId == null)
            spawnId = faction.getSpawnData().getDefaultSpawn();
        PlayerDataService.setNewFactionInformation(player, player.level(), faction.getId(), spawnId);
        sendOnJoinCommand(player, faction);

        // Send join message to affected player
        MutableComponent targetText = Component.translatable("event.%s.join.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
        player.sendSystemMessage(targetText.withColor(ModColors.SUCCESS.color));

        sendOnFactionJoinMessage(player);
        return true;
    }

    private static boolean assertUpdateFactionValues(ServerPlayer player, Faction faction, ResourceLocation spawnId) throws IdenticalFactionException, SpawnIdentifierException {
        // Verify player
        if(player == null) return false;

        // Verify faction
        // Fetch player datas
        Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.level());

        if (faction == null) {
            return previousFaction != null;
        }

        ResourceLocation resolvedSpawnId = getSpawnId(faction, spawnId);
        if (resolvedSpawnId == null) {
            return false;
        }

        if(previousFaction != null && Objects.equals(previousFaction.getId(), faction.getId())) {
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(player, player.level());
            if(spawnData != null && Objects.equals(resolvedSpawnId, spawnData.getIdentifier()))
                throw new IdenticalFactionException();
        }

        return true;
    }

    private static ResourceLocation getSpawnId(Faction faction, ResourceLocation spawnId) throws SpawnIdentifierException {
        SpawnDataHandler spawnDataHandler = faction.getSpawnData();
        if(spawnDataHandler != null){
            if(spawnId == null)
                spawnId = spawnDataHandler.getDefaultSpawn();

            boolean spawnExistInFaction = spawnDataHandler.getAllSpawnIdentifiers().contains(spawnId);

            if(spawnId == null || !spawnExistInFaction){
                throw new SpawnIdentifierException();
            }
        }
        return spawnId;
    }

    private static void sendOnJoinCommand(ServerPlayer player, Faction faction) {
        if(player == null || faction == null) return;

        Optional<List<String>> joinCommand = faction.getJoinCommands();
        if(joinCommand.isEmpty()) return;

        List<String> commands = joinCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    private static void sendOnLeaveCommand(ServerPlayer player, Faction previousFaction) {
        if(player == null || previousFaction == null) return;

        Optional<List<String>> leaveCommand = previousFaction.getLeaveCommands();
        if(leaveCommand.isEmpty()) return;

        List<String> commands = leaveCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    public static void sendOnFactionJoinMessage(Player player) {
        Faction faction = PlayerDataService.getPlayerFaction(player, player.level());
        if(faction == null){
            MiddleEarth.LOGGER.logError("Couldn't find faction");
            return;
        }


        MutableComponent targetText = Component.translatable("event.%s.join.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
        ((ServerPlayer) player).connection.send(
            new ClientboundSetTitleTextPacket(Component.nullToEmpty(""))
        );
        ((ServerPlayer) player).connection.send(
                new ClientboundSetSubtitleTextPacket(targetText.withColor(ModColors.SUCCESS.color))
        );

    }

    public static boolean clearFaction(ServerPlayer player) throws FactionIdentifierException, NoFactionException {
        Faction faction = PlayerDataService.getPlayerFaction(player, player.level());
        if(faction == null)
            throw new NoFactionException();
        if(PlayerDataService.clearPlayerData(player)){
            sendOnLeaveCommand(player, faction);
            MutableComponent targetText = Component.translatable("event.%s.leave.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
            player.sendSystemMessage(targetText.withColor(ModColors.WARNING.color));
            return true;
        }
        return false;
    }

    /**
     * Simply used to explore spawn points.
     * @param target player to teleport
     * @param spawnId spawn identifier the player need to teleport to
     * @return if the process was a success or not
     */
    public static boolean forceTeleportToSpawnMiddleEarthId(ServerPlayer target, ResourceLocation spawnId){
        BlockPos spawnBlockPos = null;
        spawnBlockPos = getSpawnBlockPos(target.level(), spawnId);
        if(spawnBlockPos == null)
            return false;
        return ModDimensions.teleportPlayerToMe(
                target,
                new Vec3(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()),
                false,
                false
        );
    }

    public static BlockPos getSpawnBlockPos(Level world, ResourceLocation spawnId) {
        BlockPos spawnBlockPos = null;
        for(Faction faction: FactionLookup.getAllFactions(world)){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null)
                spawnBlockPos = spawnDataHandler.getSpawnBlockPos(spawnId);
            if(spawnBlockPos != null) {
                return spawnBlockPos;
            }

            if(faction.getSubFactions() != null){
                for(ResourceLocation subfactionId : faction.getSubFactions()){
                    try {
                        Faction subFaction = null;
                        subFaction = FactionLookup.getFactionById(world, subfactionId);
                        SpawnDataHandler subFacspawnDataHandler = subFaction.getSpawnData();
                        if(subFacspawnDataHandler != null)
                            spawnBlockPos = subFacspawnDataHandler.getSpawnBlockPos(spawnId);
                        if(spawnBlockPos != null) {
                            return spawnBlockPos;
                        }
                    } catch (FactionIdentifierException e) {
                        return null;
                    }
                }
            }
        }
        return  null;
    }
}
