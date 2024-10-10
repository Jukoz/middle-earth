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

public class LorienNpcDataPool {
    public final static NpcData LOTHLORIEN_MILITIA;
    private final static String FACTION_BASE = "lothlorien.";
    private final static int LIGHT_BLUE = 0x3a4250;
    private final static int DARK_BLUE = 0x252b3a;
    static {
        LOTHLORIEN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.ELF, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.LORIEN_LEATHER_HELMET)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.LORIEN_ARMING_COAT)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.LORIEN_SHIELD)))
        ));
    }
}
