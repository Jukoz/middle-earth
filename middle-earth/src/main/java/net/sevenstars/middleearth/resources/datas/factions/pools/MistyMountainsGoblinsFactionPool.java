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
import net.sevenstars.middleearth.resources.datas.npcs.pools.MistyMountainsGoblinsNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class MistyMountainsGoblinsFactionPool {
    public final static Faction MISTY_MOUNTAINS_GOBLINS;

    static {
        MISTY_MOUNTAINS_GOBLINS = new Faction(FactionsME.getName(FactionsME.MISTY_MOUNTAINS_GOBLINS), true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_SNAGA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_WARRIOR,
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_SOLDIER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.BROWN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.LIGHT_GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.TRIANGLES_BOTTOM, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.EVIL_EYE, DyeColor.RED)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.gundabad"), new Vector2d(1595, 640)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.grey_mountains"), new Vector2d(1652, 640)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.goblin_town"), new Vector2d(1581.5, 874.5)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.moria"), new Vector2d(1521, 1138))
                )), List.of(), List.of()
        );
    }

}
