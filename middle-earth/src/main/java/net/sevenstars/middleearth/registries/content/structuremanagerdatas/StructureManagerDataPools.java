package net.sevenstars.middleearth.registries.content.structuremanagerdatas;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.*;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.List;

public class StructureManagerDataPools {
    public final static StructureManagerData GONDOR_GENERIC_NESTS;
    public final static StructureManagerData EREBOR_GENERIC_NESTS;
    public final static StructureManagerData DALE_KEEP_NESTS;

    static {
        GONDOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_generic_nests"), List.of(
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.captain_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.LEADER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.KNIGHT.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.VETERAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.barrack_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.SOLDIER.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.VETERAN.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.worker_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.prisoner_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.MORDOR_ORC_SNAGA.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("gondor_generic_nests.stable_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        ));

        EREBOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_nests"), List.of(
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.captain_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_LEADER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_GATEWARDEN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_ELITE.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.barrack_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_SOLDIER.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_ARCHER.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.worker_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_MINER.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.EREBOR_CIVILIAN.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.prisoner_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.MORDOR_ORC_SNAGA.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("erebor_generic_nests.stable_nest"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.BROADHOOF_GOAT, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 3).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        ));

        DALE_KEEP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_keep_nests"), List.of(
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.captain"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.SERGEANT.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.stable_carers"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.PEASANT.getId()).SetFixAmount(2)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.stable"), 1500, List.of(
                new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.small_prisoner_cell"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THIEF.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THUG.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.CHIEFTAIN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.large_prisoner_cell"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THIEF.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THUG.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.CHIEFTAIN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.soldier"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.SOLDIER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.ARCHER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.MILITIA.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(IdentifierUtil.getIdentifierFromString("dale_keep_nests.elite"), 1500, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.KNIGHT.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.SOLDIER.getId()).SetFixAmount(1)
            ))
        ));
    }
}
