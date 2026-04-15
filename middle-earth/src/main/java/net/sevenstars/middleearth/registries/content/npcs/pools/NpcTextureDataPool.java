package net.sevenstars.middleearth.registries.content.npcs.pools;

public class NpcTextureDataPool {
/*
    public final static List<TextureElementData> DEFAULT_HAIR;

    public final static TexturePresetData HUMAN_COMMON;
    public final static TexturePresetData HUMAN_MALE;
    public final static TexturePresetData HUMAN_FEMALE;

    public static class Dwarves{
        public final static TexturePresetData DWARF_MALE;
        public final static TexturePresetData DWARF_FEMALE;

        static {
            DWARF_MALE = new TexturePresetData()
                    .withMaterials(NpcTextureType.SKIN, List.of(
                        TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN),
                        TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED),
                        TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE),
                        TextureElementData.material(CharacterMaterialsRegistryME.Skin.BEIGE)
                    ))
                    .withPatterns(NpcTextureType.BODY, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
                    ))
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
                    ))
                    .withPatterns(NpcTextureType.NOSE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Nose.CUBE)
                    ))
                    .withPatterns(NpcTextureType.EAR, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
                    ))
                    .withMaterials(NpcTextureType.EYE, List.of(
                        TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLUE),
                        TextureElementData.material(CharacterMaterialsRegistryME.Eye.GREEN),
                        TextureElementData.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                        TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLACK),
                        TextureElementData.material(CharacterMaterialsRegistryME.Eye.NAVY),
                        TextureElementData.material(CharacterMaterialsRegistryME.Eye.BROWN)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                    ))
                    .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                    .withPatterns(NpcTextureType.EYEBROW, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG)
                    ))
                    .withPatterns(NpcTextureType.HAIR, List.of(
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.DIRTY_MOP),
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                           TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING),
                           TexturePresetData.EMPTY_VALUE_KEY
                    ))
                    .withPatterns(NpcTextureType.BEARD, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.BROAD),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.CHUNKY_BRAIDS),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_ORNAMENTED),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.LARGE),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.LONG_SINGLE_ORNAMENTED),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SINGLE),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_BROAD)
                    ))
                    .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT,
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS,
                            //CharacterPatternsME.Clothing.PANTS
                    ));

            DWARF_FEMALE = new TexturePresetData()
                    .withMaterials(NpcTextureType.SKIN, List.of(
                            CharacterMaterialsRegistryME.Skin.TAN,
                            CharacterMaterialsRegistryME.Skin.TAN_DESATURATED,
                            CharacterMaterialsRegistryME.Skin.PALE,
                            CharacterMaterialsRegistryME.Skin.BEIGE
                    ))
                    .withPatterns(NpcTextureType.BODY, List.of(
                            CharacterPatternsRegistryME.Skins.Body.FEMALE,
                            CharacterPatternsRegistryME.Skins.Body.SLIM,
                            CharacterPatternsRegistryME.Skins.Body.FAT
                    ))
                    .withPatterns(NpcTextureType.HEAD, List.of(
                            CharacterPatternsRegistryME.Skins.Head.FEMALE
                    ))
                    .withPatterns(NpcTextureType.EAR, List.of(
                            CharacterPatternsRegistryME.Skins.Ear.SQUARE,
                            CharacterPatternsRegistryME.Skins.Ear.NORMAL
                    ))
                    .withMaterials(NpcTextureType.EYE, List.of(
                            CharacterMaterialsRegistryME.Eye.BLUE,
                            CharacterMaterialsRegistryME.Eye.GREEN,
                            CharacterMaterialsRegistryME.Eye.DARK_GREEN,
                            CharacterMaterialsRegistryME.Eye.BLACK,
                            CharacterMaterialsRegistryME.Eye.NAVY,
                            CharacterMaterialsRegistryME.Eye.BROWN
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                            CharacterPatternsRegistryME.Eyes.Eye.COMMON
                    ))
                    .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                    .withPatterns(NpcTextureType.EYEBROW, List.of(
                            CharacterPatternsRegistryME.Hairs.Eyebrow.UNI,
                            CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC,
                            CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT,
                            CharacterPatternsRegistryME.Hairs.Eyebrow.THICK,
                            CharacterPatternsRegistryME.Hairs.Eyebrow.LONG
                    ))
                    .withPatterns(NpcTextureType.HAIR, Stream.of(
                                    CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                    CharacterPatternsRegistryME.Hairs.Hair.LONG,
                                    CharacterPatternsRegistryME.Hairs.Hair.FLAT_LONG,
                                    CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG
                            ).toList()
                    )
                    .withPatterns(NpcTextureType.BEARD, Stream.of(
                            CharacterPatternsRegistryME.Hairs.Beard.CLEAN,
                            CharacterPatternsRegistryME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                            CharacterPatternsRegistryME.Hairs.Beard.SHORT).toList())
                    .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT,
                            //CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
                    ));
        }
    }


    static {
        // region HAIR
        DEFAULT_HAIR = List.of(
                CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER,
                CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD,
                CharacterMaterialsRegistryME.Hair.COLD_BLACK_SILVER,
                CharacterMaterialsRegistryME.Hair.COLD_BLACK_ALMANDINE,

                CharacterMaterialsRegistryME.Hair.BLACK_COPPER,
                CharacterMaterialsRegistryME.Hair.BLACK_GOLD,
                CharacterMaterialsRegistryME.Hair.BLACK_SILVER,
                CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE,

                CharacterMaterialsRegistryME.Hair.BROWN_COPPER,
                CharacterMaterialsRegistryME.Hair.BROWN_GOLD,
                CharacterMaterialsRegistryME.Hair.BROWN_SILVER,
                CharacterMaterialsRegistryME.Hair.BROWN_ALMANDINE,

                CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER,
                CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD,
                CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER,
                CharacterMaterialsRegistryME.Hair.DARK_BROWN_ALMANDINE,

                CharacterMaterialsRegistryME.Hair.STRAW_COPPER,
                CharacterMaterialsRegistryME.Hair.STRAW_GOLD,
                CharacterMaterialsRegistryME.Hair.STRAW_SILVER,
                CharacterMaterialsRegistryME.Hair.STRAW_ALMANDINE,
                CharacterMaterialsRegistryME.Hair.STRAW_BEADS,

                CharacterMaterialsRegistryME.Hair.GINGER_COPPER,
                CharacterMaterialsRegistryME.Hair.GINGER_GOLD,
                CharacterMaterialsRegistryME.Hair.GINGER_SILVER,
                CharacterMaterialsRegistryME.Hair.GINGER_ALMANDINE,
                CharacterMaterialsRegistryME.Hair.GINGER_BEADS,

                CharacterMaterialsRegistryME.Hair.WHITE_COPPER,
                CharacterMaterialsRegistryME.Hair.WHITE_GOLD,
                CharacterMaterialsRegistryME.Hair.WHITE_ALMANDINE,
                CharacterMaterialsRegistryME.Hair.WHITE_BEADS,

                CharacterMaterialsRegistryME.Hair.GRAY_COPPER,
                CharacterMaterialsRegistryME.Hair.GRAY_GOLD,
                CharacterMaterialsRegistryME.Hair.GRAY_ALMANDINE,
                CharacterMaterialsRegistryME.Hair.GRAY_BEADS
        );
        // endregion

        // region HUMAN
        HUMAN_COMMON = new TexturePresetData()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsRegistryME.Skin.TAN,
                        CharacterMaterialsRegistryME.Skin.TAN_DESATURATED,
                        CharacterMaterialsRegistryME.Skin.PALE,
                        CharacterMaterialsRegistryME.Skin.BEIGE
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsRegistryME.Eye.BLUE,
                        CharacterMaterialsRegistryME.Eye.GREEN,
                        CharacterMaterialsRegistryME.Eye.DARK_GREEN,
                        CharacterMaterialsRegistryME.Eye.NAVY,
                        CharacterMaterialsRegistryME.Eye.BROWN
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsRegistryME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.HAIR, DEFAULT_HAIR)
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsRegistryME.Hairs.Eyebrow.UNI,
                        CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.TOGA,
                        //CharacterPatternsME.Clothing.FULL_TOGA,
                        //CharacterPatternsME.Clothing.SKIRT
                ));
        HUMAN_MALE = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsRegistryME.Skins.Body.MUSCULAR
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsRegistryME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsRegistryME.Hairs.Eyebrow.UNI,
                        CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        CharacterPatternsRegistryME.Hairs.Hair.BOWL,
                        CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES,
                        CharacterPatternsRegistryME.Hairs.Hair.DIRTY_MOP,
                        CharacterPatternsRegistryME.Hairs.Hair.SHORT,
                        CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING,
                        CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING,
                        null).toList()
                )
                .withPatterns(NpcTextureType.BEARD, Stream.of(
                        CharacterPatternsRegistryME.Hairs.Beard.CLEAN,
                        CharacterPatternsRegistryME.Hairs.Beard.SHORT,
                        CharacterPatternsRegistryME.Hairs.Beard.SINGLE,
                        CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                        null).toList());
        HUMAN_FEMALE = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsRegistryME.Skins.Body.FEMALE
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsRegistryME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsRegistryME.Hairs.Eyebrow.UNI,
                        CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        CharacterPatternsRegistryME.Hairs.Hair.DIRTY_MOP,
                        CharacterPatternsRegistryME.Hairs.Hair.LONG,
                        CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG,
                        CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG,
                        null).toList()
                )
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.ROBE
                ));
        // endregion
    }
 */
}
