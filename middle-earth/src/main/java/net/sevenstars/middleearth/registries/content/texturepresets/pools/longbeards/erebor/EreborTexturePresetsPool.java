package net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.erebor;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedClothingPresetHolder;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePreset;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedTexturePresetHolder;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class EreborTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder MALE_PRESET;
    private final static WeightedTexturePresetHolder FEMALE_PRESET;
    private final static WeightedTexturePresetHolder MIGHTY_PRESET;

    public final static TexturePresetDataPool COMMON;
    public final static TexturePresetDataPool SOLDIER;
    public final static TexturePresetDataPool MIGHTY;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_MIGHTY_DWARF, MIGHTY)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
            .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withPatterns(CharacterPatternTypes.NOSE, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Nose.CUBE)
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
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_COPPER)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, Stream.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHARP)
            ).toList()
            ).withPatterns(CharacterPatternTypes.BEARD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.DUAL_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.LONG_SINGLE_ORNAMENTED)
            ));

        MALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                TexturePreset.EMPTY_VALUE_KEY.withWeight(2),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
            ))
            .withPatterns(CharacterPatternTypes.BEARD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.BROAD),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.LARGE),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.VERY_LARGE_MUSTACHE),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.VERY_LONG)
            ));

        FEMALE_PRESET = new WeightedTexturePresetHolder()
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FEMALE),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
            ));

        MIGHTY_PRESET = MALE_PRESET.copy()
            .clearMaterials(CharacterMaterialTypes.HAIR)
            .withPatterns(CharacterPatternTypes.BODY, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(CharacterPatternTypes.HEAD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.WHITE_GOLD)
            ))
            .clearPatterns(CharacterPatternTypes.BEARD)
            .withPatterns(CharacterPatternTypes.BEARD, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.VERY_LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.VERY_BROAD),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.VERY_LARGE_MUSTACHE),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.FANCY_MUSTACHE_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.LONG_BRAIDS_ORNAMENTED),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.CHUNKY_BRAIDS)
            ))
            .withClothes(List.of(
                new WeightedClothingPresetHolder(
                    List.of(
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN),
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                    ),
                    List.of(
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BEIGE),
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY)
                    ),
                    3
                ),
                new WeightedClothingPresetHolder(
                    List.of(
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN),
                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                    )
                )
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

        MIGHTY = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(
                    BASE_PRESET.copy().clearMaterials(CharacterMaterialTypes.HAIR)
            ));
            put(EntityCategories.MALE, new WeightedPool<>(MIGHTY_PRESET));
        }});
    }
    // endregion
}
