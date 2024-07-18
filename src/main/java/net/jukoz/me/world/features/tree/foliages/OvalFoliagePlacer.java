package net.jukoz.me.world.features.tree.foliages;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.world.gen.ModTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.ArrayList;
import java.util.List;

public class OvalFoliagePlacer extends FoliagePlacer {
    protected final int baseHeight;
    protected final IntProvider offset;
    protected final IntProvider baseRadius;
    protected final ArrayList<BlockState> leaves;
    protected final float extraSize;

    public static final MapCodec<OvalFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.intRange(0,16).fieldOf("baseHeight").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseHeight;
            }), IntProvider.createValidatingCodec(-16, 16).fieldOf("offset").forGetter((trunkPlacer) -> {
                return trunkPlacer.offset;
            }), IntProvider.createValidatingCodec(0, 16).fieldOf("baseRadius").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseRadius;
            }), Codec.list(BlockState.CODEC).fieldOf("leaves").forGetter((trunkPlacer) -> {
                return trunkPlacer.leaves;
            }), Codec.floatRange(0.0f, 1.0f).fieldOf("extraSize").forGetter((trunkPlacer) -> {
                return trunkPlacer.extraSize;
            })).apply(instance, OvalFoliagePlacer::new));

    public OvalFoliagePlacer(int baseHeight, IntProvider offset, IntProvider baseRadius, List<BlockState> leaves, float extraRadius) {
        super(baseRadius, offset);
        this.baseHeight = baseHeight;
        this.offset = offset;
        this.baseRadius = baseRadius;
        this.leaves = new ArrayList<>(leaves);
        this.extraSize = extraRadius;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModTreeGeneration.OVAL_FOLIAGE;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(int x = -radius; x <= radius; ++x) {
            for(int z = -radius; z <= radius; ++z) {
                for(int y = -baseHeight; y <= baseHeight; ++y) {
                    if (!this.isPositionInvalid(random, x, offset + y, z, radius, false)) {
                        if (isPointInside(x, y, z, radius)) {
                            mutable.set(treeNode.getCenter(), x, offset + y, z);
                            placeLeavesBlock(world, placer, random, config, mutable, leaves);
                        }
                    }
                }
            }
        }
    }

    protected static boolean placeLeavesBlock(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                                               BlockPos pos, List<BlockState> leaves) {
        if (!TreeFeature.canReplace(world, pos)) {
            return false;
        } else {
            BlockState leavesBlock;
            if(leaves.isEmpty()) {
                leavesBlock = config.foliageProvider.get(random, pos);
            } else {
                leavesBlock = leaves.get(random.nextBetween(0, leaves.size() - 1));
            }

            if (leavesBlock.contains(Properties.WATERLOGGED)) {
                leavesBlock = leavesBlock.with(Properties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
            }

            placer.placeBlock(pos, leavesBlock);
            return true;
        }
    }

    private boolean isPointInside(int x, int y, int z, int radius) {
        float randomness = -0.35f + (float) (Math.random() * 0.7f);
        float squareRadius = (radius + extraSize + randomness) * (radius + extraSize + randomness);
        float squareHeight = (baseHeight + extraSize + randomness) * (baseHeight + extraSize + randomness);
        float deltaX = (float)(x*x) / squareRadius;
        float deltaY = (float)(y*y) / squareHeight;
        float deltaZ = (float)(z*z) / squareRadius;

        return (deltaX + deltaY + deltaZ <= 1);
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 4;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (dx + dz >= 7) {
            return true;
        } else {
            return dx * dx + dz * dz > (radius + extraSize) * (radius + extraSize);
        }
    }
}
