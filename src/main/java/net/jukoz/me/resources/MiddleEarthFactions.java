package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.ModDecorativeItems;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.NpcPreview;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;
import org.joml.Vector2i;

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

    public static void register(){
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
    }
    private static Faction register(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup, Faction faction) {
        RegistryKey<Faction> factionRegistryKey = of(faction.getName());
        String name = factionRegistryKey.getValue().getPath();
        RegistryKey<Faction> newFaction = RegistryKey.of(FACTION_KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<Faction>> optionalBiome = factionRegistryEntryLookup.getOptional(factionRegistryKey);
        optionalBiome.ifPresent(biomeReference -> context.register(newFaction,
               faction));

        return faction;
    }

    private static RegistryKey<Faction> of(String name) {
        return RegistryKey.of(FACTION_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        // region [GONDOR]
        GONDOR = new Faction("gondor", Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HUMAN, new NpcPreview(
                            ModEquipmentItems.GONDORIAN_PLATE_HELMET,
                            ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE,
                            ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS,
                            ModEquipmentItems.GONDORIAN_PLATE_BOOTS,
                            ModWeaponItems.GONDORIAN_NOBLE_SPEAR,
                            ModEquipmentItems.GONDORIAN_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.minas_tirith"), new Vector2d(1940, 1785));
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.anorien"), new Vector2d(62500, 1735));
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.ithilien"), new Vector2d(1975, 1700)); // Henneth Annun
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.lossarnach"), new Vector2d(1890, 1785)); // Erui Source
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.pelargir"), new Vector2d(1875, 1960));
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.lebennin"), new Vector2d(1715, 1955)); // Linhir
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.ringlo_vale"), new Vector2d(1625, 1800)); // Calembel
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.dol_amroth"), new Vector2d(1500, 1930));
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.morthond_vale"), new Vector2d(1530, 1730)); // Erech
                        }},
                        new HashMap<>(){
                        }
                ), List.of(), List.of()
        );
        // endregion
        // region [ROHAN]
        ROHAN = new Faction("rohan", Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HUMAN, new NpcPreview(
                            ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET,
                            ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK,
                            ModEquipmentItems.ROHIRRIC_SCALE_JACKET,
                            ModEquipmentItems.STURDY_BOOTS,
                            ModWeaponItems.ROHIRRIC_NOBLE_SPEAR,
                            ModEquipmentItems.ROHIRRIC_BUCKING_HORSE_SHIELD
                    ));
                }},
                new BannerData(DyeColor.GREEN, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.ROHAN_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.edoras"), new Vector2d(1745, 1695));
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.helms_deep"), new Vector2d(1485, 1570)); // Westfold
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.westemnet"), new Vector2d(1525, 1525));
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.aldburg"), new Vector2d(1600, 1660)); // Eastfold
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.eastemnet"), new Vector2d(1715, 1575));
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.the_wold"), new Vector2d(1675, 1475));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        //endregion
        //region [DALE]
        DALE = new Faction("dale", Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HUMAN, new NpcPreview(
                            ModEquipmentItems.DALISH_BURGONET,
                            ModEquipmentItems.DALISH_SCALE_HAUBERK,
                            ModEquipmentItems.DALISH_CHAIN_COAT,
                            ModEquipmentItems.DALISH_BOOTS,
                            ModWeaponItems.DALISH_SWORD,
                            ModEquipmentItems.ROUND_SHIELD
                    ));
                }},
                new BannerData(DyeColor.LIGHT_BLUE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.YELLOW)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "dale.capital"), new Vector2d(2021, 727));
                            put(Identifier.of(MiddleEarth.MOD_ID, "dale.esgaroth"), new Vector2d(2010, 760));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        //endregion
        // region [LONGBEARDS]
        LONGBEARDS = new Faction("longbeards", Alignment.GOOD, null, null, null,null, null);

        LONGBEARDS_EREBOR = new Faction(LONGBEARDS.getName().concat(".erebor"), Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.DWARF, new NpcPreview(
                            ModEquipmentItems.EREBOR_GATEWARDEN_HELMET,
                            ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE,
                            ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS,
                            ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS,
                            ModWeaponItems.EREBOR_NOBLE_AXE,
                            ModEquipmentItems.HEATER_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BLUE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.ravenhill")), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills")), new Vector2d(2355, 725));
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.iron_hills_spring")), new Vector2d(2262, 782));
                        }},
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.outskirts")), new Vec3d(63400, 200, 23000));
                        }}
                ), List.of(), List.of()
        );
        // endregion
        // region [LOTHLORIEN]
        LOTHLORIEN = new Faction("lothlorien", Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.ELF, new NpcPreview(
                            ModEquipmentItems.LORIEN_SOLDIER_HELMET,
                            ModEquipmentItems.LORIEN_SOLDIER_CHAIN_HAUBERK,
                            ModEquipmentItems.LORIEN_ARMING_SKIRT,
                            ModEquipmentItems.ELVEN_BOOTS,
                            ModWeaponItems.LORIEN_NOBLE_SPEAR,
                            ModEquipmentItems.LORIEN_MALLORN_SHIELD
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LOTHLORIEN_BANNER_PATTERN.getValue(), DyeColor.YELLOW)
                )
                ),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "lothlorien.cerin_amroth"), new Vector2d(1614, 1215));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        // endregion
        //region [MORDOR]
        MORDOR = new Faction("mordor", Alignment.EVIL,
                new HashMap<>(){{
                    put(MiddleEarthRaces.ORC, new NpcPreview(
                            ModEquipmentItems.MORDOR_ORC_OVERSIGHT_HELMET,
                            ModEquipmentItems.MORDOR_ORC_CHESTPLATE,
                            ModEquipmentItems.ORC_MAIL_COAT,
                            ModEquipmentItems.ORC_PLATE_BOOTS,
                            ModWeaponItems.SLAG_FALCHION,
                            ModEquipmentItems.MORDOR_SHIELD
                    ));
                    put(MiddleEarthRaces.URUK, new NpcPreview(
                            ModEquipmentItems.BLACK_URUK_PLATE_HELMET,
                            ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE,
                            ModEquipmentItems.BLACK_URUK_PLATE_LEGGINGS,
                            ModEquipmentItems.BLACK_URUK_PLATE_BOOTS,
                            ModWeaponItems.SLAG_SPEAR,
                            ModEquipmentItems.MORDOR_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.MORDOR_GREAT_EYE_BANNER_PATTERN.getValue(), DyeColor.RED)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.gorgoroth"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_gates"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.minas_morgul"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.nurn"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.dol_guldur"), new Vector2d(2017, 722));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        //endregion
        // region [MISTY MOUNTAINS GOBLINS]
        MISTY_MOUNTAINS_GOBLINS = new Faction("misty_mountains_goblins", Alignment.EVIL,
                new HashMap<>(){{
                    put(MiddleEarthRaces.ORC, new NpcPreview(
                            ModEquipmentItems.ORC_SALLET,
                            ModEquipmentItems.ORC_GORGET_HAUBERK,
                            ModEquipmentItems.ORC_MAIL_COAT,
                            ModEquipmentItems.ORC_PLATE_BOOTS,
                            ModWeaponItems.SLAG_FALCHION,
                            ModEquipmentItems.MISTY_MOUNTAINS_SHIELD
                    ));
                    put(MiddleEarthRaces.URUK, new NpcPreview(
                            ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET,
                            ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE,
                            ModEquipmentItems.GUNDABAD_HOBGOBLIN_CHAIN_COAT,
                            ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATED_BOOTS,
                            ModWeaponItems.SLAG_SPEAR,
                            ModEquipmentItems.MISTY_MOUNTAINS_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BROWN, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.MISTY_MOUNTAINS_ORCS_PEAKS_BANNER_PATTERN.getValue(), DyeColor.RED)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.gundabad"), new Vector2d(1595, 640));
                            put(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.grey_mountains"), new Vector2d(1652, 640));
                            put(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.goblin_town"), new Vector2d(1581.5, 874.5));
                            put(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.moria"), new Vector2d(1521, 1138));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        // endregion
        // region [ISENGARD]
        ISENGARD = new Faction("isengard", Alignment.EVIL,
                new HashMap<>(){{
                    put(MiddleEarthRaces.ORC, new NpcPreview(
                            ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP,
                            ModEquipmentItems.ORC_MAIL_HAUBERK,
                            null,
                            ModEquipmentItems.STURDY_BOOTS,
                            ModToolItems.SLAG_AXE,
                            ModEquipmentItems.URUK_HAI_HEATER_SHIELD
                    ));
                    put(MiddleEarthRaces.URUK, new NpcPreview(
                            ModEquipmentItems.URUK_HAI_PLATE_PAINTED_HELMET,
                            ModEquipmentItems.URUK_HAI_PLATE_CHESTPLATE,
                            ModEquipmentItems.URUK_HAI_PLATE_LEGGINGS,
                            ModEquipmentItems.URUK_HAI_PLATE_BOOTS,
                            ModWeaponItems.URUK_HAI_ELITE_SCIMITAR,
                            ModEquipmentItems.URUK_HAI_WHITE_HAND_SHIELD
                    ));
                    // TODO : add humans? No proper assets for them
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.ISENGARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "isengard.orthanc"), new Vector2d(1402, 1467));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        // endregion
        //region [SHIRE]
        SHIRE = new Faction("shire", Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HOBBIT, new NpcPreview(
                            ModEquipmentItems.STRAW_HAT,
                            null,
                            null,
                            null,
                            ModDecorativeBlocks.WOODEN_BUCKET.asItem(),
                            null
                    ));
                }},
                new BannerData(DyeColor.YELLOW, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.YELLOW),
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.YELLOW)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "shire.hobbiton"), new Vector2d(933, 900));
                            put(Identifier.of(MiddleEarth.MOD_ID, "shire.willowbottom"), new Vector2d(981, 970));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        //endregion
    }
}

