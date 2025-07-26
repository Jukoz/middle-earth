package net.sevenstars.middleearth.resources.datas.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.ModBannerPatterns;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.LorienNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class LothlorienFactionPool {
    public final static Faction LOTHLORIEN;

    static {
        LOTHLORIEN = new Faction(FactionsME.getName(FactionsME.LOTHLORIEN), true, Disposition.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            LorienNpcDataPool.LOTHLORIEN_RANGER,
                            LorienNpcDataPool.LOTHLORIEN_RANGER_ARCHER,
                            LorienNpcDataPool.LOTHLORIEN_SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            LorienNpcDataPool.LOTHLORIEN_KNIGHT,
                            LorienNpcDataPool.LOTHLORIEN_KNIGHT_ARCHER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            LorienNpcDataPool.LOTHLORIEN_VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            LorienNpcDataPool.LOTHLORIEN_LORD
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.STAR_AND_LEAF, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien.cerin_amroth"), new Vector2d(1614, 1215))
                )), List.of(), List.of()
        );
    }

}
