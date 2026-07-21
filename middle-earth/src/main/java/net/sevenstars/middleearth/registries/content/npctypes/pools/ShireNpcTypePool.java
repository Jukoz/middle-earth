package net.sevenstars.middleearth.registries.content.npctypes.pools;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.GearSlotPool;
import net.sevenstars.middleearth.resources.datas.npc_types.data.MountData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedItemData;

import java.util.List;

public class ShireNpcTypePool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.SHIRE;

    private final static int DARK_BEIGE = 0xa89371;
    private final static int DARK_GREEN = 0x336339;
    private final static int BROWN = 0x59341e;
    private final static int DARK = 0x342c27;

    public final static NpcType PEASANT;
    public final static NpcType MILITIA;
    public final static NpcType SHIRRIFF;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_SHIRRIFF, SHIRRIFF)
        );
    }

    static {
        PEASANT = new NpcType(NpcRegistry.SHIRE_PEASANT.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(WeightedItemData.create().withWeight(10))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        MILITIA = new NpcType(NpcRegistry.SHIRE_MILITIA.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_MILITIA, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET).withColor(BROWN).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK).withCape(BackAttachmentsME.CLOAK, DARK))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BEIGE).withCape(BackAttachmentsME.CLOAK, DARK))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(BROWN).withCape(BackAttachmentsME.CLOAK, DARK))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SWORD))
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.STEEL_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SHIRRIFF = new NpcType(NpcRegistry.SHIRE_SHIRRIFF.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_SHIRRIFF, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.SHIRRIFF_HAT)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.SURCOAT).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.SURCOAT).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.STEEL_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR).withWeight(3))
                                .add(WeightedItemData.create(ToolItemsME.PIPE).withWeight(3))
                                .add(WeightedItemData.create(ToolItemsME.CLAY_PIPE).withWeight(2))
                                .add(WeightedItemData.create(ToolItemsME.BRIMMINGBEND_PIPE))
                                .add(WeightedItemData.create(Items.BOOK))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT, new MountData(EntityType.PIG));
    }
}
