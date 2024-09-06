package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.FactionNpcPreviewData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

import java.util.*;

public class MiddleEarthFactions {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> FACTION_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    // [DWARVEN]
    public final static Faction LONGBEARDS;
    public final static Faction LONGBEARDS_EREBOR;

    // [ELVEN]
    public final static Faction LOTHLORIEN;

    // [MEN]
    public final static Faction GONDOR;
    public final static Faction GONDOR_ANORIEN;
    public final static Faction GONDOR_LOSSARNARCH;

    public static void register(){
        DynamicRegistries.registerSynced(FACTION_KEY, Faction.CODEC);
    }

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> factionRegistryEntryLookup = context.getRegistryLookup(FACTION_KEY);
        // Registering all factions

        // [DWARVEN]
        register(context, factionRegistryEntryLookup, LONGBEARDS);
        register(context, factionRegistryEntryLookup, LONGBEARDS_EREBOR);

        // [ELVEN]
        register(context, factionRegistryEntryLookup, LOTHLORIEN);

        // [MEN]
        register(context, factionRegistryEntryLookup, GONDOR);
        register(context, factionRegistryEntryLookup, GONDOR_ANORIEN);
        register(context, factionRegistryEntryLookup, GONDOR_LOSSARNARCH);
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
        // region [DWARVEN]
        LONGBEARDS = new Faction("longbeards", Alignment.GOOD, null, null, null,
                List.of(MiddleEarthRaces.DWARF),null, null);

        LONGBEARDS_EREBOR = new Faction(LONGBEARDS.getName().concat(".erebor"), Alignment.GOOD,
                new FactionNpcPreviewData(
                        new HashMap<>(){{
                            put(MiddleEarthRaces.DWARF, new FactionNpcPreviewData.PreviewData(
                                    ModEquipmentItems.RAVENHILL_SENTINEL_HELMET,
                                    ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE,
                                    ModEquipmentItems.RAVENHILL_SENTINEL_LEGGINGS,
                                    ModEquipmentItems.RAVENHILL_SENTINEL_BOOTS,
                                    ModWeaponItems.EREBOR_NOBLE_SWORD,
                                    ModEquipmentItems.HEATER_SHIELD
                            ));
                            put(MiddleEarthRaces.HUMAN, new FactionNpcPreviewData.PreviewData(
                                    ModEquipmentItems.EREBOR_GATEWARDEN_HELMET,
                                    ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE,
                                    ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS,
                                    ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS,
                                    ModWeaponItems.EREBOR_NOBLE_AXE,
                                    Items.SHIELD
                            ));
                        }}
                ),
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.ravenhill")), new Vector2i(2017, 722));
                        }},
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, LONGBEARDS.getName().concat(".erebor.outskirts")), new Vec3d(63400, 200, 23000));
                        }}
                ),
                List.of(MiddleEarthRaces.DWARF, MiddleEarthRaces.HUMAN), List.of(), List.of()
        );
        // endregion

        // region [ELVEN]
        LOTHLORIEN = new Faction("lothlorien", Alignment.GOOD,
                new FactionNpcPreviewData(
                        new HashMap<>(){{
                            put(MiddleEarthRaces.HUMAN, new FactionNpcPreviewData.PreviewData(
                                    ModEquipmentItems.LORIEN_LEATHER_HELMET,
                                    ModEquipmentItems.LORIEN_ARMING_COAT,
                                    ModEquipmentItems.LORIEN_ARMING_SKIRT,
                                    ModEquipmentItems.ELVEN_BOOTS,
                                    ModWeaponItems.LORIEN_NOBLE_SPEAR,
                                    ModEquipmentItems.LORIEN_MALLORN_SHIELD
                            ));
                        }}
                ),
                new BannerData(DyeColor.CYAN, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.WHITE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.WHITE),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.LOTHLORIEN_BANNER_PATTERN.getValue(), DyeColor.YELLOW)
                )
                ),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "lothlorien_cerin_amroth"), new Vector2i(1614, 1215));
                        }},
                        new HashMap<>()
                ),
                List.of(MiddleEarthRaces.HUMAN), List.of(), List.of()
        );
        // endregion

        // region [MEN]
        GONDOR = new Faction("gondor", Alignment.GOOD, null, null, null,
                List.of(MiddleEarthRaces.HUMAN),null, null);

        GONDOR_ANORIEN = new Faction(GONDOR.getName().concat(".anorien"), Alignment.GOOD,
                new FactionNpcPreviewData(
                        new HashMap<>(){{
                            put(MiddleEarthRaces.HUMAN, new FactionNpcPreviewData.PreviewData(
                                    ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET,
                                    ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE,
                                    ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS,
                                    ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS,
                                    ModWeaponItems.GONDORIAN_NOBLE_SPEAR,
                                    ModEquipmentItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD
                            ));
                        }}
                ),
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.LIGHT_GRAY),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, GONDOR.getName().concat(".anorien.minas_tirith")), new Vector2i(1940, 1785));
                        }},
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, GONDOR.getName().concat(".anorien.outskirts")), new Vec3d(62500, 200, 57600));
                        }}
                ),
                List.of(MiddleEarthRaces.HUMAN), List.of(), List.of()
        );

        GONDOR_LOSSARNARCH = new Faction("gondor.lossarnach", Alignment.GOOD,
                new FactionNpcPreviewData(
                        new HashMap<>(){{
                            put(MiddleEarthRaces.HUMAN, new FactionNpcPreviewData.PreviewData(
                                    ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET,
                                    ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE,
                                    ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS,
                                    ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS,
                                    ModWeaponItems.GONDORIAN_NOBLE_SWORD,
                                    ModEquipmentItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD
                            ));
                        }}
                ),
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.LIGHT_GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.RED),
                        new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                )
                ),
                new SpawnDataHandler(new Vector2i(0,0),
                        new HashMap<>(){{
                            put(Identifier.of(MiddleEarth.MOD_ID, "gondor.lossarnach.erui_source"), new Vector2i(1890, 1785));
                        }},
                        new HashMap<>()
                ),
                List.of(MiddleEarthRaces.HUMAN), List.of(), List.of()
        );
        // endregion
    }
}

