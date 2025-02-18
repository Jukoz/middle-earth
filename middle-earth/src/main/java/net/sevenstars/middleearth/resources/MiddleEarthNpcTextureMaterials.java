package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTextureType;

import java.util.Optional;

public class MiddleEarthNpcTextureMaterials {
    public final static String PATH = "npc_texture_materials";

    public static final RegistryKey<Registry<NpcTextureMaterial>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public final static RegistryKey<NpcTextureMaterial> SKIN_PALE = of("pale");
    public final static RegistryKey<NpcTextureMaterial> SKIN_TAN = of("tan");

    public static void bootstrap(Registerable<NpcTextureMaterial> registry) {
        RegistryEntryLookup<NpcTextureMaterial> entryLookup = registry.getRegistryLookup(KEY);

        register(registry, entryLookup,  "tan", NpcTextureType.SKIN);
        register(registry, entryLookup,"pale", NpcTextureType.SKIN);
    }

    public static Optional<RegistryEntry.Reference<NpcTextureMaterial>> get(RegistryWrapper.WrapperLookup registries, Identifier id) {
        return registries.getOrThrow(KEY).streamEntries().filter((recipe) -> id == recipe.value().getIdentifier()).findFirst();
    }

    private static void register(Registerable<NpcTextureMaterial> registry, RegistryEntryLookup<NpcTextureMaterial> entryLookup, String id, NpcTextureType type) {
        register(registry, entryLookup, create(id, type));
    }

    private static NpcTextureMaterial create(String id, NpcTextureType type) {
        return new NpcTextureMaterial(Identifier.of(MiddleEarth.MOD_ID, id), type);
    }
    private static RegistryKey<NpcTextureMaterial> of(String id) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Npc Texture Materials for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, NpcTextureMaterial.CODEC);

    }

    private static void register(Registerable<NpcTextureMaterial> registerable, RegistryEntryLookup<NpcTextureMaterial> entryLookup, NpcTextureMaterial content) {
        RegistryKey<NpcTextureMaterial> registryKey = of(content.getIdentifier().getPath());
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTextureMaterial> key = RegistryKey.of(KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcTextureMaterial>> optionalFaction = entryLookup.getOptional(registryKey);
        optionalFaction.ifPresent(reference -> registerable.register(key, content));
    }
}
