package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.WildGoblinNpcDataPool;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;

import java.util.HashMap;
import java.util.List;

public class WildGoblinsFactionPool {
        public final static Faction WILD_GOBLIN;

        static {
            WILD_GOBLIN = new Faction(FactionRegistry.WILD_GOBLINS, false, DispositionType.NEUTRAL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        WildGoblinNpcDataPool.GATHERER
                    ));
                    put(NpcRank.MILITIA, List.of(
                        WildGoblinNpcDataPool.GATHERER
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        WildGoblinNpcDataPool.GATHERER,
                        WildGoblinNpcDataPool.WARRIOR,
                        WildGoblinNpcDataPool.SCOUT,
                        WildGoblinNpcDataPool.RIDER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        WildGoblinNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.VETERAN, List.of(
                        WildGoblinNpcDataPool.BRUTE
                    ));
                    put(NpcRank.LEADER, List.of(
                        WildGoblinNpcDataPool.BRUTE
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                    new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                    new BannerData.BannerPatternWithColor(BannerPatterns.CROSS, DyeColor.RED),
                    new BannerData.BannerPatternWithColor(BannerPatterns.SKULL, DyeColor.WHITE)
                )),
                    null , List.of(), List.of(),
                    List.of(
                            new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.HOSTILE),
                            new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.ALLY),
                            new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                    )
            );
        }

}
