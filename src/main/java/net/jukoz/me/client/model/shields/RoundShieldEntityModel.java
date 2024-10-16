package net.jukoz.me.client.model.shields;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class RoundShieldEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart plate;
    private final ModelPart handle;

    public RoundShieldEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("handle", ModelPartBuilder.create().uv(17, 17).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.NONE);

        modelPartData.addChild("plate", ModelPartBuilder.create().uv(0, 6).cuboid(-8.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(46, 6).cuboid(7.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 4).cuboid(-7.0F, -4.0F, -2.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F))
                .uv(42, 4).cuboid(6.0F, -4.0F, -2.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 2).cuboid(-6.0F, -6.0F, -2.0F, 2.0F, 12.0F, 1.0F, new Dilation(0.0F))
                .uv(36, 2).cuboid(4.0F, -6.0F, -2.0F, 2.0F, 12.0F, 1.0F, new Dilation(0.0F))
                .uv(14, 1).cuboid(-4.0F, -7.0F, -2.0F, 2.0F, 14.0F, 1.0F, new Dilation(0.0F))
                .uv(30, 1).cuboid(2.0F, -7.0F, -2.0F, 2.0F, 14.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 0).cuboid(-2.0F, -8.0F, -2.0F, 4.0F, 16.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }

    public ModelPart getHandle() {
        return handle;
    }

    public ModelPart getPlate() {
        return plate;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay, color);
    }
    }