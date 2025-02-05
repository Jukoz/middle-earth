package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.ModToolItems;
import net.sevenstars.middleearth.item.ModWeaponItems;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class RohirricNpcDataPool {
    private final static String FACTION_BASE = "rohan.";

    private final static int LIGHT_GREEN = 0x516c42;
    private final static int DARK_GREEN = 0x2d4122;
    private final static int LIGHT_RED = 0x7f4442;
    private final static int DARK_RED = 0x56302d;
    private final static int LIGHT_BEIGE = 0xa39269;
    private final static int DARK_BEIGE = 0x827052;
    private final static int LIGHT_BROWN = 0x69594d;
    private final static int DARK_BROWN = 0x44382f;

    public final static NpcData ROHAN_MILITIA;
    public final static NpcData ROHAN_SOLDIER;
    public final static NpcData ROHAN_KNIGHT;
    public final static NpcData ROHAN_ROYAL_GUARD;
    public final static NpcData ROHAN_EORLING_MARSHAL;
    public final static NpcData ROHAN_HORSE_LORD;

    public static List<NpcData> fetchAll() {
        return List.of(
                ROHAN_MILITIA,
                ROHAN_SOLDIER,
                ROHAN_KNIGHT,
                ROHAN_ROYAL_GUARD,
                ROHAN_EORLING_MARSHAL,
                ROHAN_HORSE_LORD
        );
    }

    static {
        ROHAN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_HELMET).withWeight(6))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_ORNAMENTED_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.ROHIRRIC_CAPE).withColor(LIGHT_GREEN).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.ROHIRRIC_CAPE).withColor(DARK_GREEN).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.ROHIRRIC_CAPE).withColor(LIGHT_BROWN).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.ROHIRRIC_CAPE).withColor(DARK_BROWN).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.ROHIRRIC_CAPE).withColor(LIGHT_BEIGE).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.ROHIRRIC_CAPE).withColor(DARK_BEIGE).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(LIGHT_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(LIGHT_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BROWN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.TRAVELLING_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModToolItems.BRONZE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.KITE_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.HEATER_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))
                                .add(NpcGearItemData.create().withWeight(5))
                        )
        ));
        ROHAN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_MILITIA_HELMET).withWeight(6))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(DARK_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(LIGHT_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(LIGHT_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_GAMBESON).withColor(DARK_BROWN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.TRAVELLING_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_BUCKING_HORSE_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_GALLOPING_HORSE_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_HORSE_HEAD_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_PLAINSMAN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_TWIN_HORSES_SHIELD))
                                .add(NpcGearItemData.create().withWeight(10))
                        )
        ));
        ROHAN_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_SOLDIER_HELMET).withWeight(6))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_MILITIA_HELMET).withWeight(6))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_VEST).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_VEST).withColor(DARK_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST).withColor(DARK_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_BRACED_MAIL_SHIRT).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_BRACED_MAIL_SHIRT).withColor(DARK_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_RED))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_RED))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.TRAVELLING_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.HIGH_CUT_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_BUCKING_HORSE_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_GALLOPING_HORSE_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_HORSE_HEAD_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_PLAINSMAN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_TWIN_HORSES_SHIELD))
                        )
        ));
        ROHAN_ROYAL_GUARD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "royal_guard"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK).withWeight(6))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_SCALE_JACKET)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.HIGH_CUT_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_NOBLE_SPEAR).withWeight(6))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_ROYAL_GUARD_SHIELD)))
        ));
        ROHAN_EORLING_MARSHAL = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "eorling_marhsal"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.EORLING_MARSHAL_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.EORLING_MARSHAL_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.EORLING_MARSHAL_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.EORLING_MARSHAL_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_EORLING_SHIELD))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ));
        ROHAN_HORSE_LORD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "horse_lord"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.HORSE_LORD_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.HORSE_LORD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.HORSE_LORD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.HORSE_LORD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROHIRRIC_ORNAMENTED_SHIELD))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ));

    }
}
