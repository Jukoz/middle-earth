package net.sevenstars.middleearth.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.sevenstars.middleearth.enchantments.EnchantmentEffectsME;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow @Final protected EntityEquipment equipment;

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("RETURN"), cancellable = true)
    public void getAttackDistanceScalingFactor(Entity entity, CallbackInfoReturnable<Double> cir) {
        if (entity != null) {
            ItemStack chestplate = this.getEquippedStack(EquipmentSlot.CHEST);
            if(!chestplate.isEmpty()) {
                RegistryEntry<Enchantment> enchantmentRegistryEntry = entity.getWorld().getRegistryManager()
                        .getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(EnchantmentEffectsME.STEALTHY_TRAIL).orElseThrow();
                boolean hasEnchant = chestplate.getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
                int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry, chestplate);
                if(hasEnchant) {
                    double original = cir.getReturnValue();
                    double viewDistance = original + Math.max(-0.9f, -0.2f * level);
                    cir.setReturnValue(viewDistance);
                }
            }
        }
    }
}
