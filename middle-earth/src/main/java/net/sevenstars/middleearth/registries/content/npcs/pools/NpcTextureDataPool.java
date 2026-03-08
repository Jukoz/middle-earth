package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.races.data.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.List;
import java.util.stream.Stream;

public class NpcTextureDataPool {
public final static List<RegistryKey<NpcTextureMaterial>> DEFAULT_HAIR;

    public final static TexturePresetData HUMAN_COMMON;
    public final static TexturePresetData HUMAN_MALE;
    public final static TexturePresetData HUMAN_FEMALE;

    public static class Dwarves{
        public final static TexturePresetData DWARF_MALE;
        public final static TexturePresetData DWARF_FEMALE;

        static {
            DWARF_MALE = new TexturePresetData()
                    .withMaterials(NpcTextureType.SKIN, List.of(
                            CharacterMaterialsME.Skin.TAN,
                            CharacterMaterialsME.Skin.TAN_DESATURATED,
                            CharacterMaterialsME.Skin.PALE,
                            CharacterMaterialsME.Skin.BEIGE
                    ))
                    .withPatterns(NpcTextureType.BODY, List.of(
                            CharacterPatternsME.Skins.Body.MUSCULAR,
                            CharacterPatternsME.Skins.Body.FAT
                    ))
                    .withPatterns(NpcTextureType.HEAD, List.of(
                            CharacterPatternsME.Skins.Head.MALE
                    ))
                    .withPatterns(NpcTextureType.NOSE, List.of(
                            CharacterPatternsME.Skins.Nose.CUBE
                    ))
                    .withPatterns(NpcTextureType.EAR, List.of(
                            CharacterPatternsME.Skins.Ear.SQUARE,
                            CharacterPatternsME.Skins.Ear.NORMAL
                    ))
                    .withMaterials(NpcTextureType.EYE, List.of(
                            CharacterMaterialsME.Eye.BLUE,
                            CharacterMaterialsME.Eye.GREEN,
                            CharacterMaterialsME.Eye.DARK_GREEN,
                            CharacterMaterialsME.Eye.BLACK,
                            CharacterMaterialsME.Eye.NAVY,
                            CharacterMaterialsME.Eye.BROWN
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(CharacterPatternsME.Eyes.Eye.COMMON))
                    .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                    .withPatterns(NpcTextureType.EYEBROW, List.of(
                            CharacterPatternsME.Hairs.Eyebrow.UNI,
                            CharacterPatternsME.Hairs.Eyebrow.BASIC,
                            CharacterPatternsME.Hairs.Eyebrow.SHORT,
                            CharacterPatternsME.Hairs.Eyebrow.THICK,
                            CharacterPatternsME.Hairs.Eyebrow.LONG
                    ))
                    .withPatterns(NpcTextureType.HAIR, Stream.of(
                            CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                            CharacterPatternsME.Hairs.Hair.SHARP,
                            CharacterPatternsME.Hairs.Hair.BALD_SIDES,
                            CharacterPatternsME.Hairs.Hair.DIRTY_MOP,
                            CharacterPatternsME.Hairs.Hair.SHORT,
                            CharacterPatternsME.Hairs.Hair.TOP_BALDING,
                            CharacterPatternsME.Hairs.Hair.SIDE_BALDING,
                            null).toList()
                    )
                    .withPatterns(NpcTextureType.BEARD, Stream.of(
                            CharacterPatternsME.Hairs.Beard.BROAD,
                            CharacterPatternsME.Hairs.Beard.CHUNKY_BRAIDS,
                            CharacterPatternsME.Hairs.Beard.CLEAN,
                            CharacterPatternsME.Hairs.Beard.DUAL_LARGE_ORNAMENTED,
                            CharacterPatternsME.Hairs.Beard.DUAL_ORNAMENTED,
                            CharacterPatternsME.Hairs.Beard.LARGE,
                            CharacterPatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                            CharacterPatternsME.Hairs.Beard.SHORT,
                            CharacterPatternsME.Hairs.Beard.SINGLE,
                            CharacterPatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                            CharacterPatternsME.Hairs.Beard.VERY_BROAD).toList())
                    .withPatterns(NpcTextureType.CLOTHING, List.of(
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT,
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS,
                            //CharacterPatternsME.Clothing.PANTS
                    ));
            DWARF_FEMALE = new TexturePresetData()
                    .withMaterials(NpcTextureType.SKIN, List.of(
                            CharacterMaterialsME.Skin.TAN,
                            CharacterMaterialsME.Skin.TAN_DESATURATED,
                            CharacterMaterialsME.Skin.PALE,
                            CharacterMaterialsME.Skin.BEIGE
                    ))
                    .withPatterns(NpcTextureType.BODY, List.of(
                            CharacterPatternsME.Skins.Body.FEMALE,
                            CharacterPatternsME.Skins.Body.SLIM,
                            CharacterPatternsME.Skins.Body.FAT
                    ))
                    .withPatterns(NpcTextureType.HEAD, List.of(
                            CharacterPatternsME.Skins.Head.FEMALE
                    ))
                    .withPatterns(NpcTextureType.EAR, List.of(
                            CharacterPatternsME.Skins.Ear.SQUARE,
                            CharacterPatternsME.Skins.Ear.NORMAL
                    ))
                    .withMaterials(NpcTextureType.EYE, List.of(
                            CharacterMaterialsME.Eye.BLUE,
                            CharacterMaterialsME.Eye.GREEN,
                            CharacterMaterialsME.Eye.DARK_GREEN,
                            CharacterMaterialsME.Eye.BLACK,
                            CharacterMaterialsME.Eye.NAVY,
                            CharacterMaterialsME.Eye.BROWN
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                            CharacterPatternsME.Eyes.Eye.COMMON
                    ))
                    .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                    .withPatterns(NpcTextureType.EYEBROW, List.of(
                            CharacterPatternsME.Hairs.Eyebrow.UNI,
                            CharacterPatternsME.Hairs.Eyebrow.BASIC,
                            CharacterPatternsME.Hairs.Eyebrow.SHORT,
                            CharacterPatternsME.Hairs.Eyebrow.THICK,
                            CharacterPatternsME.Hairs.Eyebrow.LONG
                    ))
                    .withPatterns(NpcTextureType.HAIR, Stream.of(
                                    CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                    CharacterPatternsME.Hairs.Hair.LONG,
                                    CharacterPatternsME.Hairs.Hair.FLAT_LONG,
                                    CharacterPatternsME.Hairs.Hair.VERY_LONG
                            ).toList()
                    )
                    .withPatterns(NpcTextureType.BEARD, Stream.of(
                            CharacterPatternsME.Hairs.Beard.CLEAN,
                            CharacterPatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                            CharacterPatternsME.Hairs.Beard.SHORT).toList())
                    .withPatterns(NpcTextureType.CLOTHING, List.of(
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT,
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
                    ));
        }
    }


    static {
        // region HAIR
        DEFAULT_HAIR = List.of(
                CharacterMaterialsME.Hair.COLD_BLACK_COPPER,
                CharacterMaterialsME.Hair.COLD_BLACK_GOLD,
                CharacterMaterialsME.Hair.COLD_BLACK_SILVER,
                CharacterMaterialsME.Hair.COLD_BLACK_ALMANDINE,

                CharacterMaterialsME.Hair.BLACK_COPPER,
                CharacterMaterialsME.Hair.BLACK_GOLD,
                CharacterMaterialsME.Hair.BLACK_SILVER,
                CharacterMaterialsME.Hair.BLACK_ALMANDINE,

                CharacterMaterialsME.Hair.BROWN_COPPER,
                CharacterMaterialsME.Hair.BROWN_GOLD,
                CharacterMaterialsME.Hair.BROWN_SILVER,
                CharacterMaterialsME.Hair.BROWN_ALMANDINE,

                CharacterMaterialsME.Hair.DARK_BROWN_COPPER,
                CharacterMaterialsME.Hair.DARK_BROWN_GOLD,
                CharacterMaterialsME.Hair.DARK_BROWN_SILVER,
                CharacterMaterialsME.Hair.DARK_BROWN_ALMANDINE,

                CharacterMaterialsME.Hair.STRAW_COPPER,
                CharacterMaterialsME.Hair.STRAW_GOLD,
                CharacterMaterialsME.Hair.STRAW_SILVER,
                CharacterMaterialsME.Hair.STRAW_ALMANDINE,
                CharacterMaterialsME.Hair.STRAW_BEADS,

                CharacterMaterialsME.Hair.GINGER_COPPER,
                CharacterMaterialsME.Hair.GINGER_GOLD,
                CharacterMaterialsME.Hair.GINGER_SILVER,
                CharacterMaterialsME.Hair.GINGER_ALMANDINE,
                CharacterMaterialsME.Hair.GINGER_BEADS,

                CharacterMaterialsME.Hair.WHITE_COPPER,
                CharacterMaterialsME.Hair.WHITE_GOLD,
                CharacterMaterialsME.Hair.WHITE_ALMANDINE,
                CharacterMaterialsME.Hair.WHITE_BEADS,

                CharacterMaterialsME.Hair.GRAY_COPPER,
                CharacterMaterialsME.Hair.GRAY_GOLD,
                CharacterMaterialsME.Hair.GRAY_ALMANDINE,
                CharacterMaterialsME.Hair.GRAY_BEADS
        );
        // endregion

        // region HUMAN
        HUMAN_COMMON = new TexturePresetData()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsME.Skin.TAN,
                        CharacterMaterialsME.Skin.TAN_DESATURATED,
                        CharacterMaterialsME.Skin.PALE,
                        CharacterMaterialsME.Skin.BEIGE
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.BLUE,
                        CharacterMaterialsME.Eye.GREEN,
                        CharacterMaterialsME.Eye.DARK_GREEN,
                        CharacterMaterialsME.Eye.NAVY,
                        CharacterMaterialsME.Eye.BROWN
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.UNI,
                        CharacterPatternsME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        //CharacterPatternsME.Clothing.TOGA,
                        //CharacterPatternsME.Clothing.FULL_TOGA,
                        //CharacterPatternsME.Clothing.SKIRT
                ));
        HUMAN_MALE = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.UNI,
                        CharacterPatternsME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        CharacterPatternsME.Hairs.Hair.BOWL,
                        CharacterPatternsME.Hairs.Hair.BALD_SIDES,
                        CharacterPatternsME.Hairs.Hair.DIRTY_MOP,
                        CharacterPatternsME.Hairs.Hair.SHORT,
                        CharacterPatternsME.Hairs.Hair.TOP_BALDING,
                        CharacterPatternsME.Hairs.Hair.SIDE_BALDING,
                        null).toList()
                )
                .withPatterns(NpcTextureType.BEARD, Stream.of(
                        CharacterPatternsME.Hairs.Beard.CLEAN,
                        CharacterPatternsME.Hairs.Beard.SHORT,
                        CharacterPatternsME.Hairs.Beard.SINGLE,
                        CharacterPatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                        null).toList());
        HUMAN_FEMALE = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.FEMALE
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.UNI,
                        CharacterPatternsME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        CharacterPatternsME.Hairs.Hair.DIRTY_MOP,
                        CharacterPatternsME.Hairs.Hair.LONG,
                        CharacterPatternsME.Hairs.Hair.SEMI_LONG,
                        CharacterPatternsME.Hairs.Hair.VERY_LONG,
                        null).toList()
                )
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        //CharacterPatternsME.Clothing.ROBE
                ));
        // endregion
    }
}
