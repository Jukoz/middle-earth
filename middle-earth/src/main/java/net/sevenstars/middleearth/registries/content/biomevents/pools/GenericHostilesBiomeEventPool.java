package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GenericHostilesBiomeEventPool {
    public final static List<WildSpawnEventData> WILD_BRIGANDS_EASY = List.of(
            new WildSpawnEventData(NpcRegistry.BRIGAND_CHIEFTAIN).lightShouldBeBetween(0, 7).requireNight().withSameNpcType(0, 512),
            new WildSpawnEventData(NpcRegistry.BRIGAND_MERCENARY).withWeight(3).lightShouldBeBetween(0, 7).requireNight().withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THIEF).withWeight(5).lightShouldBeBetween(0, 7).requireNight().withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THUG).withWeight(5).lightShouldBeBetween(0, 7).requireNight().withSameNpcType(5, 256)
    );

    public final static BiomeEventData CAVE;
    public final static BiomeEventData BRIGANDS;
    public final static BiomeEventData ANDUIN;
    public final static BiomeEventData EMPTY;

    static {
        ANDUIN = new BiomeEventData( false, List.of(
            //Npcs
            new WildSpawnEventData(NpcRegistry.BRIGAND_CHIEFTAIN).withSameNpcType(0, 256).lightShouldBeAtMost(7),
            new WildSpawnEventData(NpcRegistry.BRIGAND_MERCENARY).withWeight(4).withSameNpcType(1, 256).lightShouldBeAtMost(7),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THIEF).withWeight(7).requireNight().lightShouldBeAtMost(5).withSameNpcType(3, 256).lightShouldBeAtMost(7),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THUG).withWeight(8).requireNight().withSameNpcType(4, 256).lightShouldBeAtMost(7),

            new WildSpawnEventData(NpcRegistry.GOBLIN_TOWN_GOBLIN).withWeight(5).withoutSkyRequirement().lightShouldBeBetween(0, 7).withSameNpcType(5, 128).requireNight(),
            new WildSpawnEventData(NpcRegistry.GOBLIN_TOWN_SCOUT).withWeight(4).withoutSkyRequirement().lightShouldBeBetween(0, 7).withSameNpcType(2, 128),
            new WildSpawnEventData(NpcRegistry.GOBLIN_TOWN_WARRIOR).withWeight(3).withoutSkyRequirement().lightShouldBeBetween(0, 7).withSameNpcType(1, 256).requireNight(),
            new WildSpawnEventData(NpcRegistry.GOBLIN_TOWN_RIDER).withWeight(2).withoutSkyRequirement().lightShouldBeBetween(0, 7).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.GOBLIN_TOWN_VETERAN).withWeight(2).withoutSkyRequirement().lightShouldBeBetween(0, 7).withSameNpcType(0, 256).requireNight()
        ));

        BRIGANDS = new BiomeEventData(false, WILD_BRIGANDS_EASY);

        CAVE = new BiomeEventData(false, List.of(
            // Higher Levels
                //Creatures
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .lightShouldBeBetween(0, 7)
                    .withDiscardChance(0.35)
                    .shouldSpawnAbove(0)
                    .withSameEntity(15, 128)
                    .withoutEntitySurfaceOnly(),
                // Npcs
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_GATHERER)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .withSameNpcType(4, 64)
                    .withSameEntity(35, 128)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_SCOUT)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .withSameNpcType(7, 64)
                    .withSameEntity(35, 128)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_WARRIOR)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnAbove(0)
                    .withSameNpcType(4, 64)
                    .withDiscardChance(0.45)
                    .withSameEntity(35, 128)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_BRUTE)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnAbove(0)
                    .withSameNpcType(3, 128)
                    .withDiscardChance(0.65)
                    .withSameEntity(35, 128)
                    .withoutEntitySurfaceOnly(),
                // Lower Levels (Medium Difficulty)
                //Npcs
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_WARRIOR)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnBelow(0)
                    .withSameNpcTypeLimitAmount(10)
                    .withDiscardChance(0.05)
                    .withSameEntity(50, 128)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_BRUTE)
                    .lightShouldBeBetween(0, 7)
                    .withoutSkyRequirement()
                    .shouldSpawnBelow(0)
                    .withSameNpcType(3, 64)
                    .withDiscardChance(0.15)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(NpcRegistry.WILD_GOBLIN_RIDER)
                    .lightShouldBeBetween(0, 7)
                    .shouldSpawnBelow(0)
                    .withoutSkyRequirement()
                    .withSameNpcType(1, 128)
                    .withDiscardChance(0.3)
                    .withSameEntity(50, 128)
                    .withoutEntitySurfaceOnly(),
                //Creatures
                new WildSpawnEventData(EntitiesME.CAVE_TROLL)
                    .shouldSpawnBetween(16, -32)
                    .lightShouldBeBetween(0, 7)
                    .withDiscardChance(0.16)
                    .withSameEntity(3, 128)
                    .requireUnderground()
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .shouldSpawnBetween(0, -32)
                    .withDiscardChance(0.15)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground()
                    .withSameEntity(35, 128)
                    .withoutEntitySurfaceOnly(),
                // Very Low Levels (Higher Difficulty)
                //Creatures
                new WildSpawnEventData(EntitiesME.CAVE_TROLL)
                    .shouldSpawnBelow(-16)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground()
                    .withSameEntity(3, 128)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(EntitiesME.SHELOBITE_SCUTTLER)
                    .shouldSpawnBelow(-32)
                    .withDiscardChance(0.25)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground()
                    .withSameEntity(50, 128)
                    .withoutEntitySurfaceOnly(),
                new WildSpawnEventData(EntitiesME.SPAWN_OF_SHELOB)
                    .shouldSpawnBelow(-32)
                    .withDiscardChance(0.55)
                    .withSameEntity(1, 64)
                    .lightShouldBeBetween(0, 7)
                    .requireUnderground()
                    .withoutEntitySurfaceOnly()
                ));

        EMPTY = new BiomeEventData(false, List.of());
    }
}
