package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.npcs.data.NpcRank;
import net.jukoz.me.resources.datas.npcs.pools.*;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.*;

public class MiddleEarthFactions {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> FACTION_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    // [GONDOR]
    public final static Faction GONDOR;
    // [ROHAN]
    public final static Faction ROHAN;
    // [DALE]
    public final static Faction DALE;
    // [LONGBEARDS]
    public final static Faction LONGBEARDS;
    public final static Faction LONGBEARDS_EREBOR;
    // [LOTHLORIEN]
    public final static Faction LOTHLORIEN;
    // [MORDOR]
    public final static Faction MORDOR;
    // [MISTY MOUNTAINS ORCS]
    public final static Faction MISTY_MOUNTAINS_GOBLINS;
    // [ISENGARD]
    public final static Faction ISENGARD;
    // [SHIRE]
    public final static Faction SHIRE;
    // [BANDIT]
    public final static Faction BANDIT;
    public static void register(){
        LoggerUtil.logDebugMsg("Registering Dynamic Factions for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(FACTION_KEY, Faction.CODEC);
    }

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> factionRegistryEntryLookup = context.getRegistryLookup(FACTION_KEY);
        // [GONDOR]
        register(context, factionRegistryEntryLookup, GONDOR);
        // [ROHAN]
        register(context, factionRegistryEntryLookup, ROHAN);
        // [DALE]
        register(context, factionRegistryEntryLookup, DALE);
        // [LONGBEARDS]
        register(context, factionRegistryEntryLookup, LONGBEARDS);
        register(context, factionRegistryEntryLookup, LONGBEARDS_EREBOR);
        // [LOTHLORIEN]
        register(context, factionRegistryEntryLookup, LOTHLORIEN);
        // [MORDOR]
        register(context, factionRegistryEntryLookup, MORDOR);
        // [MISTY MOUNTAINS GOBLINS]
        register(context, factionRegistryEntryLookup, MISTY_MOUNTAINS_GOBLINS);
        // [ISENGARD]
        register(context, factionRegistryEntryLookup, ISENGARD);
        // [SHIRE]
        register(context, factionRegistryEntryLookup, SHIRE);
        // [BANDIT]
        register(context, factionRegistryEntryLookup, BANDIT);
    }
    private static Faction register(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup, Faction faction) {
        RegistryKey<Faction> factionRegistryKey = of(faction.getName());
        String name = factionRegistryKey.getValue().getPath();
        RegistryKey<Faction> factionKey = RegistryKey.of(FACTION_KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<Faction>> optionalFaction = factionRegistryEntryLookup.getOptional(factionRegistryKey);
        optionalFaction.ifPresent(biomeReference -> context.register(factionKey, faction));

        return faction;
    }

    private static RegistryKey<Faction> of(String name) {
        return RegistryKey.of(FACTION_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        // region [GONDOR]
        GONDOR = new Faction("gondor", true, Disposition.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MiddleEarthNpcs.HUMAN_CIVILIAN
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
                            GondorianNpcDataPool.GONDOR_KING_GUARDS,
                            GondorianNpcDataPool.GONDOR_CITADEL_GUARDS,
                            GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARDS
                    ));
                    put(NpcRank.LEADER, List.of(
                            GondorianNpcDataPool.GONDOR_LEADER
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.minas_tirith"),  new Vector2d(1940, 1785)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.anorien"),  new Vector2d(1930, 1735)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.ithilien"),  new Vector2d(1975, 1700)), // Henneth Annun
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.lossarnach"),  new Vector2d(1890, 1785)), // Erui Source
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.pelargir"),  new Vector2d(1875, 1960)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.pelargir"),  new Vector2d(1875, 1960)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.lebennin"),  new Vector2d(1715, 1955)), // Linhir
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.ringlo_vale"),  new Vector2d(1625, 1800)), // Calembel
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.dol_amroth"),  new Vector2d(1500, 1930)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "gondor.dol_amroth"),  new Vector2d(1530, 1730)) // Erech
                    )), List.of(), List.of()
        );
        // endregion
        // region [ROHAN]
        ROHAN = new Faction("rohan", true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MiddleEarthNpcs.HUMAN_CIVILIAN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            RohirricNpcDataPool.ROHAN_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            RohirricNpcDataPool.ROHAN_SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            RohirricNpcDataPool.ROHAN_KNIGHT
                    ));
                    put(NpcRank.VETERAN, List.of(
                            RohirricNpcDataPool.ROHAN_KNIGHT,
                            RohirricNpcDataPool.ROHAN_ROYAL_GUARD
                    ));
                    put(NpcRank.LEADER, List.of(
                            RohirricNpcDataPool.ROHAN_HORSE_LORD,
                            RohirricNpcDataPool.ROHAN_EORLING_MARSHAL
                    ));
                }},
                new BannerData(DyeColor.GREEN, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.ROHAN_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.edoras"), new Vector2d(1745, 1695)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.helms_deep"), new Vector2d(1485, 1570)), // Westfold
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.westemnet"), new Vector2d(1525, 1525)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.aldburg"), new Vector2d(1600, 1660)), // Eastfold
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.eastemnet"), new Vector2d(1715, 1575)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.the_wold"), new Vector2d(1675, 1475))
                )), List.of(), List.of()
        );
        //endregion
        //region [DALE]
        DALE = new Faction("dale", true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                }},
                new BannerData(DyeColor.LIGHT_BLUE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.YELLOW)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "dale.capital"), new Vector2d(2021, 727)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "dale.esgaroth"), new Vector2d(2010, 760))
                )), List.of(), List.of()
        );
        //endregion
        // region [LONGBEARDS]
        LONGBEARDS = new Faction("longbeards", true, Disposition.GOOD, FactionType.FACTION, null,
                List.of(Identifier.of(MiddleEarth.MOD_ID, "longbeards.erebor")),
                null, null, null, List.of(), List.of());

        LONGBEARDS_EREBOR = new Faction(LONGBEARDS.getName().concat(".erebor"), true, Disposition.GOOD, FactionType.SUBFACTION, LONGBEARDS.getId(),null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            EreborNpcDataPool.EREBOR_MILITIA
                    ));
                }},
                new BannerData(DyeColor.BLUE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.ravenhill")), new Vector2d(2017, 722)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills")), new Vector2d(2355, 725)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills_spring")), new Vector2d(2262, 782)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.outskirts")), new Vec3d(63400, 200, 23000))
                )), List.of(), List.of()
        );


        // endregion
        // region [LOTHLORIEN]
        LOTHLORIEN = new Faction("lothlorien", true, Disposition.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            LorienNpcDataPool.LOTHLORIEN_MILITIA
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LOTHLORIEN_BANNER_PATTERN.getValue(), DyeColor.YELLOW)
                )
                ),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien.cerin_amroth"), new Vector2d(1614, 1215))
                )), List.of(), List.of()
        );
        // endregion
        //region [MORDOR]
        MORDOR = new Faction("mordor", true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.MORDOR_GREAT_EYE_BANNER_PATTERN.getValue(), DyeColor.RED)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.gorgoroth"), new Vector2d(2161, 1717)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_gates"), new Vector2d(2010, 1608)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.minas_morgul"), new Vector2d(2029, 1770)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.nurn"), new Vector2d(2345, 1915)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "mordor.dol_guldur"), new Vector2d(1793, 1210))
                )), List.of(), List.of()
        );
        //endregion
        // region [MISTY MOUNTAINS GOBLINS]
        MISTY_MOUNTAINS_GOBLINS = new Faction("misty_mountains_goblins", true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA,
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA,
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA,
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA,
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA,
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA,
                            MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA
                    ));
                }},
                new BannerData(DyeColor.BROWN, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.MISTY_MOUNTAINS_ORCS_PEAKS_BANNER_PATTERN.getValue(), DyeColor.RED)
                )),
                new SpawnDataHandler(List.of(
                    new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.gundabad"), new Vector2d(1595, 640)),
                    new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.grey_mountains"), new Vector2d(1652, 640)),
                    new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.goblin_town"), new Vector2d(1581.5, 874.5)),
                    new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.moria"), new Vector2d(1521, 1138))
                )), List.of(), List.of()
        );
        // endregion
        // region [ISENGARD]
        ISENGARD = new Faction("isengard", true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_MILITIA,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_MILITIA,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_MILITIA,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_MILITIA,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_MILITIA,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            IsengardNpcDataPool.ISENGARD_ORC_MILITIA,
                            IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA
                    ));
                }},
            // TODO : add humans? No proper assets for them
            new BannerData(DyeColor.BLACK, List.of(
                    new BannerData.BannerPatternWithColor(ModBannerPatterns.ISENGARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
            )),
            new SpawnDataHandler(List.of(
                    new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "isengard.orthanc"), new Vector2d(1402, 1467))
            )), List.of(), List.of()
        );
        // endregion
        //region [SHIRE]
        SHIRE = new Faction("shire", true, Disposition.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MiddleEarthNpcs.HOBBIT_CIVILIAN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            ShireNpcDataPool.SHIRE_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            ShireNpcDataPool.SHIRE_MILITIA
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            ShireNpcDataPool.SHIRE_MILITIA
                    ));
                    put(NpcRank.VETERAN, List.of(
                            ShireNpcDataPool.SHIRE_MILITIA
                    ));
                    put(NpcRank.LEADER, List.of(
                            ShireNpcDataPool.SHIRE_MILITIA
                    ));
                }},
                new BannerData(DyeColor.YELLOW, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.YELLOW)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "shire.hobbiton"), new Vector2d(933, 900)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "shire.willowbottom"), new Vector2d(981, 970))
                )), List.of(), List.of()
        );
        //endregion
        //region [BANDITS]
        BANDIT = new Faction("bandit", false, Disposition.NEUTRAL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            BanditNpcDataPool.BANDIT_THUG
                    ));
                    put(NpcRank.MILITIA, List.of(
                            BanditNpcDataPool.BANDIT_THUG,
                            BanditNpcDataPool.BANDIT_THIEF
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            BanditNpcDataPool.BANDIT_THUG
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            BanditNpcDataPool.BANDIT_THUG
                    ));
                    put(NpcRank.VETERAN, List.of(
                            BanditNpcDataPool.BANDIT_THUG
                    ));
                    put(NpcRank.LEADER, List.of(
                            BanditNpcDataPool.BANDIT_THUG
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CROSS.getValue(), DyeColor.RED),
                        new BannerData.BannerPatternWithColor(BannerPatterns.SKULL.getValue(), DyeColor.WHITE)
                )),
                null , List.of(), List.of()
        );
        //endregion

    }
}

