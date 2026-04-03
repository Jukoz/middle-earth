package net.sevenstars.middleearth.registries.content.texturepresets.pools.mordor;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetData;

import java.util.HashMap;
import java.util.List;

public class MordorTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData BLACK_NUMENOREAN_PRESET;
    private final static TexturePresetData ORC_PRESET;
    private final static TexturePresetData BLACK_URUK_PRESET;

    public final static TexturePresetDatas BLACK_NUMENOREAN;
    public final static TexturePresetDatas ORC;
    public final static TexturePresetDatas BLACK_URUK;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORDOR_BLACK_NUMENOREAN, BLACK_NUMENOREAN),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORDOR_ORC, ORC),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORDOR_BLACK_URUK, BLACK_URUK)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BROWN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.GREY),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.SLIGHT_BROWN)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.YELLOW),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.RED)
            ))
            .withEmissiveEyes(true)
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE)
            ));

        BLACK_NUMENOREAN_PRESET = new TexturePresetData()
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.POINTY)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
               WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
               WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(CharacterPatternTypes.BEARD, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SHORT)
            ));


        ORC_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
            ));

        BLACK_URUK_PRESET = ORC_PRESET.copy()
            .clearPatterns(CharacterPatternTypes.BODY)
            .clearPatterns(CharacterPatternTypes.HEAD)
            .clearPatterns(CharacterPatternTypes.EYE)
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD)
            ));
    }
    // endregion

    // region [DATAS]
    static {
        BLACK_NUMENOREAN  = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.MALE, List.of(BLACK_NUMENOREAN_PRESET));
        }});

        ORC = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
            ORC_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_WISE),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_THICK_BROW)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                )),
            ORC_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                )),
            ORC_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_VERY_WIDE)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE)
                ))
            ));
        }});

        BLACK_URUK = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
            BLACK_URUK_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                )),
            BLACK_URUK_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.URUK_DUMB)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_HIGH_WIDE),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON_HIGH)
                ))
            ));
        }});
    }
    // endregion
}
