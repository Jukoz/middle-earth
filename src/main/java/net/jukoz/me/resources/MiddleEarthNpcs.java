package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearItemData;
import net.jukoz.me.resources.datas.npcs.data.NpcGearSlotData;
import net.jukoz.me.resources.datas.npcs.pools.*;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

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
    public static void register(){
        LoggerUtil.logDebugMsg("Registering Dynamic Npcs for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(NPC_KEY, NpcData.CODEC);
    }

    public static void bootstrap(Registerable<NpcData> context) {
        RegistryEntryLookup<NpcData> npcRegistryEntryLookup = context.getRegistryLookup(NPC_KEY);
        // [RACE / GENERIC]
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
        register(context, npcRegistryEntryLookup, DalishNpcDataPool.DALE_MILITIA);
        // [LONGBEARDS]
        // [EREBOR]
        register(context, npcRegistryEntryLookup, EreborNpcDataPool.EREBOR_MILITIA);
        // [LOTHLORIEN]
        register(context, npcRegistryEntryLookup, LorienNpcDataPool.LOTHLORIEN_MILITIA);
        // [MORDOR]
        register(context, npcRegistryEntryLookup, MordorNpcDataPool.MORDOR_ORC_MILITIA);
        register(context, npcRegistryEntryLookup, MordorNpcDataPool.MORDOR_BLACK_URUK_MILITIA);
        // [MISTY MOUNTAINS GOBLINS]
        register(context, npcRegistryEntryLookup, MistyMountainsGoblinsNpcDataPool.MISTY_GOBLIN_MILITIA);
        register(context, npcRegistryEntryLookup, MistyMountainsGoblinsNpcDataPool.MISTY_HOBGOBLIN_MILITIA);
        // [ISENGARD]
        register(context, npcRegistryEntryLookup, IsengardNpcDataPool.ISENGARD_ORC_MILITIA);
        register(context, npcRegistryEntryLookup, IsengardNpcDataPool.ISENGARD_URUK_HAI_MILITIA);
        // [SHIRE]
        register(context, npcRegistryEntryLookup, ShireNpcDataPool.SHIRE_MILITIA);
        // [BANDIT]
        register(context, npcRegistryEntryLookup, BanditNpcDataPool.BANDIT_MILITIA);
        register(context, npcRegistryEntryLookup, BanditNpcDataPool.BANDIT_THIEF);
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
    }
}
