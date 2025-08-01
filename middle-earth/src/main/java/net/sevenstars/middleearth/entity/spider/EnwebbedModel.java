package net.sevenstars.middleearth.entity.spider;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaAnimations;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class EnwebbedModel extends EntityModel<BipedEntityRenderState> {
    private final ModelPart bigBody;
    private final ModelPart smallBody;

    public EnwebbedModel(ModelPart root) {
        super(root);
        this.bigBody = root.getChild("big_body");
        this.smallBody = root.getChild("small_body");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData dataRoot = modelData.getRoot();
        ModelPartData bigBody = dataRoot.addChild("big_body", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new Dilation(1.15F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData smallBody = dataRoot.addChild("small_body", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new Dilation(0.65F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(BipedEntityRenderState state) {
         boolean hasChestplate = !state.equippedChestStack.isEmpty();
        bigBody.hidden = !hasChestplate;
        smallBody.hidden = hasChestplate;
        super.setAngles(state);
    }
}