package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @WrapOperation(method = "canWalkOnPowderSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean canWalkOnPowderSnowTag(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "powder_snow_walk_on")));
    }
}
