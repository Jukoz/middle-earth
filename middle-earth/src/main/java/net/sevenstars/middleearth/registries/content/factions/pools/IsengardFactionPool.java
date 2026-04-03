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
import net.sevenstars.middleearth.registries.content.npcs.pools.IsengardNpcDataPool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class IsengardFactionPool {
    public final static Faction ISENGARD;
    static {
        ISENGARD = new Faction(FactionRegistry.ISENGARD, true, DispositionType.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            IsengardNpcDataPool.SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            IsengardNpcDataPool.WARRIOR
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            IsengardNpcDataPool.URUK_HAI_SOLDIER,
                            IsengardNpcDataPool.URUK_HAI_SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            IsengardNpcDataPool.URUK_HAI_BERSERKER,
                            IsengardNpcDataPool.ORTHANC_GUARD
                    ));
                    put(NpcRank.VETERAN, List.of(
                            IsengardNpcDataPool.URUK_HAI_VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            IsengardNpcDataPool.URUK_HAI_LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.HAND, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "isengard.orthanc"), new Vector2d(1402, 1467))
                )), List.of(), List.of(),
                List.of(FactionRegistry.HOBGOBLIN_TRIBES, FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, FactionRegistry.MORDOR), List.of(),
                List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE, FactionRegistry.LONGBEARDS, FactionRegistry.DALE, FactionRegistry.BRIGAND)
        );
    }

}
