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

    public static List<NpcData> fetchAll() {
        return List.of(
                DALE_MILITIA,
                DALE_SOLDIER,
                DALE_SOLDIER_ARCHER
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
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))
                                .add(NpcGearItemData.create(Items.SHIELD))
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
    }
}
