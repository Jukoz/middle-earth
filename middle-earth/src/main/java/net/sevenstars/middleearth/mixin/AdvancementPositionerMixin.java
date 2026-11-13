package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.advancement.AdvancementPositioner;
import net.minecraft.advancement.PlacedAdvancement;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryAdvancementOrder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Lays the Lapidary tab's branches out in a fixed order ({@link LapidaryAdvancementOrder}). */
@Mixin(AdvancementPositioner.class)
public class AdvancementPositionerMixin {
    @WrapOperation(method = {"<init>", "findChildrenRecursively"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/advancement/PlacedAdvancement;getChildren()Ljava/lang/Iterable;"))
    private Iterable<PlacedAdvancement> middleearth$orderLapidary(PlacedAdvancement advancement,
                                                                  Operation<Iterable<PlacedAdvancement>> original) {
        return LapidaryAdvancementOrder.children(advancement, original.call(advancement));
    }
}
