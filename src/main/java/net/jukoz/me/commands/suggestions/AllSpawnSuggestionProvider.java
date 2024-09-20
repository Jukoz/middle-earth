package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllSpawnSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        List<Identifier> candidates = getAllSpawns(context);
        return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
    }

    private static List<Identifier> getAllSpawns(CommandContext<ServerCommandSource> context) {
        List<Faction> factions = FactionLookup.getAllFactions();
        List<Identifier> candidates = new ArrayList<>();

        for(Faction faction: factions){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null){
                candidates.addAll(spawnDataHandler.getSpawnList().keySet());
            }
            if(faction.getSubFactions() != null){
                for(Faction subfaction : faction.getSubFactions().values()){
                    SpawnDataHandler subFacspawnDataHandler = subfaction.getSpawnData();
                    candidates.addAll(subFacspawnDataHandler.getSpawnList().keySet());
                }
            }
        }

        return candidates;
    }
}