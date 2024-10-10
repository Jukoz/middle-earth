package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.item.utils.ModCapes;
import net.jukoz.me.item.utils.ModHoods;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllHoodsSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        try {
            List<ModHoods> candidates = null;
            candidates = getAllHoods(context);
            candidates.forEach(cape -> {
                builder.suggest(cape.getName());
            });
            return builder.buildFuture();
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ModHoods> getAllHoods(CommandContext<ServerCommandSource> context) throws FactionIdentifierException {
        return List.of(ModHoods.values());
    }
}
