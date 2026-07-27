package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class SwanEntityModel extends HierarchicalModel<SwanEntity> {
    protected final ModelPart root;

    protected SwanEntityModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
