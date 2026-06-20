package net.sevenstars.middleearth.entity.npcs.renderer;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.Identifier;

public class NpcEntityRenderState extends BipedEntityRenderState {
    public float widthScale;

    public AnimationState aimingState = new AnimationState();

    public Identifier simplifiedSkinId;
    public Identifier simplifiedEarId;
    public Identifier simplifiedNoseId;
    public Identifier simplifiedHairAddonId;
    public Identifier simplifiedFeetId;

    public Identifier skinId;
    public Identifier feetId;
    public Identifier headId;
    public Identifier earId;
    public Identifier noseId;
    public Identifier eyesId;
    public Identifier eyesEmissiveId;
    public Identifier eyebrowId;
    public Identifier scarId;
    public Identifier beardId;
    public Identifier beardAddonId;
    public Identifier hairId;
    public Identifier hairAddonId;
    public Identifier clothingBase;
    public Identifier clothingOver;
    public Identifier clothingExtra;

    public boolean haveEmissiveEyes;
    public boolean blinking;

    public boolean canShowFeet;
    public boolean canShowEars;
    public boolean canShowBeard;
    public boolean canShowHair;
    public float LOD = 0;

    public NpcEntityRenderState() {
        this.simplifiedSkinId = null;
        this.simplifiedNoseId = null;
        this.simplifiedEarId = null;
        this.simplifiedHairAddonId = null;
        this.simplifiedFeetId = null;

        this.widthScale = 1.0f;
        this.skinId = null;
        this.headId = null;
        this.earId = null;
        this.noseId = null;
        this.eyesId = null;
        this.eyesEmissiveId = null;
        this.eyebrowId = null;
        this.scarId = null;
        this.beardId = null;
        this.beardAddonId = null;
        this.hairId = null;
        this.hairAddonId = null;

        this.clothingBase = null;
        this.clothingOver = null;
        this.clothingExtra = null;

        this.haveEmissiveEyes = false;
        this.blinking = false;

        this.canShowFeet = false;
        this.canShowEars = true;
        this.canShowBeard = true;
        this.canShowHair = true;
    }
}
