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

public class IsengardNpcDataPool {
    private final static String FACTION_BASE = "isengard.";
    private static List<Integer> allColors;
    private static final int DARK = 0x302b28;
    private static final int DARK_BROWN = 0x655147;
    public final static NpcData ISENGARD_ORTHANC_GUARD;
    public final static NpcData ISENGARD_ORC_SNAGA;
    public final static NpcData ISENGARD_ORC_WARRIOR;
    public final static NpcData ISENGARD_URUK_HAI_MILITIA;

    public static List<NpcData> fetchAll() {
        return List.of(
                ISENGARD_ORTHANC_GUARD,
                ISENGARD_ORC_SNAGA,
                ISENGARD_ORC_WARRIOR,
                ISENGARD_URUK_HAI_MILITIA
        );
    }

    static {
        allColors = List.of(DARK, DARK_BROWN);

        ISENGARD_ORTHANC_GUARD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orthanc_guard"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORTHANC_GUARD_HELMET).withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORTHANC_GUARD_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORTHANC_GUARD_CHESTPLATE).withCape(ModCapes.ORTHANC_GUARD_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORTHANC_GUARD_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORTHANC_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.URUK_HAI_SPEAR))
                        )
        ));

        ISENGARD_ORC_SNAGA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_snaga"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
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
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.URUK_HAI_BOOTS)))
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
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ));


        ISENGARD_ORC_WARRIOR = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_warrior"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
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
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.URUK_HAI_BOOTS)))
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
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ));
        ISENGARD_URUK_HAI_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "uruk_hai_militia"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.MAIL_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
        ));
    }
}
