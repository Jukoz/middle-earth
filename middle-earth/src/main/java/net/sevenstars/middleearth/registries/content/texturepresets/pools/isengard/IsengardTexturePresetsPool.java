package net.sevenstars.middleearth.registries.content.texturepresets.pools.isengard;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothePresetDatas;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.common.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetData;

import java.util.HashMap;
import java.util.List;

public class IsengardTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData BLACK_NUMENOREAN_PRESET;
    private final static TexturePresetData ORC_PRESET;
    private final static TexturePresetData BLACK_URUK_PRESET;

    public final static TexturePresetDatas HUMAN;
    public final static TexturePresetDatas ORC;
    public final static TexturePresetDatas URUK_HAI;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ISENGARD_ORC, ORC),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ISENGARD_URUK_HAI, URUK_HAI),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ISENGARD_HUMAN, HUMAN)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.RUST),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.RED),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.WINE)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.YELLOW)
                ))
                .withEmissiveEyes(true)
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_ALMANDINE),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GREASY_ALMANDINE),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE)
                ))
                .withClothes(List.of(
                    new ClothePresetDatas[]{
                        new ClothePresetDatas(List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.THONG_BROWN)
                        ))
                    }
                ));

        BLACK_NUMENOREAN_PRESET = new TexturePresetData()
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BROWN)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                ))
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.TAN),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
                ))
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.POINTY)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                   WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
                   WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ))
                .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
                ))
                .withPatterns(CharacterPatternTypes.BEARD, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT)
                ))
                .withClothes(List.of(
                    new ClothePresetDatas[]{
                        new ClothePresetDatas(List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                        ))
                    }
                ));

        ORC_PRESET = new TexturePresetData()
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
                ))
                .withClothes(List.of(
                    new ClothePresetDatas[]{
                        new ClothePresetDatas(List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.THONG_BROWN),
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN)
                        ))
                    }
                ));

        BLACK_URUK_PRESET = ORC_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.BODY)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .clearPatterns(CharacterPatternTypes.EYE)
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_GOLD),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD)
                ));
    }
    // endregion

    // region [DATAS]
    static {
        HUMAN  = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.MALE, List.of(BLACK_NUMENOREAN_PRESET));
        }});

        ORC = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                ORC_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_WISE),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_THICK_BROW)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                    )),
            ORC_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                )),
            ORC_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_VERY_WIDE)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE)
                ))
            ));
        }});

        URUK_HAI = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(

            BLACK_URUK_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                )),

            BLACK_URUK_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.EYE)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.URUK_DUMB)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_HIGH_WIDE),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON_HIGH)
                ))
            ));
        }});
    }
    // endregion
}
