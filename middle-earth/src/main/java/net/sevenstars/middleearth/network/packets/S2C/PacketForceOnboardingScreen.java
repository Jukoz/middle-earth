package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final Id<PacketForceOnboardingScreen> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_force_onboarding_screen"));
    public static final PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            PacketForceOnboardingScreen::new
    );
    private final float delayOnTeleportationConfirm;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
    }

    @Override
    public Id<PacketForceOnboardingScreen> getId() {
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

        var controller = OnboardingFactionScreenController.getInstance();
        if(controller == null)
            controller = new OnboardingFactionScreenController(context.player().getWorld(), delay);
        controller.open();
    }
}
