package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SneakAttackProperty implements ItemPropertyFunction {
    @Override
    public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
        if(stack.getItem() instanceof CustomDaggerWeaponItem) {
            return CustomDaggerWeaponItem.canSneakAttack(stack) ? 1.0F : 0.0F;
        }
        return 0.0F;
    }
}
