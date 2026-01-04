package net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.GundabadTexturePresetsPool;

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

public class GundabadTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData GOBLIN_PRESET;
    private final static TexturePresetData HOBGOBLIN_PRESET;

    public final static TexturePresets GOBLIN;
    public final static TexturePresets HOBGOBLIN;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GUNDABAD_GOBLIN, GOBLIN),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GUNDABAD_HOBGOBLIN, HOBGOBLIN)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsME.Skin.PALE_WHITE,
                        CharacterMaterialsME.Skin.LIGHT_GREY
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.LARGE_POINTY,
                        CharacterPatternsME.Skins.Ear.WIDE_POINTY,
                        CharacterPatternsME.Skins.Ear.SQUARE_POINTY
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.ICE
                ))
                .withEmissiveEyes(true)
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.GINGER_BEADS,
                        CharacterMaterialsME.Hair.GRAY_BEADS,
                        CharacterMaterialsME.Hair.GRAY_COPPER
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        CharacterPatternsME.Clothing.THONG
                ))
                .withMaterials(NpcTextureType.CLOTHING, List.of(
                        CharacterMaterialsME.Clothing.BROWN
                ));

        GOBLIN_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.SLIM
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        null,
                                null,
                                null,
                                CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                CharacterPatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                CharacterPatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS
                        ).toList()
                )
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        CharacterPatternsME.Clothing.THONG,
                        CharacterPatternsME.Clothing.PANTS
                ));

        HOBGOBLIN_PRESET = GOBLIN_PRESET.copy()
                .clearPatterns(NpcTextureType.BODY)
                .clearPatterns(NpcTextureType.HEAD)
                .clearPatterns(NpcTextureType.EYE)
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.FAT
                ));
    }
    // endregion

    // region [DATAS]
    static {
        GOBLIN = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    GOBLIN_PRESET.copy()
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
                    GOBLIN_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .clearPatterns(NpcTextureType.HEAD)
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    CharacterPatternsME.Skins.Head.GOBLIN_SMALL
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    CharacterPatternsME.Eyes.Eye.SMALL
                            )),
                    GOBLIN_PRESET.copy()
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

        HOBGOBLIN = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    HOBGOBLIN_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .clearPatterns(NpcTextureType.HEAD)
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    CharacterPatternsME.Skins.Head.URUK_TALL_DUMB
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    CharacterPatternsME.Eyes.Eye.SMALL_VERY_HIGH_WIDE
                            )),
                    HOBGOBLIN_PRESET.copy()
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
