package net.sevenstars.middleearth.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.sevenstars.middleearth.utils.SpawnUtil;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void canSpawnMixin(ServerWorld world, SpawnGroup group, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnSettings.SpawnEntry spawnEntry, BlockPos.Mutable pos, double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
        EntityType<?> type = spawnEntry.type();
        // Only checks if in the MEM dimension (TODO : make it more datadrivne)
        if (world.getRegistryKey().equals(ModDimensions.ME_DIMENSION_KEY) && !SpawnUtil.canCreatureSpawn(type, world, SpawnReason.NATURAL, pos, world.getRandom())) {
            cir.setReturnValue(false);
        }
    }
}
