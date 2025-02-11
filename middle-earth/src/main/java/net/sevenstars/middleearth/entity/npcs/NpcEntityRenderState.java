package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class NpcEntityRenderState extends BipedEntityRenderState {
    public Byte beardType;

    public NpcEntityRenderState() {
        this.beardType = 0;
    }
}
