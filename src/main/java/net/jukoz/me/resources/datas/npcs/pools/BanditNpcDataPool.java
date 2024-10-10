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
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class BanditNpcDataPool {
    public final static NpcData BANDIT_MILITIA;
    public final static NpcData BANDIT_THIEF;
    private final static String FACTION_BASE = "bandit.";
    static {
        BANDIT_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(Items.LEATHER_HELMET))
                                .add(new NpcGearItemData(ModEquipmentItems.BYCOCKET))
                                .add(new NpcGearItemData(ModEquipmentItems.WOVEN_HAT))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_CAP))
                                .add(new NpcGearItemData(ModEquipmentItems.GAMBESON_COWL))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GAMBESON)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.ROUND_SHIELD)))
        ));
        BANDIT_THIEF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thief"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.FUR_CLOAK)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.IRON_DAGGER)))
        ));
    }
}
