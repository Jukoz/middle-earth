package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.network.packets.S2C.PacketLivingEntityData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private static boolean stackTraceLock = false;

    @Shadow @Final protected EntityEquipment equipment;

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract @NotNull ItemStack getWeaponStack();

    @Shadow
    protected abstract float getMovementSpeed(float slipperiness);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("RETURN"), cancellable = true)
    public void getAttackDistanceScalingFactor(Entity entity, CallbackInfoReturnable<Double> cir) {
        if (entity != null) {
            ItemStack chestplate = this.getEquippedStack(EquipmentSlot.CHEST);
            if(!chestplate.isEmpty()) {
                RegistryEntry<Enchantment> enchantmentRegistryEntry = entity.getWorld().getRegistryManager()
                        .getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(EnchantmentsME.STEALTHY_TRAIL).orElseThrow();
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

    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("TAIL"))
    public final void addStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if(!this.getWorld().isClient) {
            for (ServerPlayerEntity player : ((ServerWorld) this.getWorld()).getPlayers()) {
                ServerPlayNetworking.send(player, new PacketLivingEntityData(this.getId(), effect));
            }
        }
    }

    private static int getAilmentProtectionLevel(ItemStack itemStack, World world) {
        int level = 0;
        RegistryEntry<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOptional(EnchantmentsME.AILMENT_PROTECTION).orElseThrow();
        boolean hasEnchant = itemStack.getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
        if(hasEnchant) {
            level = EnchantmentHelper.getLevel(enchantmentRegistryEntry, itemStack);
        }
        return level;
    }

    @Inject(at = @At("HEAD"), method = "drop")
    protected void drop(ServerWorld world, DamageSource damageSource, CallbackInfo callbackInfo) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity){
            this.equipment.clear();
        }
    }

    @Inject(method = "getMovementSpeed()F", at = @At("RETURN"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity npcEntity){
            float currentValue = cir.getReturnValue();
            float modifier = 1f;
            float fightingModifier = 1f;
            if(getControllingPassenger().getVehicle() instanceof HorseEntity){
                currentValue = 0.5f;
                fightingModifier = 2f;
            }
            if(npcEntity.isFighting()){
                cir.setReturnValue(Math.max(currentValue * fightingModifier, 0.5f));
            }
            cir.setReturnValue(Math.max(currentValue * modifier, 0.25f));
        }
    }
    @WrapOperation(method = "applyClimbingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private boolean ScaffoldingDescendLogic(BlockState state, Block block, Operation<Boolean> original) {
        return original.call(state, block)
                || block == Blocks.SCAFFOLDING && state.isOf(ModDecorativeBlocks.REINFORCED_SCAFFOLDING);
    }
}
