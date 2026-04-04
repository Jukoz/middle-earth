package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.middleearth.registries.content.npcs.pools.BrigandNpcDataPool;

import java.util.HashMap;
import java.util.List;

public class BrigandFactionPool {
    public final static Faction BRIGAND;

    static {
        BRIGAND = new Faction(FactionRegistry.BRIGAND, false, DispositionType.NEUTRAL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            BrigandNpcDataPool.THUG
                    ));
                    put(NpcRank.MILITIA, List.of(
                            BrigandNpcDataPool.THUG,
                            BrigandNpcDataPool.THIEF
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            BrigandNpcDataPool.MERCENARY
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            BrigandNpcDataPool.CHIEFTAIN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            BrigandNpcDataPool.CHIEFTAIN
                    ));
                    put(NpcRank.LEADER, List.of(
                            BrigandNpcDataPool.CHIEFTAIN
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CROSS, DyeColor.RED),
                        new BannerData.BannerPatternWithColor(BannerPatterns.SKULL, DyeColor.WHITE)
                )),
                null , List.of(), List.of(),
                List.of(),
                List.of(),
                List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE, FactionRegistry.LONGBEARDS,
                        FactionRegistry.DALE, FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, FactionRegistry.MORDOR, FactionRegistry.ISENGARD, FactionRegistry.WILD_GOBLINS)
        );
    }
}
