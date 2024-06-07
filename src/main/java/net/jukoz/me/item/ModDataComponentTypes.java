package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final DataComponentType<CapeDataComponent> CAPE_DATA = register("cape", (builder) -> {
        return builder.codec(CapeDataComponent.CODEC).packetCodec(CapeDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<HoodDataComponent> HOOD_DATA = register("hood", (builder) -> {
        return builder.codec(HoodDataComponent.CODEC).packetCodec(HoodDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<CustomDyeableDataComponent> DYE_DATA = register("dye", (builder) -> {
        return builder.codec(CustomDyeableDataComponent.CODEC).packetCodec(CustomDyeableDataComponent.PACKET_CODEC);
    });

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(MiddleEarth.MOD_ID, id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
