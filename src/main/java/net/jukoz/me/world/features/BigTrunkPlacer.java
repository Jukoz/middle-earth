package net.jukoz.me.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
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

public class BigTrunkPlacer extends TrunkPlacer {

    protected final int baseHeight;
    protected final int randomHeight;
    protected final float  baseRadius;
    protected final float  tipRadius;
    protected final float  angle;
    protected final int iterations;
    protected final float iteration_percentage;

    public static final Codec<BigTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.intRange(0,90).fieldOf("baseHeight").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseHeight;
                }), Codec.intRange(0,16).fieldOf("randomHeight").forGetter((trunkPlacer) -> {
                    return trunkPlacer.randomHeight;
                }), Codec.floatRange(0,16).fieldOf("baseRadius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseRadius;
                }), Codec.floatRange(0,16).fieldOf("tipRadius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.tipRadius;
                }), Codec.floatRange(0.0f, 8.0f).fieldOf("angle").forGetter((trunkPlacer) -> {
                    return trunkPlacer.angle;
                }), Codec.intRange(1,8).fieldOf("iteration").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iterations;
                }), Codec.floatRange(0.0f, 1.0f).fieldOf("iteration_percentage").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iteration_percentage;
                })).apply(instance, BigTrunkPlacer::new);
    });

    public BigTrunkPlacer(int baseHeight, int randomHeight, float baseRadius, float tipRadius, float angle, int iterations, float iteration_percentage) {
        super(baseHeight, randomHeight, 0);
        this.baseHeight = baseHeight;
        this.randomHeight = randomHeight;
        this.baseRadius = baseRadius;
        this.tipRadius = tipRadius;
        this.angle = angle;
        this.iterations = iterations;
        this.iteration_percentage = iteration_percentage;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTreeGeneration.RICH_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();
        setToDirt(world, replacer, random, blockPos, config);
        setToDirt(world, replacer, random, blockPos.east(), config);
        setToDirt(world, replacer, random, blockPos.south(), config);
        setToDirt(world, replacer, random, blockPos.south().east(), config);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        List<FoliagePlacer.TreeNode> treeNodes = createBranches(world, replacer, random, mutable, config, startPos, getHeight(random), baseRadius, tipRadius);
        return ImmutableList.copyOf(treeNodes);
    }

    private List<FoliagePlacer.TreeNode> createBranches(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                                        TreeFeatureConfig config, BlockPos startPos, int height, float radiusA, float radiusB) {
        List<FoliagePlacer.TreeNode> treeNodes = new ArrayList<>();
        float heightProgress = 0;
        List<BlockPos> currentTopBranches = List.of(startPos);
        for (int i = 0; i < iterations; i++) { // iterations (splitting branches)
            int currentHeight = (int) (height * heightProgress);
            float currentRadiusA = MathHelper.lerp(heightProgress, radiusA, radiusB); // radiusA * (1 - heightProgress);

            float step = (float)(i + 1) / iterations;
            heightProgress = (float) Math.pow(step, iteration_percentage);

            currentHeight = (int) (height * heightProgress) - currentHeight;
            float currentRadiusB = MathHelper.lerp(1 - heightProgress, radiusB, radiusA);

            List<BlockPos> newTopBranches = new ArrayList<>();
            for (int j = 0; j < currentTopBranches.size(); j++) {
                double angle = Math.random() * (360/Math.PI);
                double angle2 = angle + 80 + (Math.random() * (100/Math.PI));
                int tempHeight = currentHeight;
                if(i == (iterations - 1)) {
                    tempHeight += -randomHeight + Math.random() * (randomHeight * 2);
                }
                newTopBranches.add(createBranch(world, replacer, random, mutable, config, currentTopBranches.get(j), tempHeight, angle, currentRadiusA, currentRadiusB));
                newTopBranches.add(createBranch(world, replacer, random, mutable, config, currentTopBranches.get(j), tempHeight, angle2, currentRadiusA, currentRadiusB));
                if(i > 0 && i < (iterations - 1)) {
                    if(Math.random() < 0.45f) {
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


    private BlockPos createBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                  TreeFeatureConfig config, BlockPos startPos, int height, double direction, float radiusA, float radiusB) {
        float radius = radiusA;
        final int ceilRadius = (int) Math.ceil(radiusA);
        float offsetX = 0;
        float offsetZ = 0;
        for (int i = 0; i < height; ++i) {
            float percentage = (float) (Math.pow((float) i / height, 1.2));
            offsetX = (MathHelper.lerp(percentage, 0, (float) Math.cos(direction)) * this.angle);
            offsetZ = (MathHelper.lerp(percentage, 0, (float) Math.sin(direction)) * this.angle);
            for (int x = -ceilRadius; x <= ceilRadius; x++) {
                for (int z = -ceilRadius; z <= ceilRadius; z++) {
                    double dx = x;
                    double dz = z;
                    double distanceSquared = x * x + z * z;
                    distanceSquared += (Math.random() * -0.51f);

                    if (distanceSquared <= radius * radius) {
                        dx += offsetX;
                        dz += offsetZ;
                        this.setLog(world, replacer, random, mutable, config, startPos, (int) dx, i, (int) dz);
                    }
                }
            }
            radius = MathHelper.lerp((float) i / height, radiusA, radiusB );
        }
        return new BlockPos(startPos).add((int) offsetX, height, (int) offsetZ);
    }

    private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos,
                        TreeFeatureConfig config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.set(startPos, dx, dy, dz);
        this.trySetState(world, replacer, random, tmpPos, config);
    }
}
