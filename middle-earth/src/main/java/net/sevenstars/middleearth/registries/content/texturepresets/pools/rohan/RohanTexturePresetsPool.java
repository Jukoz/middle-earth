package net.sevenstars.middleearth.registries.content.texturepresets.pools.rohan;

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

public class RohanTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData LORD_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas SOLDIER;
    public final static TexturePresetDatas LORD;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ROHAN_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ROHAN_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.ROHAN_LORD, LORD)
        );
    }
    // region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(NpcTextureType.SKIN, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.BEIGE),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN)
            ))
            .withPatterns(NpcTextureType.EAR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.SQUARE),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Ear.NORMAL)
            ))
            .withPatterns(NpcTextureType.EYE, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(NpcTextureType.EYE, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLUE),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.NAVY),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.GREEN),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLACK),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_COPPER)
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
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT)
            ))
            .withPatterns(NpcTextureType.BEARD, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SINGLE)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                //CharacterPatternsME.Clothing.PANTS
            ));

        FEMALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SKIN_TO_BONE)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                //CharacterPatternsME.Clothing.ROBE
            ));

        LORD_PRESET = MALE_PRESET.copy()
            .clearPatterns(NpcTextureType.BODY)
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .clearMaterials(NpcTextureType.HAIR)
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_GOLD)
            ))
            .clearPatterns(NpcTextureType.HAIR)
            .withPatterns(NpcTextureType.HAIR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT)
            ))
            .withPatterns(NpcTextureType.SCAR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
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

        LORD = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(
                LORD_PRESET.copy(),
                LORD_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .withPatterns(NpcTextureType.EYE, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_LEFT)
                    ))
                    .withPatterns(NpcTextureType.SCAR, List.of(
                        TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT)
                    )),
                LORD_PRESET.copy()
                    .clearPatterns(NpcTextureType.EYE)
                    .withPatterns(NpcTextureType.EYE, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.BLIND_RIGHT)
                    ))
                    .withPatterns(NpcTextureType.SCAR, List.of(
                            TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            ));
        }});
    }
    // endregion
}
