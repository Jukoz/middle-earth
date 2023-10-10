package net.jesteur.me.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jesteur.me.world.gen.ModTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BigTrunkPlacer extends TrunkPlacer {
    public static final float TRUNK_ANGLE = 4.2f;
    public static final Codec<BigTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            fillTrunkPlacerFields(instance).apply(instance, BigTrunkPlacer::new));

    public BigTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
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

        double angle = Math.random() * (360/Math.PI);
        float radius = 2.7f;

        createBranch(world, replacer, random, mutable, config, startPos, height, angle, radius, 0.6f);

        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height)
                .add((int)(Math.cos(angle) * TRUNK_ANGLE), 0, (int)(Math.sin(angle) * TRUNK_ANGLE)), 0, true));
    }


    private void createBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                        TreeFeatureConfig config, BlockPos startPos, int height, double angle, float radiusA, float radiusB) {
        float radius = radiusA;
        final int ceilRadius = (int) Math.ceil(radiusA);
        for (int i = 0; i < height; ++i) {
            float percentage = (float) (Math.pow((float) i / height, 1.2));
            float offsetX = lerp((float) 0, (float) Math.cos(angle), percentage) * TRUNK_ANGLE;
            float offsetZ = lerp((float) 0, (float) Math.sin(angle), percentage) * TRUNK_ANGLE;
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
            radius = lerp(2.7f, radiusB, (float) i / height);
        }
    }

    public static float lerp(float a, float b, float interpolation) {
        return a + interpolation * (b - a);
    }

    private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos,
                        TreeFeatureConfig config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.set(startPos, dx, dy, dz);
        this.trySetState(world, replacer, random, tmpPos, config);
    }
}
