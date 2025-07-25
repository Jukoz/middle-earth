package net.sevenstars.middleearth.resources.datas.npcs.pools;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.resources.NpcTextureMaterialsME;
import net.sevenstars.middleearth.resources.NpcTexturePatternsME;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.List;
import java.util.stream.Stream;

public class NpcTextureDataPool {
public final static List<RegistryKey<NpcTextureMaterial>> DEFAULT_HAIR;

    public final static NpcTextureDataPreset HUMAN_MALE;
    public final static NpcTextureDataPreset HUMAN_FEMALE;

    public static class Dwarves{
        public final static NpcTextureDataPreset DWARF_MALE;
        public final static NpcTextureDataPreset DWARF_FEMALE;

        static {
            DWARF_MALE = new NpcTextureDataPreset()
                    .withMaterials(NpcTextureType.SKIN, List.of(
                            NpcTextureMaterialsME.Skin.TAN,
                            NpcTextureMaterialsME.Skin.TAN_DESATURATED,
                            NpcTextureMaterialsME.Skin.PALE,
                            NpcTextureMaterialsME.Skin.DEFAULT
                    ))
                    .withPatterns(NpcTextureType.BODY, List.of(
                            NpcTexturePatternsME.Skins.Body.MUSCULAR,
                            NpcTexturePatternsME.Skins.Body.FAT
                    ))
                    .withPatterns(NpcTextureType.HEAD, List.of(
                            NpcTexturePatternsME.Skins.Head.MALE
                    ))
                    .withPatterns(NpcTextureType.NOSE, List.of(
                            NpcTexturePatternsME.Skins.Nose.CUBE
                    ))
                    .withPatterns(NpcTextureType.EAR, List.of(
                            NpcTexturePatternsME.Skins.Ear.SQUARE,
                            NpcTexturePatternsME.Skins.Ear.NORMAL
                    ))
                    .withMaterials(NpcTextureType.EYE, List.of(
                            NpcTextureMaterialsME.Eye.BLUE,
                            NpcTextureMaterialsME.Eye.GREEN,
                            NpcTextureMaterialsME.Eye.DARK_GREEN,
                            NpcTextureMaterialsME.Eye.BLACK,
                            NpcTextureMaterialsME.Eye.NAVY,
                            NpcTextureMaterialsME.Eye.BROWN
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(NpcTexturePatternsME.Eyes.Eye.COMMON))
                    .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                    .withPatterns(NpcTextureType.EYEBROW, List.of(
                            NpcTexturePatternsME.Hairs.Eyebrow.UNI,
                            NpcTexturePatternsME.Hairs.Eyebrow.BASIC,
                            NpcTexturePatternsME.Hairs.Eyebrow.SHORT,
                            NpcTexturePatternsME.Hairs.Eyebrow.THICK,
                            NpcTexturePatternsME.Hairs.Eyebrow.LONG
                    ))
                    .withPatterns(NpcTextureType.HAIR, Stream.of(
                            NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                            NpcTexturePatternsME.Hairs.Hair.SHARP,
                            NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                            NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP,
                            NpcTexturePatternsME.Hairs.Hair.SHORT,
                            NpcTexturePatternsME.Hairs.Hair.TOP_BALDING,
                            NpcTexturePatternsME.Hairs.Hair.SIDE_BALDING,
                            null).toList()
                    )
                    .withPatterns(NpcTextureType.BEARD, Stream.of(
                            NpcTexturePatternsME.Hairs.Beard.BROAD,
                            NpcTexturePatternsME.Hairs.Beard.CHUNKY_BRAIDS,
                            NpcTexturePatternsME.Hairs.Beard.CLEAN,
                            NpcTexturePatternsME.Hairs.Beard.DUAL_LARGE_ORNAMENTED,
                            NpcTexturePatternsME.Hairs.Beard.DUAL_ORNAMENTED,
                            NpcTexturePatternsME.Hairs.Beard.LARGE,
                            NpcTexturePatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                            NpcTexturePatternsME.Hairs.Beard.SHORT,
                            NpcTexturePatternsME.Hairs.Beard.SINGLE,
                            NpcTexturePatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                            NpcTexturePatternsME.Hairs.Beard.VERY_BROAD).toList())
                    .withMaterials(NpcTextureType.CLOTHING, List.of(
                            NpcTextureMaterialsME.Clothing.BLUE_AND_COPPER,
                            NpcTextureMaterialsME.Clothing.BROWN
                    ))
                    .withPatterns(NpcTextureType.CLOTHING, List.of(
                            NpcTexturePatternsME.Clothing.DWARVEN_GARMENT,
                            NpcTexturePatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS,
                            NpcTexturePatternsME.Clothing.PANTS
                    ));
            DWARF_FEMALE = new NpcTextureDataPreset()
                    .withMaterials(NpcTextureType.SKIN, List.of(
                            NpcTextureMaterialsME.Skin.TAN,
                            NpcTextureMaterialsME.Skin.TAN_DESATURATED,
                            NpcTextureMaterialsME.Skin.PALE,
                            NpcTextureMaterialsME.Skin.DEFAULT
                    ))
                    .withPatterns(NpcTextureType.BODY, List.of(
                            NpcTexturePatternsME.Skins.Body.FEMALE,
                            NpcTexturePatternsME.Skins.Body.SLIM,
                            NpcTexturePatternsME.Skins.Body.FAT
                    ))
                    .withPatterns(NpcTextureType.HEAD, List.of(
                            NpcTexturePatternsME.Skins.Head.FEMALE
                    ))
                    .withPatterns(NpcTextureType.EAR, List.of(
                            NpcTexturePatternsME.Skins.Ear.SQUARE,
                            NpcTexturePatternsME.Skins.Ear.NORMAL
                    ))
                    .withMaterials(NpcTextureType.EYE, List.of(
                            NpcTextureMaterialsME.Eye.BLUE,
                            NpcTextureMaterialsME.Eye.GREEN,
                            NpcTextureMaterialsME.Eye.DARK_GREEN,
                            NpcTextureMaterialsME.Eye.BLACK,
                            NpcTextureMaterialsME.Eye.NAVY,
                            NpcTextureMaterialsME.Eye.BROWN
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                            NpcTexturePatternsME.Eyes.Eye.COMMON
                    ))
                    .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                    .withPatterns(NpcTextureType.EYEBROW, List.of(
                            NpcTexturePatternsME.Hairs.Eyebrow.UNI,
                            NpcTexturePatternsME.Hairs.Eyebrow.BASIC,
                            NpcTexturePatternsME.Hairs.Eyebrow.SHORT,
                            NpcTexturePatternsME.Hairs.Eyebrow.THICK,
                            NpcTexturePatternsME.Hairs.Eyebrow.LONG
                    ))
                    .withPatterns(NpcTextureType.HAIR, Stream.of(
                                    NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                    NpcTexturePatternsME.Hairs.Hair.LONG,
                                    NpcTexturePatternsME.Hairs.Hair.FLAT_LONG,
                                    NpcTexturePatternsME.Hairs.Hair.VERY_LONG
                            ).toList()
                    )
                    .withPatterns(NpcTextureType.BEARD, Stream.of(
                            NpcTexturePatternsME.Hairs.Beard.CLEAN,
                            NpcTexturePatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                            NpcTexturePatternsME.Hairs.Beard.SHORT).toList())
                    .withMaterials(NpcTextureType.CLOTHING, List.of(
                            NpcTextureMaterialsME.Clothing.BLUE_AND_COPPER,
                            NpcTextureMaterialsME.Clothing.WHITE,
                            NpcTextureMaterialsME.Clothing.GRAY
                    ))
                    .withPatterns(NpcTextureType.CLOTHING, List.of(
                            NpcTexturePatternsME.Clothing.DWARVEN_GARMENT,
                            NpcTexturePatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
                    ));
        }
    }


    static {
        // region HAIR
        DEFAULT_HAIR = List.of(
                NpcTextureMaterialsME.Hair.COLD_BLACK_COPPER,
                NpcTextureMaterialsME.Hair.COLD_BLACK_GOLD,
                NpcTextureMaterialsME.Hair.COLD_BLACK_SILVER,
                NpcTextureMaterialsME.Hair.COLD_BLACK_ALMANDINE,

                NpcTextureMaterialsME.Hair.BLACK_COPPER,
                NpcTextureMaterialsME.Hair.BLACK_GOLD,
                NpcTextureMaterialsME.Hair.BLACK_SILVER,
                NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,

                NpcTextureMaterialsME.Hair.BROWN_COPPER,
                NpcTextureMaterialsME.Hair.BROWN_GOLD,
                NpcTextureMaterialsME.Hair.BROWN_SILVER,
                NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,

                NpcTextureMaterialsME.Hair.DARK_BROWN_COPPER,
                NpcTextureMaterialsME.Hair.DARK_BROWN_GOLD,
                NpcTextureMaterialsME.Hair.DARK_BROWN_SILVER,
                NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,

                NpcTextureMaterialsME.Hair.STRAW_COPPER,
                NpcTextureMaterialsME.Hair.STRAW_GOLD,
                NpcTextureMaterialsME.Hair.STRAW_SILVER,
                NpcTextureMaterialsME.Hair.STRAW_ALMANDINE,
                NpcTextureMaterialsME.Hair.STRAW_BEADS,

                NpcTextureMaterialsME.Hair.GINGER_COPPER,
                NpcTextureMaterialsME.Hair.GINGER_GOLD,
                NpcTextureMaterialsME.Hair.GINGER_SILVER,
                NpcTextureMaterialsME.Hair.GINGER_ALMANDINE,
                NpcTextureMaterialsME.Hair.GINGER_BEADS,

                NpcTextureMaterialsME.Hair.WHITE_COPPER,
                NpcTextureMaterialsME.Hair.WHITE_GOLD,
                NpcTextureMaterialsME.Hair.WHITE_ALMANDINE,
                NpcTextureMaterialsME.Hair.WHITE_BEADS,

                NpcTextureMaterialsME.Hair.GRAY_COPPER,
                NpcTextureMaterialsME.Hair.GRAY_GOLD,
                NpcTextureMaterialsME.Hair.GRAY_ALMANDINE,
                NpcTextureMaterialsME.Hair.GRAY_BEADS
        );
        // endregion

        // region HUMAN
        HUMAN_MALE = new NpcTextureDataPreset()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        NpcTextureMaterialsME.Skin.TAN,
                        NpcTextureMaterialsME.Skin.TAN_DESATURATED,
                        NpcTextureMaterialsME.Skin.PALE,
                        NpcTextureMaterialsME.Skin.DEFAULT
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        NpcTexturePatternsME.Skins.Body.MUSCULAR
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        NpcTexturePatternsME.Skins.Head.MALE
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
                .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
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
                        NpcTexturePatternsME.Hairs.Beard.SINGLE,
                        NpcTexturePatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                        null).toList())
                .withMaterials(NpcTextureType.CLOTHING, List.of(
                        NpcTextureMaterialsME.Clothing.WHITE,
                        NpcTextureMaterialsME.Clothing.BROWN
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        NpcTexturePatternsME.Clothing.TOGA,
                        NpcTexturePatternsME.Clothing.FULL_TOGA,
                        NpcTexturePatternsME.Clothing.ROBE,
                        NpcTexturePatternsME.Clothing.SKIRT
                ));
        HUMAN_FEMALE = new NpcTextureDataPreset()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        NpcTextureMaterialsME.Skin.TAN,
                        NpcTextureMaterialsME.Skin.TAN_DESATURATED,
                        NpcTextureMaterialsME.Skin.PALE,
                        NpcTextureMaterialsME.Skin.DEFAULT
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        NpcTexturePatternsME.Skins.Body.MUSCULAR
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        NpcTexturePatternsME.Skins.Head.MALE
                ))
                .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                .withPatterns(NpcTextureType.EYE, List.of(
                        NpcTexturePatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        NpcTextureMaterialsME.Hair.BROWN_GOLD,
                        NpcTextureMaterialsME.Hair.DARK_BROWN_GOLD,
                        NpcTextureMaterialsME.Hair.GINGER_GOLD,
                        NpcTextureMaterialsME.Hair.GRAY_GOLD,
                        NpcTextureMaterialsME.Hair.STRAW_GOLD,
                        NpcTextureMaterialsME.Hair.BLACK_GOLD
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
                        NpcTexturePatternsME.Hairs.Beard.SINGLE,
                        NpcTexturePatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                        null).toList())
                .withMaterials(NpcTextureType.CLOTHING, List.of(
                        NpcTextureMaterialsME.Clothing.WHITE,
                        NpcTextureMaterialsME.Clothing.BROWN
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        NpcTexturePatternsME.Clothing.TOGA,
                        NpcTexturePatternsME.Clothing.FULL_TOGA,
                        NpcTexturePatternsME.Clothing.ROBE,
                        NpcTexturePatternsME.Clothing.SKIRT
                ));
        // endregion
    }
}
