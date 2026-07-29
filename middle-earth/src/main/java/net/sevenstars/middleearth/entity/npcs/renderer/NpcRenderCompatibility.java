package net.sevenstars.middleearth.entity.npcs.renderer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;

@EventBusSubscriber(
        modid = MiddleEarth.NEOFORGE_MOD_ID,
        value = Dist.CLIENT,
        bus = EventBusSubscriber.Bus.MOD
)
public final class NpcRenderCompatibility {
    private static final String ACCELERATED_RENDERING_MOD_ID = "acceleratedrendering";
    private static final String ENTITY_TYPE_BLACKLIST_MESSAGE = "entity_type_blacklist";

    private NpcRenderCompatibility() {
    }

    @SubscribeEvent
    public static void enqueueInterModCompatibility(InterModEnqueueEvent event) {
        if (!ModList.get().isLoaded(ACCELERATED_RENDERING_MOD_ID)) {
            return;
        }

        InterModComms.sendTo(
                ACCELERATED_RENDERING_MOD_ID,
                ENTITY_TYPE_BLACKLIST_MESSAGE,
                () -> EntitiesME.NPC
        );
    }
}
