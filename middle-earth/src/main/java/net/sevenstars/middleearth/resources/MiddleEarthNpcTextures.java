package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTexture;

import java.util.Optional;

public class MiddleEarthNpcTextures {
    public final static String PATH = "npc_textures";
    public static final RegistryKey<Registry<NpcTexture>> NPC_TEXTURE_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public final static NpcTexture PALE_SKIN;


    public static void bootstrap(Registerable<NpcTexture> context) {
        RegistryEntryLookup<NpcTexture> entryLookup = context.getRegistryLookup(NPC_TEXTURE_KEY);
        // Registering all races
        register(context, entryLookup, PALE_SKIN);
    }

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Npc Textures for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(NPC_TEXTURE_KEY, NpcTexture.CODEC);
    }

    private static NpcTexture register(Registerable<NpcTexture> context, RegistryEntryLookup<NpcTexture> entryLookup, NpcTexture npcTexture) {
        RegistryKey<NpcTexture> registryKey = of(npcTexture.assetId().getPath());
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTexture> newNpcTexture = RegistryKey.of(NPC_TEXTURE_KEY, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcTexture>> optionalRace = entryLookup.getOptional(registryKey);
        optionalRace.ifPresent(raceReference -> context.register(newNpcTexture, npcTexture));

        return npcTexture;
    }
    private static RegistryKey<NpcTexture> of(String name) {
        return RegistryKey.of(NPC_TEXTURE_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        PALE_SKIN = new NpcTexture(Identifier.of(MiddleEarth.MOD_ID, "skin_pale"), "skin_pale");
    }
}
