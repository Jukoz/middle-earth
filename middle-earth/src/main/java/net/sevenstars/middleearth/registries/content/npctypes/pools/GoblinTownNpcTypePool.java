package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
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
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.GearSlotPool;

import java.util.List;

public class GoblinTownNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.GOBLIN_TOWN;

    private static List<Integer> allColors;
    private static final int BROWN = 0x61554e;
    private static final int DARK_BROWN = 0x4a3c34;
    private static final int BROWN_GOBLIN = 7628899;

    public final static NpcType GOBLIN;
    public final static NpcType SCOUT;
    public final static NpcType WARRIOR;
    public final static NpcType RIDER;
    public final static NpcType VETERAN;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GOBLIN_TOWN_GOBLIN, GOBLIN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GOBLIN_TOWN_SCOUT, SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GOBLIN_TOWN_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GOBLIN_TOWN_RIDER, RIDER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GOBLIN_TOWN_VETERAN, VETERAN)
        );
    }

    static {
        allColors = List.of(
                BROWN,
                DARK_BROWN
        );

        GOBLIN = new NpcType(NpcRegistry.GOBLIN_TOWN_GOBLIN.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GOBLIN_TOWN_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(12))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(9))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SANDALS).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.WOODEN_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.STONE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_CLEAVER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SHANK).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_AXE).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SCOUT = new NpcType(NpcRegistry.GOBLIN_TOWN_SCOUT.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GOBLIN_TOWN_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.COOKING_POT_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SKULL_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_MANDIBLE_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CROSSBONES_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_RIBCAGE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_SCALE_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SANDALS).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BOW).withWeight(6))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_CROSSBOW).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(1))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        WARRIOR = new NpcType(NpcRegistry.GOBLIN_TOWN_WARRIOR.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GOBLIN_TOWN_WARRIOR, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.COOKING_POT_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SKULL_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_MANDIBLE_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CROSSBONES_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_REINFORCED_CARAPACE).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_RIBCAGE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_SCALE_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SANDALS).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_SHANK))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_AXE).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_LONGBLADE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_CLEAVER))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SHANK))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        RIDER = new NpcType(NpcRegistry.GOBLIN_TOWN_RIDER.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GOBLIN_TOWN_WARRIOR, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SKULL_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_MANDIBLE_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_MANDIBLE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_REINFORCED_CARAPACE).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_RIBCAGE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BONE_SCALE_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SANDALS).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_SPEAR).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_LONGBLADE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_CLEAVER))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcType(NpcRegistry.GOBLIN_TOWN_VETERAN.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.GOBLIN_TOWN_BRUTE, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SKULKER_GUARD_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_HEAVY_NASAL_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_MANDIBLE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_CRUDE_SCALE_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_BELLY_PLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_SCIMITAR).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_AXE).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}
