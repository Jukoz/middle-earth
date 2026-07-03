package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
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

public class GoblinTownNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.GOBLIN_TOWN;

    private static List<Integer> allColors;
    private static final int BROWN = 0x61554e;
    private static final int DARK_BROWN = 0x4a3c34;
    private static final int BROWN_GOBLIN = 7628899;

    public final static NpcData GOBLIN;
    public final static NpcData SCOUT;
    public final static NpcData WARRIOR;
    public final static NpcData RIDER;
    public final static NpcData VETERAN;

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

        GOBLIN = new NpcData(NpcRegistry.GOBLIN_TOWN_GOBLIN.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WEAK, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight())
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
                                .add(WeightedItemData.create(EquipmentItemsME.GOBLIN_TOWN_SANDALS).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                        )
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

        SCOUT = new NpcData(NpcRegistry.GOBLIN_TOWN_SCOUT.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WEAK, List.of(
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
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
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

        WARRIOR = new NpcData(NpcRegistry.GOBLIN_TOWN_WARRIOR.getValue(), RaceRegistry.GOBLIN, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, List.of(
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

        RIDER = new NpcData(NpcRegistry.GOBLIN_TOWN_RIDER.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, List.of(
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
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
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

        VETERAN = new NpcData(NpcRegistry.GOBLIN_TOWN_VETERAN.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, List.of(
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
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEG_BRACER))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CHIEF_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_CRUDE_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKBLADE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_HOOKAXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORIA_GOBLIN_SPEAR))
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
    }
}
