package net.jukoz.me.mixin;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
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
public class PlayerEntityMixin {
    @Shadow
    @Final
    private PlayerInventory inventory;
    @Shadow
    protected ItemStack activeItemStack;
    @Inject(at = @At(value = "HEAD"), method = "damageShield", locals = LocalCapture.CAPTURE_FAILHARD)
    protected void damageShield(float amount, CallbackInfo callBackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (activeItemStack.getItem() instanceof ShieldItem) {
            if (amount >= 3.0F) {
                int i = 1 + MathHelper.floor(amount);
                Hand hand = player.getActiveHand();
                PlayerEntity playerEntity = (PlayerEntity) (Object) this;
                this.activeItemStack.damage(i, playerEntity, PlayerEntity.getSlotForHand(hand));

                if (activeItemStack.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    activeItemStack = ItemStack.EMPTY;
                    player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.getWorld().random.nextFloat() * 0.4F);
                }
            }
        }
    }

    /**
     * @param sprinting    if player is sprinting
     * @param callbackInfo callback information
     */
    @Inject(at = @At(value = "HEAD"), method = "disableShield()V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void disableShieldHead(boolean sprinting, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        Item activeItem = activeItemStack.getItem();

        if (activeItem instanceof ShieldItem shield) {
            float f = 0.25F + (float) EnchantmentHelper.getEfficiency(player) * 0.05F;
            if (sprinting) {
                f += 0.75F;
            }
            if (player.getRandom().nextFloat() < f) {
                player.getItemCooldownManager().set(shield, 100);
                player.clearActiveItem();
                player.getWorld().sendEntityStatus(player, (byte) 30);
                callbackInfo.cancel();
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
}
