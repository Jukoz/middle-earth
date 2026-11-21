package net.sevenstars.middleearth.mixin;

import net.minecraft.block.JigsawBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(JigsawBlock.class)
public abstract class JigsawBlockMixin {
    // Face the builder, like a piston. Vanilla faces it away from the clicked block
    @Redirect(method = "getPlacementState", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemPlacementContext;getSide()Lnet/minecraft/util/math/Direction;"))
    private Direction middleEarth$faceTheBuilder(ItemPlacementContext ctx) {
        return ctx.getPlayerLookDirection().getOpposite();
    }
}
