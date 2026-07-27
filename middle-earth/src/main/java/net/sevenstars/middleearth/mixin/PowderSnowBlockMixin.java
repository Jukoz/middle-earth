package net.sevenstars.middleearth.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Unique
    private static final TagKey<Item> POWDER_SNOW_WALK_ON = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "powder_snow_walk_on")
    );

    @Inject(method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"), cancellable = true)
    private static void canWalkOnPowderSnowTag(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && entity instanceof LivingEntity livingEntity) {
            ItemStack feetStack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            if (feetStack.is(POWDER_SNOW_WALK_ON)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnowEntity(Entity entity, final CallbackInfoReturnable<Boolean> info) {
        if(entity instanceof LivingEntity livingEntity){
            AttributeMap container = livingEntity.getAttributes();
            if(container.hasAttribute(EntityAttributesME.POWDERED_SNOW_IMMUNITY) && container.getValue(EntityAttributesME.POWDERED_SNOW_IMMUNITY) != 0.0){
                info.setReturnValue(true);
            }
        }
    }
}
