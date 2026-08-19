package net.sevenstars.middleearth.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.network.packets.S2C.PacketLivingEntityData;
import net.sevenstars.middleearth.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private int middleEarth$climbTicks;

    @Unique
    private boolean middleEarth$customClimbing;

    protected LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Shadow
    public abstract void setItemSlot(EquipmentSlot slot, ItemStack stack);

    @Inject(
            method = "getVisibilityPercent(Lnet/minecraft/world/entity/Entity;)D",
            at = @At("RETURN"),
            cancellable = true
    )
    private void middleEarth$applyStealthyTrail(Entity observer, CallbackInfoReturnable<Double> cir) {
        ItemStack chestplate = this.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.isEmpty()) {
            return;
        }

        Holder<Enchantment> enchantment = this.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(EnchantmentsME.STEALTHY_TRAIL);
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, chestplate);
        if (level > 0) {
            cir.setReturnValue(cir.getReturnValue() + Math.max(-0.9F, -0.2F * level));
        }
    }

    @ModifyVariable(
            method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"),
            argsOnly = true,
            index = 1
    )
    private MobEffectInstance middleEarth$scaleHarmfulEffectDuration(MobEffectInstance effect) {
        if (effect.getEffect().value().getCategory() != MobEffectCategory.HARMFUL || effect.isInfiniteDuration()) {
            return effect;
        }

        int protectionLevel = middleEarth$getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.HEAD))
                + middleEarth$getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.CHEST))
                + middleEarth$getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.LEGS))
                + middleEarth$getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.FEET));
        if (protectionLevel <= 0) {
            return effect;
        }

        float durationScale = 1.0F - Math.min(0.8F, 0.08F * protectionLevel);
        int duration = Math.max(1, Mth.floor(effect.getDuration() * durationScale));
        return new MobEffectInstance(
                effect.getEffect(),
                duration,
                effect.getAmplifier(),
                effect.isAmbient(),
                effect.isVisible(),
                effect.showIcon()
        );
    }

    @Inject(
            method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("TAIL")
    )
    private void middleEarth$syncAddedEffect(
            MobEffectInstance effect,
            Entity source,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!this.level().isClientSide && cir.getReturnValue()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                    this,
                    new PacketLivingEntityData(this.getId(), effect)
            );
        }
    }

    @Unique
    private int middleEarth$getAilmentProtectionLevel(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }

        Holder<Enchantment> enchantment = this.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(EnchantmentsME.AILMENT_PROTECTION);
        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
    }

    @Inject(
            method = "dropAllDeathLoot(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V",
            at = @At("HEAD")
    )
    private void middleEarth$clearNpcMountEquipment(
            ServerLevel level,
            DamageSource damageSource,
            CallbackInfo callbackInfo
    ) {
        if (this.getControllingPassenger() instanceof NpcEntity) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                this.setItemSlot(slot, ItemStack.EMPTY);
            }
        }
    }

    @Inject(method = "getSpeed()F", at = @At("RETURN"), cancellable = true)
    private void middleEarth$adjustNpcMountSpeed(CallbackInfoReturnable<Float> cir) {
        Entity controllingPassenger = this.getControllingPassenger();
        if (!(controllingPassenger instanceof NpcEntity npcEntity)) {
            return;
        }

        float currentValue = cir.getReturnValue();
        if (controllingPassenger.getVehicle() instanceof Horse) {
            currentValue = 0.5F;
            if (npcEntity.isFighting()) {
                currentValue = Math.max(currentValue * 2.0F, 0.5F);
            }
        }
        cir.setReturnValue(Math.max(currentValue, 0.25F));
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void middleEarth$updateCustomClimbing(CallbackInfo ci) {
        if (!((Object) this instanceof Player player)) {
            return;
        }

        double climbingStrength = player.getAttributeValue(EntityAttributesME.CLIMBING_STRENGTH);
        if (climbingStrength <= 0.0 || player.isInWater() || player.onGround()
                || !PlayerUtil.isAgainstWall(player)) {
            this.middleEarth$climbTicks = 0;
            this.middleEarth$customClimbing = false;
            return;
        }

        this.middleEarth$customClimbing = this.middleEarth$climbTicks < climbingStrength;
        if (this.middleEarth$customClimbing) {
            this.middleEarth$climbTicks++;
        }
    }

    @Inject(method = "onClimbable()Z", at = @At("HEAD"), cancellable = true)
    private void middleEarth$applyCustomClimbing(CallbackInfoReturnable<Boolean> cir) {
        if (this.middleEarth$customClimbing) {
            cir.setReturnValue(true);
        }
    }
}
