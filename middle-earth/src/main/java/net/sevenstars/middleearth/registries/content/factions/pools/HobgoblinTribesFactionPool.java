package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.pools.GundabadNpcTypePool;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class HobgoblinTribesFactionPool {
    public final static Faction HOBGOBLIN_TRIBES;
    public final static Faction GUNDABAD;

    static {
        HOBGOBLIN_TRIBES = new Faction(FactionRegistry.HOBGOBLIN_TRIBES, true, DispositionType.EVIL, FactionType.FACTION, null,
                List.of(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD.getValue()),
                null, null, null, List.of(), List.of(), List.of());

        GUNDABAD = new Faction(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, true, DispositionType.EVIL, FactionType.SUBFACTION, HOBGOBLIN_TRIBES.getId(),null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            GundabadNpcTypePool.GOBLIN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            GundabadNpcTypePool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            GundabadNpcTypePool.SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            GundabadNpcTypePool.WARRIOR
                    ));
                    put(NpcRank.VETERAN, List.of(
                            GundabadNpcTypePool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            GundabadNpcTypePool.LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BROWN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.LIGHT_GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.TRIANGLES_BOTTOM, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.EVIL_EYE, DyeColor.RED)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(HOBGOBLIN_TRIBES.getName(), "gundabad",  "gundabad"), new Vector2d(1595, 640)),
                        new SpawnData(MiddleEarth.of(HOBGOBLIN_TRIBES.getName(), "gundabad",  "grey_mountains"), new Vector2d(1652, 640)),
                        new SpawnData(MiddleEarth.of(HOBGOBLIN_TRIBES.getName(), "gundabad",  "mount_gram"), new Vector2d(1401, 686))
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
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );

    }
}
