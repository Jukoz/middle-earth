package net.sevenstars.middleearth.client.model.hand.shields;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class HeaterShieldEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart plate;
    private final ModelPart handle;

    public HeaterShieldEntityModel(ModelPart root) {
        super(root, RenderLayer::getEntitySolid);
        this.root = root;
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("plate", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -7.0F, -2.0F, 12.0F, 10.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 11).cuboid(-5.0F, 3.0F, -2.0F, 10.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(2, 14).cuboid(-4.0F, 5.0F, -2.0F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(3, 17).mirrored().cuboid(-3.0F, 7.0F, -2.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(5, 19).mirrored().cuboid(-2.0F, 8.0F, -2.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(7, 21).mirrored().cuboid(-1.0F, 9.0F, -2.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.NONE);

        modelPartData.addChild("handle", ModelPartBuilder.create().uv(26, 0).mirrored().cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public ModelPart getHandle() {
        return handle;
    }

    public ModelPart getPlate() {
        return plate;
    }
}