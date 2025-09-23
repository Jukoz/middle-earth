package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.EreborNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class LongbeardsFactionPool {
    public final static Faction LONGBEARDS;
    public final static Faction EREBOR;

    static {
        LONGBEARDS = new Faction(FactionsME.getName(FactionsME.LONGBEARDS), true, Disposition.GOOD, FactionType.FACTION, null,
                List.of(FactionsME.LONGBEARDS_EREBOR.getValue()),
                null, null, null, List.of(), List.of());

        EREBOR = new Faction(FactionsME.getName(FactionsME.LONGBEARDS_EREBOR), true, Disposition.GOOD, FactionType.SUBFACTION, LONGBEARDS.getId(),null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            EreborNpcDataPool.EREBOR_MINER
                    ));
                    put(NpcRank.MILITIA, List.of(
                            EreborNpcDataPool.EREBOR_MINER,
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            EreborNpcDataPool.EREBOR_SOLDIER,
                            EreborNpcDataPool.EREBOR_ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            EreborNpcDataPool.EREBOR_ELITE
                    ));
                    put(NpcRank.VETERAN, List.of(
                            EreborNpcDataPool.EREBOR_VETERAN,
                            EreborNpcDataPool.EREBOR_GATEWARDEN
                    ));
                    put(NpcRank.LEADER, List.of(
                            EreborNpcDataPool.EREBOR_LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.DWARF_CROWN, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.ravenhill")), new Vector2d(2017, 722)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills")), new Vector2d(2355, 725)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills_spring")), new Vector2d(2262, 782))
                )), List.of(), List.of()
        );

    }
}
