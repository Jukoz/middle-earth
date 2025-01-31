package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class BroadhoofGoatEntityRenderState extends LivingEntityRenderState {
    BroadhoofGoatVariant variant;
    BroadhoofGoatHorns horns;
    public ItemStack armor;
    public ItemStack saddle;
    boolean beardBrushed;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState startSittingAnimationState = new AnimationState();
    public final AnimationState stopSittingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();

    public BroadhoofGoatEntityRenderState() {
        variant = BroadhoofGoatVariant.GRAY;
        horns = BroadhoofGoatHorns.TINY;
        armor = ItemStack.EMPTY;
        saddle = ItemStack.EMPTY;
    }


}
