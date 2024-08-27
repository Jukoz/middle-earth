package net.jukoz.me.commands;

import net.jukoz.me.resources.ModFactionRegistry;
import net.minecraft.util.Identifier;

import java.util.Iterator;

public class ModSuggestionProviders {
    public static Iterator<Identifier> getAllJoinableFactionSuggestion() {
        return ModFactionRegistry.getAllJoinableFactionId().iterator();
    }
}
