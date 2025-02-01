package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;

public class WargSaddleModel extends EntityModel<WargEntityRenderState> {
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
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upperBody = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData saddle = upperBody.addChild("saddle", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, -4.0F, -4.0F));

        ModelPartData cube_r1 = saddle.addChild("cube_r1", ModelPartBuilder.create().uv(23, 97).cuboid(-2.5F, -5.5F, -9.0F, 11.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -2.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r2 = saddle.addChild("cube_r2", ModelPartBuilder.create().uv(70, 32).cuboid(1.5F, 10.5F, -9.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.1F))
                .uv(69, 19).cuboid(-0.5F, 15.5F, -9.0F, 7.0F, 5.0F, 3.0F, new Dilation(0.1F))
                .uv(20, 8).cuboid(-2.5F, 16.5F, -9.0F, 11.0F, 16.0F, 15.0F, new Dilation(0.1F)), ModelTransform.of(-1.0F, -19.0F, 2.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}
