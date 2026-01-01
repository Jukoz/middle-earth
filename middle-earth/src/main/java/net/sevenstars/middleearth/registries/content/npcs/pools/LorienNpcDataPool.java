package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.resources.NpcTextureDatasME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class LorienNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.LOTHLORIEN;

    private final static int LIGHT_BLUE = 0x3a4250;
    private final static int DARK_BLUE = 0x252b3a;

    public final static NpcData SENTINEL;
    public final static NpcData RANGER;
    public final static NpcData WARRIOR;
    public final static NpcData KNIGHT;
    public final static NpcData GUARD;
    public final static NpcData LORD;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.LOTHLORIEN_SENTINEL, SENTINEL),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.LOTHLORIEN_RANGER, RANGER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.LOTHLORIEN_WARRIOR, WARRIOR),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.LOTHLORIEN_KNIGHT, KNIGHT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.LOTHLORIEN_GUARD, GUARD),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.LOTHLORIEN_LORD, LORD)
        );
    }

    static {
        SENTINEL = new NpcData(NpcRegistry.LOTHLORIEN_SENTINEL.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.LOTHLORIEN_ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_COAT).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.SHOES)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD)))
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        RANGER = new NpcData(NpcRegistry.LOTHLORIEN_RANGER.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.LOTHLORIEN_ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD, false))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_COAT).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_COAT).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_BOW))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LONGBOW))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(Items.AIR))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        WARRIOR = new NpcData(NpcRegistry.LOTHLORIEN_WARRIOR.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.LOTHLORIEN_ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_LEATHER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_DIADEM).withHood(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MAIL_HAUBERK).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_ARMING_SKIRT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ELVEN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_GLAIVE))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_MALLORN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        KNIGHT = new NpcData(NpcRegistry.LOTHLORIEN_KNIGHT.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.LOTHLORIEN_ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_HELMET))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.LORIEN_SCALE_COAT)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_GLAIVE))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SPEAR))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_MALLORN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        GUARD = new NpcData(NpcRegistry.LOTHLORIEN_GUARD.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.LOTHLORIEN_ELF, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_HELMET).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_CHESTPLATE).withWeight(4))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_CHESTPLATE).withCape(BackAttachmentsME.GALADHRIM_CAPE).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_AXE).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SWORD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SPEAR).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_GLAIVE))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GALADHRIM_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_LAURELS_SHIELD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_MALLORN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        LORD = new NpcData(NpcRegistry.LOTHLORIEN_LORD.getValue(), RaceRegistry.ELF, FACTION, TexturePresetsRegistry.LOTHLORIEN_LORD, List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE).withWeight(3))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE).withCape(BackAttachmentsME.GALADHRIM_LORD_SURCOAT))
                                .add(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE).withCape(BackAttachmentsME.GALADHRIM_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.GALADHRIM_LORD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_AXE))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SWORD))
                                .add(NpcGearItemData.create(WeaponItemsME.LORIEN_NOBLE_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(WeaponItemsME.GALADHRIM_LORD_SHIELD).withWeight(3))
                                .add(NpcGearItemData.create(WeaponItemsME.GALADHRIM_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
