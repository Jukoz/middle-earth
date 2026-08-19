package net.sevenstars.middleearth.entity.spider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

public record SpiderVariant(SpiderAssetInfo assetInfo) {
	public static final Codec<SpiderVariant> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							SpiderAssetInfo.CODEC.fieldOf("assets").forGetter(SpiderVariant::assetInfo)
					)
					.apply(instance, SpiderVariant::new)
	);

	public static final Codec<Holder<SpiderVariant>> ENTRY_CODEC = RegistryFixedCodec.create(DynamicRegistriesME.SPIDER_VARIANTS);

	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SpiderVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(DynamicRegistriesME.SPIDER_VARIANTS);

	public static ResourceLocation texture(ResourceLocation assetId) {
		return ResourceLocation.fromNamespaceAndPath(
				assetId.getNamespace(), "textures/" + assetId.getPath() + ".png");
	}

	public record SpiderAssetInfo(ResourceLocation larva, ResourceLocation scuttler, ResourceLocation spawnOfShelob) {
		public static final Codec<SpiderAssetInfo> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ResourceLocation.CODEC.fieldOf("larva").forGetter(SpiderAssetInfo::larva),
								ResourceLocation.CODEC.fieldOf("scuttler").forGetter(SpiderAssetInfo::scuttler),
								ResourceLocation.CODEC.fieldOf("spawn").forGetter(SpiderAssetInfo::spawnOfShelob)
						)
						.apply(instance, SpiderAssetInfo::new)
		);
	}
}
