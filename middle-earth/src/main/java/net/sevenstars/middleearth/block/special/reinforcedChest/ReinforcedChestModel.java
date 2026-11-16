package net.sevenstars.middleearth.block.special.reinforcedChest;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayers;

public class ReinforcedChestModel extends Model<Float> {
    private final ModelPart lid;
    private final ModelPart latch;

    public ReinforcedChestModel(ModelPart root) {
        super(root, RenderLayers::entityCutout);
        this.lid = root.getChild("lid");
        this.latch = root.getChild("lock");
    }

    @Override
    public void setAngles(Float openness) {
        super.setAngles(openness);
        latch.pitch = lid.pitch = openness * 1.5707964f;
    }
}
