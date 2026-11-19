package net.sevenstars.middleearth.registries;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.providers.dynamic.GreatHornVariantsProvider;
import net.sevenstars.middleearth.datageneration.providers.dynamic.*;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.registries.content.greathornvariants.GreatHornVariantRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.registries.content.forage.ForageTables;
import net.sevenstars.middleearth.resources.datas.forage.ForageTable;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJob;

public class DynamicRegistriesME extends DynamicRegistriesAPI {
    public static final RegistryKey<Registry<Race>> RACE = RegistryKey.ofRegistry(MiddleEarth.id("race"));
    public static final RegistryKey<Registry<Faction>> FACTION = RegistryKey.ofRegistry(MiddleEarth.id("faction"));
    public static final RegistryKey<Registry<NpcClass>> NPC_CLASS = RegistryKey.ofRegistry(MiddleEarth.id("npc_type"));
    public static final Codec<RegistryEntry<NpcClass>> NPC_CLASS_CODEC = RegistryFixedCodec.of(DynamicRegistriesME.NPC_CLASS);
    public static final RegistryKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA  = RegistryKey.ofRegistry(MiddleEarth.id("structure_manager_data"));
    public static final RegistryKey<Registry<NpcJob>> NPC_JOB = RegistryKey.ofRegistry(MiddleEarth.id("npc_role"));
    public static final RegistryKey<Registry<ForageTable>> FORAGE = RegistryKey.ofRegistry(MiddleEarth.id("forage"));
    public static final RegistryKey<Registry<StructureFeatureData>> STRUCTURE_FEATURES = RegistryKey.ofRegistry(MiddleEarth.id("structure_features"));
    public static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT = RegistryKey.ofRegistry(MiddleEarth.id("biome_event"));
    public static final RegistryKey<Registry<BiomeEventData>> STRUCTURE_EVENT = RegistryKey.ofRegistry(MiddleEarth.id("structure_event"));

    public static final RegistryKey<Registry<TexturePresetDataPool>> TEXTURE_PRESETS = RegistryKey.ofRegistry(MiddleEarth.id( "texture_presets"));

    public static final RegistryKey<Registry<CharacterTextureMaterial>> SKIN_MATERIAL = RegistryKey.ofRegistry(MiddleEarth.id("skin_material"));
    public static final RegistryKey<Registry<CharacterTextureMaterial>> EYE_MATERIAL = RegistryKey.ofRegistry(MiddleEarth.id("eye_material"));
    public static final RegistryKey<Registry<CharacterTextureMaterial>> HAIR_MATERIAL = RegistryKey.ofRegistry(MiddleEarth.id("hair_material"));

    public static final RegistryKey<Registry<CharacterTexturePattern>> SKIN_PATTERN = RegistryKey.ofRegistry(MiddleEarth.id("skin_pattern"));
    public static final RegistryKey<Registry<CharacterTexturePattern>> EYE_PATTERN = RegistryKey.ofRegistry(MiddleEarth.id("eye_pattern"));
    public static final RegistryKey<Registry<CharacterTexturePattern>> HAIR_PATTERN = RegistryKey.ofRegistry(MiddleEarth.id("hair_pattern"));

    public static final RegistryKey<Registry<SpiderVariant>> SPIDER_VARIANTS = RegistryKey.ofRegistry(MiddleEarth.id("spider_variants"));
    public static final RegistryKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS = RegistryKey.ofRegistry(MiddleEarth.id("great_horn_variants"));

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Entries for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(RACE, Race.CODEC);
        DynamicRegistries.registerSynced(NPC_CLASS, NpcClass.CODEC);
        DynamicRegistries.registerSynced(NPC_JOB, NpcJob.CODEC);
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BIOME_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC);
        // Only the server places npcs
        DynamicRegistries.register(STRUCTURE_FEATURES, StructureFeatureData.CODEC);
        DynamicRegistries.register(FORAGE, ForageTable.CODEC);
        DynamicRegistries.registerSynced(TEXTURE_PRESETS, TexturePresetDataPool.CODEC);

        DynamicRegistries.registerSynced(SKIN_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(HAIR_PATTERN, CharacterTexturePattern.CODEC);

        DynamicRegistries.registerSynced(SKIN_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(EYE_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(HAIR_MATERIAL, CharacterTextureMaterial.CODEC);

        DynamicRegistries.registerSynced(SPIDER_VARIANTS, SpiderVariant.CODEC);
        DynamicRegistries.registerSynced(GREAT_HORN_VARIANTS, GreatHornVariant.CODEC);

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            StructureFeaturesRegistry.clearLive();
        });
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RACE, RaceRegistry::bootstrap);
        registryBuilder.addRegistry(NPC_CLASS, NpcRegistry::bootstrap);
        registryBuilder.addRegistry(NPC_JOB, NpcJobs::bootstrap);
        registryBuilder.addRegistry(FACTION, FactionRegistry::bootstrap);
        registryBuilder.addRegistry(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.addRegistry(STRUCTURE_EVENT, BiomeEventRegistry::bootstrapStructureEvents);
        registryBuilder.addRegistry(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);
        registryBuilder.addRegistry(STRUCTURE_FEATURES, StructureFeaturesRegistry::bootstrap);
        registryBuilder.addRegistry(FORAGE, ForageTables::bootstrap);
        registryBuilder.addRegistry(TEXTURE_PRESETS, TexturePresetsRegistry::bootstrap);

        registryBuilder.addRegistry(SPIDER_VARIANTS, SpiderVariantRegistry::bootstrap);
        registryBuilder.addRegistry(GREAT_HORN_VARIANTS, GreatHornVariantRegistry::bootstrap);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpiderVariantsProvider::new);
        pack.addProvider(RaceProvider::new);
        pack.addProvider(TexturePresetsProvider::new);
        pack.addProvider(NpcProvider::new);
        pack.addProvider(NpcJobProvider::new);
        pack.addProvider(FactionProvider::new);
        pack.addProvider(StructureDataProvider::new);
        pack.addProvider(StructureFeaturesProvider::new);
        pack.addProvider(ForageProvider::new);
        pack.addProvider(BiomeEventProvider::new);
        pack.addProvider(StructureEventProvider::new);
        pack.addProvider(GreatHornVariantsProvider::new);
    }
}