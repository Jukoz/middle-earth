package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllAvailableSpawnSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        try {
            List<ResourceLocation> candidates = null;
            candidates = getAllSpawns(context);
            return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ResourceLocation> getAllSpawns(CommandContext<CommandSourceStack> context) throws FactionIdentifierException {
        ServerPlayer targettedPlayer = null; // Null for command blocks
        try {
            targettedPlayer = EntityArgument.getPlayer(context, "player");  // Targeted player when the argument is there
        } catch (Exception e){ // There is no player argument in the command
            if(context.getSource().isPlayer()){
                targettedPlayer = context.getSource().getPlayer(); // Source player when no player argument
            }
        }

        Faction currentSelectedFaction = null; // Null by default
        try {
            ResourceLocation factionId = context.getArgument("faction_id", ResourceLocation.class);
            currentSelectedFaction = FactionLookup.getFactionById(context.getSource().getLevel(), factionId);

        } catch (Exception e){ // There is no player argument in the command
            if(targettedPlayer != null){
                currentSelectedFaction = PlayerDataService.getPlayerFaction(targettedPlayer, targettedPlayer.level());
            }
        }
        List<ResourceLocation> candidates = new ArrayList<>();

        if(currentSelectedFaction != null) {
            SpawnDataHandler spawnDataHandler = currentSelectedFaction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<ResourceLocation> factionSpawns = spawnDataHandler.getAllSpawnIdentifiers().stream().toList();
                candidates.addAll(factionSpawns);
                return candidates;
            }
        }

        // Return all faction spawns
        List<Faction> allFactions = FactionLookup.getAllJoinableFaction(context.getSource().getLevel());
        for(Faction faction : allFactions){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<ResourceLocation> factionSpawns = spawnDataHandler.getAllSpawnIdentifiers().stream().toList();
                candidates.addAll(factionSpawns);
            }
        }
        return candidates;
    }
}