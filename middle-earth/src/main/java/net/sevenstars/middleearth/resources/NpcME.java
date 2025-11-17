package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearItemData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearSlotData;
import net.sevenstars.middleearth.resources.datas.npcs.pools.*;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Middle-earth mod npc registry<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class NpcME {
    public final static String PATH = "npcs";
    public static final RegistryKey<Registry<NpcData>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));
    public static final HashMap<EntityCategory, AttributePool> COMMON_NPC_ATTRIBUTES = new HashMap<>(){{
        put(EntityCategory.SHARED, new AttributePool().addElement(
                AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.35, 0.45)
        ));
    }};
    public static ArrayList<RegistryKey<NpcData>> allNpcDatas;
    // [GENERIC]
    public final static NpcData HUMAN_CIVILIAN;
    public final static NpcData DWARF_CIVILIAN;
    public final static NpcData ELF_CIVILIAN;
    public final static NpcData HOBBIT_CIVILIAN;
    public final static NpcData ORC_CIVILIAN;
    public final static NpcData URUK_CIVILIAN;

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Npcs for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, NpcData.CODEC);
    }

    public static void bootstrap(Registerable<NpcData> context) {
        NpcME.allNpcDatas = new ArrayList<>();

        RegistryEntryLookup<NpcData> npcRegistryEntryLookup = context.getRegistryLookup(KEY);
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
        // [BRIGAND]
        registerAll(context, npcRegistryEntryLookup, BrigandNpcDataPool.fetchAll());
    }

    private static void registerAll(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, List<NpcData> npcDatas) {
        for(NpcData data : npcDatas){
            RegistryKey<NpcData> registered = register(context, npcRegistryEntryLookup, data);
            NpcME.allNpcDatas.add(registered);
        }
    }

    public static RegistryKey<NpcData> register(Registerable<NpcData> context, RegistryEntryLookup<NpcData> npcRegistryEntryLookup, NpcData npcData) {
        RegistryKey<NpcData> npcRegistryKey = of(npcData.getId().getPath());
        String name = npcRegistryKey.getValue().getPath();
        RegistryKey<NpcData> npcKey = RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcData>> optionalNpc = npcRegistryEntryLookup.getOptional(npcRegistryKey);
        optionalNpc.ifPresent(npcReference -> context.register(npcKey, npcData));

        return npcKey;
    }

    private static RegistryKey<NpcData> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        // region [GENERIC/TEMPORARY]
        HUMAN_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "human.civilian"), RacesME.HUMAN, FactionsME.GONDOR, NpcTextureDatasME.GENERIC_HUMAN,   List.of(
                NpcGearData.create()
                        .add(EquipmentSlot.HEAD, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.LEATHER_SKULLCAP).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.STRAW_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.WOVEN_HAT).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.BYCOCKET).withWeight(1))
                                .add(NpcGearItemData.create().withWeight(4))
                        )
                        .add(EquipmentSlot.FEET, NpcGearSlotData.create()
                                .add(NpcGearItemData.create(EquipmentItemsME.STURDY_BOOTS))
                                .add(NpcGearItemData.create(EquipmentItemsME.WORK_SHOES).withWeight(2))
                                .add(NpcGearItemData.create(EquipmentItemsME.SHOES).withWeight(2))
                                .add(NpcGearItemData.create().withWeight(3))
                        )
        ), new HashMap<>());

        DWARF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "dwarf.civilian"), RacesME.DWARF, FactionsME.GONDOR, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
        ), new HashMap<>());

        ELF_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "elf.civilian"), RacesME.ELF, FactionsME.GONDOR, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
        ), new HashMap<>());

        HOBBIT_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "hobbit.civilian"), RacesME.HOBBIT, FactionsME.GONDOR, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
        ), new HashMap<>());

        ORC_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "orc.civilian"), RacesME.ORC, FactionsME.GONDOR, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
        ), new HashMap<>());

        URUK_CIVILIAN = new NpcData(Identifier.of(MiddleEarth.MOD_ID, "uruk.civilian"), RacesME.URUK, FactionsME.GONDOR, NpcTextureDatasME.GENERIC_HUMAN, List.of(
                NpcGearData.create()
        ), new HashMap<>());
        // endregion
    }
}
