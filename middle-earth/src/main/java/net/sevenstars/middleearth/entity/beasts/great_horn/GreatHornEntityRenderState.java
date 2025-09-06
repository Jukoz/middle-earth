package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatHorns;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatVariant;

@Environment(EnvType.CLIENT)
public class GreatHornEntityRenderState extends LivingEntityRenderState {
    public boolean saddle;
    public boolean hasRider;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState earWiggleAnimationState = new AnimationState();
    public final AnimationState gallopAnimationState = new AnimationState();
    public final AnimationState bowAnimationState = new AnimationState();

    public GreatHornEntityRenderState() {
        saddle = false;
        hasRider = false;
    }


}
