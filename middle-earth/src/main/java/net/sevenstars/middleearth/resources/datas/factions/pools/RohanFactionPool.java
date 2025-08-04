package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.ModBannerPatterns;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.RohirricNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class RohanFactionPool {
    public final static Faction ROHAN;

    static {
        ROHAN = new Faction(FactionsME.getName(FactionsME.ROHAN), true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            NpcME.HUMAN_CIVILIAN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            RohirricNpcDataPool.ROHAN_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            RohirricNpcDataPool.ROHAN_SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            RohirricNpcDataPool.ROHAN_KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                            RohirricNpcDataPool.ROHAN_KNIGHT,
                            RohirricNpcDataPool.ROHAN_ROYAL_GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                            RohirricNpcDataPool.ROHAN_HORSE_LORD,
                            RohirricNpcDataPool.ROHAN_EORLING_MARSHAL
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.HORSE_HEAD, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.edoras"), new Vector2d(1525, 1600)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.helms_deep"), new Vector2d(1470, 1555)), // Westfold
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.westemnet"), new Vector2d(1525, 1525)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.aldburg"), new Vector2d(1600, 1660)), // Eastfold
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.eastemnet"), new Vector2d(1715, 1575)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.the_wold"), new Vector2d(1675, 1475))
                )), List.of(), List.of()
        );
    }
}
