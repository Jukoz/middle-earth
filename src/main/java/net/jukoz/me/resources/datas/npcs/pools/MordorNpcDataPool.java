package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
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

public class MordorNpcDataPool {
    private final static String FACTION_BASE = "mordor.";

    private static final int DARK_RED = 0x4f251d;
    private static final int DARK_BROWN = 0x4a3c34;
    private static final int DARK = 0x1d1f1e;

    private static List<Integer> allColors;

    public final static NpcData MORDOR_ORC_SNAGA;
    public final static NpcData MORDOR_ORC_MILITIA;
    public final static NpcData MORDOR_ORC_SCOUT;
    public final static NpcData MORDOR_BLACK_URUK_MILITIA;

    public static List<NpcData> fetchAll() {
        return List.of(
                MORDOR_ORC_SNAGA,
                MORDOR_ORC_MILITIA,
                MORDOR_ORC_SCOUT,
                MORDOR_BLACK_URUK_MILITIA
        );
    }
    static {
        allColors = List.of(DARK_RED, DARK_BROWN, DARK);
        MORDOR_ORC_SNAGA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_snaga"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_HOE))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_WOODEN_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_PAINTED_WOODEN_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        MORDOR_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_militia"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_NASAL_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_HELMET).withWeight(2).withHood(ModHoods.HOOD, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_NASAL_HELMET).withWeight(2).withHood(ModHoods.HOOD, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_KETTLE_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_BRACED_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_HELMET).withHood(ModHoods.HOOD, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.SURCOAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_BLACK_FUR_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_AXE).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_SWORD).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_ROUND_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_PAINTED_ROUND_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_BLACK_ROUND_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));

        MORDOR_ORC_SCOUT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_scout"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_NASAL_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_HELMET).withWeight(2).withHood(ModHoods.HOOD, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_MORDOR_NASAL_HELMET).withWeight(2).withHood(ModHoods.HOOD, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_HELMET).withHood(ModHoods.HOOD, allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.SURCOAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_BLACK_FUR_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_BOW).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.ORCISH_BOW).withWeight(3))
                                .add(NpcGearItemData.create(Items.BOW).withWeight(15))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ));


        MORDOR_BLACK_URUK_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_uruk_militia"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.MAIL_COIF)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.MAIL_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
        ));
    }
}
