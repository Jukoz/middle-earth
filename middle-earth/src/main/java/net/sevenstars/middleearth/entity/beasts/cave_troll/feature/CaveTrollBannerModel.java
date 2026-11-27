package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;

/**
 * The troll's skeleton with a banner on each rear corner of the harness platform bone, so the banners move with the harness.
 * Only the banners are drawn, poles and flags in separate passes as they use different textures.
 */
public class CaveTrollBannerModel extends CaveTrollEntityModel {
    // Pole bases relative to the platform bone's origin
    private static final float CORNER_X = 17.0f;
    private static final float BASE_Y = 23.0f;
    private static final float BASE_Z = 16.0f;
    private static final float SCALE = 0.7f;

    private final ModelPart[] poles;
    private final ModelPart[] flags;

    public CaveTrollBannerModel(ModelPart root) {
        super(root);

        ModelPart platform = root.getChild("root").getChild("body_no_legs").getChild("body_no_limbs").getChild("platform");
        this.poles = new ModelPart[]{platform.getChild("pole_left"), platform.getChild("pole_right")};
        this.flags = new ModelPart[]{platform.getChild("flag_left"), platform.getChild("flag_right")};

        // The rest of the skeleton is drawn by the troll's own renderer
        for(ModelPart part : root.traverse()) {
            part.visible = false;
        }
        root.visible = true;
        for(ModelPart part : new ModelPart[]{root.getChild("root"), root.getChild("root").getChild("body_no_legs"),
                root.getChild("root").getChild("body_no_legs").getChild("body_no_limbs"), platform}) {
            part.visible = true;
        }
        for(ModelPart part : this.poles) {
            part.xScale = part.yScale = part.zScale = SCALE;
        }
        for(ModelPart part : this.flags) {
            part.xScale = part.yScale = part.zScale = SCALE;
        }
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData body = modelData.getRoot().getChild("root").getChild("body_no_legs").getChild("body_no_limbs");
        ModelPartData platform = body.addChild("platform", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -48.0F, 6.5F));

        // Same boxes as the held banner
        String[] sides = {"left", "right"};
        for(int i = 0; i < 2; i++) {
            ModelTransform base = ModelTransform.origin(i == 0 ? -CORNER_X : CORNER_X, BASE_Y, BASE_Z);
            platform.addChild("pole_" + sides[i], ModelPartBuilder.create()
                    .uv(44, 0).cuboid(-1.0F, -72.0F, -1.0F, 2.0F, 42.0F, 2.0F, new Dilation(0.0F))
                    .uv(44, 12).cuboid(-1.0F, -30.0F, -1.0F, 2.0F, 30.0F, 2.0F, new Dilation(0.0F))
                    .uv(0, 42).mirrored().cuboid(-10.0F, -74.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F)), base);
            platform.addChild("flag_" + sides[i], ModelPartBuilder.create()
                    .uv(0, 0).cuboid(-10.0F, -74.0F, -2.0F, 20.0F, 40.0F, 1.0F, new Dilation(0.0F)), base);
        }

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void showPoles() {
        for(int i = 0; i < 2; i++) {
            this.poles[i].visible = true;
            this.flags[i].visible = false;
        }
    }

    public void showFlags() {
        for(int i = 0; i < 2; i++) {
            this.poles[i].visible = false;
            this.flags[i].visible = true;
        }
    }
}
