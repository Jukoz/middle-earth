package net.sevenstars.middleearth.world.features.tree.backport;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

public class FallenTreeFeature extends Feature<FallenTreeFeatureConfig> {
    public FallenTreeFeature(Codec<FallenTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenTreeFeatureConfig> context) {
        this.generate(context.config(), context.origin(), context.level(), context.random());
        return true;
    }

    private void generate(
            FallenTreeFeatureConfig config,
            BlockPos pos,
            WorldGenLevel world,
            RandomSource random
    ) {
        this.generateStump(config, world, random, pos.mutable());
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int length = config.logLength.sample(random) - 2;
        BlockPos.MutableBlockPos mutable = pos
                .relative(direction, 2 + random.nextInt(2))
                .mutable();
        this.moveToGroundPos(world, mutable);
        if (this.canPlaceLog(world, length, mutable, direction)) {
            this.generateLog(config, world, random, length, mutable, direction);
        }
    }

    private void moveToGroundPos(WorldGenLevel world, BlockPos.MutableBlockPos pos) {
        pos.move(Direction.UP, 1);

        for (int i = 0; i < 6; i++) {
            if (this.canReplaceAndHasSolidBelow(world, pos)) {
                return;
            }

            pos.move(Direction.DOWN);
        }
    }

    private void generateStump(
            FallenTreeFeatureConfig config,
            WorldGenLevel world,
            RandomSource random,
            BlockPos.MutableBlockPos pos
    ) {
        BlockPos stumpPos = this.setBlockStateAndGetPos(
                config,
                world,
                random,
                pos,
                Function.identity()
        );
        this.applyDecorators(world, random, Set.of(stumpPos), config.stumpDecorators);
    }

    private boolean canPlaceLog(
            WorldGenLevel world,
            int length,
            BlockPos.MutableBlockPos pos,
            Direction direction
    ) {
        int unsupportedBlocks = 0;

        for (int i = 0; i < length; i++) {
            if (!TreeFeature.validTreePos(world, pos)) {
                return false;
            }

            if (!this.isSolidBelow(world, pos)) {
                if (++unsupportedBlocks > 2) {
                    return false;
                }
            } else {
                unsupportedBlocks = 0;
            }

            pos.move(direction);
        }

        pos.move(direction.getOpposite(), length);
        return true;
    }

    private void generateLog(
            FallenTreeFeatureConfig config,
            WorldGenLevel world,
            RandomSource random,
            int length,
            BlockPos.MutableBlockPos pos,
            Direction direction
    ) {
        Set<BlockPos> logPositions = new HashSet<>();

        for (int i = 0; i < length; i++) {
            logPositions.add(this.setBlockStateAndGetPos(
                    config,
                    world,
                    random,
                    pos,
                    createAxisApplier(direction)
            ));
            pos.move(direction);
        }

        this.applyDecorators(world, random, logPositions, config.logDecorators);
    }

    private boolean canReplaceAndHasSolidBelow(WorldGenLevel world, BlockPos pos) {
        return TreeFeature.validTreePos(world, pos) && this.isSolidBelow(world, pos);
    }

    private boolean isSolidBelow(WorldGenLevel world, BlockPos pos) {
        return world.getBlockState(pos.below()).isFaceSturdy(world, pos, Direction.UP);
    }

    private BlockPos setBlockStateAndGetPos(
            FallenTreeFeatureConfig config,
            WorldGenLevel world,
            RandomSource random,
            BlockPos.MutableBlockPos pos,
            Function<BlockState, BlockState> stateFunction
    ) {
        world.setBlock(
                pos,
                stateFunction.apply(config.trunkProvider.getState(random, pos)),
                3
        );
        this.markAboveForPostProcessing(world, pos);
        return pos.immutable();
    }

    private void applyDecorators(
            WorldGenLevel world,
            RandomSource random,
            Set<BlockPos> positions,
            List<TreeDecorator> decorators
    ) {
        if (!decorators.isEmpty()) {
            TreeDecorator.Context context = new TreeDecorator.Context(
                    world,
                    this.createStatePlacer(world),
                    random,
                    positions,
                    Set.of(),
                    Set.of()
            );
            decorators.forEach(decorator -> decorator.place(context));
        }
    }

    private BiConsumer<BlockPos, BlockState> createStatePlacer(WorldGenLevel world) {
        return (pos, state) -> world.setBlock(pos, state, 19);
    }

    private static Function<BlockState, BlockState> createAxisApplier(Direction direction) {
        return state -> state.trySetValue(RotatedPillarBlock.AXIS, direction.getAxis());
    }
}
