package net.jukoz.me.resources.datas.faction;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.jukoz.me.commands.CommandColors;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.exceptions.IdenticalFactionException;
import net.jukoz.me.exceptions.SpawnIdentifierException;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.faction.utils.SpawnDataHandler;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FactionUtil {
    public static final String COMMAND_PLAYER_REPLACEMENT = "<p>";

    public static Faction getFactionById(Identifier id) {
        try{
            return ModFactionRegistry.findFactionById(id);
        } catch (FactionIdentifierException e){
            return null;
        }
    }

    public static boolean updateFaction(ServerPlayerEntity player, @Nullable Faction faction, @Nullable Identifier spawnId) throws IdenticalFactionException, SpawnIdentifierException {
        if(!assertUpdateFactionValues(player, faction, spawnId))
            return false;

        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
        AffiliationData previousAffiliationData = playerState.getAffiliationData();
        Faction previousFaction = (previousAffiliationData != null) ? getFactionById(previousAffiliationData.faction) : null;

        // [CLEAR] If the next faction is null
        if(faction == null){
            playerState.setAffiliationData(null);
            if(previousFaction != null){
                sendOnLeaveCommand(player, previousFaction);
                // Send leaving message to affected player
                MutableText targetText = Text.translatable("event.me.faction.leave.source", previousFaction.getFullName());
                player.sendMessage(targetText.withColor(CommandColors.WARNING.color));
            }
            return true;
        }

        // [REPLACE] If previous faction is not null and next faction is not null
        if(previousAffiliationData != null && previousFaction != null){
            sendOnLeaveCommand(player, previousFaction);
            // Send leaving message to affected player
            MutableText targetText = Text.translatable("event.me.faction.leave.source", previousFaction.getFullName());
            player.sendMessage(targetText.withColor(CommandColors.WARNING.color));
        }

        // [JOIN] Add new affiliation data
        Identifier foundSpawnId = getSpawnId(faction, spawnId);
        AffiliationData newAffiliationData = new AffiliationData(faction.getAlignmentString(), faction.getId().getPath(), foundSpawnId.getPath());
        playerState.setAffiliationData(newAffiliationData);
        sendOnJoinCommand(player, faction);

        // Send join message to affected player
        MutableText targetText = Text.translatable("event.me.faction.join.source", faction.getFullName());
        player.sendMessage(targetText.withColor(CommandColors.SUCCESS.color));
        sendOnFactionJoinMessage(player);
        return true;
    }

    private static boolean assertUpdateFactionValues(ServerPlayerEntity player, Faction faction, Identifier spawnId) throws IdenticalFactionException, SpawnIdentifierException {

        // Verify player
        if(player == null) return false;

        // Verify faction
        // Fetch player datas
        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
        AffiliationData previousAffiliationData = playerState.getAffiliationData();
        Faction previousFaction = (previousAffiliationData != null) ? getFactionById(previousAffiliationData.faction) : null;

        // If there is no faction update, return true
        if(previousFaction == faction) {
            throw new IdenticalFactionException();
        };

        // Verify spawnId
        if(getSpawnId(faction, spawnId) == null)
            return false;

        return true;
    }

    private static Identifier getSpawnId(Faction faction, Identifier spawnId) throws SpawnIdentifierException {
        SpawnDataHandler spawnDataHandler = faction.getSpawnData();
        if(spawnDataHandler != null){
            if(spawnId == null)
                spawnId = spawnDataHandler.getDefaultSpawn();

            boolean spawnExistInFaction = spawnDataHandler.getSpawnList().containsKey(spawnId);

            if(spawnId == null || !spawnExistInFaction){
                throw new SpawnIdentifierException();
            }
        }
        return spawnId;
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

    public static void sendOnFactionJoinMessage(PlayerEntity player) {
        PlayerData data = StateSaverAndLoader.getPlayerState(player);

        if(data.hasAffilition()){
            Identifier factionId = data.getAffiliationData().faction;
            Faction foundFaction = getFactionById(factionId);
            if(foundFaction == null) return;

            MutableText targetText = Text.translatable("event.me.faction.join.source", foundFaction.getFullName());
            ((ServerPlayerEntity) player).networkHandler.sendPacket(
                new TitleS2CPacket(Text.of(""))
            );
            ((ServerPlayerEntity) player).networkHandler.sendPacket(
                    new SubtitleS2CPacket(targetText.withColor(CommandColors.SUCCESS.color))
            );
        }
    }
}
