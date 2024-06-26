package net.jukoz.me.mixin;

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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow protected abstract <T extends TooltipAppender> void appendTooltip(DataComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type);

    @Shadow public abstract Item getItem();

    @Inject(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "La/b/c/Something;doSomething()V"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "La/b/c/Something;doSomething2()V"),
                    to = @At(value = "INVOKE", target = "La/b/c/Something;doSomething3()V")
            )
    )
    private void additionalTooltip() {
        this.appendTooltip(DataComponentTypes.TRIM, context, consumer, type);
    }
}
