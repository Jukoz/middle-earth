package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.npcs.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.HashMap;
import java.util.List;

public class WildGoblinNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.WILD_GOBLINS;
    
    private static final int DARK_BROWN_GOBLIN = 0x4a3c34;

    public final static NpcData GATHERER;
    public final static NpcData WARRIOR;
    public final static NpcData SCOUT;
    public final static NpcData RIDER;
    public final static NpcData BRUTE;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_GATHERER, GATHERER),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_SCOUT, SCOUT),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_RIDER, RIDER),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_WARRIOR, WARRIOR),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_BRUTE, BRUTE)
        );
    }

    static {
        GATHERER = new NpcData(NpcRegistry.WILD_GOBLIN_GATHERER.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WEAK, List.of(
            WeightedGearData.create()
                .add(EquipmentSlot.HEAD, GearSlotPool.create()
                    .add(WeightedItemData.create(Items.AIR).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColor(DARK_BROWN_GOBLIN).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(2))
                )
                .add(EquipmentSlot.CHEST, GearSlotPool.create()
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                    .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.CAPE, DARK_BROWN_GOBLIN))

                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_CAPE).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.LEGS, GearSlotPool.create()
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS).withColor(DARK_BROWN_GOBLIN)))
                .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                    .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                    .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                    .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                    .add(WeightedItemData.create(ToolItemsME.CRUDE_PICKAXE))
                    .add(WeightedItemData.create(ToolItemsME.CRUDE_HOE))
                    .add(WeightedItemData.create(ToolItemsME.CRUDE_SHOVEL))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT);

        SCOUT = new NpcData(NpcRegistry.WILD_GOBLIN_SCOUT.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WEAK, List.of(
            WeightedGearData.create()
                .add(EquipmentSlot.HEAD, GearSlotPool.create()
                    .add(WeightedItemData.create(Items.AIR).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                )
                .add(EquipmentSlot.CHEST, GearSlotPool.create()
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.LEGS, GearSlotPool.create()
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS)))
                .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                    .add(WeightedItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(4))
                    .add(WeightedItemData.create(WeaponItemsME.GUNDABAD_BOW))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, CombatArchetypePool.DEFAULT);

        RIDER = new NpcData(NpcRegistry.WILD_GOBLIN_RIDER.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.DOL_GULDUR_JAILER_COLLAR))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(ModDecorativeBlocks.CRUDE_CHAIN.asItem()).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR.asItem()))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.STICK).withWeight(4))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        WARRIOR = new NpcData(NpcRegistry.WILD_GOBLIN_WARRIOR.getValue(), RaceRegistry.ORC, FACTION, TexturePresetsRegistry.WILD_GOBLIN_WARRIOR, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                                .add(WeightedItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                                .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_SANDALS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.CRUDE_FALCHION))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.ORC_KNIFE))
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        BRUTE = new NpcData(NpcRegistry.WILD_GOBLIN_BRUTE.getValue(), RaceRegistry.URUK, FACTION, TexturePresetsRegistry.WILD_GOBLIN_BRUTE, List.of(
            WeightedGearData.create()
                .add(EquipmentSlot.HEAD, GearSlotPool.create()
                    .add(WeightedItemData.create(Items.AIR).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(3))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(2))
                    .add(WeightedItemData.create(EquipmentItemsME.COOKING_POT_HELMET))
                )
                .add(EquipmentSlot.LEGS, GearSlotPool.create()
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(WeightedItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, GearSlotPool.create(
                        WeightedItemData.create(EquipmentItemsME.ORCISH_SANDALS))
                )
                .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                    .add(WeightedItemData.create(WeaponItemsME.BONE_AXE).withWeight(2))
                    .add(WeightedItemData.create(WeaponItemsME.BONE_SCIMITAR))
                    .add(WeightedItemData.create(WeaponItemsME.BONE_CLEAVER))
                )
        ), new HashMap<>(){{
            put(EntityCategories.SHARED, new AttributePool().addElements(List.of(
                    AttributePoolElement.create(EntityAttributes.SCALE, 0.83, 0.95),
                    AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.25, 0.30),
                    AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 28),
                    AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 1.05, 1.12),
                    AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 2).withModifier(MiddleEarth.of("brute_attack_damage_buff"), 1.35)
                )
            ));
        }} , CombatArchetypePool.DEFAULT);
    }
}
