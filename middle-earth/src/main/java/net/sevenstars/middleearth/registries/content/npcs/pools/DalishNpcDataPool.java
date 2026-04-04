package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class DalishNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.DALE;

    public final static NpcData PEASANT;

    public final static NpcData MILITIA;
    public final static NpcData SOLDIER;
    public final static NpcData ARCHER;
    public final static NpcData KNIGHT;
    public final static NpcData ELITE_ARCHER;
    public final static NpcData VETERAN;
    public final static NpcData SERGEANT;

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
        PEASANT = new NpcData(NpcRegistry.DALE_PEASANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                GearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(WeightedItemData.create().withWeight(5))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.STONE_AXE))
                                .add(WeightedItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(WeightedItemData.create(ToolItemsME.PIPE))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MILITIA = new NpcData(NpcRegistry.DALE_MILITIA.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SOLDIER = new NpcData(NpcRegistry.DALE_SOLDIER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ARCHER = new NpcData(NpcRegistry.DALE_ARCHER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        KNIGHT = new NpcData(NpcRegistry.DALE_KNIGHT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ELITE_ARCHER = new NpcData(NpcRegistry.DALE_ELITE_ARCHER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
        VETERAN = new NpcData(NpcRegistry.DALE_VETERAN.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_LORD, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SERGEANT = new NpcData(NpcRegistry.DALE_SERGEANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_LORD, List.of(
                GearData.create()
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
