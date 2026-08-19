package net.sevenstars.middleearth.world.features.platedfood;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PlatedFoodFeatureConfig implements FeatureConfiguration {
    public static final Codec<PlatedFoodFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockState.CODEC.fieldOf("plate").forGetter(config -> config.plate),
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(config -> config.lootTable)
            ).apply(instance, PlatedFoodFeatureConfig::new));

    public final BlockState plate;
    public final ResourceLocation lootTable;

    public PlatedFoodFeatureConfig(BlockState plate, ResourceLocation lootTable) {
        this.plate = plate;
        this.lootTable = lootTable;
    }
}

