package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.Identifier;

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
                DALE_SERGEANT
            );
    }


    static {
        DALE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                            .add(NpcGearItemData.create(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR))
                            .add(NpcGearItemData.create(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR))
                            .add(NpcGearItemData.create(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))
                                .add(NpcGearItemData.create(Items.SHIELD))
                        )
        ));

        DALE_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_SPEAR))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BLUE_OVAL_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BLUE_BRACED_SHIELD))
                        )
        ));
        DALE_SOLDIER_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "soldier_archer"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withCape(ModCapes.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_LONGBOW))
                                .add(NpcGearItemData.create(Items.BOW))
                        )
        ));

        DALE_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_BURGONET))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BARDING_BRACED_SHIELD))
                        )
        ));

        DALE_KNIGHT_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "knight_archer"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_HELMET_TAN_FUR))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_LONGBOW))
                        )
        ));

        DALE_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "veteran"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_BURGONET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE).withCape(ModCapes.BARDING_SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_SCALE_HAUBERK).withCape(ModCapes.BARDING_SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BARDING_BRACED_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ));

        DALE_SERGEANT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "sergeant"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SERGEANT_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE).withWeight(3).withCape(ModCapes.BARDING_SERGEANT_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE).withCape(ModCapes.BARDING_SERGEANT_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_ROYAL_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_ROYAL_ROUND_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ));
    }
}
