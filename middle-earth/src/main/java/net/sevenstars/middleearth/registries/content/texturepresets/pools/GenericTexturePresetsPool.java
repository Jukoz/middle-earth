package net.sevenstars.middleearth.registries.content.texturepresets.pools;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePreset;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedTexturePresetHolder;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class GenericTexturePresetsPool {
    private static final class UtilData {
        public static final List<WeightedIdentifier> DEFAULT_HAIR = List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_ALMANDINE),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_ALMANDINE),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_ALMANDINE),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_BEADS),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_BEADS),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_BEADS),

                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS)
        );
    }

    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder HUMAN_MALE_PRESET;
    private final static WeightedTexturePresetHolder HUMAN_FEMALE_PRESET;

    private final static TexturePresetDataPool COMMON;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
            new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GENERIC_HUMAN, COMMON)
        );
    }
    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLUE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.GREEN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.NAVY),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, UtilData.DEFAULT_HAIR)
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ));

        HUMAN_MALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey( CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
               WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
               WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
               WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePreset.EMPTY_VALUE_KEY,
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
            ))
            .withPatterns(CharacterPatternTypes.BEARD, Stream.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SINGLE),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                null).toList());
        HUMAN_FEMALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePreset.EMPTY_VALUE_KEY,
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
            ));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON= new TexturePresetDataPool(
            new HashMap<>(){{
                put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
                put(EntityCategories.MALE, new WeightedPool<>(HUMAN_MALE_PRESET));
                put(EntityCategories.FEMALE, new WeightedPool<>(HUMAN_FEMALE_PRESET));
            }}
        );
    }
    // endregion
}
