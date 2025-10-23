package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;

public class WargSaddleModel extends WargModel {
    private final ModelPart warg;
    private final ModelPart saddle;
    private float dilation;
    private float position;
    public WargSaddleModel(ModelPart root) {
        super(root);

        this.warg = root.getChild("root");
        this.saddle = this.warg.getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild("saddle");
        this.dilation = 0;
        this.position = 0;
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData upperBody = modelData.getRoot().getChild("root").getChild(EntityModelPartNames.BODY).getChild("upper_body");

        ModelPartData saddle = upperBody.addChild("saddle", ModelPartBuilder.create(), ModelTransform.origin(1.0F, -4.0F, -4.0F));

        ModelPartData cube_r1 = saddle.addChild("cube_r1", ModelPartBuilder.create().uv(23, 97).cuboid(-2.5F, -5.5F, -9.0F, 11.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -2.0F, 2.0F, 0.0F, -1.5708F, 0.0F));
        ModelPartData cube_r2 = saddle.addChild("cube_r2", ModelPartBuilder.create().uv(70, 32).cuboid(1.5F, 10.5F, -9.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.1F))
                .uv(69, 19).cuboid(-0.5F, 15.5F, -9.0F, 7.0F, 5.0F, 3.0F, new Dilation(0.1F))
                .uv(20, 8).cuboid(-2.5F, 16.5F, -9.0F, 11.0F, 16.0F, 15.0F, new Dilation(0.1F)), ModelTransform.of(-1.0F, -19.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
