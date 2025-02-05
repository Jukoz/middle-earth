package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.sevenstars.middleearth.resources.datas.npcs.pools.*;
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
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Npcs for " + MiddleEarth.MOD_ID);
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
        registerAll(context, npcRegistryEntryLookup, GondorianNpcDataPool.fetchAll());
        // [ROHAN]
        registerAll(context, npcRegistryEntryLookup, RohirricNpcDataPool.fetchAll());
        // [DALE]
        registerAll(context, npcRegistryEntryLookup, DalishNpcDataPool.fetchAll());
        // [LONGBEARDS] - [EREBOR]
        registerAll(context, npcRegistryEntryLookup, EreborNpcDataPool.fetchAll());
        // [LOTHLORIEN]
        registerAll(context, npcRegistryEntryLookup, LorienNpcDataPool.fetchAll());
        // [MORDOR]
        registerAll(context, npcRegistryEntryLookup, MordorNpcDataPool.fetchAll());
        // [MISTY MOUNTAINS GOBLINS]
        registerAll(context, npcRegistryEntryLookup, MistyMountainsGoblinsNpcDataPool.fetchAll());
        // [ISENGARD]
        registerAll(context, npcRegistryEntryLookup, IsengardNpcDataPool.fetchAll());
        // [SHIRE]
        registerAll(context, npcRegistryEntryLookup, ShireNpcDataPool.fetchAll());
        // [BANDIT]
        registerAll(context, npcRegistryEntryLookup, BanditNpcDataPool.fetchAll());
    }

    private static void registerAll(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, List<NpcData> npcDatas) {
        for(NpcData data : npcDatas){
            register(context, npcRegistryEntryLookup, data);
        }
    }

    public static NpcData register(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, NpcData npcData) {
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
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.LEATHER_SKULLCAP).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.STRAW_HAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.WOVEN_HAT).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.BYCOCKET).withWeight(1))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(ModEquipmentItems.STURDY_BOOTS))
                                .add(NpcGearItemData.create(ModEquipmentItems.WORK_SHOES).withWeight(2))
                                .add(NpcGearItemData.create(ModEquipmentItems.SHOES).withWeight(2))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ));

        DWARF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dwarf.civilian"), MiddleEarthRaces.DWARF, List.of(
                NpcGearData.create()
        ));

        ELF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "elf.civilian"), MiddleEarthRaces.ELF, List.of(
                NpcGearData.create()
        ));

        HOBBIT_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "hobbit.civilian"), MiddleEarthRaces.HOBBIT, List.of(
                NpcGearData.create()
        ));

        ORC_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "orc.civilian"), MiddleEarthRaces.ORC, List.of(
                NpcGearData.create()
        ));

        URUK_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "uruk.civilian"), MiddleEarthRaces.URUK, List.of(
                NpcGearData.create()
        ));
        // endregion
    }
}
