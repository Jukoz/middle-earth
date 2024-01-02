package net.jukoz.me.world.features.roots;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import net.jukoz.me.world.features.ModRootPlacerType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.root.RootPlacer;
import net.minecraft.world.gen.root.RootPlacerType;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class MirkwoodRootPlacer extends RootPlacer {
    public static final int field_38769 = 8;
    public static final int field_38770 = 15;
    public static final Codec<MirkwoodRootPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return method_43182(instance).and(MirkwoodRootPlacement.CODEC.fieldOf("mirkwood_root_placement").forGetter((rootPlacer) -> {
            return rootPlacer.mirkwoodRootPlacement;
        })).apply(instance, MirkwoodRootPlacer::new);
    });
    private final MirkwoodRootPlacement mirkwoodRootPlacement;

    public MirkwoodRootPlacer(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement, MirkwoodRootPlacement mirkwoodRootPlacement) {
        super(trunkOffsetY, rootProvider, aboveRootPlacement);
        this.mirkwoodRootPlacement = mirkwoodRootPlacement;
    }

    public boolean generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, BlockPos trunkPos, TreeFeatureConfig config) {
        List<BlockPos> list = Lists.newArrayList();
        BlockPos.Mutable mutable = pos.mutableCopy();

        while(mutable.getY() < trunkPos.getY()) {
            if (!this.canGrowThrough(world, mutable)) {
                return false;
            }

            mutable.move(Direction.UP);
        }

        list.add(trunkPos.down());
        Iterator var9 = Type.HORIZONTAL.iterator();

        while(var9.hasNext()) {
            Direction direction = (Direction)var9.next();
            BlockPos blockPos = trunkPos.offset(direction);
            List<BlockPos> list2 = Lists.newArrayList();
            if (!this.canGrow(world, random, blockPos, direction, trunkPos, list2, 0)) {
                return false;
            }

            list.addAll(list2);
            list.add(trunkPos.offset(direction));
        }

        var9 = list.iterator();

        while(var9.hasNext()) {
            BlockPos blockPos2 = (BlockPos)var9.next();
            this.placeRoots(world, replacer, random, blockPos2, config);
        }

        return true;
    }

    private boolean canGrow(TestableWorld world, Random random, BlockPos pos, Direction direction, BlockPos origin, List<BlockPos> offshootPositions, int rootLength) {
        int i = this.mirkwoodRootPlacement.maxRootLength();
        if (rootLength != i && offshootPositions.size() <= i) {
            List<BlockPos> list = this.getOffshootPositions(pos, direction, random, origin);
            Iterator var10 = list.iterator();

            while(var10.hasNext()) {
                BlockPos blockPos = (BlockPos)var10.next();
                if (this.canGrowThrough(world, blockPos)) {
                    offshootPositions.add(blockPos);
                    if (!this.canGrow(world, random, blockPos, direction, origin, offshootPositions, rootLength + 1)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    protected List<BlockPos> getOffshootPositions(BlockPos pos, Direction direction, Random random, BlockPos origin) {
        BlockPos blockPos = pos.down();
        BlockPos blockPos2 = pos.offset(direction);
        int i = pos.getManhattanDistance(origin);
        int j = this.mirkwoodRootPlacement.maxRootWidth();
        float f = this.mirkwoodRootPlacement.randomSkewChance();
        if (i > j - 3 && i <= j) {
            return random.nextFloat() < f ? List.of(blockPos, blockPos2.down()) : List.of(blockPos);
        } else if (i > j) {
            return List.of(blockPos);
        } else if (random.nextFloat() < f) {
            return List.of(blockPos);
        } else {
            return random.nextBoolean() ? List.of(blockPos2) : List.of(blockPos);
        }
    }

    protected boolean canGrowThrough(TestableWorld world, BlockPos pos) {
        return super.canGrowThrough(world, pos) || world.testBlockState(pos, (state) -> {
            return state.isIn(this.mirkwoodRootPlacement.canGrowThrough());
        });
    }

    protected void placeRoots(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config) {
        if (world.testBlockState(pos, (state) -> {
            return state.isIn(this.mirkwoodRootPlacement.muddyRootsIn());
        })) {
            BlockState blockState = this.mirkwoodRootPlacement.muddyRootsProvider().get(random, pos);
            replacer.accept(pos, this.applyWaterlogging(world, pos, blockState));
        } else {
            super.placeRoots(world, replacer, random, pos, config);
        }

    }

    protected RootPlacerType<?> getType() {
        return ModRootPlacerType.MIRKWOOD_ROOT_PLACER;
    }
}
