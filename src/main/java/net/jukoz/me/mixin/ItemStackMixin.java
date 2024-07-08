package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.jukoz.me.item.ModDataComponentTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow protected abstract <T extends TooltipAppender> void appendTooltip(ComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type);

    @Shadow public abstract Item getItem();

    @Inject(method = "getTooltip", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/DataComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/client/item/TooltipType;)V",
            ordinal = 0, shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)

    private void addCustomTooltips(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, @Local Consumer<Text> consumer){
        this.appendTooltip(ModDataComponentTypes.TEMPERATURE_DATA, context, consumer, type);
    }
}