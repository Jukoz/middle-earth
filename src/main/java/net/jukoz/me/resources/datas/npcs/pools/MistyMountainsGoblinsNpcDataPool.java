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

public class MistyMountainsGoblinsNpcDataPool {
    public final static NpcData MISTY_GOBLIN_MILITIA;
    public final static NpcData MISTY_HOBGOBLIN_MILITIA;

    private final static String FACTION_BASE = "misty_mountains_goblins.";
    static {
        MISTY_GOBLIN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "goblin_militia"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORC_SALLET)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORC_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
        ));
        MISTY_HOBGOBLIN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "hobgoblin_militia"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORC_SALLET)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORC_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
        ));
    }
}
