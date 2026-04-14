package net.sevenstars.middleearth.registries.content.texturepresets.pools.wildgoblin;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.*;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;

import java.util.HashMap;
import java.util.List;

public class WildGoblinTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder WEAK_PRESET;
    private final static WeightedTexturePresetHolder WARRIOR_PRESET;
    private final static WeightedTexturePresetHolder BRUTE_PRESET;

    public final static TexturePresetDataPool WEAK;
    public final static TexturePresetDataPool WARRIOR;
    public final static TexturePresetDataPool BRUTE;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WILD_GOBLIN_WEAK, WEAK),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, WARRIOR),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WILD_GOBLIN_BRUTE, BRUTE)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE_WHITE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PINK)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.RED)
            ))
            .withEmissiveEyes(true)
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE)
            ))
            .withClothes(List.of(
                new WeightedClothingPresetHolder(List.of(
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.THONG_BROWN),
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.THONG_DARK_BROWN)
                ))
            ));

        WEAK_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePreset.EMPTY_VALUE_KEY.withWeight(4),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
            ))
            .withClothes(List.of(
                new WeightedClothingPresetHolder(List.of(
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.THONG_BROWN),
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.THONG_DARK_BROWN)
                ))
            ));

        WARRIOR_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePreset.EMPTY_VALUE_KEY.withWeight(4),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
            ))
            .withClothes(List.of(
                    new WeightedClothingPresetHolder(List.of(
                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.THONG_BROWN),
                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.THONG_DARK_BROWN)
                    ))
            ));

        BRUTE_PRESET = WARRIOR_PRESET.copy()
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
        WEAK = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                WEAK_PRESET.copy()
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
                WEAK_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                    )),
                WEAK_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_VERY_WIDE)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE)
                    ))
            )));
        }});

        WARRIOR = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                WARRIOR_PRESET.copy()
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
                WARRIOR_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                    )),
                WARRIOR_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_VERY_WIDE)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE)
                    ))
            )));
        }});

        BRUTE = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                BRUTE_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                    )),
                BRUTE_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .clearPatterns(CharacterPatternTypes.HEAD)
                    .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.URUK_DUMB)
                    ))
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_HIGH_WIDE),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON_HIGH)
                    ))
            )));
        }});
    }
    // endregion
}
