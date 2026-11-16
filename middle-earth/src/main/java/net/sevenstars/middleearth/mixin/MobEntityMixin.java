package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    // A rider on a cave troll is well out of reach, so hostile mobs go for the troll they are sitting on instead
    @ModifyReturnValue(method = "getTarget", at = @At("RETURN"))
    private LivingEntity targetTrollOverRider(LivingEntity target) {
        Entity self = (Entity)(Object)this;
        if(self instanceof Monster && target instanceof PlayerEntity player && player.getVehicle() instanceof CaveTrollEntity troll
                && troll.isAlive() && !troll.hasPassenger(self)) {
            return troll;
        }
        return target;
    }
}
