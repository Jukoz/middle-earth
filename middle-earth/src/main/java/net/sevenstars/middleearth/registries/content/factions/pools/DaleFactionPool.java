package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.DispositionType;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.registries.content.npcs.pools.DalishNpcDataPool;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class DaleFactionPool {
    public final static Faction DALE;
    private final static String FACTION_PATH = FactionRegistry.DALE.getValue().getPath();

    static {
        DALE = new Faction(FactionRegistry.DALE, true, DispositionType.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        DalishNpcDataPool.PEASANT
                    ));
                    put(NpcRank.MILITIA, List.of(
                        DalishNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        DalishNpcDataPool.SOLDIER,
                        DalishNpcDataPool.ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        DalishNpcDataPool.KNIGHT,
                        DalishNpcDataPool.ELITE_ARCHER
                    ));
                    put(NpcRank.VETERAN, List.of(
                        DalishNpcDataPool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                        DalishNpcDataPool.SERGEANT
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.BELL, DyeColor.YELLOW)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "capital"), new Vector2d(2021, 727)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "esgaroth"), new Vector2d(2007, 757))
                )), List.of(), List.of(),
                List.of(FactionRegistry.LONGBEARDS_EREBOR), List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.LONGBEARDS, FactionRegistry.GONDOR, FactionRegistry.ROHAN,
                FactionRegistry.SHIRE), List.of(FactionRegistry.ISENGARD, FactionRegistry.MORDOR, FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, FactionRegistry.BRIGAND)
        );
    }

}
