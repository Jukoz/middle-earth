package net.sevenstars.middleearth.registries.content.npcs;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.factions.pools.LothlorienFactionPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.*;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NpcRegistry {
    private static final RegistryKey<Registry<NpcData>> NPC_KEY = DynamicRegistriesME.NPC;

    public static final HashMap<EntityCategory, AttributePool> COMMON_NPC_ATTRIBUTES = new HashMap<>(){{
        put(EntityCategory.SHARED, new AttributePool().addElement(
            AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.35, 0.45)
        ));
    }};

    public static ArrayList<RegistryKey<NpcData>> allNpcDatas;

    // [BRIGANDS]
    public final static RegistryKey<NpcData> BRIGAND_THUG = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.BRIGAND, "thug"));
    public final static RegistryKey<NpcData> BRIGAND_THIEF = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.BRIGAND, "thief"));
    public final static RegistryKey<NpcData> BRIGAND_MERCENARY = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.BRIGAND, "mercenary"));
    public final static RegistryKey<NpcData> BRIGAND_CHIEFTAIN = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.BRIGAND, "chieftain"));

    // [WILD GOBLINS]
    public final static RegistryKey<NpcData> WILD_GOBLIN_GATHERER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.WILD_GOBLINS, "gatherer"));
    public final static RegistryKey<NpcData> WILD_GOBLIN_WARRIOR = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.WILD_GOBLINS, "warrior"));
    public final static RegistryKey<NpcData> WILD_GOBLIN_SCOUT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.WILD_GOBLINS, "scout"));
    public final static RegistryKey<NpcData> WILD_GOBLIN_RIDER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.WILD_GOBLINS, "rider"));
    public final static RegistryKey<NpcData> WILD_GOBLIN_BRUTE = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.WILD_GOBLINS, "brute"));

    // [DALE]
    public final static RegistryKey<NpcData> DALE_PEASANT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "peasant"));
    public final static RegistryKey<NpcData> DALE_MILITIA = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "militia"));
    public final static RegistryKey<NpcData> DALE_SOLDIER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "soldier"));
    public final static RegistryKey<NpcData> DALE_ARCHER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "archer"));
    public final static RegistryKey<NpcData> DALE_KNIGHT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "knight"));
    public final static RegistryKey<NpcData> DALE_ELITE_ARCHER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "elite_archer"));
    public final static RegistryKey<NpcData> DALE_VETERAN = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "veteran"));
    public final static RegistryKey<NpcData> DALE_SERGEANT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.DALE, "sergeant"));

    // [GONDOR]
    public final static RegistryKey<NpcData> GONDOR_PEASANT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "peasant"));
    public final static RegistryKey<NpcData> GONDOR_MILITIA = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "militia"));
    public final static RegistryKey<NpcData> GONDOR_SOLDIER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "soldier"));
    public final static RegistryKey<NpcData> GONDOR_KNIGHT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "knight"));
    public final static RegistryKey<NpcData> GONDOR_VETERAN = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "veteran"));
    public final static RegistryKey<NpcData> GONDOR_FOUNTAIN_GUARD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "fountain_guard"));
    public final static RegistryKey<NpcData> GONDOR_CITADEL_GUARD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "citadel_guard"));
    public final static RegistryKey<NpcData> GONDOR_KING_GUARD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "king_guard"));
    public final static RegistryKey<NpcData> GONDOR_LEADER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.GONDOR, "leader"));

    // [ROHAN]
    public final static RegistryKey<NpcData> ROHAN_PEASANT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "peasant"));
    public final static RegistryKey<NpcData> ROHAN_MILITIA = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "militia"));
    public final static RegistryKey<NpcData> ROHAN_SOLDIER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "soldier"));
    public final static RegistryKey<NpcData> ROHAN_KNIGHT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "knight"));
    public final static RegistryKey<NpcData> ROHAN_ROYAL_GUARD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "royal_guard"));
    public final static RegistryKey<NpcData> ROHAN_EORLING_MARSHAL = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "eorling_marshal"));
    public final static RegistryKey<NpcData> ROHAN_HORSE_LORD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.ROHAN, "horse_lord"));

    // [SHIRE]
    public final static RegistryKey<NpcData> SHIRE_PEASANT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.SHIRE, "peasant"));
    public final static RegistryKey<NpcData> SHIRE_MILITIA = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.SHIRE, "militia"));
    public final static RegistryKey<NpcData> SHIRE_SHIRRIFF = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.SHIRE, "shirriff"));

    // [LONGBEARDS]
    // [EREBOR]
    public final static RegistryKey<NpcData> EREBOR_PEASANT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "peasant"));
    public final static RegistryKey<NpcData> EREBOR_MINER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "miner"));
    public final static RegistryKey<NpcData> EREBOR_MILITIA = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "militia"));
    public final static RegistryKey<NpcData> EREBOR_SOLDIER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "soldier"));
    public final static RegistryKey<NpcData> EREBOR_ARCHER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "archer"));
    public final static RegistryKey<NpcData> EREBOR_ELITE = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "elite"));
    public final static RegistryKey<NpcData> EREBOR_VETERAN = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "veteran"));
    public final static RegistryKey<NpcData> EREBOR_GATEWARDEN = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "gatewarden"));
    public final static RegistryKey<NpcData> EREBOR_LEADER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "leader"));

    // [LOTHLORIEN]
    public final static RegistryKey<NpcData> LOTHLORIEN_SENTINEL = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LOTHLORIEN, "sentinel"));
    public final static RegistryKey<NpcData> LOTHLORIEN_RANGER = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LOTHLORIEN, "ranger"));
    public final static RegistryKey<NpcData> LOTHLORIEN_WARRIOR = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LOTHLORIEN, "warrior"));
    public final static RegistryKey<NpcData> LOTHLORIEN_KNIGHT = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LOTHLORIEN, "knight"));
    public final static RegistryKey<NpcData> LOTHLORIEN_GUARD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LOTHLORIEN, "guard"));
    public final static RegistryKey<NpcData> LOTHLORIEN_LORD = DynamicRegistriesME.of(NPC_KEY, createID(FactionRegistry.LOTHLORIEN, "lord"));

    public static void bootstrap(Registerable<NpcData> context) {
        RegistryEntryLookup<NpcData> registryEntryLookup = context.getRegistryLookup(NPC_KEY);

        allNpcDatas = new ArrayList<>();

        registerAll(context, registryEntryLookup, BrigandNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, WildGoblinNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, DalishNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, GondorianNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, RohirricNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, ShireNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, EreborNpcDataPool.fetchAll());
        registerAll(context, registryEntryLookup, LorienNpcDataPool.fetchAll());
    }

    private static void registerAll(Registerable<NpcData> context, RegistryEntryLookup<NpcData> registryEntryLookup, List<RegisterableNpcData> npcDatas) {
        for(RegisterableNpcData registerable : npcDatas){
            register(context, registryEntryLookup, registerable.npcDataRegistryKey, registerable.content);
            allNpcDatas.add(registerable.npcDataRegistryKey);
        }
    }

    private static void register(Registerable<NpcData> context, RegistryEntryLookup<NpcData> registryEntryLookup, RegistryKey<NpcData> registryKey, NpcData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.npcDataEntries.add(registryKey.getValue().getPath());
    }

    private static Identifier createID(RegistryKey<Faction> faction, String name){
        return IdentifierUtil.buildAggregate(faction.getValue().getPath(), name);
    }

    public record RegisterableNpcData (RegistryKey<NpcData> npcDataRegistryKey, NpcData content){ }
}
