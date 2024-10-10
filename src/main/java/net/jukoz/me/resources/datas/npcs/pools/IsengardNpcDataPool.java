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

public class IsengardNpcDataPool {
    public final static NpcData ISENGARD_ORC_MILITIA;
    public final static NpcData ISENGARD_URUK_HAI_MILITIA;

    private final static String FACTION_BASE = "isengard.";
    static {
        ISENGARD_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ORC_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD)))
        ));
        ISENGARD_URUK_HAI_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "uruk_hai_militia"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ORC_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD)))
        ));
    }
}
