package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;

public class BeastEntityRenderState extends LivingEntityRenderState {
    public AnimationState startSittingAnimationState = new AnimationState();
    public AnimationState stopSittingAnimationState = new AnimationState();
    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;
}
