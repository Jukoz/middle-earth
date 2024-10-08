package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.datas.factions.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class MiddleEarthNpcs {
    public final static String PATH = "npcs";
    public static final RegistryKey<Registry<NpcData>> NPC_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    // [GONDOR]
    public final static NpcData GONDOR_MILITIA;
    public final static NpcData GONDOR_SOLDIER;
    // [ROHAN]
    public final static NpcData ROHAN_MILITIA;
    // [DALE]
    public final static NpcData DALE_MILITIA;
    // [LONGBEARDS]
    // [EREBOR]
    public final static NpcData EREBOR_MILITIA;
    // [LOTHLORIEN]
    public final static NpcData LOTHLORIEN_MILITIA;
    // [MORDOR]
    public final static NpcData MORDOR_ORC_MILITIA;
    public final static NpcData BLACK_URUK_SOLDIER;
    // [MISTY MOUNTAINS GOBLINS]
    public final static NpcData MISTY_GOBLIN_MILITIA;
    public final static NpcData MISTY_HOBGOBLIN_SOLDIER;
    // [ISENGARD]
    public final static NpcData ISENGARD_ORC_MILITIA;
    public final static NpcData URUK_HAI_SOLDIER;
    // [SHIRE]
    public final static NpcData HOBBIT_PEASANT;
    public static void register(){
        LoggerUtil.logDebugMsg("Registering Dynamic Npcs for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(NPC_KEY, NpcData.CODEC);
    }

    public static void bootstrap(Registerable<NpcData> context) {
        RegistryEntryLookup<NpcData> npcRegistryEntryLookup = context.getRegistryLookup(NPC_KEY);
        // [GONDOR]
        register(context, npcRegistryEntryLookup, GONDOR_MILITIA);
        register(context, npcRegistryEntryLookup, GONDOR_SOLDIER);
        // [ROHAN]
        register(context, npcRegistryEntryLookup, ROHAN_MILITIA);
        // [DALE]
        register(context, npcRegistryEntryLookup, DALE_MILITIA);
        // [LONGBEARDS]
        // [EREBOR]
        register(context, npcRegistryEntryLookup, EREBOR_MILITIA);
        // [LOTHLORIEN]
        register(context, npcRegistryEntryLookup, LOTHLORIEN_MILITIA);
        // [MORDOR]
        register(context, npcRegistryEntryLookup, MORDOR_ORC_MILITIA);
        register(context, npcRegistryEntryLookup, BLACK_URUK_SOLDIER);
        // [MISTY MOUNTAINS GOBLINS]
        register(context, npcRegistryEntryLookup, MISTY_GOBLIN_MILITIA);
        register(context, npcRegistryEntryLookup, MISTY_HOBGOBLIN_SOLDIER);
        // [ISENGARD]
        register(context, npcRegistryEntryLookup, ISENGARD_ORC_MILITIA);
        register(context, npcRegistryEntryLookup, URUK_HAI_SOLDIER);
        // [SHIRE]
        register(context, npcRegistryEntryLookup, HOBBIT_PEASANT);
    }

    private static NpcData register(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, NpcData npcData) {
        RegistryKey<NpcData> npcRegistryKey = of(npcData.getName());
        String name = npcRegistryKey.getValue().getPath();
        RegistryKey<NpcData> npcKey = RegistryKey.of(NPC_KEY, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcData>> optionalNpc = npcRegistryEntryLookup.getOptional(npcRegistryKey);
        optionalNpc.ifPresent(npcReference -> context.register(npcKey, npcData));

        return npcData;
    }

    private static RegistryKey<NpcData> of(String name) {
        return RegistryKey.of(NPC_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        // region [GONDOR]
        GONDOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.militia"), MiddleEarthRaces.HUMAN.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.KETTLE_HAT,
                        ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE,
                        ModEquipmentItems.GONDORIAN_CHAIN_COAT,
                        ModEquipmentItems.GONDORIAN_BOOTS,
                        ModWeaponItems.GONDORIAN_SWORD,
                        ModEquipmentItems.GONDORIAN_SHIELD
                )
        ));
        GONDOR_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.soldier"), MiddleEarthRaces.HUMAN.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.GONDORIAN_PLATE_HELMET,
                        ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE,
                        ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS,
                        ModEquipmentItems.GONDORIAN_PLATE_BOOTS,
                        ModWeaponItems.GONDORIAN_NOBLE_SPEAR,
                        ModEquipmentItems.GONDORIAN_SHIELD
                )
        ));
        //endregion
        // region [ROHAN]
        ROHAN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.militia"), MiddleEarthRaces.HUMAN.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET,
                        ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK,
                        ModEquipmentItems.ROHIRRIC_SCALE_JACKET,
                        ModEquipmentItems.STURDY_BOOTS,
                        ModWeaponItems.ROHIRRIC_NOBLE_SPEAR,
                        ModEquipmentItems.ROHIRRIC_BUCKING_HORSE_SHIELD

                )
        ));
        //endregion
        // region [DALE]
        DALE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dale.militia"), MiddleEarthRaces.HUMAN.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.DALISH_BURGONET,
                        ModEquipmentItems.DALISH_SCALE_HAUBERK,
                        ModEquipmentItems.DALISH_CHAIN_COAT,
                        ModEquipmentItems.DALISH_BOOTS,
                        ModWeaponItems.DALISH_NOBLE_SPEAR,
                        ModEquipmentItems.DALISH_HEYDAY_ROUND_SHIELD
                )
        ));
        //endregion
        // region [LONGBEARDS]
        // region [EREBOR]
        EREBOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "longbeards.erebor.militia"), MiddleEarthRaces.DWARF.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.EREBOR_GATEWARDEN_HELMET,
                        ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE,
                        ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS,
                        ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS,
                        ModWeaponItems.EREBOR_NOBLE_SPEAR,
                        ModEquipmentItems.EREBOR_ORNAMENTED_SHIELD
                )
        ));
        //endregion
        //endregion
        // region [LOTHLORIEN]
        LOTHLORIEN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien.militia"), MiddleEarthRaces.ELF.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.LORIEN_SOLDIER_HELMET,
                        ModEquipmentItems.LORIEN_SOLDIER_CHAIN_HAUBERK,
                        ModEquipmentItems.LORIEN_ARMING_SKIRT,
                        ModEquipmentItems.ELVEN_BOOTS,
                        ModWeaponItems.LORIEN_NOBLE_SPEAR,
                        ModEquipmentItems.LORIEN_MALLORN_SHIELD
                )
        ));
        //endregion
        // region [MORDOR]
        MORDOR_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "mordor.orc.militia"), MiddleEarthRaces.ORC.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.MORDOR_ORC_OVERSIGHT_HELMET,
                        ModEquipmentItems.MORDOR_ORC_CHESTPLATE,
                        ModEquipmentItems.MORDOR_ORC_SCALE_COAT,
                        ModEquipmentItems.ORC_PLATE_BOOTS,
                        ModWeaponItems.MORDOR_FALCHION,
                        ModEquipmentItems.HEATER_SHIELD
                )
        ));
        BLACK_URUK_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_uruk.militia"), MiddleEarthRaces.URUK.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.BLACK_URUK_PLATE_HELMET,
                        ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE,
                        ModEquipmentItems.BLACK_URUK_PLATE_LEGGINGS,
                        ModEquipmentItems.BLACK_URUK_PLATE_BOOTS,
                        ModWeaponItems.MORDOR_ELITE_SPEAR,
                        ModEquipmentItems.HEATER_SHIELD
                )
        ));
        //endregion
        // region [MISTY MOUNTAINS GOBLINS]
        MISTY_GOBLIN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.goblin.militia"), MiddleEarthRaces.ORC.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.ORC_SALLET,
                        ModEquipmentItems.ORC_GORGET_HAUBERK,
                        ModEquipmentItems.ORC_MAIL_COAT,
                        ModEquipmentItems.ORC_PLATE_BOOTS,
                        ModWeaponItems.ORC_SPEAR,
                        ModEquipmentItems.HEATER_SHIELD
                )
        ));
        MISTY_HOBGOBLIN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.hobgoblin.soldier"), MiddleEarthRaces.URUK.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET,
                        ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE,
                        ModEquipmentItems.GUNDABAD_HOBGOBLIN_CHAIN_COAT,
                        ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATED_BOOTS,
                        ModWeaponItems.ORC_AXE,
                        ModEquipmentItems.MISTY_MOUNTAINS_SHIELD
                )
        ));
        //endregion
        // region [ISENGARD]
        ISENGARD_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "isengard.orc.militia"), MiddleEarthRaces.ORC.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP,
                        ModEquipmentItems.ORC_GORGET_HAUBERK,
                        null,
                        ModEquipmentItems.STURDY_BOOTS,
                        ModWeaponItems.ISENGARD_ORC_SPEAR,
                        ModEquipmentItems.URUK_HAI_HEATER_SHIELD
                )
        ));
        URUK_HAI_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "isengard.uruk_hai.militia"), MiddleEarthRaces.URUK.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.URUK_HAI_PLATE_PAINTED_HELMET,
                        ModEquipmentItems.URUK_HAI_PLATE_CHESTPLATE,
                        ModEquipmentItems.URUK_HAI_PLATE_LEGGINGS,
                        ModEquipmentItems.URUK_HAI_PLATE_BOOTS,
                        ModWeaponItems.URUK_HAI_FALCHION,
                        ModEquipmentItems.URUK_HAI_WHITE_HAND_SHIELD
                )
        ));
        //endregion
        // region [SHIRE]
        HOBBIT_PEASANT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "shire.militia"), MiddleEarthRaces.HOBBIT.getId(), List.of(
                new NpcGearData(
                        ModEquipmentItems.STRAW_HAT,
                        null,
                        null,
                        null,
                        ModDecorativeBlocks.WOODEN_BUCKET.asItem(),
                        null
                )
        ));
        //endregion
    }
}
