package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
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

public class MoriaNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.MORIA;

    private static List<Integer> allColors;
    private static final int DARK_RED = 0x2e2624;
    private static final int DARK_BROWN = 0x4a3c34;

    public final static NpcType GOBLIN;
    public final static NpcType MILITIA;
    public final static NpcType SCOUT;
    public final static NpcType WARRIOR;
    public final static NpcType RIDER;
    public final static NpcType VETERAN;
    public final static NpcType CHIEF;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_GOBLIN, GOBLIN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_SCOUT, SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_RIDER, RIDER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_VETERAN, VETERAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORIA_CHIEF, CHIEF)
        );
    }

    static {
        allColors = List.of(
                DARK_RED,
                DARK_BROWN
        );

        GOBLIN = new NpcType(NpcRegistry.MORIA_GOBLIN.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_NASAL_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_CUIRASS).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_BELLY_PLATE))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_CLEAVER).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.BONE_SHANK).withWeight(2))

                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);


        MILITIA = new NpcType(NpcRegistry.MORIA_MILITIA.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(8))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_NASAL_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_CUIRASS).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_BELLY_PLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_SHIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SHANK))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SCOUT = new NpcType(NpcRegistry.MORIA_SCOUT.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(8))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_NASAL_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_CUIRASS).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_BELLY_PLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_SHIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SNAGA_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_BOW).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_CROSSBOW).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(1))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        WARRIOR = new NpcType(NpcRegistry.MORIA_WARRIOR.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHARGER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_RUINED_DWARVEN_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_HAUBERK).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_RUINED_DWARVEN_HAUBERK).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_SHIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CRUDE_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SHANK).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE))
                                .add(WeightedItemData.create(ToolItemsME.BURZUM_STEEL_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD))
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        RIDER = new NpcType(NpcRegistry.MORIA_RIDER.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHARGER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_CHESTPLATE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT).withWeight(2).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LACED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_BOW).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.GOBLIN_CROSSBOW))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR))
                                .add(WeightedItemData.create(ToolItemsME.BURZUM_STEEL_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(ModDecorativeBlocks.CRUDE_CHAIN.asItem()))
                                .add(WeightedItemData.create(ModDecorativeBlocks.CRUDE_BROAD_CHAIN.asItem()))
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcType(NpcRegistry.MORIA_VETERAN.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_LEGGINGS))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKBLADE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKAXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SPEAR).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKAXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKBLADE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD))
                        ).withWeight(4)
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        CHIEF = new NpcType(NpcRegistry.MORIA_CHIEF.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.MORIA_GOBLIN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKBLADE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKAXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}
