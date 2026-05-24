package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.WoodlandRealmNpcDataPool;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class WoodlandRealmFactionPool {
    public final static Faction WOODLAND_REALM;

    static {
        WOODLAND_REALM = new Faction(FactionRegistry.WOODLAND_REALM, true, DispositionType.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            WoodlandRealmNpcDataPool.RANGER
                    ));
                    put(NpcRank.MILITIA, List.of(
                            WoodlandRealmNpcDataPool.RANGER
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            WoodlandRealmNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            WoodlandRealmNpcDataPool.RANGER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            WoodlandRealmNpcDataPool.RANGER
                    ));
                    put(NpcRank.LEADER, List.of(
                            WoodlandRealmNpcDataPool.COMMANDER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BROWN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.ELK, DyeColor.BROWN)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(FactionRegistry.WOODLAND_REALM.getValue().getPath(),  "elvenkings_halls"), new Vector2d(1957, 766))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );
    }
}
