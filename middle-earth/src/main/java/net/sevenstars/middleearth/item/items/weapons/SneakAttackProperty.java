package net.sevenstars.middleearth.item.items.weapons;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SneakAttackProperty implements BooleanProperty {
    public static final MapCodec<SneakAttackProperty> CODEC = MapCodec.unit(new SneakAttackProperty());

    public MapCodec<SneakAttackProperty> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if(stack.getItem() instanceof CustomDaggerWeaponItem) {
            return CustomDaggerWeaponItem.canSneakAttack(stack);
        }
        return false;
    }
}
