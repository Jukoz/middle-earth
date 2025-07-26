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
import net.sevenstars.middleearth.resources.datas.npcs.pools.ShireNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class ShireFactionPool {
    public final static Faction SHIRE;

    static {
        SHIRE = new Faction(FactionsME.getName(FactionsME.SHIRE), true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            NpcME.HOBBIT_CIVILIAN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            ShireNpcDataPool.SHIRE_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                    put(NpcRank.VETERAN, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                    put(NpcRank.LEADER, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.PIPE, DyeColor.BROWN)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "shire.hobbiton"), new Vector2d(933, 900)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "shire.willowbottom"), new Vector2d(981, 970))
                )), List.of(), List.of()
        );
    }

}
