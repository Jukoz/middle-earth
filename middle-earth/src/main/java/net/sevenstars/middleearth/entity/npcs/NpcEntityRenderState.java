package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;

public class NpcEntityRenderState extends BipedEntityRenderState {
    public Identifier skinTextureIdentifier;
    public Identifier earTextureIdentifier;
    public Identifier eyeTextureIdentifier;
    public boolean haveEmissiveEyes;
    public Identifier hairTextureIdentifier;
    public Identifier hairAddonTextureIdentifier;
    public Identifier eyebrowTextureIdentifier;
    public Identifier beardTextureIdentifier;
    public Identifier beardAddonTextureIdentifier;

    public Identifier clothingTextureIdentifier;


    public Byte beardType;

    public NpcEntityRenderState() {
        this.skinTextureIdentifier = null;
        this.earTextureIdentifier = null;
        this.eyeTextureIdentifier = null;
        this.haveEmissiveEyes = false;
        this.hairTextureIdentifier = null;
        this.hairAddonTextureIdentifier = null;
        this.beardTextureIdentifier = null;
        this.beardAddonTextureIdentifier = null;
        this.eyebrowTextureIdentifier = null;
        this.clothingTextureIdentifier = null;

        // TODO
        this.beardType = 0;
    }
}
