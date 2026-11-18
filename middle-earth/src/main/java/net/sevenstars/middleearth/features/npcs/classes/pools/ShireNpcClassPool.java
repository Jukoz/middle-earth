package net.sevenstars.middleearth.features.npcs.classes.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.features.npcs.classes.NpcLoot;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.features.npcs.classes.NpcRoutines;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.data.GearSlotPool;
import net.sevenstars.middleearth.features.npcs.classes.data.WeightedGearData;
import net.sevenstars.middleearth.features.npcs.classes.data.WeightedItemData;

import java.util.List;

public class ShireNpcClassPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.SHIRE;

    private final static int DARK_BEIGE = 0xa89371;
    private final static int DARK_GREEN = 0x336339;
    private final static int BROWN = 0x59341e;
    private final static int DARK = 0x342c27;

    public final static NpcClass PEASANT;
    public final static NpcClass MILITIA;
    public final static NpcClass BOUNDER;
    public final static NpcClass SHIRRIFF;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_BOUNDER, BOUNDER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_SHIRRIFF, SHIRRIFF)
        );
    }

    static {
        PEASANT = new NpcClass(NpcRegistry.SHIRE_PEASANT.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.CHAPERON).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(WeightedItemData.create().withWeight(12))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_1_TO_4);

        MILITIA = new NpcClass(NpcRegistry.SHIRE_MILITIA.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_MILITIA, List.of(
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_6_TO_10);

        BOUNDER = new NpcClass(NpcRegistry.SHIRE_BOUNDER.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_MILITIA, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET).withColor(DARK_GREEN).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET).withColor(BROWN).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.CHAPERON))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_GREEN).withCape(BackAttachmentsME.CLOAK, BROWN))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(BROWN).withCape(BackAttachmentsME.CLOAK, DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BEIGE).withCape(BackAttachmentsME.CLOAK, DARK_GREEN))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.BOW).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SWORD))
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_6_TO_10);

        SHIRRIFF = new NpcClass(NpcRegistry.SHIRE_SHIRRIFF.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_SHIRRIFF, List.of(
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
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES, NpcLoot.FROM_13_TO_20);

        PEASANT.withRoutine(NpcRoutines.HOBBIT);
        MILITIA.withRoutine(NpcRoutines.DAY_WATCH);
        BOUNDER.withRoutine(NpcRoutines.BOUNDS_WATCH);
        SHIRRIFF.withRoutine(NpcRoutines.DAY_WATCH);
        PEASANT.asCivilian();
    }
}
