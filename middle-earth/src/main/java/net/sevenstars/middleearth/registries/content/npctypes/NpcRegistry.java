package net.sevenstars.middleearth.registries.content.npctypes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.pools.*;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NpcRegistry {
    private static final ResourceKey<Registry<NpcType>> NPC_TYPE_KEY = DynamicRegistriesME.NPC_TYPE;

    public static final HashMap<EntityCategories, AttributePool> COMMON_NPC_ATTRIBUTES = new HashMap<>(){{
        put(EntityCategories.SHARED, new AttributePool().addElement(
            AttributePoolElement.create(Attributes.MOVEMENT_SPEED, 0.25, 0.35)
        ));
    }};

    public static volatile List<ResourceKey<NpcType>> allNpcTypes = List.of();

    // [BRIGANDS]
    public final static ResourceKey<NpcType> BRIGAND_THUG = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.BRIGAND, "thug"));
    public final static ResourceKey<NpcType> BRIGAND_THIEF = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.BRIGAND, "thief"));
    public final static ResourceKey<NpcType> BRIGAND_MERCENARY = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.BRIGAND, "mercenary"));
    public final static ResourceKey<NpcType> BRIGAND_CHIEFTAIN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.BRIGAND, "chieftain"));

    // [WILD GOBLINS]
    public final static ResourceKey<NpcType> WILD_GOBLIN_GATHERER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WILD_GOBLINS, "gatherer"));
    public final static ResourceKey<NpcType> WILD_GOBLIN_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WILD_GOBLINS, "warrior"));
    public final static ResourceKey<NpcType> WILD_GOBLIN_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WILD_GOBLINS, "scout"));
    public final static ResourceKey<NpcType> WILD_GOBLIN_RIDER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WILD_GOBLINS, "rider"));
    public final static ResourceKey<NpcType> WILD_GOBLIN_BRUTE = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WILD_GOBLINS, "brute"));

    // [DALE]
    public final static ResourceKey<NpcType> DALE_PEASANT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "peasant"));
    public final static ResourceKey<NpcType> DALE_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "militia"));
    public final static ResourceKey<NpcType> DALE_SOLDIER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "soldier"));
    public final static ResourceKey<NpcType> DALE_ARCHER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "archer"));
    public final static ResourceKey<NpcType> DALE_KNIGHT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "knight"));
    public final static ResourceKey<NpcType> DALE_ELITE_ARCHER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "elite_archer"));
    public final static ResourceKey<NpcType> DALE_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "veteran"));
    public final static ResourceKey<NpcType> DALE_SERGEANT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.DALE, "sergeant"));

    // [GONDOR]
    public final static ResourceKey<NpcType> GONDOR_PEASANT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "peasant"));
    public final static ResourceKey<NpcType> GONDOR_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "militia"));
    public final static ResourceKey<NpcType> GONDOR_SOLDIER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "soldier"));
    public final static ResourceKey<NpcType> GONDOR_KNIGHT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "knight"));
    public final static ResourceKey<NpcType> GONDOR_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "veteran"));
    public final static ResourceKey<NpcType> GONDOR_FOUNTAIN_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "fountain_guard"));
    public final static ResourceKey<NpcType> GONDOR_CITADEL_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "citadel_guard"));
    public final static ResourceKey<NpcType> GONDOR_KING_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "king_guard"));
    public final static ResourceKey<NpcType> GONDOR_LEADER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GONDOR, "leader"));

    // [ROHAN]
    public final static ResourceKey<NpcType> ROHAN_PEASANT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "peasant"));
    public final static ResourceKey<NpcType> ROHAN_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "militia"));
    public final static ResourceKey<NpcType> ROHAN_SOLDIER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "soldier"));
    public final static ResourceKey<NpcType> ROHAN_KNIGHT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "knight"));
    public final static ResourceKey<NpcType> ROHAN_ROYAL_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "royal_guard"));
    public final static ResourceKey<NpcType> ROHAN_EORLING_MARSHAL = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "eorling_marshal"));
    public final static ResourceKey<NpcType> ROHAN_HORSE_LORD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ROHAN, "horse_lord"));

    // [SHIRE]
    public final static ResourceKey<NpcType> SHIRE_PEASANT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.SHIRE, "peasant"));
    public final static ResourceKey<NpcType> SHIRE_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.SHIRE, "militia"));
    public final static ResourceKey<NpcType> SHIRE_SHIRRIFF = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.SHIRE, "shirriff"));

    // [LONGBEARDS]
    // [EREBOR]
    public final static ResourceKey<NpcType> EREBOR_PEASANT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "peasant"));
    public final static ResourceKey<NpcType> EREBOR_MINER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "miner"));
    public final static ResourceKey<NpcType> EREBOR_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "militia"));
    public final static ResourceKey<NpcType> EREBOR_SOLDIER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "soldier"));
    public final static ResourceKey<NpcType> EREBOR_ARCHER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "archer"));
    public final static ResourceKey<NpcType> EREBOR_ELITE = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "elite"));
    public final static ResourceKey<NpcType> EREBOR_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "veteran"));
    public final static ResourceKey<NpcType> EREBOR_GATEWARDEN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "gatewarden"));
    public final static ResourceKey<NpcType> EREBOR_LEADER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "leader"));

    // [LOTHLORIEN]
    public final static ResourceKey<NpcType> LOTHLORIEN_CIVILIAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "civilian"));
    public final static ResourceKey<NpcType> LOTHLORIEN_SENTINEL = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "sentinel"));
    public final static ResourceKey<NpcType> LOTHLORIEN_RANGER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "ranger"));
    public final static ResourceKey<NpcType> LOTHLORIEN_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "warrior"));
    public final static ResourceKey<NpcType> LOTHLORIEN_KNIGHT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "knight"));
    public final static ResourceKey<NpcType> LOTHLORIEN_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "guard"));
    public final static ResourceKey<NpcType> LOTHLORIEN_EGLADIL_SENTINEL = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "egladil_sentinel"));
    public final static ResourceKey<NpcType> LOTHLORIEN_EGLADIL_COMMANDER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "egladil_commander"));
    public final static ResourceKey<NpcType> LOTHLORIEN_LORD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.LOTHLORIEN, "lord"));

    // [WOODLAND_REALMS]
    public final static ResourceKey<NpcType> WOODLAND_REALM_ARTISAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "artisan"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_HUNTER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "hunter"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_SENTINEL = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "sentinel"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_RANGER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "ranger"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "warrior"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_LANCER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "lancer"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_NIGHTSHADE = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "nightshade"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_ELVEN_KINGS_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "elven_kings_guard"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_COMMANDER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "commander"));
    public final static ResourceKey<NpcType> WOODLAND_REALM_WARDEN_OF_THE_GLADE = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.WOODLAND_REALM, "warden_of_the_glade"));

    // [MORDOR]
    public final static ResourceKey<NpcType> MORDOR_BLACK_NUMENOREAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "black_numenorean"));
    public final static ResourceKey<NpcType> MORDOR_SNAGA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "snaga"));
    public final static ResourceKey<NpcType> MORDOR_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "scout"));
    public final static ResourceKey<NpcType> MORDOR_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "militia"));
    public final static ResourceKey<NpcType> MORDOR_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "warrior"));
    public final static ResourceKey<NpcType> MORDOR_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "veteran"));
    public final static ResourceKey<NpcType> MORDOR_CAPTAIN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "captain"));

    public final static ResourceKey<NpcType> DOL_GULDUR_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "dol_guldur_scout"));
    public final static ResourceKey<NpcType> DOL_GULDUR_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORDOR, "dol_guldur_warrior"));

    // [HOBGOBLINS TRIBES]
    // [GUNDABAD]
    public final static ResourceKey<NpcType> GUNDABAD_GOBLIN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "goblin"));
    public final static ResourceKey<NpcType> GUNDABAD_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "militia"));
    public final static ResourceKey<NpcType> GUNDABAD_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "scout"));
    public final static ResourceKey<NpcType> GUNDABAD_RIDER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "rider"));
    public final static ResourceKey<NpcType> GUNDABAD_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "warrior"));
    public final static ResourceKey<NpcType> GUNDABAD_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "veteran"));
    public final static ResourceKey<NpcType> GUNDABAD_LEADER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "leader"));
    // [MORIA]
    public final static ResourceKey<NpcType> MORIA_GOBLIN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "goblin"));
    public final static ResourceKey<NpcType> MORIA_MILITIA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "militia"));
    public final static ResourceKey<NpcType> MORIA_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "scout"));
    public final static ResourceKey<NpcType> MORIA_RIDER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "rider"));
    public final static ResourceKey<NpcType> MORIA_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "warrior"));
    public final static ResourceKey<NpcType> MORIA_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "veteran"));
    public final static ResourceKey<NpcType> MORIA_CHIEF = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.MORIA, "chief"));

    // [GOBLIN TOWN]
    public final static ResourceKey<NpcType> GOBLIN_TOWN_GOBLIN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GOBLIN_TOWN, "goblin"));
    public final static ResourceKey<NpcType> GOBLIN_TOWN_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GOBLIN_TOWN, "scout"));
    public final static ResourceKey<NpcType> GOBLIN_TOWN_RIDER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GOBLIN_TOWN, "rider"));
    public final static ResourceKey<NpcType> GOBLIN_TOWN_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GOBLIN_TOWN, "warrior"));
    public final static ResourceKey<NpcType> GOBLIN_TOWN_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.GOBLIN_TOWN, "veteran"));

    // [ISENGARD]
    public final static ResourceKey<NpcType> ISENGARD_ORC_SNAGA = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "snaga"));
    public final static ResourceKey<NpcType> ISENGARD_ORC_WARRIOR = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "warrior"));
    public final static ResourceKey<NpcType> ISENGARD_URUK_HAI_SOLDIER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "soldier"));
    public final static ResourceKey<NpcType> ISENGARD_URUK_HAI_SCOUT = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "scout"));
    public final static ResourceKey<NpcType> ISENGARD_URUK_HAI_VETERAN = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "veteran"));
    public final static ResourceKey<NpcType> ISENGARD_URUK_HAI_BERSERKER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "berserker"));
    public final static ResourceKey<NpcType> ISENGARD_URUK_HAI_LEADER = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "leader"));
    public final static ResourceKey<NpcType> ISENGARD_ORTHANC_GUARD = DynamicRegistriesME.of(NPC_TYPE_KEY, createID(FactionRegistry.ISENGARD, "orthanc_guard"));



    public static void bootstrap(BootstrapContext<NpcType> context) {
        HolderGetter<NpcType> registryEntryLookup = context.lookup(NPC_TYPE_KEY);
        List<ResourceKey<NpcType>> registeredNpcTypes = new ArrayList<>();

        registerAll(context, registryEntryLookup, registeredNpcTypes, BrigandNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, WildGoblinNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, DalishNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, GondorianNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, RohirricNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, ShireNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, EreborNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, LorienNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, WoodlandRealmNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, MordorNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, GoblinTownNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, MoriaNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, GundabadNpcTypePool.fetchAll());
        registerAll(context, registryEntryLookup, registeredNpcTypes, IsengardNpcTypePool.fetchAll());
        allNpcTypes = List.copyOf(registeredNpcTypes);
    }

    private static void registerAll(
            BootstrapContext<NpcType> context,
            HolderGetter<NpcType> registryEntryLookup,
            List<ResourceKey<NpcType>> registeredNpcTypes,
            List<RegisterableNpcData> npcDatas) {
        for(RegisterableNpcData registerable : npcDatas){
            register(context, registryEntryLookup, registerable.npcDataRegistryKey, registerable.content);
            registeredNpcTypes.add(registerable.npcDataRegistryKey);
        }
    }

    private static void register(BootstrapContext<NpcType> context, HolderGetter<NpcType> registryEntryLookup, ResourceKey<NpcType> registryKey, NpcType element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.npcTypeEntries.add(registryKey.location().getPath());
        TranslationEntries.spawnEggEntries.add(element.getId());
    }

    private static ResourceLocation createID(ResourceKey<Faction> faction, String name){
        return MiddleEarth.of(faction.location().getPath(), name);
    }

    public record RegisterableNpcData (ResourceKey<NpcType> npcDataRegistryKey, NpcType content){ }
}
