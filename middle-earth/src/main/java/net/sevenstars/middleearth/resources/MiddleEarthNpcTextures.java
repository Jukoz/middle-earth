package net.sevenstars.middleearth.resources;

public class MiddleEarthNpcTextures {
/*
    public final static String PATH = "npc_skin_textures";
    private static final String ASSET_PATH = "textures/npc_skin_textures/";

    public static final RegistryKey<Registry<NpcTexture>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public final static NpcTexture SKIN_TYPE_A;


    public static void bootstrap(Registerable<NpcTexture> context) {
        RegistryEntryLookup<NpcTexture> entryLookup = context.getRegistryLookup(KEY);
        // Registering all races
        register(context, entryLookup, SKIN_TYPE_A);
    }

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Npc Textures for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, NpcTexture.CODEC);
    }

    private static NpcTexture register(Registerable<NpcTexture> context, RegistryEntryLookup<NpcTexture> entryLookup, NpcTexture npcTexture) {
        RegistryKey<NpcTexture> registryKey = of(npcTexture.getIdentifier().getPath());
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTexture> newNpcTexture = RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcTexture>> optionalRace = entryLookup.getOptional(registryKey);
        optionalRace.ifPresent(raceReference -> context.register(newNpcTexture, npcTexture));

        return npcTexture;
    }
    private static RegistryKey<NpcTexture> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    static {
        SKIN_TYPE_A = new NpcTexture(MiddleEarthNpcTextureMaterials.SKIN_PALE, MiddleEarthNpcTexturePatterns.SKIN_COMMON);
    }
 */
}
