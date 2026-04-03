package net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien;

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

public class LothlorienTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData LORD_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LOTHLORIEN_ELF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LOTHLORIEN_LORD, LORD)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
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

        MALE_PRESET = new TexturePresetData()
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

        FEMALE_PRESET = new TexturePresetData()
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
        COMMON = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(MALE_PRESET));
            put(EntityCategories.FEMALE, List.of(FEMALE_PRESET));
        }});

        LORD = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(
                    BASE_PRESET.copy()
                            .clearMaterials(CharacterMaterialTypes.HAIR)
            ));
            put(EntityCategories.MALE, List.of(
                LORD_PRESET.copy(),
                LORD_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)
                    ))
                    .withPatterns(CharacterPatternTypes.SCAR,  List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT)
                    )),
                LORD_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)
                    ))
                    .withPatterns(CharacterPatternTypes.SCAR,  List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            ));
        }});
    }
    // endregion
}
