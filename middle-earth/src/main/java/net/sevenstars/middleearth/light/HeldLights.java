package net.sevenstars.middleearth.light;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import org.jetbrains.annotations.Nullable;

/**
 * Which items give off light in the off hand (or worn on the head), and how: the {@code middle-earth:held_light}
 * entries ({@link HeldLightData}), so a data pack can add any mod's lights. A mod may also register one in code
 * ({@link #register}); an entry for the same item wins. The light then follows its holder on every client and
 * server ({@link BeamLights}).
 * <p>
 * Each side keeps its own table, built from that side's registries whenever its tags load (the server's at start
 * and on {@code /reload}, a client's as it joins) and swapped in whole, so the server and render threads of one
 * game never share a table being built. Look a light up with the world (or side) it is for.
 * <p>
 * Torches and candles are always lit and flicker; lanterns and lamps are shaded and lit again by using them from
 * the off hand ({@link LanternToggling}).
 */
public final class HeldLights {
    /** Lights registered in code, by mods, while they start (before any world loads). */
    private static final Map<Item, HeldLight> CODE = new ConcurrentHashMap<>();

    /** One side's lights: carried in the off hand, and worn on the head, by item. */
    private record Table(Map<Item, HeldLightData> carried, Map<Item, HeldLightData> worn) {
        static final Table EMPTY = new Table(Map.of(), Map.of());
    }

    private static volatile Table server = Table.EMPTY;
    private static volatile Table client = Table.EMPTY;

    private HeldLights() {
    }

    /**
     * Gives {@code item} a light from code, for a mod that would rather not ship a data pack (call it while the
     * mod starts). It is held as Middle-earth's lanterns are; a {@code held_light} entry for the item wins.
     */
    public static void register(ItemConvertible item, HeldLight light) {
        CODE.put(item.asItem(), light);
    }

    /** Loads each side's lights from its registries as they come, and drops the server's when it stops. */
    public static void registerDefaults() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, isClient) -> load(registries, isClient));
        ServerLifecycleEvents.SERVER_STARTED.register(minecraft -> load(minecraft.getRegistryManager(), false));
        ServerLifecycleEvents.SERVER_STOPPED.register(minecraft -> clear(false));
    }

    /** Builds one side's table from its registries (the {@code held_light} entries and item tags) and swaps it in. */
    public static void load(DynamicRegistryManager registries, boolean isClient) {
        Map<Item, HeldLightData> carried = new IdentityHashMap<>();
        Map<Item, HeldLightData> worn = new IdentityHashMap<>();
        CODE.forEach((item, light) -> carried.put(item, new HeldLightData(List.of(Registries.ITEM.getId(item).toString()),
            HeldLightData.Slot.OFFHAND, light, HeldLightData.DEFAULT_GRIP, HeldLightData.DEFAULT_LENS, HeldLightData.Display.DEFAULT,
            Optional.empty(), Optional.empty()).resolved(placedLevel(item))));

        Optional<Registry<HeldLightData>> entries = registries.getOptional(DynamicRegistriesME.HELD_LIGHT);
        Registry<Item> itemRegistry = registries.getOrThrow(RegistryKeys.ITEM);
        if (entries.isPresent()) {
            // In order of their ids, so where two entries name one item, which wins never changes.
            List<Map.Entry<RegistryKey<HeldLightData>, HeldLightData>> sorted = new ArrayList<>(entries.get().getEntrySet());
            sorted.sort(Comparator.comparing(entry -> entry.getKey().getValue().toString()));
            for (Map.Entry<RegistryKey<HeldLightData>, HeldLightData> entry : sorted) {
                HeldLightData data = entry.getValue();
                Map<Item, HeldLightData> table = data.slot() == HeldLightData.Slot.HEAD ? worn : carried;
                for (Item item : items(entry.getKey().getValue(), data.items(), itemRegistry, !isClient)) {
                    table.put(item, data.resolved(placedLevel(item)));
                }
            }
        }
        Table built = new Table(Collections.unmodifiableMap(carried), Collections.unmodifiableMap(worn));
        if (isClient) {
            client = built;
        } else {
            server = built;
        }
    }

    /** Forgets one side's lights (the server stopped, or the client left its world). */
    public static void clear(boolean isClient) {
        if (isClient) {
            client = Table.EMPTY;
        } else {
            server = Table.EMPTY;
        }
    }

    /** The items an entry names: ids and tags, skipping (and, once per load, logging) those not there. */
    private static List<Item> items(Identifier entry, List<String> names, Registry<Item> registry, boolean log) {
        List<Item> items = new ArrayList<>();
        for (String name : names) {
            if (name.startsWith("#")) {
                TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, Identifier.of(name.substring(1)));
                if (registry.getOptional(tag).isEmpty()) {
                    if (log) {
                        MiddleEarth.LOGGER.logWarn("Held light " + entry + ": no item tag " + name + ", skipped");
                    }
                    continue;
                }
                for (RegistryEntry<Item> item : registry.iterateEntries(tag)) {
                    items.add(item.value());
                }
            } else {
                Optional<Item> item = registry.getOptionalValue(Identifier.of(name));
                if (item.isEmpty()) {
                    if (log) {
                        MiddleEarth.LOGGER.logWarn("Held light " + entry + ": no item " + name + ", skipped");
                    }
                    continue;
                }
                items.add(item.get());
            }
        }
        return items;
    }

    /** The light an item's block gives when placed and lit: its brightest state. */
    private static int placedLevel(Item item) {
        if (!(item instanceof BlockItem blockItem)) {
            return 0;
        }
        return blockItem.getBlock().getStateManager().getStates().stream().mapToInt(BlockState::getLuminance).max().orElse(0);
    }

    private static Table table(boolean isClient) {
        return isClient ? client : server;
    }

    /** This stack's carried light entry on the given side, or null. */
    public static @Nullable HeldLightData data(boolean isClient, ItemStack stack) {
        return stack == null || stack.isEmpty() ? null : table(isClient).carried().get(stack.getItem());
    }

    /** This stack's worn light entry on the given side, or null. */
    public static @Nullable HeldLightData wornData(boolean isClient, ItemStack stack) {
        return stack == null || stack.isEmpty() ? null : table(isClient).worn().get(stack.getItem());
    }

    /** The light this stack gives in the off hand, on the given side, or null. */
    public static @Nullable HeldLight of(boolean isClient, ItemStack stack) {
        HeldLightData data = data(isClient, stack);
        return data == null ? null : data.light();
    }

    public static @Nullable HeldLight of(World world, ItemStack stack) {
        return of(world.isClient, stack);
    }

    /** Whether a toggled lamp is switched on (lanterns are, until shaded). */
    public static boolean isOn(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypesME.LAMP_ON, true);
    }

    /** The light this stack gives off right now in the off hand, on the given side, or null. */
    public static @Nullable HeldLight active(boolean isClient, ItemStack stack) {
        HeldLight light = of(isClient, stack);
        if (light == null || (light.toggled() && !isOn(stack))) {
            return null;
        }
        return light;
    }

    public static @Nullable HeldLight active(World world, ItemStack stack) {
        return active(world.isClient, stack);
    }

    /** The light a player gives off right now: held in the off hand, or else worn; or null. */
    public static @Nullable HeldLight held(PlayerEntity player) {
        if (player.isSpectator()) {
            return null;
        }
        HeldLight light = active(player.getWorld(), player.getOffHandStack());
        return light != null ? light : worn(player);
    }

    /** A light worn on the head (the Dwarven Miner and Goblin Town Tunneler helmets' candles), or null. */
    public static @Nullable HeldLight worn(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        HeldLightData data = wornData(player.getWorld().isClient, head);
        if (data == null || (data.light().toggled() && !isOn(head))) {
            return null;
        }
        return data.light();
    }
}
