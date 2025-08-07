package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.resources.datas.npcs.pools.EreborNpcDataPool;
import net.sevenstars.middleearth.resources.datas.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.resources.datas.npcs.pools.MordorNpcDataPool;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.List;
import java.util.Optional;

public class StructureManagerDatasME {
    public final static String PATH = "structure_manager_datas";
    public static final RegistryKey<Registry<StructureManagerData>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Structure Manager Datas for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, StructureManagerData.CODEC);
    }

    public final static StructureManagerData GONDOR_GENERIC_NESTS;
    public final static StructureManagerData EREBOR_GENERIC_NESTS;

    public static void bootstrap(Registerable<StructureManagerData> context) {
        RegistryEntryLookup<StructureManagerData> structureDataRegistryEntryLookup = context.getRegistryLookup(KEY);
        // [TEMPLATE]
        register(context, structureDataRegistryEntryLookup, GONDOR_GENERIC_NESTS);
        register(context, structureDataRegistryEntryLookup, EREBOR_GENERIC_NESTS);
    }

    private static StructureManagerData register(Registerable<StructureManagerData> context, RegistryEntryLookup<StructureManagerData> registryEntryLookup, StructureManagerData structureManagerData) {
        RegistryKey<StructureManagerData> registryKey = RegistryKey.of(KEY, structureManagerData.getId());
        String name = registryKey.getValue().getPath();
        RegistryKey<StructureManagerData> structureDataKey = RegistryKey.of(KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<StructureManagerData>> optionalStructureDataReference = registryEntryLookup.getOptional(registryKey);
        optionalStructureDataReference.ifPresent(biomeReference -> context.register(structureDataKey, structureManagerData));

        TranslationEntries.factionEntries.add(structureManagerData.getId().getPath());

        return structureManagerData;
    }

    static {
        GONDOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_generic_nests"), List.of(
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.captain_nest"), 1000, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 1).SetNpcData(FactionsME.GONDOR, GondorianNpcDataPool.GONDOR_LEADER.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 2).SetNpcData(FactionsME.GONDOR, GondorianNpcDataPool.GONDOR_KNIGHT.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.GONDOR, GondorianNpcDataPool.GONDOR_VETERAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.barrack_nest"), 500, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.GONDOR, GondorianNpcDataPool.GONDOR_SOLDIER.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(ModEntities.NPC, 2).SetNpcData(FactionsME.GONDOR, GondorianNpcDataPool.GONDOR_VETERAN.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(ModEntities.NPC, 5).SetNpcData(FactionsME.GONDOR, GondorianNpcDataPool.GONDOR_MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.worker_nest"), 500, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 2).SetNpcData(FactionsME.GONDOR, NpcME.HUMAN_CIVILIAN.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.prisoner_nest"), 500, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 5).SetNpcData(FactionsME.MORDOR, MordorNpcDataPool.MORDOR_ORC_SNAGA.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.LONGBEARDS_EREBOR, NpcME.DWARF_CIVILIAN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 1).SetNpcData(FactionsME.LOTHLORIEN, NpcME.ELF_CIVILIAN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.BRIGAND, NpcME.HUMAN_CIVILIAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.stable_nest"), 3000, List.of(
                    new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        ));

        EREBOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_nests"), List.of(
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.captain_nest"), 1000, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 1).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_LEADER.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 2).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_GATEWARDEN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_ELITE.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.barrack_nest"), 500, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_SOLDIER.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(ModEntities.NPC, 2).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_ARCHER.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(ModEntities.NPC, 5).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.worker_nest"), 500, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 2).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_MINER.getId()).SetRangeAmount(1,3),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_CIVILIAN.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.prisoner_nest"), 500, List.of(
                    new StructureSpawnNestPool(ModEntities.NPC, 5).SetNpcData(FactionsME.MORDOR, MordorNpcDataPool.MORDOR_ORC_SNAGA.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.LONGBEARDS_EREBOR, NpcME.DWARF_CIVILIAN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 1).SetNpcData(FactionsME.LOTHLORIEN, NpcME.ELF_CIVILIAN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(ModEntities.NPC, 3).SetNpcData(FactionsME.BRIGAND, NpcME.HUMAN_CIVILIAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.stable_nest"), 3000, List.of(
                    new StructureSpawnNestPool(ModEntities.BROADHOOF_GOAT, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 3).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        ));
    }
}
