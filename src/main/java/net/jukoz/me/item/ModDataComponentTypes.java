package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final DataComponentType<TemperatureDataComponent> TEMPERATURE_DATA = register("temprature", (builder) -> {
        return builder.codec(TemperatureDataComponent.CODEC).packetCodec(TemperatureDataComponent.PACKET_CODEC);
    });

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
