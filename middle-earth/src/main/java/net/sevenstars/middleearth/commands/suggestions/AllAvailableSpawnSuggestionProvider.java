package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.factions.FactionOld;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllAvailableSpawnSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        try {
            List<Identifier> candidates = null;
            candidates = getAllSpawns(context);
            return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Identifier> getAllSpawns(CommandContext<ServerCommandSource> context) throws FactionIdentifierException {

         /* [TODO]
        ServerPlayerEntity targettedPlayer = null; // Null for command blocks
        try {
            targettedPlayer = EntityArgumentType.getPlayer(context, "player");  // Targeted player when the argument is there
        } catch (Exception e){ // There is no player argument in the command
            if(context.getSource().isExecutedByPlayer()){
                targettedPlayer = context.getSource().getPlayer(); // Source player when no player argument
            }
        }

        FactionOld currentSelectedFaction = null; // Null by default
        try {
            Identifier factionId = context.getArgument("faction_id", Identifier.class);
            currentSelectedFaction = FactionService.getFactionById(context.getSource().getWorld(), factionId);

        } catch (Exception e){ // There is no player argument in the command
            if(targettedPlayer != null){
                currentSelectedFaction = PlayerDataService.getPlayerFaction(targettedPlayer, targettedPlayer.getWorld());
            }
        }
        List<Identifier> candidates = new ArrayList<>();

        if(currentSelectedFaction != null) {
            /* [TODO]
            SpawnDataHandler spawnDataHandler = currentSelectedFaction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<Identifier> factionSpawns = spawnDataHandler.getAllSpawnIdentifiers().stream().toList();
                candidates.addAll(factionSpawns);
                return candidates;
            }
        }

        // Return all faction spawns
        List<FactionOld> allFactions = FactionLookup.getAllJoinableFaction(context.getSource().getWorld());
        for(FactionOld faction : allFactions){
                        /* [TODO]
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<Identifier> factionSpawns = spawnDataHandler.getAllSpawnIdentifiers().stream().toList();
                candidates.addAll(factionSpawns);
            }
        }

          */
        return List.of();
    }

}