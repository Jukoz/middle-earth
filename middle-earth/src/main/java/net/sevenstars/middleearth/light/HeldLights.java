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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ClientConfigME;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import org.jetbrains.annotations.Nullable;

/** Lookup of which items give off held or worn light, built per side from the {@code held_light} registry. */
public final class HeldLights {
    private static final Map<Item, HeldLight> CODE = new ConcurrentHashMap<>();

    private record Table(Map<Item, HeldLightData> carried, Map<Item, HeldLightData> worn) {
        static final Table EMPTY = new Table(Map.of(), Map.of());
    }

    private static volatile Table server = Table.EMPTY;
    private static volatile Table client = Table.EMPTY;

    private HeldLights() {
    }

    public static void register(ItemConvertible item, HeldLight light) {
        CODE.put(item.asItem(), light);
    }

    public static void registerDefaults() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, isClient) -> load(registries, isClient));
        ServerLifecycleEvents.SERVER_STARTED.register(minecraft -> load(minecraft.getRegistryManager(), false));
        ServerLifecycleEvents.SERVER_STOPPED.register(minecraft -> clear(false));
    }

    public static void load(DynamicRegistryManager registries, boolean isClient) {
        Map<Item, HeldLightData> carried = new IdentityHashMap<>();
        Map<Item, HeldLightData> worn = new IdentityHashMap<>();
        CODE.forEach((item, light) -> carried.put(item, new HeldLightData(List.of(Registries.ITEM.getId(item).toString()),
            HeldLightData.Slot.OFFHAND, light, HeldLightData.DEFAULT_GRIP, HeldLightData.DEFAULT_LENS, HeldLightData.Display.DEFAULT,
            Optional.empty(), Optional.empty()).resolved(placedLevel(item))));

        Optional<Registry<HeldLightData>> entries = registries.getOptional(DynamicRegistriesME.HELD_LIGHT);
        Registry<Item> itemRegistry = registries.getOrThrow(RegistryKeys.ITEM);
        if (entries.isPresent()) {
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

    public static void clear(boolean isClient) {
        if (isClient) {
            client = Table.EMPTY;
        } else {
            server = Table.EMPTY;
        }
    }

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

    private static int placedLevel(Item item) {
        if (!(item instanceof BlockItem blockItem)) {
            return 0;
        }
        return blockItem.getBlock().getStateManager().getStates().stream().mapToInt(BlockState::getLuminance).max().orElse(0);
    }

    private static Table table(boolean isClient) {
        return isClient ? client : server;
    }

    public static @Nullable HeldLightData data(boolean isClient, ItemStack stack) {
        return stack == null || stack.isEmpty() ? null : table(isClient).carried().get(stack.getItem());
    }

    public static @Nullable HeldLightData wornData(boolean isClient, ItemStack stack) {
        return stack == null || stack.isEmpty() ? null : table(isClient).worn().get(stack.getItem());
    }

    public static @Nullable HeldLight of(boolean isClient, ItemStack stack) {
        HeldLightData data = data(isClient, stack);
        return data == null ? null : data.light();
    }

    public static @Nullable HeldLight of(World world, ItemStack stack) {
        return of(world.isClient, stack);
    }

    public static boolean ambientLight(MinecraftServer server) {
        return ServerConfigME.ENABLE_HELD_AMBIENT_LIGHT && (server.isDedicated() || ClientConfigME.ENABLE_HELD_AMBIENT_LIGHT);
    }

    public static boolean isOn(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypesME.LAMP_ON, true);
    }

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

    public static @Nullable HeldLight held(PlayerEntity player) {
        if (player.isSpectator()) {
            return null;
        }
        HeldLight light = active(player.getWorld(), player.getOffHandStack());
        return light != null ? light : worn(player);
    }

    public static @Nullable HeldLight worn(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        HeldLightData data = wornData(player.getWorld().isClient, head);
        if (data == null || (data.light().toggled() && !isOn(head))) {
            return null;
        }
        return data.light();
    }
}
