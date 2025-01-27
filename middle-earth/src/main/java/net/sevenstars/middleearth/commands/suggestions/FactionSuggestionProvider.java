package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

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
        List<Faction> candidates = FactionLookup.getAllJoinableFaction(context.getSource().getWorld());
        List<Identifier> identifiers = new ArrayList<>();
        for(Faction faction : candidates){
            if(onlyJoinable){
                if(faction.getFactionType() == FactionType.SUBFACTION)
                    identifiers.add(faction.getId());
                else if(faction.getFactionType() == FactionType.FACTION && faction.getSubFactions() == null || faction.getSubFactions().isEmpty())
                    identifiers.add(faction.getId());
            } else{
                identifiers.add(faction.getId());
            }
        }
        return SuggestionUtil.getCorrespondingIdentifiers(identifiers, builder);
    }
}
