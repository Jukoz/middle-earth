package net.sevenstars.middleearth.registries.content.structuremanagerdatas;

import net.minecraft.entity.EntityType;
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
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THUG.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.bandit"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THIEF.getId()).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THUG.getId()).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.MERCENARY.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.prisoner"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, RohirricNpcTypePool.PEASANT.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES, GundabadNpcTypePool.GOBLIN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.mercenary"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.MERCENARY.getId()).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("brigand_dungeon_nests.chief"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.CHIEFTAIN.getId()).SetFixAmount(1)
                ))
        ));

        DALE_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("dale_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.MILITIA.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.baker"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.lumber"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("dale_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                ))
        ));

        DALE_KEEP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "dale_keep_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.captain"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.SERGEANT.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stables_master"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 4).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.small_prisoner_cell"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THIEF.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THUG.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.CHIEFTAIN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.large_prisoner_cell"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THIEF.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.THUG.getId()).SetRangeAmount(1,3),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.DALE, BrigandNpcTypePool.CHIEFTAIN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.soldier"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.SOLDIER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.ARCHER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.MILITIA.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("dale_keep_nests.elite"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 4).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.KNIGHT.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.DALE, DalishNpcTypePool.SOLDIER.getId()).SetFixAmount(1)
            ))
        ));

        EREBOR_GENERIC_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "erebor_generic_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.captain"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.LEADER.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.guard"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.GATEWARDEN.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.ELITE.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.barrack"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.SOLDIER.getId()).SetRangeAmount(1,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.ARCHER.getId()).SetRangeAmount(1,3),
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.MILITIA.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.worker"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.MINER.getId()).SetRangeAmount(1,2),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.PEASANT.getId()).SetRangeAmount(1,2)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.smith"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LONGBEARDS_EREBOR, EreborNpcTypePool.MINER.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.prisoner"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SNAGA.getId()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THIEF.getValue()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_MERCENARY.getValue()).SetFixAmount(1),
                    new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.BRIGAND, NpcRegistry.BRIGAND_THUG.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("erebor_generic_nests.stable"), RESPAWN_TICKS, List.of(
                    new StructureSpawnNestPool(EntitiesME.BROADHOOF_GOAT, 5).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.PIG, 2).SetFixAmount(1),
                    new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1)
            ))
        ));

        GONDOR_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gondor_village_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.peasant"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.lumber"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.potter"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_village_nests.smith"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetFixAmount(1)
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
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.LEADER.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.guard"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.KNIGHT.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.barracks"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.VETERAN.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.SOLDIER.getId()).SetRangeAmount(1,2),
                new StructureSpawnNestPool(EntitiesME.NPC, 5).SetNpcData(FactionRegistry.GONDOR, GondorianNpcTypePool.MILITIA.getId()).SetRangeAmount(1,3)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.workers"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.GONDOR, NpcRegistry.GONDOR_PEASANT.getValue()).SetRangeAmount(2,3)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.prisoner"), RESPAWN_TICKS, 0, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THIEF.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THUG.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("gondor_lord_nests.stable"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntityType.HORSE, 5).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.DONKEY, 2).SetFixAmount(1),
                new StructureSpawnNestPool(EntityType.PIG, 1).SetFixAmount(1)
            ))
        ));

        GUNDABAD_CAMP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "gundabad_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.GOBLIN.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.MILITIA.getId()).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.GOBLIN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.WARRIOR.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.SCOUT.getId()).SetRangeAmount(1,3)
                )),
                new SpawnNestNodeData(createIdentifier("gundabad_camp_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.VETERAN.getId()).SetFixAmount(1)
                ))
        ));

        ISENGARD_CAMP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "isengard_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.SNAGA.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.WARRIOR.getId()).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.SNAGA.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.WARRIOR.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.URUK_HAI_SCOUT.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.URUK_HAI_SOLDIER.getId()).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.URUK_HAI_BERSERKER.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.URUK_HAI_VETERAN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("isengard_camp_nests.orthanc_guard"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ISENGARD, IsengardNpcTypePool.ORTHANC_GUARD.getId()).SetFixAmount(1)
                ))
        ));

        LOTHLORIEN_HAMLET_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien_hamlet_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, LorienNpcTypePool.RANGER.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.LOTHLORIEN, LorienNpcTypePool.SENTINEL.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.innkeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, LorienNpcTypePool.RANGER.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("lothlorien_hamlet_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.LOTHLORIEN, LorienNpcTypePool.WARRIOR.getId()).SetFixAmount(1)
                ))
        ));

        MORDOR_CAMP_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "mordor_camp_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SNAGA.getId()).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SCOUT.getId()).SetRangeAmount(1,2),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.MILITIA.getId()).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.warg"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.WARG, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SNAGA.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.warrior"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.WARRIOR.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.chieftain"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.WARRIOR.getId()).SetFixAmount(1)
                ))
        ));

        MORIA_HALL_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "moria_hall_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.orc"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.GOBLIN.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.MILITIA.getId()).SetRangeAmount(1,2)
                )),
                new SpawnNestNodeData(createIdentifier("mordor_camp_nests.cave_troll"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.CAVE_TROLL, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.GOBLIN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.WARRIOR.getId()).SetRangeAmount(1,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.SCOUT.getId()).SetRangeAmount(1,3)
                )),
                new SpawnNestNodeData(createIdentifier("moria_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, GundabadNpcTypePool.VETERAN.getId()).SetFixAmount(1)
                ))
        ));

        ROHAN_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "rohan_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.PEASANT.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.MILITIA.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.farmer"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.beekeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.bees"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.BEE, 1).SetRangeAmount(2,3)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, DalishNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.HORSE, 6).SetFixAmount(1),
                        new StructureSpawnNestPool(EntityType.DONKEY, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                ))
        ));

        ROHAN_MILITARY_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "rohan_military_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.SOLDIER.getId()).SetRangeAmount(2,3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.KNIGHT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.guard"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.ROYAL_GUARD.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("rohan_military_nests.marshal"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.ROHAN, RohirricNpcTypePool.EORLING_MARSHAL.getId()).SetFixAmount(1)
                ))
        ));

        SHIRE_VILLAGE_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "shire_village_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("shire_village_nests.poor_civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.rich_civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.baker"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.beekeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.bees"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntityType.BEE, 1).SetRangeAmount(2,3)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.innkeeper"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.smith"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.PEASANT.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("shire_village_nests.shirriff"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.SHIRRIFF.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.SHIRE, ShireNpcTypePool.MILITIA.getId()).SetFixAmount(1)
                ))
        ));

        WOODLAND_REALM_HAMLET_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hamlet_pool"), List.of(
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.civilian"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.RANGER.getId()).SetFixAmount(1),
                new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.ARTISAN.getId()).SetFixAmount(1)
            )),
            new SpawnNestNodeData(createIdentifier("woodland_realm_hamlet_nests.innkeeper"), RESPAWN_TICKS, List.of(
                new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.ARTISAN.getId()).SetFixAmount(1)
            ))
        ));

        WOODLAND_REALM_HALL_NESTS = new StructureManagerData(Identifier.of(MiddleEarth.MOD_ID, "woodland_realm_hall_pool"), List.of(
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.lord"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.COMMANDER.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.civilian"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.ARTISAN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stables_master"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.ARTISAN.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.stable"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.GREAT_HORN, 1).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.prisoner_cell"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SNAGA.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.SCOUT.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.MORDOR, MordorNpcTypePool.MILITIA.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THIEF.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.THUG.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.BRIGAND, BrigandNpcTypePool.MERCENARY.getId()).SetFixAmount(1)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.soldier"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 3).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.WARRIOR.getId()).SetRangeAmount(2, 3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.RANGER.getId()).SetRangeAmount(2, 3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.HUNTER.getId()).SetRangeAmount(2, 3),
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.LANCER.getId()).SetRangeAmount(1, 2)
                )),
                new SpawnNestNodeData(createIdentifier("woodland_realm_hall_nests.elite"), RESPAWN_TICKS, List.of(
                        new StructureSpawnNestPool(EntitiesME.NPC, 1).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.WARRIOR.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.ELVEN_KINGS_GUARD.getId()).SetFixAmount(1),
                        new StructureSpawnNestPool(EntitiesME.NPC, 2).SetNpcData(FactionRegistry.WOODLAND_REALM, WoodlandRealmNpcTypePool.WARDEN_OF_THE_GLADE.getId()).SetFixAmount(1)
                ))
        ));
    }
}
