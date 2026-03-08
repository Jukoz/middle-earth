package net.sevenstars.middleearth.registries.content.texturepresets.pools.wildgoblin;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class WildGoblinTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData WEAK_PRESET;
    private final static TexturePresetData WARRIOR_PRESET;
    private final static TexturePresetData BRUTE_PRESET;

    public final static TexturePresets WEAK;
    public final static TexturePresets WARRIOR;
    public final static TexturePresets BRUTE;

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
                        CharacterMaterialsME.Skin.PALE_WHITE,
                        CharacterMaterialsME.Skin.PINK
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.LARGE_POINTY,
                        CharacterPatternsME.Skins.Ear.WIDE_POINTY,
                        CharacterPatternsME.Skins.Ear.SQUARE_POINTY
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.RED
                ))
                .withEmissiveEyes(true)
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.DIRTY_BROWN_ALMANDINE,
                        CharacterMaterialsME.Hair.GREASY_ALMANDINE,
                        CharacterMaterialsME.Hair.BLACK_ALMANDINE
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        // CharacterPatternsME.Clothing.THONG
                ));

        WEAK_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.SKIN_TO_BONE
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                null,
                        null,
                        null,
                        null,
                        CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                        CharacterPatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                        CharacterPatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS
                        ).toList()
                )
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        //CharacterPatternsME.Clothing.PANTS
                ));

        WARRIOR_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.SLIM
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                                null,
                                null,
                                null,
                                null,
                                CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                CharacterPatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                CharacterPatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS
                        ).toList()
                )
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        //CharacterPatternsME.Clothing.ROBE
                ));

        BRUTE_PRESET = WARRIOR_PRESET.copy()
                .clearPatterns(NpcTextureType.BODY)
                .clearPatterns(NpcTextureType.HEAD)
                .clearPatterns(NpcTextureType.EYE)
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.FAT
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.DIRTY_BROWN_GOLD,
                        CharacterMaterialsME.Hair.GREASY_GOLD,
                        CharacterMaterialsME.Hair.BLACK_GOLD
                ));
    }
    // endregion

    // region [DATAS]
    static {
        WEAK = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    WEAK_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL_WISE,
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL_THICK_BROW
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL_WIDE,
                                CharacterPatternsME.Eyes.Eye.COMMON
                        )),
                    WEAK_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL
                        )),
                    WEAK_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL_VERY_WIDE
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL_WIDE
                        ))
            ));
        }});

        WARRIOR = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    WARRIOR_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL_WISE,
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL_THICK_BROW
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL_WIDE,
                                CharacterPatternsME.Eyes.Eye.COMMON
                        )),
                    WARRIOR_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL
                        )),
                    WARRIOR_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.GOBLIN_SMALL_VERY_WIDE
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL_WIDE
                        ))
            ));
        }});

        BRUTE = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    BRUTE_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.URUK_TALL_DUMB
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL_VERY_HIGH_WIDE
                        )),
                    BRUTE_PRESET.copy()
                        .clearPatterns(NpcTextureType.EYE)
                        .clearPatterns(NpcTextureType.HEAD)
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                CharacterPatternsME.Skins.Head.URUK_DUMB
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                CharacterPatternsME.Eyes.Eye.SMALL_HIGH_WIDE,
                                CharacterPatternsME.Eyes.Eye.COMMON_HIGH
                        ))
            ));
        }});
    }
    // endregion
}
