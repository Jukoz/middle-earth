package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;

public class NpcEntityRenderState extends BipedEntityRenderState {
    public Identifier skinTextureIdentifier;
    public Byte beardType;

    public NpcEntityRenderState() {
        this.beardType = 0;
        this.skinTextureIdentifier = null;
    }
}
