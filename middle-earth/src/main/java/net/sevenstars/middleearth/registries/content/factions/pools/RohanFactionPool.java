package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.middleearth.registries.content.npctypes.pools.RohirricNpcTypePool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class RohanFactionPool {
    public final static Faction ROHAN;
    private final static String FACTION_PATH = FactionRegistry.ROHAN.getValue().getPath();

    static {
        ROHAN = new Faction(FactionRegistry.ROHAN, true, DispositionType.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        RohirricNpcTypePool.MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                        RohirricNpcTypePool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        RohirricNpcTypePool.SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        RohirricNpcTypePool.KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                        RohirricNpcTypePool.KNIGHT,
                        RohirricNpcTypePool.ROYAL_GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                        RohirricNpcTypePool.HORSE_LORD,
                        RohirricNpcTypePool.EORLING_MARSHAL
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.HORSE_HEAD, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of("rohan",  "edoras"), new Vector2d(1525, 1600)),
                        new SpawnData(MiddleEarth.of("rohan",  "helms_deep"), new Vector2d(1470, 1555)), // Westfold
                        new SpawnData(MiddleEarth.of("rohan",  "westemnet"), new Vector2d(1525, 1525)),
                        new SpawnData(MiddleEarth.of("rohan",  "aldburg"), new Vector2d(1600, 1660)), // Eastfold
                        new SpawnData(MiddleEarth.of("rohan",  "eastemnet"), new Vector2d(1715, 1575)),
                        new SpawnData(MiddleEarth.of("rohan",  "the_wold"), new Vector2d(1675, 1475))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS_EREBOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );
    }
}
