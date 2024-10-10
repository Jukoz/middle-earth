package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class GondorianNpcDataPool {
    public final static NpcData GONDOR_MILITIA;
    public final static NpcData GONDOR_SOLDIER;
    public final static NpcData GONDOR_KNIGHT;
    public final static NpcData GONDOR_VETERAN;
    public final static NpcData GONDOR_FOUNTAIN_GUARDS;
    public final static NpcData GONDOR_CITADEL_GUARDS;
    public final static NpcData GONDOR_KING_GUARDS;
    public final static NpcData GONDOR_LEADER;


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

    private final static String FACTION_BASE = "gondor.";

    static {
        GONDOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HUMAN, List.of(

                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP))
                                // TODO : WAIT FOR FIX
                                //.add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP).withColor(DARK_BEIGE))
                                //.add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_BEIGE))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BLUE).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_ORANGE))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(DARK_ORANGE))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BEIGE))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_COAT).withColor(DARK_BEIGE))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_ORANGE))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_BEIGE))
                                .add(new NpcGearItemData().withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS))
                                .add(new NpcGearItemData().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SPEAR).withWeight(4))
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD).withWeight(3))
                                .add(new NpcGearItemData(ModToolItems.BRONZE_AXE).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(Items.IRON_SWORD))
                                .add(new NpcGearItemData(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KITE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.HEATER_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROUND_SHIELD))
                                .add(new NpcGearItemData().withWeight(5))
                        ),
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL).withColor(LIGHT_GRAY))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL).withColor(DARK_GRAY))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL).withColor(LIGHT_GREEN))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL).withColor(DARK_GREEN))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP).withColor(LIGHT_GRAY))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_GRAY))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP).withColor(LIGHT_GREEN))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_GREEN))
                                .add(new NpcGearItemData(ModEquipmentItems.OPEN_FACE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON).withColor(LIGHT_GRAY))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON).withColor(DARK_GRAY))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON).withColor(LIGHT_GREEN))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GRAY).withWeight(4))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GREEN).withWeight(4))
                                .add(new NpcGearItemData().withWeight(7))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS))
                                .add(new NpcGearItemData().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD))
                                .add(new NpcGearItemData(ModToolItems.BRONZE_AXE))
                                .add(new NpcGearItemData(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(new NpcGearItemData(Items.IRON_SWORD))
                                .add(new NpcGearItemData(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.KITE_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.HEATER_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.ROUND_SHIELD))
                                .add(new NpcGearItemData().withWeight(5))
                        )
        ));
        GONDOR_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CABASSET_HELMET))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SOLDIER_HELMET).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_TABBARD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GRAY).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.ARMING_SKIRT).withColor(DARK_GREEN).withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_AXE))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData())
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD))
                        )
        ));
        GONDOR_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_AXE))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD))
                        ),
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_TOWER_SHIELD))
                                .add(new NpcGearItemData().withWeight(3))
                        )
        ));
        GONDOR_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_HELMET).withWeight(10))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_HERO_SHIELD))
                                .add(new NpcGearItemData().withWeight(2))
                        )
        ));
        GONDOR_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "leader"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE).withWeight(3))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_HERO_SHIELD))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD))
                        )
        ));
        GONDOR_CITADEL_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "citadel_guards"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SWORD))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KNIGHT_SHIELD))
                        )
        ));
        GONDOR_FOUNTAIN_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "fountain_guards"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_FOUNTAIN_GUARD_SPEAR)))
        ));
        GONDOR_KING_GUARDS = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "king_guards"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_SPEAR).withWeight(3))
                                .add(new NpcGearItemData(ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).withWeight(8))
                                .add(new NpcGearItemData(ModEquipmentItems.GONDORIAN_TOWER_SHIELD))
                        )
        ));
    }
}
