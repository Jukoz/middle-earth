package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.npcs.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class MoriaNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.MORIA;

    private static List<Integer> allColors;
    private static final int DARK_PURPLE = 0x3c3135;
    private static final int DARK_BROWN = 0x4a3c34;

    public final static NpcData GOBLIN;
    public final static NpcData SCOUT;
    public final static NpcData WARRIOR;
    public final static NpcData RIDER;
    public final static NpcData VETERAN;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_GOBLIN, GOBLIN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_SCOUT, SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_RIDER, RIDER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_VETERAN, VETERAN)
        );
    }

    static {
        allColors = List.of(
                DARK_PURPLE,
                DARK_BROWN
        );

        GOBLIN = new NpcData(NpcRegistry.MORIA_GOBLIN.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.HOOD).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        WARRIOR = new NpcData(NpcRegistry.MORIA_WARRIOR.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_SHANK).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        RIDER = new NpcData(NpcRegistry.MORIA_RIDER.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.HOOD).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withWeight(2))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SCOUT = new NpcData(NpcRegistry.MORIA_SCOUT.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(8))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.HOOD).withColors(allColors))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_BOW).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORCISH_BOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcData(NpcRegistry.MORIA_VETERAN.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SOLDIER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SCREECHER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))


                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_MAIL_COAT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_CLEAVER))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_HEAVY_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}
