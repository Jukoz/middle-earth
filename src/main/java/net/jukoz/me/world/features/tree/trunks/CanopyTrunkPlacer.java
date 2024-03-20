package net.jukoz.me.world.features.tree.trunks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.world.gen.ModTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CanopyTrunkPlacer extends TrunkPlacer {
    protected final int baseHeight;
    protected final int randomHeight;
    protected final float  baseRadius;
    protected final float  tipRadius;
    protected final float velocity;
    protected final int iterations;
    protected final float iteration_percentage;
    protected final float trunk_noise;
    protected final int roots_offset;
    protected final int straight_trunk;

    public static final Codec<CanopyTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.intRange(0,90).fieldOf("base_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseHeight;
                }), Codec.intRange(0,16).fieldOf("random_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.randomHeight;
                }), Codec.floatRange(0,16).fieldOf("base_radius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseRadius;
                }), Codec.floatRange(0,16).fieldOf("tip_radius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.tipRadius;
                }), Codec.floatRange(0.0f, 16.0f).fieldOf("velocity").forGetter((trunkPlacer) -> {
                    return trunkPlacer.velocity;
                }), Codec.intRange(1,8).fieldOf("iteration").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iterations;
                }), Codec.floatRange(0.0f, 1.0f).fieldOf("iteration_percentage").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iteration_percentage;
                }), Codec.floatRange(-1.0f, 1.0f).fieldOf("trunk_noise").forGetter((trunkPlacer) -> {
                    return trunkPlacer.trunk_noise;
                }), Codec.intRange(-8, 8).fieldOf("roots_offset").forGetter((trunkPlacer) -> {
                    return trunkPlacer.roots_offset;
                }), Codec.intRange(0, 1).fieldOf("straight_trunk").forGetter((trunkPlacer) -> {
                    return trunkPlacer.straight_trunk;
                })).apply(instance, CanopyTrunkPlacer::new);
    });

    public CanopyTrunkPlacer(int baseHeight, int randomHeight, float baseRadius, float tipRadius, float velocity,
                             int iterations, float iteration_percentage, float trunk_noise, int roots_offset, int straight_trunk) {
        super(baseHeight, randomHeight, 0);
        this.baseHeight = baseHeight;
        this.randomHeight = randomHeight;
        this.baseRadius = baseRadius;
        this.tipRadius = tipRadius;
        this.velocity = velocity;
        this.iterations = iterations;
        this.iteration_percentage = iteration_percentage;
        this.trunk_noise = trunk_noise;
        this.roots_offset = roots_offset;
        this.straight_trunk = straight_trunk;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTreeGeneration.CANOPY_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();
        setToDirt(world, replacer, random, blockPos, config);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        float heightProgress = (float) Math.pow((float) 1 / iterations, iteration_percentage);


        BlockPos newPos = new BlockPos(startPos).add(0, (int)(height * heightProgress), 0);
        List<FoliagePlacer.TreeNode> treeNodes = createCircleBranches(world, replacer, random, mutable, config,
                newPos, (int) (getHeight(random) - (height * heightProgress)), MathHelper.lerp(heightProgress, baseRadius, tipRadius), tipRadius);

        float direction = 0;
        float velocity = 0;
        if(straight_trunk == 0) {
            direction = (float) (Math.random() * (360 / Math.PI));
            velocity = this.velocity;
        }
        FoliagePlacer.TreeNode treeNode = new FoliagePlacer.TreeNode(createBranch(world, replacer, random, mutable, config,
                startPos, height, direction, velocity, baseRadius, tipRadius), 1, false);
        treeNodes.add(treeNode);

        createRoots(world, replacer, random, mutable, config, startPos.add(0, this.roots_offset, 0), (int) (getHeight(random) / 2.5f), baseRadius * 0.95f, tipRadius);

        return ImmutableList.copyOf(treeNodes);
    }

    private List<FoliagePlacer.TreeNode> createCircleBranches(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                                        TreeFeatureConfig config, BlockPos startPos, int height, float radiusA, float radiusB) {
        List<FoliagePlacer.TreeNode> treeNodes = new ArrayList<>();
        List<BlockPos> lastTopBranches = List.of(startPos);
        float heightProgress = 0;

        for (int i = 0; i < iterations; i++) {
            List<BlockPos> currentTopBranches = new ArrayList<>();
            int currentHeight = (int) (height * heightProgress);
            float currentRadiusA = MathHelper.lerp(heightProgress, radiusA, radiusB);

            float step = (float)(i + 1) / iterations;
            heightProgress = (float) Math.pow(step, iteration_percentage);

            currentHeight = (int) (height * heightProgress) - currentHeight;
            float currentRadiusB = MathHelper.lerp(1 - heightProgress, radiusB, radiusA);

            for (BlockPos topBranchBlock : lastTopBranches) {
                BlockPos newTopPos = topBranchBlock;
                int count = 2;
                double angle = (float) (360 / count);
                double offsetAngle = Math.random() * 180;
                if (i == 0) {
                    count = Math.max(1, 5 + (int) (Math.random() * 2));
                }

                for (int k = 0; k < count; k++) {
                    if(i == 0) {
                        newTopPos = new BlockPos(topBranchBlock).add(0, (int) (-2 + (Math.random() * 5)), 0);
                    }
                    double angle2 = (angle * k) + offsetAngle;
                    if(currentRadiusA <= 1) {
                        currentTopBranches.add(createLinearBranch(world, replacer, random, mutable, config, newTopPos,
                            currentHeight, angle2, this.velocity, currentRadiusA, currentRadiusB));
                    } else {
                        currentTopBranches.add(createBranch(world, replacer, random, mutable, config, newTopPos,
                            currentHeight, angle2, this.velocity, currentRadiusA, currentRadiusB));
                    }

                    if (Math.random() < 0.4f) {
                        int index = currentTopBranches.size() - 1;
                        treeNodes.add(new FoliagePlacer.TreeNode(currentTopBranches.get(index), 0, false));
                        currentTopBranches.remove(index);
                    }
                }
            }
            lastTopBranches = currentTopBranches;
        }

        for(BlockPos pos : lastTopBranches) {
            treeNodes.add(new FoliagePlacer.TreeNode(pos, 0, false));
        }
        return treeNodes;
    }

    protected void createRoots(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                               TreeFeatureConfig config, BlockPos startPos, int height, float radiusA, float radiusB) {
        int rootsNb = 5 + (int)(Math.random() * 2);
        startPos = startPos.add(0, (int) (height * 0.6f), 0);
        double direction = Math.random() * (360/Math.PI);
        for (int i = 0; i < rootsNb; i++) {
            createBranch(world, replacer, random, mutable, config, startPos, -height, direction, this.velocity / 2, radiusA, radiusB);
            direction = direction + (float)(360 / (rootsNb + 1)) -5 + (Math.random() * 10);
        }
    }

    protected BlockPos createLinearBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                    TreeFeatureConfig config, BlockPos startPos, int height, double angle, float velocity, float radiusA, float radiusB) {
        if(height < 0) {
            height *= -1;
        }
        Vec3d dir = new Vec3d(Math.cos(angle), 1, Math.sin(angle)); //.normalize()
        Vec3d currentPos = new Vec3d(startPos.getX(), startPos.getY(), startPos.getZ());

        int iterations = (int) (height / dir.y);
        for (int i = 0; i < iterations; ++i) {
            this.setLog(world, replacer, random, mutable, config, new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z),
                    0, 0, 0);
            currentPos = currentPos.add(dir);
        }
        return new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z);
    }


    protected BlockPos createBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                    TreeFeatureConfig config, BlockPos startPos, int height, double direction, float velocity, float radiusA, float radiusB) {
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
            offsetX = (MathHelper.lerp(percentage, 0, (float) Math.cos(direction)) * velocity);
            offsetZ = (MathHelper.lerp(percentage, 0, (float) Math.sin(direction)) * velocity);

            for (int x = -ceilRadius; x <= ceilRadius; x++) {
                for (int z = -ceilRadius; z <= ceilRadius; z++) {
                    double dx = x;
                    double dz = z;
                    double distanceSquared = x * x + z * z;
                    distanceSquared += (Math.random() * this.trunk_noise);

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
