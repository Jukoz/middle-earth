package net.sevenstars.middleearth.registries.content.texturepresets.pools.woodlandrealm;

import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePreset;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedClothingPresetHolder;
import net.sevenstars.middleearth.resources.datas.texture_presets.WeightedTexturePresetHolder;

import java.util.HashMap;
import java.util.List;

public class WoodlandRealmTexturePresetsPool {
    private final static WeightedTexturePresetHolder BASE_PRESET;
    private final static WeightedTexturePresetHolder MALE_PRESET;
    private final static WeightedTexturePresetHolder FEMALE_PRESET;
    private final static WeightedTexturePresetHolder ELITE_PRESET;

    public final static TexturePresetDataPool COMMON;
    public final static TexturePresetDataPool SENTINEL;
    public final static TexturePresetDataPool NIGHTSHADE;
    public final static TexturePresetDataPool COMMANDER;
    public final static TexturePresetDataPool WARDEN_OF_THE_GLADE;

    public static List<TexturePresetsRegistry.RegisterableNpcTextureData> fetchAll() {
        return List.of(
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WOODLAND_REALM_ELF, COMMON),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WOODLAND_REALM_SENTINEL, SENTINEL),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WOODLAND_REALM_NIGHTSHADE, NIGHTSHADE),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WOODLAND_REALM_COMMANDER, COMMANDER),
                new TexturePresetsRegistry.RegisterableNpcTextureData(TexturePresetsRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE, WARDEN_OF_THE_GLADE)
        );
    }

    //region [PRESETS]
    static {
        BASE_PRESET = new WeightedTexturePresetHolder()
                .withMaterials(CharacterMaterialTypes.SKIN, List.of(
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.PALE),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Skin.BEIGE)
                ))
                .withPatterns(CharacterPatternTypes.EAR, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Ear.SMALL)
                ))
                .withPatterns(CharacterPatternTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Eyes.Eye.COMMON)
                ))
                .withMaterials(CharacterMaterialTypes.EYE, List.of(
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.BROWN),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Eye.GREEN)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.RED_SILVER).withWeight(2),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.DARK_BROWN_SILVER).withWeight(2),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_SILVER)
                ))
                .withPatterns(CharacterPatternTypes.EYEBROW, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.BASIC),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Eyebrow.SHORT)
                ));

        MALE_PRESET = new WeightedTexturePresetHolder()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.UNCUT),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.SEMI_LONG)
                ))
                .withClothes(List.of(
                        new WeightedClothingPresetHolder[]{
                                new WeightedClothingPresetHolder(
                                        List.of(
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN)
                                        ),
                                        List.of(
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BEIGE),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SILVER_TUNIC),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.TAN_TUNIC),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY)
                                        )
                                )
                        }
                ));

        FEMALE_PRESET = new WeightedTexturePresetHolder()
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.SLIM),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.FEMALE)
                ))
                .withPatterns(CharacterPatternTypes.HAIR, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.LONG),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.FLAT_LONG),
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Hairs.Hair.VERY_LONG)
                ))
                .withClothes(List.of(
                        new WeightedClothingPresetHolder[]{
                                new WeightedClothingPresetHolder(
                                        List.of(
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN)
                                        ),
                                        List.of(
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BEIGE),
                                                WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY)
                                        )
                                )
                        }
                ));

        ELITE_PRESET = MALE_PRESET.copy()
                .clearPatterns(CharacterPatternTypes.BODY)
                .clearMaterials(CharacterMaterialTypes.HAIR)
                .withPatterns(CharacterPatternTypes.HEAD, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Head.MALE)
                ))
                .withPatterns(CharacterPatternTypes.BODY, List.of(
                        WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Body.MUSCULAR)
                ))
                .withMaterials(CharacterMaterialTypes.HAIR, List.of(
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.RED_SILVER),
                        WeightedIdentifier.fromKey(CharacterMaterialsRegistryME.Hair.BLACK_SILVER)
                ));
    }
    // endregion

    // region [DATAS]
    static {
        COMMON = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET));
            put(EntityCategories.MALE, new WeightedPool<>(MALE_PRESET));
            put(EntityCategories.FEMALE, new WeightedPool<>(FEMALE_PRESET));
        }});

        SENTINEL = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET.copy().clearClothes().withClothes(List.of(
                    new WeightedClothingPresetHolder[]{
                            new WeightedClothingPresetHolder(
                                    List.of(
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_DARK_BROWN),
                                        WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_BROWN)
                                    ),
                                    List.of(
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_BURGUNDY)
                                    ),
                                    List.of(
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SCARF_DARK_BROWN),
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SCARF_BROWN)
                                    )
                            )
                    }))));
            put(EntityCategories.MALE, new WeightedPool<>(MALE_PRESET.copy().clearClothes()));
            put(EntityCategories.FEMALE, new WeightedPool<>(FEMALE_PRESET.copy().clearClothes()));
        }});

        NIGHTSHADE = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(BASE_PRESET.copy().clearClothes().withClothes(List.of(
                    new WeightedClothingPresetHolder[]{
                            new WeightedClothingPresetHolder(
                                    List.of(
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Base.PANTS_NIGHTSHADE)
                                    ),
                                    List.of(
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Over.SHIRT_NIGHTSHADE)
                                    ),
                                    List.of(
                                            TexturePreset.EMPTY_VALUE_KEY.withWeight(2),
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SCARF_NIGHTSHADE).withWeight(2),
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SCARF_TEAL),
                                            WeightedIdentifier.fromIdentifier(CharacterClothesRegistryME.Extra.SCARF_RED)
                                    )
                            )
                    }))));
            put(EntityCategories.MALE, new WeightedPool<>(MALE_PRESET.copy().clearClothes()));
            put(EntityCategories.FEMALE, new WeightedPool<>(FEMALE_PRESET.copy().clearClothes()));
        }});

        COMMANDER = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(
                    BASE_PRESET.copy()
                               .clearMaterials(CharacterMaterialTypes.HAIR)
            ));
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                    ELITE_PRESET.copy().withPatterns(CharacterPatternTypes.SCAR, List.of(
                            TexturePreset.EMPTY_VALUE_KEY.withWeight(30),
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            )));
        }});

        WARDEN_OF_THE_GLADE = new TexturePresetDataPool(new HashMap<>(){{
            put(EntityCategories.SHARED, new WeightedPool<>(
                    BASE_PRESET.copy().clearMaterials(CharacterMaterialTypes.HAIR)
            ));
            put(EntityCategories.MALE, new WeightedPool<>(List.of(
                    ELITE_PRESET.copy().withPatterns(CharacterPatternTypes.SCAR, List.of(
                            TexturePreset.EMPTY_VALUE_KEY.withWeight(30),
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_LEFT),
                            WeightedIdentifier.fromKey(CharacterPatternsRegistryME.Skins.Scar.EYE_RIGHT)
                    ))
            )));
        }});
    }
    // endregion
}
