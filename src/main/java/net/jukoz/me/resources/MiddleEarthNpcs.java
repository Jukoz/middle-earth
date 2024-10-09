package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MiddleEarthNpcs {
    public final static String PATH = "npcs";
    public static final RegistryKey<Registry<NpcData>> NPC_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    // [GENERIC]
    public final static NpcData HUMAN_CIVILIAN;
    public final static NpcData DWARF_CIVILIAN;
    public final static NpcData ELF_CIVILIAN;
    public final static NpcData HOBBIT_CIVILIAN;
    public final static NpcData ORC_CIVILIAN;
    public final static NpcData URUK_CIVILIAN;

    // [GONDOR]
    public final static NpcData GONDOR_MILITIA;
    public final static NpcData GONDOR_SOLDIER;
    public final static NpcData GONDOR_KNIGHT;
    public final static NpcData GONDOR_VETERAN;
    public final static NpcData GONDOR_FOUNTAIN_GUARDS;
    public final static NpcData GONDOR_CITADEL_GUARDS;
    public final static NpcData GONDOR_KING_GUARDS;
    public final static NpcData GONDOR_LEADER;

    // [ROHAN]
    public final static NpcData ROHAN_MILITIA;
    public final static NpcData ROHAN_SOLDIER;
    public final static NpcData ROHAN_KNIGHT;
    public final static NpcData ROHAN_ROYAL_GUARD;
    public final static NpcData ROHAN_EORLING_MARSHAL;
    public final static NpcData ROHAN_HORSE_LORD;
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
        // [GENERIC]
        register(context, npcRegistryEntryLookup, HUMAN_CIVILIAN);
        register(context, npcRegistryEntryLookup, DWARF_CIVILIAN);
        register(context, npcRegistryEntryLookup, ELF_CIVILIAN);
        register(context, npcRegistryEntryLookup, HOBBIT_CIVILIAN);
        register(context, npcRegistryEntryLookup, ORC_CIVILIAN);
        register(context, npcRegistryEntryLookup, URUK_CIVILIAN);

        // [GONDOR]
        register(context, npcRegistryEntryLookup, GONDOR_MILITIA);
        register(context, npcRegistryEntryLookup, GONDOR_SOLDIER);
        register(context, npcRegistryEntryLookup, GONDOR_KNIGHT);
        register(context, npcRegistryEntryLookup, GONDOR_FOUNTAIN_GUARDS);
        register(context, npcRegistryEntryLookup, GONDOR_CITADEL_GUARDS);
        register(context, npcRegistryEntryLookup, GONDOR_KING_GUARDS);
        register(context, npcRegistryEntryLookup, GONDOR_VETERAN);
        register(context, npcRegistryEntryLookup, GONDOR_LEADER);
        // [ROHAN]
        register(context, npcRegistryEntryLookup, ROHAN_MILITIA);
        register(context, npcRegistryEntryLookup, ROHAN_SOLDIER);
        register(context, npcRegistryEntryLookup, ROHAN_KNIGHT);
        register(context, npcRegistryEntryLookup, ROHAN_ROYAL_GUARD);
        register(context, npcRegistryEntryLookup, ROHAN_EORLING_MARSHAL);
        register(context, npcRegistryEntryLookup, ROHAN_HORSE_LORD);

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
        // region [GENERIC]
        HUMAN_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "human.civilian"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.STRAW_HAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.WOVEN_HAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.BYCOCKET).withWeight(1))
                                .add(new NpcGearItemData().withWeight(4))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.WORK_SHOES).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.SHOES).withWeight(2))
                                .add(new NpcGearItemData().withWeight(3))
                        )
        ));

        DWARF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dwarf.civilian"), MiddleEarthRaces.DWARF, List.of(
                new NpcGearData()
        ));

        ELF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "elf.civilian"), MiddleEarthRaces.ELF, List.of(
                new NpcGearData()
        ));

        HOBBIT_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "hobbit.civilian"), MiddleEarthRaces.HOBBIT, List.of(
                new NpcGearData()
        ));

        ORC_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "orc.civilian"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
        ));

        URUK_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "uruk.civilian"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
        ));
        // endregion
        // region [GONDOR]
        GONDOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.militia"), MiddleEarthRaces.HUMAN, List.of(

                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(0xE8CB9B).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(0x2b3965))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(0x182038))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(0xc6764f))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(0x82492c))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(0xE8CB9B))
                                .add(new NpcGearItemData().withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS))
                                .add(new NpcGearItemData().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SPEAR).withWeight(4))
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD).withWeight(3))
                                .add(new NpcGearItemData(ModToolItems.BRONZE_AXE).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(Items.IRON_SWORD))
                                .add(new NpcGearItemData(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KITE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.HEATER_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROUND_SHIELD))
                                .add(new NpcGearItemData().withWeight(5))
                        ),
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP))
                                .add(new NpcGearItemData(ModEquipmentItems.OPEN_FACE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON).withColor(0x68697d))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON).withColor(0x555666))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(0x68697d).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(0x555666).withWeight(4))
                                .add(new NpcGearItemData().withWeight(7))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS))
                                .add(new NpcGearItemData().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD))
                                .add(new NpcGearItemData(ModToolItems.BRONZE_AXE))
                                .add(new NpcGearItemData(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(Items.IRON_SWORD))
                                .add(new NpcGearItemData(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KITE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.HEATER_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROUND_SHIELD))
                                .add(new NpcGearItemData().withWeight(5))
                        )
        ));
        GONDOR_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.soldier"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CABASSET_HELMET))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SOLDIER_HELMET).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_TABBARD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(0x68697d).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(0x555666).withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_AXE))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData())
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD))
                        )
        ));
        GONDOR_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.knight"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_AXE))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD))
                        ),
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_TOWER_SHIELD))
                                .add(new NpcGearItemData().withWeight(3))
                        )
        ));
        GONDOR_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.veteran"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_HERO_SHIELD))
                                .add(new NpcGearItemData().withWeight(2))
                        )
        ));
        GONDOR_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.leader"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_HERO_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD))
                        )
        ));
        GONDOR_CITADEL_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.citadel_guards"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KNIGHT_SHIELD))
                        )
        ));
        GONDOR_FOUNTAIN_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.fountain_guards"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_FOUNTAIN_GUARD_SPEAR)))
        ));
        GONDOR_KING_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "gondor.king_guards"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SPEAR).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_TOWER_SHIELD))
                        )
        ));
        //endregion
        // region [ROHAN]
        ROHAN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.militia"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_HELMET).withWeight(6))
                                .add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_HELMET).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ORNAMENTED_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_COAT).withColor(0x617144).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_COAT).withColor(0x516c42).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_COAT).withColor(0x2d4122).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_COAT).withColor(0x988360).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_COAT).withColor(0xa39269).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(0x617144))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(0x516c42))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(0x2d4122))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(0x988360))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(0xa39269))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.TRAVELLING_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SPEAR).withWeight(4))
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD).withWeight(3))
                                .add(new NpcGearItemData(ModToolItems.BRONZE_AXE).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(Items.IRON_SWORD))
                                .add(new NpcGearItemData(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KITE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.HEATER_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROUND_SHIELD))
                                .add(new NpcGearItemData().withWeight(5))
                        )
        ));
        ROHAN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.soldier"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MILITIA_HELMET).withWeight(6))
                                .add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0x617144))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0x516c42))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0x2d4122))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0x988360))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0xa39269))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0x617144))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0x516c42))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0x2d4122))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0x988360))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0xa39269))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(0x617144))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(0x516c42))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(0x2d4122))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(0x988360))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(0xa39269))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.TRAVELLING_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_SPEAR).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_SHIELD).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_BUCKING_HORSE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GALLOPING_HORSE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_HORSE_HEAD_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_PLAINSMAN_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_TWIN_HORSES_SHIELD))
                                .add(new NpcGearItemData().withWeight(10))
                        )
        ));
        ROHAN_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.knight"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_SOLDIER_HELMET).withWeight(6))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MILITIA_HELMET).withWeight(6))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT).withColor(0x7f4442))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT).withColor(0x56302d))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MAIL_HAUBERK).withColor(0x7f4442))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MAIL_HAUBERK).withColor(0x56302d))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT_OPEN).withColor(0x7f4442))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT_OPEN).withColor(0x56302d))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0x7f4442))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(0x56302d))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0x7f4442))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(0x56302d))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.TRAVELLING_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_AXE))
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_SHIELD).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_BUCKING_HORSE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_GALLOPING_HORSE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_HORSE_HEAD_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_PLAINSMAN_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_TWIN_HORSES_SHIELD))
                        )
        ));
        ROHAN_ROYAL_GUARD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.royal_guard"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK).withWeight(6))
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_SCALE_JACKET)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.HIGH_CUT_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_NOBLE_SPEAR).withWeight(6))
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_SHIELD)))
        ));
        ROHAN_EORLING_MARSHAL = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.eorling_marhsal"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.EORLING_MARSHAL_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.EORLING_MARSHAL_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.EORLING_MARSHAL_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.EORLING_MARSHAL_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_NOBLE_SWORD).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_EORLING_SHIELD))
                                .add(new NpcGearItemData().withWeight(3))
                        )
        ));
        ROHAN_HORSE_LORD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "rohan.horse_lord"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.HORSE_LORD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.HORSE_LORD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.HORSE_LORD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.HORSE_LORD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.ROHIRRIC_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SHIELD))
                                .add(new NpcGearItemData().withWeight(3))
                        )
        ));



        //endregion
        // region [DALE]
        DALE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dale.militia"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [LONGBEARDS]
        // region [EREBOR]
        EREBOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "longbeards.erebor.militia"), MiddleEarthRaces.DWARF, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        //endregion
        // region [LOTHLORIEN]
        LOTHLORIEN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien.militia"), MiddleEarthRaces.ELF, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [MORDOR]
        MORDOR_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "mordor.orc.militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        BLACK_URUK_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_uruk.militia"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [MISTY MOUNTAINS GOBLINS]
        MISTY_GOBLIN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.goblin.militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        MISTY_HOBGOBLIN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.hobgoblin.soldier"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [ISENGARD]
        ISENGARD_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "isengard.orc.militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        URUK_HAI_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "isengard.uruk_hai.militia"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [SHIRE]
        HOBBIT_PEASANT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "shire.militia"), MiddleEarthRaces.HOBBIT, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
    }
}
