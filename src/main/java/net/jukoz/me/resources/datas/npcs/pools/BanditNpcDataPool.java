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

    private static final int DARK_BROWN_GOBLIN = 0x4a3c34;

    public final static NpcData BANDIT_THUG;
    public final static NpcData BANDIT_THIEF;
    public final static NpcData BANDIT_MERCENARY;
    public final static NpcData BANDIT_CHIEFTAIN;
    public final static NpcData WILD_GOBLIN;

    public static List<NpcData> fetchAll() {
        return List.of(
                BANDIT_THUG,
                BANDIT_THIEF,
                BANDIT_MERCENARY,
                BANDIT_CHIEFTAIN,
                WILD_GOBLIN
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

        BANDIT_THIEF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thief"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withHood(ModHoods.HOOD, allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_VEST).withCape(ModCapes.CLOAK, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SCALE_VEST).withCape(ModCapes.CLOAK, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GAMBESON).withCape(ModCapes.CLOAK, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.CLOAK, allColors))
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

        BANDIT_MERCENARY = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "mercenary"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.OPEN_FACE_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.TAN_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.TAN_FUR))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.BLACK_FUR))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withCape(ModCapes.CAPE, allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES))
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.STEEL_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.STEEL_SPEAR))
                                .add(NpcGearItemData.create(ModToolItems.STEEL_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.HEATER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_ROUND_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_BRACED_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_WOODEN_SHIELD))
                        )
        ));

        BANDIT_CHIEFTAIN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "chieftain"), MiddleEarthRaces.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_LEATHER_HELMET).withColor(DEFAULT).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_BRACED_LEATHER_HELMET).withColor(DEFAULT).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.SALLET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_BRACED_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.KETTLE_HAT_WITH_CLOSED_COIF).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SEEKER_HELMET).withColor(DARK_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SEEKER_HELMET).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SEEKER_HELMET).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.EREBOR_NASAL_LEATHER_HELMET).withColor(DEFAULT))

                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withCape(ModCapes.CLOAK, DEFAULT).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST).withCape(ModCapes.CLOAK, DEFAULT).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.MAIL_SKIRT))
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_MAIL_COAT))

                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ARMING_COAT).withCape(ModCapes.CLOAK, DEFAULT))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_REINFORCED_LEATHER_SKIRT).withCape(ModCapes.CLOAK, DEFAULT))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withCape(ModCapes.CLOAK, DEFAULT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.DWARVEN_REINFORCED_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GONDORIAN_PLATE_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATED_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.STEEL_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.STEEL_SPEAR))
                                .add(NpcGearItemData.create(ModToolItems.STEEL_AXE))
                                .add(NpcGearItemData.create(ModWeaponItems.GONDORIAN_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.EREBOR_SWORD))
                                .add(NpcGearItemData.create(ModWeaponItems.DALISH_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.ROUND_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.HEATER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_ROUND_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_BRACED_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_WOODEN_SHIELD))
                        )
        ));


        WILD_GOBLIN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "wild_goblin"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColor(DARK_BROWN_GOBLIN).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withColor(DARK_BROWN_GOBLIN).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.CAPE, DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_CAPE).withColor(DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColor(DARK_BROWN_GOBLIN))
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
                                .add(NpcGearItemData.create(ModWeaponItems.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(5))
                        )
        ));
    }
}
