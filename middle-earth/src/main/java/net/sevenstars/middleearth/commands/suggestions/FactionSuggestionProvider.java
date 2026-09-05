package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FactionSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    boolean onlyJoinable = true;
    public FactionSuggestionProvider(){
    }
    public FactionSuggestionProvider(boolean onlyJoinable){
        this.onlyJoinable = onlyJoinable;
    }
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        List<RegistryEntry<Faction>> candidates=  FactionService.getAllJoinableFactionEntries(context.getSource().getWorld());
        List<Identifier> identifiers = new ArrayList<>();
        for(RegistryEntry<Faction> faction : candidates){
            identifiers.add(faction.getKey().orElseThrow().getValue());
        }
        return SuggestionUtil.getCorrespondingIdentifiers(identifiers, builder);
    }
}
