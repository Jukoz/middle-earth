package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllHoodsSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        try {
            List<String> candidates = getAllHoods();
            return SuggestionUtil.getCorrespondingNames(candidates, builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getAllHoods() {
        List<String> candidates = new ArrayList<>();
        for(ModHoods hood : ModHoods.values())
            candidates.add(hood.getName());
        return candidates;
    }
}
