package net.sevenstars.middleearth.client.model.hand.shields;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class RoundShieldEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart plate;
    private final ModelPart handle;

    public RoundShieldEntityModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(17, 17).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        modelPartData.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 6).addBox(-8.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 6).addBox(7.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-7.0F, -4.0F, -2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 4).addBox(6.0F, -4.0F, -2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 2).addBox(-6.0F, -6.0F, -2.0F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 2).addBox(4.0F, -6.0F, -2.0F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 1).addBox(-4.0F, -7.0F, -2.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 1).addBox(2.0F, -7.0F, -2.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(modelData, 64, 64);
    }

    public ModelPart getHandle() {
        return handle;
    }

    public ModelPart getPlate() {
        return plate;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay, color);
    }
}
