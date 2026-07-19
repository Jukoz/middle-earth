package net.sevenstars.middleearth.registries;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.pools.*;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.List;
import java.util.Optional;

public class StructureManagerDataRegistryME {
    public final static StructureManagerData GONDOR_GENERIC_NESTS;
    public final static StructureManagerData EREBOR_GENERIC_NESTS;
    public final static StructureManagerData DALE_KEEP_NESTS;

    public static void bootstrap(Registerable<StructureManagerData> context) {
        RegistryEntryLookup<StructureManagerData> structureDataRegistryEntryLookup = context.getRegistryLookup(DynamicRegistriesME.STRUCTURE_MANAGER_DATA);
        // [TEMPLATE]
        register(context, structureDataRegistryEntryLookup, GONDOR_GENERIC_NESTS);
        register(context, structureDataRegistryEntryLookup, EREBOR_GENERIC_NESTS);
        register(context, structureDataRegistryEntryLookup, DALE_KEEP_NESTS);
    }

    private static StructureManagerData register(Registerable<StructureManagerData> context, RegistryEntryLookup<StructureManagerData> registryEntryLookup, StructureManagerData structureManagerData) {
        RegistryKey<StructureManagerData> registryKey = RegistryKey.of(DynamicRegistriesME.STRUCTURE_MANAGER_DATA, structureManagerData.getId());
        String name = registryKey.getValue().getPath();
        RegistryKey<StructureManagerData> structureDataKey = RegistryKey.of(DynamicRegistriesME.STRUCTURE_MANAGER_DATA, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<StructureManagerData>> optionalStructureDataReference = registryEntryLookup.getOptional(registryKey);
        optionalStructureDataReference.ifPresent(biomeReference -> context.register(structureDataKey, structureManagerData));
        
        return structureManagerData;
    }

    static {
        GONDOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_generic_nests"), List.of(
            new SpawnNestNodeData(MiddleEarth.fetchId("gondor_generic_nests.captain_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.LEADER.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.KNIGHT.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.VETERAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("gondor_generic_nests.barrack_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.SOLDIER.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.VETERAN.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("gondor_generic_nests.worker_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.DALE_PEASANT.getValue()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("gondor_generic_nests.prisoner_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SNAGA.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("gondor_generic_nests.stable_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        ));

        EREBOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_nests"), List.of(
            new SpawnNestNodeData(MiddleEarth.fetchId("erebor_generic_nests.captain_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.LEADER.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.GATEWARDEN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.ELITE.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("erebor_generic_nests.barrack_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.SOLDIER.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.ARCHER.getId()).SetRangeAmount(2,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("erebor_generic_nests.worker_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.MINER.getId()).SetRangeAmount(1,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.PEASANT.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("erebor_generic_nests.prisoner_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SNAGA.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(MiddleEarth.fetchId("erebor_generic_nests.stable_nest"), 1500, List.of(
                    new StructureSpawnNestPool(EntitiesME.BROADHOOF_GOAT, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 3).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        ));

        DALE_KEEP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_keep_nests"), List.of(
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.captain"), 1500, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.SERGEANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.stable_carers"), 1500, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(2)
                )),
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.stable"), 1500, List.of(
                        new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.small_prisoner_cell"), 1500, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THIEF.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THUG.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.CHIEFTAIN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.large_prisoner_cell"), 1500, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THIEF.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THUG.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.CHIEFTAIN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.soldier"), 1500, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.SOLDIER.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.ARCHER.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.MILITIA.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(MiddleEarth.fetchId("dale_keep_nests.elite"), 1500, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.KNIGHT.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.SOLDIER.getId()).SetFixAmount(1)
                ))
        ));
    }
}
