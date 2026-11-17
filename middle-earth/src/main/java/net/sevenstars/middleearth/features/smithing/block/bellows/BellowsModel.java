package net.sevenstars.middleearth.features.smithing.block.bellows;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayers;

public class BellowsModel extends Model<BellowsBlockEntityRenderState> {
    private static final float BELLOW_MAX_ANGLE = 0.72f;
    private final ModelPart top;

    public BellowsModel(ModelPart root) {
        super(root, RenderLayers::entityCutout);
        this.top = root.getChild("top");
    }

    @Override
    public void setAngles(BellowsBlockEntityRenderState state) {
        super.setAngles(state);
        this.top.pitch = 0.37f + (state.animationProgress * -BELLOW_MAX_ANGLE);
    }
}