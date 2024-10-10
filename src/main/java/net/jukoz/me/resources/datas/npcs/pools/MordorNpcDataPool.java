package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

import java.util.List;

public class MordorNpcDataPool {
    public final static NpcData MORDOR_ORC_MILITIA;
    public final static NpcData MORDOR_BLACK_URUK_MILITIA;
    private final static String FACTION_BASE = "mordor.";
    static {
        MORDOR_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ORC_MAIL_COIF)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ORC_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD)))
        ));
        MORDOR_BLACK_URUK_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_uruk_militia"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ORC_MAIL_COIF)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ORC_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD)))
        ));
    }
}
