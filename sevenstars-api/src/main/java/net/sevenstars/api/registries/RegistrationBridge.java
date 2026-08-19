package net.sevenstars.api.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.sevenstars.api.SevenStarsApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class RegistrationBridge {
    private static final List<PendingRegistration<?>> PENDING = new ArrayList<>();
    private static final Set<ResourceKey<? extends Registry<?>>> FIRED_REGISTRIES = new HashSet<>();
    private static boolean attached;

    private RegistrationBridge() {
    }

    public static synchronized void attach(IEventBus modEventBus) {
        if (attached) {
            return;
        }
        attached = true;
        modEventBus.addListener(RegistrationBridge::onRegister);

        enableIntrusiveRegistration(BuiltInRegistries.BLOCK);
        enableIntrusiveRegistration(BuiltInRegistries.ITEM);
        enableIntrusiveRegistration(BuiltInRegistries.ENTITY_TYPE);
        enableIntrusiveRegistration(BuiltInRegistries.BLOCK_ENTITY_TYPE);
        enableIntrusiveRegistration(BuiltInRegistries.FLUID);
    }

    public static <V, T extends V> T register(Registry<V> registry, ResourceLocation id, T entry) {
        enqueue(registry.key(), id, entry);
        return entry;
    }

    public static <V, T extends V> T register(Registry<V> registry, String id, T entry) {
        return register(registry, ResourceLocation.parse(id), entry);
    }

    public static <V, T extends V> Holder<V> registerForHolder(
            Registry<V> registry, ResourceLocation id, T entry
    ) {
        enqueue(registry.key(), id, entry);
        return DeferredHolder.create(registry.key(), id);
    }

    private static synchronized <T> void enqueue(
            ResourceKey<? extends Registry<T>> registryKey, ResourceLocation id, T entry
    ) {
        ResourceKey<? extends Registry<?>> keyView = castRegistryKey(registryKey);
        if (!attached) {
            throw new IllegalStateException("Registration bridge is not attached while queuing " + id);
        }
        if (FIRED_REGISTRIES.contains(keyView)) {
            throw new IllegalStateException("Registry event already fired for " + registryKey + " while queuing " + id);
        }
        PENDING.add(new PendingRegistration<>(registryKey, id, entry));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static synchronized void onRegister(RegisterEvent event) {
        FIRED_REGISTRIES.add(event.getRegistryKey());
        Iterator<PendingRegistration<?>> iterator = PENDING.iterator();
        while (iterator.hasNext()) {
            PendingRegistration pending = iterator.next();
            if (pending.registryKey.equals(event.getRegistryKey())) {
                event.register(pending.registryKey, pending.id, () -> pending.entry);
                iterator.remove();
            }
        }
    }

    private static void enableIntrusiveRegistration(Registry<?> registry) {
        if (registry instanceof MappedRegistry<?> mappedRegistry) {
            mappedRegistry.unfreeze();
        } else {
            SevenStarsApi.LOGGER.logWarn("Could not enable intrusive registration for " + registry.key().location());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> ResourceKey<? extends Registry<?>> castRegistryKey(
            ResourceKey<? extends Registry<T>> registryKey
    ) {
        return (ResourceKey<? extends Registry<?>>) (ResourceKey<?>) registryKey;
    }

    private record PendingRegistration<T>(
            ResourceKey<? extends Registry<T>> registryKey, ResourceLocation id, T entry
    ) {
    }
}
