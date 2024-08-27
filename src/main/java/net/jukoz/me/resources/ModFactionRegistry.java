package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.Race;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.utils.BannerData;
import net.jukoz.me.resources.datas.faction.utils.FactionNpcPreviewData;
import net.jukoz.me.resources.datas.faction.utils.SpawnDataHandler;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

import java.util.*;

public class ModFactionRegistry {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> FACTION_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    static final String RACE_RESET_COMMAND = "/execute at <p> run function me:reset_race";
    static final String MAN_RACE_JOIN_COMMAND = "/execute at <p> run function me:join_man";
    static final String DWARF_RACE_JOIN_COMMAND = "/execute at <p> run function me:join_dwarf";
    static final String ELF_RACE_JOIN_COMMAND = "/execute at <p> run function me:join_elf";
    static final String HOBBIT_RACE_JOIN_COMMAND = "/execute at <p> run function me:join_hobbit";
    static final String ORC_RACE_JOIN_COMMAND = "/execute at <p> run function me:join_orc";
    static final String URUK_RACE_JOIN_COMMAND = "/execute at <p> run function me:join_uruk";


    private static HashMap<Identifier, Faction> factions;
    private static HashMap<Identifier, List<Faction>> temporaryFactions;
    public static void register(){
        DynamicRegistries.registerSynced(FACTION_KEY, Faction.CODEC);
    }

    public static void addFaction(Faction faction, Identifier id){
        if(factions == null)
            factions = new HashMap<>();
        if(temporaryFactions == null)
            temporaryFactions = new HashMap<>();

        String parentName = null;
        List<String> splittedId = Arrays.stream(id.getPath().split("\\.")).toList();
        if(splittedId.size() == 2){
            parentName = splittedId.get(0);
        }
        if(parentName != null){
            Identifier parentFactionId = Identifier.of(MiddleEarth.MOD_ID, parentName);
            Faction parentFaction = factions.get(parentFactionId);
            if(parentFaction != null){
                parentFaction.addSubfaction(faction);
            } else {
                List<Faction> subFactions = temporaryFactions.get(parentFactionId);
                if(subFactions == null){
                    subFactions = new ArrayList<>();
                } else {
                    temporaryFactions.remove(parentFactionId);
                }
                subFactions.add(faction);
                temporaryFactions.put(parentFactionId, subFactions);
            }
        } else if(factions.get(id) == null){ // Prevent overwrites

            List<Faction> subFactions = temporaryFactions.get(id);
            if(subFactions != null && !subFactions.isEmpty()){
                for(Faction subFaction : subFactions){
                    faction.addSubfaction(subFaction);
                }
                temporaryFactions.remove(id);
            }
            factions.put(faction.getId(), faction);
        }
    }

    public static HashMap<Identifier, Faction> getFactionsByAlignment(Alignment alignment){
        HashMap<Identifier, Faction> foundFactions = new HashMap<>();

        for(Faction faction : factions.values().stream().filter(x -> x.getAlignment() == alignment).toList()){
            foundFactions.put(faction.getId(), faction);
        }
        return foundFactions;
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
                                ModEquipmentItems.LORIEN_LEATHER_HELMET,
                                ModEquipmentItems.LORIEN_ARMING_COAT,
                                ModEquipmentItems.LORIEN_ARMING_SKIRT,
                                ModEquipmentItems.ELVEN_BOOTS,
                                ModWeaponItems.LORIEN_NOBLE_SPEAR,
                                ModEquipmentItems.LORIEN_MALLORN_SHIELD
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
                        List.of(Race.ELF),
                        List.of(ELF_RACE_JOIN_COMMAND),
                        List.of(RACE_RESET_COMMAND)
                )
        );
    }

    private static void registerMannishFactions(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup) {
        // region [GONDOR]
        register(context, factionRegistryEntryLookup, of("gondor"),
                new Faction("gondor", Alignment.GOOD,
                        null, null, null, List.of(Race.HUMAN),null, null
                )
        );
        String gondorAnorien = "gondor.anorien";
        register(context, factionRegistryEntryLookup, of(gondorAnorien),
                new Faction(gondorAnorien, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET,
                                ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE,
                                ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS,
                                ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS,
                                ModWeaponItems.GONDORIAN_NOBLE_SPEAR,
                                ModEquipmentItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD
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
                        List.of(Race.HUMAN),
                        List.of(MAN_RACE_JOIN_COMMAND),
                        List.of(RACE_RESET_COMMAND)
                )
        );

        String gondorLossarnach = "gondor.lossarnach";
        register(context, factionRegistryEntryLookup, of(gondorLossarnach),
                new Faction(gondorLossarnach, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET,
                                ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE,
                                ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS,
                                ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS,
                                ModWeaponItems.GONDORIAN_NOBLE_SWORD,
                                ModEquipmentItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD
                        ),
                        new BannerData(DyeColor.BLACK, List.of(
                                new BannerData.BannerPatternWithColor(BannerPatterns.BORDER.getValue(), DyeColor.LIGHT_GRAY),
                                new BannerData.BannerPatternWithColor(BannerPatterns.CIRCLE.getValue(), DyeColor.RED),
                                new BannerData.BannerPatternWithColor(ModBannerPatterns.GONDOR_BANNER_PATTERN.getValue(), DyeColor.WHITE)
                            )
                        ),
                        new SpawnDataHandler(new Vector2i(0,0),
                                new HashMap<>(){{
                                    put(Identifier.of(MiddleEarth.MOD_ID, gondorLossarnach.concat(".enui_source")), new Vector2i(1890, 1785));
                                }},
                                new HashMap<>()
                        ),
                        List.of(Race.HUMAN),
                        List.of(HOBBIT_RACE_JOIN_COMMAND),
                        List.of(RACE_RESET_COMMAND)
                )
        );
        // endregion

    }

    private static void registerDwarvenFactions(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup) {
        // region [LONGBEARDS]
        register(context, factionRegistryEntryLookup, of("longbeards"),
                new Faction("longbeards", Alignment.GOOD,
                        null, null, null, List.of(Race.DWARF),null, null
                )
        );
        String longbeardsErebor = "longbeards.erebor";
        register(context, factionRegistryEntryLookup, of(longbeardsErebor),
                new Faction(longbeardsErebor, Alignment.GOOD,
                        new FactionNpcPreviewData(
                                ModEquipmentItems.RAVENHILL_SENTINEL_HELMET,
                                ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE,
                                ModEquipmentItems.RAVENHILL_SENTINEL_LEGGINGS,
                                ModEquipmentItems.RAVENHILL_SENTINEL_BOOTS,
                                ModWeaponItems.EREBOR_NOBLE_SWORD,
                                ModEquipmentItems.HEATER_SHIELD
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
                        List.of(Race.DWARF),
                        List.of(DWARF_RACE_JOIN_COMMAND),
                        List.of(RACE_RESET_COMMAND)
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

    public static Faction findFactionById(Identifier factionId) {
        List<String> splittedId = Arrays.stream(factionId.getPath().split("\\.")).toList();
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, splittedId.get(0));
        Faction foundFaction = factions.get(id);


        if(foundFaction != null && splittedId.size() == 2){
            foundFaction = foundFaction.findSubfaction(factionId);
        }
        return foundFaction;
    }

    public static List<Identifier> getAllJoinableFactionId() {
        List<Identifier> factionIds = new ArrayList<>();
        for(Faction faction : factions.values()) {
            HashMap<Identifier, Faction> subFactions = faction.getSubFactions();
            if(subFactions == null)
                factionIds.add(faction.getId());
            else{
                List<Faction> subFacs = subFactions.values().stream().toList();
                for(Faction subFac : subFacs) {
                    factionIds.add(subFac.getId());
                }
            }
        }
        return factionIds;
    }
}

