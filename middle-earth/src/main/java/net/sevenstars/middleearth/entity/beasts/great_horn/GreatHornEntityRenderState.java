package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GreatHornEntityRenderState extends LivingEntityRenderState {
    public GreatHornVariantDep variant;
    public ItemStack saddle;
    public ItemStack armor;
    public boolean blueSaddle;
    public boolean hasRider;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState earWiggleAnimationState = new AnimationState();
    public final AnimationState gallopAnimationState = new AnimationState();
    public final AnimationState bowAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public GreatHornVariant greatHornVariant;

    public GreatHornEntityRenderState() {
        saddle = ItemStack.EMPTY;
        armor = ItemStack.EMPTY;
        blueSaddle = false;
        hasRider = false;
    }

    public boolean hasRedNose() {
        if(this.customName != null) {
            String name = this.customName.getString();
            return ("rudolph".equalsIgnoreCase(name) || "rudolf".equalsIgnoreCase(name));
        }
        return false;
    }

    public boolean isElkebies() {
        return (this.customName != null && "elkebies".equalsIgnoreCase(this.customName.getString()));
    }
}
