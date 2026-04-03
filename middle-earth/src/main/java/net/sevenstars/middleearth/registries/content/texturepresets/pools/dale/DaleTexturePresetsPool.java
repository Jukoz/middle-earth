package net.sevenstars.middleearth.registries.content.texturepresets.pools.dale;

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

public class DaleTexturePresetsPool {

    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData LORD_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas SOLDIER;
    public final static TexturePresetDatas LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.DALE_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.DALE_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.DALE_LORD, LORD)
        );
    }

    // region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.PALE),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.BEIGE)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BLUE),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.GREEN),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BLACK),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.NAVY),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BROWN)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_COPPER),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GREASY_COPPER),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                    WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER)
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
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
                ))
                .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY,
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL)
                ))
                .withPatterns(CharacterPatternTypes.BEARD, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY.withWeight(3),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.SINGLE)
                ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ));

        LORD_PRESET = MALE_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.BODY)
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
                ))
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
                ))
                .clearMaterials(CharacterMaterialTypes.HAIR)
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                        WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                        WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                        WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                        WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                        WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD)
                ))
                .clearPatterns(CharacterPatternTypes.HAIR)
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY.withWeight(4),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
                ))
                .withPatterns(CharacterPatternTypes.SCAR, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                        WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
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

        SOLDIER = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(MALE_PRESET));
        }});

        LORD = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                    LORD_PRESET.copy(),
                    LORD_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)))
                            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT))),
                    LORD_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)))
                            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                                WeightedIdentifier.pattern((CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT))))
                    ));
        }});
    }
    // endregion
}
