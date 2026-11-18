package net.sevenstars.middleearth.registries.content.npcclasses.pools;

import net.sevenstars.middleearth.features.smithing.SmithingItems;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcLoot;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRoutines;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.GearSlotPool;

import java.util.List;

public class EreborNpcClassPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.LONGBEARDS_EREBOR;

    private final static int LIGHT_BLUE = 0x4b6274;
    private final static int LIGHT_RED = 0x7f4442;
    private final static int DARK_RED = 0x56302d;
    private final static int DARK_BROWN = 0x3b291e;

    public final static NpcClass PEASANT;
    public final static NpcClass MILITIA;
    public final static NpcClass SOLDIER;
    public final static NpcClass ARCHER;
    public final static NpcClass ELITE;
    public final static NpcClass VETERAN;
    public final static NpcClass GATEWARDEN;
    public final static NpcClass LEADER;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_SOLDIER, SOLDIER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_ARCHER, ARCHER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_ELITE, ELITE),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_VETERAN, VETERAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_GATEWARDEN, GATEWARDEN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_LEADER, LEADER)
        );
    }

    static {
        PEASANT = new NpcClass(NpcRegistry.EREBOR_PEASANT.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(DARK_RED).withWeight(1))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_COWL).withColor(DARK_RED).withWeight(1))
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET).withColor(DARK_RED).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_MINER_HELMET))
                                .add(WeightedItemData.create().withWeight(21))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BLACKSMITH_APRON))
                                .add(WeightedItemData.create().withWeight(10))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_DAGGER))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_1_TO_4);

        MILITIA = new NpcClass(NpcRegistry.EREBOR_MILITIA.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_PARTISAN_OUTFIT).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))

                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DWARVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.KHAZAD_STEEL_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.KHAZAD_STEEL_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_3_TO_7);

        SOLDIER = new NpcClass(NpcRegistry.EREBOR_SOLDIER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_COIF).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK).withColor(LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_REINFORCED_COAT).withColor(LIGHT_BLUE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS).withColor(LIGHT_RED).withWeight(5))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_LEGGINGS))
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.EREBOR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SHIELD).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_CROSS_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_6_TO_10);

        ARCHER = new NpcClass(NpcRegistry.EREBOR_ARCHER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_COIF).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LONG_COAT).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK).withColor(LIGHT_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_REINFORCED_COAT).withColor(LIGHT_BLUE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS).withColor(LIGHT_RED).withWeight(5))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_LEGGINGS))
                                .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(LIGHT_RED))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.EREBOR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_CROSSBOW).withWeight(7))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_BOW).withWeight(3))
                                .add(WeightedItemData.create(Items.BOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_6_TO_10);

        ELITE = new NpcClass(NpcRegistry.EREBOR_ELITE.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GUARD_HELMET).withWeight(8))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_CAPTAIN_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(DARK_RED).withCape(BackAttachmentsME.EREBOR_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE).withColor(LIGHT_BLUE).withCape(BackAttachmentsME.EREBOR_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.EREBOR_CAPE).withWeight(3))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS).withColor(DARK_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_BOOTS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_REINFORCED_SHIELD).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SHIELD).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_CROSS_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_10_TO_16);

        VETERAN = new NpcClass(NpcRegistry.EREBOR_VETERAN.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GUARD_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_SCALE_COAT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_BOOTS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_REINFORCED_SHIELD).withWeight(5))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_13_TO_20);

        GATEWARDEN = new NpcClass(NpcRegistry.EREBOR_GATEWARDEN.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_GATEWARDEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_BUCKLER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_13_TO_20);


        LEADER = new NpcClass(NpcRegistry.EREBOR_LEADER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_MIGHTY_DWARF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_CAPTAIN_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.EREBOR_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE).withCape(BackAttachmentsME.SURCOAT, LIGHT_BLUE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_SCALE_COAT).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_MAIL_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_PLATE_BOOTS).withWeight(3))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_BUCKLER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_15_TO_25);

        MILITIA.withRoutine(NpcRoutines.WATCH);
        SOLDIER.withRoutine(NpcRoutines.WATCH);
        GATEWARDEN.withRoutine(NpcRoutines.GUARD);
        PEASANT.withRoutine(NpcRoutines.GATHERER);
        ARCHER.withRoutine(NpcRoutines.ARCHER);
        ELITE.withRoutine(NpcRoutines.ELITE);
        VETERAN.withRoutine(NpcRoutines.SOLDIER);
        LEADER.withRoutine(NpcRoutines.LEADER);
        PEASANT.asCivilian();
    }
}
