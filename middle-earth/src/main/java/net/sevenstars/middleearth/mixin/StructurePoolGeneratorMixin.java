package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.sevenstars.middleearth.world.gen.structure.JigsawFreeSpace;
import net.sevenstars.middleearth.world.gen.structure.RulePoolElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(targets = "net.minecraft.structure.pool.StructurePoolBasedGenerator$StructurePoolGenerator")
public abstract class StructurePoolGeneratorMixin {
    // One generator per structure, so these are counts within it
    @Unique
    private final Map<String, Integer> middleEarth$placed = new HashMap<>();

    // Drops rule pieces that are used up or at the wrong depth, and puts priority ones first
    @WrapOperation(method = "generatePiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/pool/StructurePool;getElementIndicesInRandomOrder(Lnet/minecraft/util/math/random/Random;)Ljava/util/List;"))
    private List<StructurePoolElement> middleEarth$applyRules(StructurePool pool, Random random, Operation<List<StructurePoolElement>> original, @Local(argsOnly = true) int depth) {
        List<StructurePoolElement> shuffled = original.call(pool, random);
        boolean rules = false;
        for(StructurePoolElement element : shuffled) {
            if(element instanceof RulePoolElement) {
                rules = true;
                break;
            }
        }
        if(!rules)
            return shuffled;
        List<StructurePoolElement> first = new ArrayList<>();
        List<StructurePoolElement> rest = new ArrayList<>();
        for(StructurePoolElement element : shuffled) {
            if(element instanceof RulePoolElement rule) {
                if(rule.isFull(middleEarth$placed.getOrDefault(rule.getName(), 0)) || !rule.allowsDepth(depth + 1))
                    continue;
                // Weight copies of a priority piece would only be tried again
                if(rule.isPriority()) {
                    if(!first.contains(rule))
                        first.add(rule);
                    continue;
                }
            }
            rest.add(element);
        }
        first.addAll(rest);
        return first;
    }

    @WrapOperation(method = "generatePiece", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private boolean middleEarth$countPlaced(List<Object> children, Object piece, Operation<Boolean> original) {
        if(piece instanceof PoolStructurePiece placed && placed.getPoolElement() instanceof RulePoolElement rule)
            middleEarth$placed.merge(rule.getName(), 1, Integer::sum);
        return original.call(children, piece);
    }

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
