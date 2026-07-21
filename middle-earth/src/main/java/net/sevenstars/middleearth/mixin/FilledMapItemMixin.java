package net.sevenstars.middleearth.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.FilledMapItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FilledMapItem.class)
public class FilledMapItemMixin {
    @Redirect(
            method = "updateColors",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getMapColor(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/MapColor;"
            )
    )
    private MapColor preventNullMapColor(BlockState state, BlockView world, BlockPos pos) {
        MapColor color = state.getMapColor(world, pos);
        //debug(state, world, pos);
        return color == null ? MapColor.CLEAR : color;
    }

    private void debug(BlockState state, BlockView world, BlockPos pos) {
        MapColor color = state.getMapColor(world, pos);

        if (color == null) {
            MiddleEarth.LOGGER.logError("Null map color at " +  pos+ " block " + state.toString());
        }
    }
}
