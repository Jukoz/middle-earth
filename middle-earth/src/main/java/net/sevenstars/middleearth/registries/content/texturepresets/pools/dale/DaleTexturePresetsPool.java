package net.sevenstars.middleearth.registries.content.texturepresets.pools.dale;

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
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLUE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLACK),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.NAVY),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BROWN)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_COPPER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_COPPER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER)
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
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
                ))
                .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY,
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BOWL)
                ))
                .withPatterns(CharacterPatternTypes.BEARD, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY.withWeight(3),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SINGLE)
                ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ));

        LORD_PRESET = MALE_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.BODY)
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
                ))
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
                ))
                .clearMaterials(CharacterMaterialTypes.HAIR)
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_GOLD)
                ))
                .clearPatterns(CharacterPatternTypes.HAIR)
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY.withWeight(4),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
                ))
                .withPatterns(CharacterPatternTypes.SCAR, List.of(
                        TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
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
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)))
                            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT))),
                    LORD_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)))
                            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                                WeightedIdentifier.fromKey((CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT))))
                    ));
        }});
    }
    // endregion
}
