package net.sevenstars.middleearth.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    //TODO broken to fix
/*
    @Shadow protected abstract <T extends TooltipAppender> void appendTooltip(ComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type);

    @Shadow public abstract Item getItem();

    @WrapOperation(method = "getTooltip", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
            ordinal = 0))

    private void addCustomTooltips(ItemStack instance, ComponentType componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, Operation<Void> original, @Local Consumer<Text> consumer){
        this.appendTooltip(ModDataComponentTypes.TEMPERATURE_DATA, context, consumer, type);
    }*/
}