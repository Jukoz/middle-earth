package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.registries.content.npcs.pools.RohirricNpcDataPool;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class RohanFactionPool {
    public final static Faction ROHAN;
    private final static String FACTION_PATH = FactionRegistry.ROHAN.getValue().getPath();

    static {
        ROHAN = new Faction(FactionRegistry.ROHAN, true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        RohirricNpcDataPool.MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                        RohirricNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        RohirricNpcDataPool.SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        RohirricNpcDataPool.KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                        RohirricNpcDataPool.KNIGHT,
                        RohirricNpcDataPool.ROYAL_GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                        RohirricNpcDataPool.HORSE_LORD,
                        RohirricNpcDataPool.EORLING_MARSHAL
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.HORSE_HEAD, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "edoras"), new Vector2d(1525, 1600)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "helms_deep"), new Vector2d(1470, 1555)), // Westfold
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "westemnet"), new Vector2d(1525, 1525)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "aldburg"), new Vector2d(1600, 1660)), // Eastfold
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "eastemnet"), new Vector2d(1715, 1575)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "the_wold"), new Vector2d(1675, 1475))
                )), List.of(), List.of(),
                List.of(FactionRegistry.GONDOR), List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.LONGBEARDS, FactionRegistry.DALE,
                FactionRegistry.SHIRE), List.of(FactionRegistry.ISENGARD, FactionRegistry.MORDOR, FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, FactionRegistry.BRIGAND)
        );
    }
}
