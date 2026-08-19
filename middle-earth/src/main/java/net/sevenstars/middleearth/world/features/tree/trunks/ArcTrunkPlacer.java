package net.sevenstars.middleearth.world.features.tree.trunks;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.world.gen.ModTreeGeneration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ArcTrunkPlacer extends TrunkPlacer {

    protected final int baseHeight;
    protected final int randomHeight;
    protected final float minAcceleration;
    protected final float maxAcceleration;
    protected final float velocity;

    public static final MapCodec<ArcTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                Codec.intRange(0,90).fieldOf("base_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseHeight;
                }), Codec.intRange(0,16).fieldOf("random_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.randomHeight;
                }), Codec.floatRange(0.0f,0.2f).fieldOf("min_acceleration").forGetter((trunkPlacer) -> {
                    return trunkPlacer.minAcceleration;
                }), Codec.floatRange(0.0f,0.2f).fieldOf("max_acceleration").forGetter((trunkPlacer) -> {
                    return trunkPlacer.maxAcceleration;
                }), Codec.floatRange(0.0f,0.2f).fieldOf("velocity").forGetter((trunkPlacer) -> {
                    return trunkPlacer.velocity;
                })).apply(instance, ArcTrunkPlacer::new);
    });

    public ArcTrunkPlacer(int baseHeight, int randomHeight, float minAcceleration, float maxAcceleration, float velocity) {
        super(baseHeight, randomHeight, 0);
        this.baseHeight = baseHeight;
        this.randomHeight = randomHeight;
        this.minAcceleration = minAcceleration;
        this.maxAcceleration = maxAcceleration;
        this.velocity = velocity;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTreeGeneration.ARC_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random,
                                                 int height, BlockPos startPos, TreeConfiguration config) {
        BlockPos blockPos = startPos.below();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        double angle = random.nextDouble() * 180;
        float acceleration = (float) ((random.nextDouble() * (maxAcceleration - minAcceleration)) + minAcceleration);
        List<FoliagePlacer.FoliageAttachment> treeNodes = createArcBranch(world, replacer, random, mutable, config, startPos, getTreeHeight(random), angle, acceleration, this.velocity);

        return ImmutableList.copyOf(treeNodes);
    }

    protected List<FoliagePlacer.FoliageAttachment> createArcBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable,
                                          TreeConfiguration config, BlockPos startPos, int height, double angle, float acceleration, float velocity) {
        if(height < 0) {
            height *= -1;
        }
        Vec3 dir = new Vec3(Math.cos(angle), 1, Math.sin(angle)).normalize();
        Vec3 currentPos = new Vec3(startPos.getX(), startPos.getY() - 1, startPos.getZ());
        float currentVel = velocity;

        int topHeight = (int) (currentPos.y + height);
        while (currentPos.y() < topHeight) {
            currentPos = currentPos.add(new Vec3(dir.x * currentVel, dir.y, dir.z * currentVel));
            this.setLog(world, replacer, random, mutable, config, new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z),
                    0, 0, 0);
            currentVel += acceleration;
        }
        List<FoliagePlacer.FoliageAttachment> treeNodes = new ArrayList<>();
        treeNodes.add(new FoliagePlacer.FoliageAttachment(new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z), 0, false));
        return treeNodes;
    }

    protected void setLog(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos tmpPos,
                          TreeConfiguration config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.setWithOffset(startPos, dx, dy, dz);
        this.placeLogIfFree(world, replacer, random, tmpPos, config);
    }
}
