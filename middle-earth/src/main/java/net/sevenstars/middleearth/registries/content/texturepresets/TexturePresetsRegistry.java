package net.sevenstars.middleearth.registries.content.texturepresets;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.GenericTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.brigand.BrigandTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.dale.DaleTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.gondor.GondorTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.GundabadTexturePresetsPool.GundabadTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.HobgoblinTribesTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.LongbeardsTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.erebor.EreborTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien.LothlorienTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.mordor.MordorTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.rohan.RohanTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.shire.ShireTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.wildgoblin.WildGoblinTexturePresetsPool;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TexturePresetsRegistry {
    private static final RegistryKey<Registry<TexturePresets>> NPC_TEXTURE_DATA_KEY = DynamicRegistriesME.TEXTURE_PRESETS;

    /* [GENERIC] */
    public final static RegistryKey<TexturePresets> GENERIC_HUMAN       = of("generic_human");

    /* [BRIGAND] */
    public final static RegistryKey<TexturePresets> BRIGAND_THUG        = of(FactionRegistry.BRIGAND, "thug");
    public final static RegistryKey<TexturePresets> BRIGAND_MERCENARY   = of(FactionRegistry.BRIGAND, "mercenary");
    public final static RegistryKey<TexturePresets> BRIGAND_CHIEF       = of(FactionRegistry.BRIGAND, "chief");

    /* [WILD GOBLINS] */
    public final static RegistryKey<TexturePresets> WILD_GOBLIN_WEAK    = of(FactionRegistry.WILD_GOBLINS, "weak");
    public final static RegistryKey<TexturePresets> WILD_GOBLIN_WARRIOR = of(FactionRegistry.WILD_GOBLINS, "warrior");
    public final static RegistryKey<TexturePresets> WILD_GOBLIN_BRUTE   = of(FactionRegistry.WILD_GOBLINS, "brute");

    /* [DALE] */
    public final static RegistryKey<TexturePresets> DALE_PEASANT        = of(FactionRegistry.DALE, "peasant");
    public final static RegistryKey<TexturePresets> DALE_SOLDIER        = of(FactionRegistry.DALE, "soldier");
    public final static RegistryKey<TexturePresets> DALE_LORD           = of(FactionRegistry.DALE, "lord");


    /* [GONDOR] */
    public final static RegistryKey<TexturePresets> GONDOR_PEASANT        = of(FactionRegistry.GONDOR, "peasant");
    public final static RegistryKey<TexturePresets> GONDOR_SOLDIER        = of(FactionRegistry.GONDOR, "soldier");
    public final static RegistryKey<TexturePresets> GONDOR_LORD           = of(FactionRegistry.GONDOR, "lord");

    /* [ROHAN] */
    public final static RegistryKey<TexturePresets> ROHAN_PEASANT        = of(FactionRegistry.ROHAN, "peasant");
    public final static RegistryKey<TexturePresets> ROHAN_SOLDIER        = of(FactionRegistry.ROHAN, "soldier");
    public final static RegistryKey<TexturePresets> ROHAN_LORD           = of(FactionRegistry.ROHAN, "lord");

    /* [SHIRE] */
    public final static RegistryKey<TexturePresets> SHIRE_PEASANT        = of(FactionRegistry.SHIRE, "peasant");
    public final static RegistryKey<TexturePresets> SHIRE_MILITIA        = of(FactionRegistry.SHIRE, "militia");
    public final static RegistryKey<TexturePresets> SHIRE_SHIRRIFF       = of(FactionRegistry.SHIRE, "shirriff");

    /* [LONGBEARDS.EREBOR] */
    public final static RegistryKey<TexturePresets> LONGBEARDS_EREBOR_DWARF         = of(FactionRegistry.LONGBEARDS_EREBOR, "dwarf");
    public final static RegistryKey<TexturePresets> LONGBEARDS_EREBOR_DWARF_SOLDIER = of(FactionRegistry.LONGBEARDS_EREBOR, "dwarf_soldier");
    public final static RegistryKey<TexturePresets> LONGBEARDS_EREBOR_MIGHTY_DWARF  = of(FactionRegistry.LONGBEARDS_EREBOR, "mighty_dwarf");

    /* [LOTHLORIEN] */
    public final static RegistryKey<TexturePresets> LOTHLORIEN_ELF      = of(FactionRegistry.LOTHLORIEN, "elf");
    public final static RegistryKey<TexturePresets> LOTHLORIEN_LORD     = of(FactionRegistry.LOTHLORIEN, "lord");

    /* [MORDOR] */
    public final static RegistryKey<TexturePresets> MORDOR_BLACK_NUMENOREAN     = of(FactionRegistry.MORDOR, "black_numenorean");
    public final static RegistryKey<TexturePresets> MORDOR_ORC                  = of(FactionRegistry.MORDOR, "orc");
    public final static RegistryKey<TexturePresets> MORDOR_BLACK_URUK           = of(FactionRegistry.MORDOR, "black_uruk");

    /* [HOBGOBLIN TRIBES.GUNDABAD] */
    public final static RegistryKey<TexturePresets> GUNDABAD_GOBLIN         = of(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "goblin");
    public final static RegistryKey<TexturePresets> GUNDABAD_HOBGOBLIN      = of(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "hobgoblin");

    /* [ISENGARD] */
    public final static RegistryKey<TexturePresets> ISENGARD_ORC = of("isengard_orc");
    public final static RegistryKey<TexturePresets> ISENGARD_URUK_HAI = of("isengard_uruk_hai");



    public static void bootstrap(Registerable<TexturePresets> context) {
        RegistryEntryLookup<TexturePresets> registryEntryLookup = context.getRegistryLookup(NPC_TEXTURE_DATA_KEY);

        /* [ADD ALL POOLS BELOW] */
        registerAll(context, registryEntryLookup, GenericTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, BrigandTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, WildGoblinTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, LongbeardsTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, EreborTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, DaleTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, GondorTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, RohanTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, ShireTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, LothlorienTexturePresetsPool.fetchAll());

        registerAll(context, registryEntryLookup, MordorTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, HobgoblinTribesTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, GundabadTexturePresetsPool.fetchAll());
    }

    private static void registerAll(Registerable<TexturePresets> context, RegistryEntryLookup<TexturePresets> registryEntryLookup, List<RegisterableNpcTextureData> npcTextureDatas) {
        for(RegisterableNpcTextureData registerable : npcTextureDatas){
            register(context, registryEntryLookup, registerable.npcTextureDataRegistryKey, registerable.content);
        }
    }

    private static void register(Registerable<TexturePresets> context, RegistryEntryLookup<TexturePresets> registryEntryLookup, RegistryKey<TexturePresets> registryKey, TexturePresets element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // N/A
    }

    private static RegistryKey<TexturePresets> of(String... names) {
        return RegistryKey.of(DynamicRegistriesME.TEXTURE_PRESETS, IdentifierUtil.buildAggregate(names));
    }

    private static RegistryKey<TexturePresets> of(RegistryKey<Faction> base, String... names) {

        ArrayList<String> aggregateNames = new ArrayList<String>();
        aggregateNames.add(base.getValue().getPath());
        aggregateNames.addAll(Arrays.stream(names).toList());

        String[] stringArray = aggregateNames.toArray(new String[0]);
        return RegistryKey.of(DynamicRegistriesME.TEXTURE_PRESETS, IdentifierUtil.buildAggregate(stringArray));
    }

    public record RegisterableNpcTextureData (RegistryKey<TexturePresets> npcTextureDataRegistryKey, TexturePresets content){ }
}
