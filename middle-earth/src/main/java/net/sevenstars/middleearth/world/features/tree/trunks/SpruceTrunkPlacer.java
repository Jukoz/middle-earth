package net.sevenstars.middleearth.world.features.tree.trunks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.sevenstars.middleearth.world.gen.ModTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SpruceTrunkPlacer extends TrunkPlacer {

    protected final int baseHeight;
    protected final int randomHeight;

    public static final MapCodec<SpruceTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                Codec.intRange(0,90).fieldOf("base_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseHeight;
                }), Codec.intRange(0,16).fieldOf("random_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.randomHeight;
                })).apply(instance, SpruceTrunkPlacer::new);
    });

    public SpruceTrunkPlacer(int baseHeight, int randomHeight) {
        super(baseHeight, randomHeight, 0);
        this.baseHeight = baseHeight;
        this.randomHeight = randomHeight;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTreeGeneration.SPRUCE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random,
                                                 int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();
        setToDirt(world, replacer, random, blockPos, config);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        List<FoliagePlacer.TreeNode> treeNodes = createTrunk(world, replacer, random, mutable, config, startPos, getHeight(random));

        return ImmutableList.copyOf(treeNodes);
    }

    protected List<FoliagePlacer.TreeNode> createTrunk(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable mutable,
                                                       TreeFeatureConfig config, BlockPos startPos, int height) {
        for (int i = 0; i < height; ++i) {
            this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 0);
            if(i > 3 && i < height - 3 && i % 3 == 0) {
                for (int j = 1; j < 2; j++) {
                    Direction direction = Direction.byId((int) (Math.random() * 4) + 2);
                    Direction.Axis axis = Direction.Axis.X;
                    if(direction == Direction.NORTH || direction == Direction.SOUTH) {
                        axis = Direction.Axis.Z;
                    }
                    replacer.accept(startPos.up(i).offset(direction, j),
                            (BlockState) Function.identity().apply(config.trunkProvider
                                    .get(random, startPos.up(i).offset(direction, j))
                                    .with(PillarBlock.AXIS, axis)));
                }
            }
        }
        List<FoliagePlacer.TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(new FoliagePlacer.TreeNode(startPos.add(0, height, 0), 0, false));
        return treeNodes;
    }

    protected void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos,
                        TreeFeatureConfig config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.set(startPos, dx, dy, dz);
        this.trySetState(world, replacer, random, tmpPos, config);
    }
}
