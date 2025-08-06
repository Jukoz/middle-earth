package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.npcs.pools.*;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Middle-earth mod npc registry<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class NpcME {
    public final static String PATH = "npcs";
    public static final RegistryKey<Registry<NpcData>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));
    public static List<RegistryKey<NpcData>> allNpcDatas;
    // [GENERIC]
    public final static NpcData HUMAN_CIVILIAN;
    public final static NpcData DWARF_CIVILIAN;
    public final static NpcData ELF_CIVILIAN;
    public final static NpcData HOBBIT_CIVILIAN;
    public final static NpcData ORC_CIVILIAN;
    public final static NpcData URUK_CIVILIAN;

    public static NpcTextureData COMMON_TEXTURE_TEST = new NpcTextureData(new HashMap<>(){{
        put(EntityCategory.MALE, List.of(
                new NpcTextureDataPreset()
                        .withPatterns(NpcTextureType.BODY, List.of(
                                NpcTexturePatternsME.Skins.Body.MUSCULAR,
                                NpcTexturePatternsME.Skins.Body.FEMALE,
                                NpcTexturePatternsME.Skins.Body.SLIM
                        ))
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                NpcTexturePatternsME.Skins.Head.MALE,
                                NpcTexturePatternsME.Skins.Head.FEMALE,
                                NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_WISE,
                                NpcTexturePatternsME.Skins.Head.URUK_DUMB,
                                NpcTexturePatternsME.Skins.Head.URUK_TALL_DUMB
                        ))
                        .withPatterns(NpcTextureType.SCAR,
                                Stream.of(
                                                NpcTexturePatternsME.Skins.Scar.EYE_RIGHT,
                                                NpcTexturePatternsME.Skins.Scar.EYE_LEFT,
                                                null)
                                        .toList()
                        )
                        .withPatterns(NpcTextureType.EAR, List.of(
                                NpcTexturePatternsME.Skins.Ear.LARGE_POINTY,
                                NpcTexturePatternsME.Skins.Ear.NORMAL,
                                NpcTexturePatternsME.Skins.Ear.POINTY,
                                NpcTexturePatternsME.Skins.Ear.SMALL,
                                NpcTexturePatternsME.Skins.Ear.SMALL_POINTY,
                                NpcTexturePatternsME.Skins.Ear.SQUARE,
                                NpcTexturePatternsME.Skins.Ear.SQUARE_POINTY,
                                NpcTexturePatternsME.Skins.Ear.WIDE_POINTY
                        ))
                        .withPatterns(NpcTextureType.NOSE, Stream.of(
                                NpcTexturePatternsME.Skins.Nose.CUBE
                                //,
                                //NpcTexturePatternsME.Skins.Nose.LARGE_CUBE_CENTER,
                                //NpcTexturePatternsME.Skins.Nose.LARGE_CUBE_HIGH,
                                //NpcTexturePatternsME.Skins.Nose.VILLAGER
                            )
                            .toList()
                        )
                        .withMaterials(NpcTextureType.SKIN, List.of(
                                NpcTextureMaterialsME.Skin.PALE,
                                NpcTextureMaterialsME.Skin.OLIVE,
                                NpcTextureMaterialsME.Skin.BEIGE,
                                NpcTextureMaterialsME.Skin.GREENISH,
                                NpcTextureMaterialsME.Skin.BROWN,
                                NpcTextureMaterialsME.Skin.TAN,
                                NpcTextureMaterialsME.Skin.TAN_DESATURATED
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                NpcTexturePatternsME.Eyes.Eye.COMMON,
                                NpcTexturePatternsME.Eyes.Eye.SMALL_WIDE,
                                NpcTexturePatternsME.Eyes.Eye.SMALL_HIGH_WIDE,
                                NpcTexturePatternsME.Eyes.Eye.SMALL
                        ))
                        .withMaterials(NpcTextureType.EYE, List.of(
                                NpcTextureMaterialsME.Eye.BLACK,
                                NpcTextureMaterialsME.Eye.BLUE,
                                NpcTextureMaterialsME.Eye.BROWN,
                                NpcTextureMaterialsME.Eye.DARK_GREEN,
                                NpcTextureMaterialsME.Eye.GREEN,
                                NpcTextureMaterialsME.Eye.NAVY
                        ))
                        .withEmissiveEyes(false)
                        .withPatterns(NpcTextureType.HAIR, Stream.of(
                                        NpcTexturePatternsME.Hairs.Hair.SHORT,
                                        NpcTexturePatternsME.Hairs.Hair.UNCUT,
                                        NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                                        NpcTexturePatternsME.Hairs.Hair.SHARP,
                                        NpcTexturePatternsME.Hairs.Hair.BOWL,
                                        NpcTexturePatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS,
                                        NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP,
                                        NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Hair.TOP_BALDING,
                                        NpcTexturePatternsME.Hairs.Hair.SIDE_BALDING)
                                .toList()
                        )
                        .withPatterns(NpcTextureType.EYEBROW, List.of(
                                /*
                                                                NpcTexturePatternsME.Hairs.Eyebrow.SHORT,
                                NpcTexturePatternsME.Hairs.Eyebrow.BASIC,
                                NpcTexturePatternsME.Hairs.Eyebrow.LONG,
                                NpcTexturePatternsME.Hairs.Eyebrow.UNI,
                                 */
                                NpcTexturePatternsME.Hairs.Eyebrow.THICK

                                ))
                        .withPatterns(NpcTextureType.BEARD, Stream.of(
                                        NpcTexturePatternsME.Hairs.Beard.VERY_BROAD,
                                        NpcTexturePatternsME.Hairs.Beard.VERY_LONG,
                                        NpcTexturePatternsME.Hairs.Beard.CHUNKY_BRAIDS,
                                        NpcTexturePatternsME.Hairs.Beard.CLEAN,
                                        NpcTexturePatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Beard.DUAL_ORNAMENTED,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.HAIR, List.of(
                                NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BLACK_BEADS,
                                NpcTextureMaterialsME.Hair.BLACK_COPPER,
                                NpcTextureMaterialsME.Hair.BLACK_GOLD,
                                NpcTextureMaterialsME.Hair.BLACK_SILVER,

                                NpcTextureMaterialsME.Hair.COLD_BLACK_ALMANDINE,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_BEADS,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_COPPER,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_GOLD,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_SILVER,

                                NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.BROWN_SILVER,

                                NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_SILVER,

                                NpcTextureMaterialsME.Hair.BLONDE_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BLONDE_BEADS,
                                NpcTextureMaterialsME.Hair.BLONDE_COPPER,
                                NpcTextureMaterialsME.Hair.BLONDE_GOLD,
                                NpcTextureMaterialsME.Hair.BLONDE_SILVER,

                                NpcTextureMaterialsME.Hair.STRAW_ALMANDINE,
                                NpcTextureMaterialsME.Hair.STRAW_BEADS,
                                NpcTextureMaterialsME.Hair.STRAW_COPPER,
                                NpcTextureMaterialsME.Hair.STRAW_GOLD,
                                NpcTextureMaterialsME.Hair.STRAW_SILVER,

                                NpcTextureMaterialsME.Hair.GINGER_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GINGER_BEADS,
                                NpcTextureMaterialsME.Hair.GINGER_COPPER,
                                NpcTextureMaterialsME.Hair.GINGER_GOLD,
                                NpcTextureMaterialsME.Hair.GINGER_SILVER,

                                NpcTextureMaterialsME.Hair.GRAY_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GRAY_BEADS,
                                NpcTextureMaterialsME.Hair.GRAY_COPPER,
                                NpcTextureMaterialsME.Hair.GRAY_GOLD,
                                NpcTextureMaterialsME.Hair.GRAY_SILVER,

                                NpcTextureMaterialsME.Hair.WHITE_ALMANDINE,
                                NpcTextureMaterialsME.Hair.WHITE_BEADS,
                                NpcTextureMaterialsME.Hair.WHITE_COPPER,
                                NpcTextureMaterialsME.Hair.WHITE_GOLD,
                                NpcTextureMaterialsME.Hair.WHITE_SILVER
                        ))
                        .withPatterns(NpcTextureType.CLOTHING, List.of(
                                NpcTexturePatternsME.Clothing.ROBE,
                                NpcTexturePatternsME.Clothing.FULL_TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT,
                                NpcTexturePatternsME.Clothing.TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT_WITH_STROPHIUM
                        ))
                        .withMaterials(NpcTextureType.CLOTHING, List.of(
                                NpcTextureMaterialsME.Clothing.WHITE,
                                NpcTextureMaterialsME.Clothing.BROWN,
                                NpcTextureMaterialsME.Clothing.GRAY,
                                NpcTextureMaterialsME.Clothing.ROT_GREEN
                        ))
        ));
        put(EntityCategory.FEMALE, List.of(
                new NpcTextureDataPreset()
                        .withPatterns(NpcTextureType.BODY, List.of(
                                NpcTexturePatternsME.Skins.Body.MUSCULAR,
                                NpcTexturePatternsME.Skins.Body.FEMALE,
                                NpcTexturePatternsME.Skins.Body.SLIM
                        ))
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                NpcTexturePatternsME.Skins.Head.MALE,
                                NpcTexturePatternsME.Skins.Head.FEMALE,
                                NpcTexturePatternsME.Skins.Head.GOBLIN_SMALL_WISE,
                                NpcTexturePatternsME.Skins.Head.URUK_DUMB,
                                NpcTexturePatternsME.Skins.Head.URUK_TALL_DUMB
                        ))
                        .withPatterns(NpcTextureType.SCAR,
                                Stream.of(
                                    NpcTexturePatternsME.Skins.Scar.EYE_LEFT,
                                            NpcTexturePatternsME.Skins.Scar.EYE_RIGHT,
                                                null)
                                        .toList()
                        )
                        .withPatterns(NpcTextureType.EAR, List.of(
                                NpcTexturePatternsME.Skins.Ear.LARGE_POINTY,
                                NpcTexturePatternsME.Skins.Ear.NORMAL,
                                NpcTexturePatternsME.Skins.Ear.POINTY,
                                NpcTexturePatternsME.Skins.Ear.SMALL,
                                NpcTexturePatternsME.Skins.Ear.SMALL_POINTY,
                                NpcTexturePatternsME.Skins.Ear.SQUARE,
                                NpcTexturePatternsME.Skins.Ear.SQUARE_POINTY,
                                NpcTexturePatternsME.Skins.Ear.WIDE_POINTY
                        ))
                        .withPatterns(NpcTextureType.NOSE, Stream.of(
                                        NpcTexturePatternsME.Skins.Nose.CUBE,
                                        null
                                        //,
                                        //NpcTexturePatternsME.Skins.Nose.LARGE_CUBE_CENTER,
                                        //NpcTexturePatternsME.Skins.Nose.LARGE_CUBE_HIGH,
                                        //NpcTexturePatternsME.Skins.Nose.VILLAGER
                                ).toList()
                        )
                        .withMaterials(NpcTextureType.SKIN, List.of(
                                NpcTextureMaterialsME.Skin.PALE,
                                NpcTextureMaterialsME.Skin.OLIVE,
                                NpcTextureMaterialsME.Skin.BEIGE,
                                NpcTextureMaterialsME.Skin.GREENISH,
                                NpcTextureMaterialsME.Skin.BROWN,
                                NpcTextureMaterialsME.Skin.TAN,
                                NpcTextureMaterialsME.Skin.TAN_DESATURATED
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                NpcTexturePatternsME.Eyes.Eye.COMMON,
                                NpcTexturePatternsME.Eyes.Eye.SMALL_WIDE,
                                NpcTexturePatternsME.Eyes.Eye.SMALL_HIGH_WIDE,
                                NpcTexturePatternsME.Eyes.Eye.SMALL
                        ))
                        .withMaterials(NpcTextureType.EYE, List.of(
                                NpcTextureMaterialsME.Eye.BLACK,
                                NpcTextureMaterialsME.Eye.BLUE,
                                NpcTextureMaterialsME.Eye.BROWN,
                                NpcTextureMaterialsME.Eye.DARK_GREEN,
                                NpcTextureMaterialsME.Eye.GREEN,
                                NpcTextureMaterialsME.Eye.NAVY
                        ))
                        .withEmissiveEyes(false)
                        .withPatterns(NpcTextureType.HAIR, Stream.of(
                                        NpcTexturePatternsME.Hairs.Hair.SHORT,
                                        NpcTexturePatternsME.Hairs.Hair.UNCUT,
                                        NpcTexturePatternsME.Hairs.Hair.BALD_SIDES,
                                        NpcTexturePatternsME.Hairs.Hair.SHARP,
                                        NpcTexturePatternsME.Hairs.Hair.BOWL,
                                        NpcTexturePatternsME.Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Hair.BALD_SMALL_DREADLOCKS,
                                        NpcTexturePatternsME.Hairs.Hair.DIRTY_MOP,
                                        NpcTexturePatternsME.Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Hair.TOP_BALDING,
                                        NpcTexturePatternsME.Hairs.Hair.SIDE_BALDING)
                                .toList()
                        )
                        .withPatterns(NpcTextureType.EYEBROW, List.of(
                                NpcTexturePatternsME.Hairs.Eyebrow.SHORT,
                                NpcTexturePatternsME.Hairs.Eyebrow.BASIC,
                                NpcTexturePatternsME.Hairs.Eyebrow.LONG,
                                NpcTexturePatternsME.Hairs.Eyebrow.UNI,
                                NpcTexturePatternsME.Hairs.Eyebrow.THICK
                        ))
                        .withPatterns(NpcTextureType.BEARD, Stream.of(
                                        NpcTexturePatternsME.Hairs.Beard.SHORT,
                                        NpcTexturePatternsME.Hairs.Beard.LARGE,
                                        NpcTexturePatternsME.Hairs.Beard.SINGLE,
                                        NpcTexturePatternsME.Hairs.Beard.CLEAN,
                                        NpcTexturePatternsME.Hairs.Beard.UNCLEAN_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Beard.LONG_SINGLE_ORNAMENTED,
                                        NpcTexturePatternsME.Hairs.Beard.DUAL_ORNAMENTED,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.HAIR, List.of(
                                NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BLACK_BEADS,
                                NpcTextureMaterialsME.Hair.BLACK_COPPER,
                                NpcTextureMaterialsME.Hair.BLACK_GOLD,
                                NpcTextureMaterialsME.Hair.BLACK_SILVER,

                                NpcTextureMaterialsME.Hair.COLD_BLACK_ALMANDINE,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_BEADS,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_COPPER,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_GOLD,
                                NpcTextureMaterialsME.Hair.COLD_BLACK_SILVER,

                                NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.BROWN_SILVER,

                                NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_SILVER,

                                NpcTextureMaterialsME.Hair.BLONDE_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BLONDE_BEADS,
                                NpcTextureMaterialsME.Hair.BLONDE_COPPER,
                                NpcTextureMaterialsME.Hair.BLONDE_GOLD,
                                NpcTextureMaterialsME.Hair.BLONDE_SILVER,

                                NpcTextureMaterialsME.Hair.STRAW_ALMANDINE,
                                NpcTextureMaterialsME.Hair.STRAW_BEADS,
                                NpcTextureMaterialsME.Hair.STRAW_COPPER,
                                NpcTextureMaterialsME.Hair.STRAW_GOLD,
                                NpcTextureMaterialsME.Hair.STRAW_SILVER,

                                NpcTextureMaterialsME.Hair.GINGER_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GINGER_BEADS,
                                NpcTextureMaterialsME.Hair.GINGER_COPPER,
                                NpcTextureMaterialsME.Hair.GINGER_GOLD,
                                NpcTextureMaterialsME.Hair.GINGER_SILVER,

                                NpcTextureMaterialsME.Hair.GRAY_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GRAY_BEADS,
                                NpcTextureMaterialsME.Hair.GRAY_COPPER,
                                NpcTextureMaterialsME.Hair.GRAY_GOLD,
                                NpcTextureMaterialsME.Hair.GRAY_SILVER,

                                NpcTextureMaterialsME.Hair.WHITE_ALMANDINE,
                                NpcTextureMaterialsME.Hair.WHITE_BEADS,
                                NpcTextureMaterialsME.Hair.WHITE_COPPER,
                                NpcTextureMaterialsME.Hair.WHITE_GOLD,
                                NpcTextureMaterialsME.Hair.WHITE_SILVER
                        ))
                        .withPatterns(NpcTextureType.CLOTHING, List.of(
                                NpcTexturePatternsME.Clothing.ROBE,
                                NpcTexturePatternsME.Clothing.FULL_TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT,
                                NpcTexturePatternsME.Clothing.TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT_WITH_STROPHIUM
                        ))
                        .withMaterials(NpcTextureType.CLOTHING, List.of(
                                NpcTextureMaterialsME.Clothing.WHITE,
                                NpcTextureMaterialsME.Clothing.BROWN,
                                NpcTextureMaterialsME.Clothing.GRAY,
                                NpcTextureMaterialsME.Clothing.ROT_GREEN
                        ))
        ));
    }});


    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Npcs for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, NpcData.CODEC);
    }

    public static void bootstrap(Registerable<NpcData> context) {
        RegistryEntryLookup<NpcData> npcRegistryEntryLookup = context.getRegistryLookup(KEY);
        // [RACE / GENERIC]
        register(context, npcRegistryEntryLookup, HUMAN_CIVILIAN);
        register(context, npcRegistryEntryLookup, DWARF_CIVILIAN);
        register(context, npcRegistryEntryLookup, ELF_CIVILIAN);
        register(context, npcRegistryEntryLookup, HOBBIT_CIVILIAN);
        register(context, npcRegistryEntryLookup, ORC_CIVILIAN);
        register(context, npcRegistryEntryLookup, URUK_CIVILIAN);

        // [GONDOR]
        registerAll(context, npcRegistryEntryLookup, GondorianNpcDataPool.fetchAll());
        // [ROHAN]
        registerAll(context, npcRegistryEntryLookup, RohirricNpcDataPool.fetchAll());
        // [DALE]
        registerAll(context, npcRegistryEntryLookup, DalishNpcDataPool.fetchAll());
        // [LONGBEARDS] - [EREBOR]
        registerAll(context, npcRegistryEntryLookup, EreborNpcDataPool.fetchAll());
        // [LOTHLORIEN]
        registerAll(context, npcRegistryEntryLookup, LorienNpcDataPool.fetchAll());
        // [MORDOR]
        registerAll(context, npcRegistryEntryLookup, MordorNpcDataPool.fetchAll());
        // [MISTY MOUNTAINS GOBLINS]
        registerAll(context, npcRegistryEntryLookup, MistyMountainsGoblinsNpcDataPool.fetchAll());
        // [ISENGARD]
        registerAll(context, npcRegistryEntryLookup, IsengardNpcDataPool.fetchAll());
        // [SHIRE]
        registerAll(context, npcRegistryEntryLookup, ShireNpcDataPool.fetchAll());
        // [BANDIT]
        registerAll(context, npcRegistryEntryLookup, BanditNpcDataPool.fetchAll());
    }

    private static void registerAll(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, List<NpcData> npcDatas) {
        NpcME.allNpcDatas = List.of();

        for(NpcData data : npcDatas){
            RegistryKey<NpcData> registered = register(context, npcRegistryEntryLookup, data);
            NpcME.allNpcDatas.add(registered);
        }
    }

    public static RegistryKey<NpcData> register(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, NpcData npcData) {
        RegistryKey<NpcData> npcRegistryKey = of(npcData.getName());
        String name = npcRegistryKey.getValue().getPath();
        RegistryKey<NpcData> npcKey = RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcData>> optionalNpc = npcRegistryEntryLookup.getOptional(npcRegistryKey);
        optionalNpc.ifPresent(npcReference -> context.register(npcKey, npcData));

        return npcKey;
    }

    private static RegistryKey<NpcData> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        // region [GENERIC/TEMPORARY]
        HUMAN_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "human.civilian"), RacesME.HUMAN, FactionsME.GONDOR, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withWeight(1))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.WORK_SHOES).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.SHOES).withWeight(2))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ), new HashMap<>(), COMMON_TEXTURE_TEST);

        DWARF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dwarf.civilian"), RacesME.DWARF, FactionsME.GONDOR, List.of(
                NpcGearData.create()
        ), new HashMap<>(), COMMON_TEXTURE_TEST);

        ELF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "elf.civilian"), RacesME.ELF, FactionsME.GONDOR, List.of(
                NpcGearData.create()
        ), new HashMap<>(), COMMON_TEXTURE_TEST);

        HOBBIT_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "hobbit.civilian"), RacesME.HOBBIT, FactionsME.GONDOR, List.of(
                NpcGearData.create()
        ), new HashMap<>(), COMMON_TEXTURE_TEST);

        ORC_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "orc.civilian"), RacesME.ORC, FactionsME.GONDOR, List.of(
                NpcGearData.create()
        ), new HashMap<>(), COMMON_TEXTURE_TEST);

        URUK_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "uruk.civilian"), RacesME.URUK, FactionsME.GONDOR, List.of(
                NpcGearData.create()
        ), new HashMap<>(), COMMON_TEXTURE_TEST);
        // endregion
    }
}
