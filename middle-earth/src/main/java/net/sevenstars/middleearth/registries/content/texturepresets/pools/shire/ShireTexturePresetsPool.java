package net.sevenstars.middleearth.registries.content.texturepresets.pools.shire;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.SimplifiedTexturesME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.*;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;

import java.util.HashMap;
import java.util.List;

public class ShireTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder MALE_PRESET;
    private final static WeightedTexturePresetHolder FEMALE_PRESET;
    private final static WeightedTexturePresetHolder SHIRRIFF_PRESET;

    public final static TexturePresetDataPool COMMON;
    public final static TexturePresetDataPool SOLDIER;
    public final static TexturePresetDataPool SHIRRIFF;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_MILITIA, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_SHIRRIFF, SHIRRIFF)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
            ))
            .withPatterns(CharacterPatternTypes.FEET, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLUE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.NAVY),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.GREEN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BLACK),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
            ));

        MALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.FEET, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHIRE_SHAG).withWeight(3)
            ))
            .withClothes(List.of(
                new WeightedClothingPresetHolder(
                        List.of(
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.SHORT_PANTS_BROWN),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.SHORT_PANTS_TAN)
                        ),
                        List.of(
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_WHITE),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_BEIGE)
                        ),
                        List.of(
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.OVERALLS_BROWN).withWeight(2),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.OVERALLS_TAN).withWeight(2),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.BROWN_TOWNSMAN_SHIRT_SHORT),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.GREEN_TOWNSMAN_SHIRT_SHORT),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SHORT_RED_COTTE),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.YELLOW_TOWNSMAN_SHIRT_SHORT),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BEIGE).withWeight(3),
                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.EMPTY)
                        ),
                        1
                )
            ))
            .withSimplifiedTextures(List.of(
                    new WeightedSimplifiedTexturePresetHolder(
                            SimplifiedTexturePreset.create(SimplifiedTexturesME.Shire.MALE_A)
                                    .withEar(SimplifiedTexturesME.Shire.MALE_A_EAR)
                                    .withFeet(SimplifiedTexturesME.Shire.MALE_A_FEET),
                            2
                    ),
                    new WeightedSimplifiedTexturePresetHolder(
                            SimplifiedTexturePreset.create(SimplifiedTexturesME.Shire.MALE_B)
                                    .withEar(SimplifiedTexturesME.Shire.MALE_B_EAR)
                                    .withFeet(SimplifiedTexturesME.Shire.MALE_B_FEET)
                    )
            ));

        FEMALE_PRESET = new WeightedTexturePresetHolder()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.FEET, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHIRE_SHAG),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ))
                .withClothes(List.of(
                    new WeightedClothingPresetHolder(
                            List.of(
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.SHORT_PANTS_BROWN),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.SHORT_PANTS_TAN)
                            ),
                            List.of(
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_WHITE),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_BEIGE)
                            ),
                            List.of(
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.OVERALLS_BROWN).withWeight(2),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.OVERALLS_TAN).withWeight(2),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.BROWN_TOWNSMAN_SHIRT_SHORT),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.GREEN_TOWNSMAN_SHIRT_SHORT),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SHORT_RED_COTTE),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.YELLOW_TOWNSMAN_SHIRT_SHORT),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BEIGE).withWeight(3),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.EMPTY)
                            ),
                            1
                    )
                ))
                .withSimplifiedTextures(List.of(
                        new WeightedSimplifiedTexturePresetHolder(
                                SimplifiedTexturePreset.create(SimplifiedTexturesME.Shire.FEMALE_A)
                                        .withEar(SimplifiedTexturesME.Shire.FEMALE_A_EAR)
                                        .withFeet(SimplifiedTexturesME.Shire.FEMALE_A_FEET)
                                        .withHair(SimplifiedTexturesME.Shire.FEMALE_A_HAIR)
                                ),
                        new WeightedSimplifiedTexturePresetHolder(
                                SimplifiedTexturePreset.create(SimplifiedTexturesME.Shire.FEMALE_B)
                                        .withEar(SimplifiedTexturesME.Shire.FEMALE_B_EAR)
                                        .withFeet(SimplifiedTexturesME.Shire.FEMALE_B_FEET)
                                        .withHair(SimplifiedTexturesME.Shire.FEMALE_B_HAIR),
                                2
                        )
                ));

        SHIRRIFF_PRESET = MALE_PRESET.copy()
            .clearClothes()
            .withClothes(List.of(
                    new WeightedClothingPresetHolder(
                            List.of(
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.SHORT_PANTS_BROWN),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.SHORT_PANTS_TAN)
                            ),
                            List.of(
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_WHITE),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_BEIGE)
                            ),
                            List.of(
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.BROWN_TOWNSMAN_SHIRT_SHORT),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.GREEN_TOWNSMAN_SHIRT_SHORT),
                                    WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.YELLOW_TOWNSMAN_SHIRT_SHORT)
                            ),
                            1
                    )
            ))
            .clearPatterns(CharacterPatternTypes.BODY)
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
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

        SOLDIER = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(MALE_PRESET));
        }});

        SHIRRIFF = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(SHIRRIFF_PRESET));
        }});
    }
    // endregion
}
