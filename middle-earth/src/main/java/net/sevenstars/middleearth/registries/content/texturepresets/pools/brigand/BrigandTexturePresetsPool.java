package net.sevenstars.middleearth.registries.content.texturepresets.pools.brigand;

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

public class BrigandTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData CHIEF_PRESET;

    public final static TexturePresetDatas THUG;
    public final static TexturePresetDatas MERCENARY;
    public final static TexturePresetDatas CHIEF;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.BRIGAND_THUG, THUG),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.BRIGAND_MERCENARY, MERCENARY),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.BRIGAND_CHIEF, CHIEF)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.DARK_BEIGE)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLACK),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BROWN)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_SILVER)
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

        CHIEF_PRESET = MALE_PRESET.copy()
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
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_GOLD)
                ))
                .clearPatterns(CharacterPatternTypes.HAIR)
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(5),
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
        THUG = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(MALE_PRESET));
            put(EntityCategories.FEMALE, List.of(FEMALE_PRESET));
        }});

        MERCENARY = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(MALE_PRESET));
            put(EntityCategories.FEMALE, List.of(FEMALE_PRESET));
        }});

        CHIEF = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                    CHIEF_PRESET.copy(),
                    CHIEF_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)))
                            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT))),
                    CHIEF_PRESET.copy()
                            .clearPatterns(CharacterPatternTypes.EYE)
                            .withPatterns(CharacterPatternTypes.EYE, List.of(
                                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)))
                            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)))
            ));
        }});
    }
    // endregion
}
