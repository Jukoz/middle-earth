package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.BrigandNpcDataPool;

import java.util.HashMap;
import java.util.List;

public class BrigandFactionPool {
    public final static Faction BRIGAND;

    static {
        BRIGAND = new Faction(FactionsME.getName(FactionsME.BRIGAND), false, Disposition.NEUTRAL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            BrigandNpcDataPool.BRIGAND_THUG
                    ));
                    put(NpcRank.MILITIA, List.of(
                            BrigandNpcDataPool.BRIGAND_THUG,
                            BrigandNpcDataPool.BRIGAND_THIEF
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            BrigandNpcDataPool.BRIGAND_MERCENARY,
                            BrigandNpcDataPool.WILD_GOBLIN_GATHERER,
                            BrigandNpcDataPool.WILD_GOBLIN_WARRIOR,
                            BrigandNpcDataPool.WILD_GOBLIN_SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            BrigandNpcDataPool.BRIGAND_CHIEFTAIN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            BrigandNpcDataPool.BRIGAND_CHIEFTAIN
                    ));
                    put(NpcRank.LEADER, List.of(
                            BrigandNpcDataPool.BRIGAND_CHIEFTAIN
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
                List.of(FactionsME.LOTHLORIEN, FactionsME.GONDOR, FactionsME.ROHAN, FactionsME.SHIRE, FactionsME.LONGBEARDS,
                        FactionsME.DALE, FactionsME.MISTY_MOUNTAINS_GOBLINS, FactionsME.MORDOR, FactionsME.ISENGARD)
        );
    }
}
