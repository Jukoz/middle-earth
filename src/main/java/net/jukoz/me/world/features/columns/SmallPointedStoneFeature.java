package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.DripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Iterator;
import java.util.Optional;

public class SmallPointedStoneFeature extends Feature<SmallPointedStoneFeatureConfig> {

    public SmallPointedStoneFeature(Codec<SmallPointedStoneFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<SmallPointedStoneFeatureConfig> context) {
        WorldAccess worldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        SmallPointedStoneFeatureConfig SmallPointedStoneFeatureConfig = context.getConfig();
        Optional<Direction> optional = getDirection(worldAccess, blockPos, random);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockPos2 = blockPos.offset((optional.get()).getOpposite());
            generateDripstoneBlocks(worldAccess, random, blockPos2, SmallPointedStoneFeatureConfig);
            int i = random.nextFloat() < SmallPointedStoneFeatureConfig.chanceOfTallerDripstone && ClusterFeature.canGenerate(worldAccess.getBlockState(blockPos.offset(optional.get()))) ? 2 : 1;
            ClusterFeature.generatePointedBlock(worldAccess, blockPos, optional.get(), i, false, context.getConfig().pointedBlockState);
            return true;
        }
    }

    private static Optional<Direction> getDirection(WorldAccess world, BlockPos pos, Random random) {
        boolean bl = ClusterFeature.canReplace(world.getBlockState(pos.up()));
        boolean bl2 = ClusterFeature.canReplace(world.getBlockState(pos.down()));
        if (bl && bl2) {
            return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
        } else if (bl) {
            return Optional.of(Direction.DOWN);
        } else {
            return bl2 ? Optional.of(Direction.UP) : Optional.empty();
        }
    }

    private static void generateDripstoneBlocks(WorldAccess world, Random random, BlockPos pos, SmallPointedStoneFeatureConfig config) {
        ClusterFeature.generateBlock(world, pos, config.blockState);
        Iterator var4 = Direction.Type.HORIZONTAL.iterator();

        while(var4.hasNext()) {
            Direction direction = (Direction)var4.next();
            if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
                BlockPos blockPos = pos.offset(direction);
                ClusterFeature.generateBlock(world, blockPos, config.blockState);
                if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
                    BlockPos blockPos2 = blockPos.offset(Direction.random(random));
                    ClusterFeature.generateBlock(world, blockPos2, config.blockState);
                    if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
                        BlockPos blockPos3 = blockPos2.offset(Direction.random(random));
                        ClusterFeature.generateBlock(world, blockPos3, config.blockState);
                    }
                }
            }
        }

    }
}
