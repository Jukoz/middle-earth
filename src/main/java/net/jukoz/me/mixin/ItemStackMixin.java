package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jukoz.me.item.ModDataComponentTypes;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TooltipAppender;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow protected abstract <T extends TooltipAppender> void appendTooltip(DataComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type);

    @Shadow public abstract Item getItem();

    @WrapOperation(method = "getTooltip",
            at = @At(value = "INVOKE",
                    ordinal = 0,
                    target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/DataComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/client/item/TooltipType;)V"),
            slice = @Slice(from = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/DataComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/client/item/TooltipType;)V"),
                            to = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/DataComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/client/item/TooltipType;)V")))

    private void addCustomTooltips(ItemStack instance, DataComponentType<?> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, Operation<Void> original){
        this.appendTooltip(ModDataComponentTypes.TEMPERATURE_DATA, context, textConsumer, type);
        this.appendTooltip(DataComponentTypes.TRIM, context, textConsumer, type);
    }
}
