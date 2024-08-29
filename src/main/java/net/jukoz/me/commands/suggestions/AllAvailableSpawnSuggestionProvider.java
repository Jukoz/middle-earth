package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.FactionUtil;
import net.jukoz.me.resources.datas.faction.utils.SpawnDataHandler;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllAvailableSpawnSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        List<Identifier> candidates = getAllSpawns(context);
        return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
    }

    private static List<Identifier> getAllSpawns(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity targettedPlayer = null; // Null for command blocks
        try {
            targettedPlayer = EntityArgumentType.getPlayer(context, "player");  // Targeted player when the argument is there
        } catch (Exception e){ // There is no player argument in the command
            if(context.getSource().isExecutedByPlayer()){
                targettedPlayer = context.getSource().getPlayer(); // Source player when no player argument
            }
        }

        LoggerUtil.logDebugMsg("current user: " + targettedPlayer);


        Faction currentSelectedFaction = null; // Null by default
        try {
            Identifier factionId = context.getArgument("faction_id", Identifier.class);
            currentSelectedFaction = FactionUtil.getFactionById(factionId);

        } catch (Exception e){ // There is no player argument in the command
            if(targettedPlayer != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(targettedPlayer);
                if(playerData.hasAffilition()){
                    currentSelectedFaction = FactionUtil.getFactionById(playerData.getAffiliationData().faction);
                }
            }
        }
        List<Identifier> candidates = new ArrayList<>();

        if(currentSelectedFaction != null) {
            SpawnDataHandler spawnDataHandler = currentSelectedFaction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<Identifier> factionSpawns = spawnDataHandler.getSpawnList().keySet().stream().toList();
                candidates.addAll(factionSpawns);
                return candidates;
            }
        }

        // Return all faction spawns
        List<Identifier> allFactionIds = ModFactionRegistry.getAllJoinableFactionId();
        for(Identifier id : allFactionIds){
            Faction faction = FactionUtil.getFactionById(id);
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<Identifier> factionSpawns = spawnDataHandler.getSpawnList().keySet().stream().toList();
                candidates.addAll(factionSpawns);
            }
        }
        return candidates;
    }
}