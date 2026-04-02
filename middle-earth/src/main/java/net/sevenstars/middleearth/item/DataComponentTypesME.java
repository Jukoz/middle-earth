package net.sevenstars.middleearth.item;

import net.minecraft.entity.passive.HorseColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatColor;
import net.sevenstars.middleearth.item.dataComponents.*;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliases;

import java.util.function.UnaryOperator;

public class DataComponentTypesME {

    /**
     * Middle-earth mod Data Components registry
     */

    public static final ComponentType<TemperatureDataComponent> TEMPERATURE_DATA = register("temperature", (builder) -> {
        return builder.codec(TemperatureDataComponent.CODEC).packetCodec(TemperatureDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<ArmorVariantDataComponent> ARMOR_VARIANT_DATA = register("armor_variant", (builder) -> {
        return builder.codec(ArmorVariantDataComponent.CODEC).packetCodec(ArmorVariantDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<ArtisanDataComponent> ARTISAN_DATA = register("artisan", (builder) -> {
        return builder.codec(ArtisanDataComponent.CODEC).packetCodec(ArtisanDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<SeasonDataComponent> SEASON_DATA = register("season", (builder) -> {
        return builder.codec(SeasonDataComponent.CODEC).packetCodec(SeasonDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<FactionDataComponent> FACTION_DATA = register("faction", (builder) -> {
        return builder.codec(FactionDataComponent.CODEC).packetCodec(FactionDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<RaceDataComponent> RACE_DATA = register("race", (builder) -> {
        return builder.codec(RaceDataComponent.CODEC).packetCodec(RaceDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<ArmorTierDataComponent> ARMOR_TIER_DATA = register("armor_tier", (builder) -> {
        return builder.codec(ArmorTierDataComponent.CODEC).packetCodec(ArmorTierDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<WeaponTypeDataComponent> WEAPON_TYPE_DATA = register("weapon_type", (builder) -> {
        return builder.codec(WeaponTypeDataComponent.CODEC).packetCodec(WeaponTypeDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<BlockAuthorDataComponent> BLOCK_AUTHOR_DATA = register("block_author", (builder) -> {
        return builder.codec(BlockAuthorDataComponent.CODEC).packetCodec(BlockAuthorDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<BackAttachmentDataComponent> BACK_ATTACHMENT_DATA = register("back_attachment", (builder) -> {
        return builder.codec(BackAttachmentDataComponent.CODEC).packetCodec(BackAttachmentDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<HelmetAttachmentDataComponent> HELMET_ATTACHMENT_DATA = register("helmet_attachment", (builder) -> {
        return builder.codec(HelmetAttachmentDataComponent.CODEC).packetCodec(HelmetAttachmentDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<MountArmorAddonComponent> MOUNT_ARMOR_DATA = register("mount_armor_addon", (builder) -> {
        return builder.codec(MountArmorAddonComponent.CODEC).packetCodec(MountArmorAddonComponent.PACKET_CODEC);
    });

    public static final ComponentType<BroadhoofGoatColor> GOAT_VARIANT = register(
            "broadhoof_goat/variant", builder -> builder.codec(BroadhoofGoatColor.CODEC).packetCodec(BroadhoofGoatColor.PACKET_CODEC)
    );

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.DATA_COMPONENT_TYPE, id));
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
