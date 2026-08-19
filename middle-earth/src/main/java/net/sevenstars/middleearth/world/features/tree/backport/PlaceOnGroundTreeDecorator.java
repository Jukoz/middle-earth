package net.sevenstars.middleearth.world.features.tree.backport;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.sevenstars.middleearth.world.features.tree.ModTreeDecoratorType;

public class PlaceOnGroundTreeDecorator extends TreeDecorator {
    public static final MapCodec<PlaceOnGroundTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            ExtraCodecs.POSITIVE_INT
                                    .fieldOf("tries")
                                    .orElse(128)
                                    .forGetter(decorator -> decorator.tries),
                            ExtraCodecs.NON_NEGATIVE_INT
                                    .fieldOf("radius")
                                    .orElse(2)
                                    .forGetter(decorator -> decorator.radius),
                            ExtraCodecs.NON_NEGATIVE_INT
                                    .fieldOf("height")
                                    .orElse(1)
                                    .forGetter(decorator -> decorator.height),
                            BlockStateProvider.CODEC
                                    .fieldOf("block_state_provider")
                                    .forGetter(decorator -> decorator.blockStateProvider)
                    )
                    .apply(instance, PlaceOnGroundTreeDecorator::new)
    );

    private final int tries;
    private final int radius;
    private final int height;
    private final BlockStateProvider blockStateProvider;

    public PlaceOnGroundTreeDecorator(
            int tries,
            int radius,
            int height,
            BlockStateProvider blockStateProvider
    ) {
        this.tries = tries;
        this.radius = radius;
        this.height = height;
        this.blockStateProvider = blockStateProvider;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorType.PLACE_ON_GROUND;
    }

    @Override
    public void place(Context context) {
        List<BlockPos> positions = getLeafLitterPositions(context);
        if (!positions.isEmpty()) {
            BlockPos first = positions.get(0);
            int groundY = first.getY();
            int minX = first.getX();
            int maxX = first.getX();
            int minZ = first.getZ();
            int maxZ = first.getZ();

            for (BlockPos pos : positions) {
                if (pos.getY() == groundY) {
                    minX = Math.min(minX, pos.getX());
                    maxX = Math.max(maxX, pos.getX());
                    minZ = Math.min(minZ, pos.getZ());
                    maxZ = Math.max(maxZ, pos.getZ());
                }
            }

            RandomSource random = context.random();
            BoundingBox bounds = new BoundingBox(
                    minX,
                    groundY,
                    minZ,
                    maxX,
                    groundY,
                    maxZ
            ).inflatedBy(this.radius, this.height, this.radius);
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (int i = 0; i < this.tries; i++) {
                mutable.set(
                        random.nextIntBetweenInclusive(bounds.minX(), bounds.maxX()),
                        random.nextIntBetweenInclusive(bounds.minY(), bounds.maxY()),
                        random.nextIntBetweenInclusive(bounds.minZ(), bounds.maxZ())
                );
                this.placeOne(context, mutable);
            }
        }
    }

    private void placeOne(Context context, BlockPos pos) {
        BlockPos above = pos.above();
        if (context.level().isStateAtPosition(
                        above,
                        state -> state.isAir() || state.is(Blocks.VINE)
                )
                && context.level().isStateAtPosition(
                        pos,
                        PlaceOnGroundTreeDecorator::isOpaqueFullCube
                )
                && context.level()
                        .getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos)
                        .getY() <= above.getY()) {
            context.setBlock(
                    above,
                    this.blockStateProvider.getState(context.random(), above)
            );
        }
    }

    private static List<BlockPos> getLeafLitterPositions(Context context) {
        List<BlockPos> positions = new ArrayList<>();
        List<BlockPos> roots = context.roots();
        List<BlockPos> logs = context.logs();
        if (roots.isEmpty()) {
            positions.addAll(logs);
        } else if (!logs.isEmpty() && roots.get(0).getY() == logs.get(0).getY()) {
            positions.addAll(logs);
            positions.addAll(roots);
        } else {
            positions.addAll(roots);
        }
        return positions;
    }

    private static boolean isOpaqueFullCube(BlockState state) {
        return state.isSolidRender(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
    }
}
