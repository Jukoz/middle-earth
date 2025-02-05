package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllCapesSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        try {
            List<String> candidates = getAllCapes();
            return SuggestionUtil.getCorrespondingNames(candidates, builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getAllCapes() {
        List<String> candidates = new ArrayList<>();
        for(ModCapes cape : ModCapes.values())
            candidates.add(cape.getName());
        return candidates;
    }
}
