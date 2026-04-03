package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.registries.content.npcs.pools.EreborNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class LongbeardsFactionPool {
    public final static Faction LONGBEARDS;
    public final static Faction EREBOR;

    static {
        LONGBEARDS = new Faction(FactionRegistry.LONGBEARDS, true, DispositionType.GOOD, FactionType.FACTION, null,
                List.of(FactionRegistry.LONGBEARDS_EREBOR.getValue()),
                null, null, null, List.of(), List.of(), List.of(), List.of(), List.of());

        EREBOR = new Faction(FactionRegistry.LONGBEARDS_EREBOR, true, DispositionType.GOOD, FactionType.SUBFACTION, LONGBEARDS.getId(),null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            EreborNpcDataPool.PEASANT,
                            EreborNpcDataPool.MINER
                    ));
                    put(NpcRank.MILITIA, List.of(
                            EreborNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            EreborNpcDataPool.SOLDIER,
                            EreborNpcDataPool.ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            EreborNpcDataPool.ELITE
                    ));
                    put(NpcRank.VETERAN, List.of(
                            EreborNpcDataPool.VETERAN,
                            EreborNpcDataPool.GATEWARDEN
                    ));
                    put(NpcRank.LEADER, List.of(
                            EreborNpcDataPool.LEADER
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
                )), List.of(), List.of(),
                List.of(FactionRegistry.LONGBEARDS, FactionRegistry.DALE), List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE),
                List.of(FactionRegistry.ISENGARD, FactionRegistry.MORDOR, FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, FactionRegistry.BRIGAND)
        );

    }
}
