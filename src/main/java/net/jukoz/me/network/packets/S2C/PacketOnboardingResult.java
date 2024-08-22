package net.jukoz.me.network.packets.S2C;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.network.handlers.OnboardingScreenHandler;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class PacketOnboardingResult extends ServerToClientPacket<PacketOnboardingResult> {
    public static final CustomPayload.Id<PacketOnboardingResult> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_onboarding_result"));
    public static final PacketCodec<RegistryByteBuf, PacketOnboardingResult> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, p -> p.havePlayerData,
            PacketCodecs.BOOL, p -> p.canChangeFaction,
            PacketCodecs.BOOL, p -> p.canReturnToOverworld,
            PacketOnboardingResult::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
    }

    @Override
    public Id<PacketOnboardingResult> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketOnboardingResult> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ClientPacketContext context) {
        OnboardingScreenHandler.handle(context, havePlayerData);
    }
}
