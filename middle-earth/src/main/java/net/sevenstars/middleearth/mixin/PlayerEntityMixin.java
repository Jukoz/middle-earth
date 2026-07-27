package net.sevenstars.middleearth.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.utils.PlayerActionHandlingContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract Inventory getInventory();

    @Inject(
            method = "getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void middleEarth$hideOffhandForTwoHandedItems(
            EquipmentSlot slot,
            CallbackInfoReturnable<ItemStack> cir
    ) {
        if (slot != EquipmentSlot.OFFHAND
                || ((Object) this instanceof ServerPlayer && PlayerActionHandlingContext.isActive())) {
            return;
        }

        Inventory inventory = this.getInventory();
        if (isTwoHandedItem(inventory.getSelected())
                || isTwoHandedItem(inventory.getItem(Inventory.SLOT_OFFHAND))) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    @Unique
    private static boolean isTwoHandedItem(ItemStack stack) {
        return stack.getItem() instanceof ReachWeaponItem weaponItem && weaponItem.type.twoHanded
                || stack.getItem() instanceof CustomSiegeShieldItem
                || stack.getItem() instanceof CustomLongbowWeaponItem;
    }
}
