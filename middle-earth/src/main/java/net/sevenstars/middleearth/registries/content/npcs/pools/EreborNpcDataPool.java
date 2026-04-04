package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class EreborNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.LONGBEARDS_EREBOR;

    private final static int LIGHT_BLUE = 0x4b6274;
    private final static int LIGHT_RED = 0x7f4442;
    private final static int DARK_RED = 0x56302d;
    private final static int DARK_BROWN = 0x3b291e;

    public final static NpcData PEASANT;
    public final static NpcData MINER;
    public final static NpcData MILITIA;
    public final static NpcData SOLDIER;
    public final static NpcData ARCHER;
    public final static NpcData ELITE;
    public final static NpcData VETERAN;
    public final static NpcData GATEWARDEN;
    public final static NpcData LEADER;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.EREBOR_MINER, MINER),
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
        PEASANT = new NpcData(NpcRegistry.EREBOR_PEASANT.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, List.of(
                GearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET).withColor(DARK_RED).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_MINER_HELMET))
                                .add(WeightedItemData.create())
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.WOODEN_HOE))
                                .add(WeightedItemData.create(Items.WHEAT))
                                .add(WeightedItemData.create(Items.WOODEN_SHOVEL))
                                .add(WeightedItemData.create(ModDecorativeBlocks.WATERING_CAN.asItem()))
                                .add(WeightedItemData.create(Items.AIR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MINER = new NpcData(NpcRegistry.EREBOR_MINER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, List.of(
            GearData.create()
                .add(EquipmentSlot.HEAD, GearSlotPool.create()
                        .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(4))
                        .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET).withColor(DARK_RED).withWeight(4))
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_MINER_HELMET))
                        .add(WeightedItemData.create())
                )
                .add(EquipmentSlot.CHEST, GearSlotPool.create()
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_GAMBESON).withColor(LIGHT_RED))
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_MINER_GAMBESON).withColor(LIGHT_RED))
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_GAMBESON).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, DARK_BROWN))
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_MINER_GAMBESON).withColor(LIGHT_RED).withCape(BackAttachmentsME.SURCOAT, DARK_BROWN))
                )
                .add(EquipmentSlot.LEGS, GearSlotPool.create()
                        .add(WeightedItemData.create(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS).withColor(DARK_RED))
                )
                .add(EquipmentSlot.FEET, GearSlotPool.create()
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_BOOTS).withWeight(3))
                        .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_REINFORCED_BOOTS))
                )
                .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                        .add(WeightedItemData.create(ToolItemsME.KHAZAD_STEEL_PICKAXE))
                        .add(WeightedItemData.create(ToolItemsME.KHAZAD_STEEL_AXE))
                )
                .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                        .add(WeightedItemData.create(Items.AIR))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MILITIA = new NpcData(NpcRegistry.EREBOR_MILITIA.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SOLDIER = new NpcData(NpcRegistry.EREBOR_SOLDIER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ARCHER = new NpcData(NpcRegistry.EREBOR_ARCHER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                GearData.create()
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
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_BOW).withWeight(3))
                                .add(WeightedItemData.create(Items.BOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ELITE = new NpcData(NpcRegistry.EREBOR_ELITE.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        VETERAN = new NpcData(NpcRegistry.EREBOR_VETERAN.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        GATEWARDEN = new NpcData(NpcRegistry.EREBOR_GATEWARDEN.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);


        LEADER = new NpcData(NpcRegistry.EREBOR_LEADER.getValue(), RaceRegistry.DWARF, FACTION, TexturePresetsRegistry.LONGBEARDS_EREBOR_MIGHTY_DWARF, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
