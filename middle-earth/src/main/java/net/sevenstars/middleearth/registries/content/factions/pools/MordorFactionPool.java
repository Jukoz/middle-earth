package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.registries.content.npcs.pools.MordorNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class MordorFactionPool {
    public final static Faction MORDOR;

    static {
        MORDOR = new Faction(FactionRegistry.MORDOR, true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MordorNpcDataPool.SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MordorNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MordorNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MordorNpcDataPool.WARRIOR,
                            MordorNpcDataPool.VETERAN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MordorNpcDataPool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            MordorNpcDataPool.CAPTAIN,
                            MordorNpcDataPool.BLACK_NUMENOREAN
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
                )), List.of(), List.of(),
                List.of(FactionRegistry.ISENGARD), List.of(FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD),
                List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE, FactionRegistry.LONGBEARDS, FactionRegistry.DALE, FactionRegistry.BRIGAND)
        );
    }
}
