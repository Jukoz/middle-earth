package net.sevenstars.middleearth.world.features.tree.backport;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.sevenstars.middleearth.world.features.tree.ModTreeDecoratorType;

public class AttachedToLogsTreeDecorator extends TreeDecorator {
    public static final MapCodec<AttachedToLogsTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.floatRange(0.0F, 1.0F)
                                    .fieldOf("probability")
                                    .forGetter(decorator -> decorator.probability),
                            BlockStateProvider.CODEC
                                    .fieldOf("block_provider")
                                    .forGetter(decorator -> decorator.blockProvider),
                            ExtraCodecs.nonEmptyList(Direction.CODEC.listOf())
                                    .fieldOf("directions")
                                    .forGetter(decorator -> decorator.directions)
                    )
                    .apply(instance, AttachedToLogsTreeDecorator::new)
    );

    private final float probability;
    private final BlockStateProvider blockProvider;
    private final List<Direction> directions;

    public AttachedToLogsTreeDecorator(
            float probability,
            BlockStateProvider blockProvider,
            List<Direction> directions
    ) {
        this.probability = probability;
        this.blockProvider = blockProvider;
        this.directions = directions;
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        for (BlockPos logPos : Util.shuffledCopy(context.logs(), random)) {
            Direction direction = Util.getRandom(this.directions, random);
            BlockPos attachedPos = logPos.relative(direction);
            if (random.nextFloat() <= this.probability && context.isAir(attachedPos)) {
                context.setBlock(
                        attachedPos,
                        this.blockProvider.getState(random, attachedPos)
                );
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorType.ATTACHED_TO_LOGS;
    }
}
