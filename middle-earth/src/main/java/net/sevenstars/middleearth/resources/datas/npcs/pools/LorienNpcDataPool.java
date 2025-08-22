package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

public class LorienNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionsME.LOTHLORIEN;
    private final static String FACTION_BASE = FACTION.getValue().getPath() + ".%s";

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
                LOTHLORIEN_LORD);
    }
    static {
        LOTHLORIEN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("militia")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.SHOES)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD)))
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_RANGER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("ranger")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_COAT).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_COAT).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_RANGER_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("ranger_archer")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD, false))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_COAT).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_COAT).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_BOW))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("soldier")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MAIL_HAUBERK).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_GLAIVE))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_MALLORN_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("knight")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_SCALE_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_GLAIVE))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_MALLORN_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_KNIGHT_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("knight_archer")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_SCALE_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LONGBOW))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_LONGBOW))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_BOW))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("veteran")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_CHESTPLATE).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_CHESTPLATE).withCape(BackAttachmentsME.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_GLAIVE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GALADHRIM_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_MALLORN_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        LOTHLORIEN_LORD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("lord")), RacesME.ELF, FACTION, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE).withCape(BackAttachmentsME.GALADHRIM_LORD_SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GALADHRIM_LORD_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GALADHRIM_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);
    }
}
