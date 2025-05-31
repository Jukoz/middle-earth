package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @WrapOperation(method = "canWalkOnPowderSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean canWalkOnPowderSnowTag(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "powder_snow_walk_on")));
    }

    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnowEntity(Entity entity, final CallbackInfoReturnable<Boolean> info) {
        if(entity instanceof LivingEntity livingEntity){
            if(livingEntity.getAttributes().getValue(ModEntityAttributes.POWDERED_SNOW_IMMUNITY) == 1.0){
                info.setReturnValue(true);
            }
        }
    }
}
