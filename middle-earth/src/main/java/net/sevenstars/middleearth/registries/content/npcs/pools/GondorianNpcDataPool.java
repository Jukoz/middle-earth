package net.sevenstars.middleearth.registries.content.npcs.pools;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.registries.content.npcs.CombatArchetypePool;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.GearSlotPool;

import java.util.List;

public class GondorianNpcDataPool {
    private final static RegistryKey<Faction> FACTION = FactionRegistry.GONDOR;

    private final static int LIGHT_BLUE = 0x2b3965;
    private final static int DARK_BLUE = 0x182038;
    private final static int LIGHT_ORANGE = 0xc67640;
    private final static int DARK_ORANGE = 0x82492c;
    private final static int LIGHT_BEIGE = 0xe8cb9b;
    private final static int DARK_BEIGE = 0xa89371;
    private final static int LIGHT_GRAY = 0x68697d;
    private final static int DARK_GRAY = 0x555666;
    private final static int LIGHT_GREEN = 0x435353;
    private final static int DARK_GREEN = 0x262f2f;

    public final static NpcData PEASANT;
    public final static NpcData MILITIA;
    public final static NpcData SOLDIER;
    public final static NpcData KNIGHT;
    public final static NpcData VETERAN;
    public final static NpcData FOUNTAIN_GUARD;
    public final static NpcData CITADEL_GUARD;
    public final static NpcData KING_GUARD;
    public final static NpcData LEADER;

    public static List<NpcRegistry.RegisterableNpcData> fetchAll() {
        return List.of(
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_PEASANT, PEASANT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_MILITIA, MILITIA),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_SOLDIER, SOLDIER),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_KNIGHT, KNIGHT),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_VETERAN, VETERAN),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_FOUNTAIN_GUARD, FOUNTAIN_GUARD),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_CITADEL_GUARD, CITADEL_GUARD),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_KING_GUARD, KING_GUARD),
                new NpcRegistry.RegisterableNpcData(NpcRegistry.GONDOR_LEADER, LEADER)
        );
    }

    static {
        PEASANT = new NpcData(NpcRegistry.GONDOR_PEASANT.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GONDOR_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.STRAW_HAT))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(WeightedItemData.create(EquipmentItemsME.BYCOCKET))
                                .add(WeightedItemData.create().withWeight(7))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_DAGGER))
                                .add(WeightedItemData.create(Items.IRON_HOE))
                                .add(WeightedItemData.create(ToolItemsME.SMITHING_HAMMER))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        MILITIA = new NpcData(NpcRegistry.GONDOR_MILITIA.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GONDOR_PEASANT, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(DARK_BEIGE))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BLUE).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_ORANGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_ORANGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(LIGHT_BEIGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_COAT).withColor(DARK_BEIGE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_BLUE))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_ORANGE))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_BEIGE))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_BOOTS))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SPEAR).withWeight(4))
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SWORD).withWeight(3))
                                .add(WeightedItemData.create(ToolItemsME.BRONZE_AXE).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.IRON_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                                .add(WeightedItemData.create(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.KITE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(WeightedItemData.create().withWeight(5))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_COWL))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_COWL).withColor(LIGHT_GRAY))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_COWL).withColor(DARK_GRAY))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_COWL).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_COWL).withColor(DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(LIGHT_GRAY))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(DARK_GRAY))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON_CAP).withColor(DARK_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.OPEN_FACE_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(LIGHT_GRAY))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_GRAY))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(LIGHT_GREEN))
                                .add(WeightedItemData.create(EquipmentItemsME.GAMBESON).withColor(DARK_GREEN))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_GRAY).withWeight(4))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_GREEN).withWeight(4))
                                .add(WeightedItemData.create().withWeight(7))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_BOOTS))
                                .add(WeightedItemData.create().withWeight(2))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(WeaponItemsME.BRONZE_SWORD))
                                .add(WeightedItemData.create(ToolItemsME.BRONZE_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.IRON_SPEAR).withWeight(2))
                                .add(WeightedItemData.create(Items.IRON_SWORD))
                                .add(WeightedItemData.create(Items.IRON_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.KITE_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.HEATER_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.ROUND_SHIELD))
                                .add(WeightedItemData.create().withWeight(5))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        SOLDIER = new NpcData(NpcRegistry.GONDOR_SOLDIER.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GONDOR_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CABASSET_HELMET))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_SOLDIER_HELMET).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.KETTLE_HAT))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_TABBARD))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_LEATHER_CUIRASS))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_LEATHER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_MAIL_COAT))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_GRAY).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.ARMING_SKIRT).withColor(DARK_GREEN).withWeight(2))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_AXE))
                        )
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create())
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        KNIGHT = new NpcData(NpcRegistry.GONDOR_KNIGHT.getValue(), RaceRegistry.HUMAN, FACTION,  TexturePresetsRegistry.GONDOR_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_MAIL_COAT).withWeight(2))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        )
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_AXE))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SHIELD))
                        ),
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SPEAR))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_TOWER_SHIELD))
                                .add(WeightedItemData.create().withWeight(3))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        VETERAN = new NpcData(NpcRegistry.GONDOR_VETERAN.getValue(), RaceRegistry.HUMAN, FACTION,  TexturePresetsRegistry.GONDOR_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_HELMET).withWeight(10))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET))
                        )
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_HERO_SHIELD))
                                .add(WeightedItemData.create().withWeight(2))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        CITADEL_GUARD = new NpcData(NpcRegistry.GONDOR_CITADEL_GUARD.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GONDOR_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_KNIGHT_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        FOUNTAIN_GUARD = new NpcData(NpcRegistry.GONDOR_FOUNTAIN_GUARD.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GONDOR_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create(WeightedItemData.create(WeaponItemsME.GONDORIAN_FOUNTAIN_GUARD_SPEAR)))
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        KING_GUARD = new NpcData(NpcRegistry.GONDOR_KING_GUARD.getValue(), RaceRegistry.HUMAN, FACTION, TexturePresetsRegistry.GONDOR_SOLDIER, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_KINGS_GUARD_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_KINGS_GUARD_CHESTKPLATE)))
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_KINGS_GUARD_LEGGINGS)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_SPEAR).withWeight(3))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_LONGSWORD))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).withWeight(8))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_TOWER_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);

        LEADER = new NpcData(NpcRegistry.GONDOR_LEADER.getValue(), RaceRegistry.HUMAN, FACTION,  TexturePresetsRegistry.GONDOR_LORD, List.of(
                WeightedGearData.create()
                        .add(EquipmentSlot.HEAD, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET)))
                        .add(EquipmentSlot.CHEST, GearSlotPool.create()
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE).withCape(BackAttachmentsME.GONDORIAN_HERO_CAPE).withWeight(3))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_KINGS_GUARD_CHESTKPLATE).withCape(BackAttachmentsME.GONDORIAN_HERO_CAPE))
                                .add(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CHESTPLATE).withCape(BackAttachmentsME.GONDORIAN_HERO_CAPE))
                        )
                        .add(EquipmentSlot.LEGS, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CHESTPLATE)))
                        .add(EquipmentSlot.FEET, GearSlotPool.create(WeightedItemData.create(EquipmentItemsME.GONDORIAN_KINGS_GUARD_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_SWORD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_NOBLE_AXE))
                        )
                        .add(EquipmentSlot.OFFHAND, GearSlotPool.create()
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_HERO_SHIELD))
                                .add(WeightedItemData.create(WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD))
                        )
        ), NpcRegistry.COMMON_NPC_ATTRIBUTES , CombatArchetypePool.DEFAULT);
    }
}