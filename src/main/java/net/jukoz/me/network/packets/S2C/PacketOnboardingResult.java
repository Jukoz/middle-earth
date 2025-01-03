package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.ReturnConfirmationScreen;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.network.handlers.OnboardingScreenHandler;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
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
            PacketCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            PacketOnboardingResult::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;
    private final float delayOnTeleportationConfirm;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
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
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        if(!canReturnToOverworld){
            return;
        }
        float delay = delayOnTeleportationConfirm;
        if(context.player().isInCreativeMode())
            delay = 0;
        if(ModDimensions.isInMiddleEarth(context.player().getWorld())){
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new ReturnConfirmationScreen(delay));
        } else if(ModDimensions.isInOverworld(context.player().getWorld())){
            OnboardingScreenHandler.handle(context, havePlayerData, delay);
        }
    }
}
