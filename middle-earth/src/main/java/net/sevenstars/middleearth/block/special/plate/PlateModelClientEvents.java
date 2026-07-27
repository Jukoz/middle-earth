package net.sevenstars.middleearth.block.special.plate;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.sevenstars.middleearth.MiddleEarth;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID, value = Dist.CLIENT)
public final class PlateModelClientEvents {
    private PlateModelClientEvents() {
    }

    @SubscribeEvent
    private static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        PlateFoodModels.plateModels.values().stream()
                .map(id -> id.withPrefix("item/"))
                .map(ModelResourceLocation::standalone)
                .distinct()
                .forEach(event::register);
    }
}
