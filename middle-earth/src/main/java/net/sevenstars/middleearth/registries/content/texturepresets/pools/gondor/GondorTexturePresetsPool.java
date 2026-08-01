package net.sevenstars.middleearth.registries.content.texturepresets.pools.gondor;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.SimplifiedTexturesME;
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

public class GondorTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder MALE_PRESET;
    private final static WeightedTexturePresetHolder FEMALE_PRESET;
    private final static WeightedTexturePresetHolder LORD_PRESET;

    public final static TexturePresetDataPool COMMON;
    public final static TexturePresetDataPool SOLDIER;
    public final static TexturePresetDataPool LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GONDOR_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GONDOR_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GONDOR_LORD, LORD)
        );
    }
    // region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED)
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
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GREASY_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_SILVER)
                ))
                .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
                ))
                .withClothes(List.of(
                    new WeightedClothingPresetHolder[]{
                        new WeightedClothingPresetHolder(
                                List.of(
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN)
                                ),
                                List.of(
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BEIGE),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_GRAY).withWeight(2),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_RED),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.BROWN_TOWNSMAN_SHIRT)
                                ),
                                List.of(
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.RED_HOUPPELANDE),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.GONDORIAN_TABBARD),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.GONDORIAN_TABBARD_OPEN),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.EMPTY).withWeight(1)
                                )
                        )
                    }
                ));

        MALE_PRESET = new WeightedTexturePresetHolder()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
                ))
                .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                   WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                   WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                   WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePreset.EMPTY_VALUE_KEY,
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.FLAT_LONG)
                ))
                .withPatterns(CharacterPatternTypes.BEARD, List.of(
                    TexturePreset.EMPTY_VALUE_KEY.withWeight(7),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SHORT)
                ))
                .withSimplifiedTextures(List.of(
                        new WeightedSimplifiedTexturePresetHolder(
                                SimplifiedTexturePreset.create(SimplifiedTexturesME.Gondor.MALE_A)
                        ),
                        new WeightedSimplifiedTexturePresetHolder(
                                SimplifiedTexturePreset.create(SimplifiedTexturesME.Gondor.MALE_B)
                        )
                ));

        FEMALE_PRESET = new WeightedTexturePresetHolder()
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
                ))
                .withSimplifiedTextures(List.of(
                        new WeightedSimplifiedTexturePresetHolder(
                                SimplifiedTexturePreset.create(SimplifiedTexturesME.Gondor.FEMALE_A)
                                        .withHair(SimplifiedTexturesME.Gondor.FEMALE_A_HAIR)
                        ),
                        new WeightedSimplifiedTexturePresetHolder(
                                SimplifiedTexturePreset.create(SimplifiedTexturesME.Gondor.FEMALE_B)
                                        .withHair(SimplifiedTexturesME.Gondor.FEMALE_B_HAIR)
                        )
                ))
                .withClothes(List.of(
                        new WeightedClothingPresetHolder[]{
                                new WeightedClothingPresetHolder(
                                        List.of(
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BEIGE),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN)
                                        ),
                                        List.of(
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_BEIGE),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHORT_CHEMISE_RED),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.DRESS_BURGUNDY_AND_WHITE)
                                        )
                                )
                        }
                ))
        ;

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
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                    WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD)
                ))
                .clearPatterns(CharacterPatternTypes.HAIR)
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    TexturePreset.EMPTY_VALUE_KEY.withWeight(5),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
                ))
                .withPatterns(CharacterPatternTypes.SCAR, List.of(
                    TexturePreset.EMPTY_VALUE_KEY.withWeight(18),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                    WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
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

        LORD = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(MALE_PRESET));
        }});
    }
    // endregion
}
