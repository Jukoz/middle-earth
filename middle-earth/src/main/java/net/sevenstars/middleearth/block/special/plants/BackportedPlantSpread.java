package net.sevenstars.middleearth.block.special.plants;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

final class BackportedPlantSpread {
    private BackportedPlantSpread() {
    }

    static boolean canSpread(LevelReader level, BlockPos pos, BlockState state) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos candidate = pos.relative(direction);
            if (level.isEmptyBlock(candidate) && state.canSurvive(level, candidate)) {
                return true;
            }
        }
        return false;
    }

    static Optional<BlockPos> findSpreadPosition(Level level, BlockPos pos, BlockState state) {
        for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(level.random)) {
            BlockPos candidate = pos.relative(direction);
            if (level.isEmptyBlock(candidate) && state.canSurvive(level, candidate)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }
}
