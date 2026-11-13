package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
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

        pathStates.put(BlockRegistryME.DRY_DIRT, Blocks.DIRT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.CHALKSOIL_GRASS_BLOCK, BlockRegistryME.CHALKSOIL_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.CHALKSOIL, BlockRegistryME.CHALKSOIL_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.GRASSY_CHALKSOIL, BlockRegistryME.CHALKSOIL_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.COARSE_CHALKSOIL, BlockRegistryME.CHALKSOIL_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.LOAM_GRASS_BLOCK, BlockRegistryME.LOAM_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.LOAM, BlockRegistryME.LOAM_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.GRASSY_LOAM, BlockRegistryME.LOAM_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.COARSE_LOAM, BlockRegistryME.LOAM_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.PEAT_GRASS_BLOCK, BlockRegistryME.PEAT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.PEAT, BlockRegistryME.PEAT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.GRASSY_PEAT, BlockRegistryME.PEAT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.COARSE_PEAT, BlockRegistryME.PEAT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.SILT_GRASS_BLOCK, BlockRegistryME.SILT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.SILT, BlockRegistryME.SILT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.GRASSY_SILT, BlockRegistryME.SILT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.COARSE_SILT, BlockRegistryME.SILT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.DIRTY_ROOTS, Blocks.DIRT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.GRASSY_DIRT, Blocks.DIRT_PATH.getDefaultState());
        pathStates.put(BlockRegistryME.TURF, Blocks.DIRT_PATH.getDefaultState());

        PATH_STATES = pathStates;
    }
}
