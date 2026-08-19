package net.sevenstars.middleearth.world.features.tree.trunks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.sevenstars.middleearth.world.gen.ModTreeGeneration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class LargeTrunkPlacer extends TrunkPlacer {

    protected final int baseHeight;
    protected final int randomHeight;
    protected final float  baseRadius;
    protected final float  tipRadius;
    protected final float velocity;
    protected final int iterations;
    protected final float iterationPercentage;

    public static final MapCodec<LargeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                Codec.intRange(0,90).fieldOf("base_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseHeight;
                }), Codec.intRange(0,16).fieldOf("random_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.randomHeight;
                }), Codec.floatRange(0,16).fieldOf("base_hadius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseRadius;
                }), Codec.floatRange(0,16).fieldOf("tip_hadius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.tipRadius;
                }), Codec.floatRange(0.0f, 8.0f).fieldOf("velocity").forGetter((trunkPlacer) -> {
                    return trunkPlacer.velocity;
                }), Codec.intRange(1,8).fieldOf("iteration").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iterations;
                }), Codec.floatRange(0.0f, 1.0f).fieldOf("iteration_percentage").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iterationPercentage;
                })).apply(instance, LargeTrunkPlacer::new);
    });

    public LargeTrunkPlacer(int baseHeight, int randomHeight, float baseRadius, float tipRadius, float velocity, int iterations, float iterationPercentage) {
        super(baseHeight, randomHeight, 0);
        this.baseHeight = baseHeight;
        this.randomHeight = randomHeight;
        this.baseRadius = baseRadius;
        this.tipRadius = tipRadius;
        this.velocity = velocity;
        this.iterations = iterations;
        this.iterationPercentage = iterationPercentage;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTreeGeneration.LARGE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random,
                                                 int height, BlockPos startPos, TreeConfiguration config) {
        BlockPos blockPos = startPos.below();
        setDirtAt(world, replacer, random, blockPos, config);
        setDirtAt(world, replacer, random, blockPos.east(), config);
        setDirtAt(world, replacer, random, blockPos.south(), config);
        setDirtAt(world, replacer, random, blockPos.south().east(), config);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        List<FoliagePlacer.FoliageAttachment> treeNodes = createBranches(world, replacer, random, mutable, config, startPos, getTreeHeight(random), baseRadius, tipRadius);
        createRoots(world, replacer, random, mutable, config, startPos, (int) (getTreeHeight(random) / 3.5f), baseRadius * 0.9f, tipRadius);

        return ImmutableList.copyOf(treeNodes);
    }

    private List<FoliagePlacer.FoliageAttachment> createBranches(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                                        TreeConfiguration config, BlockPos startPos, int height, float radiusA, float radiusB) {
        List<FoliagePlacer.FoliageAttachment> treeNodes = new ArrayList<>();
        float heightProgress = 0;
        List<BlockPos> currentTopBranches = List.of(startPos);
        for (int i = 0; i < iterations; i++) { // iterations (splitting branches)
            int currentHeight = (int) (height * heightProgress);
            float currentRadiusA = Mth.lerp(heightProgress, radiusA, radiusB);

            float step = (float)(i + 1) / iterations;
            heightProgress = (float) Math.pow(step, iterationPercentage);

            currentHeight = (int) (height * heightProgress) - currentHeight;
            float currentRadiusB = Mth.lerp(1 - heightProgress, radiusB, radiusA);

            List<BlockPos> newTopBranches = new ArrayList<>();
            for (BlockPos currentTopBranch : currentTopBranches) {
                double angle = random.nextDouble() * (360 / Math.PI);
                double angle2 = angle + 80 + (random.nextDouble() * (100 / Math.PI));
                int tempHeight = currentHeight;
                if (i == (iterations - 1)) {
                    tempHeight += -randomHeight + random.nextDouble() * (randomHeight * 2);
                }
                newTopBranches.add(createBranch(world, replacer, random, mutable, config, currentTopBranch, tempHeight, angle, currentRadiusA, currentRadiusB));
                newTopBranches.add(createBranch(world, replacer, random, mutable, config, currentTopBranch, tempHeight, angle2, currentRadiusA, currentRadiusB));
                if (i > 0 && i < (iterations - 1)) {
                    if (random.nextDouble() < 0.45f) {
                        int index = newTopBranches.size() - 1;
                        treeNodes.add(new FoliagePlacer.FoliageAttachment(newTopBranches.get(index), 0, false));
                        newTopBranches.remove(index);
                    }
                }
            }
            currentTopBranches = newTopBranches;
        }

        for(BlockPos pos : currentTopBranches) {
            treeNodes.add(new FoliagePlacer.FoliageAttachment(pos, 0, false));
        }
        return treeNodes;
    }

    protected void createRoots(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                             TreeConfiguration config, BlockPos startPos, int height, float radiusA, float radiusB) {
        int rootsNb = 4 + (int)(random.nextDouble() * 3);
        startPos = startPos.offset(0, (int) (height * 0.6f), 0);
        double angle = random.nextDouble() * (360/Math.PI);
        for (int i = 0; i < rootsNb; i++) {
            createBranch(world, replacer, random, mutable, config, startPos, -height, angle, radiusA, radiusB);
            angle = angle + (float)(360 / (rootsNb + 1)) -5 + (random.nextDouble() * 10);
        }
    }


    protected BlockPos createBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                  TreeConfiguration config, BlockPos startPos, int height, double direction, float radiusA, float radiusB) {
        int multiplier = 1;
        if(height < 0) {
            multiplier = -1;
            height *= -1;
        }
        float radius = radiusA;
        final int ceilRadius = (int) Math.ceil(radiusA);
        float offsetX = 0;
        float offsetZ = 0;

        for (int i = 0; i < height; ++i) {
            float percentage = (float) (Math.pow((float) i / height, 1.2));
            offsetX = (Mth.lerp(percentage, 0, (float) Math.cos(direction)) * this.velocity);
            offsetZ = (Mth.lerp(percentage, 0, (float) Math.sin(direction)) * this.velocity);
            for (int x = -ceilRadius; x <= ceilRadius; x++) {
                for (int z = -ceilRadius; z <= ceilRadius; z++) {
                    double dx = x;
                    double dz = z;
                    double distanceSquared = x * x + z * z;
                    distanceSquared += (random.nextDouble() * -0.51f);

                    if (distanceSquared <= radius * radius) {
                        dx += offsetX;
                        dz += offsetZ;
                        this.setLog(world, replacer, random, mutable, config, startPos, (int) dx, i * multiplier, (int) dz);
                    }
                }
            }
            radius = Mth.lerp((float) i / height, radiusA, radiusB );
        }
        return new BlockPos(startPos).offset((int) offsetX, multiplier * height, (int) offsetZ);
    }

    protected void setLog(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos tmpPos,
                        TreeConfiguration config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.setWithOffset(startPos, dx, dy, dz);
        this.placeLogIfFree(world, replacer, random, tmpPos, config);
    }
}
