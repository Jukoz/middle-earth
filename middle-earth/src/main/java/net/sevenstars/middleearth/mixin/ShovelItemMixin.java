package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Mutable
    @Final @Shadow protected static Map<Block, BlockState> PATH_STATES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addModdedDirtBlocks(CallbackInfo ci) {
        Map<Block, BlockState> pathStates = PATH_STATES;

        pathStates.put(ModBlocks.DRY_DIRT, Blocks.DIRT_PATH.getDefaultState());
        pathStates.put(ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.LOAM_PATH.getDefaultState());
        pathStates.put(ModBlocks.LOAM, ModBlocks.LOAM_PATH.getDefaultState());
        pathStates.put(ModBlocks.GRASSY_LOAM, ModBlocks.LOAM_PATH.getDefaultState());
        pathStates.put(ModBlocks.COARSE_LOAM, ModBlocks.LOAM_PATH.getDefaultState());
        pathStates.put(ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.PEAT_PATH.getDefaultState());
        pathStates.put(ModBlocks.PEAT, ModBlocks.PEAT_PATH.getDefaultState());
        pathStates.put(ModBlocks.GRASSY_PEAT, ModBlocks.PEAT_PATH.getDefaultState());
        pathStates.put(ModBlocks.COARSE_PEAT, ModBlocks.PEAT_PATH.getDefaultState());
        pathStates.put(ModBlocks.SILT_GRASS_BLOCK, ModBlocks.SILT_PATH.getDefaultState());
        pathStates.put(ModBlocks.SILT, ModBlocks.SILT_PATH.getDefaultState());
        pathStates.put(ModBlocks.GRASSY_SILT, ModBlocks.SILT_PATH.getDefaultState());
        pathStates.put(ModBlocks.COARSE_SILT, ModBlocks.SILT_PATH.getDefaultState());
        pathStates.put(ModBlocks.DIRTY_ROOTS, Blocks.DIRT_PATH.getDefaultState());
        pathStates.put(ModBlocks.GRASSY_DIRT, Blocks.DIRT_PATH.getDefaultState());
        pathStates.put(ModBlocks.TURF, Blocks.DIRT_PATH.getDefaultState());

        PATH_STATES = pathStates;
    }
}
