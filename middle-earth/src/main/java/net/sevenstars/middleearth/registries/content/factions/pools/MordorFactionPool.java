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
import net.sevenstars.middleearth.registries.content.npctypes.pools.MordorNpcTypePool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class MordorFactionPool {
    public final static Faction MORDOR;

    static {
        MORDOR = new Faction(FactionRegistry.MORDOR, true, DispositionType.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MordorNpcTypePool.SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MordorNpcTypePool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MordorNpcTypePool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MordorNpcTypePool.WARRIOR,
                            MordorNpcTypePool.VETERAN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MordorNpcTypePool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            MordorNpcTypePool.CAPTAIN,
                            MordorNpcTypePool.BLACK_NUMENOREAN
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
                        new SpawnData(MiddleEarth.of("mordor",  "gorgoroth"), new Vector2d(2161, 1717)),
                        new SpawnData(MiddleEarth.of("mordor",  "black_gates"), new Vector2d(2010, 1608)),
                        new SpawnData(MiddleEarth.of("mordor",  "minas_morgul"), new Vector2d(2029, 1770)),
                        new SpawnData(MiddleEarth.of("mordor",  "nurn"), new Vector2d(2345, 1915)),
                        new SpawnData(MiddleEarth.of("mordor",  "dol_guldur"), new Vector2d(1793, 1210))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS_EREBOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WOODLAND_REALM, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );
    }
}
