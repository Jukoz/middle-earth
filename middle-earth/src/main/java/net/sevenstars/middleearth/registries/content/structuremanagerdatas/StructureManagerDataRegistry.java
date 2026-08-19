package net.sevenstars.middleearth.registries.content.structuremanagerdatas;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

public class StructureManagerDataRegistry {
    private static final ResourceKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA_KEY = DynamicRegistriesME.STRUCTURE_MANAGER_DATA;

    public final static ResourceKey<StructureManagerData> BRIGAND_DUNGEON_POOL      = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("brigand_dungeon_pool"));
    public final static ResourceKey<StructureManagerData> DALE_VILLAGE_POOL         = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("dale_village_pool"));
    public final static ResourceKey<StructureManagerData> DALE_KEEP_POOL            = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("dale_keep_pool"));
    public final static ResourceKey<StructureManagerData> EREBOR_GENERIC_POOL       = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("erebor_generic_pool"));
    public final static ResourceKey<StructureManagerData> GONDOR_VILLAGE_POOL       = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("gondor_village_pool"));
    public final static ResourceKey<StructureManagerData> GONDOR_LORD_POOL          = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("gondor_lord_pool"));
    public final static ResourceKey<StructureManagerData> GUNDABAD_CAMP_POOL        = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("gundabad_camp_pool"));
    public final static ResourceKey<StructureManagerData> ISENGARD_CAMP_POOL        = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("isengard_camp_pool"));
    public final static ResourceKey<StructureManagerData> LOTHLORIEN_HAMLET_POOL    = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("lothlorien_hamlet_pool"));
    public final static ResourceKey<StructureManagerData> MORDOR_CAMP_POOL          = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("mordor_camp_pool"));
    public final static ResourceKey<StructureManagerData> MORIA_HALL_POOL           = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("moria_hall_pool"));
    public final static ResourceKey<StructureManagerData> ROHAN_VILLAGE_POOL        = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("rohan_village_pool"));
    public final static ResourceKey<StructureManagerData> ROHAN_MILITARY_POOL       = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("rohan_military_pool"));
    public final static ResourceKey<StructureManagerData> SHIRE_VILLAGE_POOL        = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("shire_village_pool"));
    public final static ResourceKey<StructureManagerData> WOODLAND_REALM_HAMLET_POOL= DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("woodland_realm_hamlet_pool"));
    public final static ResourceKey<StructureManagerData> WOODLAND_REALM_HALL_POOL  = DynamicRegistriesME.of(STRUCTURE_MANAGER_DATA_KEY, MiddleEarth.of("woodland_realm_hall_pool"));

    public static void bootstrap(BootstrapContext<StructureManagerData> context) {
        HolderGetter<StructureManagerData> registryEntryLookup = context.lookup(STRUCTURE_MANAGER_DATA_KEY);
        
        register(context, registryEntryLookup, BRIGAND_DUNGEON_POOL     , StructureManagerDataPools.BRIGAND_DUNGEON_NESTS);
        register(context, registryEntryLookup, DALE_VILLAGE_POOL        , StructureManagerDataPools.DALE_VILLAGE_NESTS);
        register(context, registryEntryLookup, DALE_KEEP_POOL           , StructureManagerDataPools.DALE_KEEP_NESTS);
        register(context, registryEntryLookup, EREBOR_GENERIC_POOL      , StructureManagerDataPools.EREBOR_GENERIC_NESTS);
        register(context, registryEntryLookup, GONDOR_VILLAGE_POOL      , StructureManagerDataPools.GONDOR_VILLAGE_NESTS);
        register(context, registryEntryLookup, GONDOR_LORD_POOL         , StructureManagerDataPools.GONDOR_LORD_NESTS);
        register(context, registryEntryLookup, GUNDABAD_CAMP_POOL       , StructureManagerDataPools.GUNDABAD_CAMP_NESTS);
        register(context, registryEntryLookup, ISENGARD_CAMP_POOL       , StructureManagerDataPools.ISENGARD_CAMP_NESTS);
        register(context, registryEntryLookup, LOTHLORIEN_HAMLET_POOL   , StructureManagerDataPools.LOTHLORIEN_HAMLET_NESTS);
        register(context, registryEntryLookup, MORDOR_CAMP_POOL         , StructureManagerDataPools.MORDOR_CAMP_NESTS);
        register(context, registryEntryLookup, MORIA_HALL_POOL          , StructureManagerDataPools.MORIA_HALL_NESTS);
        register(context, registryEntryLookup, ROHAN_VILLAGE_POOL       , StructureManagerDataPools.ROHAN_VILLAGE_NESTS);
        register(context, registryEntryLookup, ROHAN_MILITARY_POOL      , StructureManagerDataPools.ROHAN_MILITARY_NESTS);
        register(context, registryEntryLookup, SHIRE_VILLAGE_POOL       , StructureManagerDataPools.SHIRE_VILLAGE_NESTS);
        register(context, registryEntryLookup, WOODLAND_REALM_HAMLET_POOL,StructureManagerDataPools.WOODLAND_REALM_HAMLET_NESTS);
        register(context, registryEntryLookup, WOODLAND_REALM_HALL_POOL , StructureManagerDataPools.WOODLAND_REALM_HALL_NESTS);
    }

    private static void register(BootstrapContext<StructureManagerData> context, HolderGetter<StructureManagerData> registryEntryLookup, ResourceKey<StructureManagerData> registryKey, StructureManagerData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        TranslationEntries.structureManagerEntries.add(element);
    }
}
