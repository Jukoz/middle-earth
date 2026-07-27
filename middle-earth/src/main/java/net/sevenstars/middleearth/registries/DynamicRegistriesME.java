package net.sevenstars.middleearth.registries;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.registries.content.greathornvariants.GreatHornVariantRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

public class DynamicRegistriesME extends net.sevenstars.api.registries.DynamicRegistries {
    public static final ResourceKey<Registry<Race>> RACE = ResourceKey.createRegistryKey(MiddleEarth.of("race"));
    public static final ResourceKey<Registry<Faction>> FACTION = ResourceKey.createRegistryKey(MiddleEarth.of("faction"));
    public static final ResourceKey<Registry<NpcType>> NPC_TYPE = ResourceKey.createRegistryKey(MiddleEarth.of("npc_type"));
    public static final Codec<Holder<NpcType>> NPC_TYPE_CODEC = RegistryFixedCodec.create(DynamicRegistriesME.NPC_TYPE);
    public static final ResourceKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA  = ResourceKey.createRegistryKey(MiddleEarth.of("structure_manager_data"));
    public static final ResourceKey<Registry<BiomeEventData>> BIOME_EVENT = ResourceKey.createRegistryKey(MiddleEarth.of("biome_event"));
    public static final ResourceKey<Registry<BiomeEventData>> STRUCTURE_EVENT = ResourceKey.createRegistryKey(MiddleEarth.of("structure_event"));

    public static final ResourceKey<Registry<TexturePresetDataPool>> TEXTURE_PRESETS = ResourceKey.createRegistryKey(MiddleEarth.of( "texture_presets"));

    public static final ResourceKey<Registry<CharacterTextureMaterial>> SKIN_MATERIAL = ResourceKey.createRegistryKey(MiddleEarth.of("skin_material"));
    public static final ResourceKey<Registry<CharacterTextureMaterial>> EYE_MATERIAL = ResourceKey.createRegistryKey(MiddleEarth.of("eye_material"));
    public static final ResourceKey<Registry<CharacterTextureMaterial>> HAIR_MATERIAL = ResourceKey.createRegistryKey(MiddleEarth.of("hair_material"));

    public static final ResourceKey<Registry<CharacterTexturePattern>> SKIN_PATTERN = ResourceKey.createRegistryKey(MiddleEarth.of("skin_pattern"));
    public static final ResourceKey<Registry<CharacterTexturePattern>> EYE_PATTERN = ResourceKey.createRegistryKey(MiddleEarth.of("eye_pattern"));
    public static final ResourceKey<Registry<CharacterTexturePattern>> HAIR_PATTERN = ResourceKey.createRegistryKey(MiddleEarth.of("hair_pattern"));

    public static final ResourceKey<Registry<SpiderVariant>> SPIDER_VARIANTS = ResourceKey.createRegistryKey(MiddleEarth.of("spider_variants"));
    public static final ResourceKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS = ResourceKey.createRegistryKey(MiddleEarth.of("great_horn_variants"));

    public static void register(DataPackRegistryEvent.NewRegistry event) {
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Entries for " + MiddleEarth.MOD_ID);
        event.dataPackRegistry(RACE, Race.CODEC, Race.CODEC);
        event.dataPackRegistry(NPC_TYPE, NpcType.CODEC, NpcType.CODEC);
        event.dataPackRegistry(FACTION, Faction.CODEC, Faction.CODEC);
        event.dataPackRegistry(BIOME_EVENT, BiomeEventData.CODEC, BiomeEventData.CODEC);
        event.dataPackRegistry(STRUCTURE_EVENT, BiomeEventData.CODEC, BiomeEventData.CODEC);
        event.dataPackRegistry(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC, StructureManagerData.CODEC);
        event.dataPackRegistry(TEXTURE_PRESETS, TexturePresetDataPool.CODEC, TexturePresetDataPool.CODEC);

        event.dataPackRegistry(SKIN_PATTERN, CharacterTexturePattern.CODEC, CharacterTexturePattern.CODEC);
        event.dataPackRegistry(EYE_PATTERN, CharacterTexturePattern.CODEC, CharacterTexturePattern.CODEC);
        event.dataPackRegistry(HAIR_PATTERN, CharacterTexturePattern.CODEC, CharacterTexturePattern.CODEC);

        event.dataPackRegistry(SKIN_MATERIAL, CharacterTextureMaterial.CODEC, CharacterTextureMaterial.CODEC);
        event.dataPackRegistry(EYE_MATERIAL, CharacterTextureMaterial.CODEC, CharacterTextureMaterial.CODEC);
        event.dataPackRegistry(HAIR_MATERIAL, CharacterTextureMaterial.CODEC, CharacterTextureMaterial.CODEC);

        event.dataPackRegistry(SPIDER_VARIANTS, SpiderVariant.CODEC, SpiderVariant.CODEC);
        event.dataPackRegistry(GREAT_HORN_VARIANTS, GreatHornVariant.CODEC, GreatHornVariant.CODEC);
    }

    public static void prepareBoostrap(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(RACE, RaceRegistry::bootstrap);
        registryBuilder.add(NPC_TYPE, NpcRegistry::bootstrap);
        registryBuilder.add(FACTION, FactionRegistry::bootstrap);
        registryBuilder.add(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.add(STRUCTURE_EVENT, BiomeEventRegistry::bootstrapStructureEvents);
        registryBuilder.add(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);
        registryBuilder.add(TEXTURE_PRESETS, TexturePresetsRegistry::bootstrap);

        registryBuilder.add(SPIDER_VARIANTS, SpiderVariantRegistry::bootstrap);
        registryBuilder.add(GREAT_HORN_VARIANTS, GreatHornVariantRegistry::bootstrap);
    }

}
