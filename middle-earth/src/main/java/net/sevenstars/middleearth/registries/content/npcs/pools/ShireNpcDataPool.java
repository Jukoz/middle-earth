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
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class ShireNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.SHIRE;

    private final static int DARK_BEIGE = 0xa89371;
    private final static int DARK_GREEN = 0x336339;
    private final static int BROWN = 0x59341e;
    private final static int DARK = 0x342c27;

    public final static NpcData PEASANT;
    public final static NpcData MILITIA;
    public final static NpcData SHIRRIFF;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.SHIRE_SHIRRIFF, SHIRRIFF)
        );
    }

    static {
        PEASANT = new NpcData(NpcRegistry.SHIRE_PEASANT.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_PEASANT, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(NpcGearItemData.create().withWeight(10))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        MILITIA = new NpcData(NpcRegistry.SHIRE_MILITIA.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_MILITIA, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withColor(BROWN).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(EquipmentItemsME.GAMBESON).withColor(BROWN))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK).withCape(BackAttachmentsME.CLOAK, DARK))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BEIGE).withCape(BackAttachmentsME.CLOAK, DARK))
                                .add(NpcGearItemData.create(EquipmentItemsME.ARMING_COAT).withColor(BROWN).withCape(BackAttachmentsME.CLOAK, DARK))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.BRONZE_SWORD))
                                .add(NpcGearItemData.create(Items.IRON_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.STEEL_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.ROUND_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SHIRRIFF = new NpcData(NpcRegistry.SHIRE_SHIRRIFF.getValue(), RaceRegistry.HOBBIT, FACTION, TexturePresetsRegistry.SHIRE_SHIRRIFF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.SHIRRIFF_HAT)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.SURCOAT).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(EquipmentItemsME.SURCOAT).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.STEEL_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                                .add(NpcGearItemData.create(ToolItemsME.PIPE).withWeight(3))
                                .add(NpcGearItemData.create(ToolItemsME.CLAY_PIPE).withWeight(2))
                                .add(NpcGearItemData.create(ToolItemsME.BRIMMINGBEND_PIPE))
                                .add(NpcGearItemData.create(Items.BOOK))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
