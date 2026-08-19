package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllSpawnSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
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
        List<Faction> factions = FactionLookup.getAllFactions(context.getSource().getLevel());
        List<ResourceLocation> candidates = new ArrayList<>();

        for(Faction faction: factions){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null){
                List<ResourceLocation> spawnIds = spawnDataHandler.getAllSpawnIdentifiers();
                if(spawnIds != null)
                    candidates.addAll(spawnIds);
            }
            if(faction.getSubFactions() != null){
                for(ResourceLocation subfactionId : faction.getSubFactions()){
                    Faction subFaction = FactionLookup.getFactionById(context.getSource().getLevel(), subfactionId);
                    SpawnDataHandler subFacspawnDataHandler = subFaction.getSpawnData();

                    List<ResourceLocation> spawnIds = subFacspawnDataHandler.getAllSpawnIdentifiers();
                    if(spawnIds != null)
                        candidates.addAll(spawnIds);
                }
            }
        }

        return candidates;
    }
}