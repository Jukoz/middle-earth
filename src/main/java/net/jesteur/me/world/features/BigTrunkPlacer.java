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
        //double radius = 2.5f;
        //final int square = (int) Math.ceil(radius);
        //for(int i = 0; i < height; ++i) {
        //    for (int dx = -1; dx <= square; dx++) {
        //        for (int dz = -1; dz <= square; dz++) {
        //            double radiusSquared = radius * radius;
        //            double x = dx - radius;
        //            double z = dz - radius;
        //            double distanceSquared = x * x + z * z;
        //            if(distanceSquared <= radiusSquared) this.setLog(world, replacer, random, mutable, config, startPos, dx, i, dz);
        //        }
        //    }
        //    float halfHeight = (float)height / 2;
        //    if(i < height / 2) {
        //        radius = lerp(1.6f, 2.5f, i / halfHeight);
        //    } else {
        //        radius = lerp(2.5f, 1.6f, (i - halfHeight) / halfHeight);
        //    }
        //}
        double radius = 2.7;
        final int ceilRadius = (int) Math.ceil(radius);
        for (int i = 0; i < height; ++i) {
            for (int dx = -ceilRadius; dx <= ceilRadius; dx++) {
                for (int dz = -ceilRadius; dz <= ceilRadius; dz++) {
                    //double x = dx + 0.5;
                    //double z = dz + 0.5;
                    double distanceSquared = dx * dx + dz * dz;
                    distanceSquared += (-0.45f + Math.random() * 0.5f);

                    if (distanceSquared <= radius * radius) {
                        this.setLog(world, replacer, random, mutable, config, startPos, dx, i, dz);
                    }
                }
            }

            float halfHeight = (float) height / 2;
            if (i < height * 0.4f) {
                radius = lerp(2.5f, 1.2f, (i / halfHeight));
            } else {
                radius = lerp(1.2f, 2.3f, ((i - halfHeight) / halfHeight));
            }
        }
        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, true));
    }


    public static double interpolate(double a, double b, float percentage) {
        double normalizedHeight = (double) percentage / a;
        double lerpFactor = Math.abs(normalizedHeight - 1);

        // Use linear interpolation to smoothly transition from A to B and back to A
        return a + lerpFactor * (b - a);
    }

    public static float lerp(float a, float b, float interpolation) {
        return a + interpolation * (b - a);
    }

    private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos, TreeFeatureConfig config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.set(startPos, dx, dy, dz);
        this.trySetState(world, replacer, random, tmpPos, config);
    }
}
