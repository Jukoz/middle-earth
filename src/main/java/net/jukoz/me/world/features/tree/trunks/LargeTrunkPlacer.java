package net.jukoz.me.world.features.tree.trunks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.world.gen.ModTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

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
    protected TrunkPlacerType<?> getType() {
        return ModTreeGeneration.LARGE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random,
                                                 int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();
        setToDirt(world, replacer, random, blockPos, config);
        setToDirt(world, replacer, random, blockPos.east(), config);
        setToDirt(world, replacer, random, blockPos.south(), config);
        setToDirt(world, replacer, random, blockPos.south().east(), config);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        List<FoliagePlacer.TreeNode> treeNodes = createBranches(world, replacer, random, mutable, config, startPos, getHeight(random), baseRadius, tipRadius);
        createRoots(world, replacer, random, mutable, config, startPos, (int) (getHeight(random) / 3.5f), baseRadius * 0.9f, tipRadius);

        return ImmutableList.copyOf(treeNodes);
    }

    private List<FoliagePlacer.TreeNode> createBranches(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                                        TreeFeatureConfig config, BlockPos startPos, int height, float radiusA, float radiusB) {
        List<FoliagePlacer.TreeNode> treeNodes = new ArrayList<>();
        float heightProgress = 0;
        List<BlockPos> currentTopBranches = List.of(startPos);
        for (int i = 0; i < iterations; i++) { // iterations (splitting branches)
            int currentHeight = (int) (height * heightProgress);
            float currentRadiusA = MathHelper.lerp(heightProgress, radiusA, radiusB);

            float step = (float)(i + 1) / iterations;
            heightProgress = (float) Math.pow(step, iterationPercentage);

            currentHeight = (int) (height * heightProgress) - currentHeight;
            float currentRadiusB = MathHelper.lerp(1 - heightProgress, radiusB, radiusA);

            List<BlockPos> newTopBranches = new ArrayList<>();
            for (BlockPos currentTopBranch : currentTopBranches) {
                double angle = Math.random() * (360 / Math.PI);
                double angle2 = angle + 80 + (Math.random() * (100 / Math.PI));
                int tempHeight = currentHeight;
                if (i == (iterations - 1)) {
                    tempHeight += -randomHeight + Math.random() * (randomHeight * 2);
                }
                newTopBranches.add(createBranch(world, replacer, random, mutable, config, currentTopBranch, tempHeight, angle, currentRadiusA, currentRadiusB));
                newTopBranches.add(createBranch(world, replacer, random, mutable, config, currentTopBranch, tempHeight, angle2, currentRadiusA, currentRadiusB));
                if (i > 0 && i < (iterations - 1)) {
                    if (Math.random() < 0.45f) {
                        int index = newTopBranches.size() - 1;
                        treeNodes.add(new FoliagePlacer.TreeNode(newTopBranches.get(index), 0, false));
                        newTopBranches.remove(index);
                    }
                }
            }
            currentTopBranches = newTopBranches;
        }

        for(BlockPos pos : currentTopBranches) {
            treeNodes.add(new FoliagePlacer.TreeNode(pos, 0, false));
        }
        return treeNodes;
    }

    protected void createRoots(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                             TreeFeatureConfig config, BlockPos startPos, int height, float radiusA, float radiusB) {
        int rootsNb = 4 + (int)(Math.random() * 3);
        startPos = startPos.add(0, (int) (height * 0.6f), 0);
        double angle = Math.random() * (360/Math.PI);
        for (int i = 0; i < rootsNb; i++) {
            createBranch(world, replacer, random, mutable, config, startPos, -height, angle, radiusA, radiusB);
            angle = angle + (float)(360 / (rootsNb + 1)) -5 + (Math.random() * 10);
        }
    }


    protected BlockPos createBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                  TreeFeatureConfig config, BlockPos startPos, int height, double direction, float radiusA, float radiusB) {
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
            offsetX = (MathHelper.lerp(percentage, 0, (float) Math.cos(direction)) * this.velocity);
            offsetZ = (MathHelper.lerp(percentage, 0, (float) Math.sin(direction)) * this.velocity);
            for (int x = -ceilRadius; x <= ceilRadius; x++) {
                for (int z = -ceilRadius; z <= ceilRadius; z++) {
                    double dx = x;
                    double dz = z;
                    double distanceSquared = x * x + z * z;
                    distanceSquared += (Math.random() * -0.51f);

                    if (distanceSquared <= radius * radius) {
                        dx += offsetX;
                        dz += offsetZ;
                        this.setLog(world, replacer, random, mutable, config, startPos, (int) dx, i * multiplier, (int) dz);
                    }
                }
            }
            radius = MathHelper.lerp((float) i / height, radiusA, radiusB );
        }
        return new BlockPos(startPos).add((int) offsetX, multiplier * height, (int) offsetZ);
    }

    protected void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos,
                        TreeFeatureConfig config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.set(startPos, dx, dy, dz);
        this.trySetState(world, replacer, random, tmpPos, config);
    }
}
