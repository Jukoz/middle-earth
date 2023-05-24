package net.jesteur.me.events;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;

/**
 * ModEvents are only for the few built-in events provided by Fabric
 * For other "events", check out the Mixins.
 */
public class ModEvents {
    public static void register() {
        EntitySleepEvents.START_SLEEPING.register(new OnPlayerSleepEvent());
    }
}
