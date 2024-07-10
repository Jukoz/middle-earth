package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<CapeDataComponent> CAPE_DATA = register("cape", (builder) -> {
        return builder.codec(CapeDataComponent.CODEC).packetCodec(CapeDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<HoodDataComponent> HOOD_DATA = register("hood", (builder) -> {
        return builder.codec(HoodDataComponent.CODEC).packetCodec(HoodDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<CustomDyeableDataComponent> DYE_DATA = register("dye", (builder) -> {
        return builder.codec(CustomDyeableDataComponent.CODEC).packetCodec(CustomDyeableDataComponent.PACKET_CODEC);
    });

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
