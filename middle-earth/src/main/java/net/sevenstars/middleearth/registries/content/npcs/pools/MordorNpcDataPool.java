package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.content.npcs.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class MordorNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.MORDOR;

    private static final int DARK_RED = 0x4f251d;
    private static final int DARK_BROWN = 0x4a3c34;
    private static final int DARK = 0x1d1f1e;

    private static List<Integer> allColors;

    public final static NpcData BLACK_NUMENOREAN;
    public final static NpcData SNAGA;
    public final static NpcData SCOUT;
    public final static NpcData MILITIA;
    public final static NpcData WARRIOR;
    public final static NpcData VETERAN;
    public final static NpcData CAPTAIN;
    public final static NpcData DOL_GULDUR_SCOUT;
    public final static NpcData DOL_GULDUR_WARRIOR;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_SNAGA, SNAGA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_SCOUT, SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_VETERAN, VETERAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_CAPTAIN, CAPTAIN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.MORDOR_BLACK_NUMENOREAN, BLACK_NUMENOREAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.DOL_GULDUR_SCOUT, DOL_GULDUR_SCOUT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.DOL_GULDUR_WARRIOR, DOL_GULDUR_WARRIOR)
        );
    }

    static {
        allColors = List.of(DARK_RED, DARK_BROWN, DARK);

        BLACK_NUMENOREAN = new NpcData(NpcRegistry.MORDOR_BLACK_NUMENOREAN.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.MORDOR_BLACK_NUMENOREAN, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET).withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.MORDOR_BLACK_NUMENOREAN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BLACK_NUMENOREAN_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.BLACK_NUMENOREAN_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.BLACK_NUMENOREAN_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SNAGA = new NpcData(NpcRegistry.MORDOR_SNAGA.getValue(), RaceRegistry.SNAGA, FACTION, TexturePresetsRegistry.MORDOR_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_SNOUT_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.HOOD).withHood(HelmetAttachmentsME.HOOD, allColors))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_CAPE).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        MILITIA = new NpcData(NpcRegistry.MORDOR_MILITIA.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORDOR_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SWORD).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ROUND_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SCOUT = new NpcData(NpcRegistry.MORDOR_SCOUT.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORDOR_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.SURCOAT, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.SURCOAT).withColors(allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BOW).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(3))
                                .add(WeightedItemData.create(Items.BOW).withWeight(1))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        WARRIOR = new NpcData(NpcRegistry.MORDOR_WARRIOR.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.MORDOR_BLACK_URUK, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_NASAL_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_KETTLE_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_MANDIBLE_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_SALLET))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CREST_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_PAINTED_CUIRASS).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_GORGET_HAUBERK).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))

                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.MORDOR_REINFORCED_COAT).withColors(allColors).withCape(BackAttachmentsME.CAPE, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_FALCHION).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SWORD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BRACED_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_LARGE_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcData(NpcRegistry.MORDOR_VETERAN.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.MORDOR_BLACK_URUK, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ELITE_CLEAVER))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ELITE_WARBLADE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_HEAVY_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        CAPTAIN = new NpcData(NpcRegistry.MORDOR_CAPTAIN.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.MORDOR_BLACK_URUK, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE).withCape(BackAttachmentsME.CAPE, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE).withCape(BackAttachmentsME.SHOULDER_CAPE_LEFT, allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE).withCape(BackAttachmentsME.SHOULDER_CAPE_RIGHT, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BLACK_URUK_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ELITE_CLEAVER))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_ELITE_WARBLADE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_HEAVY_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        DOL_GULDUR_SCOUT = new NpcData(NpcRegistry.DOL_GULDUR_SCOUT.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORDOR_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_HUNTER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_RAIDER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_PADDED_CHESTPLATE).withWeight(5))

                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE).withWeight(2))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT).withCape(BackAttachmentsME.ORCISH_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK).withCape(BackAttachmentsME.ORCISH_CAPE))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColors(allColors).withCape(BackAttachmentsME.ORCISH_SHOULDERS, allColors))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_RAIDER_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ORC_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_AXE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(3))
                                .add(WeightedItemData.create(Items.BOW).withWeight(1))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_EXECUTIONER_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_EXECUTIONER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_EXECUTIONER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColors(allColors))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColors(allColors))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        DOL_GULDUR_WARRIOR = new NpcData(NpcRegistry.DOL_GULDUR_WARRIOR.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.MORDOR_ORC, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_MARAUDER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_MARAUDER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_MARAUDER_LEGGINGS))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_MARAUDER_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_MACHETE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_AXE).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_PAVISE))
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_SHIELD))
                                .add(WeightedItemData.create(Items.AIR))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_STALKER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_STALKER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_STALKER_LEGGINGS))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_STALKER_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_MACHETE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_AXE).withWeight(2))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_PAVISE))
                                .add(WeightedItemData.create(WeaponItemsME.DOL_GULDUR_SHIELD))
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}
