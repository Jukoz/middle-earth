package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
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
    public final static Faction GONDOR_ANORIEN;
    public final static Faction GONDOR_LOSSARNARCH;
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
    // TODO
    // [ISENGARD]
    public final static Faction ISENGARD;

    public static void register(){
        DynamicRegistries.registerSynced(FACTION_KEY, Faction.CODEC);
    }

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> factionRegistryEntryLookup = context.getRegistryLookup(FACTION_KEY);
        // [GONDOR]
        register(context, factionRegistryEntryLookup, GONDOR);
        register(context, factionRegistryEntryLookup, GONDOR_ANORIEN);
        register(context, factionRegistryEntryLookup, GONDOR_LOSSARNARCH);
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
        // [ISENGARD]
        register(context, factionRegistryEntryLookup, ISENGARD);
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
        GONDOR = new Faction("gondor", Alignment.GOOD, null, null, null,
                null,null);

        GONDOR_ANORIEN = new Faction(GONDOR.getName().concat(".anorien"), Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HUMAN, new NpcPreview(
                            ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET,
                            ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE,
                            ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS,
                            ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS,
                            ModWeaponItems.GONDORIAN_NOBLE_SPEAR,
                            ModEquipmentItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.LIGHT_GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, GONDOR.getName().concat(".anorien.minas_tirith")), new Vector2d(1940, 1785));
                        }},
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, GONDOR.getName().concat(".anorien.outskirts")), new Vec3d(62500, 200, 57600));
                        }}
                ), List.of(), List.of()
        );

        GONDOR_LOSSARNARCH = new Faction(GONDOR.getName().concat(".lossarnach"), Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HUMAN, new NpcPreview(
                            ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET,
                            ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE,
                            ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS,
                            ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS,
                            ModWeaponItems.GONDORIAN_NOBLE_SWORD,
                            ModEquipmentItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.LIGHT_GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.RED),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )
                ),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, GONDOR.getName().concat(".lossarnach.erui_source")), new Vector2d(1890, 1785));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        // endregion
        // region [ROHAN]
        ROHAN = new Faction("rohan", Alignment.GOOD,
                new HashMap<>(){{
                    put(MiddleEarthRaces.HUMAN, new NpcPreview(
                            ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET,
                            ModEquipmentItems.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK,
                            ModEquipmentItems.ROHIRRIC_REINFORCED_COAT,
                            ModEquipmentItems.STURDY_BOOTS,
                            ModWeaponItems.ROHIRRIC_NOBLE_SPEAR,
                            ModEquipmentItems.ROHIRRIC_BUCKING_HORSE_SHIELD
                    ));
                }},
                new BannerData(DyeColor.GREEN, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.WHITE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.ROHAN_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "rohan.edoras"), new Vector2d(1745, 1695));
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
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.CROSS.getValue(), DyeColor.RED)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "dale.capital"), new Vector2d(2021, 727));
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
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.ravenhill")), new Vector2d(2017, 722));
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
                            ModEquipmentItems.LORIEN_LEATHER_HELMET,
                            ModEquipmentItems.LORIEN_ARMING_COAT,
                            ModEquipmentItems.LORIEN_ARMING_SKIRT,
                            ModEquipmentItems.ELVEN_BOOTS,
                            ModWeaponItems.LORIEN_NOBLE_SPEAR,
                            ModEquipmentItems.LORIEN_MALLORN_SHIELD
                    ));
                }},
                new BannerData(DyeColor.CYAN, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.WHITE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.WHITE),
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
        // TODO : In progress
        MORDOR = new Faction("mordor", Alignment.EVIL,
                new HashMap<>(){{
                    put(MiddleEarthRaces.ORC, new NpcPreview(
                            ModEquipmentItems.EREBOR_GATEWARDEN_HELMET,
                            ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE,
                            ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS,
                            ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS,
                            ModWeaponItems.EREBOR_NOBLE_AXE,
                            ModEquipmentItems.HEATER_SHIELD
                    ));
                    put(MiddleEarthRaces.URUK, new NpcPreview(
                            ModEquipmentItems.EREBOR_GATEWARDEN_HELMET,
                            ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE,
                            ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS,
                            ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS,
                            ModWeaponItems.EREBOR_NOBLE_AXE,
                            ModEquipmentItems.HEATER_SHIELD
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_gate"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.dol_goldur"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.minas_morgul"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID,"mordor.nurn"), new Vector2d(2017, 722));
                            put(Identifier.of(MiddleEarth.MOD_ID, "mordor.plateau"), new Vector2d(2017, 722));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );

        //endregion
        // region [ISENGARD]
        ISENGARD = new Faction("isengard", Alignment.EVIL,
                new HashMap<>(){{
                    put(MiddleEarthRaces.ORC, new NpcPreview(
                            ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP,
                            ModEquipmentItems.ORC_MAIL_HAUBERK,
                            null,
                            ModEquipmentItems.STURDY_BOOTS,
                            ModWeaponItems.SLAG_FALCHION,
                            ModWeaponItems.SLAG_DAGGER
                    ));
                    put(MiddleEarthRaces.URUK, new NpcPreview(
                            ModEquipmentItems.URUK_HAI_BERSERKER_PAINTED_HELMET,
                            ModEquipmentItems.URUK_HAI_PLATE_CHESTPLATE,
                            ModEquipmentItems.URUK_HAI_PLATE_LEGGINGS,
                            ModEquipmentItems.URUK_HAI_PLATE_BOOTS,
                            ModWeaponItems.URUK_HAI_AXE,
                            ModEquipmentItems.URUK_HAI_HEATER_SHIELD
                    ));
                    // TODO : add humans? No proper assets for them
                }},
                new BannerData(DyeColor.BROWN, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT.getValue(), DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.RED),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.ISENGARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "isengard.orthanc"), new Vector2d(1405, 1464));
                        }},
                        new HashMap<>()
                ), List.of(), List.of()
        );
        // endregion
    }
}

