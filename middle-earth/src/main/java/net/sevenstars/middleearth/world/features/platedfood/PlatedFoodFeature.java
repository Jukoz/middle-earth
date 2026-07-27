package net.sevenstars.middleearth.world.features.platedfood;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.plate.PlateBlockEntity;
import net.sevenstars.middleearth.world.features.chain.ChainFeatureConfig;

public class PlatedFoodFeature extends Feature<PlatedFoodFeatureConfig> {

    public PlatedFoodFeature(Codec<PlatedFoodFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PlatedFoodFeatureConfig> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        RandomSource random = context.random();
        PlatedFoodFeatureConfig config = context.config();
        ResourceLocation lootTableIdentifier = config.lootTable;

        this.setBlock(structureWorldAccess, blockPos, config.plate);

        BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos);
        if(blockEntity != null) {
            PlateBlockEntity plateBlockEntity = (PlateBlockEntity) blockEntity;
            plateBlockEntity.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableIdentifier), random.nextLong());
            plateBlockEntity.setBlockPlaced();
            plateBlockEntity.generateItem((ServerLevel) structureWorldAccess);
        }
        return true;
    }
}
