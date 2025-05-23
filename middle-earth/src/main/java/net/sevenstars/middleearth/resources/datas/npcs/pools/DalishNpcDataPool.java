package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

public class DalishNpcDataPool {
    private final static String FACTION_BASE = "dale.";

    public final static NpcData DALE_MILITIA;
    public final static NpcData DALE_SOLDIER;
    public final static NpcData DALE_SOLDIER_ARCHER;
    public final static NpcData DALE_KNIGHT;
    public final static NpcData DALE_KNIGHT_ARCHER;
    public final static NpcData DALE_VETERAN;
    public final static NpcData DALE_SERGEANT;

    public static List<NpcData> fetchAll() {
        return List.of(
                DALE_MILITIA,
                DALE_SOLDIER,
                DALE_SOLDIER_ARCHER,
                DALE_KNIGHT,
                DALE_KNIGHT_ARCHER,
                DALE_VETERAN,
                DALE_SERGEANT);
    }


    static {
        DALE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR))
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR))
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(NpcGearItemData.create(Items.SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        DALE_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);
        DALE_SOLDIER_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier_archer"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR).withCape(ModCapes.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGBOW))
                                .add(NpcGearItemData.create(Items.BOW))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        DALE_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        DALE_KNIGHT_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight_archer"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGBOW))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        DALE_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_BURGONET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(ModCapes.BARDING_SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK).withCape(ModCapes.BARDING_SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        DALE_SERGEANT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "sergeant"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE).withWeight(3).withCape(ModCapes.BARDING_SERGEANT_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(ModCapes.BARDING_SERGEANT_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);
    }
}
