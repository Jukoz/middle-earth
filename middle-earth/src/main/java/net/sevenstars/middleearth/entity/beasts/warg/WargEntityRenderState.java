package net.sevenstars.middleearth.entity.beasts.warg;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatHorns;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatVariant;

@Environment(EnvType.CLIENT)
public class WargEntityRenderState extends LivingEntityRenderState {
    WargVariant variant;
    public ItemStack armor;
    public ItemStack saddle;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState startSittingAnimationState = new AnimationState();
    public final AnimationState stopSittingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();

    public WargEntityRenderState() {
        variant = WargVariant.BROWN;
        armor = ItemStack.EMPTY;
        saddle = ItemStack.EMPTY;
    }
}
