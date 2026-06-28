package net.sevenstars.middleearth.registries.content.texturepresets.pools.moria;

import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePreset;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedTexturePresetHolder;

import java.util.HashMap;
import java.util.List;

public class MoriaTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder GOBLIN_PRESET;

    public final static TexturePresetDataPool GOBLIN;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORIA_GOBLIN, GOBLIN)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.SWAMPY_GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE_GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.MURKY_GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.DARK_GREEN)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.PALE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.YELLOW)
                ))
                .withEmissiveEyes(true)
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_COPPER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_COPPER)
                ));

        GOBLIN_PRESET = new WeightedTexturePresetHolder()
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePreset.EMPTY_VALUE_KEY.withWeight(3),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
                ));
    }
    // endregion

    // region [DATAS]
    static {
        GOBLIN = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE,  new WeightedPool<>(List.of(
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
            )));
        }});
    }
    // endregion
}
