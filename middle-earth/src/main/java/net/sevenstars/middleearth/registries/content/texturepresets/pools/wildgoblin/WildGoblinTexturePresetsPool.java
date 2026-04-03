package net.sevenstars.middleearth.registries.content.texturepresets.pools.wildgoblin;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TextureElementData;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class WildGoblinTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData WEAK_PRESET;
    private final static TexturePresetData WARRIOR_PRESET;
    private final static TexturePresetData BRUTE_PRESET;

    public final static TexturePresetDatas WEAK;
    public final static TexturePresetDatas WARRIOR;
    public final static TexturePresetDatas BRUTE;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WILD_GOBLIN_WEAK, WEAK),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, WARRIOR),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WILD_GOBLIN_BRUTE, BRUTE)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(NpcTextureType.SKIN, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE_WHITE),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.PINK)
            ))
            .withPatterns(NpcTextureType.EAR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
            ))
            .withMaterials(NpcTextureType.EYE, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.RED)
            ))
            .withEmissiveEyes(true)
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GREASY_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    // CharacterPatternsME.Clothing.THONG
            ));

        WEAK_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(4),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.PANTS
            ));

        WARRIOR_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(4),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)

            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.ROBE
            ));

        BRUTE_PRESET = WARRIOR_PRESET.copy()
            .clearPatterns(NpcTextureType.BODY)
            .clearPatterns(NpcTextureType.HEAD)
            .clearPatterns(NpcTextureType.EYE)
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD)
            ));
    }
    // endregion

    // region [DATAS]
    static {
        WEAK = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                WEAK_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_WISE),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_THICK_BROW)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE),
                            TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                    )),
                WEAK_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                    )),
                WEAK_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_VERY_WIDE)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE)
                    ))
            ));
        }});

        WARRIOR = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                WARRIOR_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_WISE),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_THICK_BROW)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                    )),
                WARRIOR_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                    )),
                WARRIOR_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL_VERY_WIDE)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_WIDE)
                    ))
            ));
        }});

        BRUTE = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                BRUTE_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                    )),
                BRUTE_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .clearPatterns(NpcTextureType.HEAD)
                    .withPatterns(NpcTextureType.HEAD, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.URUK_DUMB)
                    ))
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_HIGH_WIDE),
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON_HIGH)
                    ))
            ));
        }});
    }
    // endregion
}
