package net.sevenstars.middleearth.world.features.tree.trunks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.sevenstars.middleearth.world.gen.ModTreeGeneration;
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
    protected TrunkPlacerType<?> type() {
        return ModTreeGeneration.SPRUCE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random,
                                                 int height, BlockPos startPos, TreeConfiguration config) {
        BlockPos blockPos = startPos.below();
        setDirtAt(world, replacer, random, blockPos, config);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        List<FoliagePlacer.FoliageAttachment> treeNodes = createTrunk(world, replacer, random, mutable, config, startPos, getTreeHeight(random));

        return ImmutableList.copyOf(treeNodes);
    }

    protected List<FoliagePlacer.FoliageAttachment> createTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                                       TreeConfiguration config, BlockPos startPos, int height) {
        for (int i = 0; i < height; ++i) {
            this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 0);
            if(i > 3 && i < height - 3 && i % 3 == 0) {
                for (int j = 1; j < 2; j++) {
                    Direction direction = Direction.from3DDataValue((int) (random.nextDouble() * 4) + 2);
                    Direction.Axis axis = Direction.Axis.X;
                    if(direction == Direction.NORTH || direction == Direction.SOUTH) {
                        axis = Direction.Axis.Z;
                    }
                    replacer.accept(startPos.above(i).relative(direction, j),
                            (BlockState) Function.identity().apply(config.trunkProvider
                                    .getState(random, startPos.above(i).relative(direction, j))
                                    .setValue(RotatedPillarBlock.AXIS, axis)));
                }
            }
        }
        List<FoliagePlacer.FoliageAttachment> treeNodes = new ArrayList<>();
        treeNodes.add(new FoliagePlacer.FoliageAttachment(startPos.offset(0, height, 0), 0, false));
        return treeNodes;
    }

    protected void setLog(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos tmpPos,
                        TreeConfiguration config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.setWithOffset(startPos, dx, dy, dz);
        this.placeLogIfFree(world, replacer, random, tmpPos, config);
    }
}
