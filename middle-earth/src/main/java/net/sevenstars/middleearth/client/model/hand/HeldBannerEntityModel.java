package net.sevenstars.middleearth.client.model.hand;

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


public class HeldBannerEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart pole;
    private final ModelPart banner;

    public HeldBannerEntityModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
        this.pole = root.getChild("pole");
        this.banner = root.getChild("banner");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("pole", CubeListBuilder.create()
                        .texOffs(44, 0).addBox(-1.0F, -72.0F, -1.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 12).addBox(-1.0F, -30.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 42).mirror().addBox(-10.0F, -74.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);

        modelPartData.addOrReplaceChild("banner", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-10.0F, -74.0F, -2.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);

        return LayerDefinition.create(modelData, 64, 64);
    }

    public ModelPart getPole() {
        return pole;
    }

    public ModelPart getBanner() {
        return banner;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay, color);
    }
}
