package net.sevenstars.middleearth.registries.content.texturepresets.pools.mordor;

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

public class MordorTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData BLACK_NUMENOREAN_PRESET;
    private final static TexturePresetData ORC_PRESET;
    private final static TexturePresetData BLACK_URUK_PRESET;

    public final static TexturePresetDatas BLACK_NUMENOREAN;
    public final static TexturePresetDatas ORC;
    public final static TexturePresetDatas BLACK_URUK;


    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORDOR_BLACK_NUMENOREAN, BLACK_NUMENOREAN),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORDOR_ORC, ORC),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.MORDOR_BLACK_URUK, BLACK_URUK)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(NpcTextureType.SKIN, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.BROWN),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.GREY),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.SLIGHT_BROWN)
            ))
            .withPatterns(NpcTextureType.EAR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
            ))
            .withMaterials(NpcTextureType.EYE, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.YELLOW),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.RED)
            ))
            .withEmissiveEyes(true)
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GREASY_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.THONG
            ));

        BLACK_NUMENOREAN_PRESET = new TexturePresetData()
            .withMaterials(NpcTextureType.EYE, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withPatterns(NpcTextureType.EYE, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(NpcTextureType.SKIN, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(NpcTextureType.EAR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.POINTY)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
               TextureElementData.material(CharacterMaterialsRegistryME.Hair.GREASY_GOLD),
               TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(NpcTextureType.BEARD, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                //CharacterPatternsME.Clothing.PANTS,
                //CharacterPatternsME.Clothing.FULL_TOGA
            ));


        ORC_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.THONG,
                    //CharacterPatternsME.Clothing.PANTS
            ));

        BLACK_URUK_PRESET = ORC_PRESET.copy()
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
        BLACK_NUMENOREAN  = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.MALE, List.of(BLACK_NUMENOREAN_PRESET));
        }});

        ORC = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
            ORC_PRESET.copy()
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
            ORC_PRESET.copy()
                .clearPatterns(NpcTextureType.EYE)
                .clearPatterns(NpcTextureType.HEAD)
                .withPatterns(NpcTextureType.HEAD, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                )),
            ORC_PRESET.copy()
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

        BLACK_URUK = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
            BLACK_URUK_PRESET.copy()
                .clearPatterns(NpcTextureType.EYE)
                .clearPatterns(NpcTextureType.HEAD)
                .withPatterns(NpcTextureType.HEAD, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                )),
            BLACK_URUK_PRESET.copy()
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
