package net.jukoz.me.world.features.foliages;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.special.ModLeavesBlock;
import net.jukoz.me.world.gen.ModTreeGeneration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class PalmFoliagePlacer extends FoliagePlacer {
    protected final int baseHeight;
    protected final IntProvider offset;
    protected final IntProvider baseRadius;
    protected final float acceleration;
    protected final float velocity;

    public static final Codec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.intRange(0,16).fieldOf("baseHeight").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseHeight;
            }), IntProvider.createValidatingCodec(0, 16).fieldOf("baseRadius").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseRadius;
            }), IntProvider.createValidatingCodec(-16, 16).fieldOf("offset").forGetter((trunkPlacer) -> {
                return trunkPlacer.offset;
            }), Codec.floatRange(-1.0f, 1.0f).fieldOf("acceleration").forGetter((trunkPlacer) -> {
                return trunkPlacer.acceleration;
            }), Codec.floatRange(-1.0f, 1.0f).fieldOf("velocity").forGetter((trunkPlacer) -> {
                return trunkPlacer.velocity;
            })).apply(instance, PalmFoliagePlacer::new));

    public PalmFoliagePlacer(int baseHeight, IntProvider baseRadius, IntProvider offset, float acceleration, float velocity) {
        super(baseRadius, offset);
        this.baseHeight = baseHeight;
        this.baseRadius = baseRadius;
        this.offset = offset;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModTreeGeneration.PALM_FOLIAGE;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        int count = 6 + (int) (Math.random() * 2);
        double angle = (float) (360 / count);
        double offsetAngle = Math.random() * 180;

        for (int i = 0; i < count; i++) {
            createArcBranch(world, placer, random, mutable, config, treeNode.getCenter().add(0, offset, 0), this.baseHeight,
                    (angle * i) + offsetAngle, this.acceleration, this.velocity);
        }

        count--;
        angle = (float) (360 / count);
        offsetAngle = Math.random() * 180;
        for (int i = 0; i < count; i++) {
            createArcBranch(world, placer, random, mutable, config, treeNode.getCenter().add(0, offset, 0), this.baseHeight - 1,
                    (angle * i) + offsetAngle, this.acceleration - 0.05f, this.velocity + 1f);
        }
    }

    protected void createArcBranch(TestableWorld world, BlockPlacer placer, Random random, BlockPos.Mutable mutable,
                                                           TreeFeatureConfig config, BlockPos startPos, int height, double angle, float acceleration, float velocity) {
        if(height < 0) {
            height *= -1;
        }
        Vec3d dir = new Vec3d(Math.cos(angle), 0, Math.sin(angle)).normalize();
        Vec3d currentPos = new Vec3d(startPos.getX(), startPos.getY() - 1, startPos.getZ());
        float currentVel = velocity;

        for(int i = 0; i < height; i++) {
            currentPos = currentPos.add(new Vec3d(dir.x, Math.max(-1, currentVel), dir.z));
            mutable.set(currentPos.x, currentPos.y, currentPos.z);
            placeFoliageBlock(world, placer, random, config, mutable);
            currentVel += acceleration;
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 4;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx + dz >= ModLeavesBlock.MAX_DISTANCE;
    }
}
