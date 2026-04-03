package net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.GundabadTexturePresetsPool;

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

public class GundabadTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData GOBLIN_PRESET;
    private final static TexturePresetData HOBGOBLIN_PRESET;

    public final static TexturePresetDatas GOBLIN;
    public final static TexturePresetDatas HOBGOBLIN;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GUNDABAD_GOBLIN, GOBLIN),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, HOBGOBLIN)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE_WHITE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.LIGHT_GREY)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.ICE)
                ))
                .withEmissiveEyes(true)
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_BEADS),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_COPPER)
                ));

        GOBLIN_PRESET = new TexturePresetData()
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(3),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
                ));

        HOBGOBLIN_PRESET = GOBLIN_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.BODY)
                .clearPatterns(CharacterPatternTypes.HEAD)
                .clearPatterns(CharacterPatternTypes.EYE)
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
                ));
    }
    // endregion

    // region [DATAS]
    static {
        GOBLIN = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                    GOBLIN_PRESET.copy()
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
                    GOBLIN_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .clearPatterns(CharacterPatternTypes.HEAD)
                            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                            ))
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                            )),
                    GOBLIN_PRESET.copy()
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

        HOBGOBLIN = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                    HOBGOBLIN_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .clearPatterns(CharacterPatternTypes.HEAD)
                            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                            ))
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                            )),
                    HOBGOBLIN_PRESET.copy()
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
