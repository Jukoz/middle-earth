package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.middleearth.registries.content.npctypes.pools.BrigandNpcTypePool;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;

import java.util.HashMap;
import java.util.List;

public class BrigandFactionPool {
    public final static Faction BRIGAND;

    static {
        BRIGAND = new Faction(FactionRegistry.BRIGAND, false, DispositionType.NEUTRAL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            BrigandNpcTypePool.THUG
                    ));
                    put(NpcRank.MILITIA, List.of(
                            BrigandNpcTypePool.THUG,
                            BrigandNpcTypePool.THIEF
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            BrigandNpcTypePool.MERCENARY
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            BrigandNpcTypePool.CHIEFTAIN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            BrigandNpcTypePool.CHIEFTAIN
                    ));
                    put(NpcRank.LEADER, List.of(
                            BrigandNpcTypePool.CHIEFTAIN
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
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS_EREBOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WOODLAND_REALM, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.ALLY)
                )
        );
    }
}
