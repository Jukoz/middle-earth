package net.sevenstars.middleearth.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllBackAttachmentsSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        try {
            List<String> candidates = getAllBackAttachments();
            return SuggestionUtil.getCorrespondingNames(candidates, builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getAllBackAttachments() {
        List<String> candidates = new ArrayList<>();
        for(BackAttachmentsME backAttachment : BackAttachmentsME.values())
            candidates.add(backAttachment.getName());
        return candidates;
    }
}
