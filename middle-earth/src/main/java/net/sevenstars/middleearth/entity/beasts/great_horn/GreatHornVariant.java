package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.VariantSelectorProvider;
import net.minecraft.entity.spawn.SpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.AssetInfo;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.List;

public record GreatHornVariant(GreatHornAssetInfo assetInfo, SpawnConditionSelectors spawnConditions) implements VariantSelectorProvider<SpawnContext, SpawnCondition> {
	public static final Codec<GreatHornVariant> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							GreatHornAssetInfo.CODEC.fieldOf("assets").forGetter(GreatHornVariant::assetInfo),
							SpawnConditionSelectors.CODEC.fieldOf("spawn_conditions").forGetter(GreatHornVariant::spawnConditions)
					)
					.apply(instance, GreatHornVariant::new)
	);

	public static final Codec<RegistryEntry<GreatHornVariant>> ENTRY_CODEC = RegistryFixedCodec.of(DynamicRegistriesME.GREAT_HORN_VARIANTS);

	public static final PacketCodec<RegistryByteBuf, RegistryEntry<GreatHornVariant>> PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.GREAT_HORN_VARIANTS);

	private GreatHornVariant(GreatHornAssetInfo assetInfo) {
		this(assetInfo, SpawnConditionSelectors.EMPTY);
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> getSelectors() {
		return this.spawnConditions.selectors();
	}

	public record GreatHornAssetInfo(AssetInfo id) {
		public static final Codec<GreatHornAssetInfo> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(AssetInfo.CODEC.fieldOf("id").forGetter(GreatHornAssetInfo::id)).apply(instance, GreatHornAssetInfo::new)
		);
	}
}