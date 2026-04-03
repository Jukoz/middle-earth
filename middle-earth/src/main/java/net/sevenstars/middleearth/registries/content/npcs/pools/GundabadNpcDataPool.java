package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
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
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class GundabadNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD;

    private static List<Integer> allColors;
    private static final int DARK_PURPLE = 0x3c3135;
    private static final int DARK_BROWN = 0x4a3c34;

    public final static NpcData SNAGA;
    public final static NpcData MILITIA;
    public final static NpcData SCOUT;
    public final static NpcData RIDER;
    public final static NpcData WARRIOR;
    public final static NpcData VETERAN;
    public final static NpcData LEADER;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_SNAGA, SNAGA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_SCOUT, SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_RIDER, RIDER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_VETERAN, VETERAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_LEADER, LEADER)
        );
    }

    static {
        allColors = List.of(
                DARK_PURPLE,
                DARK_BROWN
        );

        SNAGA = new NpcData(NpcRegistry.GUNDABAD_SNAGA.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.HOOD).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MILITIA = new NpcData(NpcRegistry.GUNDABAD_MILITIA.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_SHANK).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        RIDER = new NpcData(NpcRegistry.GUNDABAD_RIDER.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.HOOD).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SCOUT = new NpcData(NpcRegistry.GUNDABAD_SCOUT.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(8))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.HOOD).withColors(allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_BOW).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORCISH_BOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        WARRIOR = new NpcData(NpcRegistry.GUNDABAD_WARRIOR.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SOLDIER_HELMET).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SCREECHER_HELMET).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColors(allColors).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))


                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_CLEAVER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        VETERAN = new NpcData(NpcRegistry.GUNDABAD_VETERAN.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SOLDIER_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SCREECHER_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))


                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_MAIL_COAT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_CLEAVER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        LEADER = new NpcData(NpcRegistry.GUNDABAD_LEADER.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_CAPTAIN_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withWeight(2).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_SCIMITAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_ELITE_CLEAVER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
