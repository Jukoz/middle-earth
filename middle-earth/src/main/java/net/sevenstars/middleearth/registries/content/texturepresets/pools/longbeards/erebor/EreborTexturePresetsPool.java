package net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.erebor;

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
import java.util.stream.Stream;

public class EreborTexturePresetsPool {
    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData MALE_PRESET;
    private final static TexturePresetData FEMALE_PRESET;
    private final static TexturePresetData MIGHTY_PRESET;

    public final static TexturePresetDatas COMMON;
    public final static TexturePresetDatas SOLDIER;
    public final static TexturePresetDatas MIGHTY;

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
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withPatterns(NpcTextureType.NOSE, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Nose.CUBE)
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
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.GREEN),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLACK),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.NAVY),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_COPPER)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.THICK),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.LONG)
            ))
            .withPatterns(NpcTextureType.HAIR, Stream.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHARP)
            ).toList()
            ).withPatterns(NpcTextureType.BEARD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.LONG_SINGLE_ORNAMENTED)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                //CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
            ));

        MALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY.withWeight(2),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
            ))
            .withPatterns(NpcTextureType.BEARD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.BROAD),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.LARGE),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LARGE_MUSTACHE),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LONG)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                    //CharacterPatternsME.Clothing.PANTS
            ));

        FEMALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(NpcTextureType.CLOTHE_PRESETS, List.of(
                //CharacterPatternsME.Clothing.DWARVEN_GARMENT_WITH_PANTS
            ))
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.SLIM),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ));

        MIGHTY_PRESET = MALE_PRESET.copy()
            .clearMaterials(NpcTextureType.HAIR)
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FAT)
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withMaterials(NpcTextureType.HAIR, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.WHITE_GOLD)
            ))
            .clearPatterns(NpcTextureType.BEARD)
            .withPatterns(NpcTextureType.BEARD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_BROAD),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.DUAL_LARGE_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.VERY_LARGE_MUSTACHE),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.FANCY_MUSTACHE_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.LONG_BRAIDS_ORNAMENTED),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.CHUNKY_BRAIDS)
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

        MIGHTY = new TexturePresetDatas(new HashMap<>(){{
            put(EntityCategories.SHARED, List.of(
                    BASE_PRESET.copy().clearMaterials(NpcTextureType.HAIR)
            ));
            put(EntityCategories.MALE, List.of(MIGHTY_PRESET));
        }});
    }
    // endregion
}
