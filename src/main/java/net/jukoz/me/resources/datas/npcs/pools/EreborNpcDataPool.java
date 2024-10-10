package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class EreborNpcDataPool {
    public final static NpcData EREBOR_MILITIA;
    private final static int LIGHT_BLUE = 0x4b6274;
    private final static int DARK_BLUE = 0x223345;
    private final static int LIGHT_RED = 0x7f4442;
    private final static int DARK_RED = 0x56302d;
    private final static int LIGHT_BROWN = 0x6b463a;
    private final static int DARK_BROWN = 0x3b291e;
    private final static String FACTION_BASE = "longbeards.erebor.";

    static {
        EREBOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.DWARF, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(Items.LEATHER_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withColor(LIGHT_RED))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withColor(DARK_RED))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withColor(LIGHT_RED))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withColor(DARK_RED))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withColor(LIGHT_RED))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withColor(DARK_RED))

                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withColor(LIGHT_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withColor(DARK_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withColor(LIGHT_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withColor(DARK_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withColor(LIGHT_BLUE))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withColor(DARK_BLUE))

                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withColor(LIGHT_BROWN))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).withColor(DARK_BROWN))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withColor(LIGHT_BROWN))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).withColor(DARK_BROWN))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withColor(LIGHT_BROWN))
                                .add(new NpcGearItemData(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).withColor(DARK_BROWN))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModToolItems.BRONZE_AXE)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.EREBOR_SHIELD)))
        ));
    }
}
