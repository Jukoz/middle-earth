package net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.erebor;

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

public class EreborTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData MIGHTY_PRESET;

    public final static TexturePresets COMMON;
    public final static TexturePresets SOLDIER;
    public final static TexturePresets MIGHTY;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_DWARF_SOLDIER, SOLDIER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.LONGBEARDS_EREBOR_MIGHTY_DWARF, MIGHTY)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
                .withMaterials(NpcTextureType.SKIN, List.of(
                        CharacterMaterialsME.Skin.PALE,
                        CharacterMaterialsME.Skin.BEIGE
                ))
                .withPatterns(NpcTextureType.NOSE, List.of(
                        CharacterPatternsME.Skins.Nose.CUBE
                ))
                .withPatterns(NpcTextureType.EAR, List.of(
                        CharacterPatternsME.Skins.Ear.SQUARE,
                        CharacterPatternsME.Skins.Ear.NORMAL
                ))
                .withPatterns(NpcTextureType.EYE, List.of(
                        CharacterPatternsME.Eyes.Eye.COMMON
                ))
                .withMaterials(NpcTextureType.EYE, List.of(
                        CharacterMaterialsME.Eye.BLUE,
                        CharacterMaterialsME.Eye.GREEN,
                        CharacterMaterialsME.Eye.DARK_GREEN,
                        CharacterMaterialsME.Eye.BLACK,
                        CharacterMaterialsME.Eye.NAVY,
                        CharacterMaterialsME.Eye.BROWN
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.COLD_BLACK_COPPER,
                        CharacterMaterialsME.Hair.BLACK_COPPER,
                        CharacterMaterialsME.Hair.DARK_BROWN_COPPER,
                        CharacterMaterialsME.Hair.BROWN_COPPER,
                        CharacterMaterialsME.Hair.BROWN_BEADS,
                        CharacterMaterialsME.Hair.GRAY_BEADS,
                        CharacterMaterialsME.Hair.GRAY_COPPER
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.BASIC,
                        CharacterPatternsME.Hairs.Eyebrow.SHORT,
                        CharacterPatternsME.Hairs.Eyebrow.THICK,
                        CharacterPatternsME.Hairs.Eyebrow.LONG
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                                CharacterPatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                CharacterPatternsME.Hairs.Hair.SHARP
                ).toList()
                ).withPatterns(NpcTextureType.BEARD, List.of(
                        CharacterPatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                        CharacterPatternsME.Hairs.Beard.DUAL_LARGE_ORNAMENTED,
                        CharacterPatternsME.Hairs.Beard.DUAL_ORNAMENTED,
                        CharacterPatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED
                ))
                .withMaterials(NpcTextureType.CLOTHING, List.of(
                        CharacterMaterialsME.Clothing.BLUE_AND_COPPER,
                        CharacterMaterialsME.Clothing.BROWN
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
                ));

        MALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.FAT
                ))
                .withPatterns(NpcTextureType.EYEBROW, List.of(
                        CharacterPatternsME.Hairs.Eyebrow.UNI
                ))
                .withPatterns(NpcTextureType.HAIR, Stream.of(
                        CharacterPatternsME.Hairs.Hair.SHORT,
                        CharacterPatternsME.Hairs.Hair.BALD_SIDES,
                        CharacterPatternsME.Hairs.Hair.TOP_BALDING,
                        CharacterPatternsME.Hairs.Hair.SIDE_BALDING,
                        null,
                        null).toList()
                )
                .withPatterns(NpcTextureType.BEARD, List.of(
                        CharacterPatternsME.Hairs.Beard.BROAD,
                        CharacterPatternsME.Hairs.Beard.LARGE,
                        CharacterPatternsME.Hairs.Beard.VERY_LARGE_MUSTACHE,
                        CharacterPatternsME.Hairs.Beard.VERY_LONG
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        CharacterPatternsME.Clothing.PANTS
                ));

        FEMALE_PRESET = new TexturePresetData()
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.FEMALE
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
                ))
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.FEMALE,
                        CharacterPatternsME.Skins.Body.SLIM,
                        CharacterPatternsME.Skins.Body.FAT
                ));

        MIGHTY_PRESET = MALE_PRESET.copy()
                .clearMaterials(NpcTextureType.HAIR)
                .withPatterns(NpcTextureType.BODY, List.of(
                        CharacterPatternsME.Skins.Body.MUSCULAR,
                        CharacterPatternsME.Skins.Body.FAT
                ))
                .withPatterns(NpcTextureType.HEAD, List.of(
                        CharacterPatternsME.Skins.Head.MALE
                ))
                .withMaterials(NpcTextureType.HAIR, List.of(
                        CharacterMaterialsME.Hair.COLD_BLACK_GOLD,
                        CharacterMaterialsME.Hair.BLACK_GOLD,
                        CharacterMaterialsME.Hair.DARK_BROWN_GOLD,
                        CharacterMaterialsME.Hair.BROWN_GOLD,
                        CharacterMaterialsME.Hair.GRAY_GOLD,
                        CharacterMaterialsME.Hair.WHITE_GOLD
                ))
                .clearPatterns(NpcTextureType.BEARD)
                .withPatterns(NpcTextureType.BEARD, List.of(
                        CharacterPatternsME.Hairs.Beard.CLEAN,
                        CharacterPatternsME.Hairs.Beard.VERY_LONG,
                        CharacterPatternsME.Hairs.Beard.VERY_BROAD,
                        CharacterPatternsME.Hairs.Beard.DUAL_LARGE_ORNAMENTED,
                        CharacterPatternsME.Hairs.Beard.VERY_LARGE_MUSTACHE,
                        CharacterPatternsME.Hairs.Beard.FANCY_MUSTACHE_ORNAMENTED,
                        CharacterPatternsME.Hairs.Beard.LONG_BRAIDS_ORNAMENTED,
                        CharacterPatternsME.Hairs.Beard.CHUNKY_BRAIDS
                ))
                .withPatterns(NpcTextureType.CLOTHING, List.of(
                        CharacterPatternsME.Clothing.TOGA,
                        CharacterPatternsME.Clothing.FULL_TOGA,
                        CharacterPatternsME.Clothing.SKIRT
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

        MIGHTY = new TexturePresets(new HashMap<>(){{
            put(EntityCategory.SHARED, List.of(
                    BASE_PRESET.copy().clearMaterials(NpcTextureType.HAIR)
            ));
            put(EntityCategory.MALE, List.of(MIGHTY_PRESET));
        }});
    }
    // endregion
}
