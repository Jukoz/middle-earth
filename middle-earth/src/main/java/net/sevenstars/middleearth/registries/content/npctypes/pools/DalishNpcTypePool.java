package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.content.npctypes.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.GearSlotPool;

import java.util.List;

public class DalishNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.DALE;

    public final static NpcType PEASANT;

    public final static NpcType MILITIA;
    public final static NpcType SOLDIER;
    public final static NpcType ARCHER;
    public final static NpcType KNIGHT;
    public final static NpcType ELITE_ARCHER;
    public final static NpcType VETERAN;
    public final static NpcType SERGEANT;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_PEASANT, PEASANT),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_MILITIA, MILITIA),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_SOLDIER, SOLDIER),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_ARCHER, ARCHER),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_KNIGHT, KNIGHT),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_ELITE_ARCHER, ELITE_ARCHER),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_VETERAN, VETERAN),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.DALE_SERGEANT, SERGEANT)
        );
    }

    static {
        PEASANT = new NpcType(NpcRegistry.DALE_PEASANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(1))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withWeight(1))
                                .add(WeightedItemData.create().withWeight(10))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.FISHING_ROD))
                                .add(WeightedItemData.create(Items.IRON_HOE))
                                .add(WeightedItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(WeightedItemData.create(ToolItemsME.BRONZE_AXE))
                                .add(WeightedItemData.create().withWeight(4))
                        )
                        .withWeight(10),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.TAN_FUR_HOOD).withHood(HelmetAttachmentsME.TAN_FUR_HOOD, true))
                                .add(WeightedItemData.create(EquipmentItemsME.GRAY_FUR_HOOD).withHood(HelmetAttachmentsME.GRAY_FUR_HOOD, true))
                                .add(WeightedItemData.create(EquipmentItemsME.BROWN_FUR_HOOD).withHood(HelmetAttachmentsME.BROWN_FUR_HOOD, true))
                                .add(WeightedItemData.create().withWeight(1))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.CLOAK).withColor(4536623)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.FISHING_ROD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.BRONZE_AXE))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        MILITIA = new NpcType(NpcRegistry.DALE_MILITIA.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                            .add(WeightedItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR))
                            .add(WeightedItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR))
                            .add(WeightedItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.IRON_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(WeightedItemData.create(Items.SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SOLDIER = new NpcType(NpcRegistry.DALE_SOLDIER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(WeightedItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.IRON_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        ARCHER = new NpcType(NpcRegistry.DALE_ARCHER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(WeightedItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR).withCape(BackAttachmentsME.SURCOAT))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR).withCape(BackAttachmentsME.SURCOAT))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR).withCape(BackAttachmentsME.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_LONGBOW))
                                .add(WeightedItemData.create(Items.BOW))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        KNIGHT = new NpcType(NpcRegistry.DALE_KNIGHT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(WeightedItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        ELITE_ARCHER = new NpcType(NpcRegistry.DALE_ELITE_ARCHER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_LONGBOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcType(NpcRegistry.DALE_VETERAN.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_LORD, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(WeightedItemData.create().withWeight(1))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(BackAttachmentsME.BARDING_SURCOAT))
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK).withCape(BackAttachmentsME.BARDING_SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SERGEANT = new NpcType(NpcRegistry.DALE_SERGEANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_LORD, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SERGEANT_HELMET).withWeight(3))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE).withWeight(3).withCape(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}
