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

import java.util.List;

public class StructureManagerDataPools {
    private final static int RESPAWN_TICKS = 1500;
    
    public final static StructureManagerData GONDOR_GENERIC_NESTS;
    public final static StructureManagerData EREBOR_GENERIC_NESTS;
    public final static StructureManagerData DALE_KEEP_NESTS;
    public final static StructureManagerData WOODLAND_REALM_HAMLET_NESTS;
    public final static StructureManagerData WOODLAND_REALM_HALL_NESTS;

    private static Identifier createIdentifier(String path) {
        return MiddleEarth.of('.', path);
    }
    
    static {
        GONDOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_generic_nests"), List.of(
            new SpawnNestNodeData(createIdentifier("gondor_generic_nests.captain_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.LEADER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.KNIGHT.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.VETERAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_generic_nests.barrack_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.SOLDIER.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.VETERAN.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.GONDOR, GondorianNpcDataPool.MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_generic_nests.worker_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_generic_nests.prisoner_nest"), RESPAWN_TICKS, 0, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.SNAGA.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.DALE_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_generic_nests.stable_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        ));

        EREBOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_nests"), List.of(
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.captain_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.LEADER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.GATEWARDEN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.ELITE.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.barrack_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.SOLDIER.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.ARCHER.getId()).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.MILITIA.getId()).SetRangeAmount(2,4)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.worker_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.MINER.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcDataPool.PEASANT.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.prisoner_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.SNAGA.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY.getValue()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.stable_nest"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.BROADHOOF_GOAT, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 3).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        ));

        DALE_KEEP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_keep_nests"), List.of(
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.captain"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.SERGEANT.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stable_carers"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.PEASANT.getId()).SetFixAmount(2)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.small_prisoner_cell"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THIEF.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THUG.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.CHIEFTAIN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.large_prisoner_cell"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THIEF.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.THUG.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcDataPool.CHIEFTAIN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.soldier"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.SOLDIER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.ARCHER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.MILITIA.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.elite"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.KNIGHT.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcDataPool.SOLDIER.getId()).SetFixAmount(1)
            ))
        ));

        WOODLAND_REALM_HAMLET_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hamlet_nests"), List.of(
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcDataPool.RANGER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcDataPool.ARTISAN.getId()).SetFixAmount(1)
            ))
        ));

        WOODLAND_REALM_HALL_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hall_nests"), List.of(
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.lord"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcDataPool.COMMANDER.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcDataPool.ARTISAN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcDataPool.ARTISAN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.GREAT_HORN, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.prisoner_cell"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.SNAGA.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.SCOUT.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcDataPool.MILITIA.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcDataPool.THIEF.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcDataPool.THUG.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcDataPool.MERCENARY.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.WARRIOR.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.RANGER.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.HUNTER.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.LANCER.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.WARRIOR.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.ELVEN_KINGS_GUARD.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, WoodlandRealmNpcDataPool.WARDEN_OF_THE_GLADE.getId()).SetFixAmount(1)
                ))
        ));
    }
}
