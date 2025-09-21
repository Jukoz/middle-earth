package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollSaddleModel extends CaveTrollEntityModel {
    private final ModelPart reins;

    protected CaveTrollSaddleModel(ModelPart root) {
        super(root);

        reins = root.getChild("root").getChild("body_no_legs").getChild("body_no_limbs").getChild("Head").getChild("Saddle_Reigns");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot().getChild("root").getChild("body_no_legs").getChild("body_no_limbs");

        ModelPartData saddle_platform = modelPartData.addChild("saddle_platform", ModelPartBuilder.create().uv(0, 0).cuboid(-7.5F, 1.0F, -7.5F, 15.0F, 2.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 77).cuboid(-7.5F, -17.0F, -7.5F, 15.0F, 18.0F, 16.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -47.0F, 6.5F));

        ModelPartData log2_r1 = saddle_platform.addChild("log2_r1", ModelPartBuilder.create().uv(67, 25).cuboid(-1.5F, -1.5F, 3.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.4F, 4.5F, 9.0F, 3.1416F, 0.0F, 3.1416F));

        ModelPartData log1_r1 = saddle_platform.addChild("log1_r1", ModelPartBuilder.create().uv(53, 0).cuboid(-1.5F, -1.5F, -10.0F, 4.0F, 4.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.5F, -2.5F, 0.0F, -1.5708F, 0.0F));

        ModelPartData Platform = modelPartData.addChild("Platform", ModelPartBuilder.create().uv(106, 15).cuboid(-15.5F, 12.0F, 1.5F, 31.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(89, 28).cuboid(-19.0F, 10.0F, 1.6F, 38.0F, 2.0F, 16.0F, new Dilation(0.0F))
                .uv(77, 114).cuboid(-19.0F, 1.0F, 1.6F, 38.0F, 9.0F, 16.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -48.0F, 6.5F));

        ModelPartData log2_r2 = Platform.addChild("log2_r2", ModelPartBuilder.create().uv(111, 0).cuboid(-1.5F, -1.5F, -1.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-7.5F, 13.5F, 14.5F, 3.1416F, 0.0F, 3.1416F));

        ModelPartData log1_r2 = Platform.addChild("log1_r2", ModelPartBuilder.create().uv(142, 0).cuboid(-1.5F, -1.5F, -1.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(8.5F, 13.5F, 14.5F, 3.1416F, 0.0F, 3.1416F));

        ModelPartData Head = modelPartData.addChild("Head", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -34.5F, -8.5F));

        ModelPartData Saddle_Reigns = Head.addChild("Saddle_Reigns", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        ModelPartData cube_r1 = Saddle_Reigns.addChild("cube_r1", ModelPartBuilder.create().uv(0, 43).cuboid(-5.0F, -22.0F, -2.0F, 10.0F, 22.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, -4.0F, -0.6981F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 256, 256).transform(ModelTransformer.NO_OP);
    }

    @Override
    public void setAngles(CaveTrollEntityRenderState state) {
        super.setAngles(state);

        reins.visible = state.conrollingPassenger != null;
    }
}
