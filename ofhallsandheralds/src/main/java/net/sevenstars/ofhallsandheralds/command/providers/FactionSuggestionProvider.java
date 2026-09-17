package net.sevenstars.ofhallsandheralds.command.providers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.sevenstars.api.command.SuggestionUtil;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.persistentdatas.PlayerPersistentDataManagerHH;
import net.sevenstars.ofhallsandheralds.persistentdatas.reputation.reputationPersistentData;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FactionSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    boolean onlyKnown;
    boolean onlyJoinable;

    public FactionSuggestionProvider(boolean onlyKnown, boolean onlyJoinable) {
        this.onlyKnown = onlyKnown;
        this.onlyJoinable = onlyJoinable;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        List<RegistryEntry<Faction>> candidates =  FactionService.getAllFactionEntries(context.getSource().getWorld(), onlyJoinable);

        List<Identifier> identifiersToReturn = new ArrayList<>();

        for(RegistryEntry<Faction> faction : candidates){
            if(factionIsValid(context, faction)){
                identifiersToReturn.add(faction.getKey().orElseThrow().getValue());
            }
        }
        return SuggestionUtil.getCorrespondingIdentifiers(identifiersToReturn, builder);
    }

    private boolean factionIsValid(CommandContext<ServerCommandSource> context, RegistryEntry<Faction> faction) {
        if(this.onlyKnown && context.getSource().isExecutedByPlayer()){
            reputationPersistentData reputation = PlayerPersistentDataManagerHH.getReputation().get(context.getSource().getPlayer().getUuid());
            return reputation.isFactionKnown(faction);
        }
        return true;
    }
}
