package net.sevenstars.middleearth.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.sevenstars.middleearth.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    private static final TagKey<net.minecraft.world.item.Item> ANVIL_ITEMS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "anvil_items")
    );

    @Inject(at = @At("RETURN"), method = "canCombine(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private static void canCombineStacks(ItemStack first, ItemStack second, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && (first.is(ANVIL_ITEMS) || second.is(ANVIL_ITEMS))) {
            cir.setReturnValue(false);
        }
    }
}
