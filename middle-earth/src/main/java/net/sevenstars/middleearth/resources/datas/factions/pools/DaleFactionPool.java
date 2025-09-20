package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.DalishNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class DaleFactionPool {
    public final static Faction DALE;

    static {
        DALE = new Faction(FactionsME.getName(FactionsME.DALE), true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            NpcME.HUMAN_CIVILIAN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            DalishNpcDataPool.DALE_SOLDIER,
                            DalishNpcDataPool.DALE_SOLDIER_ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            DalishNpcDataPool.DALE_KNIGHT,
                            DalishNpcDataPool.DALE_KNIGHT_ARCHER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            DalishNpcDataPool.DALE_VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            DalishNpcDataPool.DALE_SERGEANT
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.BELL, DyeColor.YELLOW)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "dale.capital"), new Vector2d(2021, 727)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "dale.esgaroth"), new Vector2d(2007, 757))
                )), List.of(), List.of()
        );
    }

}
