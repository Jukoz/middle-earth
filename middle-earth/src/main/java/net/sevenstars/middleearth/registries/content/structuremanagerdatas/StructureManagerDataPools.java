package net.sevenstars.middleearth.registries.content.structuremanagerdatas;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.List;

public class StructureManagerDataPools {
    private final static int RESPAWN_TICKS = 24000 * 3;
    
    public final static StructureManagerData BRIGAND_DUNGEON_NESTS;
    public final static StructureManagerData DALE_VILLAGE_NESTS;
    public final static StructureManagerData DALE_KEEP_NESTS;
    public final static StructureManagerData EREBOR_GENERIC_NESTS;
    public final static StructureManagerData GONDOR_VILLAGE_NESTS;
    public final static StructureManagerData GONDOR_LORD_NESTS;
    public final static StructureManagerData GUNDABAD_CAMP_NESTS;
    public final static StructureManagerData ISENGARD_CAMP_NESTS;
    public final static StructureManagerData LOTHLORIEN_HAMLET_NESTS;
    public final static StructureManagerData MORDOR_CAMP_NESTS;
    public final static StructureManagerData MORIA_HALL_NESTS;
    public final static StructureManagerData ROHAN_VILLAGE_NESTS;
    public final static StructureManagerData ROHAN_MILITARY_NESTS;
    public final static StructureManagerData SHIRE_VILLAGE_NESTS;
    public final static StructureManagerData WOODLAND_REALM_HAMLET_NESTS;
    public final static StructureManagerData WOODLAND_REALM_HALL_NESTS;

    private static Identifier createIdentifier(String path) {
        return MiddleEarth.of('.', path);
    }
    
    static {
        BRIGAND_DUNGEON_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "brigand_dungeon_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.cook"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.bandit"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.prisoner"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.ROHAN_PEASANT).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES, NpcRegistry.GUNDABAD_GOBLIN).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.mercenary"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.chief"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_CHIEFTAIN).SetFixAmount(1)
                ))
        ));

        DALE_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("dale_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_MILITIA).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.baker"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.lumber"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT).SetFixAmount(1)
                ))
        ));

        DALE_KEEP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_keep_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.captain"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_VETERAN).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_SERGEANT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stables_master"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 4).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.small_prisoner_cell"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, NpcRegistry.BRIGAND_THIEF).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.BRIGAND_THUG).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.BRIGAND_CHIEFTAIN).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.large_prisoner_cell"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, NpcRegistry.BRIGAND_THIEF).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.BRIGAND_THUG).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.BRIGAND_CHIEFTAIN).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.soldier"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_SOLDIER).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_ARCHER).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_MILITIA).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.elite"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_KNIGHT).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_VETERAN).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_SOLDIER).SetFixAmount(1)
            ))
        ));

        EREBOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.captain"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_LEADER).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.guard"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_VETERAN).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_GATEWARDEN).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_ELITE).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.barrack"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_SOLDIER).SetRangeAmount(1,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_ARCHER).SetRangeAmount(1,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_MILITIA).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.worker"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_MINER).SetRangeAmount(1,2),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_PEASANT).SetRangeAmount(1,2)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.smith"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_MINER).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.prisoner"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SNAGA).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.stable"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.BROADHOOF_GOAT, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 2).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        ));

        GONDOR_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_village_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.peasant"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.lumber"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.potter"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.smith"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.barn"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.COW, 1).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntityType.CHICKEN, 2).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntityType.SHEEP, 2).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntityType.PIG, 3).SetRangeAmount(2,3)
            ))
        ));
        
        GONDOR_LORD_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_lord_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.lord"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_LEADER).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.guard"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_VETERAN).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_KNIGHT).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.barracks"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_VETERAN).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_SOLDIER).SetRangeAmount(1,2),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_MILITIA).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.workers"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT).SetRangeAmount(2,3)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.prisoner"), RESPAWN_TICKS, 0, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        ));

        GUNDABAD_CAMP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gundabad_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_MILITIA).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_WARRIOR).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_SCOUT).SetRangeAmount(1,3)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_VETERAN).SetFixAmount(1)
                ))
        ));

        ISENGARD_CAMP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "isengard_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_SNAGA).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_WARRIOR).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_SNAGA).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_WARRIOR).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_SCOUT).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_SOLDIER).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_BERSERKER).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_VETERAN).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.orthanc_guard"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORTHANC_GUARD).SetFixAmount(1)
                ))
        ));

        LOTHLORIEN_HAMLET_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien_hamlet_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_RANGER).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_SENTINEL).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.innkeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_RANGER).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN,  NpcRegistry.LOTHLORIEN_WARRIOR).SetFixAmount(1)
                ))
        ));

        MORDOR_CAMP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "mordor_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SNAGA).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SCOUT).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_MILITIA).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SNAGA).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.warrior"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_WARRIOR).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.chieftain"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_WARRIOR).SetFixAmount(1)
                ))
        ));

        MORIA_HALL_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "moria_hall_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_MILITIA).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.cave_troll"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.CAVE_TROLL, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_WARRIOR).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_SCOUT).SetRangeAmount(1,3)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_VETERAN).SetFixAmount(1)
                ))
        ));

        ROHAN_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "rohan_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_MILITIA).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.farmer"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.beekeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.bees"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.BEE, 1).SetRangeAmount(2,3)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.HORSE, 6).SetFixAmount(1),
                        new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT).SetFixAmount(1)
                ))
        ));

        ROHAN_MILITARY_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "rohan_military_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_SOLDIER).SetRangeAmount(2,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_KNIGHT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.guard"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_ROYAL_GUARD).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.marshal"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_EORLING_MARSHAL).SetFixAmount(1)
                ))
        ));

        SHIRE_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "shire_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("shire_village_nests.poor_civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.rich_civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.baker"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.beekeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.bees"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.BEE, 1).SetRangeAmount(2,3)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.innkeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.shirriff"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_SHIRRIFF).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_MILITIA).SetFixAmount(1)
                ))
        ));

        WOODLAND_REALM_HAMLET_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hamlet_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_RANGER).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.innkeeper"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN).SetFixAmount(1)
            ))
        ));

        WOODLAND_REALM_HALL_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hall_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.lord"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_COMMANDER).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.GREAT_HORN, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.prisoner_cell"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SNAGA).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SCOUT).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_MILITIA).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_WARRIOR).SetRangeAmount(2, 3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_RANGER).SetRangeAmount(2, 3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_HUNTER).SetRangeAmount(2, 3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_LANCER).SetRangeAmount(1, 2)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_WARRIOR).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ELVEN_KINGS_GUARD).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE).SetFixAmount(1)
                ))
        ));
    }
}
