package net.sevenstars.middleearth.features.generation.worldfeatures.underground;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

// keeps the fire features out of the deeps, and far enough above them that a blob cannot spill in
// a column with the usual floor has no deeps, so nothing changes there
public final class NotInDeepsPlacementModifier extends AbstractConditionalPlacementModifier {
    public static final NotInDeepsPlacementModifier INSTANCE = new NotInDeepsPlacementModifier();
    public static final MapCodec<NotInDeepsPlacementModifier> CODEC = MapCodec.unit(INSTANCE);
    public static final PlacementModifierType<NotInDeepsPlacementModifier> TYPE = () -> CODEC;

    private static final int MARGIN = 6; // blocks, up and sideways
    private static final int[][] AROUND = {{0, 0}, {MARGIN, 0}, {-MARGIN, 0}, {0, MARGIN}, {0, -MARGIN}};

    private NotInDeepsPlacementModifier() {
    }

    @Override
    protected boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        long seed = context.getWorld().getSeed();
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        for (int[] offset : AROUND) {
            int x = pos.getX() + offset[0];
            int z = pos.getZ() + offset[1];
            if (DepthBands.deepFloor(map, x, z, seed) >= DepthBands.SHALLOW_FLOOR) continue;
            if (pos.getY() < DepthBands.MITHRIL_BOTTOM + (int) MiddleEarthChunkGenerator.caveBlendNoise(x, z, seed) + MARGIN) return false;
        }
        return true;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return TYPE;
    }
}
