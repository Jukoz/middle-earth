package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
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

public class BanditNpcDataPool {
    private final static String FACTION_BASE = "bandit.";
    private final static int DEFAULT = 0x43403a;
    private final static int DARKEST_RED = 0x321111;
    private final static int DARK_BROWN = 0x2b1c17;
    private final static int LIGHT_BROWN = 0x412f26;
    private final static int DARK_ORANGE = 0x5d3309;
    private final static int DARK_GREEN = 0x1b3220;
    private final static int GREEN = 0x283d1a;
    private final static int OLIVE_GREEN = 0x3c3e2a;
    private final static int DARK_BLUE = 0x1e1e32;
    private final static int BLUE = 0x294141;
    private final static int PINK = 0x5d2b52;

    public final static NpcData BANDIT_THUG;
    public final static NpcData BANDIT_THIEF_GREEN;
    public final static NpcData BANDIT_THIEF_DEFAULT;

    public static List<NpcData> fetchAll() {
        return List.of(
                BANDIT_THUG
        );
    }

    private static final List<Integer> allColors = List.of(DEFAULT, DARK_BROWN, LIGHT_BROWN, DARK_GREEN, DARK_BLUE, DARKEST_RED, GREEN, BLUE, DARK_ORANGE);
    static {
        BANDIT_THUG = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thug"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(6))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON_CAP).withColors(allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_VEST).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SCALE_VEST).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.SURCOAT, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.MAIL_SKIRT).withWeight(3))

                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_SKIRT).withColors(allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ELVEN_ARMING_COAT).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ELVEN_ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ELVEN_ARMING_COAT).withColors(allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.ELVEN_ARMING_COAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.HEATER_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_DAGGER))
                                .add(NpcGearItemData.create(ModWeaponItems.BRONZE_DAGGER))
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER))
                        )
        ));

        BANDIT_THIEF_GREEN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thief_green"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withHood(ModHoods.HOOD, OLIVE_GREEN))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withCape(ModCapes.CLOAK, OLIVE_GREEN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ));
        BANDIT_THIEF_DEFAULT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thief_default"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withHood(ModHoods.HOOD, DEFAULT))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withCape(ModCapes.CLOAK, DEFAULT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.IRON_DAGGER))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ));
    }
}
