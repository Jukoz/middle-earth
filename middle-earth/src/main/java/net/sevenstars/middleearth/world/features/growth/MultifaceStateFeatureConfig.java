package net.sevenstars.middleearth.world.features.growth;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.sevenstars.middleearth.world.features.columns.CaveColumnFeatureConfig;

import java.util.List;

public class MultifaceStateFeatureConfig implements FeatureConfig {
	public final MultifaceGrowthBlock block;
	public final int searchRange;
	public final boolean placeOnFloor;
	public final boolean placeOnCeiling;
	public final boolean placeOnWalls;
	public final boolean persistent;
	public final float spreadChance;
	public final RegistryEntryList<Block> canPlaceOn;
	private final ObjectArrayList<Direction> directions;

	public static final Codec<MultifaceStateFeatureConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Registries.BLOCK
							.getCodec()
							.fieldOf("block")
							.<Block>flatXmap(MultifaceStateFeatureConfig::validateBlock, DataResult::success)
							.orElse(Blocks.GLOW_LICHEN)
							.forGetter(config -> config.block),
					Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(config -> config.searchRange),
					Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(config -> config.placeOnFloor),
					Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(config -> config.placeOnCeiling),
					Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(config -> config.placeOnWalls),
					Codec.BOOL.fieldOf("persistent").orElse(false).forGetter(config -> config.persistent),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").orElse(0.5F).forGetter(config -> config.spreadChance),
					RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("can_be_placed_on").forGetter(config -> config.canPlaceOn)
			).apply(instance, MultifaceStateFeatureConfig::new));

    public MultifaceStateFeatureConfig(Block block, int searchRange, boolean placeOnFloor, boolean placeOnCeiling,
									   boolean placeOnWalls, boolean persistent, float spreadChance, RegistryEntryList<Block> canPlaceOn) {
        this.block = (MultifaceGrowthBlock) block;
        this.searchRange = searchRange;
        this.placeOnFloor = placeOnFloor;
		this.placeOnCeiling = placeOnCeiling;
		this.placeOnWalls = placeOnWalls;
		this.persistent = persistent;
		this.spreadChance = spreadChance;
		this.canPlaceOn = canPlaceOn;
		this.directions = new ObjectArrayList<>(6);
		if (placeOnCeiling) {
			this.directions.add(Direction.UP);
		}

		if (placeOnFloor) {
			this.directions.add(Direction.DOWN);
		}

		if (placeOnWalls) {
			Direction.Type.HORIZONTAL.forEach(this.directions::add);
		}
    }

    private static DataResult<MultifaceGrowthBlock> validateBlock(Block block) {
		return block instanceof MultifaceGrowthBlock multifaceGrowthBlock
			? DataResult.success(multifaceGrowthBlock)
			: DataResult.error(() -> "Growth block should be a multiface spreadeable block");
	}

	public List<Direction> shuffleDirections(Random random, Direction excluded) {
		return Util.copyShuffled(this.directions.stream().filter(direction -> direction != excluded), random);
	}

	public List<Direction> shuffleDirections(Random random) {
		return Util.copyShuffled(this.directions, random);
	}
}
