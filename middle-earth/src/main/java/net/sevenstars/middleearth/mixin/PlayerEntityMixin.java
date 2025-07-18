package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.enchantments.EnchantmentEffectsME;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.sevenstars.middleearth.utils.PlayerMovementData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    @Shadow public abstract PlayerInventory getInventory();

    //TODO Shield stuff broken, most likely because of new data comps
    //@Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    //@Shadow public abstract boolean canUseSlot(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V",
    shift = At.Shift.AFTER), ordinal = 0)
    public float attack(float damage, Entity target) {
        RegistryEntry<Enchantment> enchantmentRegistryEntry = getWorld().getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(EnchantmentEffectsME.FIRST_STRIKE).orElseThrow();
        boolean hasEnchant = getStackInHand(getActiveHand()).getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
        if(hasEnchant) {
            if(target instanceof LivingEntity livingEntity) {
                float healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
                if(healthRatio > 0.9f) {
                    return damage * 1.5f;
                }
            }
        }
        return damage;
    }

/*
    @WrapOperation(method = "damageShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    protected boolean canWalkOnPowderSnowTag(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.getItem() instanceof ShieldItem;
    }

    @Inject(at = @At(value = "HEAD"), method = "disableShield", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void disableShieldHead(CallbackInfo ci) {
        Item activeItem = activeItemStack.getItem();

        if (activeItem instanceof ShieldItem shield) {
            float f = 0.25F; //+ (float) EnchantmentHelper.getEfficiency(this) * 0.05F; //TODO Test this
            if (isSprinting()) {
                f += 0.75F;
            }
            if (this.getRandom().nextFloat() < f) {
                this.getItemCooldownManager().set(activeItemStack, 100);
                this.clearActiveItem();
                this.getWorld().sendEntityStatus(this, (byte) 30);
                ci.cancel();
            }
        }
    }

    //TODO test if this works, cooldown manager set takes stack now not item
    @Inject(method = "disableShield", at = @At("HEAD"))
    public void shield_api$disableShield(CallbackInfo ci) {
        for (CustomShieldItem customShieldItem : CustomShieldItem.instances) {
            this.getItemCooldownManager().set(new ItemStack(customShieldItem), 100);
        }
    }

    @Inject(method = "getEquippedStack", at = @At("HEAD"), cancellable = true)
    public void getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if(isCalledFrom("net.minecraft.server.network.ServerPlayNetworkHandler", "onPlayerAction")) {
            return;
        }
        boolean twoHanded = false;
        ItemStack stackMainHand = this.getInventory().getMainHandStack();
        ItemStack stackOffHand = this.getInventory().getStack(PlayerInventory.OFF_HAND_SLOT);

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

        if (slot == OFFHAND) {
            if (twoHanded) {
                cir.setReturnValue(ItemStack.EMPTY);
                cir.cancel();
            }
        }
    }*/

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerMovementData.addAFKTime((IEntityDataSaver) this,1);
    }

    @Inject(method = "travel", at = @At("HEAD"))
    public void travel(CallbackInfo ci, @Local Vec3d movementInput) {
        if(movementInput.length() > 0.01f) {
            PlayerMovementData.resetAFK((IEntityDataSaver) this);
        }
    }

/*
    @Inject(method = "attack", at = @At("HEAD"))
    public void attack(Entity target, CallbackInfo ci) {
        PlayerMovementData.resetAFK((IEntityDataSaver) this);
    }

    @ModifyVariable(method = "attack", ordinal = 3, at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 0))
    public float attackBackStab(float value, Entity target) {
        ItemStack mainStack = this.getStackInHand(Hand.MAIN_HAND);
        if(mainStack.getItem() instanceof CustomDaggerWeaponItem) {
            if(CustomDaggerWeaponItem.canBackStab(target, this)) {
                return value * 1.5f;
            }
        }
        return value;
    }*/

    @Inject(method = "resetLastAttackedTicks", at = @At("HEAD"))
    public void resetLastAttackedTicks(CallbackInfo ci) {
        PlayerMovementData.resetAFK((IEntityDataSaver) this);
    }


    private boolean isCalledFrom(String className, String methodName) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains(className)) {
                if(element.getMethodName().contains(methodName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
