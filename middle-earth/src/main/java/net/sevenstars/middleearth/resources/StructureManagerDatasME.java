package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.resources.datas.npcs.pools.EreborNpcDataPool;
import net.sevenstars.middleearth.resources.datas.npcs.pools.GondorianNpcDataPool;
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

    public final static StructureManagerData NPC_TESTING_AREA_GONDOR;
    public final static StructureManagerData NPC_TESTING_AREA_EREBOR;

    public static void bootstrap(Registerable<StructureManagerData> context) {
        RegistryEntryLookup<StructureManagerData> structureDataRegistryEntryLookup = context.getRegistryLookup(KEY);
        // [TEMPLATE]
        register(context, structureDataRegistryEntryLookup, NPC_TESTING_AREA_GONDOR);
        register(context, structureDataRegistryEntryLookup, NPC_TESTING_AREA_EREBOR);
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
        NPC_TESTING_AREA_GONDOR = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "npc_testing_area_gondor"), List.of(
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("npc_testing_area_gondor.captain_room"), 100, new BlockPos(-3,14,-5), List.of(
                    new StructureSpawnNestPool(GondorianNpcDataPool.GONDOR_LEADER.getId(), 2).SetFixAmount(1),
                    new StructureSpawnNestPool(GondorianNpcDataPool.GONDOR_VETERAN.getId(), 4).SetFixAmount(1),
                    new StructureSpawnNestPool(GondorianNpcDataPool.GONDOR_KNIGHT.getId(), 4).SetFixAmount(1)
            )).WithBedRadius(5),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("npc_testing_area_gondor.barracks_room"), 200, new BlockPos(0,0,0), List.of(
                    new StructureSpawnNestPool(GondorianNpcDataPool.GONDOR_MILITIA.getId(), 4).SetRangeAmount(2, 6),
                    new StructureSpawnNestPool(GondorianNpcDataPool.GONDOR_SOLDIER.getId(), 2).SetRangeAmount(2, 4)
            )).WithBedRadius(8)
        ));

        NPC_TESTING_AREA_EREBOR = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "npc_testing_area_erebor"), List.of(
                new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("npc_testing_area_erebor.captain_room"), 100, new BlockPos(-3,14,-5), List.of(
                        new StructureSpawnNestPool(EreborNpcDataPool.EREBOR_LEADER.getId(), 2).SetFixAmount(1),
                        new StructureSpawnNestPool(EreborNpcDataPool.EREBOR_VETERAN.getId(), 4).SetFixAmount(1),
                        new StructureSpawnNestPool(EreborNpcDataPool.EREBOR_ELITE.getId(), 4).SetFixAmount(1)
                )).WithBedRadius(5),
                new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("npc_testing_area_erebor.barracks_room"), 200, new BlockPos(0,0,0), List.of(
                        new StructureSpawnNestPool(EreborNpcDataPool.EREBOR_SOLDIER.getId(), 4).SetRangeAmount(2, 6),
                        new StructureSpawnNestPool(EreborNpcDataPool.EREBOR_MINER.getId(), 2).SetRangeAmount(2, 4)
                )).WithBedRadius(8)
        ));
    }
}
