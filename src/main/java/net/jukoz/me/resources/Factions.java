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

public class Factions {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> FACTION_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        DynamicRegistries.registerSynced(FACTION_KEY, Faction.CODEC);
    }

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> factionRegistryEntryLookup = context.getRegistryLookup(FACTION_KEY);
        // Registering all factions
        registerDwarvenFactions(context, factionRegistryEntryLookup);
        registerElvenFactions(context, factionRegistryEntryLookup);
        registerMannishFactions(context, factionRegistryEntryLookup);
    }

    private static void registerElvenFactions(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup) {
        // region [LOTHLORIEN]
        String lothlorien = "lothlorien";
        register(context, factionRegistryEntryLookup, of(lothlorien),
                new Faction(lothlorien, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                new HashMap<>(){{
                                    put(Races.HUMAN, new FactionNpcPreviewData.PreviewData(
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
                                    put(Identifier.of(MiddleEarth.MOD_ID, lothlorien.concat(".cerin_amroth")), new Vector2i(1614, 1215));
                                }},
                                new HashMap<>()
                        ),
                        List.of(Races.HUMAN), List.of(), List.of()
                )
        );
    }

    private static void registerMannishFactions(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup) {
        // region [GONDOR]
        register(context, factionRegistryEntryLookup, of("gondor"),
                new Faction("gondor", Alignment.GOOD,
                        null, null, null, List.of(Races.HUMAN),null, null
                )
        );
        String gondorAnorien = "gondor.anorien";
        register(context, factionRegistryEntryLookup, of(gondorAnorien),
                new Faction(gondorAnorien, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                new HashMap<>(){{
                                    put(Races.HUMAN, new FactionNpcPreviewData.PreviewData(
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
                            )
                        ),
                        new SpawnDataHandler(new Vector2i(0,0),
                                new HashMap<>(){{
                                    put(Identifier.of(MiddleEarth.MOD_ID, gondorAnorien.concat(".minas_tirith")), new Vector2i(1940, 1785));
                                }},
                                new HashMap<>(){{
                                    put(Identifier.of(MiddleEarth.MOD_ID, gondorAnorien.concat(".outskirts")), new Vec3d(62500, 200, 57600));
                                }}
                        ),
                        List.of(Races.HUMAN), List.of(), List.of()
                )
        );

        String gondorLossarnach = "gondor.lossarnach";
        register(context, factionRegistryEntryLookup, of(gondorLossarnach),
                new Faction(gondorLossarnach, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                new HashMap<>(){{
                                    put(Races.HUMAN, new FactionNpcPreviewData.PreviewData(
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
                                    put(Identifier.of(MiddleEarth.MOD_ID, gondorLossarnach.concat(".erui_source")), new Vector2i(1890, 1785));
                                }},
                                new HashMap<>()
                        ),
                        List.of(Races.HUMAN), List.of(), List.of()
                )
        );
        // endregion

    }

    private static void registerDwarvenFactions(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup) {
        // region [LONGBEARDS]
        register(context, factionRegistryEntryLookup, of("longbeards"),
                new Faction("longbeards", Alignment.GOOD,
                        null, null, null, List.of(Races.DWARF),null, null
                )
        );
        String longbeardsErebor = "longbeards.erebor";
        register(context, factionRegistryEntryLookup, of(longbeardsErebor),
                new Faction(longbeardsErebor, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                new HashMap<>(){{
                                    put(Races.DWARF, new FactionNpcPreviewData.PreviewData(
                                            ModEquipmentItems.RAVENHILL_SENTINEL_HELMET,
                                            ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE,
                                            ModEquipmentItems.RAVENHILL_SENTINEL_LEGGINGS,
                                            ModEquipmentItems.RAVENHILL_SENTINEL_BOOTS,
                                            ModWeaponItems.EREBOR_NOBLE_SWORD,
                                            ModEquipmentItems.HEATER_SHIELD
                                    ));
                                    put(Races.HUMAN, new FactionNpcPreviewData.PreviewData(
                                            ModEquipmentItems.EREBOR_GATEWARDEN_HELMET,
                                            ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE,
                                            ModEquipmentItems.EREBOR_PLATE_LEGGINGS,
                                            ModEquipmentItems.EREBOR_PLATE_BOOTS,
                                            ModWeaponItems.EREBOR_NOBLE_AXE,
                                            Items.SHIELD
                                    ));
                                }}
                        ),
                        new BannerData(DyeColor.BLACK, List.of(
                                new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLUE),
                                new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                                )
                        ),
                        new SpawnDataHandler(new Vector2i(0,0),
                                new HashMap<>(){{
                                    put(Identifier.of(MiddleEarth.MOD_ID, longbeardsErebor.concat(".ravenhill")), new Vector2i(2017, 722));
                                }},
                                new HashMap<>(){{
                                    put(Identifier.of(MiddleEarth.MOD_ID, longbeardsErebor.concat(".outskirts")), new Vec3d(63400, 200, 23000));
                                }}
                        ),
                        List.of(Races.DWARF, Races.HUMAN), List.of(), List.of()
                )
        );
        // endregion
    }

    private static RegistryKey<Faction> of(String name) {
        return RegistryKey.of(FACTION_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    private static Faction register(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup,
                                 RegistryKey<Faction> factionRegistryKey, Faction faction) {
        String name = factionRegistryKey.getValue().getPath();
        RegistryKey<Faction> newFaction = RegistryKey.of(FACTION_KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<Faction>> optionalBiome = factionRegistryEntryLookup.getOptional(factionRegistryKey);
        optionalBiome.ifPresent(biomeReference -> context.register(newFaction,
               faction));

        return faction;
    }
}

