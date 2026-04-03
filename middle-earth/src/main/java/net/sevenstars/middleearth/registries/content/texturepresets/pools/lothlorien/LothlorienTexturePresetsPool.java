package net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien;

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
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.POINTY),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.SMALL_POINTY)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.DEEP_BLUE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BLACK)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLONDE_SILVER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_SILVER)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
            ));

        MALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
            ));

        FEMALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.FLAT_LONG),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
            ));

        LORD_PRESET = MALE_PRESET.copy()
            .clearPatterns(CharacterPatternTypes.BODY)
            .clearMaterials(CharacterMaterialTypes.HAIR)
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                            WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                            WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLONDE_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.STRAW_GOLD)
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
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)
                    ))
                    .withPatterns(CharacterPatternTypes.SCAR,  List.of(
                            WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT)
                    )),
                LORD_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                            WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)
                    ))
                    .withPatterns(CharacterPatternTypes.SCAR,  List.of(
                            WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            ));
        }});
    }
    // endregion
}
