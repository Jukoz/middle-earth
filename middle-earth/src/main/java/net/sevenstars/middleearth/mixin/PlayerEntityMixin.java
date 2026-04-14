package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.sevenstars.middleearth.enchantments.EnchantmentEffectsME;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.sevenstars.middleearth.utils.PlayerMovementData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    @Shadow public abstract PlayerInventory getInventory();

    @Shadow protected float damageTiltYaw;
    int climbDistance = 0;
    //TODO Shield stuff broken, most likely because of new data comps
    //@Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    //@Shadow public abstract boolean canUseSlot(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V",
    shift = At.Shift.AFTER), ordinal = 0)
    public float attack(float damage, Entity target) {
        float newDamage = damage;
        ItemStack mainStack = getStackInHand(getActiveHand());
        RegistryEntry<Enchantment> enchantmentRegistryEntry = getWorld().getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(EnchantmentEffectsME.FIRST_STRIKE).orElseThrow();
        boolean hasEnchant = mainStack.getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
        if(hasEnchant) {
            if(target instanceof LivingEntity livingEntity) {
                float healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
                if(healthRatio > 0.9f) {
                    newDamage *= 1.5f;
                }
            }
        }
        if(mainStack.getItem() instanceof CustomDaggerWeaponItem) {
            if(CustomDaggerWeaponItem.canBackStab(target, this)) {
                newDamage *= 1.5f;
            }
        }
        return newDamage;
    }

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

        return value;
    }*/

    @Inject(method = "resetLastAttackedTicks", at = @At("HEAD"))
    public void resetLastAttackedTicks(CallbackInfo ci) {
        PlayerMovementData.resetAFK((IEntityDataSaver) this);
    }


    @Inject(method = "createPlayerAttributes", require = 1, allow = 1, at = @At("return"))
    private static void createPlayerAttributesInject(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info){
        info.getReturnValue().add(EntityAttributesME.POWDERED_SNOW_IMMUNITY);
        info.getReturnValue().add(EntityAttributesME.DELVERS_FEAR_STRENGTH);
        info.getReturnValue().add(EntityAttributesME.CLIMBING_STRENGTH);
        info.getReturnValue().add(EntityAttributesME.DETECTION_RANGE);

    }

    @Inject(method = "isClimbing", at = @At("HEAD"), cancellable = true)
    private void isClimbingInject(CallbackInfoReturnable<Boolean> cir) {
        if ((LivingEntity) this instanceof PlayerEntity entity){
            if(!entity.isTouchingWater() && !entity.isOnGround() && PlayerUtil.isAgainstWall(entity)){
                climbDistance += 1;
                if(climbDistance < entity.getAttributeValue(EntityAttributesME.CLIMBING_STRENGTH)){
                    cir.setReturnValue(true);
                }
            } else if(entity.isOnGround()){
                climbDistance = 0;
            }
        }
    }
}
