package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.features.generation.map.MapBiomeLocator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MapBiomeSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        List<Identifier> identifiers = MapBiomeLocator.getFindableBiomes().stream().map(RegistryKey::getValue).sorted().toList();
        return SuggestionUtil.getCorrespondingIdentifiers(identifiers, builder);
    }
}
