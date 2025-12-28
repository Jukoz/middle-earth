package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.NpcTextureDatasME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class IsengardNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.ISENGARD;
    private final static String FACTION_BASE = FACTION.getValue().getPath() + ".%s";

    private static List<Integer> allColors;
    private static final int DARK = 0x302b28;
    private static final int DARK_BROWN = 0x655147;
    public final static NpcData ISENGARD_ORTHANC_GUARD;
    public final static NpcData ISENGARD_ORC_SNAGA;
    public final static NpcData ISENGARD_ORC_WARRIOR;
    public final static NpcData ISENGARD_URUK_HAI_SOLDIER;
    public final static NpcData ISENGARD_URUK_HAI_SCOUT;
    public final static NpcData ISENGARD_URUK_HAI_VETERAN;
    public final static NpcData ISENGARD_URUK_HAI_BERSERKER;
    public final static NpcData ISENGARD_URUK_HAI_LEADER;

    public static List<NpcData> fetchAll() {
        return List.of(
                ISENGARD_ORTHANC_GUARD,
                ISENGARD_ORC_SNAGA,
                ISENGARD_ORC_WARRIOR,
                ISENGARD_URUK_HAI_SOLDIER,
                ISENGARD_URUK_HAI_SCOUT,
                ISENGARD_URUK_HAI_VETERAN,
                ISENGARD_URUK_HAI_BERSERKER,
                ISENGARD_URUK_HAI_LEADER
        );
    }

    static {
        allColors = List.of(DARK, DARK_BROWN);

        ISENGARD_ORTHANC_GUARD = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("orthanc_guard")), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORTHANC_GUARD_HELMET).withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE).withCape(BackAttachmentsME.ORTHANC_GUARD_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORTHANC_GUARD_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORTHANC_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_SPEAR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ISENGARD_ORC_SNAGA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("orc_snaga")), RaceRegistry.ORC, FACTION, NpcTextureDatasME.ISENGARD_ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.HOOD).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.WOODEN_SPEAR).withWeight(6))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);


        ISENGARD_ORC_WARRIOR = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("orc_warrior")), RaceRegistry.ORC, FACTION, NpcTextureDatasME.ISENGARD_ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LIGHT_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SALLET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_CLEAVER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_AXE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(6))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ISENGARD_URUK_HAI_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("uruk_hai_soldier")), RaceRegistry.URUK, FACTION, NpcTextureDatasME.ISENGARD_URUK_HAI, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PLATE_HELMET).withWeight(5))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LIGHT_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_SAPPER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_WARBLADE))
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_CLEAVER))
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.ISENGARD_ORC_SPEAR))

                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ISENGARD_URUK_HAI_SCOUT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("uruk_hai_scout")), RaceRegistry.URUK, FACTION, NpcTextureDatasME.ISENGARD_URUK_HAI, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LIGHT_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_REINFORCED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_LIGHT_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_MAIL_SKIRT))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_BOW).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_FALCHION))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_SPEAR).withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ISENGARD_URUK_HAI_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("uruk_hai_veteran")), RaceRegistry.URUK, FACTION, NpcTextureDatasME.ISENGARD_URUK_HAI, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_PLATE_HELMET).withWeight(5))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_SAPPER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_CUIRASS))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.MAIL_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WARBLADE))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_HEATER_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);


        ISENGARD_URUK_HAI_BERSERKER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("uruk_hai_berserker")), RaceRegistry.URUK, FACTION, NpcTextureDatasME.ISENGARD_URUK_HAI, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_BERSERKER_HELMET))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WARBLADE))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(DecorativeItemsME.TORCH_OF_ORTHANC).withWeight(4))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ISENGARD_URUK_HAI_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("uruk_hai_leader")), RaceRegistry.URUK, FACTION, NpcTextureDatasME.ISENGARD_URUK_HAI, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PAINTED_COMMANDER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE).withCape(BackAttachmentsME.CAPE, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.MAIL_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.URUK_HAI_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_WARBLADE))
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.URUK_HAI_HEATER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
