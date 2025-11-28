package net.sevenstars.middleearth.registries;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class DynamicRegistriesME extends net.sevenstars.api.registries.DynamicRegistries {

    public static final RegistryKey<Registry<Race>> RACE = RegistryKey.ofRegistry(IdentifierUtil.create( "race"));
    public static final RegistryKey<Registry<Faction>> FACTION = RegistryKey.ofRegistry(IdentifierUtil.create( "faction"));
    public static final RegistryKey<Registry<NpcData>> NPC = RegistryKey.ofRegistry(IdentifierUtil.create( "npc"));
    public static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT = RegistryKey.ofRegistry(IdentifierUtil.create( "biome_event"));
    public static final RegistryKey<Registry<SpiderVariant>> SPIDER_VARIANT = RegistryKey.ofRegistry(IdentifierUtil.create( "spider_variant"));
    public static final RegistryKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA = RegistryKey.ofRegistry(IdentifierUtil.create( "structure_manager_data"));

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Entries for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(RACE, Race.CODEC);
        DynamicRegistries.registerSynced(NPC, NpcData.CODEC);
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BIOME_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(SPIDER_VARIANT, SpiderVariant.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC);
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RACE, RaceRegistry::bootstrap);
        registryBuilder.addRegistry(NPC, NpcRegistry::bootstrap);
        registryBuilder.addRegistry(FACTION, FactionRegistry::bootstrap);
        registryBuilder.addRegistry(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.addRegistry(SPIDER_VARIANT, SpiderVariantRegistry::bootstrap);
        registryBuilder.addRegistry(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);

        //registryBuilder.addRegistry(NpcTextureDatasME.KEY, NpcTextureDatasME::bootstrap);

    }
}