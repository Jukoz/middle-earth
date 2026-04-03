package net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.GundabadTexturePresetsPool;

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

public class GundabadTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData GOBLIN_PRESET;
    private final static TexturePresetData HOBGOBLIN_PRESET;

    public final static TexturePresetDatas GOBLIN;
    public final static TexturePresetDatas HOBGOBLIN;


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
                    TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE_WHITE),
                    TextureElementData.material(CharacterMaterialsRegistryME.Skin.LIGHT_GREY)
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.LARGE_POINTY),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.WIDE_POINTY),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE_POINTY)
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                    TextureElementData.material(CharacterMaterialsRegistryME.Eye.ICE)
                ))
                .withEmissiveEyes(true)
                .withMaterials(NpcTextureType.HAIR, List.of(
                    TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_BEADS),
                    TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                    TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_COPPER)
                ));

        GOBLIN_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.BODY, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                    TexturePresetData.EMPTY_VALUE_KEY.withWeight(3),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SMALL_DREADLOCKS)
                ));

        HOBGOBLIN_PRESET = GOBLIN_PRESET.copy()
                .clearPatterns(NpcTextureType.BODY)
                .clearPatterns(NpcTextureType.HEAD)
                .clearPatterns(NpcTextureType.EYE)
                .withPatterns(NpcTextureType.BODY, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
                ));
    }
    // endregion

    // region [DATAS]
    static {
        GOBLIN = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                    GOBLIN_PRESET.copy()
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
                    GOBLIN_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .clearPatterns(NpcTextureType.HEAD)
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.GOBLIN_SMALL)
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL)
                            )),
                    GOBLIN_PRESET.copy()
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

        HOBGOBLIN = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                    HOBGOBLIN_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .clearPatterns(NpcTextureType.HEAD)
                            .withPatterns(NpcTextureType.HEAD, List.of(
                                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.URUK_TALL_DUMB)
                            ))
                            .withPatterns(NpcTextureType.EYE, List.of(
                                TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.SMALL_VERY_HIGH_WIDE)
                            )),
                    HOBGOBLIN_PRESET.copy()
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
