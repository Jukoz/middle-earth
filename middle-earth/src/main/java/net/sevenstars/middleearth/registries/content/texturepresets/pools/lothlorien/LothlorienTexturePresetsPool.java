package net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien;

import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;

public class LothlorienTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData LORD_PRESET;

    public final static TexturePresets COMMON;
    public final static TexturePresets LORD;

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
                        CharacterMaterialsME.Skin.PALE,
                        CharacterMaterialsME.Skin.BEIGE
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.POINTY,
                        CharacterPatternsME.Skins.Ear.SMALL_POINTY
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.DEEP_BLUE,
                        CharacterMaterialsME.Eye.BLACK
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.BLONDE_SILVER,
                        CharacterMaterialsME.Hair.GRAY_BEADS,
                        CharacterMaterialsME.Hair.STRAW_SILVER
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.BASIC
                ));

        MALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.SLIM
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                                CharacterPatternsME.Hairs.Hair.UNCUT,
                                CharacterPatternsME.Hairs.Hair.SEMI_LONG
                ))
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.TOGA,
                        //CharacterPatternsME.Clothing.PANTS
                ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.SLIM,
                        CharacterPatternsME.Skins.Body.FEMALE
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                                CharacterPatternsME.Hairs.Hair.LONG,
                                CharacterPatternsME.Hairs.Hair.FLAT_LONG,
                                CharacterPatternsME.Hairs.Hair.VERY_LONG
                ))
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.ROBE,
                        //CharacterPatternsME.Clothing.FULL_TOGA
                ));

        LORD_PRESET = MALE_PRESET.copy()
                .clearPatterns(NpcTextureType.BODY)
                .clearMaterials(NpcTextureType.HAIR)
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.BLONDE_GOLD,
                        CharacterMaterialsME.Hair.GRAY_GOLD,
                        CharacterMaterialsME.Hair.STRAW_GOLD
                ));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(MALE_PRESET));
            put(EntityCategory.FEMALE, List.of(FEMALE_PRESET));
        }});

        LORD = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET.copy().clearMaterials(NpcTextureType.HAIR)));
            put(EntityCategory.MALE, List.of(
                    LORD_PRESET.copy(),
                    LORD_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .withPatterns(NpcTextureType.EYE, List.of(CharacterPatternsME.Eyes.Eye.BLIND_LEFT))
                            .withPatterns(NpcTextureType.SCAR, List.of(CharacterPatternsME.Skins.Scar.EYE_LEFT)),
                    LORD_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .withPatterns(NpcTextureType.EYE, List.of(CharacterPatternsME.Eyes.Eye.BLIND_RIGHT))
                            .withPatterns(NpcTextureType.SCAR, List.of(CharacterPatternsME.Skins.Scar.EYE_RIGHT))
            ));
        }});
    }
    // endregion
}
