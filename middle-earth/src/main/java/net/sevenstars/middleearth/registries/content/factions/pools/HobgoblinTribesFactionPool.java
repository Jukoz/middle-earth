package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.pools.GundabadNpcDataPool;
import net.sevenstars.middleearth.resources.datas.DispositionType;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class HobgoblinTribesFactionPool {
    public final static Faction HOBGOBLIN_TRIBES;
    public final static Faction GUNDABAD;
    private final static String FACTION_PATH = FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD.getValue().getPath();

    static {
        HOBGOBLIN_TRIBES = new Faction(FactionRegistry.HOBGOBLIN_TRIBES, true, DispositionType.EVIL, FactionType.FACTION, null,
                List.of(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD.getValue()),
                null, null, null, List.of(), List.of(), List.of(), List.of(), List.of());

        GUNDABAD = new Faction(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, true, DispositionType.EVIL, FactionType.SUBFACTION, HOBGOBLIN_TRIBES.getId(),null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            GundabadNpcDataPool.SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            GundabadNpcDataPool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            GundabadNpcDataPool.SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            GundabadNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.VETERAN, List.of(
                            GundabadNpcDataPool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            GundabadNpcDataPool.LEADER
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
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "gundabad"), new Vector2d(1595, 640)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "grey_mountains"), new Vector2d(1652, 640)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "goblin_town"), new Vector2d(1581.5, 874.5)),
                        new SpawnData(IdentifierUtil.buildAggregate(FACTION_PATH, "moria"), new Vector2d(1521, 1138))
                )), List.of(), List.of(),
                List.of(FactionRegistry.ISENGARD), List.of(FactionRegistry.MORDOR),
                List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE, FactionRegistry.LONGBEARDS, FactionRegistry.DALE, FactionRegistry.BRIGAND)
        );

    }
}
