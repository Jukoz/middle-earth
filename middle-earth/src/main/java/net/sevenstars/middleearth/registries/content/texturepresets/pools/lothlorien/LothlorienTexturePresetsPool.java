package net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePreset;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedClothingPresetHolder;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedTexturePresetHolder;

import java.util.HashMap;
import java.util.List;

public class LothlorienTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder MALE_PRESET;
    private final static WeightedTexturePresetHolder FEMALE_PRESET;
    private final static WeightedTexturePresetHolder LORD_PRESET;

    public final static TexturePresetDataPool COMMON;
    public final static TexturePresetDataPool LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LOTHLORIEN_ELF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LOTHLORIEN_LORD, LORD)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.POINTY),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SMALL_POINTY)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.DEEP_BLUE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLACK)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_SILVER)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
            ));

        MALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
            ));

        FEMALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.FLAT_LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
            ));

        LORD_PRESET = MALE_PRESET.copy()
            .clearPatterns(CharacterPatternTypes.BODY)
            .clearMaterials(CharacterMaterialTypes.HAIR)
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_GOLD)
            ));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(MALE_PRESET));
            put(EntityCategories.FEMALE, new WeightedPool<>(FEMALE_PRESET));
        }});

        LORD = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(
                    BASE_PRESET.copy()
                               .clearMaterials(CharacterMaterialTypes.HAIR)
            ));
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                    LORD_PRESET.copy().withPatterns(CharacterPatternTypes.SCAR, List.of(
                            TexturePreset.EMPTY_VALUE_KEY.withWeight(30),
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            )));
        }});
    }
    // endregion
}
