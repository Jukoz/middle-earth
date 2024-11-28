package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
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
    public final static NpcData BANDIT_THUG;
    public final static NpcData BANDIT_THIEF;
    private final static String FACTION_BASE = "bandit.";
    private final static int DARK_BLUE = 0x2d3744;
    private final static int OLIVE_GREEN = 0x3c3e2a;
    private final static int DARK_BROWN = 0x2b1c17;

    static {
        BANDIT_THUG = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thug"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.LEATHER_HELMET).withHood(ModHoods.HOOD, true))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withHood(ModHoods.HOOD, true))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withHood(ModHoods.HOOD, true))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET))
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withoutCape())
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withCape(ModCapes.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))),
            NpcGearData.create()
                    .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                            .add(NpcGearItemData.create(Items.LEATHER_HELMET).withHood(ModHoods.HOOD, true).withColor(DARK_BLUE))
                            .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withHood(ModHoods.HOOD, true).withColor(DARK_BLUE))
                            .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withHood(ModHoods.HOOD, true).withColor(DARK_BLUE))
                            .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(DARK_BLUE))
                            .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT))
                            .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                    )
                    .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                            .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BLUE).withCape(ModCapes.SURCOAT))
                            .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE))
                            .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE).withCape(ModCapes.SURCOAT))
                            .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BLUE).withoutCape())
                            .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BLUE).withCape(ModCapes.SURCOAT))
                    )
                    .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(DARK_BLUE)))
                    .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                    .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
                    .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))),
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.LEATHER_HELMET).withHood(ModHoods.HOOD, true).withColor(OLIVE_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withHood(ModHoods.HOOD, true).withColor(OLIVE_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withHood(ModHoods.HOOD, true).withColor(OLIVE_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(OLIVE_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(OLIVE_GREEN).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(OLIVE_GREEN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(OLIVE_GREEN).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(OLIVE_GREEN).withoutCape())
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(OLIVE_GREEN).withCape(ModCapes.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(OLIVE_GREEN)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD))),
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.LEATHER_HELMET).withHood(ModHoods.HOOD, true).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withHood(ModHoods.HOOD, true).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_COWL).withHood(ModHoods.HOOD, true).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BROWN).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BROWN).withoutCape())
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).withColor(DARK_BROWN).withCape(ModCapes.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(DARK_BROWN)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD)))
        ));
        BANDIT_THIEF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thief"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColor(DARK_BLUE).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(DARK_BLUE).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE).withWeight(3).withCape(ModCapes.CLOAK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE).withWeight(2).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_BLUE).withCape(ModCapes.CLOAK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(DARK_BLUE)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                ,
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColor(OLIVE_GREEN).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(OLIVE_GREEN).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(OLIVE_GREEN).withWeight(3).withCape(ModCapes.CLOAK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(OLIVE_GREEN).withWeight(2).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(OLIVE_GREEN).withCape(ModCapes.CLOAK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(OLIVE_GREEN)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                ,
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColor(DARK_BROWN).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(DARK_BROWN).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(2).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN).withCape(ModCapes.CLOAK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(DARK_BROWN)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                ,
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withHood(ModHoods.HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withWeight(3).withCape(ModCapes.CLOAK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withWeight(2).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withCape(ModCapes.CLOAK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(Items.LEATHER_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER)))
        ));
    }
}
