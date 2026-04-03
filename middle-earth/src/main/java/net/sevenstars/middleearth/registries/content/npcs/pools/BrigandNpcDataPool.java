package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class BrigandNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.BRIGAND;

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


    public final static NpcData THUG;
    public final static NpcData THIEF;
    public final static NpcData MERCENARY;
    public final static NpcData CHIEFTAIN;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
            new NpcRegistry.RegisterableNpcData(NpcRegistry.BRIGAND_THUG, THUG),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.BRIGAND_THIEF, THIEF),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.BRIGAND_MERCENARY, MERCENARY),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.BRIGAND_CHIEFTAIN, CHIEFTAIN)
        );
    }

    private static final List<Integer> allColors = List.of(DEFAULT, DARK_BROWN, LIGHT_BROWN, DARK_GREEN, DARK_BLUE, DARKEST_RED, GREEN, BLUE, DARK_ORANGE);
    static {
        THUG = new NpcData(NpcRegistry.BRIGAND_THUG.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_THUG, List.of(
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
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.SURCOAT, allColors))
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        THIEF = new NpcData(NpcRegistry.BRIGAND_THIEF.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_THUG, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withHood(HelmetAttachmentsME.HOOD, allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_VEST).withCape(BackAttachmentsME.CLOAK, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CLOAK, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withCape(BackAttachmentsME.CLOAK, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CLOAK, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.CLOAK, allColors))
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MERCENARY = new NpcData(NpcRegistry.BRIGAND_MERCENARY.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_MERCENARY, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.OPEN_FACE_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.TAN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.TAN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.BLACK_FUR))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.CAPE, allColors))
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        CHIEFTAIN = new NpcData(NpcRegistry.BRIGAND_CHIEFTAIN.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_CHIEF, List.of(
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
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CLOAK, DEFAULT).withCape(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CLOAK, DEFAULT).withCape(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MAIL_SKIRT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DWARVEN_MAIL_COAT))

                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.CLOAK, DEFAULT))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withCape(BackAttachmentsME.CLOAK, DEFAULT))

                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withCape(BackAttachmentsME.CLOAK, DEFAULT))
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
