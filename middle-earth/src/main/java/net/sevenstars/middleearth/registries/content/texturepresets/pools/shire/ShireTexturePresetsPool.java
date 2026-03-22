package net.sevenstars.middleearth.registries.content.texturepresets.pools.shire;

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

public class ShireTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData SHIRRIFF_PRESET;

    public final static TexturePresets COMMON;
    public final static TexturePresets SOLDIER;
    public final static TexturePresets SHIRRIFF;

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
                        CharacterMaterialsME.Skin.PALE,
                        CharacterMaterialsME.Skin.BEIGE,
                        CharacterMaterialsME.Skin.TAN
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.SQUARE,
                        CharacterPatternsME.Skins.Ear.NORMAL
                ))
                .withPatterns(NpcTextureType.FEET, List.of(
                        CharacterPatternsME.Skins.Feet.NORMAL
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.BLUE,
                        CharacterMaterialsME.Eye.NAVY,
                        CharacterMaterialsME.Eye.GREEN,
                        CharacterMaterialsME.Eye.DARK_GREEN,
                        CharacterMaterialsME.Eye.BLACK,
                        CharacterMaterialsME.Eye.BROWN
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.BROWN_BEADS,
                        CharacterMaterialsME.Hair.DARK_BROWN_BEADS,
                        CharacterMaterialsME.Hair.DIRTY_BROWN_BEADS,
                        CharacterMaterialsME.Hair.GREASY_BEADS,
                        CharacterMaterialsME.Hair.DIRTY_BLONDE_BEADS,
                        CharacterMaterialsME.Hair.BLONDE_BEADS,
                        CharacterMaterialsME.Hair.GRAY_BEADS
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.BASIC
                ));

        MALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.FEET, List.of(
                        CharacterPatternsME.Skins.Feet.NORMAL
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.FAT,
                        CharacterPatternsME.Skins.Body.SLIM
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.UNI,
                        CharacterPatternsME.Hairs.Eyebrow.LONG,
                        CharacterPatternsME.Hairs.Eyebrow.THICK
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                        CharacterPatternsME.Hairs.Hair.SHORT,
                        CharacterPatternsME.Hairs.Hair.BOWL,
                        CharacterPatternsME.Hairs.Hair.SHARP,
                        CharacterPatternsME.Hairs.Hair.BALD_SIDES
                ))
                .withClothes(List.of(
                        new ClothePresets(
                                List.of(
                                        CharacterClothesME.Base.PANTS_BROWN,
                                        CharacterClothesME.Base.PANTS_DARK_BROWN
                                ),
                                List.of(
                                        CharacterClothesME.Over.SHIRT_BEIGE,
                                        CharacterClothesME.Over.SHIRT_BURGUNDY
                                ),
                                3
                        ),
                        new ClothePresets(
                                List.of(
                                        CharacterClothesME.Base.PANTS_BROWN,
                                        CharacterClothesME.Base.PANTS_DARK_BROWN
                                )
                        )
                ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.FEET, List.of(
                        CharacterPatternsME.Skins.Feet.NORMAL
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.FEMALE
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                        CharacterPatternsME.Hairs.Hair.LONG,
                        CharacterPatternsME.Hairs.Hair.UNCUT,
                        CharacterPatternsME.Hairs.Hair.SEMI_LONG
                ))
                .withClothes(List.of(
                        new ClothePresets(
                                List.of(
                                        CharacterClothesME.Base.PANTS_BROWN,
                                        CharacterClothesME.Base.PANTS_DARK_BROWN
                                ),
                                List.of(
                                        CharacterClothesME.Over.DRESS_BURGUNDY_AND_WHITE
                                )
                        )
                ));

        SHIRRIFF_PRESET = MALE_PRESET.copy()
                .clearPatterns(NpcTextureType.BODY)
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.FAT
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

        SOLDIER = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(MALE_PRESET));
        }});

        SHIRRIFF = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(SHIRRIFF_PRESET));
        }});
    }
    // endregion
}
