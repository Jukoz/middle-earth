package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.npctypes.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.npctypes.NpcLoot;
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

public class IsengardNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.ISENGARD;

    private static List<Integer> allColors;
    private static final int DARK = 0x302b28;
    private static final int DARK_BROWN = 0x655147;

    public final static NpcType SNAGA;
    public final static NpcType WARRIOR;

    public final static NpcType URUK_HAI_SOLDIER;
    public final static NpcType URUK_HAI_SCOUT;
    public final static NpcType URUK_HAI_VETERAN;
    public final static NpcType URUK_HAI_BERSERKER;
    public final static NpcType URUK_HAI_LEADER;

    public final static NpcType ORTHANC_GUARD;


    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_ORC_SNAGA, SNAGA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_ORC_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_URUK_HAI_SOLDIER, URUK_HAI_SOLDIER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_URUK_HAI_SCOUT, URUK_HAI_SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_URUK_HAI_VETERAN, URUK_HAI_VETERAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_URUK_HAI_BERSERKER, URUK_HAI_BERSERKER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_URUK_HAI_LEADER, URUK_HAI_LEADER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ISENGARD_ORTHANC_GUARD, ORTHANC_GUARD)
        );
    }

    static {
        allColors = List.of(DARK, DARK_BROWN);

        ORTHANC_GUARD = new NpcType(NpcRegistry.ISENGARD_ORTHANC_GUARD.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ISENGARD_HUMAN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORTHANC_GUARD_HELMET).withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE).withCape(BackAttachmentsME.ORTHANC_GUARD_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORTHANC_GUARD_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORTHANC_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_SPEAR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_13_TO_20);

        SNAGA = new NpcType(NpcRegistry.ISENGARD_ORC_SNAGA.getValue(), RaceRegistry.SNAGA, FACTION, TexturePresetsRegistry.ISENGARD_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColors(allColors).withWeight(1))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_SANDALS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODEN_SPEAR).withWeight(6))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_1_TO_4);


        WARRIOR = new NpcType(NpcRegistry.ISENGARD_ORC_WARRIOR.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.ISENGARD_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LIGHT_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SALLET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_CLEAVER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(6))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_3_TO_7);

        URUK_HAI_SOLDIER = new NpcType(NpcRegistry.ISENGARD_URUK_HAI_SOLDIER.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.ISENGARD_URUK_HAI, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PLATE_HELMET).withWeight(5))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LIGHT_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_SAPPER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_WARBLADE))
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_CLEAVER))
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.ISENGARD_ORC_SPEAR))

                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_6_TO_10);

        URUK_HAI_SCOUT = new NpcType(NpcRegistry.ISENGARD_URUK_HAI_SCOUT.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.ISENGARD_URUK_HAI, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LIGHT_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_REINFORCED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_LIGHT_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_MAIL_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_CROSSBOW).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_BOW).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_FALCHION))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_SPEAR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_6_TO_10);

        URUK_HAI_VETERAN = new NpcType(NpcRegistry.ISENGARD_URUK_HAI_VETERAN.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.ISENGARD_URUK_HAI, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_PLATE_HELMET).withWeight(5))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_SAPPER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_SOLDIER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_CUIRASS))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_MAIL_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WARBLADE))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_HEATER_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_10_TO_16);


        URUK_HAI_BERSERKER = new NpcType(NpcRegistry.ISENGARD_URUK_HAI_BERSERKER.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.ISENGARD_URUK_HAI, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_BERSERKER_HELMET))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WARBLADE))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(DecorativeItemsME.TORCH_OF_ORTHANC))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_13_TO_20);

        URUK_HAI_LEADER = new NpcType(NpcRegistry.ISENGARD_URUK_HAI_LEADER.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.ISENGARD_URUK_HAI, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_COMMANDER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_MAIL_SKIRT)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.URUK_HAI_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_WARBLADE))
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.URUK_HAI_HEATER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, NpcLoot.FROM_15_TO_25);
    }
}
