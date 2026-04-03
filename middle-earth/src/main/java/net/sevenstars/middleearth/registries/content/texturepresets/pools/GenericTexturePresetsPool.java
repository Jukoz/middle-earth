package net.sevenstars.middleearth.registries.content.texturepresets.pools;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TextureElementData;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class GenericTexturePresetsPool {
    private static final class UtilData {
        public static final List<TextureElementData> DEFAULT_HAIR = List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.COLD_BLACK_ALMANDINE),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BLACK_ALMANDINE),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.BROWN_ALMANDINE),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.DARK_BROWN_ALMANDINE),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.STRAW_BEADS),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_SILVER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GINGER_BEADS),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.WHITE_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.WHITE_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.WHITE_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.WHITE_BEADS),

                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_COPPER),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_GOLD),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_ALMANDINE),
                TextureElementData.material(CharacterMaterialsRegistryME.Hair.GRAY_BEADS)
        );
    }

    private final static TexturePresetData BASE_PRESET;
    private final static TexturePresetData HUMAN_MALE_PRESET;
    private final static TexturePresetData HUMAN_FEMALE_PRESET;

    private final static TexturePresetDatas COMMON;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
            new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.GENERIC_HUMAN, COMMON)
        );
    }
    //region [PRESETS]
    static {
        BASE_PRESET = new TexturePresetData()
            .withMaterials(NpcTextureType.SKIN, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.TAN_DESATURATED),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.PALE),
                TextureElementData.material(CharacterMaterialsRegistryME.Skin.BEIGE)
            ))
            .withMaterials(NpcTextureType.EYE, List.of(
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BLUE),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.GREEN),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.DARK_GREEN),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.NAVY),
                TextureElementData.material(CharacterMaterialsRegistryME.Eye.BROWN)
            ))
            .withPatterns(NpcTextureType.EYE, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
            ))
            .withMaterials(NpcTextureType.HAIR, UtilData.DEFAULT_HAIR)
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                    TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ));

        HUMAN_MALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern( CharacterPatternsRegistryME.Skins.Head.MALE)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
               TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
               TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
               TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY,
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BOWL),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.BALD_SIDES),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.TOP_BALDING),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SIDE_BALDING)
            ))
            .withPatterns(NpcTextureType.BEARD, Stream.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.CLEAN),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SHORT),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.SINGLE),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Beard.UNCLEAN_ORNAMENTED),
                null).toList());
        HUMAN_FEMALE_PRESET = new TexturePresetData()
            .withPatterns(NpcTextureType.BODY, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Body.FEMALE)
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Skins.Head.FEMALE)
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.UNI),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
            ))
            .withPatterns(NpcTextureType.HAIR, List.of(
                TexturePresetData.EMPTY_VALUE_KEY,
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG),
                TextureElementData.pattern(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
            ));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON= new TexturePresetDatas(
            new HashMap<>(){{
                put(EntityCategories.SHARED, List.of(BASE_PRESET));
                put(EntityCategories.MALE, List.of(HUMAN_MALE_PRESET));
                put(EntityCategories.FEMALE, List.of(HUMAN_FEMALE_PRESET));
            }}
        );
    }
    // endregion
}
