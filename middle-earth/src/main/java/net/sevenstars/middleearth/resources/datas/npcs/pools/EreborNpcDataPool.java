package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;

import java.util.HashMap;
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

    public final static NpcData EREBOR_ELITE;
    public final static NpcData EREBOR_VETERAN;
    public final static NpcData EREBOR_GATEWARDEN;
    public final static NpcData EREBOR_LEADER;

    public static List<NpcData> fetchAll() {
        return List.of(
                EREBOR_MINER,
                EREBOR_MILITIA,
                EREBOR_SOLDIER,
                EREBOR_ARCHER,
                EREBOR_ELITE,
                EREBOR_VETERAN,
                EREBOR_GATEWARDEN,
                EREBOR_LEADER);
    }

    static {
        EREBOR_MINER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "miner"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withColor(DARK_RED).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_MINER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_GAMBESON).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_MINER_GAMBESON).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_GAMBESON).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, DARK_BROWN))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_MINER_GAMBESON).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, DARK_BROWN))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(DARK_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_BOOTS).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_REINFORCED_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ToolItemsME.KHAZAD_STEEL_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.KHAZAD_STEEL_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_PARTISAN_OUTFIT).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))

                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DWARVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.KHAZAD_STEEL_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.KHAZAD_STEEL_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.HEATER_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_COIF).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_REINFORCED_COAT).withColor(LIGHT_BLUE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS).withColor(LIGHT_RED).withWeight(5))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_LEGGINGS))
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.EREBOR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_CROSS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.HEATER_SHIELD))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "archer"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_COIF).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK).withColor(LIGHT_BLUE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_REINFORCED_COAT).withColor(LIGHT_BLUE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS).withColor(LIGHT_RED).withWeight(5))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_LEGGINGS))
                                .add(NpcGearItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.EREBOR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_BOW).withWeight(3))
                                .add(NpcGearItemData.create(Items.BOW))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_ELITE = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "elite"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GUARD_HELMET).withWeight(8))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(BackAttachmentsME.EREBOR_CAPE))

                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE).withCape(BackAttachmentsME.EREBOR_CAPE))

                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.EREBOR_CAPE).withWeight(3))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS).withColor(DARK_RED))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_BOOTS).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_REINFORCED_SHIELD).withWeight(5))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_CROSS_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(5))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GUARD_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_SCALE_COAT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_BOOTS).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_REINFORCED_SHIELD).withWeight(5))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_GATEWARDEN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "gatewarden"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_BUCKLER_SHIELD))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));

        EREBOR_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "leader"), RacesME.DWARF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_SCALE_COAT).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_PLATE_BOOTS).withWeight(3))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_BUCKLER_SHIELD))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(NpcTextureDataPool.DWARF_MALE));
            put(EntityCategory.FEMALE, List.of(NpcTextureDataPool.DWARF_FEMALE));
        }}));
    }
}
