package net.sevenstars.middleearth.world.features.columns;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SmallPointedStoneFeature extends Feature<SmallPointedStoneFeatureConfig> {

    public SmallPointedStoneFeature(Codec<SmallPointedStoneFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<SmallPointedStoneFeatureConfig> context) {
        LevelAccessor worldAccess = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        SmallPointedStoneFeatureConfig SmallPointedStoneFeatureConfig = context.config();
        Optional<Direction> optional = getDirection(worldAccess, blockPos, random);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockPos2 = blockPos.relative((optional.get()).getOpposite());
            generateDripstoneBlocks(worldAccess, random, blockPos2, SmallPointedStoneFeatureConfig);
            int i = random.nextFloat() < SmallPointedStoneFeatureConfig.chanceOfTallerDripstone && ClusterFeature.canGenerate(worldAccess.getBlockState(blockPos.relative(optional.get()))) ? 2 : 1;
            ClusterFeature.generatePointedBlock(worldAccess, blockPos, optional.get(), i, false, context.config().pointedBlockState);
            return true;
        }
    }

    private static Optional<Direction> getDirection(LevelAccessor world, BlockPos pos, RandomSource random) {
        boolean bl = ClusterFeature.canReplace(world.getBlockState(pos.above()));
        boolean bl2 = ClusterFeature.canReplace(world.getBlockState(pos.below()));
        if (bl && bl2) {
            return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
        } else if (bl) {
            return Optional.of(Direction.DOWN);
        } else {
            return bl2 ? Optional.of(Direction.UP) : Optional.empty();
        }
    }

    private static void generateDripstoneBlocks(LevelAccessor world, RandomSource random, BlockPos pos, SmallPointedStoneFeatureConfig config) {
        ClusterFeature.generateBlock(world, pos, config.blockState);
        Iterator var4 = Direction.Plane.HORIZONTAL.iterator();

        while(var4.hasNext()) {
            Direction direction = (Direction)var4.next();
            if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
                BlockPos blockPos = pos.relative(direction);
                ClusterFeature.generateBlock(world, blockPos, config.blockState);
                if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
                    BlockPos blockPos2 = blockPos.relative(Direction.getRandom(random));
                    ClusterFeature.generateBlock(world, blockPos2, config.blockState);
                    if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
                        BlockPos blockPos3 = blockPos2.relative(Direction.getRandom(random));
                        ClusterFeature.generateBlock(world, blockPos3, config.blockState);
                    }
                }
            }
        }

    }
}
