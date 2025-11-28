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
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.NpcTextureDatasME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;

import java.util.List;

public class WildGoblinNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.WILD_GOBLINS;

    private static final int DARK_BROWN_GOBLIN = 0x4a3c34;

    public final static NpcData GATHERER;
    public final static NpcData WARRIOR;
    public final static NpcData SCOUT;
    public final static NpcData BRUTE;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_GATHERER, GATHERER),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_WARRIOR, WARRIOR),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_SCOUT, SCOUT),
            new NpcRegistry.RegisterableNpcData(NpcRegistry.WILD_GOBLIN_BRUTE, BRUTE)
        );
    }

    static {
        GATHERER = new NpcData(NpcRegistry.WILD_GOBLIN_GATHERER.getValue(), RaceRegistry.ORC, FACTION, NpcTextureDatasME.WILD_GOBLIN_GOBLIN, List.of(
            NpcGearData.create()
                .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColor(DARK_BROWN_GOBLIN).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(2))
                )
                .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_STRAP).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.CAPE, DARK_BROWN_GOBLIN))

                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_CAPE).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEG_BRACER).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_SANDALS).withColor(DARK_BROWN_GOBLIN)))
                .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                    .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                    .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                    .add(NpcGearItemData.create(ToolItemsME.CRUDE_PICKAXE))
                    .add(NpcGearItemData.create(ToolItemsME.CRUDE_HOE))
                    .add(NpcGearItemData.create(ToolItemsME.CRUDE_SHOVEL))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        WARRIOR = new NpcData(NpcRegistry.WILD_GOBLIN_WARRIOR.getValue(), RaceRegistry.ORC, FACTION, NpcTextureDatasME.WILD_GOBLIN_GOBLIN, List.of(
            NpcGearData.create()
                .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                )
                .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.GUNDABAD_BONE_PAULDRON).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_LONG_CAPE))
                )
                .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_SANDALS)))
                .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(WeaponItemsME.CRUDE_SPEAR).withWeight(4))
                    .add(NpcGearItemData.create(ToolItemsME.CRUDE_AXE).withWeight(3))
                    .add(NpcGearItemData.create(WeaponItemsME.CRUDE_DAGGER).withWeight(2))
                    .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE).withWeight(2))
                    .add(NpcGearItemData.create(WeaponItemsME.CRUDE_FALCHION))
                )
                .add(EquipmentSlot.OFFHAND, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(WeaponItemsME.MORDOR_WOODEN_SHIELD).withWeight(2))
                    .add(NpcGearItemData.create(WeaponItemsME.ORC_KNIFE))
                    .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        SCOUT = new NpcData(NpcRegistry.WILD_GOBLIN_SCOUT.getValue(), RaceRegistry.ORC, FACTION, NpcTextureDatasME.WILD_GOBLIN_GOBLIN, List.of(
            NpcGearData.create()
                .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                )
                .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS)))
                .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(4))
                    .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_BOW))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);

        BRUTE = new NpcData(NpcRegistry.WILD_GOBLIN_BRUTE.getValue(), RaceRegistry.URUK, FACTION, NpcTextureDatasME.WILD_GOBLIN_HOBGOBLIN, List.of(
            NpcGearData.create()
                .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(Items.AIR).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_HELMET).withWeight(6))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_BRACED_HELMET).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF).withWeight(3))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_MAIL_COIF).withWeight(2))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_MORDOR_HELMET))
                )
                .add(EquipmentSlot.CHEST, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))

                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_CAPE))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST).withColor(DARK_BROWN_GOBLIN).withCape(BackAttachmentsME.ORCISH_SHOULDERS, DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.LEGS, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                    .add(NpcGearItemData.create(EquipmentItemsME.ORCISH_LEATHER_SKIRT).withColor(DARK_BROWN_GOBLIN))
                )
                .add(EquipmentSlot.FEET, NpcGearSlotData.create(NpcGearItemData.create(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS)))
                .add(EquipmentSlot.MAINHAND, NpcGearSlotData.create()
                    .add(NpcGearItemData.create(WeaponItemsME.ORCISH_BOW).withWeight(4))
                    .add(NpcGearItemData.create(WeaponItemsME.GUNDABAD_BOW))
                )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES);
    }
}
