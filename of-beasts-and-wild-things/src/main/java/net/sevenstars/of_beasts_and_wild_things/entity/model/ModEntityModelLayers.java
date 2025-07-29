package net.sevenstars.of_beasts_and_wild_things.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanAdultModel;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanBabyModel;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntityModel;

@Environment(value= EnvType.CLIENT)
public class ModEntityModelLayers {
    public static final EntityModelLayer SNAIL = ModEntityModelLayers.registerEntityModelLayer("snail", SnailEntityModel.getTexturedModelData());
    public static final EntityModelLayer PHEASANT = ModEntityModelLayers.registerEntityModelLayer("pheasant", PheasantEntityModel.getTexturedModelData());
    public static final EntityModelLayer SWAN = ModEntityModelLayers.registerEntityModelLayer("swan", SwanAdultModel.getTexturedModelData());
    public static final EntityModelLayer SWAN_BABY = ModEntityModelLayers.registerEntityModelLayer("swan_baby", SwanBabyModel.getTexturedModelData());
    public static final EntityModelLayer DEER = ModEntityModelLayers.registerEntityModelLayer("deer", DeerEntityModel.getTexturedModelData());

    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(Identifier.of(OfBeastsAndWildThings.MOD_ID, registryName), "main");
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }
}
