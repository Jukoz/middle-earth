package net.sevenstars.middleearth.client.model.hand.shields;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class KiteShieldEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart plate;
    private final ModelPart handle;

    public KiteShieldEntityModel(ModelPart root) {
        super(root, RenderLayer::getEntitySolid);
        this.root = root;
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("handle", ModelPartBuilder.create().uv(38, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.NONE);

        modelPartData.addChild("plate", ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -8.0F, -2.0F, 4.0F, 19.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 1).cuboid(2.0F, -7.0F, -2.0F, 2.0F, 13.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 1).cuboid(-4.0F, -7.0F, -2.0F, 2.0F, 13.0F, 1.0F, new Dilation(0.0F))
                .uv(30, 2).cuboid(4.0F, -6.0F, -2.0F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 2).cuboid(-5.0F, -6.0F, -2.0F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 4).cuboid(5.0F, -4.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 4).cuboid(-6.0F, -4.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 15).cuboid(2.0F, 6.0F, -2.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 15).cuboid(-3.0F, 6.0F, -2.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(15, 20).cuboid(-1.0F, 11.0F, -2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }

    public ModelPart getHandle() {
        return handle;
    }

    public ModelPart getPlate() {
        return plate;
    }
}
