package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.BrigandNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.GoblinTownNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.WildGoblinNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GenericHostilesBiomeEventPool {
    public final static BiomeEventData CAVE;
    public final static BiomeEventData BRIGANDS;
    public final static BiomeEventData ANDUIN;
    public final static BiomeEventData EMPTY;

    static {
        ANDUIN = new BiomeEventData( false, List.of(
            //Npcs
            new WildSpawnEventData(BrigandNpcDataPool.CHIEFTAIN),
            new WildSpawnEventData(BrigandNpcDataPool.MERCENARY).withWeight(4),
            new WildSpawnEventData(BrigandNpcDataPool.THIEF).withWeight(7).requireNight().lightShouldBeAtMost(5),
            new WildSpawnEventData(BrigandNpcDataPool.THUG).withWeight(8).requireNight(),

            new WildSpawnEventData(GoblinTownNpcDataPool.GOBLIN).withWeight(5).lightShouldBeAtLeast(0).withoutSkyRequirement(),
            new WildSpawnEventData(GoblinTownNpcDataPool.SCOUT).withWeight(4).lightShouldBeAtLeast(0).withoutSkyRequirement(),
            new WildSpawnEventData(GoblinTownNpcDataPool.WARRIOR).withWeight(3).requireNight().lightShouldBeAtMost(5).withoutSkyRequirement(),
            new WildSpawnEventData(GoblinTownNpcDataPool.RIDER).withWeight(2).lightShouldBeAtLeast(0).withoutSkyRequirement(),
            new WildSpawnEventData(GoblinTownNpcDataPool.VETERAN).withWeight(2).lightShouldBeAtMost(5).withoutSkyRequirement()
        ));

        BRIGANDS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(BrigandNpcDataPool.CHIEFTAIN),
            new WildSpawnEventData(BrigandNpcDataPool.MERCENARY).withWeight(3),
            new WildSpawnEventData(BrigandNpcDataPool.THIEF).withWeight(5),
            new WildSpawnEventData(BrigandNpcDataPool.THUG).withWeight(5)
        ));

        CAVE = new BiomeEventData(false, List.of(
            // Higher Levels
                //Creatures
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .lightShouldBeBetween(0, 7)
                    .withDiscardChance(0.35)
                    .shouldSpawnAbove(0),
                // Npcs
                new WildSpawnEventData(WildGoblinNpcDataPool.GATHERER)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .withSameNpc(4, 64),
                new WildSpawnEventData(WildGoblinNpcDataPool.SCOUT)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .withSameNpc(7, 64),
                new WildSpawnEventData(WildGoblinNpcDataPool.WARRIOR)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnAbove(0)
                    .withSameNpc(4, 64)
                    .withDiscardChance(0.45),
                new WildSpawnEventData(WildGoblinNpcDataPool.BRUTE)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnAbove(0)
                    .withSameNpc(3, 128)
                    .withDiscardChance(0.65),
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .shouldSpawnBetween(0, -32)
                    .withDiscardChance(0.45)
                    .lightShouldBeAtMost(7)
                    .requireUnderground(),
            // Lower Levels (Medium Difficulty)
                //Npcs
                new WildSpawnEventData(WildGoblinNpcDataPool.WARRIOR)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnBelow(0)
                    .withSameNpcTypeLimitAmount(10)
                    .withDiscardChance(0.05),
                new WildSpawnEventData(WildGoblinNpcDataPool.BRUTE)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnBelow(0)
                    .withSameNpc(2, 64)
                    .withDiscardChance(0.15),
                new WildSpawnEventData(WildGoblinNpcDataPool.RIDER)
                    .lightShouldBeBetween(0, 7)
                    .shouldSpawnBelow(0)
                    .withoutSkyRequirement()
                    .withSameNpc(1, 128)
                    .withDiscardChance(0.3),
                //Creatures
                new WildSpawnEventData(EntitiesME.CAVE_TROLL)
                    .shouldSpawnBetween(16, -32)
                    .lightShouldBeBetween(0, 7)
                    .withDiscardChance(0.16)
                    .withSameEntity(0, 96)
                    .requireUnderground(),
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .shouldSpawnBetween(0, -32)
                    .withDiscardChance(0.15)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground(),
            // Very Low Levels (Higher Difficulty)
                //Creatures
                new WildSpawnEventData(EntitiesME.CAVE_TROLL)
                    .shouldSpawnBelow(-16)
                    .lightShouldBeBetween(0, 7)
                    .withSameEntity(0, 64)
                    .requireUnderground(),
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .shouldSpawnBelow(-32)
                    .withDiscardChance(0.25)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground(),
                new WildSpawnEventData(EntitiesME.SPAWN_OF_SHELOB)
                    .shouldSpawnBelow(-32)
                    .withDiscardChance(0.55)
                    .withSameEntity(0, 64)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground()
        ));

        EMPTY = new BiomeEventData(false, List.of());
    }
}
