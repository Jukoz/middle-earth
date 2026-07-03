package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.MoriaNpcDataPool;
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

public class MoriaFactionPool {
    public final static Faction MORIA;

    static {
        MORIA = new Faction(FactionRegistry.MORIA, true, DispositionType.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MoriaNpcDataPool.GOBLIN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MoriaNpcDataPool.MILITIA,
                            MoriaNpcDataPool.SCOUT
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MoriaNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MoriaNpcDataPool.RIDER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MoriaNpcDataPool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            MoriaNpcDataPool.CHIEF
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.SCREECHING_SKULL, DyeColor.RED)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of("moria",  "east_gate"), new Vector2d(1522, 1143)),
                        new SpawnData(MiddleEarth.of("moria",  "west_gate"), new Vector2d(1465, 1143)),
                        new SpawnData(MiddleEarth.of("moria",  "goblin_camp"), new Vector2d(1546, 1115))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WOODLAND_REALM, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );

    }
}
