package net.sevenstars.middleearth.entity.beasts.cave_troll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.sevenstars.middleearth.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class CaveTrollEntityRenderState extends BeastEntityRenderState {
    public AnimationState sleepingAnimationState = new AnimationState();
    public AnimationState chaseAnimationState = new AnimationState();
    public AnimationState scavengingAnimationState = new AnimationState();
    public boolean isSprinting = false;
}
