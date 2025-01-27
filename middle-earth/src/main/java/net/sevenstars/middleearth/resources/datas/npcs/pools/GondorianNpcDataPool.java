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

public class GondorianNpcDataPool {
    private final static String FACTION_BASE = "gondor.";

    private final static int LIGHT_BLUE = 0x2b3965;
    private final static int DARK_BLUE = 0x182038;
    private final static int LIGHT_ORANGE = 0xc67640;
    private final static int DARK_ORANGE = 0x82492c;
    private final static int LIGHT_BEIGE = 0xe8cb9b;
    private final static int DARK_BEIGE = 0xa89371;
    private final static int LIGHT_GRAY = 0x68697d;
    private final static int DARK_GRAY = 0x555666;
    private final static int LIGHT_GREEN = 0x435353;
    private final static int DARK_GREEN = 0x262f2f;

    public final static NpcData GONDOR_MILITIA;
    public final static NpcData GONDOR_SOLDIER;
    public final static NpcData GONDOR_KNIGHT;
    public final static NpcData GONDOR_VETERAN;
    public final static NpcData GONDOR_FOUNTAIN_GUARDS;
    public final static NpcData GONDOR_CITADEL_GUARDS;
    public final static NpcData GONDOR_KING_GUARDS;
    public final static NpcData GONDOR_LEADER;


    public static List<NpcData> fetchAll() {
        return List.of(
                GONDOR_MILITIA,
                GONDOR_SOLDIER,
                GONDOR_KNIGHT,
                GONDOR_VETERAN,
                GONDOR_FOUNTAIN_GUARDS,
                GONDOR_CITADEL_GUARDS,
                GONDOR_KING_GUARDS,
                GONDOR_LEADER
                );
    }

    static {
        GONDOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HUMAN, List.of(

                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_BEIGE))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BLUE).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_ORANGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_ORANGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BEIGE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_BLUE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_ORANGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_BOOTS))
                                .add(NpcGearItemData.create().withWeight(2))
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
                        ),
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withColor(LIGHT_GRAY))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withColor(DARK_GRAY))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withColor(DARK_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(LIGHT_GRAY))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_GRAY))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.OPEN_FACE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(LIGHT_GRAY))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_GRAY))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(LIGHT_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GRAY).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GREEN).withWeight(4))
                                .add(NpcGearItemData.create().withWeight(7))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_BOOTS))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD))
                                .add(NpcGearItemData.create(ModToolItems.BRONZE_AXE))
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
        GONDOR_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CABASSET_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_SOLDIER_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_TABBARD))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GRAY).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GREEN).withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_AXE))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create())
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SHIELD))
                        )
        ));
        GONDOR_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_MAIL_COAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SHIELD))
                        ),
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_TOWER_SHIELD))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ));
        GONDOR_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_HELMET).withWeight(10))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_HERO_SHIELD))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
        ));
        GONDOR_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "leader"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE).withCape(ModCapes.GONDORIAN_HERO_CAPE).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE).withCape(ModCapes.GONDORIAN_HERO_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE).withCape(ModCapes.GONDORIAN_HERO_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_HERO_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD))
                        )
        ));
        GONDOR_CITADEL_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "citadel_guards"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_KNIGHT_SHIELD))
                        )
        ));
        GONDOR_FOUNTAIN_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "fountain_guards"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.GONDORIAN_FOUNTAIN_GUARD_SPEAR)))
        ));
        GONDOR_KING_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "king_guards"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).withWeight(8))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_TOWER_SHIELD))
                        )
        ));
    }
}
