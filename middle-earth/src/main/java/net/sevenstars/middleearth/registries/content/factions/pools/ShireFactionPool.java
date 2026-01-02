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
import net.sevenstars.middleearth.registries.content.npcs.pools.ShireNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class ShireFactionPool {
    public final static Faction SHIRE;

    static {
        SHIRE = new Faction(FactionRegistry.SHIRE, true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        ShireNpcDataPool.PEASANT
                    ));
                    put(NpcRank.MILITIA, List.of(
                            ShireNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            ShireNpcDataPool.SHIRRIFF
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            ShireNpcDataPool.SHIRRIFF
                    ));
                    put(NpcRank.VETERAN, List.of(
                            ShireNpcDataPool.SHIRRIFF
                    ));
                    put(NpcRank.LEADER, List.of(
                            ShireNpcDataPool.SHIRRIFF
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.PIPE, DyeColor.BROWN)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "shire.hobbiton"), new Vector2d(933, 900)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "shire.willowbottom"), new Vector2d(981, 970))
                )), List.of(), List.of(),
                List.of(),
                List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.LONGBEARDS, FactionRegistry.DALE),
                List.of(FactionRegistry.MISTY_MOUNTAINS_GOBLINS, FactionRegistry.MORDOR, FactionRegistry.ISENGARD, FactionRegistry.BRIGAND)
        );
    }

}
