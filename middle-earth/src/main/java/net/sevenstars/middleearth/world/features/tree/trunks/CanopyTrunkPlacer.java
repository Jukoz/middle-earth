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
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.world.gen.ModTreeGeneration;
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

    public static final MapCodec<CanopyTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
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
    protected TrunkPlacerType<?> type() {
        return ModTreeGeneration.CANOPY_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        BlockPos blockPos = startPos.below();
        setDirtAt(world, replacer, random, blockPos, config);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        float heightProgress = (float) Math.pow((float) 1 / iterations, iteration_percentage);


        BlockPos newPos = new BlockPos(startPos).offset(0, (int)(height * heightProgress), 0);
        List<FoliagePlacer.FoliageAttachment> treeNodes = createCircleBranches(world, replacer, random, mutable, config,
                newPos, (int) (getTreeHeight(random) - (height * heightProgress)), Mth.lerp(heightProgress, baseRadius, tipRadius), tipRadius);

        float direction = 0;
        float velocity = 0;
        if(straight_trunk == 0) {
            direction = (float) (random.nextDouble() * (360 / Math.PI));
            velocity = this.velocity;
        }
        FoliagePlacer.FoliageAttachment treeNode = new FoliagePlacer.FoliageAttachment(createBranch(world, replacer, random, mutable, config,
                startPos, height, direction, velocity, baseRadius, tipRadius), 1, false);
        treeNodes.add(treeNode);

        createRoots(world, replacer, random, mutable, config, startPos.offset(0, this.roots_offset, 0), (int) (getTreeHeight(random) / 2.5f), baseRadius * 0.95f, tipRadius);

        return ImmutableList.copyOf(treeNodes);
    }

    private List<FoliagePlacer.FoliageAttachment> createCircleBranches(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                                        TreeConfiguration config, BlockPos startPos, int height, float radiusA, float radiusB) {
        List<FoliagePlacer.FoliageAttachment> treeNodes = new ArrayList<>();
        List<BlockPos> lastTopBranches = List.of(startPos);
        float heightProgress = 0;

        for (int i = 0; i < iterations; i++) {
            List<BlockPos> currentTopBranches = new ArrayList<>();
            int currentHeight = (int) (height * heightProgress);
            float currentRadiusA = Mth.lerp(heightProgress, radiusA, radiusB);

            float step = (float)(i + 1) / iterations;
            heightProgress = (float) Math.pow(step, iteration_percentage);

            currentHeight = (int) (height * heightProgress) - currentHeight;
            float currentRadiusB = Mth.lerp(1 - heightProgress, radiusB, radiusA);

            for (BlockPos topBranchBlock : lastTopBranches) {
                BlockPos newTopPos = topBranchBlock;
                int count = 2;
                double angle = (float) (360 / count);
                double offsetAngle = random.nextDouble() * 180;
                if (i == 0) {
                    count = Math.max(1, 5 + (int) (random.nextDouble() * 2));
                }

                for (int k = 0; k < count; k++) {
                    if(i == 0) {
                        newTopPos = new BlockPos(topBranchBlock).offset(0, (int) (-2 + (random.nextDouble() * 5)), 0);
                    }
                    double angle2 = (angle * k) + offsetAngle;
                    if(currentRadiusA <= 1) {
                        currentTopBranches.add(createLinearBranch(world, replacer, random, mutable, config, newTopPos,
                            currentHeight, angle2, this.velocity, currentRadiusA, currentRadiusB));
                    } else {
                        currentTopBranches.add(createBranch(world, replacer, random, mutable, config, newTopPos,
                            currentHeight, angle2, this.velocity, currentRadiusA, currentRadiusB));
                    }

                    if (random.nextDouble() < 0.4f) {
                        int index = currentTopBranches.size() - 1;
                        treeNodes.add(new FoliagePlacer.FoliageAttachment(currentTopBranches.get(index), 0, false));
                        currentTopBranches.remove(index);
                    }
                }
            }
            lastTopBranches = currentTopBranches;
        }

        for(BlockPos pos : lastTopBranches) {
            treeNodes.add(new FoliagePlacer.FoliageAttachment(pos, 0, false));
        }
        return treeNodes;
    }

    protected void createRoots(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                               TreeConfiguration config, BlockPos startPos, int height, float radiusA, float radiusB) {
        int rootsNb = 5 + (int)(random.nextDouble() * 2);
        startPos = startPos.offset(0, (int) (height * 0.6f), 0);
        double direction = random.nextDouble() * (360/Math.PI);
        for (int i = 0; i < rootsNb; i++) {
            createBranch(world, replacer, random, mutable, config, startPos, -height, direction, this.velocity / 2, radiusA, radiusB);
            direction = direction + (float)(360 / (rootsNb + 1)) -5 + (random.nextDouble() * 10);
        }
    }

    protected BlockPos createLinearBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                    TreeConfiguration config, BlockPos startPos, int height, double angle, float velocity, float radiusA, float radiusB) {
        if(height < 0) {
            height *= -1;
        }
        Vec3 dir = new Vec3(Math.cos(angle), 1, Math.sin(angle)); //.normalize()
        Vec3 currentPos = new Vec3(startPos.getX(), startPos.getY(), startPos.getZ());

        int iterations = (int) (height / dir.y);
        for (int i = 0; i < iterations; ++i) {
            this.setLog(world, replacer, random, mutable, config, new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z),
                    0, 0, 0);
            currentPos = currentPos.add(dir);
        }
        return new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z);
    }


    protected BlockPos createBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                    TreeConfiguration config, BlockPos startPos, int height, double direction, float velocity, float radiusA, float radiusB) {
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
            offsetX = (Mth.lerp(percentage, 0, (float) Math.cos(direction)) * velocity);
            offsetZ = (Mth.lerp(percentage, 0, (float) Math.sin(direction)) * velocity);

            for (int x = -ceilRadius; x <= ceilRadius; x++) {
                for (int z = -ceilRadius; z <= ceilRadius; z++) {
                    double dx = x;
                    double dz = z;
                    double distanceSquared = x * x + z * z;
                    distanceSquared += (random.nextDouble() * this.trunk_noise);

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
