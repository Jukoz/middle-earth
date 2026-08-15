package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.handlers.OnboardingReturnResult;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public final class PacketReturnToOverworldResult extends ServerToClientPacket<PacketReturnToOverworldResult> {
    public static final Type<PacketReturnToOverworldResult> ID = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_return_to_overworld_result")
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketReturnToOverworldResult> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            PacketReturnToOverworldResult::statusCode,
            ByteBufCodecs.VAR_INT,
            PacketReturnToOverworldResult::retryAfterMillis,
            PacketReturnToOverworldResult::new
    );

    private final int statusCode;
    private final int retryAfterMillis;

    public PacketReturnToOverworldResult(int statusCode, int retryAfterMillis) {
        this.statusCode = statusCode;
        this.retryAfterMillis = Math.max(0, retryAfterMillis);
    }

    public PacketReturnToOverworldResult(OnboardingReturnResult result) {
        this(result.status().code(), result.retryAfterMillis());
    }

    @Override
    public Type<PacketReturnToOverworldResult> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketReturnToOverworldResult> streamCodec() {
        return CODEC;
    }

    public int statusCode() {
        return statusCode;
    }

    public OnboardingReturnResult.Status status() {
        return OnboardingReturnResult.Status.fromCode(statusCode);
    }

    public int retryAfterMillis() {
        return retryAfterMillis;
    }
}
