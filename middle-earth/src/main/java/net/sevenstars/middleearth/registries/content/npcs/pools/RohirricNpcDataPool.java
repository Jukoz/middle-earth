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
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class RohirricNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.ROHAN;

    private final static MeleeCombatArchetypeData temporaryArchetypeData = new MeleeCombatArchetypeData(0.3f);

    private final static int LIGHT_GREEN = 0x516c42;
    private final static int DARK_GREEN = 0x2d4122;
    private final static int LIGHT_RED = 0x7f4442;
    private final static int DARK_RED = 0x56302d;
    private final static int LIGHT_BEIGE = 0xa39269;
    private final static int DARK_BEIGE = 0x827052;
    private final static int LIGHT_BROWN = 0x69594d;
    private final static int DARK_BROWN = 0x44382f;

    public final static NpcData PEASANT;
    public final static NpcData MILITIA;
    public final static NpcData SOLDIER;
    public final static NpcData KNIGHT;
    public final static NpcData ROYAL_GUARD;
    public final static NpcData EORLING_MARSHAL;
    public final static NpcData HORSE_LORD;


    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_SOLDIER, SOLDIER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_KNIGHT, KNIGHT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_ROYAL_GUARD, ROYAL_GUARD),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_EORLING_MARSHAL, EORLING_MARSHAL),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.ROHAN_HORSE_LORD, HORSE_LORD)
        );
    }


    static {
        PEASANT = new NpcData(NpcRegistry.ROHAN_PEASANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(WeightedItemData.create().withWeight(5))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.WORK_SHOES)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.STONE_AXE))
                                .add(WeightedItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(WeightedItemData.create(ToolItemsME.PIPE))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);

        MILITIA = new NpcData(NpcRegistry.ROHAN_MILITIA.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_HELMET).withWeight(6))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_ORNAMENTED_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.ROHIRRIC_CAPE).withColor(LIGHT_GREEN).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.ROHIRRIC_CAPE).withColor(DARK_GREEN).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.ROHIRRIC_CAPE).withColor(LIGHT_BROWN).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.ROHIRRIC_CAPE).withColor(DARK_BROWN).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.ROHIRRIC_CAPE).withColor(LIGHT_BEIGE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.ROHIRRIC_CAPE).withColor(DARK_BEIGE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT).withColor(DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT).withColor(LIGHT_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT).withColor(LIGHT_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BROWN))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(ToolItemsME.BRONZE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.IRON_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                                .add(WeightedItemData.create(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.KITE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(WeightedItemData.create().withWeight(5))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);

        SOLDIER = new NpcData(NpcRegistry.ROHAN_SOLDIER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_MILITIA_HELMET).withWeight(6))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_BRACED_MILITIA_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_MILITIA_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_ORNAMENTED_MILITIA_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_GAMBESON).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_GAMBESON).withColor(DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_GAMBESON).withColor(LIGHT_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_GAMBESON).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_GAMBESON).withColor(LIGHT_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_GAMBESON).withColor(DARK_BROWN))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD))
                                .add(WeightedItemData.create().withWeight(10))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);

        KNIGHT = new NpcData(NpcRegistry.ROHAN_KNIGHT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_SOLDIER_HELMET).withWeight(6))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_MILITIA_HELMET).withWeight(6))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_BRACED_MILITIA_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_MILITIA_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_ORNAMENTED_MILITIA_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_VEST).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_VEST).withColor(DARK_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST).withColor(DARK_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_BRACED_MAIL_SHIRT).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_BRACED_MAIL_SHIRT).withColor(DARK_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_RED))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_RED))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);

        ROYAL_GUARD = new NpcData(NpcRegistry.ROHAN_ROYAL_GUARD.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK).withWeight(6))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.HIGH_CUT_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_NOBLE_SPEAR).withWeight(6))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create(WeightedItemData.create(WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD)))
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);

        EORLING_MARSHAL = new NpcData(NpcRegistry.ROHAN_EORLING_MARSHAL.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_LORD, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.EORLING_MARSHAL_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.EORLING_MARSHAL_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.EORLING_MARSHAL_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.EORLING_MARSHAL_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_NOBLE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_EORLING_SHIELD))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);

        HORSE_LORD = new NpcData(NpcRegistry.ROHAN_HORSE_LORD.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.ROHAN_LORD, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.HORSE_LORD_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.HORSE_LORD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.HORSE_LORD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.HORSE_LORD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, temporaryArchetypeData);
    }
}
