package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;

import java.util.List;

public class WoodlandRealmNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.WOODLAND_REALM;

    public final static NpcData ARTISAN; // Citizen
    public final static NpcData HUNTER; // Very light, focusing entities (spiders and deer rather than orcs)
    public final static NpcData SENTINEL; // Very light armor, but stealth (stay far from the enemies or unknown, 30-40 blocks)
    public final static NpcData RANGER; // Medium armor - archers
    public final static NpcData WARRIOR; // Medium armor
    public final static NpcData LANCER; // Medium armor - have spears and do charges - riding great horns
    public final static NpcData NIGHTSHADE; // Nightshade armor - Hybrid
    public final static NpcData ELVEN_KINGS_GUARD; // Heavily armored - spears and shields
    public final static NpcData COMMANDER; // Heavily armored, can ride a great horn (depend on context)
    public final static NpcData WARDEN_OF_THE_GLADE; // Heavily armored, elite, melee

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_ARTISAN, ARTISAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_HUNTER, HUNTER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_SENTINEL, SENTINEL),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_RANGER, RANGER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_LANCER, LANCER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_NIGHTSHADE, NIGHTSHADE),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_ELVEN_KINGS_GUARD, ELVEN_KINGS_GUARD),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_COMMANDER, COMMANDER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE, WARDEN_OF_THE_GLADE)
        );
    }

    static {
        ARTISAN = new NpcData(NpcRegistry.WOODLAND_REALM_ARTISAN.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withColors(List.of(0x4a5110, 0x0b2d11, 0x315534)))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT).withColors(List.of(0x4a5110, 0x0b2d11, 0x315534)))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                                .add(WeightedItemData.create(ToolItemsME.ELVEN_SMITHING_HAMMER))
                                .add(WeightedItemData.create(ToolItemsME.NOBLE_SMITHING_HAMMER))
                                .add(WeightedItemData.create(DecorativeItemsME.WATERING_CAN))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        HUNTER = new NpcData(NpcRegistry.WOODLAND_REALM_HUNTER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT)
                                        .withColors(List.of(0x0b2d11, 0x315534))
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SENTINEL = new NpcData(NpcRegistry.WOODLAND_REALM_SENTINEL.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF_SENTINEL, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                            .add(WeightedItemData.create(EquipmentItemsME.HOOD).withColor(0x20351a))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT)
                                        .withColors(List.of(0x0b2d11))
                                        .withCape(BackAttachmentsME.MIRK_LEAF_CAPE))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        RANGER = new NpcData(NpcRegistry.WOODLAND_REALM_RANGER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create().withWeight(3)
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                            .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_BRONZE_TRIMMED_RANGER_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT)
                                        .withColor(0x424733)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_BRONZED_ARMING_COAT)
                                        .withColor(0x424733)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_ARMING_SKIRT)
                                        .withColor(0x424733))
                                .add(WeightedItemData.create(EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_BOW))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(
                                WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SILVER_TRIMMED_RANGER_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_SILVER_ARMING_COAT)
                                        .withColor(0x3a261b)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_ARMING_SKIRT).withColor(0x3a261b))
                                .add(WeightedItemData.create(EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        WARRIOR = new NpcData(NpcRegistry.WOODLAND_REALM_WARRIOR.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        LANCER = new NpcData(NpcRegistry.WOODLAND_REALM_LANCER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_CAVALRY_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK)
                                        .withCape(BackAttachmentsME.WOODLAND_REALM_SOLDIER_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        NIGHTSHADE = new NpcData(NpcRegistry.WOODLAND_REALM_NIGHTSHADE.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF_SENTINEL, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        ELVEN_KINGS_GUARD = new NpcData(NpcRegistry.WOODLAND_REALM_ELVEN_KINGS_GUARD.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_ROYAL_GUARD_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK)
                                        .withCape(BackAttachmentsME.WOODLAND_REALM_ROYAL_GUARD_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        COMMANDER = new NpcData(NpcRegistry.WOODLAND_REALM_COMMANDER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_COMMANDER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        WARDEN_OF_THE_GLADE = new NpcData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(EquipmentItemsME.WARDEN_OF_THE_GLADE_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARDEN_OF_THE_GLADE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARDEN_OF_THE_GLADE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WARDEN_OF_THE_GLADE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
