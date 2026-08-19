package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

public record GreatHornVariant(GreatHornAssetInfo assetInfo) {
	public static final Codec<GreatHornVariant> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							GreatHornAssetInfo.CODEC.fieldOf("assets").forGetter(GreatHornVariant::assetInfo)
					)
					.apply(instance, GreatHornVariant::new)
	);

	public static final Codec<Holder<GreatHornVariant>> ENTRY_CODEC = RegistryFixedCodec.create(DynamicRegistriesME.GREAT_HORN_VARIANTS);

	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GreatHornVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(DynamicRegistriesME.GREAT_HORN_VARIANTS);

	public static ResourceLocation texture(ResourceLocation assetId) {
		return ResourceLocation.fromNamespaceAndPath(
				assetId.getNamespace(), "textures/" + assetId.getPath() + ".png");
	}

	public record GreatHornAssetInfo(ResourceLocation id) {
		public static final Codec<GreatHornAssetInfo> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(ResourceLocation.CODEC.fieldOf("id").forGetter(GreatHornAssetInfo::id)).apply(instance, GreatHornAssetInfo::new)
		);
	}
}
