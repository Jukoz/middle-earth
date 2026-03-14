package net.sevenstars.middleearth.registries.content.texturepresets.pools.isengard;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.CharacterClothesME;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.ClothePresets;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class IsengardTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData BLACK_NUMENOREAN_PRESET;
    private final static TexturePresetData ORC_PRESET;
    private final static TexturePresetData BLACK_URUK_PRESET;

    public final static TexturePresets HUMAN;
    public final static TexturePresets ORC;
    public final static TexturePresets URUK_HAI;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ISENGARD_ORC, ORC),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ISENGARD_URUK_HAI, URUK_HAI),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ISENGARD_HUMAN, HUMAN)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsME.Skin.RUST,
                        CharacterMaterialsME.Skin.RED,
                        CharacterMaterialsME.Skin.WINE
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.LARGE_POINTY,
                        CharacterPatternsME.Skins.Ear.WIDE_POINTY,
                        CharacterPatternsME.Skins.Ear.SQUARE_POINTY
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.YELLOW
                ))
                .withEmissiveEyes(true)
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.DIRTY_BROWN_ALMANDINE,
                        CharacterMaterialsME.Hair.GREASY_ALMANDINE,
                        CharacterMaterialsME.Hair.BLACK_ALMANDINE
                ))
                .withClothes(List.of(
                        new ClothePresets[]{
                                new ClothePresets(List.of(
                                        CharacterClothesME.Base.THONG_BROWN
                                ))
                        }
                ));

        BLACK_NUMENOREAN_PRESET = new TexturePresetData()
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.BROWN
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsME.Skin.TAN,
                        CharacterMaterialsME.Skin.TAN_DESATURATED
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.SLIM
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                    CharacterPatternsME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.POINTY
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.GREASY_GOLD,
                        CharacterMaterialsME.Hair.BLACK_GOLD
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        null,
                        null,
                        CharacterPatternsME.Hairs.Hair.SHARP,
                        CharacterPatternsME.Hairs.Hair.BOWL,
                        CharacterPatternsME.Hairs.Hair.LONG,
                        CharacterPatternsME.Hairs.Hair.SEMI_LONG
                ).toList())
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsME.Hairs.Eyebrow.SHORT
                ))
                .withPatterns(NpcTextureType.BEARD, Stream.of(
                        null,
                        null,
                        CharacterPatternsME.Hairs.Beard.SHORT
                ).toList())
                .withClothes(List.of(
                        new ClothePresets[]{
                                new ClothePresets(List.of(
                                        CharacterClothesME.Base.PANTS_BROWN,
                                        CharacterClothesME.Base.PANTS_DARK_BROWN
                                ))
                        }
                ));


        ORC_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.SLIM
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                            null,
                            null,
                            CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                            CharacterPatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                            CharacterPatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS
                        ).toList()
                )
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.THONG,
                        //CharacterPatternsME.Clothing.PANTS
                ));

        BLACK_URUK_PRESET = ORC_PRESET.copy()
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
        HUMAN  = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.MALE, List.of(BLACK_NUMENOREAN_PRESET));
        }});

        ORC = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    ORC_PRESET.copy()
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
                    ORC_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .clearPatterns(NpcTextureType.HEAD)
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    CharacterPatternsME.Skins.Head.GOBLIN_SMALL
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    CharacterPatternsME.Eyes.Eye.SMALL
                            )),
                    ORC_PRESET.copy()
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

        URUK_HAI = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    BLACK_URUK_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .clearPatterns(NpcTextureType.HEAD)
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                    CharacterPatternsME.Skins.Head.URUK_TALL_DUMB
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                    CharacterPatternsME.Eyes.Eye.SMALL_VERY_HIGH_WIDE
                            )),
                    BLACK_URUK_PRESET.copy()
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
