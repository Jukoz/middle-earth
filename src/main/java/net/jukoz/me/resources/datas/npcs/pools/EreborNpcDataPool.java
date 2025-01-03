package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.Identifier;

import java.util.List;

public class EreborNpcDataPool {
    private final static String FACTION_BASE = "longbeards.erebor.";
    // region GEAR COLORS ========================================>>
    private final static int LIGHT_BLUE = 0x4b6274;
    private final static int DARK_BLUE = 0x223345;
    private final static int LIGHT_RED = 0x7f4442;
    private final static int DARK_RED = 0x56302d;
    private final static int LIGHT_BROWN = 0x6b463a;
    private final static int DARK_BROWN = 0x3b291e;
    // endregion
    // NPC ========================================>>
    public final static NpcData EREBOR_MINER;
    public final static NpcData EREBOR_MILITIA;
    public final static NpcData EREBOR_SOLDIER;
    public final static NpcData EREBOR_ARCHER;

    public final static NpcData EREBOR_BRAWLER;
    public final static NpcData EREBOR_VETERAN;
    public final static NpcData EREBOR_GATEWARDEN;
    public final static NpcData EREBOR_LEADER;

    public static List<NpcData> fetchAll() {
        return List.of(
                EREBOR_MINER,
                EREBOR_MILITIA,
                EREBOR_SOLDIER,
                EREBOR_ARCHER,
                EREBOR_BRAWLER,
                EREBOR_VETERAN,
                EREBOR_GATEWARDEN,
                EREBOR_LEADER
        );
    }

    static {
        EREBOR_MINER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "miner"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(DARK_RED).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_MINER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_GAMBESON).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_MINER_GAMBESON).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_GAMBESON).withColor(LIGHT_RED).withCape(ModCapes.SURCOAT, DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_MINER_GAMBESON).withColor(LIGHT_RED).withCape(ModCapes.SURCOAT, DARK_BROWN))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_LEATHER_LEGGINGS).withColor(DARK_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_BOOTS).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_REINFORCED_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModToolItems.BURZUM_STEEL_PICKAXE))
                                .add(NpcGearItemData.create(ModToolItems.BURZUM_STEEL_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        EREBOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_SEGMENTED_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRACED_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_NASAL_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_PARTISAN_OUTFIT).withColor(LIGHT_RED).withCape(ModCapes.SURCOAT, LIGHT_BLUE))

                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.DWARVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.BURZUM_STEEL_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.BURZUM_STEEL_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.HEATER_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        EREBOR_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_COIF).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_SEGMENTED_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRACED_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_NASAL_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PADDED_MAIL_HAUBERK).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PADDED_MAIL_HAUBERK).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LONG_COAT).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LONG_COAT).withColor(LIGHT_RED).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_REINFORCED_LEATHER_HAUBERK).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_REINFORCED_COAT).withColor(LIGHT_BLUE).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_LEGGINGS).withColor(LIGHT_RED).withWeight(5))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_LEGGINGS))
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.EREBOR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_CROSS_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_CROSS_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.HEATER_SHIELD))
                        )
        ));

        EREBOR_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "archer"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_COIF).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_SEGMENTED_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRACED_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_NASAL_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PADDED_MAIL_HAUBERK).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PADDED_MAIL_HAUBERK).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LONG_COAT).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LONG_COAT).withColor(LIGHT_RED).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_REINFORCED_LEATHER_HAUBERK).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_REINFORCED_COAT).withColor(LIGHT_BLUE).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_LEGGINGS).withColor(LIGHT_RED).withWeight(5))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_LEGGINGS))
                                .add(NpcGearItemData.create(ModEquipmentItems.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.EREBOR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_BOW).withWeight(3))
                                .add(NpcGearItemData.create(Items.BOW))
                        )
        ));

        EREBOR_BRAWLER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "brawler"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GUARD_HELMET).withWeight(8))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GILDED_MAIL_COIF).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_LEGGINGS).withColor(DARK_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_BOOTS).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        EREBOR_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GUARD_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_LEGGINGS))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_SCALE_COAT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_BOOTS).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_REINFORCED_TOWER_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_REINFORCED_SHIELD).withWeight(5))
                        )
        ));

        EREBOR_GATEWARDEN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "gatewarden"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GATEWARDEN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_ORNAMENTED_TOWER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_REINFORCED_TOWER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_BUCKLER_SHIELD))
                        )
        ));

        EREBOR_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "leader"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE).withCape(ModCapes.EREBOR_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE).withCape(ModCapes.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_SCALE_COAT).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_PLATE_BOOTS).withWeight(3))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_ORNAMENTED_TOWER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_BUCKLER_SHIELD))
                        )
        ));
    }
}
