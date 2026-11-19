package net.sevenstars.middleearth.registries.content.structuremanagerdatas;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.List;

public class StructureManagerDataPools {
    // Three days, in ticks
    public final static int RESPAWN_TICKS = 24000 * 3;
    // Who is held in a cell unless the bed names someone else
    private static final List<StructureSpawnNestPool> PRISONERS = List.of(
            new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF),
            new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG));

    private static final List<StructureManagerData> ALL = new ArrayList<>();

    // The pools as built in code, for datagen and reports. A running world reads its registry instead
    public static List<StructureManagerData> all() {
        return List.copyOf(ALL);
    }

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

    private static StructureManagerData add(StructureManagerData data) {
        ALL.add(data);
        return data;
    }

    private static Identifier createIdentifier(String path) {
        return MiddleEarth.idAggregate('.', path);
    }
    
    static {
        BRIGAND_DUNGEON_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "brigand_dungeon_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.cook"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.bandit"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.prisoner"), RESPAWN_TICKS, PRISONERS),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.mercenary"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.chief"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_CHIEFTAIN)
                ))
        )));

        DALE_VILLAGE_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("dale_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_MILITIA)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.baker"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.lumber"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT)
                ))
        )));

        DALE_KEEP_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_keep_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.captain"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_VETERAN),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_SERGEANT)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stables_master"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_PEASANT)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 4).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.small_prisoner_cell"), RESPAWN_TICKS, PRISONERS),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.large_prisoner_cell"), RESPAWN_TICKS, PRISONERS),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.soldier"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_SOLDIER),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_ARCHER),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_MILITIA)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.elite"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_KNIGHT),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_VETERAN),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, NpcRegistry.DALE_SOLDIER)
            ))
        )));

        EREBOR_GENERIC_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.captain"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_LEADER)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.guard"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_VETERAN),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_GATEWARDEN),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_ELITE)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.barrack"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_SOLDIER),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_ARCHER),
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_MILITIA)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.worker"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_PEASANT)
            )).withJob(NpcJobs.MINER),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.smith"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, NpcRegistry.EREBOR_PEASANT)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.prisoner"), RESPAWN_TICKS, PRISONERS),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.stable"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.BROADHOOF_GOAT, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 2).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        )));

        GONDOR_VILLAGE_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_village_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.peasant"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_MILITIA)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.lumber"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.potter"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.smith"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.barn"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.COW, 1).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntityType.CHICKEN, 2).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntityType.SHEEP, 2).SetRangeAmount(2,3),
                new StructureSpawnNestPool(EntityType.PIG, 3).SetRangeAmount(2,3)
            ))
        )));
        
        GONDOR_LORD_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_lord_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.lord"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_LEADER)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.guard"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_VETERAN),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_KNIGHT)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.barracks"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_VETERAN),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_SOLDIER),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_MILITIA)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.workers"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.prisoner"), RESPAWN_TICKS, PRISONERS),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        )));

        GUNDABAD_CAMP_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gundabad_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_MILITIA)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_WARRIOR),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_SCOUT)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_VETERAN)
                ))
        )));

        ISENGARD_CAMP_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "isengard_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_SNAGA),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_WARRIOR)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_SNAGA)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORC_WARRIOR),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_SCOUT),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_SOLDIER)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_BERSERKER),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_URUK_HAI_VETERAN)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.orthanc_guard"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, NpcRegistry.ISENGARD_ORTHANC_GUARD)
                ))
        )));

        LOTHLORIEN_HAMLET_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien_hamlet_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_CIVILIAN),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_SENTINEL),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_RANGER)
                )),
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.innkeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, NpcRegistry.LOTHLORIEN_CIVILIAN)
                )),
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN,  NpcRegistry.LOTHLORIEN_WARRIOR)
                ))
        )));

        MORDOR_CAMP_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "mordor_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SNAGA),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SCOUT),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_MILITIA)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_SNAGA)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.warrior"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_WARRIOR)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.chieftain"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, NpcRegistry.MORDOR_WARRIOR)
                )).withJob(NpcJobs.NIGHT_LORD)
        )));

        MORIA_HALL_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "moria_hall_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_MILITIA)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.cave_troll"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.CAVE_TROLL, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_GOBLIN)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_WARRIOR),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_SCOUT)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, NpcRegistry.GUNDABAD_VETERAN)
                ))
        )));

        ROHAN_VILLAGE_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "rohan_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_MILITIA)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.farmer"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.beekeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.bees"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.BEE, 1).SetRangeAmount(2,3)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.HORSE, 6).SetFixAmount(1),
                        new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_PEASANT)
                ))
        )));

        ROHAN_MILITARY_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "rohan_military_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_SOLDIER),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_KNIGHT)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.guard"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_ROYAL_GUARD)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.marshal"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, NpcRegistry.ROHAN_EORLING_MARSHAL)
                ))
        )));

        SHIRE_VILLAGE_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "shire_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("shire_village_nests.poor_civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.rich_civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.baker"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.beekeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.bees"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.BEE, 1).SetRangeAmount(2,3)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.innkeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_PEASANT)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.shirriff"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_SHIRRIFF),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, NpcRegistry.SHIRE_MILITIA)
                ))
        )));

        WOODLAND_REALM_HAMLET_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hamlet_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_RANGER),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN)
            )),
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.innkeeper"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN)
            ))
        )));

        WOODLAND_REALM_HALL_NESTS = add(new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hall_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.lord"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_COMMANDER)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ARTISAN)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.GREAT_HORN, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.prisoner_cell"), RESPAWN_TICKS, PRISONERS),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_WARRIOR),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_RANGER),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_HUNTER),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_LANCER)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_WARRIOR),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_ELVEN_KINGS_GUARD),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE)
                ))
        )));
    }
}
