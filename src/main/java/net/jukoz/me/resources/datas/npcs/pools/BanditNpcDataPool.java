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
import net.minecraft.entity.Npc;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class BanditNpcDataPool {
    public final static NpcData BANDIT_MILITIA;
    public final static NpcData BANDIT_THIEF;
    private final static String FACTION_BASE = "bandit.";
    private final static int DARK_BLUE = 0x2d3744;

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
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColor(DARK_BLUE).withHood(ModHoods.BASE_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColor(DARK_BLUE).withHood(ModHoods.BASE_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.BASE_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE).withWeight(3).withCape(ModCapes.BASE_CLOAK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BLUE).withWeight(2).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_BLUE).withCape(ModCapes.BASE_CLOAK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.LEATHER_LEGGINGS).withColor(DARK_BLUE))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.IRON_DAGGER)))
                ,
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withHood(ModHoods.BASE_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withHood(ModHoods.BASE_HOOD))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withHood(ModHoods.BASE_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withWeight(3).withCape(ModCapes.BASE_CLOAK))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withWeight(2).withCape(ModCapes.SURCOAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withCape(ModCapes.BASE_CLOAK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.LEATHER_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES))
                        )
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.IRON_DAGGER)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.IRON_DAGGER)))
        ));
    }
}
