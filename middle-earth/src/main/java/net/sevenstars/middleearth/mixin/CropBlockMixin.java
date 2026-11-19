package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @Inject(at = @At("TAIL"), method = "canPlantOnTop", cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.isIn(BlockTagsME.FARMLANDS)){
            cir.setReturnValue(true);
        }
    }

    // Vanilla only counts minecraft:farmland as soil, which left crops on the other farmlands at the slowest growth rate
    @WrapOperation(method = "getAvailableMoisture", at = @At(value = "INVOKE", ordinal = 0,
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private static boolean countCustomFarmland(BlockState soil, Block farmland, Operation<Boolean> original) {
        return original.call(soil, farmland) || (soil.isIn(BlockTagsME.FARMLANDS) && soil.getBlock() instanceof FarmlandBlock);
    }
}
