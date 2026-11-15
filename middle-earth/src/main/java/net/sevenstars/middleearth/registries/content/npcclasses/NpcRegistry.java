package net.sevenstars.middleearth.registries.content.npcclasses;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.registries.content.npcclasses.pools.*;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NpcRegistry {
    private static final RegistryKey<Registry<NpcClass>> NPC_CLASS_KEY = DynamicRegistriesME.NPC_CLASS;
    private static final double COMMON_SPEED_MIN = 0.25;
    private static final double COMMON_SPEED_MAX = 0.35;

    public static final HashMap<EntityCategories, AttributePool> COMMON_NPC_ATTRIBUTES = new HashMap<>(){{
        put(EntityCategories.SHARED, new AttributePool().addElement(
            AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, COMMON_SPEED_MIN, COMMON_SPEED_MAX)
        ));
    }};

    public static ArrayList<RegistryKey<NpcClass>> allNpcClasses;

    // [BRIGANDS]
    public final static RegistryKey<NpcClass> BRIGAND_THUG = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.BRIGAND, "thug"));
    public final static RegistryKey<NpcClass> BRIGAND_THIEF = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.BRIGAND, "thief"));
    public final static RegistryKey<NpcClass> BRIGAND_MERCENARY = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.BRIGAND, "mercenary"));
    public final static RegistryKey<NpcClass> BRIGAND_CHIEFTAIN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.BRIGAND, "chieftain"));

    // [WILD GOBLINS]
    public final static RegistryKey<NpcClass> WILD_GOBLIN_GATHERER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WILD_GOBLINS, "gatherer"));
    public final static RegistryKey<NpcClass> WILD_GOBLIN_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WILD_GOBLINS, "warrior"));
    public final static RegistryKey<NpcClass> WILD_GOBLIN_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WILD_GOBLINS, "scout"));
    public final static RegistryKey<NpcClass> WILD_GOBLIN_RIDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WILD_GOBLINS, "rider"));
    public final static RegistryKey<NpcClass> WILD_GOBLIN_BRUTE = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WILD_GOBLINS, "brute"));

    // [DALE]
    public final static RegistryKey<NpcClass> DALE_PEASANT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "peasant"));
    public final static RegistryKey<NpcClass> DALE_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "militia"));
    public final static RegistryKey<NpcClass> DALE_SOLDIER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "soldier"));
    public final static RegistryKey<NpcClass> DALE_ARCHER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "archer"));
    public final static RegistryKey<NpcClass> DALE_KNIGHT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "knight"));
    public final static RegistryKey<NpcClass> DALE_ELITE_ARCHER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "elite_archer"));
    public final static RegistryKey<NpcClass> DALE_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "veteran"));
    public final static RegistryKey<NpcClass> DALE_SERGEANT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.DALE, "sergeant"));

    // [GONDOR]
    public final static RegistryKey<NpcClass> GONDOR_PEASANT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "peasant"));
    public final static RegistryKey<NpcClass> GONDOR_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "militia"));
    public final static RegistryKey<NpcClass> GONDOR_SOLDIER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "soldier"));
    public final static RegistryKey<NpcClass> GONDOR_ITHILIEN_RANGER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "ithilien_ranger"));
    public final static RegistryKey<NpcClass> GONDOR_KNIGHT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "knight"));
    public final static RegistryKey<NpcClass> GONDOR_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "veteran"));
    public final static RegistryKey<NpcClass> GONDOR_FOUNTAIN_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "fountain_guard"));
    public final static RegistryKey<NpcClass> GONDOR_CITADEL_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "citadel_guard"));
    public final static RegistryKey<NpcClass> GONDOR_KING_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "king_guard"));
    public final static RegistryKey<NpcClass> GONDOR_LEADER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GONDOR, "leader"));

    // [ROHAN]
    public final static RegistryKey<NpcClass> ROHAN_PEASANT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "peasant"));
    public final static RegistryKey<NpcClass> ROHAN_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "militia"));
    public final static RegistryKey<NpcClass> ROHAN_SOLDIER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "soldier"));
    public final static RegistryKey<NpcClass> ROHAN_KNIGHT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "knight"));
    public final static RegistryKey<NpcClass> ROHAN_ROYAL_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "royal_guard"));
    public final static RegistryKey<NpcClass> ROHAN_EORLING_MARSHAL = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "eorling_marshal"));
    public final static RegistryKey<NpcClass> ROHAN_HORSE_LORD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ROHAN, "horse_lord"));

    // [SHIRE]
    public final static RegistryKey<NpcClass> SHIRE_PEASANT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.SHIRE, "peasant"));
    public final static RegistryKey<NpcClass> SHIRE_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.SHIRE, "militia"));
    public final static RegistryKey<NpcClass> SHIRE_BOUNDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.SHIRE, "bounder"));
    public final static RegistryKey<NpcClass> SHIRE_SHIRRIFF = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.SHIRE, "shirriff"));

    // [LONGBEARDS]
    // [EREBOR]
    public final static RegistryKey<NpcClass> EREBOR_PEASANT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "peasant"));
    public final static RegistryKey<NpcClass> EREBOR_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "militia"));
    public final static RegistryKey<NpcClass> EREBOR_SOLDIER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "soldier"));
    public final static RegistryKey<NpcClass> EREBOR_ARCHER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "archer"));
    public final static RegistryKey<NpcClass> EREBOR_ELITE = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "elite"));
    public final static RegistryKey<NpcClass> EREBOR_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "veteran"));
    public final static RegistryKey<NpcClass> EREBOR_GATEWARDEN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "gatewarden"));
    public final static RegistryKey<NpcClass> EREBOR_LEADER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LONGBEARDS_EREBOR, "leader"));

    // [LOTHLORIEN]
    public final static RegistryKey<NpcClass> LOTHLORIEN_CIVILIAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "civilian"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_SENTINEL = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "sentinel"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_RANGER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "ranger"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "warrior"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_KNIGHT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "knight"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "guard"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_EGLADIL_SENTINEL = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "egladil_sentinel"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_EGLADIL_COMMANDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "egladil_commander"));
    public final static RegistryKey<NpcClass> LOTHLORIEN_LORD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.LOTHLORIEN, "lord"));

    // [WOODLAND_REALMS]
    public final static RegistryKey<NpcClass> WOODLAND_REALM_ARTISAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "artisan"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_HUNTER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "hunter"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_SENTINEL = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "sentinel"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_RANGER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "ranger"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "warrior"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_LANCER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "lancer"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_NIGHTSHADE = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "nightshade"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_ELVEN_KINGS_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "elven_kings_guard"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_COMMANDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "commander"));
    public final static RegistryKey<NpcClass> WOODLAND_REALM_WARDEN_OF_THE_GLADE = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.WOODLAND_REALM, "warden_of_the_glade"));

    // [MORDOR]
    public final static RegistryKey<NpcClass> MORDOR_BLACK_NUMENOREAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "black_numenorean"));
    public final static RegistryKey<NpcClass> MORDOR_SNAGA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "snaga"));
    public final static RegistryKey<NpcClass> MORDOR_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "scout"));
    public final static RegistryKey<NpcClass> MORDOR_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "militia"));
    public final static RegistryKey<NpcClass> MORDOR_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "warrior"));
    public final static RegistryKey<NpcClass> MORDOR_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "veteran"));
    public final static RegistryKey<NpcClass> MORDOR_CAPTAIN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "captain"));

    public final static RegistryKey<NpcClass> DOL_GULDUR_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "dol_guldur_scout"));
    public final static RegistryKey<NpcClass> DOL_GULDUR_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORDOR, "dol_guldur_warrior"));

    // [HOBGOBLINS TRIBES]
    // [GUNDABAD]
    public final static RegistryKey<NpcClass> GUNDABAD_GOBLIN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "goblin"));
    public final static RegistryKey<NpcClass> GUNDABAD_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "militia"));
    public final static RegistryKey<NpcClass> GUNDABAD_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "scout"));
    public final static RegistryKey<NpcClass> GUNDABAD_RIDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "rider"));
    public final static RegistryKey<NpcClass> GUNDABAD_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "warrior"));
    public final static RegistryKey<NpcClass> GUNDABAD_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "veteran"));
    public final static RegistryKey<NpcClass> GUNDABAD_LEADER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "leader"));
    // [MORIA]
    public final static RegistryKey<NpcClass> MORIA_GOBLIN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "goblin"));
    public final static RegistryKey<NpcClass> MORIA_MILITIA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "militia"));
    public final static RegistryKey<NpcClass> MORIA_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "scout"));
    public final static RegistryKey<NpcClass> MORIA_RIDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "rider"));
    public final static RegistryKey<NpcClass> MORIA_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "warrior"));
    public final static RegistryKey<NpcClass> MORIA_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "veteran"));
    public final static RegistryKey<NpcClass> MORIA_CHIEF = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.MORIA, "chief"));

    // [GOBLIN TOWN]
    public final static RegistryKey<NpcClass> GOBLIN_TOWN_GOBLIN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GOBLIN_TOWN, "goblin"));
    public final static RegistryKey<NpcClass> GOBLIN_TOWN_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GOBLIN_TOWN, "scout"));
    public final static RegistryKey<NpcClass> GOBLIN_TOWN_RIDER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GOBLIN_TOWN, "rider"));
    public final static RegistryKey<NpcClass> GOBLIN_TOWN_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GOBLIN_TOWN, "warrior"));
    public final static RegistryKey<NpcClass> GOBLIN_TOWN_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.GOBLIN_TOWN, "veteran"));

    // [ISENGARD]
    public final static RegistryKey<NpcClass> ISENGARD_ORC_SNAGA = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "snaga"));
    public final static RegistryKey<NpcClass> ISENGARD_ORC_WARRIOR = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "warrior"));
    public final static RegistryKey<NpcClass> ISENGARD_URUK_HAI_SOLDIER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "soldier"));
    public final static RegistryKey<NpcClass> ISENGARD_URUK_HAI_SCOUT = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "scout"));
    public final static RegistryKey<NpcClass> ISENGARD_URUK_HAI_VETERAN = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "veteran"));
    public final static RegistryKey<NpcClass> ISENGARD_URUK_HAI_BERSERKER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "berserker"));
    public final static RegistryKey<NpcClass> ISENGARD_URUK_HAI_LEADER = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "leader"));
    public final static RegistryKey<NpcClass> ISENGARD_ORTHANC_GUARD = DynamicRegistriesME.of(NPC_CLASS_KEY, createID(FactionRegistry.ISENGARD, "orthanc_guard"));
    // Classes that were folded into a base class with a job. An npc saved as one of them comes back as that
    public record Folded(RegistryKey<NpcClass> npcClass, RegistryKey<NpcJob> job) {}
    public final static Map<Identifier, Folded> FOLDED = Map.of(
            createID(FactionRegistry.LONGBEARDS_EREBOR, "miner"), new Folded(EREBOR_PEASANT, NpcJobs.MINER)
    );

    public static void bootstrap(Registerable<NpcClass> context) {
        RegistryEntryLookup<NpcClass> registryEntryLookup = context.getRegistryLookup(NPC_CLASS_KEY);

        allNpcClasses = new ArrayList<>();

        registerAll(context, registryEntryLookup, BrigandNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, WildGoblinNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, DalishNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, GondorianNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, RohirricNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, ShireNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, EreborNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, LorienNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, WoodlandRealmNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, MordorNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, GoblinTownNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, MoriaNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, GundabadNpcClassPool.fetchAll());
        registerAll(context, registryEntryLookup, IsengardNpcClassPool.fetchAll());
    }

    private static void registerAll(Registerable<NpcClass> context, RegistryEntryLookup<NpcClass> registryEntryLookup, List<RegisterableNpcData> npcDatas) {
        for(RegisterableNpcData registerable : npcDatas){
            register(context, registryEntryLookup, registerable.npcDataRegistryKey, registerable.content);
            allNpcClasses.add(registerable.npcDataRegistryKey);
        }
    }

    private static void register(Registerable<NpcClass> context, RegistryEntryLookup<NpcClass> registryEntryLookup, RegistryKey<NpcClass> registryKey, NpcClass element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.npcClassEntries.add(registryKey.getValue().getPath());
        TranslationEntries.spawnEggEntries.add(element.getId());
    }

    private static Identifier createID(RegistryKey<Faction> faction, String name){
        return MiddleEarth.idAggregate(faction.getValue().getPath(), name);
    }

    public record RegisterableNpcData (RegistryKey<NpcClass> npcDataRegistryKey, NpcClass content){ }
}
