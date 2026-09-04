package net.sevenstars.middleearth.registries;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
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
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.sevenstars.middleearth.registries.custom.DispositionRegistryME;
import net.sevenstars.middleearth.registries.custom.FactionRegistryME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

public class DynamicRegistriesME extends DynamicRegistriesAPI {
    public static final RegistryKey<Registry<Race>> RACE = RegistryKey.ofRegistry(MiddleEarth.id("race"));
    public static final RegistryKey<Registry<NpcType>> NPC_TYPE = RegistryKey.ofRegistry(MiddleEarth.id("npc_type"));
    public static final Codec<RegistryEntry<NpcType>> NPC_TYPE_CODEC = RegistryFixedCodec.of(DynamicRegistriesME.NPC_TYPE);
    public static final RegistryKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA  = RegistryKey.ofRegistry(MiddleEarth.id("structure_manager_data"));
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
        MiddleEarth.logRegistryMsg("Dynamic Registries");
        DynamicRegistries.registerSynced(RACE, Race.CODEC);
        DynamicRegistries.registerSynced(NPC_TYPE, NpcType.CODEC);
        DynamicRegistries.registerSynced(BIOME_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC);
        DynamicRegistries.registerSynced(TEXTURE_PRESETS, TexturePresetDataPool.CODEC);

        DynamicRegistries.registerSynced(SKIN_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(HAIR_PATTERN, CharacterTexturePattern.CODEC);

        DynamicRegistries.registerSynced(SKIN_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(EYE_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(HAIR_MATERIAL, CharacterTextureMaterial.CODEC);

        DynamicRegistries.registerSynced(SPIDER_VARIANTS, SpiderVariant.CODEC);
        DynamicRegistries.registerSynced(GREAT_HORN_VARIANTS, GreatHornVariant.CODEC);
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(DynamicRegistriesHH.FACTION, FactionRegistryME::bootstrap);
        registryBuilder.addRegistry(DynamicRegistriesHH.DISPOSITION, DispositionRegistryME::bootstrap);

        registryBuilder.addRegistry(RACE, RaceRegistry::bootstrap);
        //registryBuilder.addRegistry(NPC_TYPE, NpcRegistry::bootstrap);
        registryBuilder.addRegistry(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.addRegistry(STRUCTURE_EVENT, BiomeEventRegistry::bootstrapStructureEvents);
        registryBuilder.addRegistry(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);
        registryBuilder.addRegistry(TEXTURE_PRESETS, TexturePresetsRegistry::bootstrap);

        registryBuilder.addRegistry(SPIDER_VARIANTS, SpiderVariantRegistry::bootstrap);
        registryBuilder.addRegistry(GREAT_HORN_VARIANTS, GreatHornVariantRegistry::bootstrap);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(DispositionProvider::new);
        pack.addProvider(FactionProvider::new);

        pack.addProvider(SpiderVariantsProvider::new);
        pack.addProvider(RaceProvider::new);
        pack.addProvider(TexturePresetsProvider::new);
        pack.addProvider(NpcProvider::new);
        pack.addProvider(StructureDataProvider::new);
        pack.addProvider(BiomeEventProvider::new);
        pack.addProvider(StructureEventProvider::new);
        pack.addProvider(GreatHornVariantsProvider::new);
    }
}