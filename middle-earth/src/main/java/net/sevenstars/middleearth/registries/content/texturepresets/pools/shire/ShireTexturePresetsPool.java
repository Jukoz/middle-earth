package net.sevenstars.middleearth.registries.content.texturepresets.pools.shire;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothePresetDatas;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TextureElementData;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;

import java.util.HashMap;
import java.util.List;

public class ShireTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData SHIRRIFF_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas SOLDIER;
    public final static TexturePresetDatas SHIRRIFF;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_PEASANT, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_MILITIA, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.SHIRE_SHIRRIFF, SHIRRIFF)
        );
    }

    //region [PRESETS]
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
            .withPatterns(NpcTextureType.FEET, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
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
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BROWN_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GREASY_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DIRTY_BLONDE_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLONDE_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC)
            ));

        MALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(NpcTextureType.FEET, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES)
            ))
            .withClothes(List.of(
                new ClothePresetDatas(
                        List.of(
                            TextureElementData.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                            TextureElementData.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                        ),
                        List.of(
                            TextureElementData.clothe(CharacterClothesRegistryME.Over.SHIRT_BEIGE),
                            TextureElementData.clothe(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY)
                        ),
                        3
                ),
                new ClothePresetDatas(
                    List.of(
                        TextureElementData.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                        TextureElementData.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                    )
                )
            ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
                ))
                .withPatterns(NpcTextureType.FEET, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Feet.NORMAL)
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE)
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ))
                .withClothes(List.of(
                    new ClothePresetDatas(
                        List.of(
                            TextureElementData.clothe(CharacterClothesRegistryME.Base.PANTS_BROWN),
                            TextureElementData.clothe(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN)
                        ),
                        List.of(
                            TextureElementData.clothe(CharacterClothesRegistryME.Over.DRESS_BURGUNDY_AND_WHITE)
                        )
                    )
                ));

        SHIRRIFF_PRESET = MALE_PRESET.copy()
            .clearPatterns(NpcTextureType.BODY)
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
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

        SHIRRIFF = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(BASE_PRESET));
            put(EntityCategories.MALE, List.of(SHIRRIFF_PRESET));
        }});
    }
    // endregion
}
