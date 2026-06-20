package net.sevenstars.middleearth.world.features.platedfood;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PlatedFoodFeatureConfig implements FeatureConfig {
    public static final Codec<PlatedFoodFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockState.CODEC.fieldOf("plate").forGetter(config -> config.plate),
                    Identifier.CODEC.fieldOf("loot_table").forGetter(config -> config.lootTable)
            ).apply(instance, PlatedFoodFeatureConfig::new));

    public final BlockState plate;
    public final Identifier lootTable;

    public PlatedFoodFeatureConfig(BlockState plate, Identifier lootTable) {
        this.plate = plate;
        this.lootTable = lootTable;
    }
}

