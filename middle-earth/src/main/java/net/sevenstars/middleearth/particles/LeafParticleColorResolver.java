package net.sevenstars.middleearth.particles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class LeafParticleColorResolver {
    private static Resolver resolver = (state, level, pos) -> 0xFFFFFF;

    private LeafParticleColorResolver() {
    }

    public static int resolve(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        int color = resolver.resolve(state, level, pos);
        return color == -1 ? 0xFFFFFF : color;
    }

    public static void install(Resolver resolver) {
        LeafParticleColorResolver.resolver = resolver;
    }

    @FunctionalInterface
    public interface Resolver {
        int resolve(BlockState state, BlockAndTintGetter level, BlockPos pos);
    }
}
