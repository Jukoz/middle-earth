package net.sevenstars.middleearth.registries.content.texturepresets.pools;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.common.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetData;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class GenericTexturePresetsPool {
    private static final class UtilData {
        public static final List<WeightedIdentifier> DEFAULT_HAIR = List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_ALMANDINE),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_ALMANDINE),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_ALMANDINE),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_ALMANDINE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_BEADS),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GINGER_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GINGER_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GINGER_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GINGER_ALMANDINE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GINGER_BEADS),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.WHITE_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.WHITE_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.WHITE_ALMANDINE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.WHITE_BEADS),

                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_ALMANDINE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS)
        );
    }

    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData HUMAN_MALE_PRESET;
    private final static TexturePresetData HUMAN_FEMALE_PRESET;

    private final static TexturePresetDatas COMMON;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
            new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GENERIC_HUMAN, COMMON)
        );
    }
    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.TAN),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BLUE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.GREEN),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.NAVY),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, UtilData.DEFAULT_HAIR)
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ));

        HUMAN_MALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern( CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
               WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
               WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
               WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY,
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
            ))
            .withPatterns(CharacterPatternTypes.BEARD, Stream.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.SINGLE),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                null).toList());
        HUMAN_FEMALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY,
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
            ));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON= new TexturePresetDatas(
            new HashMap<>(){{
                put(EntityCategories.SHARED, List.of(BASE_PRESET));
                put(EntityCategories.MALE, List.of(HUMAN_MALE_PRESET));
                put(EntityCategories.FEMALE, List.of(HUMAN_FEMALE_PRESET));
            }}
        );
    }
    // endregion
}
