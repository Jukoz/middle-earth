package net.sevenstars.middleearth.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.enchantments.EnchantmentEffectsME;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private static boolean stackTraceLock = false;

    @Shadow @Final protected EntityEquipment equipment;

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

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

    @Inject(method = "getEquippedStack", at = @At("HEAD"), cancellable = true)
    public void getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if(stackTraceLock) return;
        stackTraceLock = true;
        boolean twoHanded = false;
        ItemStack stackMainHand = this.getStackInHand(Hand.MAIN_HAND);
        ItemStack stackOffHand = this.getStackInHand(Hand.OFF_HAND);

        if(stackMainHand != null){
            if ((stackMainHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackMainHand.getItem()).type.twoHanded))
                    || (stackMainHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackMainHand.getItem() instanceof CustomLongbowWeaponItem)) {
                twoHanded = true;
            }
        }
        if(stackOffHand != null){
            if ((stackOffHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackOffHand.getItem()).type.twoHanded))
                    || (stackOffHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackOffHand.getItem() instanceof CustomLongbowWeaponItem)) {
                twoHanded = true;
            }
        }

        if (slot == EquipmentSlot.OFFHAND) {
            if (twoHanded) {
                cir.setReturnValue(ItemStack.EMPTY);
                cir.cancel();
            }
        }
        stackTraceLock = false;
    }

    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"), index = 1, argsOnly = true)
    public final StatusEffectInstance addStatusEffect(StatusEffectInstance effect) {
        if(effect.getEffectType().value().getCategory().equals(StatusEffectCategory.HARMFUL)) {
            float ailmentLevel = 0;
            World world = this.getWorld();
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.HEAD), world);
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.CHEST), world);
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.LEGS), world);
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.FEET), world);
            if(ailmentLevel > 0) {
                float scale = 0.08f * ailmentLevel;
                scale = 1 - Math.min(0.8f, scale);
                effect = effect.withScaledDuration(scale);
            }
        }
        return effect;
    }

    private static int getAilmentProtectionLevel(ItemStack itemStack, World world) {
        int level = 0;
        RegistryEntry<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOptional(EnchantmentEffectsME.AILMENT_PROTECTION).orElseThrow();
        boolean hasEnchant = itemStack.getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
        if(hasEnchant) {
            level = EnchantmentHelper.getLevel(enchantmentRegistryEntry, itemStack);
        }
        return level;
    }
}
