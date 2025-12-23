package net.sevenstars.middleearth.registries.content.npctexturedatas;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctexturedatas.pools.GenericNpcTextureDataPool;
import net.sevenstars.middleearth.registries.content.npctexturedatas.pools.dale.DaleNpcTextureDataPool;
import net.sevenstars.middleearth.registries.content.npctexturedatas.pools.longbeards.LongbeardsNpcTextureDataPool;
import net.sevenstars.middleearth.registries.content.npctexturedatas.pools.longbeards.erebor.EreborNpcTextureDataPool;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.apache.logging.log4j.util.Cast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NpcTextureDataRegistry {
    private static final RegistryKey<Registry<NpcTextureData>> NPC_TEXTURE_DATA_KEY = DynamicRegistriesME.NPC_TEXTURE_DATA;

    /* [GENERIC] */
    public final static RegistryKey<NpcTextureData> GENERIC_HUMAN = of("generic_human");

    /* [DALE] */
    public final static RegistryKey<NpcTextureData> DALE_PEASANT = of(FactionRegistry.DALE, "peasant");
    public final static RegistryKey<NpcTextureData> DALE_SOLDIER = of(FactionRegistry.DALE, "soldier");
    public final static RegistryKey<NpcTextureData> DALE_LORD = of(FactionRegistry.DALE, "lord");

    /* [LONGBEARDS.EREBOR] */
    public final static RegistryKey<NpcTextureData> LONGBEARDS_EREBOR_DWARF         = of(FactionRegistry.LONGBEARDS_EREBOR, "dwarf");
    public final static RegistryKey<NpcTextureData> LONGBEARDS_EREBOR_DWARF_SOLDIER = of(FactionRegistry.LONGBEARDS_EREBOR, "dwarf_soldier");
    public final static RegistryKey<NpcTextureData> LONGBEARDS_EREBOR_MIGHTY_DWARF  = of(FactionRegistry.LONGBEARDS_EREBOR, "mighty_dwarf");

    /* [LOTHLORIEN] */
    public final static RegistryKey<NpcTextureData> LORIEN_ELF = of("lothlorien_elf");

    /* [MORDOR] */
    public final static RegistryKey<NpcTextureData> MORDOR_ORC = of("mordor_orc");
    public final static RegistryKey<NpcTextureData> MORDOR_BLACK_URUK = of("mordor_black_uruk");

    /* [HOBGOBLIN TRIBES] */
    public final static RegistryKey<NpcTextureData> HOBGOBLIN_TRIBES_GOBLIN = of("hobgoblin_tribes_goblin");
    public final static RegistryKey<NpcTextureData> HOBGOBLIN_TRIBES_HOBGOBLIN = of("hobgoblin_tribes_hobgoblin");

    /* [ISENGARD] */
    public final static RegistryKey<NpcTextureData> ISENGARD_ORC = of("isengard_orc");
    public final static RegistryKey<NpcTextureData> ISENGARD_URUK_HAI = of("isengard_uruk_hai");

    /* [WILD GOBLINS] */
    public final static RegistryKey<NpcTextureData> WILD_GOBLIN_GOBLIN = of("wild_goblin_goblin");
    public final static RegistryKey<NpcTextureData> WILD_GOBLIN_HOBGOBLIN = of("wild_goblin_hobgoblin");

    public static void bootstrap(Registerable<NpcTextureData> context) {
        RegistryEntryLookup<NpcTextureData> registryEntryLookup = context.getRegistryLookup(NPC_TEXTURE_DATA_KEY);

        registerAll(context, registryEntryLookup, GenericNpcTextureDataPool.fetchAll());
        registerAll(context, registryEntryLookup, EreborNpcTextureDataPool.fetchAll());
        registerAll(context, registryEntryLookup, LongbeardsNpcTextureDataPool.fetchAll());
        registerAll(context, registryEntryLookup, DaleNpcTextureDataPool.fetchAll());
    }

    private static void registerAll(Registerable<NpcTextureData> context, RegistryEntryLookup<NpcTextureData> registryEntryLookup, List<RegisterableNpcTextureData> npcTextureDatas) {
        for(RegisterableNpcTextureData registerable : npcTextureDatas){
            register(context, registryEntryLookup, registerable.npcTextureDataRegistryKey, registerable.content);
        }
    }

    private static void register(Registerable<NpcTextureData> context, RegistryEntryLookup<NpcTextureData> registryEntryLookup, RegistryKey<NpcTextureData> registryKey, NpcTextureData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // N/A
    }

    private static RegistryKey<NpcTextureData> of(String... names) {
        return RegistryKey.of(DynamicRegistriesME.NPC_TEXTURE_DATA, IdentifierUtil.buildAggregate(names));
    }

    private static RegistryKey<NpcTextureData> of(RegistryKey<Faction> base, String... names) {

        ArrayList<String> aggregateNames = new ArrayList<String>();
        aggregateNames.add(base.getValue().getPath());
        aggregateNames.addAll(Arrays.stream(names).toList());

        String[] stringArray = aggregateNames.toArray(new String[0]);
        return RegistryKey.of(DynamicRegistriesME.NPC_TEXTURE_DATA, IdentifierUtil.buildAggregate(stringArray));
    }

    public record RegisterableNpcTextureData (RegistryKey<NpcTextureData> npcTextureDataRegistryKey, NpcTextureData content){ }
}
