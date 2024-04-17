package net.jukoz.me.mixin;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "damageShield", locals = LocalCapture.CAPTURE_FAILHARD)
    protected void damageShield(float amount, CallbackInfo callBackInfo) {
        if (activeItemStack.getItem() instanceof ShieldItem) {
            if (amount >= 3.0F) {
                int i = 1 + MathHelper.floor(amount);
                Hand hand = this.getActiveHand();
                this.activeItemStack.damage(i, this, PlayerEntity.getSlotForHand(hand));

                if (activeItemStack.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    activeItemStack = ItemStack.EMPTY;
                    this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.getWorld().random.nextFloat() * 0.4F);
                }
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "disableShield()V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void disableShieldHead(CallbackInfo ci) {
        Item activeItem = activeItemStack.getItem();

        if (activeItem instanceof ShieldItem shield) {
            float f = 0.25F + (float) EnchantmentHelper.getEfficiency(this) * 0.05F;
            if (isSprinting()) {
                f += 0.75F;
            }
            if (this.getRandom().nextFloat() < f) {
                this.getItemCooldownManager().set(shield, 100);
                this.clearActiveItem();
                this.getWorld().sendEntityStatus(this, (byte) 30);
                ci.cancel();
            }
        }
    }

    private void getEntryList(PlayerEntity player) {
        Optional<RegistryEntryList.Named<Item>> opt = Registries.ITEM.getEntryList(ConventionalItemTags.SHIELDS);
        List<Item> list = new ArrayList<>();
        if (opt.isPresent()) {
            list = opt.get().stream().map(RegistryEntry::value).toList();
        }

        for (int amountOfShields = list.size(); amountOfShields > 0; amountOfShields--) {

            if (list.get(amountOfShields - 1) instanceof ShieldItem) {
                player.getItemCooldownManager().set(Items.SHIELD, 100);
            }
            player.clearActiveItem();
            player.getWorld().sendEntityStatus(player, (byte) 30);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return null;
    }
}
