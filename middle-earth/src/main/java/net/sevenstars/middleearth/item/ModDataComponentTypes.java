package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.dataComponents.*;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<TemperatureDataComponent> TEMPERATURE_DATA = register("temperature", (builder) -> {
        return builder.codec(TemperatureDataComponent.CODEC).packetCodec(TemperatureDataComponent.PACKET_CODEC);
    });
    public static final ComponentType<CapeDataComponent> CAPE_DATA = register("cape", (builder) -> {
        return builder.codec(CapeDataComponent.CODEC).packetCodec(CapeDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<HoodDataComponent> HOOD_DATA = register("hood", (builder) -> {
        return builder.codec(HoodDataComponent.CODEC).packetCodec(HoodDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<CustomDyeableDataComponent> DYE_DATA = register("dye", (builder) -> {
        return builder.codec(CustomDyeableDataComponent.CODEC).packetCodec(CustomDyeableDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<MountArmorAddonComponent> MOUNT_ARMOR_DATA = register("mount_armor_addon", (builder) -> {
        return builder.codec(MountArmorAddonComponent.CODEC).packetCodec(MountArmorAddonComponent.PACKET_CODEC);
    });

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        LoggerUtil.logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
