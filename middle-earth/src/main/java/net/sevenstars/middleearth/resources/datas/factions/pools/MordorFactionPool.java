package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.MordorNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class MordorFactionPool {
    public final static Faction MORDOR;

    static {
        MORDOR = new Faction(FactionsME.getName(FactionsME.MORDOR), true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MordorNpcDataPool.MORDOR_ORC_SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_NUMENOREAN,
                            MordorNpcDataPool.MORDOR_ORC_SNAGA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_ORC_SCOUT,
                            MordorNpcDataPool.MORDOR_ORC_SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_URUK_SOLDIER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_URUK_VETERAN,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_VETERAN_ARCHER
                    ));
                    put(NpcRank.LEADER, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_URUK_LEADER
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.SMALL_CIRCLE, DyeColor.ORANGE),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.EYE_OF_SAURON, DyeColor.RED)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.gorgoroth"), new Vector2d(2161, 1717)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_gates"), new Vector2d(2010, 1608)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.minas_morgul"), new Vector2d(2029, 1770)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.nurn"), new Vector2d(2345, 1915)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.dol_guldur"), new Vector2d(1793, 1210))
                )), List.of(), List.of()
        );
    }
}
