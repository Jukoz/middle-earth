package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.Identifier;

import java.util.List;

public class LorienNpcDataPool {
    private final static String FACTION_BASE = "lothlorien.";
    private final static int LIGHT_BLUE = 0x3a4250;
    private final static int DARK_BLUE = 0x252b3a;
    public final static NpcData LOTHLORIEN_MILITIA;
    public final static NpcData LOTHLORIEN_RANGER;
    public final static NpcData LOTHLORIEN_RANGER_ARCHER;
    public final static NpcData LOTHLORIEN_SOLDIER;
    public final static NpcData LOTHLORIEN_KNIGHT;
    public final static NpcData LOTHLORIEN_KNIGHT_ARCHER;
    public final static NpcData LOTHLORIEN_VETERAN;
    public final static NpcData LOTHLORIEN_LORD;

    public static List<NpcData> fetchAll() {
        return List.of(
                LOTHLORIEN_MILITIA,
                LOTHLORIEN_RANGER,
                LOTHLORIEN_RANGER_ARCHER,
                LOTHLORIEN_SOLDIER,
                LOTHLORIEN_KNIGHT,
                LOTHLORIEN_KNIGHT_ARCHER,
                LOTHLORIEN_VETERAN,
                LOTHLORIEN_LORD
                );
    }
    static {
        LOTHLORIEN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM).withHood(ModHoods.LORIEN_MARCHWARDEN_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ELVEN_ARMING_COAT).withCape(ModCapes.LORIEN_MARCHWARDEN_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ELVEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.SHOES)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.LORIEN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.LORIEN_SHIELD)))
        ));

        LOTHLORIEN_RANGER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "ranger"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM).withHood(ModHoods.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SHORT_MAIL_COIF_DIADEM))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_COAT).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_COAT).withCape(ModCapes.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SHIELD))
                        )
        ));

        LOTHLORIEN_RANGER_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "ranger_archer"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM).withHood(ModHoods.LORIEN_MARCHWARDEN_HOOD, false))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_COAT).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_COAT).withCape(ModCapes.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_BOW))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        LOTHLORIEN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_DIADEM).withHood(ModHoods.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SHORT_MAIL_COIF_DIADEM))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MAIL_HAUBERK).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(ModCapes.GALADHRIM_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(ModCapes.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_GLAIVE))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SPEAR))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_MALLORN_SHIELD))
                        )
        ));

        LOTHLORIEN_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(ModCapes.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(ModCapes.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.LORIEN_SCALE_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_GLAIVE))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SPEAR))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_MALLORN_SHIELD))
                        )
        ));

        LOTHLORIEN_KNIGHT_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight_archer"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(ModCapes.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(ModCapes.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.LORIEN_SCALE_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_LONGBOW))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_LONGBOW))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_BOW))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        LOTHLORIEN_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_CHESTPLATE).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_CHESTPLATE).withCape(ModCapes.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(ModCapes.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_GLAIVE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GALADHRIM_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_MALLORN_SHIELD))
                        )
        ));

        LOTHLORIEN_LORD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "lord"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LORD_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE).withCape(ModCapes.GALADHRIM_LORD_SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE).withCape(ModCapes.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LORD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GALADHRIM_LORD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.LORIEN_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GALADHRIM_LORD_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GALADHRIM_SHIELD))
                        )
        ));
    }
}
