package net.sevenstars.middleearth.features.storage.block.coffers;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayers;

public class CofferModel extends Model<Float> {
    private final ModelPart lid;
    private final boolean roll;

    public CofferModel(ModelPart root, boolean roll) {
        super(root, RenderLayers::entityCutout);
        this.lid = root.getChild("lid");
        this.roll = roll;
    }

    @Override
    public void setAngles(Float openness) {
        super.setAngles(openness);
        if (roll) {
            lid.roll = openness * -1.5707964f;
        } else {
            lid.pitch = openness * -1.5707964f;
        }
    }
}
