package net.jukoz.me.network.packets.s2c;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.network.handlers.OnboardingScreenHandler;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class OnboardingDetailParsedPacket extends ServerToClientPacket<OnboardingDetailParsedPacket> {
    public static final CustomPayload.Id<OnboardingDetailParsedPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "onboarding_details_parsed_packet"));
    public static final PacketCodec<RegistryByteBuf, OnboardingDetailParsedPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, p -> p.havePlayerData,
            PacketCodecs.BOOL, p -> p.canChangeFaction,
            PacketCodecs.BOOL, p -> p.canReturnToOverworld,
            OnboardingDetailParsedPacket::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;

    public OnboardingDetailParsedPacket(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
    }

    @Override
    public Id<OnboardingDetailParsedPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, OnboardingDetailParsedPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ClientPacketContext context) {
        OnboardingScreenHandler.handle(context, havePlayerData);
    }
}
