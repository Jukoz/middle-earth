package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.jukoz.me.resources.datas.npcs.pools.GondorianNpcDataPool;
import net.jukoz.me.resources.datas.npcs.pools.RohirricNpcDataPool;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MiddleEarthNpcs {
    public final static String PATH = "npcs";
    public static final RegistryKey<Registry<NpcData>> NPC_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    // [GENERIC]
    public final static NpcData HUMAN_CIVILIAN;
    public final static NpcData DWARF_CIVILIAN;
    public final static NpcData ELF_CIVILIAN;
    public final static NpcData HOBBIT_CIVILIAN;
    public final static NpcData ORC_CIVILIAN;
    public final static NpcData URUK_CIVILIAN;
    // [DALE]
    public final static NpcData DALE_MILITIA;
    // [LONGBEARDS]
    // [EREBOR]
    public final static NpcData EREBOR_MILITIA;
    // [LOTHLORIEN]
    public final static NpcData LOTHLORIEN_MILITIA;
    // [MORDOR]
    public final static NpcData MORDOR_ORC_MILITIA;
    public final static NpcData BLACK_URUK_SOLDIER;
    // [MISTY MOUNTAINS GOBLINS]
    public final static NpcData MISTY_GOBLIN_MILITIA;
    public final static NpcData MISTY_HOBGOBLIN_SOLDIER;
    // [ISENGARD]
    public final static NpcData ISENGARD_ORC_MILITIA;
    public final static NpcData URUK_HAI_SOLDIER;
    // [SHIRE]
    public final static NpcData HOBBIT_PEASANT;
    public static void register(){
        LoggerUtil.logDebugMsg("Registering Dynamic Npcs for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(NPC_KEY, NpcData.CODEC);
    }

    public static void bootstrap(Registerable<NpcData> context) {
        RegistryEntryLookup<NpcData> npcRegistryEntryLookup = context.getRegistryLookup(NPC_KEY);
        // [GENERIC]
        register(context, npcRegistryEntryLookup, HUMAN_CIVILIAN);
        register(context, npcRegistryEntryLookup, DWARF_CIVILIAN);
        register(context, npcRegistryEntryLookup, ELF_CIVILIAN);
        register(context, npcRegistryEntryLookup, HOBBIT_CIVILIAN);
        register(context, npcRegistryEntryLookup, ORC_CIVILIAN);
        register(context, npcRegistryEntryLookup, URUK_CIVILIAN);

        // [GONDOR]
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_MILITIA);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_SOLDIER);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_KNIGHT);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARDS);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_CITADEL_GUARDS);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_KING_GUARDS);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_VETERAN);
        register(context, npcRegistryEntryLookup, GondorianNpcDataPool.GONDOR_LEADER);
        // [ROHAN]
        register(context, npcRegistryEntryLookup, RohirricNpcDataPool.ROHAN_MILITIA);
        register(context, npcRegistryEntryLookup, RohirricNpcDataPool.ROHAN_SOLDIER);
        register(context, npcRegistryEntryLookup, RohirricNpcDataPool.ROHAN_KNIGHT);
        register(context, npcRegistryEntryLookup, RohirricNpcDataPool.ROHAN_ROYAL_GUARD);
        register(context, npcRegistryEntryLookup, RohirricNpcDataPool.ROHAN_EORLING_MARSHAL);
        register(context, npcRegistryEntryLookup, RohirricNpcDataPool.ROHAN_HORSE_LORD);

        // [DALE]
        register(context, npcRegistryEntryLookup, DALE_MILITIA);
        // [LONGBEARDS]
        // [EREBOR]
        register(context, npcRegistryEntryLookup, EREBOR_MILITIA);
        // [LOTHLORIEN]
        register(context, npcRegistryEntryLookup, LOTHLORIEN_MILITIA);
        // [MORDOR]
        register(context, npcRegistryEntryLookup, MORDOR_ORC_MILITIA);
        register(context, npcRegistryEntryLookup, BLACK_URUK_SOLDIER);
        // [MISTY MOUNTAINS GOBLINS]
        register(context, npcRegistryEntryLookup, MISTY_GOBLIN_MILITIA);
        register(context, npcRegistryEntryLookup, MISTY_HOBGOBLIN_SOLDIER);
        // [ISENGARD]
        register(context, npcRegistryEntryLookup, ISENGARD_ORC_MILITIA);
        register(context, npcRegistryEntryLookup, URUK_HAI_SOLDIER);
        // [SHIRE]
        register(context, npcRegistryEntryLookup, HOBBIT_PEASANT);
    }

    private static NpcData register(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, NpcData npcData) {
        RegistryKey<NpcData> npcRegistryKey = of(npcData.getName());
        String name = npcRegistryKey.getValue().getPath();
        RegistryKey<NpcData> npcKey = RegistryKey.of(NPC_KEY, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcData>> optionalNpc = npcRegistryEntryLookup.getOptional(npcRegistryKey);
        optionalNpc.ifPresent(npcReference -> context.register(npcKey, npcData));

        return npcData;
    }

    private static RegistryKey<NpcData> of(String name) {
        return RegistryKey.of(NPC_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        // region [GENERIC]
        HUMAN_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "human.civilian"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.LEATHER_CAP).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.STRAW_HAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.WOVEN_HAT).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.BYCOCKET).withWeight(1))
                                .add(new NpcGearItemData().withWeight(4))
                        )
                        .add(EquipmentSlot.FEET, new NpcGearSlotData()
                                .add(new NpcGearItemData(ModEquipmentItems.STURDY_BOOTS))
                                .add(new NpcGearItemData(ModEquipmentItems.WORK_SHOES).withWeight(2))
                                .add(new NpcGearItemData(ModEquipmentItems.SHOES).withWeight(2))
                                .add(new NpcGearItemData().withWeight(3))
                        )
        ));

        DWARF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dwarf.civilian"), MiddleEarthRaces.DWARF, List.of(
                new NpcGearData()
        ));

        ELF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "elf.civilian"), MiddleEarthRaces.ELF, List.of(
                new NpcGearData()
        ));

        HOBBIT_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "hobbit.civilian"), MiddleEarthRaces.HOBBIT, List.of(
                new NpcGearData()
        ));

        ORC_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "orc.civilian"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
        ));

        URUK_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "uruk.civilian"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
        ));
        // endregion
        // region [DALE]
        DALE_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dale.militia"), MiddleEarthRaces.HUMAN, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [LONGBEARDS]
        // region [EREBOR]
        EREBOR_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "longbeards.erebor.militia"), MiddleEarthRaces.DWARF, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        //endregion
        // region [LOTHLORIEN]
        LOTHLORIEN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "lothlorien.militia"), MiddleEarthRaces.ELF, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [MORDOR]
        MORDOR_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "mordor.orc.militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        BLACK_URUK_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "mordor.black_uruk.militia"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [MISTY MOUNTAINS GOBLINS]
        MISTY_GOBLIN_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.goblin.militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        MISTY_HOBGOBLIN_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "misty_mountains_goblins.hobgoblin.soldier"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [ISENGARD]
        ISENGARD_ORC_MILITIA = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "isengard.orc.militia"), MiddleEarthRaces.ORC, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        URUK_HAI_SOLDIER = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "isengard.uruk_hai.militia"), MiddleEarthRaces.URUK, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
        // region [SHIRE]
        HOBBIT_PEASANT = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "shire.militia"), MiddleEarthRaces.HOBBIT, List.of(
                new NpcGearData()
                        .add(EquipmentSlot.HEAD, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.KETTLE_HAT)))
                        .add(EquipmentSlot.CHEST, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE)))
                        .add(EquipmentSlot.LEGS, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_CHAIN_COAT)))
                        .add(EquipmentSlot.FEET, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_BOOTS)))
                        .add(EquipmentSlot.MAINHAND, new NpcGearSlotData(new NpcGearItemData(ModWeaponItems.GONDORIAN_SWORD)))
                        .add(EquipmentSlot.OFFHAND, new NpcGearSlotData(new NpcGearItemData(ModEquipmentItems.GONDORIAN_SHIELD)))
        ));
        //endregion
    }
}
