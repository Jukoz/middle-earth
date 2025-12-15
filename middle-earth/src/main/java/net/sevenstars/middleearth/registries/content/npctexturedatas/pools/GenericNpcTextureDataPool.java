package net.sevenstars.middleearth.registries.content.npctexturedatas.pools;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.content.npctexturedatas.NpcTextureDataRegistry;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class GenericNpcTextureDataPool {
    private static final class UtilData {
        public static final List<RegistryKey<NpcTextureMaterial>> DEFAULT_HAIR = List.of(
                CharacterMaterialsME.Hair.COLD_BLACK_COPPER,
                CharacterMaterialsME.Hair.COLD_BLACK_GOLD,
                CharacterMaterialsME.Hair.COLD_BLACK_SILVER,
                CharacterMaterialsME.Hair.COLD_BLACK_ALMANDINE,

                CharacterMaterialsME.Hair.BLACK_COPPER,
                CharacterMaterialsME.Hair.BLACK_GOLD,
                CharacterMaterialsME.Hair.BLACK_SILVER,
                CharacterMaterialsME.Hair.BLACK_ALMANDINE,

                CharacterMaterialsME.Hair.BROWN_COPPER,
                CharacterMaterialsME.Hair.BROWN_GOLD,
                CharacterMaterialsME.Hair.BROWN_SILVER,
                CharacterMaterialsME.Hair.BROWN_ALMANDINE,

                CharacterMaterialsME.Hair.DARK_BROWN_COPPER,
                CharacterMaterialsME.Hair.DARK_BROWN_GOLD,
                CharacterMaterialsME.Hair.DARK_BROWN_SILVER,
                CharacterMaterialsME.Hair.DARK_BROWN_ALMANDINE,

                CharacterMaterialsME.Hair.STRAW_COPPER,
                CharacterMaterialsME.Hair.STRAW_GOLD,
                CharacterMaterialsME.Hair.STRAW_SILVER,
                CharacterMaterialsME.Hair.STRAW_ALMANDINE,
                CharacterMaterialsME.Hair.STRAW_BEADS,

                CharacterMaterialsME.Hair.GINGER_COPPER,
                CharacterMaterialsME.Hair.GINGER_GOLD,
                CharacterMaterialsME.Hair.GINGER_SILVER,
                CharacterMaterialsME.Hair.GINGER_ALMANDINE,
                CharacterMaterialsME.Hair.GINGER_BEADS,

                CharacterMaterialsME.Hair.WHITE_COPPER,
                CharacterMaterialsME.Hair.WHITE_GOLD,
                CharacterMaterialsME.Hair.WHITE_ALMANDINE,
                CharacterMaterialsME.Hair.WHITE_BEADS,

                CharacterMaterialsME.Hair.GRAY_COPPER,
                CharacterMaterialsME.Hair.GRAY_GOLD,
                CharacterMaterialsME.Hair.GRAY_ALMANDINE,
                CharacterMaterialsME.Hair.GRAY_BEADS
        );
    }

    private final static NpcTextureDataPreset BASE_PRESET;
    private final static NpcTextureDataPreset HUMAN_MALE_PRESET;
    private final static NpcTextureDataPreset HUMAN_FEMALE_PRESET;

    private final static NpcTextureData COMMON;

    public static List<NpcTextureDataRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
            new NpcTextureDataRegistry.RegisterableNpcTextureData(NpcTextureDataRegistry.GENERIC_HUMAN, COMMON)
        );
    }
    //region [PRESETS]
    static {
        BASE_PRESET = new NpcTextureDataPreset()
            .withMaterials(NpcTextureType.SKIN, List.of(
                    CharacterMaterialsME.Skin.TAN,
                    CharacterMaterialsME.Skin.TAN_DESATURATED,
                    CharacterMaterialsME.Skin.PALE,
                    CharacterMaterialsME.Skin.BEIGE
            ))
            .withMaterials(NpcTextureType.EYE, List.of(
                    CharacterMaterialsME.Eye.BLUE,
                    CharacterMaterialsME.Eye.GREEN,
                    CharacterMaterialsME.Eye.DARK_GREEN,
                    CharacterMaterialsME.Eye.NAVY,
                    CharacterMaterialsME.Eye.BROWN
            ))
            .withPatterns(NpcTextureType.EYE, List.of(
                    CharacterPatternsME.Eyes.Eye.COMMON
            ))
            .withMaterials(NpcTextureType.HAIR, UtilData.DEFAULT_HAIR)
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                    CharacterPatternsME.Hairs.Eyebrow.UNI,
                    CharacterPatternsME.Hairs.Eyebrow.BASIC,
                    CharacterPatternsME.Hairs.Eyebrow.SHORT
            ))
            .withMaterials(NpcTextureType.CLOTHING, List.of(
                    CharacterMaterialsME.Clothing.WHITE,
                    CharacterMaterialsME.Clothing.BROWN
            ))
            .withPatterns(NpcTextureType.CLOTHING, List.of(
                    CharacterPatternsME.Clothing.TOGA,
                    CharacterPatternsME.Clothing.FULL_TOGA,
                    CharacterPatternsME.Clothing.SKIRT
            ));

        HUMAN_MALE_PRESET = new NpcTextureDataPreset()
            .withPatterns(NpcTextureType.BODY, List.of(
                    CharacterPatternsME.Skins.Body.MUSCULAR
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                    CharacterPatternsME.Skins.Head.MALE
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                    CharacterPatternsME.Hairs.Eyebrow.UNI,
                    CharacterPatternsME.Hairs.Eyebrow.BASIC,
                    CharacterPatternsME.Hairs.Eyebrow.SHORT
            ))
            .withPatterns(NpcTextureType.HAIR, Stream.of(
                    CharacterPatternsME.Hairs.Hair.BOWL,
                    CharacterPatternsME.Hairs.Hair.BALD_SIDES,
                    CharacterPatternsME.Hairs.Hair.SHORT,
                    CharacterPatternsME.Hairs.Hair.TOP_BALDING,
                    CharacterPatternsME.Hairs.Hair.SIDE_BALDING,
                    null).toList()
            )
            .withPatterns(NpcTextureType.BEARD, Stream.of(
                    CharacterPatternsME.Hairs.Beard.CLEAN,
                    CharacterPatternsME.Hairs.Beard.SHORT,
                    CharacterPatternsME.Hairs.Beard.SINGLE,
                    CharacterPatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                    null).toList());
        HUMAN_FEMALE_PRESET = new NpcTextureDataPreset()
            .withPatterns(NpcTextureType.BODY, List.of(
                    CharacterPatternsME.Skins.Body.FEMALE
            ))
            .withPatterns(NpcTextureType.HEAD, List.of(
                    CharacterPatternsME.Skins.Head.FEMALE
            ))
            .withPatterns(NpcTextureType.EYEBROW, List.of(
                    CharacterPatternsME.Hairs.Eyebrow.UNI,
                    CharacterPatternsME.Hairs.Eyebrow.BASIC,
                    CharacterPatternsME.Hairs.Eyebrow.SHORT
            ))
            .withPatterns(NpcTextureType.HAIR, Stream.of(
                    CharacterPatternsME.Hairs.Hair.LONG,
                    CharacterPatternsME.Hairs.Hair.SEMI_LONG,
                    CharacterPatternsME.Hairs.Hair.VERY_LONG,
                    null).toList()
            )
            .withPatterns(NpcTextureType.CLOTHING, List.of(CharacterPatternsME.Clothing.ROBE));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON= new NpcTextureData(
            new HashMap<>(){{
                put(EntityCategory.SHARED, List.of(BASE_PRESET));
                put(EntityCategory.MALE, List.of(HUMAN_MALE_PRESET));
                put(EntityCategory.FEMALE, List.of(HUMAN_FEMALE_PRESET));
            }}
        );
    }
    // endregion
}
