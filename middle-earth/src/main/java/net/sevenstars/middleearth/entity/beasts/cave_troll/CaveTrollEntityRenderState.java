package net.sevenstars.middleearth.entity.beasts.cave_troll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class CaveTrollEntityRenderState extends BeastEntityRenderState {
    public final ItemRenderState handItemState = new ItemRenderState();

    public AnimationState chaseAnimationState = new AnimationState();
    public AnimationState scavengingAnimationState = new AnimationState();
    public AnimationState startSleepingAnimationState = new AnimationState();
    public AnimationState sleepingAnimationState = new AnimationState();
    public AnimationState stopSleepingANimationState = new AnimationState();
    public boolean isSprinting = false;
    public boolean isControlled = false;

    public static void updateRenderState(LivingEntity entity, CaveTrollEntityRenderState state, ItemModelManager itemModelManager) {
        itemModelManager.updateForLivingEntity(state.handItemState, entity.getStackInArm(Arm.RIGHT), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, entity);
    }
}
