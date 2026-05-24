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
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class BrigandNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.BRIGAND;

    private final static int DEFAULT = 0x43403a;
    private final static int DARK_BROWN = 0x2b1c17;
    private final static int LIGHT_BROWN = 0x412f26;
    private final static int DESATURATED_BROWN = 0x5e503f;
    private final static int DESATURATED_GREEN = 0x515546;
    private final static int DESATURATED_RED = 0x554c46;

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

    private static final List<Integer> allColors = List.of(DEFAULT, DARK_BROWN, LIGHT_BROWN, DESATURATED_BROWN, DESATURATED_GREEN, DESATURATED_RED);
    static {
        THUG = new NpcData(NpcRegistry.BRIGAND_THUG.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_THUG, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create().withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.OPEN_FACE_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColor(DARK_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.CAPE).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.SHOULDER_CAPE_LEFT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.SHOULDER_CAPE_RIGHT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_VEST)
                                        .withCape(BackAttachmentsME.CAPE, DARK_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_VEST)
                                        .withCape(BackAttachmentsME.SHOULDER_CAPE_LEFT, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_VEST)
                                        .withCape(BackAttachmentsME.SHOULDER_CAPE_RIGHT, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT)
                                        .withCape(BackAttachmentsME.CAPE, DARK_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT)
                                        .withCape(BackAttachmentsME.SHOULDER_CAPE_LEFT, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT)
                                        .withCape(BackAttachmentsME.SHOULDER_CAPE_RIGHT, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.MAIL_SHIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.MAIL_HAUBERK)
                                    .withCape(BackAttachmentsME.SURCOAT, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.MAIL_SHIRT)
                                                .withCape(BackAttachmentsME.SURCOAT, allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.ROHIRRIC_AXE))
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                                .add(WeightedItemData.create(Items.STONE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.IRON_DAGGER))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        THIEF = new NpcData(NpcRegistry.BRIGAND_THIEF.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_THUG, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create().withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.HOOD))
                                .add(WeightedItemData.create(EquipmentItemsME.TALL_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.CAPE).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.SHOULDER_CAPE_LEFT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.SHOULDER_CAPE_RIGHT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT)
                                        .withCape(BackAttachmentsME.CAPE, DARK_BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT)
                                        .withCape(BackAttachmentsME.CLOAK, allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.IRON_DAGGER))
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_DAGGER))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.IRON_DAGGER))
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_DAGGER))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MERCENARY = new NpcData(NpcRegistry.BRIGAND_MERCENARY.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_MERCENARY, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.OPEN_FACE_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_VEST).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.TAN_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.TAN_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.BLACK_FUR))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.CAPE, allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.SHOES))
                                .add(WeightedItemData.create(EquipmentItemsME.STURDY_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.STEEL_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.STEEL_SPEAR))
                                .add(WeightedItemData.create(ToolItemsME.STEEL_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ROUND_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        CHIEFTAIN = new NpcData(NpcRegistry.BRIGAND_CHIEFTAIN.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.BRIGAND_CHIEF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_LEATHER_HELMET).withColor(DEFAULT).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET).withColor(DEFAULT).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.SALLET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColor(DARK_BROWN).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColor(LIGHT_BROWN).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_SEEKER_HELMET).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET).withColor(DEFAULT))

                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CLOAK, DEFAULT).withCape(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withCape(BackAttachmentsME.CLOAK, DEFAULT).withCape(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT).withCape(BackAttachmentsME.CAPE, allColors).withCape(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MAIL_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_MAIL_COAT))

                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withCape(BackAttachmentsME.CLOAK, DEFAULT))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT).withCape(BackAttachmentsME.CLOAK, DEFAULT))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColor(DARK_BROWN).withWeight(3).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withColor(LIGHT_BROWN).withWeight(2).withCape(BackAttachmentsME.CAPE, DEFAULT))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS).withCape(BackAttachmentsME.CLOAK, DEFAULT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DWARVEN_REINFORCED_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.STEEL_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.STEEL_SPEAR))
                                .add(WeightedItemData.create(ToolItemsME.STEEL_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.EREBOR_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ROUND_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
