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

public class ShireNpcDataPool {
    public final static NpcData SHIRE_MILITIA;
    private final static String FACTION_BASE = "shire.";
    private final static int DARK_BEIGE = 0xa89371;
    static {
        SHIRE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HOBBIT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BEIGE)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD)))
        ));
    }
}
