package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.ModBannerPatterns;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.pools.*;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Factions for " + MiddleEarth.MOD_ID);
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
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.TREE, DyeColor.WHITE)
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
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.HORSE_HEAD, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.edoras"), new Vector2d(1525, 1600)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "rohan.helms_deep"), new Vector2d(1470, 1555)), // Westfold
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
                            MiddleEarthNpcs.HUMAN_CIVILIAN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            DalishNpcDataPool.DALE_MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            DalishNpcDataPool.DALE_SOLDIER,
                            DalishNpcDataPool.DALE_SOLDIER_ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            DalishNpcDataPool.DALE_KNIGHT,
                            DalishNpcDataPool.DALE_KNIGHT_ARCHER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            DalishNpcDataPool.DALE_VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            DalishNpcDataPool.DALE_SERGEANT
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.BELL, DyeColor.YELLOW)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "dale.capital"), new Vector2d(2021, 727)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, "dale.esgaroth"), new Vector2d(2007, 757))
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
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.DWARF_CROWN, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.ravenhill")), new Vector2d(2017, 722)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills")), new Vector2d(2355, 725)),
                        new SpawnData(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills_spring")), new Vector2d(2262, 782))
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
        // endregion
        //region [MORDOR]
        MORDOR = new Faction("mordor", true, Disposition.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            MordorNpcDataPool.MORDOR_ORC_SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_NUMENOREAN,
                            MordorNpcDataPool.MORDOR_ORC_SNAGA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            MordorNpcDataPool.MORDOR_ORC_MILITIA,
                            MordorNpcDataPool.MORDOR_ORC_SCOUT,
                            MordorNpcDataPool.MORDOR_ORC_SOLDIER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_URUK_SOLDIER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_URUK_VETERAN,
                            MordorNpcDataPool.MORDOR_BLACK_URUK_VETERAN_ARCHER
                    ));
                    put(NpcRank.LEADER, List.of(
                            MordorNpcDataPool.MORDOR_BLACK_URUK_LEADER
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.SMALL_CIRCLE, DyeColor.ORANGE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.EYE_OF_SAURON, DyeColor.RED)
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
        // endregion
        // region [ISENGARD]
        ISENGARD = new Faction("isengard", true, Disposition.EVIL, FactionType.FACTION, null,null,
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
                new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.GRAY),
                new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                new BannerData.BannerPatternWithColor(ModBannerPatterns.HAND, DyeColor.WHITE)
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
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                    put(NpcRank.VETERAN, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                    put(NpcRank.LEADER, List.of(
                            ShireNpcDataPool.SHIRE_SHIRRIFF
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.CLOTH, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.LIME),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE, DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.PIPE, DyeColor.BROWN)
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
                            BanditNpcDataPool.BANDIT_MERCENARY,
                            BanditNpcDataPool.WILD_GOBLIN_GATHERER,
                            BanditNpcDataPool.WILD_GOBLIN_WARRIOR,
                            BanditNpcDataPool.WILD_GOBLIN_SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            BanditNpcDataPool.BANDIT_CHIEFTAIN
                    ));
                    put(NpcRank.VETERAN, List.of(
                            BanditNpcDataPool.BANDIT_CHIEFTAIN
                    ));
                    put(NpcRank.LEADER, List.of(
                            BanditNpcDataPool.BANDIT_CHIEFTAIN
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CROSS, DyeColor.RED),
                        new BannerData.BannerPatternWithColor(BannerPatterns.SKULL, DyeColor.WHITE)
                )),
                null , List.of(), List.of()
        );
        //endregion

    }
}

