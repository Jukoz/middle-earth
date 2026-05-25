package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.InitialDiplomacy;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.common.NpcRank;
import net.sevenstars.api.utils.IdentifierUtil;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class GondorFactionPool {
    public final static Faction GONDOR;
    private final static String FACTION_PATH = FactionRegistry.GONDOR.getValue().getPath();

    static {
        GONDOR = new Faction(FactionRegistry.GONDOR, true, DispositionType.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        GondorianNpcDataPool.PEASANT
                    ));
                    put(NpcRank.MILITIA, List.of(
                        GondorianNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        GondorianNpcDataPool.SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        GondorianNpcDataPool.KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                        GondorianNpcDataPool.VETERAN,
                        GondorianNpcDataPool.KING_GUARD,
                        GondorianNpcDataPool.CITADEL_GUARD,
                        GondorianNpcDataPool.FOUNTAIN_GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                            GondorianNpcDataPool.LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.TREE, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "minas_tirith"),  new Vector2d(1945, 1785)),
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "anorien"),  new Vector2d(1930, 1735)),
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "ithilien"),  new Vector2d(1975, 1700)), // Henneth Annun
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "lossarnach"),  new Vector2d(1895, 1792)), // Erui Source
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "pelargir"),  new Vector2d(1875, 1960)),
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "lamedon"),  new Vector2d(1625, 1800)), // Linhir
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "lebennin"),  new Vector2d(1715, 1955)), // Linhir
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "ringlo_vale"),  new Vector2d(1530, 1730)), // Calembel
                        new SpawnData(MiddleEarth.of(FACTION_PATH,  "dol_amroth"),  new Vector2d(1500, 1930))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.ALLY),
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
