package net.sevenstars.middleearth.registries.content.texturepresets.pools.shire;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothePresetDatas;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.common.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetData;

import java.util.HashMap;
import java.util.List;

public class ShireTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData SHIRRIFF_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas SOLDIER;
    public final static TexturePresetDatas SHIRRIFF;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_MILITIA, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_SHIRRIFF, SHIRRIFF)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.BEIGE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.TAN)
            ))
            .withPatterns(CharacterPatternTypes.EAR, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
            ))
            .withPatterns(CharacterPatternTypes.FEET, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
            ))
            .withPatterns(CharacterPatternTypes.EYE, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(CharacterMaterialTypes.EYE, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BLUE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.NAVY),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.GREEN),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BLACK),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GREASY_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLONDE_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
            ));

        MALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.FEET, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES)
            ))
            .withClothes(List.of(
                new ClothePresetDatas(
                        List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                        ),
                        List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Over.SHIRT_BEIGE),
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY)
                        ),
                        3
                ),
                new ClothePresetDatas(
                    List.of(
                        WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                        WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                    )
                )
            ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.FEET, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                    WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ))
                .withClothes(List.of(
                    new ClothePresetDatas(
                        List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                        ),
                        List.of(
                            WeightedIdentifier.clothe(CharacterClothesRegistryME.Over.DRESS_BURGUNDY_AND_WHITE)
                        )
                    )
                ));

        SHIRRIFF_PRESET = MALE_PRESET.copy()
            .clearPatterns(CharacterPatternTypes.BODY)
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
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

        SHIRRIFF = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(SHIRRIFF_PRESET));
        }});
    }
    // endregion
}
