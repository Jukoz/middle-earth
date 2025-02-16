package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTextureType;

import java.util.Optional;

public class MiddleEarthNpcTexturePatterns {
    public final static String PATH = "npc_texture_patterns";
    public static final RegistryKey<Registry<NpcTexturePattern>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON = of("common");

    public static void bootstrap(Registerable<NpcTexturePattern> registry) {
        register(registry, SKIN_COMMON, new NpcTexturePattern(Identifier.of(MiddleEarth.MOD_ID, "common"), NpcTextureType.SKIN));
    }

    public static Optional<RegistryEntry.Reference<NpcTexturePattern>> get(RegistryWrapper.WrapperLookup registries, Identifier id) {
        return registries.getOrThrow(KEY).streamEntries().filter((recipe) -> id == recipe.value().getIdentifier()).findFirst();
    }

    private static void register(Registerable<NpcTexturePattern> registry, RegistryKey<NpcTexturePattern> key, NpcTexturePattern material) {
        registry.register(key, material);
    }

    private static RegistryKey<NpcTexturePattern> of(String id) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Npc Texture Patterns for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, NpcTexturePattern.CODEC);
    }
}
