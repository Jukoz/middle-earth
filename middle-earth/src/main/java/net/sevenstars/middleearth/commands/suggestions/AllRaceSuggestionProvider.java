package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllRaceSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        try {
            List<Identifier> candidates = null;
            candidates = getAllRaces(context);
            return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Identifier> getAllRaces(CommandContext<ServerCommandSource> context) throws FactionIdentifierException {
        List<Race> races = RaceLookup.getAllRaces(context.getSource().getWorld());
        List<Identifier> candidates = new ArrayList<>();

        for(Race race: races){
            candidates.add(race.getId());
        }

        return candidates;
    }
}