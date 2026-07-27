package net.sevenstars.middleearth.world.features.tree.roots;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.rootplacers.*;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.sevenstars.middleearth.world.features.tree.ModRootPlacerType;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class MirkwoodRootPlacer extends RootPlacer {
    public static final int field_38769 = 8;
    public static final int field_38770 = 15;

    public static final MapCodec<MirkwoodRootPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return rootPlacerParts(instance).and(MirkwoodRootPlacement.CODEC.fieldOf("mirkwood_root_placement").forGetter((rootPlacer) -> {
            return rootPlacer.mirkwoodRootPlacement;
        })).apply(instance, MirkwoodRootPlacer::new);
    });

    private final MirkwoodRootPlacement mirkwoodRootPlacement;

    public MirkwoodRootPlacer(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement, MirkwoodRootPlacement mirkwoodRootPlacement) {
        super(trunkOffsetY, rootProvider, aboveRootPlacement);
        this.mirkwoodRootPlacement = mirkwoodRootPlacement;
    }

    public boolean placeRoots(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos pos, BlockPos trunkPos, TreeConfiguration config) {
        List<BlockPos> list = Lists.newArrayList();
        BlockPos.MutableBlockPos mutable = pos.mutable();

        while(mutable.getY() < trunkPos.getY()) {
            if (!this.canPlaceRoot(world, mutable)) {
                return false;
            }

            mutable.move(Direction.UP);
        }

        list.add(trunkPos.below());
        Iterator var9 = Plane.HORIZONTAL.iterator();

        while(var9.hasNext()) {
            Direction direction = (Direction)var9.next();
            BlockPos blockPos = trunkPos.relative(direction);
            List<BlockPos> list2 = Lists.newArrayList();
            if (!this.canGrow(world, random, blockPos, direction, trunkPos, list2, 0)) {
                return false;
            }

            list.addAll(list2);
            list.add(trunkPos.relative(direction));
        }

        var9 = list.iterator();

        while(var9.hasNext()) {
            BlockPos blockPos2 = (BlockPos)var9.next();
            this.placeRoot(world, replacer, random, blockPos2, config);
        }

        return true;
    }

    private boolean canGrow(LevelSimulatedReader world, RandomSource random, BlockPos pos, Direction direction, BlockPos origin, List<BlockPos> offshootPositions, int rootLength) {
        int i = this.mirkwoodRootPlacement.maxRootLength();
        if (rootLength != i && offshootPositions.size() <= i) {
            List<BlockPos> list = this.getOffshootPositions(pos, direction, random, origin);
            Iterator var10 = list.iterator();

            while(var10.hasNext()) {
                BlockPos blockPos = (BlockPos)var10.next();
                if (this.canPlaceRoot(world, blockPos)) {
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

    protected List<BlockPos> getOffshootPositions(BlockPos pos, Direction direction, RandomSource random, BlockPos origin) {
        BlockPos blockPos = pos.below();
        BlockPos blockPos2 = pos.relative(direction);
        int i = pos.distManhattan(origin);
        int j = this.mirkwoodRootPlacement.maxRootWidth();
        float f = this.mirkwoodRootPlacement.randomSkewChance();
        if (i > j - 3 && i <= j) {
            return random.nextFloat() < f ? List.of(blockPos, blockPos2.below()) : List.of(blockPos);
        } else if (i > j) {
            return List.of(blockPos);
        } else if (random.nextFloat() < f) {
            return List.of(blockPos);
        } else {
            return random.nextBoolean() ? List.of(blockPos2) : List.of(blockPos);
        }
    }

    protected boolean canPlaceRoot(LevelSimulatedReader world, BlockPos pos) {
        return super.canPlaceRoot(world, pos) || world.isStateAtPosition(pos, (state) -> {
            return state.is(this.mirkwoodRootPlacement.canGrowThrough());
        });
    }

    protected void placeRoot(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos pos, TreeConfiguration config) {
        if (world.isStateAtPosition(pos, (state) -> {
            return state.is(this.mirkwoodRootPlacement.muddyRootsIn());
        })) {
            BlockState blockState = this.mirkwoodRootPlacement.muddyRootsProvider().getState(random, pos);
            replacer.accept(pos, this.getPotentiallyWaterloggedState(world, pos, blockState));
        } else {
            super.placeRoot(world, replacer, random, pos, config);
        }

    }

    protected RootPlacerType<?> type() {
        return ModRootPlacerType.MIRKWOOD_ROOT_PLACER;
    }
}
