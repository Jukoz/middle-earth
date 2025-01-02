package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.Identifier;

import java.util.List;

public class ShireNpcDataPool {
    private final static String FACTION_BASE = "shire.";
    private final static int DARK_BEIGE = 0xa89371;
    private final static int DARK_GREEN = 0x336339;
    public final static NpcData SHIRE_MILITIA;
    public final static NpcData SHIRE_SHIRRIF;

    public static List<NpcData> fetchAll() {
        return List.of(
                SHIRE_MILITIA,
                SHIRE_SHIRRIF
        );
    }

    static {
        SHIRE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HOBBIT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BEIGE)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD)))
        ));

        SHIRE_SHIRRIF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "shirrif"), MiddleEarthRaces.HOBBIT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.SHIRRIFF_HAT)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SURCOAT).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.SURCOAT).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModToolItems.PIPE)))
        ));
    }
}
