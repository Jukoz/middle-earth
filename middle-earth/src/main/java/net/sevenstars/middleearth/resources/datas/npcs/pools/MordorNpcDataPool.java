package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.NpcTextureMaterialsME;
import net.sevenstars.middleearth.resources.NpcTexturePatternsME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class MordorNpcDataPool {
    private final static String FACTION_BASE = "mordor.";

    private static final int DARK_RED = 0x4f251d;
    private static final int DARK_BROWN = 0x4a3c34;
    private static final int DARK = 0x1d1f1e;

    private static List<Integer> allColors;

    public final static NpcData MORDOR_BLACK_NUMENOREAN;
    public final static NpcData MORDOR_ORC_SNAGA;
    public final static NpcData MORDOR_ORC_MILITIA;
    public final static NpcData MORDOR_ORC_SCOUT;
    public final static NpcData MORDOR_ORC_SOLDIER;
    public final static NpcData MORDOR_BLACK_URUK_SOLDIER;
    public final static NpcData MORDOR_BLACK_URUK_VETERAN;
    public final static NpcData MORDOR_BLACK_URUK_LEADER;
    public final static NpcData MORDOR_BLACK_URUK_VETERAN_ARCHER;


    public static List<NpcData> fetchAll() {
        return List.of(
                MORDOR_BLACK_NUMENOREAN,
                MORDOR_ORC_SNAGA,
                MORDOR_ORC_MILITIA,
                MORDOR_ORC_SCOUT,
                MORDOR_ORC_SOLDIER,
                MORDOR_BLACK_URUK_SOLDIER,
                MORDOR_BLACK_URUK_VETERAN,
                MORDOR_BLACK_URUK_VETERAN_ARCHER,
                MORDOR_BLACK_URUK_LEADER
        );
    }
    static {
        allColors = List.of(DARK_RED, DARK_BROWN, DARK);

        MORDOR_BLACK_NUMENOREAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_numenorean"), RacesME.HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET).withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.MORDOR_BLACK_NUMENOREAN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.BLACK_NUMENOREAN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(
                    new NpcTextureDataPreset()
                            .withMaterials(NpcTextureType.SKIN, List.of(
                                    NpcTextureMaterialsME.Skin.OLIVE
                            ))
                            .withPatterns(NpcTextureType.BODY, List.of(
                                    NpcTexturePatternsME.Skins.Body.MUSCULAR
                            ))
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    NpcTexturePatternsME.Skins.Head.MALE
                            ))
                            .withPatterns(NpcTextureType.SCAR, List.of(
                                    NpcTexturePatternsME.Skins.Scar.EYE_RIGHT
                            ))
                            .withPatterns(NpcTextureType.EAR, List.of(
                                    NpcTexturePatternsME.Skins.Ear.WIDE_POINTY
                            ))
                            .withMaterials(NpcTextureType.EYE, List.of(
                                    NpcTextureMaterialsME.Eye.BROWN
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    NpcTexturePatternsME.Eyes.Eye.COMMON
                            ))
                            .withMaterials(NpcTextureType.HAIR, List.of(
                                    NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.GINGER_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.GRAY_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.STRAW_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.BLACK_ALMANDINE
                            ))
                            .withPatterns(NpcTextureType.EYEBROW, List.of(
                                    NpcTexturePatternsME.Hairs.Eyebrow.UNI,
                                    NpcTexturePatternsME.Hairs.Eyebrow.BASIC,
                                    NpcTexturePatternsME.Hairs.Eyebrow.SHORT
                            ))
                            .withPatterns(NpcTextureType.HAIR, Stream.of(
                                    NpcTexturePatternsME.Hairs.Hair.BOWL,
                                    NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                                    NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP,
                                    NpcTexturePatternsME.Hairs.Hair.SHORT,
                                    NpcTexturePatternsME.Hairs.Hair.TOP_BALDING,
                                    NpcTexturePatternsME.Hairs.Hair.SIDE_BALDING,
                                    null).toList()
                            )
                            .withPatterns(NpcTextureType.BEARD, Stream.of(
                                    NpcTexturePatternsME.Hairs.Beard.CLEAN,
                                    NpcTexturePatternsME.Hairs.Beard.SHORT,
                                    NpcTexturePatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                                    null).toList()
                            )
                            .withMaterials(NpcTextureType.CLOTHING, List.of(
                                    NpcTextureMaterialsME.Clothing.GRAY
                            ))
                            .withPatterns(NpcTextureType.CLOTHING, List.of(
                                    NpcTexturePatternsME.Clothing.TOGA,
                                    NpcTexturePatternsME.Clothing.FULL_TOGA,
                                    NpcTexturePatternsME.Clothing.ROBE,
                                    NpcTexturePatternsME.Clothing.SKIRT
                            ))
            ));
            put(EntityCategory.FEMALE, List.of(
                    new NpcTextureDataPreset()
                            .withMaterials(NpcTextureType.SKIN, List.of(
                                    NpcTextureMaterialsME.Skin.OLIVE
                            ))
                            .withPatterns(NpcTextureType.BODY, List.of(
                                    NpcTexturePatternsME.Skins.Body.SLIM,
                                    NpcTexturePatternsME.Skins.Body.FEMALE
                            ))
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    NpcTexturePatternsME.Skins.Head.FEMALE
                            ))
                            .withPatterns(NpcTextureType.EAR, List.of(
                                    NpcTexturePatternsME.Skins.Ear.LARGE_POINTY
                            ))
                            .withMaterials(NpcTextureType.EYE, List.of(
                                    NpcTextureMaterialsME.Eye.BLUE,
                                    NpcTextureMaterialsME.Eye.GREEN,
                                    NpcTextureMaterialsME.Eye.DARK_GREEN,
                                    NpcTextureMaterialsME.Eye.NAVY,
                                    NpcTextureMaterialsME.Eye.BROWN
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    NpcTexturePatternsME.Eyes.Eye.COMMON
                            ))
                            .withMaterials(NpcTextureType.HAIR, List.of(
                                    NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.BLACK_ALMANDINE
                            ))
                            .withPatterns(NpcTextureType.EYEBROW, List.of(
                                    NpcTexturePatternsME.Hairs.Eyebrow.BASIC,
                                    NpcTexturePatternsME.Hairs.Eyebrow.SHORT
                            ))
                            .withPatterns(NpcTextureType.HAIR, List.of(
                                    NpcTexturePatternsME.Hairs.Hair.LONG,
                                    NpcTexturePatternsME.Hairs.Hair.FLAT_LONG,
                                    NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP
                            ))
                            .withMaterials(NpcTextureType.CLOTHING, List.of(
                                    NpcTextureMaterialsME.Clothing.WHITE,
                                    NpcTextureMaterialsME.Clothing.BROWN
                            ))
                            .withPatterns(NpcTextureType.CLOTHING, List.of(
                                    NpcTexturePatternsME.Clothing.FULL_TOGA,
                                    NpcTexturePatternsME.Clothing.SKIRT_WITH_STROPHIUM
                            ))
            ));
        }}));

        MORDOR_ORC_SNAGA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_snaga"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
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

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD))
                        )
        ), new HashMap<>(), new NpcTextureData(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(
                    new NpcTextureDataPreset()
                            .withMaterials(NpcTextureType.SKIN, List.of(
                                    NpcTextureMaterialsME.Skin.GREENISH,
                                    NpcTextureMaterialsME.Skin.OLIVE,
                                    NpcTextureMaterialsME.Skin.TAN_DESATURATED,
                                    NpcTextureMaterialsME.Skin.BROWN
                            ))
                            .withPatterns(NpcTextureType.BODY, List.of(
                                    NpcTexturePatternsME.Skins.Body.MUSCULAR
                            ))
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL,
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_THICK_BROW,
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_WISE,
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_VERY_WIDE
                            ))
                            .withPatterns(NpcTextureType.EAR, List.of(
                                    NpcTexturePatternsME.Skins.Ear.SQUARE_POINTY,
                                    NpcTexturePatternsME.Skins.Ear.WIDE_POINTY,
                                    NpcTexturePatternsME.Skins.Ear.LARGE_POINTY
                            ))
                            .withMaterials(NpcTextureType.EYE, List.of(
                                    NpcTextureMaterialsME.Eye.YELLOW
                            ))
                            .withEmissiveEyes(true)
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    NpcTexturePatternsME.Eyes.Eye.SMALL_LOW_WIDE
                            ))
                            .withMaterials(NpcTextureType.HAIR, List.of(
                                    NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.BROWN_BEADS,
                                    NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                                    NpcTextureMaterialsME.Hair.BLACK_BEADS
                            ))
                            .withPatterns(NpcTextureType.HAIR, Stream.of(
                                    NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                                    NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP,
                                    NpcTexturePatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS,
                                    NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                    NpcTexturePatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                    NpcTexturePatternsME.Hairs.Hair.UNCUT,
                                    NpcTexturePatternsME.Hairs.Hair.TOP_BALDING,
                                    NpcTexturePatternsME.Hairs.Hair.SIDE_BALDING,
                                    null).toList()
                            )
                            .withMaterials(NpcTextureType.CLOTHING, List.of(
                                    NpcTextureMaterialsME.Clothing.BROWN,
                                    NpcTextureMaterialsME.Clothing.ROT_GREEN
                                    ))
                            .withPatterns(NpcTextureType.CLOTHING, List.of(
                                    NpcTexturePatternsME.Clothing.SKIRT
                            ))
            ));
            put(EntityCategory.FEMALE, List.of(
                    new NpcTextureDataPreset()
                            .withMaterials(NpcTextureType.SKIN, List.of(
                                    NpcTextureMaterialsME.Skin.GREENISH,
                                    NpcTextureMaterialsME.Skin.OLIVE,
                                    NpcTextureMaterialsME.Skin.TAN_DESATURATED,
                                    NpcTextureMaterialsME.Skin.BROWN
                            ))
                            .withPatterns(NpcTextureType.BODY, List.of(
                                    NpcTexturePatternsME.Skins.Body.SLIM
                            ))
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL,
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_THICK_BROW,
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_WISE,
                                    NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_VERY_WIDE
                            ))
                            .withPatterns(NpcTextureType.EAR, List.of(
                                    NpcTexturePatternsME.Skins.Ear.POINTY,
                                    NpcTexturePatternsME.Skins.Ear.WIDE_POINTY,
                                    NpcTexturePatternsME.Skins.Ear.LARGE_POINTY
                            ))
                            .withMaterials(NpcTextureType.EYE, List.of(
                                    NpcTextureMaterialsME.Eye.YELLOW
                            ))
                            .withEmissiveEyes(true)
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    NpcTexturePatternsME.Eyes.Eye.SMALL_LOW_WIDE
                            ))
                            .withMaterials(NpcTextureType.HAIR, List.of(
                                    NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,
                                    NpcTextureMaterialsME.Hair.BROWN_BEADS,
                                    NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                                    NpcTextureMaterialsME.Hair.BLACK_BEADS
                            ))
                            .withPatterns(NpcTextureType.HAIR, Stream.of(
                                    NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                                    NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP,
                                    NpcTexturePatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS,
                                    NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                    NpcTexturePatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                    NpcTexturePatternsME.Hairs.Hair.UNCUT,
                                    NpcTexturePatternsME.Hairs.Hair.TOP_BALDING,
                                    NpcTexturePatternsME.Hairs.Hair.SIDE_BALDING).toList()
                            )
                            .withMaterials(NpcTextureType.CLOTHING, List.of(
                                    NpcTextureMaterialsME.Clothing.BROWN,
                                    NpcTextureMaterialsME.Clothing.ROT_GREEN
                            ))
                            .withPatterns(NpcTextureType.CLOTHING, List.of(
                                    NpcTexturePatternsME.Clothing.SKIRT_WITH_STROPHIUM
                            ))
            ));
        }}));
        MORDOR_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_militia"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.SURCOAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_AXE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_SWORD).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ROUND_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        MORDOR_ORC_SCOUT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_scout"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withHood(HelmetAttachmentsME.HOOD, allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.SURCOAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BOW).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(3))
                                .add(NpcGearItemData.create(Items.BOW).withWeight(15))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        MORDOR_ORC_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "orc_soldier"), RacesME.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_NASAL_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_NASAL_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_KETTLE_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_MANDIBLE_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_SALLET).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CREST_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CUIRASS).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_AXE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_FALCHION).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_SWORD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_AXE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_SPEAR).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_LARGE_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);


        MORDOR_BLACK_URUK_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_uruk_soldier"), RacesME.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_NASAL_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_NASAL_HELMET).withWeight(2).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_KETTLE_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_MANDIBLE_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_SALLET).withHood(HelmetAttachmentsME.HOOD, allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CREST_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_AXE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_FALCHION).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_SWORD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_AXE).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.ORC_SPEAR).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_LARGE_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        MORDOR_BLACK_URUK_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_uruk_veteran"), RacesME.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ELITE_CLEAVER))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ELITE_WARBLADE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_HEAVY_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        MORDOR_BLACK_URUK_VETERAN_ARCHER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_uruk_veteran_archer"), RacesME.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ELITE_LONGBOW))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);

        MORDOR_BLACK_URUK_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "black_uruk_leader"), RacesME.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_HELMET)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE).withCape(BackAttachmentsME.CAPE, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ELITE_CLEAVER))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_ELITE_WARBLADE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_HEAVY_SHIELD).withWeight(2))
                                .add(NpcGearItemData.create(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD))
                        )
        ), new HashMap<>(), NpcME.COMMON_TEXTURE_TEST);
    }
}
