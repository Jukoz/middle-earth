package net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.erebor;

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
import java.util.stream.Stream;

public class EreborTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData MIGHTY_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas SOLDIER;
    public final static TexturePresetDatas MIGHTY;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_MIGHTY_DWARF, MIGHTY)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withPatterns(CharacterPatternTypes.NOSE, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Nose.CUBE)
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
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_COPPER)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, Stream.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP)
            ).toList()
            ).withPatterns(CharacterPatternTypes.BEARD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.LONG_SINGLE_ORNAMENTED)
            ));

        MALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
            ))
            .withPatterns(CharacterPatternTypes.BEARD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.BROAD),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.LARGE),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LARGE_MUSTACHE),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LONG)
            ));

        FEMALE_PRESET = new TexturePresetData()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ));

        MIGHTY_PRESET = MALE_PRESET.copy()
            .clearMaterials(CharacterMaterialTypes.HAIR)
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                WeightedIdentifier.material(CharacterMaterialsRegistryME.Hair.WHITE_GOLD)
            ))
            .clearPatterns(CharacterPatternTypes.BEARD)
            .withPatterns(CharacterPatternTypes.BEARD, List.of(
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LONG),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_BROAD),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LARGE_MUSTACHE),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.FANCY_MUSTACHE_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.LONG_BRAIDS_ORNAMENTED),
                WeightedIdentifier.pattern(CharacterPatternsRegistryME.Hairs.Beard.CHUNKY_BRAIDS)
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

        MIGHTY = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(
                    BASE_PRESET.copy().clearMaterials(CharacterMaterialTypes.HAIR)
            ));
            put(EntityCategories.MALE, List.of(MIGHTY_PRESET));
        }});
    }
    // endregion
}
