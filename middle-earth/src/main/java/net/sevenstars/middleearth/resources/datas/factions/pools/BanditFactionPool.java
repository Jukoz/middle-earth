package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.BanditNpcDataPool;

import java.util.HashMap;
import java.util.List;

public class BanditFactionPool {
    public final static Faction BANDIT;

    static {
        BANDIT = new Faction(FactionsME.getName(FactionsME.BANDIT), false, Disposition.NEUTRAL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            BanditNpcDataPool.BANDIT_THUG
                    ));
                    put(NpcRank.MILITIA, List.of(
                            BanditNpcDataPool.BANDIT_THUG,
                            BanditNpcDataPool.BANDIT_THIEF
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            BanditNpcDataPool.BANDIT_MERCENARY,
                            BanditNpcDataPool.WILD_GOBLIN_GATHERER,
                            BanditNpcDataPool.WILD_GOBLIN_WARRIOR,
                            BanditNpcDataPool.WILD_GOBLIN_SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            BanditNpcDataPool.BANDIT_CHIEFTAIN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            BanditNpcDataPool.BANDIT_CHIEFTAIN
                    ));
                    put(NpcRank.LEADER, List.of(
                            BanditNpcDataPool.BANDIT_CHIEFTAIN
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CROSS, DyeColor.RED),
                        new BannerData.BannerPatternWithColor(BannerPatterns.SKULL, DyeColor.WHITE)
                )),
                null , List.of(), List.of()
        );
    }
}
