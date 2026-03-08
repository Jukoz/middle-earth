package net.sevenstars.middleearth.registries.content.texturepresets.pools.brigand;

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

public class BrigandTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData CHIEF_PRESET;

    public final static TexturePresets THUG;
    public final static TexturePresets MERCENARY;
    public final static TexturePresets CHIEF;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.BRIGAND_THUG, THUG),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.BRIGAND_MERCENARY, MERCENARY),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.BRIGAND_CHIEF, CHIEF)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsME.Skin.PALE,
                        CharacterMaterialsME.Skin.BEIGE,
                        CharacterMaterialsME.Skin.TAN_DESATURATED,
                        CharacterMaterialsME.Skin.DARK_BEIGE
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.SQUARE,
                        CharacterPatternsME.Skins.Ear.NORMAL
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.GREEN,
                        CharacterMaterialsME.Eye.DARK_GREEN,
                        CharacterMaterialsME.Eye.BLACK,
                        CharacterMaterialsME.Eye.BROWN
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.DIRTY_BROWN_SILVER,
                        CharacterMaterialsME.Hair.GREASY_SILVER,
                        CharacterMaterialsME.Hair.GRAY_BEADS,
                        CharacterMaterialsME.Hair.BROWN_SILVER,
                        CharacterMaterialsME.Hair.BROWN_BEADS,
                        CharacterMaterialsME.Hair.DARK_BROWN_SILVER,
                        CharacterMaterialsME.Hair.BLACK_SILVER,
                        CharacterMaterialsME.Hair.COLD_BLACK_SILVER
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
                        CharacterPatternsME.Skins.Body.FAT,
                        CharacterPatternsME.Skins.Body.SKIN_TO_BONE
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.UNI,
                        CharacterPatternsME.Hairs.Eyebrow.LONG,
                        CharacterPatternsME.Hairs.Eyebrow.THICK
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                                null,
                                CharacterPatternsME.Hairs.Hair.SHORT,
                                CharacterPatternsME.Hairs.Hair.BALD_SIDES,
                                CharacterPatternsME.Hairs.Hair.TOP_BALDING,
                                CharacterPatternsME.Hairs.Hair.SIDE_BALDING,
                                CharacterPatternsME.Hairs.Hair.BOWL
                        ).toList()
                )
                .withPatterns(NpcTextureType.BEARD, Stream.of(
                                null,
                                null,
                                null,
                                CharacterPatternsME.Hairs.Beard.SHORT,
                                CharacterPatternsME.Hairs.Beard.SINGLE
                        ).toList()
                )
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.PANTS,
                        //CharacterPatternsME.Clothing.PANTS_WITH_SCARF,
                        //CharacterPatternsME.Clothing.PANTS_WITH_SCARF,
                        //CharacterPatternsME.Clothing.PANTS_WITH_SCARF
                ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.SLIM,
                        CharacterPatternsME.Skins.Body.SKIN_TO_BONE
                ))
                .withPatterns(NpcTextureType.HAIR, List.of(
                                CharacterPatternsME.Hairs.Hair.LONG,
                                CharacterPatternsME.Hairs.Hair.UNCUT,
                                CharacterPatternsME.Hairs.Hair.SEMI_LONG
                        )
                )
                .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                        //CharacterPatternsME.Clothing.ROBE
                ));

        CHIEF_PRESET = MALE_PRESET.copy()
                .clearPatterns(NpcTextureType.BODY)
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.FAT
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .clearMaterials(NpcTextureType.HAIR)
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.DIRTY_BROWN_GOLD,
                        CharacterMaterialsME.Hair.GREASY_GOLD,
                        CharacterMaterialsME.Hair.GRAY_GOLD,
                        CharacterMaterialsME.Hair.BROWN_GOLD,
                        CharacterMaterialsME.Hair.DARK_BROWN_GOLD,
                        CharacterMaterialsME.Hair.BLACK_GOLD,
                        CharacterMaterialsME.Hair.COLD_BLACK_GOLD,
                        CharacterMaterialsME.Hair.WHITE_GOLD
                ))
                .clearPatterns(NpcTextureType.HAIR)
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                            null,
                                null,
                                null,
                                null,
                                null,
                                CharacterPatternsME.Hairs.Hair.SHORT,
                                CharacterPatternsME.Hairs.Hair.BALD_SIDES,
                                CharacterPatternsME.Hairs.Hair.TOP_BALDING,
                                CharacterPatternsME.Hairs.Hair.SIDE_BALDING
                        ).toList()
                )
                .withPatterns(NpcTextureType.SCAR, Stream.of(
                        null,
                        null,
                        CharacterPatternsME.Skins.Scar.EYE_LEFT,
                        CharacterPatternsME.Skins.Scar.EYE_RIGHT
                ).toList())
        ;
    }
    // endregion

    // region [DATAS]
    static {
        THUG = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(MALE_PRESET));
            put(EntityCategory.FEMALE, List.of(FEMALE_PRESET));
        }});

        MERCENARY = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(MALE_PRESET));
            put(EntityCategory.FEMALE, List.of(FEMALE_PRESET));
        }});

        CHIEF = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(BASE_PRESET));
            put(EntityCategory.MALE, List.of(
                    CHIEF_PRESET.copy(),
                    CHIEF_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .withPatterns(NpcTextureType.EYE, List.of(CharacterPatternsME.Eyes.Eye.BLIND_LEFT))
                            .withPatterns(NpcTextureType.SCAR, List.of(CharacterPatternsME.Skins.Scar.EYE_LEFT)),
                    CHIEF_PRESET.copy()
                            .clearPatterns(NpcTextureType.EYE)
                            .withPatterns(NpcTextureType.EYE, List.of(CharacterPatternsME.Eyes.Eye.BLIND_RIGHT))
                            .withPatterns(NpcTextureType.SCAR, List.of(CharacterPatternsME.Skins.Scar.EYE_RIGHT))
            ));
        }});
    }
    // endregion
}
