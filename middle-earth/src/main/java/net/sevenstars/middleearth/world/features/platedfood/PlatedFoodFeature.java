package net.sevenstars.middleearth.world.features.platedfood;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.plate.PlateBlockEntity;
import net.sevenstars.middleearth.world.features.chain.ChainFeatureConfig;

public class PlatedFoodFeature extends Feature<PlatedFoodFeatureConfig> {

    public PlatedFoodFeature(Codec<PlatedFoodFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<PlatedFoodFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        PlatedFoodFeatureConfig config = context.getConfig();
        Identifier lootTableIdentifier = config.lootTable;

        this.setBlockState(structureWorldAccess, blockPos, config.plate);

        BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos);
        if(blockEntity != null) {
            PlateBlockEntity plateBlockEntity = (PlateBlockEntity) blockEntity;
            plateBlockEntity.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTableIdentifier), random.nextLong());
            plateBlockEntity.setBlockPlaced();
            plateBlockEntity.generateItem((ServerWorld) structureWorldAccess);
        }
        return true;
    }
}
