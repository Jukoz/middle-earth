package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class SuggestionUtil {
    public static CompletableFuture<Suggestions> getCorrespondingIdentifiers(List<Identifier> candidates, SuggestionsBuilder builder){
        String string = builder.getRemaining().toLowerCase(Locale.ROOT);

        for (Identifier id : candidates){
            if(id.toString().contains(string)){
                builder.suggest(id.toString());
            }
        }

        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> getCorrespondingNames(List<String> candidates, SuggestionsBuilder builder){
        String string = builder.getRemaining().toLowerCase(Locale.ROOT);

        for (String name : candidates){
            if(name.contains(string)){
                builder.suggest(name);
            }
        }

        return builder.buildFuture();
    }
}
