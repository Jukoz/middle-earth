package net.sevenstars.middleearth.registries.content.structuremanagerdatas;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

public class StructureManagerDataRegistry {
    private static final RegistryKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA_KEY = DynamicRegistriesME.STRUCTURE_MANAGER_DATA;

    public final static RegistryKey<StructureManagerData> GONDOR_GENERIC_NESTS = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("gondor_generic_nests"));
    public final static RegistryKey<StructureManagerData> EREBOR_GENERIC_NESTS = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("erebor_generic_nests"));
    public final static RegistryKey<StructureManagerData> DALE_KEEP_NESTS = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("dale_keep_nests"));
    public final static RegistryKey<StructureManagerData> WOODLAND_REALM_HAMLET_NESTS = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("woodland_realm_hamlet_nests"));
    public final static RegistryKey<StructureManagerData> WOODLAND_REALM_HALL_NESTS = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("woodland_realm_hall_nests"));

    public static void bootstrap(Registerable<StructureManagerData> context) {
        RegistryEntryLookup<StructureManagerData> registryEntryLookup = context.getRegistryLookup(STRUCTURE_MANAGER_DATA_KEY);

        register(context, registryEntryLookup, GONDOR_GENERIC_NESTS, StructureManagerDataPools.GONDOR_GENERIC_NESTS);
        register(context, registryEntryLookup, EREBOR_GENERIC_NESTS, StructureManagerDataPools.EREBOR_GENERIC_NESTS);
        register(context, registryEntryLookup, DALE_KEEP_NESTS, StructureManagerDataPools.DALE_KEEP_NESTS);
        register(context, registryEntryLookup, WOODLAND_REALM_HAMLET_NESTS, StructureManagerDataPools.WOODLAND_REALM_HAMLET_NESTS);
        register(context, registryEntryLookup, WOODLAND_REALM_HALL_NESTS, StructureManagerDataPools.WOODLAND_REALM_HALL_NESTS);
    }

    private static void register(Registerable<StructureManagerData> context, RegistryEntryLookup<StructureManagerData> registryEntryLookup, RegistryKey<StructureManagerData> registryKey, StructureManagerData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        TranslationEntries.structureManagerEntries.add(element);
    }
}
