package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

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
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(NpcGearItemData.create().withWeight(5))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.STONE_AXE))
                                .add(NpcGearItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(NpcGearItemData.create(ToolItemsME.PIPE))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MILITIA = new NpcData(NpcRegistry.DALE_MILITIA.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_PEASANT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR))
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR))
                            .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(NpcGearItemData.create(Items.SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SOLDIER = new NpcData(NpcRegistry.DALE_SOLDIER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.IRON_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ARCHER = new NpcData(NpcRegistry.DALE_ARCHER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR).withCape(BackAttachmentsME.SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR).withCape(BackAttachmentsME.SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR).withCape(BackAttachmentsME.SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGBOW))
                                .add(NpcGearItemData.create(Items.BOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        KNIGHT = new NpcData(NpcRegistry.DALE_KNIGHT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ELITE_ARCHER = new NpcData(NpcRegistry.DALE_ELITE_ARCHER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_SOLDIER, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BROWN_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_BLACK_FUR))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_HELMET_TAN_FUR))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.DALISH_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGBOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
        VETERAN = new NpcData(NpcRegistry.DALE_VETERAN.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_LORD, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_BURGONET))
                                .add(NpcGearItemData.create().withWeight(1))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(BackAttachmentsME.BARDING_SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_SCALE_HAUBERK).withCape(BackAttachmentsME.BARDING_SURCOAT))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.DALISH_MAIL_COAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_LONGSWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SERGEANT = new NpcData(NpcRegistry.DALE_SERGEANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.DALE_LORD, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_HELMET).withWeight(3))
                                .add(NpcGearItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE).withWeight(3).withCape(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE).withCape(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.BARDING_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.BARDING_PLATED_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
