package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.dataComponents.*;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class DataComponentTypesME {

    /**
     * Middle-earth mod Data Components registry
     */

    public static final ComponentType<TemperatureDataComponent> TEMPERATURE_DATA = register("temperature", (builder) -> {
        return builder.codec(TemperatureDataComponent.CODEC).packetCodec(TemperatureDataComponent.PACKET_CODEC);
    });
    public static final ComponentType<ArtisanDataComponent> ARTISAN_DATA = register("artisan", (builder) -> {
        return builder.codec(ArtisanDataComponent.CODEC).packetCodec(ArtisanDataComponent.PACKET_CODEC);
    });
    public static final ComponentType<FactionDataComponent> FACTION_DATA = register("faction", (builder) -> {
        return builder.codec(FactionDataComponent.CODEC).packetCodec(FactionDataComponent.PACKET_CODEC);
    });
    public static final ComponentType<ArmorTierDataComponent> ARMOR_TIER_DATA = register("armor_tier", (builder) -> {
        return builder.codec(ArmorTierDataComponent.CODEC).packetCodec(ArmorTierDataComponent.PACKET_CODEC);
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

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
