package net.sevenstars.middleearth.registries;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.providers.dynamic.*;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class DynamicRegistriesME extends net.sevenstars.api.registries.DynamicRegistries {
    public static final RegistryKey<Registry<Race>> RACE = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("race"));
    public static final RegistryKey<Registry<Faction>> FACTION = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("faction"));
    public static final RegistryKey<Registry<NpcData>> NPC = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("npc"));
    public static final RegistryKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA  = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("structure_manager_data"));
    public static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("biome_event"));

    public static final RegistryKey<Registry<TexturePresetDatas>> TEXTURE_PRESETS = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate( "texture_presets"));

    public static final RegistryKey<Registry<CharacterTextureMaterial>> SKIN_MATERIAL = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("skin_material"));
    public static final RegistryKey<Registry<CharacterTextureMaterial>> EYE_MATERIAL = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("eye_material"));
    public static final RegistryKey<Registry<CharacterTextureMaterial>> HAIR_MATERIAL = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("hair_material"));

    public static final RegistryKey<Registry<CharacterTexturePattern>> SKIN_PATTERN = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("skin_pattern"));
    public static final RegistryKey<Registry<CharacterTexturePattern>> EYE_PATTERN = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("eye_pattern"));
    public static final RegistryKey<Registry<CharacterTexturePattern>> HAIR_PATTERN = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("hair_pattern"));

    public static final RegistryKey<Registry<SpiderVariant>> SPIDER_VARIANT = RegistryKey.ofRegistry(IdentifierUtil.buildAggregate("spider_variant"));

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Entries for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(RACE, Race.CODEC);
        DynamicRegistries.registerSynced(NPC, NpcData.CODEC);
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BIOME_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(SPIDER_VARIANT, SpiderVariant.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC);
        DynamicRegistries.registerSynced(TEXTURE_PRESETS, TexturePresetDatas.CODEC);

        DynamicRegistries.registerSynced(SKIN_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(HAIR_PATTERN, CharacterTexturePattern.CODEC);

        DynamicRegistries.registerSynced(SKIN_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(EYE_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(HAIR_MATERIAL, CharacterTextureMaterial.CODEC);
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RACE, RaceRegistry::bootstrap);
        registryBuilder.addRegistry(NPC, NpcRegistry::bootstrap);
        registryBuilder.addRegistry(FACTION, FactionRegistry::bootstrap);
        registryBuilder.addRegistry(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.addRegistry(SPIDER_VARIANT, SpiderVariantRegistry::bootstrap);
        registryBuilder.addRegistry(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);
        registryBuilder.addRegistry(TEXTURE_PRESETS, TexturePresetsRegistry::bootstrap);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpiderVariantsProvider::new);
        pack.addProvider(RaceProvider::new);
        pack.addProvider(TexturePresetsProvider::new);
        pack.addProvider(NpcProvider::new);
        pack.addProvider(FactionProvider::new);
        pack.addProvider(StructureDataProvider::new);
        pack.addProvider(BiomeEventProvider::new);
    }
}