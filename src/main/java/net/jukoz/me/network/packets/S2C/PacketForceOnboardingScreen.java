package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.faction_selection.FactionSelectionScreen;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final CustomPayload.Id<PacketForceOnboardingScreen> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_force_onboarding_screen"));
    public static final PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            PacketForceOnboardingScreen::new
    );
    private final float delayOnTeleportationConfirm;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
    }

    @Override
    public CustomPayload.Id<PacketForceOnboardingScreen> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isInCreativeMode())
            delay = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(new FactionSelectionScreen(delay));
    }
}
