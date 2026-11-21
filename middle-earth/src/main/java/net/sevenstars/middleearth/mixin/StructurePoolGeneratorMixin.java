package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.sevenstars.middleearth.world.gen.structure.JigsawFreeSpace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.structure.pool.StructurePoolBasedGenerator$StructurePoolGenerator")
public abstract class StructurePoolGeneratorMixin {
    // A piece's own box, for children placed inside it
    @WrapOperation(method = "generatePiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/shape/VoxelShapes;cuboid(Lnet/minecraft/util/math/Box;)Lnet/minecraft/util/shape/VoxelShape;", ordinal = 0))
    private VoxelShape middleEarth$innerFreeSpace(Box box, Operation<VoxelShape> original) {
        return new JigsawFreeSpace(box);
    }

    @WrapOperation(method = "generatePiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/shape/VoxelShapes;matchesAnywhere(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/function/BooleanBiFunction;)Z"))
    private boolean middleEarth$outsideFreeSpace(VoxelShape free, VoxelShape candidate, BooleanBiFunction function, Operation<Boolean> original) {
        if(!(free instanceof JigsawFreeSpace space))
            return original.call(free, candidate, function);
        return !candidate.isEmpty() && !space.fits(candidate.getBoundingBox());
    }

    @WrapOperation(method = "generatePiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/shape/VoxelShapes;combine(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/function/BooleanBiFunction;)Lnet/minecraft/util/shape/VoxelShape;"))
    private VoxelShape middleEarth$takeFreeSpace(VoxelShape free, VoxelShape piece, BooleanBiFunction function, Operation<VoxelShape> original) {
        if(!(free instanceof JigsawFreeSpace space))
            return original.call(free, piece, function);
        space.take(piece.getBoundingBox());
        return space;
    }
}
