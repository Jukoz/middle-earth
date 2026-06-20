package net.sevenstars.middleearth.world.features.tree.trunks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.sevenstars.middleearth.world.gen.ModTreeGeneration;

import java.util.List;
import java.util.function.BiConsumer;

public class CanopyTrunkStructurePlacer extends CanopyTrunkPlacer {
    protected final float structureStart;

    public static final MapCodec<CanopyTrunkStructurePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                Codec.intRange(0,90).fieldOf("base_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseHeight;
                }), Codec.intRange(0,16).fieldOf("random_height").forGetter((trunkPlacer) -> {
                    return trunkPlacer.randomHeight;
                }), Codec.floatRange(0,16).fieldOf("base_radius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.baseRadius;
                }), Codec.floatRange(0,16).fieldOf("tip_radius").forGetter((trunkPlacer) -> {
                    return trunkPlacer.tipRadius;
                }), Codec.floatRange(0.0f, 16.0f).fieldOf("velocity").forGetter((trunkPlacer) -> {
                    return trunkPlacer.velocity;
                }), Codec.intRange(1,8).fieldOf("iteration").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iterations;
                }), Codec.floatRange(0.0f, 1.0f).fieldOf("iteration_percentage").forGetter((trunkPlacer) -> {
                    return trunkPlacer.iteration_percentage;
                }), Codec.floatRange(-1.0f, 1.0f).fieldOf("trunk_noise").forGetter((trunkPlacer) -> {
                    return trunkPlacer.trunk_noise;
                }), Codec.intRange(-8, 8).fieldOf("roots_offset").forGetter((trunkPlacer) -> {
                    return trunkPlacer.roots_offset;
                }), Codec.intRange(0, 1).fieldOf("straight_trunk").forGetter((trunkPlacer) -> {
                    return trunkPlacer.straight_trunk;
                }), Codec.floatRange(0,1).fieldOf("structure_start").forGetter((trunkPlacer) -> {
                    return trunkPlacer.structureStart;
                })).apply(instance, CanopyTrunkStructurePlacer::new);
    });

    public CanopyTrunkStructurePlacer(int baseHeight, int randomHeight, float baseRadius, float tipRadius, float velocity,
                                      int iterations, float iteration_percentage, float trunk_noise, int roots_offset, int straight_trunk,
                                      float structureStart) {
        super(baseHeight, randomHeight, baseRadius, tipRadius, velocity, iterations, iteration_percentage, trunk_noise, roots_offset, straight_trunk);
        this.structureStart = structureStart;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTreeGeneration.CANOPY_TRUNK_STRUCTURE_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        // Deprecated, although it can be useful later on, so keeping it there
        /*if(world instanceof ServerWorld serverWorld) {
            StructureTemplate template = getStructureTemplate(serverWorld);
            generateStructurePool(serverWorld, startPos, height);
        } else if(world instanceof ChunkRegion chunkRegion) {
            StructureTemplate template = getStructureTemplate(chunkRegion);
            generateStructurePool(chunkRegion.toServerWorld(), startPos, height, template);
        }*/
        return super.generate(world, replacer, random, height, startPos, config);
    }

    // NOTE keep it for later maybe
    /*private void generateStructurePool(ServerWorld world, BlockPos startPos, int height) {
        RegistryKey<StructurePool> pool = ModStructureKeys.LOTHLORIEN_TALAN_KEY;
        Registry<StructurePool> registry = world.getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL);
        RegistryEntry<StructurePool> registryEntry = registry.getOrThrow(pool);
        BlockPos blockPos = startPos.mutableCopy().add(new Vec3i(0, (int)(this.structureStart * height), 0));
        StructurePoolBasedGenerator.generate(world, registryEntry, ModStructureKeys.LOTHLORIEN_TALAN_KEY.getValue(), 8, blockPos, false);
    }

    @Nullable
    private StructureTemplate getStructureTemplate(ServerWorld world) {
        return structureName == null ? null : world.getStructureTemplateManager().getTemplate(this.structureName).orElse(null);
    }

    @Nullable
    private StructureTemplate getStructureTemplate(ChunkRegion world) {
        return structureName == null ? null : world.toServerWorld().getStructureTemplateManager().getTemplate(this.structureName).orElse(null);
    }*/
}
