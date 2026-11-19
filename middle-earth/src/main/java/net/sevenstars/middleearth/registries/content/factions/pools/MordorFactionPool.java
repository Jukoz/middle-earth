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
import net.sevenstars.middleearth.resources.datas.factions.data.FactionCreatures;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.middleearth.features.npcs.classes.pools.MordorNpcClassPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class MordorFactionPool {
    public final static Faction MORDOR;

    static {
        MORDOR = new Faction(FactionRegistry.MORDOR, true, DispositionType.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MordorNpcClassPool.SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MordorNpcClassPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MordorNpcClassPool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MordorNpcClassPool.WARRIOR,
                            MordorNpcClassPool.VETERAN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MordorNpcClassPool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            MordorNpcClassPool.CAPTAIN,
                            MordorNpcClassPool.BLACK_NUMENOREAN
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
                        new SpawnData(MiddleEarth.idAggregate("mordor",  "gorgoroth"), new Vector2d(2161, 1717)),
                        new SpawnData(MiddleEarth.idAggregate("mordor",  "black_gates"), new Vector2d(2010, 1608)),
                        new SpawnData(MiddleEarth.idAggregate("mordor",  "minas_morgul"), new Vector2d(2029, 1770)),
                        new SpawnData(MiddleEarth.idAggregate("mordor",  "nurn"), new Vector2d(2345, 1915)),
                        new SpawnData(MiddleEarth.idAggregate("mordor",  "dol_guldur"), new Vector2d(1793, 1210))
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
        ).withCreatures(FactionCreatures.friendlyToShadowBeasts());
    }
}
