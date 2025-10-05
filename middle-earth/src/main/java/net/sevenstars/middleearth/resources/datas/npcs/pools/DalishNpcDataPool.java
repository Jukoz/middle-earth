package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.resources.*;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.List;
import java.util.stream.Stream;

public class DalishNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionsME.DALE;
    private final static String FACTION_BASE = FACTION.getValue().getPath() + ".%s";

    public final static NpcData DALE_CITIZEN;
    public final static NpcData DALE_WORKER;

    public final static NpcData DALE_MILITIA;
    public final static NpcData DALE_SOLDIER;
    public final static NpcData DALE_SOLDIER_ARCHER;
    public final static NpcData DALE_KNIGHT;
    public final static NpcData DALE_KNIGHT_ARCHER;
    public final static NpcData DALE_VETERAN;
    public final static NpcData DALE_SERGEANT;

    public static List<NpcData> fetchAll() {
        return List.of(
                DALE_CITIZEN,
                DALE_WORKER,
                DALE_MILITIA,
                DALE_SOLDIER,
                DALE_SOLDIER_ARCHER,
                DALE_KNIGHT,
                DALE_KNIGHT_ARCHER,
                DALE_VETERAN,
                DALE_SERGEANT);
    }


    static {
        NpcTextureDataPreset BASE_MALE_DALE_TEXTURE_PRESET = NpcTextureDataPool.HUMAN_MALE.copy()
                .clearMaterials(NpcTextureType.SKIN)
                .withMaterials(NpcTextureType.SKIN, List.of(
                        NpcTextureMaterialsME.Skin.PALE,
                        NpcTextureMaterialsME.Skin.BEIGE
                ))
                .clearMaterials(NpcTextureType.HAIR)
                .withMaterials(NpcTextureType.HAIR, List.of(
                        NpcTextureMaterialsME.Hair.COLD_BLACK_COPPER,
                        NpcTextureMaterialsME.Hair.BLACK_COPPER,
                        NpcTextureMaterialsME.Hair.DARK_BROWN_COPPER,
                        NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                        NpcTextureMaterialsME.Hair.GREASY_BEADS,
                        NpcTextureMaterialsME.Hair.GREASY_COPPER,
                        NpcTextureMaterialsME.Hair.BROWN_COPPER,
                        NpcTextureMaterialsME.Hair.BROWN_BEADS,
                        NpcTextureMaterialsME.Hair.GRAY_BEADS,
                        NpcTextureMaterialsME.Hair.GRAY_COPPER,
                        NpcTextureMaterialsME.Hair.WHITE_COPPER,
                        NpcTextureMaterialsME.Hair.WHITE_BEADS
                ))
                .clearPatterns(NpcTextureType.HAIR)
                .withPatterns(NpcTextureType.HAIR, List.of(
                        NpcTexturePatternsME.Hairs.Hair.BOWL,
                        NpcTexturePatternsME.Hairs.Hair.SHORT,
                        NpcTexturePatternsME.Hairs.Hair.SHARP,
                        NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                        NpcTexturePatternsME.Hairs.Hair.UNCUT,
                        NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED
                ))
                .clearPatterns(NpcTextureType.BEARD)
                .withPatterns(NpcTextureType.BEARD, Stream.of(
                        null,
                        NpcTexturePatternsME.Hairs.Beard.SHORT,
                        NpcTexturePatternsME.Hairs.Beard.SINGLE,
                        NpcTexturePatternsME.Hairs.Beard.SEMI_LONG
                ).toList())
                .clearPatterns(NpcTextureType.CLOTHING)
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        NpcTexturePatternsME.Clothing.PANTS
                ));

        NpcTextureDataPreset BASE_FEMALE_DALE_TEXTURE_PRESET = BASE_MALE_DALE_TEXTURE_PRESET.copy()
                .clearPatterns(NpcTextureType.HAIR)
                .clearPatterns(NpcTextureType.BEARD)
                .withPatterns(NpcTextureType.HAIR, List.of(
                        NpcTexturePatternsME.Hairs.Hair.LONG,
                        NpcTexturePatternsME.Hairs.Hair.FLAT_LONG,
                        NpcTexturePatternsME.Hairs.Hair.SEMI_LONG
                ));

        DALE_CITIZEN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("citizen")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(NpcGearItemData.create().withWeight(5))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.STONE_AXE))
                                .add(NpcGearItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(NpcGearItemData.create(ToolItemsME.PIPE))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_WORKER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("worker")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.STONE_AXE))
                                .add(NpcGearItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(NpcGearItemData.create(ToolItemsME.PIPE))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("militia")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR))
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR))
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(NpcGearItemData.create(Items.SHIELD))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("soldier")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_SOLDIER_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("soldier_archer")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR).withCape(BackAttachmentsME.SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR).withCape(BackAttachmentsME.SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR).withCape(BackAttachmentsME.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGBOW))
                                .add(NpcGearItemData.create(Items.BOW))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_KNIGHT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("knight")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_KNIGHT_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("knight_archer")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGBOW))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);
        DALE_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("veteran")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(NpcGearItemData.create().withWeight(1))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(BackAttachmentsME.BARDING_SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK).withCape(BackAttachmentsME.BARDING_SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);

        DALE_SERGEANT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("sergeant")), RacesME.HUMAN, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_HELMET).withWeight(3))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE).withWeight(3).withCape(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), NpcME.COMMON_NPC_ATTRIBUTES);
    }
}
