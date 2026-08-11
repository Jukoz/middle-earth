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
import net.sevenstars.middleearth.registries.content.npctypes.pools.ShireNpcTypePool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class ShireFactionPool {
    public final static Faction SHIRE;

    static {
        SHIRE = new Faction(FactionRegistry.SHIRE, true, DispositionType.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        ShireNpcTypePool.PEASANT
                    ));
                    put(NpcRank.MILITIA, List.of(
                            ShireNpcTypePool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            ShireNpcTypePool.SHIRRIFF
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            ShireNpcTypePool.SHIRRIFF
                    ));
                    put(NpcRank.VETERAN, List.of(
                            ShireNpcTypePool.SHIRRIFF
                    ));
                    put(NpcRank.LEADER, List.of(
                            ShireNpcTypePool.SHIRRIFF
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.PIPE, DyeColor.BROWN)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(FactionRegistry.SHIRE.getValue().getPath(), "hobbiton"), new Vector2d(933, 900)),
                        new SpawnData(MiddleEarth.of(FactionRegistry.SHIRE.getValue().getPath(), "willowbottom"), new Vector2d(981, 970))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.ALLY),
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
