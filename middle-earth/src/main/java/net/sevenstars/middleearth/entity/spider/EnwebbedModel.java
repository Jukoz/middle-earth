package net.sevenstars.middleearth.entity.spider;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaAnimations;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class EnwebbedModel extends EntityModel<BipedEntityRenderState> {
    private final ModelPart root;

    public EnwebbedModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new Dilation(1.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}