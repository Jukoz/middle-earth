package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoods;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
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

    public final static NpcData WILD_GOBLIN_GATHERER;
    public final static NpcData WILD_GOBLIN_WARRIOR;
    public final static NpcData WILD_GOBLIN_SCOUT;

    public static List<NpcData> fetchAll() {
        return List.of(
                BANDIT_THUG,
                BANDIT_THIEF,
                BANDIT_MERCENARY,
                BANDIT_CHIEFTAIN,
                WILD_GOBLIN_GATHERER,
                WILD_GOBLIN_WARRIOR,
                WILD_GOBLIN_SCOUT
        );
    }

    private static final List<Integer> allColors = List.of(DEFAULT, DARK_BROWN, LIGHT_BROWN, DARK_GREEN, DARK_BLUE, DARKEST_RED, GREEN, BLUE, DARK_ORANGE);
    static {
        BANDIT_THUG = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thug"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(6))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.WOVEN_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON_CAP).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON_CAP).withColors(allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withCape(ModCapes.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(ModCapes.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(ModCapes.SURCOAT, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MAIL_SKIRT).withWeight(3))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT).withColors(allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withColors(allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.SHOES))
                                .add(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.BRONZE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.BRONZE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.HEATER_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD).withWeight(4))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER))
                                .add(NpcGearItemData.create(WeaponItemsME.BRONZE_DAGGER))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_DAGGER))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        BANDIT_THIEF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "thief"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withHood(ModHoods.HOOD, allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withCape(ModCapes.CLOAK, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withCape(ModCapes.CLOAK, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withCape(ModCapes.CLOAK, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(ModCapes.CLOAK, allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.SHOES))
                                .add(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_DAGGER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_DAGGER))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        BANDIT_MERCENARY = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "mercenary"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.OPEN_FACE_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(ModCapes.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.TAN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.TAN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.BLACK_FUR))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withCape(ModCapes.CAPE, allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.SHOES))
                                .add(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.STEEL_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.STEEL_SPEAR))
                                .add(NpcGearItemData.create(ToolItemsME.STEEL_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.HEATER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ROUND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        BANDIT_CHIEFTAIN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "chieftain"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET).withColor(DEFAULT).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET).withColor(DEFAULT).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.SALLET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColor(DARK_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColor(LIGHT_BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET).withColor(DEFAULT))

                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withCape(ModCapes.CLOAK, DEFAULT).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withCape(ModCapes.CLOAK, DEFAULT).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(ModCapes.CAPE, allColors).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MAIL_SKIRT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_MAIL_COAT))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(ModCapes.CLOAK, DEFAULT))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withCape(ModCapes.CLOAK, DEFAULT))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColor(DARK_BROWN).withWeight(3).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColor(LIGHT_BROWN).withWeight(2).withCape(ModCapes.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withCape(ModCapes.CLOAK, DEFAULT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_REINFORCED_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.GONDORIAN_PLATE_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.STEEL_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.STEEL_SPEAR))
                                .add(NpcGearItemData.create(ToolItemsME.STEEL_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.HEATER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ROUND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);


        WILD_GOBLIN_GATHERER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "wild_goblin_gatherer"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColor(DARK_BROWN_GOBLIN).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.CAPE, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_CAPE).withColor(DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColor(DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS).withColor(DARK_BROWN_GOBLIN)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        WILD_GOBLIN_WARRIOR= new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "wild_goblin_warrior"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_LONG_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        WILD_GOBLIN_SCOUT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "wild_goblin_scout"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(ModCapes.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_BOW))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);
    }
}
