package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.npctypes.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.*;

import java.util.List;

public class GundabadNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD;

    private static List<Integer> allColors;
    private static final int DARK_PURPLE = 0x3c3135;
    private static final int DARK_BROWN = 0x4a3c34;

    public final static NpcType GOBLIN;
    public final static NpcType MILITIA;
    public final static NpcType SCOUT;
    public final static NpcType RIDER;
    public final static NpcType WARRIOR;
    public final static NpcType VETERAN;
    public final static NpcType LEADER;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GUNDABAD_GOBLIN, GOBLIN),
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

        GOBLIN = new NpcType(NpcRegistry.GUNDABAD_GOBLIN.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
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

        MILITIA = new NpcType(NpcRegistry.GUNDABAD_MILITIA.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
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

        RIDER = new NpcType(NpcRegistry.GUNDABAD_RIDER.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, new MountData(EntitiesME.WARG).withArmor(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR));

        SCOUT = new NpcType(NpcRegistry.GUNDABAD_SCOUT.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GUNDABAD_GOBLIN, List.of(
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

        WARRIOR = new NpcType(NpcRegistry.GUNDABAD_WARRIOR.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SOLDIER_HELMET).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SCREECHER_HELMET).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColors(allColors).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
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
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_CLEAVER))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcType(NpcRegistry.GUNDABAD_VETERAN.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, List.of(
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

        LEADER = new NpcType(NpcRegistry.GUNDABAD_LEADER.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_CAPTAIN_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_SCIMITAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_ELITE_CLEAVER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_HEAVY_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}
