package net.sevenstars.middleearth.registries.content.texturepresets.pools.rohan;

import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePreset;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedTexturePresetHolder;

import java.util.HashMap;
import java.util.List;

public class RohanTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder MALE_PRESET;
    private final static WeightedTexturePresetHolder FEMALE_PRESET;
    private final static WeightedTexturePresetHolder LORD_PRESET;

    public final static TexturePresetDataPool COMMON;
    public final static TexturePresetDataPool SOLDIER;
    public final static TexturePresetDataPool LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ROHAN_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ROHAN_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ROHAN_LORD, LORD)
        );
    }
    // region [PRESETS]
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
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_COPPER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_ALMANDINE),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_COPPER)
            ))
            .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
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
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
            ))
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.UNCUT)
            ))
            .withPatterns(CharacterPatternTypes.BEARD, List.of(
                TexturePreset.EMPTY_VALUE_KEY.withWeight(2),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Beard.SINGLE)
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
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.STRAW_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLONDE_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_GOLD),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_SILVER),
                WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.GINGER_GOLD)
            ))
            .clearPatterns(CharacterPatternTypes.HAIR)
            .withPatterns(CharacterPatternTypes.HAIR, List.of(
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.UNCUT)
            ))
            .withPatterns(CharacterPatternTypes.SCAR, List.of(
                TexturePreset.EMPTY_VALUE_KEY.withWeight(2),
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
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                LORD_PRESET.copy(),
                LORD_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)
                    ))
                    .withPatterns(CharacterPatternTypes.SCAR, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT)
                    )),
                LORD_PRESET.copy()
                    .clearPatterns(CharacterPatternTypes.EYE)
                    .withPatterns(CharacterPatternTypes.EYE, List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)
                    ))
                    .withPatterns(CharacterPatternTypes.SCAR, List.of(
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            )));
        }});
    }
    // endregion
}
