package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.NpcTextureDatasME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class ShireNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.SHIRE;
    private final static String FACTION_BASE = FACTION.getValue().getPath() + ".%s";

    private final static int DARK_BEIGE = 0xa89371;
    private final static int DARK_GREEN = 0x336339;
    private final static int BROWN = 0x59341e;
    private final static int DARK = 0x342c27;

    public final static NpcData SHIRE_MILITIA;
    public final static NpcData SHIRE_SHIRRIFF;

    public static List<NpcData> fetchAll() {
        return List.of(
                SHIRE_MILITIA,
                SHIRE_SHIRRIFF
        );
    }

    static {
        SHIRE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("militia")), RaceRegistry.HOBBIT, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
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
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS)))
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

    SHIRE_SHIRRIFF = new NpcData(Identifier.of(MiddleEarth.MOD_ID, FACTION_BASE.formatted("shirriff")), RaceRegistry.HOBBIT, FACTION, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.SHIRRIFF_HAT)))
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.SURCOAT).withColor(DARK_BEIGE))
                                .add(NpcGearItemData.create(EquipmentItemsME.SURCOAT).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS)))
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
