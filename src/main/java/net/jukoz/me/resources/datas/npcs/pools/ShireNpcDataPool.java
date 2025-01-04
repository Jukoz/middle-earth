package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
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

public class ShireNpcDataPool {
    private final static String FACTION_BASE = "shire.";
    private final static int DARK_BEIGE = 0xa89371;
    private final static int DARK_GREEN = 0x336339;
    private final static int BROWN = 0x59341e;
    private final static int DARK = 0x342c27;

    public final static NpcData SHIRE_MILITIA;
    public final static NpcData SHIRE_SHIRRIFF;

    public static List<NpcData> fetchAll() {
        return List.of(
                SHIRE_MILITIA,
                SHIRE_SHIRRIFF
        );
    }

    static {
        SHIRE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "militia"), MiddleEarthRaces.HOBBIT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.STRAW_HAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK).withCape(ModCapes.CLOAK, DARK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BEIGE).withCape(ModCapes.CLOAK, DARK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(BROWN).withCape(ModCapes.CLOAK, DARK))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD))
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.STEEL_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))
                        )
        ));

        SHIRE_SHIRRIFF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "shirriff"), MiddleEarthRaces.HOBBIT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.SHIRRIFF_HAT)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SURCOAT).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(ModEquipmentItems.SURCOAT).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.STEEL_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModToolItems.PIPE).withWeight(3))
                                .add(NpcGearItemData.create(ModToolItems.CLAY_PIPE).withWeight(2))
                                .add(NpcGearItemData.create(ModToolItems.BRIMMINGBEND_PIPE))
                                .add(NpcGearItemData.create(Items.BOOK))
                        )
        ));
    }
}
