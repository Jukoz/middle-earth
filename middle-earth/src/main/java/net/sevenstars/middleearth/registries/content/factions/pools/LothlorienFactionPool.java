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
import net.sevenstars.middleearth.registries.content.npcs.pools.LorienNpcDataPool;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class LothlorienFactionPool {
    public final static Faction LOTHLORIEN;
    private final static String FACTION_PATH = FactionRegistry.LOTHLORIEN.getValue().getPath();

    static {
        LOTHLORIEN = new Faction(FactionRegistry.LOTHLORIEN, true, Disposition.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            LorienNpcDataPool.SENTINEL
                    ));
                    put(NpcRank.MILITIA, List.of(
                            LorienNpcDataPool.RANGER
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            LorienNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            LorienNpcDataPool.KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                            LorienNpcDataPool.GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                            LorienNpcDataPool.LORD
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.STAR_AND_LEAF, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "cerin_amroth"), new Vector2d(1614, 1215))
                )), List.of(), List.of(),
                List.of(), List.of(FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE, FactionRegistry.DALE, FactionRegistry.LONGBEARDS),
                List.of(FactionRegistry.ISENGARD, FactionRegistry.MORDOR, FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, FactionRegistry.BRIGAND)
        );
    }

}
