package net.sevenstars.middleearth.mixin;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beds are low, so aiming above one hits the block behind. Holding the tool outlines the whole block
@Mixin(BedBlock.class)
public class BedBlockOutlineMixin {
    @Inject(method = "getOutlineShape", at = @At("HEAD"), cancellable = true)
    private void middleEarth$wholeBlockForTheTool(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if(context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof PlayerEntity player
                && (player.getMainHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL) || player.getOffHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL)))
            cir.setReturnValue(VoxelShapes.fullCube());
    }
}
