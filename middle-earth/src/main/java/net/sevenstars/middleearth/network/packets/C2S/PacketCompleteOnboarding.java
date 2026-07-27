package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingServerHandler;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public final class PacketCompleteOnboarding extends ClientToServerPacket<PacketCompleteOnboarding> {
    public static final Type<PacketCompleteOnboarding> ID = new Type<>(MiddleEarth.of("packet_complete_onboarding"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketCompleteOnboarding> CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, PacketCompleteOnboarding::factionId,
            ResourceLocation.STREAM_CODEC, PacketCompleteOnboarding::raceId,
            ResourceLocation.STREAM_CODEC, PacketCompleteOnboarding::spawnId,
            BlockPos.STREAM_CODEC, PacketCompleteOnboarding::origin,
            ByteBufCodecs.BOOL, PacketCompleteOnboarding::offHand,
            PacketCompleteOnboarding::new
    );

    private final ResourceLocation factionId;
    private final ResourceLocation raceId;
    private final ResourceLocation spawnId;
    private final BlockPos origin;
    private final boolean offHand;

    public PacketCompleteOnboarding(
            ResourceLocation factionId,
            ResourceLocation raceId,
            ResourceLocation spawnId,
            BlockPos origin,
            boolean offHand
    ) {
        this.factionId = factionId;
        this.raceId = raceId;
        this.spawnId = spawnId;
        this.origin = origin;
        this.offHand = offHand;
    }

    @Override
    public Type<PacketCompleteOnboarding> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketCompleteOnboarding> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        OnboardingServerHandler.completeSelection(
                context.player(),
                factionId,
                raceId,
                spawnId,
                origin,
                offHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND
        );
    }

    public ResourceLocation factionId() {
        return factionId;
    }

    public ResourceLocation raceId() {
        return raceId;
    }

    public ResourceLocation spawnId() {
        return spawnId;
    }

    public BlockPos origin() {
        return origin;
    }

    public boolean offHand() {
        return offHand;
    }
}
