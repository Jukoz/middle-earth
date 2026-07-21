package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.npctypes.NpcLoot;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.GearSlotPool;
import net.sevenstars.middleearth.resources.datas.npc_types.data.MountData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedItemData;

import java.util.HashMap;
import java.util.List;

public class WoodlandRealmNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.WOODLAND_REALM;

    public static final Identifier TOTAL_DAMAGE_MODIFIER = MiddleEarth.of("total_damage");

    public final static NpcType ARTISAN; // Citizen / Artisans
    public final static NpcType HUNTER; // Very lightly armored, focusing entities (spiders and deer (cooldown) rather than orcs)
    public final static NpcType SENTINEL; // Very light armor, but stealth (stay far from the enemies or unknown, 30-40 blocks)
    public final static NpcType RANGER; // Medium armor - ranged
    public final static NpcType WARRIOR; // Medium armor
    public final static NpcType LANCER; // Medium armor - have spears and do charges - riding great horns
    public final static NpcType NIGHTSHADE; // Nightshade armor - Hybrid
    public final static NpcType ELVEN_KINGS_GUARD; // Heavily armored - spears and shields
    public final static NpcType COMMANDER; // Heavily armored, can ride a great horn (depend on context)
    public final static NpcType WARDEN_OF_THE_GLADE; // Heavily armored, elite, melee

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
        ARTISAN = new NpcType(NpcRegistry.WOODLAND_REALM_ARTISAN.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withColors(List.of(0x4a5110, 0x0b2d11, 0x315534)))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT).withColors(List.of(0x4a5110, 0x0b2d11, 0x315534)))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(7))
                                .add(WeightedItemData.create(ToolItemsME.BRONZE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_AXE))
                                .add(WeightedItemData.create(ToolItemsME.ELVEN_SMITHING_HAMMER))
                                .add(WeightedItemData.create(ToolItemsME.NOBLE_SMITHING_HAMMER))
                                .add(WeightedItemData.create(DecorativeItemsME.WATERING_CAN))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT, NpcLoot.FROM_1_TO_4);

        HUNTER = new NpcType(NpcRegistry.WOODLAND_REALM_HUNTER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.WOODLAND_REALMS_RANGER, NpcLoot.FROM_3_TO_7);

        SENTINEL = new NpcType(NpcRegistry.WOODLAND_REALM_SENTINEL.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_SENTINEL, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                            .add(WeightedItemData.create(EquipmentItemsME.HOOD).withColor(0x20351a))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT)
                                        .withColor(0x0b2d11)
                                        .withCape(BackAttachmentsME.MIRK_LEAF_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(0x0b2d11))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.HOOD).withColor(0x45311d))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT)
                                        .withColors(List.of(0x45311d))
                                        .withCape(BackAttachmentsME.MIRK_BARK_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(5))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.WOODLAND_REALMS_RANGER, NpcLoot.FROM_3_TO_7);

        RANGER = new NpcType(NpcRegistry.WOODLAND_REALM_RANGER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create().withWeight(3)
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.BRONZED_ELVEN_MAIL_COIF)
                                        .withHood(HelmetAttachmentsME.HOOD, 0x424733)
                                )
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_BRONZE_TRIMMED_RANGER_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT)
                                        .withColor(0x424733)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_BRONZED_ARMING_COAT)
                                        .withColor(0x424733)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                                .add(WeightedItemData.create(EquipmentItemsME.BRONZED_ELVEN_GORGET_MAIL_HAUBERK)
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
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_BOW))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SILVER_TRIMMED_RANGER_HELMET).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_LEATHER_SILVER_ARMING_COAT)
                                        .withColor(0x3a261b)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_SILVER_GORGET_MAIL_HAUBERK)
                                        .withColor(0x424733)
                                        .withCape(BackAttachmentsME.CAPE, 0x3a261b))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_ARMING_SKIRT).withColor(0x3a261b))
                                .add(WeightedItemData.create(EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ELVEN_BOOTS))
                                .add(WeightedItemData.create(EquipmentItemsME.TRAVELLING_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_LONGBOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.WOODLAND_REALMS_RANGER, NpcLoot.FROM_3_TO_7);

        WARRIOR = new NpcType(NpcRegistry.WOODLAND_REALM_WARRIOR.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_HELMET).withWeight(2))
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
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT, NpcLoot.FROM_6_TO_10);

        LANCER = new NpcType(NpcRegistry.WOODLAND_REALM_LANCER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_CAVALRY_HELMET).withWeight(3))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK)
                                        .withCape(BackAttachmentsME.WOODLAND_REALM_SOLDIER_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_SOLDIER_BOOTS))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, new MountData(EntitiesME.GREAT_HORN).withArmor(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR.getDefaultStack()), NpcLoot.FROM_6_TO_10);

        NIGHTSHADE = new NpcType(NpcRegistry.WOODLAND_REALM_NIGHTSHADE.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_NIGHTSHADE, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_HELMET).withWeight(3))
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
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_HELMET).withWeight(3))
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT, NpcLoot.FROM_10_TO_16);

        ELVEN_KINGS_GUARD = new NpcType(NpcRegistry.WOODLAND_REALM_ELVEN_KINGS_GUARD.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_ELF, List.of(
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT, NpcLoot.FROM_10_TO_16);

        COMMANDER = new NpcType(NpcRegistry.WOODLAND_REALM_COMMANDER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_COMMANDER, List.of(
                WeightedGearData.create().withWeight(2)
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_HELMET).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_SPEAR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_AXE))

                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_HELMET).withWeight(2))
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
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_HELMET).withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.WOODLAND_REALM_COMMANDER_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT, NpcLoot.FROM_13_TO_20);

        WARDEN_OF_THE_GLADE = new NpcType(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE, List.of(
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
        ), new HashMap<>(){{
            put(EntityCategories.SHARED, new AttributePool().addElements(List.of(
                    AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.25, 0.30),
                    AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 28),
                    AttributePoolElement.create(EntityAttributes.ARMOR, 10, 20),
                    AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1, 2)
                            .withModifier(TOTAL_DAMAGE_MODIFIER, 1.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) // Does 1.5x damage, with 1 -> 2 base damage (strong)
            )));
        }}, CombatArchetypePool.DEFAULT, NpcLoot.FROM_15_TO_25);
    }
}
