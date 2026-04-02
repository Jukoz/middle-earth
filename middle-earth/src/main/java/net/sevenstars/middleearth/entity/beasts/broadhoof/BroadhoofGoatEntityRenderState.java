package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class BroadhoofGoatEntityRenderState extends BeastEntityRenderState {

    public BroadhoofGoatColor color = BroadhoofGoatColor.WHITE;
    public BroadhoofGoatPattern pattern = BroadhoofGoatPattern.NONE;
    BroadhoofGoatHorns horns = BroadhoofGoatHorns.TINY;
    public boolean hasLeftHorn = false;
    public boolean hasRightHorn = false;
    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;
    boolean beardBrushed;

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState sittingAnimationState = new AnimationState();
    public AnimationState jumpAnimationState = new AnimationState();


}
