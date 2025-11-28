package net.sevenstars.middleearth.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.BannerPatternsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
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
        ISENGARD = new Faction(FactionRegistry.ISENGARD, true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_SNAGA,
                            IsengardNpcDataPool.ISENGARD_ORTHANC_GUARD
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_SOLDIER,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_SCOUT
                    ));
                    put(NpcRank.VETERAN, List.of(
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_VETERAN,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_BERSERKER
                    ));
                    put(NpcRank.LEADER, List.of(
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_LEADER
                    ));
                }},
                // TODO : add humans? No proper assets for them
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.HAND, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "isengard.orthanc"), new Vector2d(1402, 1467))
                )), List.of(), List.of(),
                List.of(FactionRegistry.MISTY_MOUNTAINS_GOBLINS, FactionRegistry.MORDOR), List.of(),
                List.of(FactionRegistry.LOTHLORIEN, FactionRegistry.GONDOR, FactionRegistry.ROHAN, FactionRegistry.SHIRE, FactionRegistry.LONGBEARDS, FactionRegistry.DALE, FactionRegistry.BRIGAND)
        );
    }

}
