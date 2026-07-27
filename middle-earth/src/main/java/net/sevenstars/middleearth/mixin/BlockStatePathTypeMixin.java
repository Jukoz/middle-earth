package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.extensions.IBlockStateExtension;
import net.sevenstars.middleearth.registries.RegistriesME;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockState.class)
public abstract class BlockStatePathTypeMixin implements IBlockStateExtension {
    @Override
    @Nullable
    public PathType getBlockPathType(BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        PathType registeredType = RegistriesME.getLandPathType(((BlockState) (Object) this).getBlock());
        return registeredType != null
                ? registeredType
                : IBlockStateExtension.super.getBlockPathType(level, pos, mob);
    }

    @Override
    @Nullable
    public PathType getAdjacentBlockPathType(
            BlockGetter level,
            BlockPos pos,
            @Nullable Mob mob,
            PathType originalType
    ) {
        PathType registeredType = RegistriesME.getLandPathType(((BlockState) (Object) this).getBlock());
        return registeredType != null
                ? registeredType
                : IBlockStateExtension.super.getAdjacentBlockPathType(level, pos, mob, originalType);
    }
}
