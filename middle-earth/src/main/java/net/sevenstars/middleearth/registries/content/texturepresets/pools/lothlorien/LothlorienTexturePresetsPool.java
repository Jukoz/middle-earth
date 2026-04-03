package net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien;

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

public class LothlorienTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData LORD_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LOTHLORIEN_ELF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LOTHLORIEN_LORD, LORD)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(NpcTextureType.SKIN, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withPatterns(NpcTextureType.EAR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.POINTY),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.SMALL_POINTY)
            ))
            .withPatterns(NpcTextureType.EYE, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(NpcTextureType.EYE, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.DEEP_BLUE),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLACK)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_SILVER)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
            ));

        MALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.TOGA,
                    //CharacterPatternsME.Clothing.PANTS
            ));

        FEMALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.FLAT_LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.ROBE,
                    //CharacterPatternsME.Clothing.FULL_TOGA
            ));

        LORD_PRESET = MALE_PRESET.copy()
            .clearPatterns(NpcTextureType.BODY)
            .clearMaterials(NpcTextureType.HAIR)
            .withPatterns(NpcTextureType.HEAD, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_GOLD)
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

        LORD = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET.copy().clearMaterials(NpcTextureType.HAIR)));
            put(EntityCategories.MALE, List.of(
                LORD_PRESET.copy(),
                LORD_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)
                    ))
                    .withPatterns(NpcTextureType.SCAR,  List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT)
                    )),
                LORD_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .withPatterns(NpcTextureType.EYE, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)
                    ))
                    .withPatterns(NpcTextureType.SCAR,  List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            ));
        }});
    }
    // endregion
}
