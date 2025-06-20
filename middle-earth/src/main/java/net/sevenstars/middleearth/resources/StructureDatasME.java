package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.resources.datas.structure_datas.StructureData;

import java.util.Optional;

public class StructureDatasME {
    public final static String PATH = "structure_datas";
    public static final RegistryKey<Registry<StructureData>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Structure Datas for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, StructureData.CODEC);
    }

    public final static StructureData TEMPLATE;

    public static void bootstrap(Registerable<StructureData> context) {
        RegistryEntryLookup<StructureData> structureDataRegistryEntryLookup = context.getRegistryLookup(KEY);
        // [TEMPLATE]
        register(context, structureDataRegistryEntryLookup, TEMPLATE);
    }

    private static StructureData register(Registerable<StructureData> context, RegistryEntryLookup<StructureData> registryEntryLookup, StructureData structureData) {
        RegistryKey<StructureData> registryKey = RegistryKey.of(KEY, structureData.getId());
        String name = registryKey.getValue().getPath();
        RegistryKey<StructureData> structureDataKey = RegistryKey.of(KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<StructureData>> optionalStructureDataReference = registryEntryLookup.getOptional(registryKey);
        optionalStructureDataReference.ifPresent(biomeReference -> context.register(structureDataKey, structureData));

        TranslationEntries.factionEntries.add(structureData.getId().getPath());
        return structureData;
    }

    static {
        TEMPLATE = new StructureData(Identifier.of(MiddleEarth.MOD_ID, "template"), FactionsME.GONDOR.getId());
    }
}
