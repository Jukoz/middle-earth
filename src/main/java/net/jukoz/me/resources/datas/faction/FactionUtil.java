package net.jukoz.me.resources.datas.faction;

import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FactionUtil {
    public static final String COMMAND_PLAYER_REPLACEMENT = "<p>";

    public static Faction getFactionById(Identifier id){
        return ModFactionRegistry.findFactionById(id);
    }

    public static boolean updateFaction(ServerPlayerEntity player, Identifier factionIdentifier, @Nullable Identifier spawnId) {
        Faction foundFaction = getFactionById(factionIdentifier);
        if(foundFaction == null) return false;
        return updateFaction(player, foundFaction, spawnId);
    }

    public static boolean updateFaction(ServerPlayerEntity player, @Nullable Faction faction, @Nullable Identifier spawnId) {
        if(player == null) return false;

        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
        AffiliationData previousAffiliationData = playerState.getAffiliationData();
        Faction previousFaction = (previousAffiliationData != null) ? getFactionById(previousAffiliationData.faction) : null;

        // [CLEAR] If the next faction is null
        if(faction == null){
            playerState.setAffiliationData(null);
            if(previousFaction != null)
                sendOnLeaveCommand(player, previousFaction);
            return true;
        }
        // [REPLACE] If previous faction is not null and next faction is not null
        if(previousAffiliationData != null && previousFaction != null){
            sendOnLeaveCommand(player, previousFaction);
        }
        // [JOIN] Add new affiliation data
        AffiliationData newAffiliationData = new AffiliationData(faction.getAlignmentString(), faction.getId().getPath(), faction.getSpawnData().getDefaultSpawn().getPath());
        playerState.setAffiliationData(newAffiliationData);
        sendOnJoinCommand(player, faction);
        return true;
    }

    private static void sendOnJoinCommand(ServerPlayerEntity player, Faction faction) {
        if(player == null || faction == null) return;

        CommandManager commandManager =  player.server.getCommandManager();
        ServerCommandSource commandSource = player.server.getCommandSource();

        Optional<List<String>> joinCommand = faction.getJoinCommands();
        if(joinCommand.isEmpty()) return;

        List<String> command = joinCommand.orElse(null);

        if(command.isEmpty()) return;

        for(String com : command) {
            com = com.replace(FactionUtil.COMMAND_PLAYER_REPLACEMENT, player.getName().getString());
            commandManager.executeWithPrefix(commandSource, com);
        }
    }

    private static void sendOnLeaveCommand(ServerPlayerEntity player, Faction previousFaction) {
        if(player == null || previousFaction == null) return;

        CommandManager commandManager =  player.server.getCommandManager();
        ServerCommandSource commandSource = player.server.getCommandSource();

        Optional<List<String>> leaveCommand = previousFaction.getLeaveCommands();
        if(leaveCommand.isEmpty()) return;

        List<String> command = leaveCommand.orElse(null);

        if(command.isEmpty()) return;
        for(String com : command) {
            com = com.replace(FactionUtil.COMMAND_PLAYER_REPLACEMENT, player.getName().getString());
            commandManager.executeWithPrefix(commandSource, com);
        }
    }
}
