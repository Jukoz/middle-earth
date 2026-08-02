package net.sevenstars.middleearth.block.special.plate;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID, value = Dist.CLIENT)
public final class PlateModelClientEvents {
    private static volatile Map<ResourceLocation, BakedModel> BAKED_MODELS = Map.of();

    private PlateModelClientEvents() {
    }

    @SubscribeEvent
    private static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        Set<ResourceLocation> modelIds = new HashSet<>(PlateFoodModels.plateModels.values());
        for (ResourceLocation modelId : modelIds) {
            event.register(ModelResourceLocation.standalone(modelId.withPrefix("item/")));
        }
    }

    @SubscribeEvent
    private static void cacheBakedModels(ModelEvent.BakingCompleted event) {
        BakedModel missingModel = event.getModelManager().getMissingModel();
        Map<ResourceLocation, BakedModel> models = new HashMap<>();
        for (ResourceLocation modelId : PlateFoodModels.plateModels.values()) {
            BakedModel model = event.getModels().get(
                    ModelResourceLocation.standalone(modelId.withPrefix("item/"))
            );
            if (model != null && model != missingModel) {
                models.put(modelId, model);
            }
        }
        BAKED_MODELS = Map.copyOf(models);
    }

    static BakedModel getBakedModel(ResourceLocation modelId) {
        return BAKED_MODELS.get(modelId);
    }
}
