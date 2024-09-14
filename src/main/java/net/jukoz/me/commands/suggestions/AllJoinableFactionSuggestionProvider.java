package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jukoz.me.resources.ModFactionRegistry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllJoinableFactionSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        List<Identifier> candidates = ModFactionRegistry.getAllJoinableFactionId();
        return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
    }
}
