package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.sevenstars.middleearth.world.gen.structure.JigsawFreeSpace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructurePoolBasedGenerator.class)
public abstract class StructurePoolBasedGeneratorMixin {
    // The structure's bounds less its start piece
    @WrapOperation(method = "method_39824", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/shape/VoxelShapes;combineAndSimplify(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/function/BooleanBiFunction;)Lnet/minecraft/util/shape/VoxelShape;"))
    private static VoxelShape middleEarth$boxFreeSpace(VoxelShape bounds, VoxelShape start, BooleanBiFunction function, Operation<VoxelShape> original) {
        JigsawFreeSpace space = new JigsawFreeSpace(bounds.getBoundingBox());
        space.take(start.getBoundingBox());
        return space;
    }
}
