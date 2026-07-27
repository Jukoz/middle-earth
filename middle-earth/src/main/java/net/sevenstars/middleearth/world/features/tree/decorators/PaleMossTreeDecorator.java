package net.sevenstars.middleearth.world.features.tree.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.special.hangingstuff.CustomHangingBlock;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.tree.ModTreeDecoratorType;

import java.util.List;

public class PaleMossTreeDecorator extends TreeDecorator {
    public static final MapCodec<PaleMossTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.floatRange(0.0F, 1.0F)
                    .fieldOf("leaves_probability")
                    .forGetter(decorator -> decorator.leavesProbability),
            Codec.floatRange(0.0F, 1.0F)
                    .fieldOf("trunk_probability")
                    .forGetter(decorator -> decorator.trunkProbability),
            Codec.floatRange(0.0F, 1.0F)
                    .fieldOf("ground_probability")
                    .forGetter(decorator -> decorator.groundProbability)
    ).apply(instance, PaleMossTreeDecorator::new));

    private final float leavesProbability;
    private final float trunkProbability;
    private final float groundProbability;

    public PaleMossTreeDecorator(float leavesProbability, float trunkProbability, float groundProbability) {
        this.leavesProbability = leavesProbability;
        this.trunkProbability = trunkProbability;
        this.groundProbability = groundProbability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorType.PALE_MOSS;
    }

    @Override
    public void place(Context context) {
        if (context.logs().isEmpty()) {
            return;
        }

        RandomSource random = context.random();
        List<BlockPos> shuffledLogs = Util.shuffledCopy(context.logs(), random);
        BlockPos lowestLog = shuffledLogs.get(0);
        for (BlockPos log : shuffledLogs) {
            if (log.getY() < lowestLog.getY()) {
                lowestLog = log;
            }
        }

        if (random.nextFloat() < this.groundProbability) {
            placeGroundPatch(context, lowestLog.above());
        }

        for (BlockPos log : context.logs()) {
            tryDecorateBelow(context, random, log, this.trunkProbability);
        }
        for (BlockPos leaves : context.leaves()) {
            tryDecorateBelow(context, random, leaves, this.leavesProbability);
        }
    }

    private static void placeGroundPatch(Context context, BlockPos origin) {
        if (!(context.level() instanceof WorldGenLevel level)) {
            return;
        }

        level.registryAccess()
                .registry(Registries.CONFIGURED_FEATURE)
                .flatMap(registry -> registry.getHolder(ModTreeConfiguredFeatures.PALE_MOSS_PATCH_KEY))
                .ifPresent(feature -> feature.value().place(
                        level,
                        level.getLevel().getChunkSource().getGenerator(),
                        context.random(),
                        origin));
    }

    private static void tryDecorateBelow(Context context, RandomSource random, BlockPos source, float probability) {
        if (random.nextFloat() < probability) {
            BlockPos below = source.below();
            if (context.isAir(below)) {
                decorate(context, below);
            }
        }
    }

    private static void decorate(Context context, BlockPos start) {
        RandomSource random = context.random();
        BlockPos cursor = start;
        BlockState body = ModNatureBlocks.PALE_HANGING_MOSS.defaultBlockState()
                .setValue(CustomHangingBlock.TIP, false);
        BlockState tip = ModNatureBlocks.PALE_HANGING_MOSS.defaultBlockState()
                .setValue(CustomHangingBlock.TIP, true);

        while (context.isAir(cursor.below()) && random.nextFloat() >= 0.5F) {
            context.setBlock(cursor, body);
            cursor = cursor.below();
        }
        context.setBlock(cursor, tip);
    }
}
