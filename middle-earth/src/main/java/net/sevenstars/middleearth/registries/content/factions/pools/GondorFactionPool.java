package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class GondorFactionPool {
    public final static Faction GONDOR;

    static {
        GONDOR = new Faction(FactionRegistry.GONDOR, true, Disposition.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        GondorianNpcDataPool.GONDOR_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                        GondorianNpcDataPool.GONDOR_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        GondorianNpcDataPool.GONDOR_SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        GondorianNpcDataPool.GONDOR_KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                        GondorianNpcDataPool.GONDOR_VETERAN,
                        GondorianNpcDataPool.GONDOR_KING_GUARD,
                        GondorianNpcDataPool.GONDOR_CITADEL_GUARD,
                        GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                            GondorianNpcDataPool.GONDOR_LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.TREE, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.minas_tirith"),  new Vector2d(1945, 1785)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.anorien"),  new Vector2d(1930, 1735)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.ithilien"),  new Vector2d(1975, 1700)), // Henneth Annun
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.lossarnach"),  new Vector2d(1895, 1792)), // Erui Source
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.pelargir"),  new Vector2d(1875, 1960)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.lamedon"),  new Vector2d(1625, 1800)), // Linhir
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.lebennin"),  new Vector2d(1715, 1955)), // Linhir
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.ringlo_vale"),  new Vector2d(1530, 1730)), // Calembel
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.dol_amroth"),  new Vector2d(1500, 1930))
                )), List.of(), List.of(),
                List.of(FactionRegistry.ROHAN), List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.LONGBEARDS, FactionRegistry.DALE,
                FactionRegistry.SHIRE), List.of(FactionRegistry.ISENGARD, FactionRegistry.MORDOR, FactionRegistry.MISTY_MOUNTAINS_GOBLINS, FactionRegistry.BRIGAND)
        );
    }
}
