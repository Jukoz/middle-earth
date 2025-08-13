package net.sevenstars.middleearth.entity.spider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.VariantSelectorProvider;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.entity.spawn.SpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.AssetInfo;

import java.util.List;

public record SpiderVariant(SpiderAssetInfo assetInfo, SpawnConditionSelectors spawnConditions) implements VariantSelectorProvider<SpawnContext, SpawnCondition> {
	public static final Codec<SpiderVariant> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							SpiderAssetInfo.CODEC.fieldOf("assets").forGetter(SpiderVariant::assetInfo),
							SpawnConditionSelectors.CODEC.fieldOf("spawn_conditions").forGetter(SpiderVariant::spawnConditions)
					)
					.apply(instance, SpiderVariant::new)
	);

	public static final Codec<RegistryEntry<SpiderVariant>> ENTRY_CODEC = RegistryFixedCodec.of(SpiderVariants.KEY);

	public static final PacketCodec<RegistryByteBuf, RegistryEntry<SpiderVariant>> PACKET_CODEC = PacketCodecs.registryEntry(SpiderVariants.KEY);

	private SpiderVariant(SpiderAssetInfo assetInfo) {
		this(assetInfo, SpawnConditionSelectors.EMPTY);
	}

	@Override
	public List<VariantSelectorProvider.Selector<SpawnContext, SpawnCondition>> getSelectors() {
		return this.spawnConditions.selectors();
	}

	public record SpiderAssetInfo(AssetInfo larva, AssetInfo scuttler) {
		public static final Codec<SpiderAssetInfo> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								AssetInfo.CODEC.fieldOf("larva").forGetter(SpiderAssetInfo::larva),
								AssetInfo.CODEC.fieldOf("scuttler").forGetter(SpiderAssetInfo::scuttler)
						)
						.apply(instance, SpiderAssetInfo::new)
		);
	}
}