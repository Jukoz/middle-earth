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
import net.jukoz.me.resources.datas.faction.utils.SpawnsData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;
import org.joml.Vector2i;

import java.util.*;

public class ModFactionRegistry {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> FACTION_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));
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

    }

    private static void registerDwarvenFactions(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup) {
        register(context, factionRegistryEntryLookup, of("longbeards.erebor"),
                new Faction("longbeards.erebor", Alignment.GOOD,
                        new FactionNpcPreviewData(
                                ModEquipmentItems.RAVENHILL_SENTINEL_HELMET,
                                ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE,
                                ModEquipmentItems.RAVENHILL_SENTINEL_LEGGINGS,
                                ModEquipmentItems.RAVENHILL_SENTINEL_BOOTS,
                                ModWeaponItems.EREBOR_NOBLE_SWORD,
                                ModEquipmentItems.HEATER_SHIELD
                        ),
                        new BannerData(DyeColor.BLACK, new ArrayList<>(){
                            {
                                add(new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLUE));
                                add(new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.ORANGE));
                            }
                        }),
                        new SpawnsData(new Vector2i(0,0), new HashMap<>(){
                            {
                                put(Identifier.of(MiddleEarth.MOD_ID, "longbeards.erebor.spawn.a"), new Vector2i(64500, 23200));
                            }
                        }, new HashMap<>()),
                        new ArrayList<>(){
                            {
                                add(Race.Dwarf);
                            }
                        }
                )
        );
        register(context, factionRegistryEntryLookup, of("longbeards"),
                new Faction("longbeards", Alignment.GOOD,
                        new FactionNpcPreviewData(
                                ModEquipmentItems.RAVENHILL_SENTINEL_HELMET,
                                ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE,
                                ModEquipmentItems.RAVENHILL_SENTINEL_LEGGINGS,
                                ModEquipmentItems.RAVENHILL_SENTINEL_BOOTS,
                                ModWeaponItems.EREBOR_NOBLE_SWORD,
                                ModEquipmentItems.HEATER_SHIELD
                        ),
                        new BannerData(DyeColor.BLACK, new ArrayList<>(){
                            {
                                add(new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP.getValue(), DyeColor.BLUE));
                                add(new BannerData.BannerPatternWithColor(ModBannerPatterns.LONGBEARD_BANNER_PATTERN.getValue(), DyeColor.ORANGE));
                            }
                        }),
                        new SpawnsData(new Vector2i(0,0), new HashMap<>(), new HashMap<>()),
                        new ArrayList<>(){
                            {
                                add(Race.Dwarf);
                            }
                        }
                )
        );
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

