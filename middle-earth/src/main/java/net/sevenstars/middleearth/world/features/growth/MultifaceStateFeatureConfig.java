package net.sevenstars.middleearth.world.features.growth;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.sevenstars.middleearth.world.features.columns.CaveColumnFeatureConfig;

import java.util.List;

public class MultifaceStateFeatureConfig implements FeatureConfiguration {
	public final MultifaceBlock block;
	public final int searchRange;
	public final boolean placeOnFloor;
	public final boolean placeOnCeiling;
	public final boolean placeOnWalls;
	public final boolean persistent;
	public final float spreadChance;
	public final HolderSet<Block> canPlaceOn;
	private final ObjectArrayList<Direction> directions;

	public static final Codec<MultifaceStateFeatureConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BuiltInRegistries.BLOCK
							.byNameCodec()
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
					RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(config -> config.canPlaceOn)
			).apply(instance, MultifaceStateFeatureConfig::new));

    public MultifaceStateFeatureConfig(Block block, int searchRange, boolean placeOnFloor, boolean placeOnCeiling,
									   boolean placeOnWalls, boolean persistent, float spreadChance, HolderSet<Block> canPlaceOn) {
        this.block = (MultifaceBlock) block;
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
			Direction.Plane.HORIZONTAL.forEach(this.directions::add);
		}
    }

    private static DataResult<MultifaceBlock> validateBlock(Block block) {
		return block instanceof MultifaceBlock multifaceGrowthBlock
			? DataResult.success(multifaceGrowthBlock)
			: DataResult.error(() -> "Growth block should be a multiface block");
	}

	public List<Direction> shuffleDirections(RandomSource random, Direction excluded) {
		return Util.toShuffledList(this.directions.stream().filter(direction -> direction != excluded), random);
	}

	public List<Direction> shuffleDirections(RandomSource random) {
		return Util.shuffledCopy(this.directions, random);
	}
}
