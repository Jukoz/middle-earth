package net.jukoz.me.resources.datas.npcs.pools;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.Identifier;

import java.util.List;

public class MistyMountainsGoblinsNpcDataPool {
    private final static String FACTION_BASE = "misty_mountains_goblins.";
    private static List<Integer> allColors;
    private static final int DARK_PURPLE = 0x3c3135;
    private static final int DARK_BROWN = 0x4a3c34;

    public final static NpcData MISTY_GOBLIN_SNAGA;
    public final static NpcData MISTY_GOBLIN_WARRIOR;
    public final static NpcData MISTY_GOBLIN_ARCHER;
    public final static NpcData MISTY_HOBGOBLIN_SOLDIER;
    public final static NpcData MISTY_HOBGOBLIN_VETERAN;
    public final static NpcData MISTY_HOBGOBLIN_LEADER;

    public static List<NpcData> fetchAll() {
        return List.of(
                MISTY_GOBLIN_SNAGA,
                MISTY_GOBLIN_WARRIOR,
                MISTY_GOBLIN_ARCHER,
                MISTY_HOBGOBLIN_SOLDIER,
                MISTY_HOBGOBLIN_VETERAN,
                MISTY_HOBGOBLIN_LEADER
        );
    }
    static {
        allColors = List.of(DARK_PURPLE, DARK_BROWN);
        MISTY_GOBLIN_SNAGA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "goblin_snaga"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_DAGGER).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_PICKAXE))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_HOE))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_SHOVEL))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                        )
        ));

        MISTY_GOBLIN_WARRIOR = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "goblin_warrior"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SEEKER_HELMET).withColors(allColors).withWeight(3))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_COIF))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_SHANK).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.ORC_KNIFE).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.CRUDE_FALCHION))
                                .add(NpcGearItemData.create(ModToolItems.CRUDE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ));

        MISTY_GOBLIN_ARCHER= new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "goblin_archer"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(8))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_COIF))
                                .add(NpcGearItemData.create(ModEquipmentItems.HOOD).withColors(allColors))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.ORCISH_LONG_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_BONE_PAULDRON).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_BOW).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.ORCISH_BOW))
                        )
        ));

        MISTY_HOBGOBLIN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "hobgoblin_soldierr"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SOLDIER_HELMET).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SCREECHER_HELMET).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SKULLCAP_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SEEKER_HELMET).withColors(allColors).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))


                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.ORCISH_SANDALS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_BATTLEAXE))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_CLEAVER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ));

        MISTY_HOBGOBLIN_VETERAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "hobgoblin_veteran"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET).withColors(allColors).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SOLDIER_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_SCREECHER_HELMET).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))


                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_CAPE))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT).withColors(allColors).withCape(ModCapes.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_MAIL_COAT))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS).withColors(allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_MAIL_COAT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATED_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_LACED_BOOTS).withColors(allColors))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_SPEAR).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_FALCHION).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_AXE).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_BATTLEAXE))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_CLEAVER))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_REINFORCED_SHIELD))
                        )
        ));

        MISTY_HOBGOBLIN_LEADER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE + "hobgoblin_leader"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_CAPTAIN_HELMET))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE).withWeight(4))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE).withCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE).withCape(ModCapes.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE).withCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE, allColors))

                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withWeight(2).withCape(ModCapes.CAPE, allColors))
                                .add(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE).withWeight(2).withCape(ModCapes.ORCISH_LONG_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_MAIL_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_BATTLEAXE).withWeight(4))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_SCIMITAR).withWeight(3))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_SPEAR).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_ELITE_CLEAVER).withWeight(2))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(ModWeaponItems.GUNDABAD_REINFORCED_SHIELD))
                                .add(NpcGearItemData.create(Items.AIR).withWeight(2))
                        )
        ));
    }
}
