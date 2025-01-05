package net.jukoz.me.mixin;

import net.jukoz.me.MiddleEarth;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {

    @Inject(at = @At("HEAD"), method = "canCombineStacks", cancellable = true)
    private static void canCombineStacks(ItemStack first, ItemStack second, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(second.isOf(first.getItem())
                && first.getCount() == 1
                && second.getCount() == 1

                && first.contains(DataComponentTypes.MAX_DAMAGE)
                && second.contains(DataComponentTypes.MAX_DAMAGE)

                && first.contains(DataComponentTypes.DAMAGE)
                && second.contains(DataComponentTypes.DAMAGE)

                && !first.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "anvil_items")))
                && !second.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "anvil_items")))
                );
    }
}
