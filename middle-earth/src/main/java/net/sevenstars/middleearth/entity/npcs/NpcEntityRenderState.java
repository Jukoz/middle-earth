package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;

public class NpcEntityRenderState extends BipedEntityRenderState {
    public Identifier skinTextureIdentifier;
    public Identifier eyeTextureIdentifier;
    public boolean haveEmissiveEyes;
    public Identifier hairTextureIdentifier;


    public Byte beardType;

    public NpcEntityRenderState() {
        this.skinTextureIdentifier = null;
        this.eyeTextureIdentifier = null;
        this.haveEmissiveEyes = false;
        this.hairTextureIdentifier = null;

        // TODO
        this.beardType = 0;
    }
}
