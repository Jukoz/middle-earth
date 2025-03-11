package net.sevenstars.middleearth.entity.spider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class MirkwoodSpiderEntityRenderState extends LivingEntityRenderState {
    public MirkwoodSpiderVariants variant;

    public MirkwoodSpiderEntityRenderState() {
        variant = MirkwoodSpiderVariants.BLACK;
        ;
    }
}