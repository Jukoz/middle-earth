package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FactionSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
    boolean onlyJoinable = true;
    public FactionSuggestionProvider(){

    }
    public FactionSuggestionProvider(boolean onlyJoinable){
        this.onlyJoinable = onlyJoinable;
    }
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        List<Faction> candidates = FactionLookup.getAllJoinableFaction(context.getSource().getLevel());
        List<ResourceLocation> identifiers = new ArrayList<>();
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
