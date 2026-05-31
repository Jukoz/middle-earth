package net.sevenstars.middleearth.registries.content.texturepresets;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.GenericTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.brigand.BrigandTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.dale.DaleTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.gondor.GondorTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.GundabadTexturePresetsPool.GundabadTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.hobgoblintribes.HobgoblinTribesTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.isengard.IsengardTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.LongbeardsTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.longbeards.erebor.EreborTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.lothlorien.LothlorienTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.mordor.MordorTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.rohan.RohanTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.shire.ShireTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.wildgoblin.WildGoblinTexturePresetsPool;
import net.sevenstars.middleearth.registries.content.texturepresets.pools.woodlandrealm.WoodlandRealmTexturePresetsPool;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TexturePresetsRegistry {
    private static final RegistryKey<Registry<TexturePresetDataPool>> NPC_TEXTURE_DATA_KEY = DynamicRegistriesME.TEXTURE_PRESETS;

    /* [GENERIC] */
    public final static RegistryKey<TexturePresetDataPool> GENERIC_HUMAN       = of("generic_human");

    /* [BRIGAND] */
    public final static RegistryKey<TexturePresetDataPool> BRIGAND_THUG        = of(FactionRegistry.BRIGAND, "thug");
    public final static RegistryKey<TexturePresetDataPool> BRIGAND_MERCENARY   = of(FactionRegistry.BRIGAND, "mercenary");
    public final static RegistryKey<TexturePresetDataPool> BRIGAND_CHIEF       = of(FactionRegistry.BRIGAND, "chief");

    /* [WILD GOBLINS] */
    public final static RegistryKey<TexturePresetDataPool> WILD_GOBLIN_WEAK    = of(FactionRegistry.WILD_GOBLINS, "weak");
    public final static RegistryKey<TexturePresetDataPool> WILD_GOBLIN_WARRIOR = of(FactionRegistry.WILD_GOBLINS, "warrior");
    public final static RegistryKey<TexturePresetDataPool> WILD_GOBLIN_BRUTE   = of(FactionRegistry.WILD_GOBLINS, "brute");

    /* [DALE] */
    public final static RegistryKey<TexturePresetDataPool> DALE_PEASANT        = of(FactionRegistry.DALE, "peasant");
    public final static RegistryKey<TexturePresetDataPool> DALE_SOLDIER        = of(FactionRegistry.DALE, "soldier");
    public final static RegistryKey<TexturePresetDataPool> DALE_LORD           = of(FactionRegistry.DALE, "lord");


    /* [GONDOR] */
    public final static RegistryKey<TexturePresetDataPool> GONDOR_PEASANT        = of(FactionRegistry.GONDOR, "peasant");
    public final static RegistryKey<TexturePresetDataPool> GONDOR_SOLDIER        = of(FactionRegistry.GONDOR, "soldier");
    public final static RegistryKey<TexturePresetDataPool> GONDOR_LORD           = of(FactionRegistry.GONDOR, "lord");

    /* [ROHAN] */
    public final static RegistryKey<TexturePresetDataPool> ROHAN_PEASANT        = of(FactionRegistry.ROHAN, "peasant");
    public final static RegistryKey<TexturePresetDataPool> ROHAN_SOLDIER        = of(FactionRegistry.ROHAN, "soldier");
    public final static RegistryKey<TexturePresetDataPool> ROHAN_LORD           = of(FactionRegistry.ROHAN, "lord");

    /* [SHIRE] */
    public final static RegistryKey<TexturePresetDataPool> SHIRE_PEASANT        = of(FactionRegistry.SHIRE, "peasant");
    public final static RegistryKey<TexturePresetDataPool> SHIRE_MILITIA        = of(FactionRegistry.SHIRE, "militia");
    public final static RegistryKey<TexturePresetDataPool> SHIRE_SHIRRIFF       = of(FactionRegistry.SHIRE, "shirriff");

    /* [LONGBEARDS.EREBOR] */
    public final static RegistryKey<TexturePresetDataPool> LONGBEARDS_EREBOR_DWARF         = of(FactionRegistry.LONGBEARDS_EREBOR, "dwarf");
    public final static RegistryKey<TexturePresetDataPool> LONGBEARDS_EREBOR_DWARF_SOLDIER = of(FactionRegistry.LONGBEARDS_EREBOR, "dwarf_soldier");
    public final static RegistryKey<TexturePresetDataPool> LONGBEARDS_EREBOR_MIGHTY_DWARF  = of(FactionRegistry.LONGBEARDS_EREBOR, "mighty_dwarf");

    /* [LOTHLORIEN] */
    public final static RegistryKey<TexturePresetDataPool> LOTHLORIEN_ELF      = of(FactionRegistry.LOTHLORIEN, "elf");
    public final static RegistryKey<TexturePresetDataPool> LOTHLORIEN_LORD     = of(FactionRegistry.LOTHLORIEN, "lord");

    /* [WOODLAND_REALMS] */
    public final static RegistryKey<TexturePresetDataPool> WOODLAND_REALM_ELF = of(FactionRegistry.WOODLAND_REALM, "elf");
    public final static RegistryKey<TexturePresetDataPool> WOODLAND_REALM_SENTINEL = of(FactionRegistry.WOODLAND_REALM, "sentinel");
    public final static RegistryKey<TexturePresetDataPool> WOODLAND_REALM_NIGHTSHADE = of(FactionRegistry.WOODLAND_REALM, "nightshade");
    public final static RegistryKey<TexturePresetDataPool> WOODLAND_REALM_COMMANDER = of(FactionRegistry.WOODLAND_REALM, "commander");
    public final static RegistryKey<TexturePresetDataPool> WOODLAND_REALM_WARDEN_OF_THE_GLADE = of(FactionRegistry.WOODLAND_REALM, "warden_of_the_glade");


    /* [MORDOR] */
    public final static RegistryKey<TexturePresetDataPool> MORDOR_BLACK_NUMENOREAN     = of(FactionRegistry.MORDOR, "black_numenorean");
    public final static RegistryKey<TexturePresetDataPool> MORDOR_ORC                  = of(FactionRegistry.MORDOR, "orc");
    public final static RegistryKey<TexturePresetDataPool> MORDOR_BLACK_URUK           = of(FactionRegistry.MORDOR, "black_uruk");

    /* [HOBGOBLIN TRIBES.GUNDABAD] */
    public final static RegistryKey<TexturePresetDataPool> GUNDABAD_GOBLIN         = of(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "goblin");
    public final static RegistryKey<TexturePresetDataPool> GUNDABAD_HOBGOBLIN      = of(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, "hobgoblin");

    /* [ISENGARD] */
    public final static RegistryKey<TexturePresetDataPool> ISENGARD_ORC = of(FactionRegistry.ISENGARD, "orc");
    public final static RegistryKey<TexturePresetDataPool> ISENGARD_URUK_HAI = of(FactionRegistry.ISENGARD, "uruk_hai");
    public final static RegistryKey<TexturePresetDataPool> ISENGARD_HUMAN = of(FactionRegistry.ISENGARD, "human");


    public static void bootstrap(Registerable<TexturePresetDataPool> context) {
        RegistryEntryLookup<TexturePresetDataPool> registryEntryLookup = context.getRegistryLookup(NPC_TEXTURE_DATA_KEY);

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
        registerAll(context, registryEntryLookup, WoodlandRealmTexturePresetsPool.fetchAll());

        registerAll(context, registryEntryLookup, MordorTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, HobgoblinTribesTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, GundabadTexturePresetsPool.fetchAll());
        registerAll(context, registryEntryLookup, IsengardTexturePresetsPool.fetchAll());
    }

    private static void registerAll(Registerable<TexturePresetDataPool> context, RegistryEntryLookup<TexturePresetDataPool> registryEntryLookup, List<RegisterableNpcTextureData> npcTextureDatas) {
        for(RegisterableNpcTextureData registerable : npcTextureDatas){
            register(context, registryEntryLookup, registerable.npcTextureDataRegistryKey, registerable.content);
        }
    }

    private static void register(Registerable<TexturePresetDataPool> context, RegistryEntryLookup<TexturePresetDataPool> registryEntryLookup, RegistryKey<TexturePresetDataPool> registryKey, TexturePresetDataPool element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // N/A
    }

    private static RegistryKey<TexturePresetDataPool> of(String... names) {
        return RegistryKey.of(DynamicRegistriesME.TEXTURE_PRESETS, MiddleEarth.of(names));
    }

    private static RegistryKey<TexturePresetDataPool> of(RegistryKey<Faction> base, String... names) {

        ArrayList<String> aggregateNames = new ArrayList<String>();
        aggregateNames.add(base.getValue().getPath());
        aggregateNames.addAll(Arrays.stream(names).toList());

        String[] stringArray = aggregateNames.toArray(new String[0]);
        return RegistryKey.of(DynamicRegistriesME.TEXTURE_PRESETS, MiddleEarth.of(stringArray));
    }

    public record RegisterableNpcTextureData (RegistryKey<TexturePresetDataPool> npcTextureDataRegistryKey, TexturePresetDataPool content){ }
}
