package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.Optional;

public class MiddleEarthNpcTextureMaterials {
    public final static String SKIN_PATH = "npc_skin_material";
    public final static String EYE_PATH = "npc_eye_material";
    public final static String HAIR_PATH = "npc_hair_material";
    public final static String CLOTHING_PATH = "npc_clothing_material";

    public static final RegistryKey<Registry<NpcTextureMaterial>> SKIN_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, SKIN_PATH));
    public static final RegistryKey<Registry<NpcTextureMaterial>> EYE_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, EYE_PATH));
    public static final RegistryKey<Registry<NpcTextureMaterial>> HAIR_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, HAIR_PATH));
    public static final RegistryKey<Registry<NpcTextureMaterial>> CLOTHING_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, CLOTHING_PATH));

    public final static RegistryKey<NpcTextureMaterial> SKIN_PALE = of("pale", NpcTextureType.SKIN);
    public final static RegistryKey<NpcTextureMaterial> SKIN_TAN = of("tan", NpcTextureType.SKIN);
    public final static RegistryKey<NpcTextureMaterial> SKIN_NEUTRAL = of("neutral", NpcTextureType.SKIN);
    public final static RegistryKey<NpcTextureMaterial> SKIN_OLIVE = of("olive", NpcTextureType.SKIN);


    public final static RegistryKey<NpcTextureMaterial> EYE_BROWN = of("brown", NpcTextureType.EYE);
    public final static RegistryKey<NpcTextureMaterial> EYE_BLUE = of("blue", NpcTextureType.EYE);
    public final static RegistryKey<NpcTextureMaterial> EYE_GREEN = of("green", NpcTextureType.EYE);
    public final static RegistryKey<NpcTextureMaterial> EYE_YELLOW = of("yellow", NpcTextureType.EYE);


    public final static RegistryKey<NpcTextureMaterial> HAIR_BLACK = of("black", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_BROWN = of("brown", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_DARK_BROWN = of("dark_brown", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_BLONDE = of("blonde", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_STRAW = of("straw", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_WHITE = of("white", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_GRAY = of("gray", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTextureMaterial> HAIR_ORANGE = of("orange", NpcTextureType.HAIR);

    public final static RegistryKey<NpcTextureMaterial> CLOTHING_WHITE = of("white", NpcTextureType.CLOTHING);
    public final static RegistryKey<NpcTextureMaterial> CLOTHING_BROWN = of("brown", NpcTextureType.CLOTHING);

    public static void bootstrapSkins(Registerable<NpcTextureMaterial> registry) {
        RegistryEntryLookup<NpcTextureMaterial> skinEntryLookup = registry.getRegistryLookup(SKIN_KEY);
        register(registry, skinEntryLookup,  "tan", NpcTextureType.SKIN);
        register(registry, skinEntryLookup,"pale", NpcTextureType.SKIN);
        register(registry, skinEntryLookup,"neutral", NpcTextureType.SKIN);
        register(registry, skinEntryLookup,"olive", NpcTextureType.SKIN);
    }
    public static void bootstrapEyes(Registerable<NpcTextureMaterial> registry) {
        RegistryEntryLookup<NpcTextureMaterial> eyeEntryLookup = registry.getRegistryLookup(EYE_KEY);
        register(registry, eyeEntryLookup,"brown", NpcTextureType.EYE);
        register(registry, eyeEntryLookup,"blue", NpcTextureType.EYE);
        register(registry, eyeEntryLookup,"green", NpcTextureType.EYE);
        register(registry, eyeEntryLookup,"yellow", NpcTextureType.EYE);

    }
    public static void bootstrapHairs(Registerable<NpcTextureMaterial> registry) {
        RegistryEntryLookup<NpcTextureMaterial> hairEntryLookup = registry.getRegistryLookup(HAIR_KEY);
        register(registry, hairEntryLookup,"black", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"brown", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"dark_brown", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"blonde", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"straw", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"orange", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"white", NpcTextureType.HAIR);
        register(registry, hairEntryLookup,"gray", NpcTextureType.HAIR);
    }
    public static void bootstrapClothings(Registerable<NpcTextureMaterial> registry) {
        RegistryEntryLookup<NpcTextureMaterial> clothingEntryLookup = registry.getRegistryLookup(CLOTHING_KEY);
        register(registry, clothingEntryLookup,"white", NpcTextureType.CLOTHING);
        register(registry, clothingEntryLookup,"brown", NpcTextureType.CLOTHING);
    }

    public static Optional<RegistryEntry.Reference<NpcTextureMaterial>> get(RegistryWrapper.WrapperLookup registries, Identifier id) {
        return registries.getOrThrow(SKIN_KEY).streamEntries().filter((recipe) -> id == recipe.value().getIdentifier()).findFirst();
    }

    private static void register(Registerable<NpcTextureMaterial> registry, RegistryEntryLookup<NpcTextureMaterial> entryLookup, String id, NpcTextureType type) {
        var registryKey = switch (type) {
            case NpcTextureType.SKIN -> SKIN_KEY;
            case NpcTextureType.EYE -> EYE_KEY;
            case NpcTextureType.HAIR -> HAIR_KEY;
            case NpcTextureType.CLOTHING -> CLOTHING_KEY;
            default -> null;
        };

        register(registry, entryLookup, create(id, type), of(id, type), registryKey);
    }

    private static NpcTextureMaterial create(String id, NpcTextureType type) {
        return new NpcTextureMaterial(Identifier.of(MiddleEarth.MOD_ID, id), type);
    }
    private static RegistryKey<NpcTextureMaterial> of(String id, NpcTextureType type) {
        return switch (type) {
            case NpcTextureType.SKIN -> RegistryKey.of(SKIN_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.EYE -> RegistryKey.of(EYE_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.HAIR -> RegistryKey.of(HAIR_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.CLOTHING -> RegistryKey.of(CLOTHING_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
        };
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Npc Texture Materials for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(SKIN_KEY, NpcTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(EYE_KEY, NpcTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(HAIR_KEY, NpcTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(CLOTHING_KEY, NpcTextureMaterial.CODEC);
    }


    private static void register(Registerable<NpcTextureMaterial> registerable, RegistryEntryLookup<NpcTextureMaterial> entryLookup, NpcTextureMaterial content, RegistryKey<NpcTextureMaterial> registryKey, RegistryKey<Registry<NpcTextureMaterial>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTextureMaterial> key = RegistryKey.of(registryRegistryKey, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcTextureMaterial>> optionalFaction = entryLookup.getOptional(registryKey);
        optionalFaction.ifPresent(reference -> registerable.register(key, content));
    }
}
