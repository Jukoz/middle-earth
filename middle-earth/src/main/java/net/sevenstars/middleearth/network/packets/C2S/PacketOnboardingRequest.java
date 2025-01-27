package net.sevenstars.middleearth.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.PacketOnboardingResult;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PacketOnboardingRequest extends ClientToServerPacket<PacketOnboardingRequest>
{
    public static final Id<PacketOnboardingRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_onboarding_request"));
    public static final PacketOnboardingRequest INSTANCE = new PacketOnboardingRequest();
    public static final PacketCodec<RegistryByteBuf, PacketOnboardingRequest> CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<PacketOnboardingRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketOnboardingRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayerEntity player = context.player();

                PacketOnboardingResult newPacket = new PacketOnboardingResult(
                        StateSaverAndLoader.getPlayerState(context.player()).hasAffilition(),
                        ModServerConfigs.ENABLE_FACTION_RESET,
                        ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD,
                        ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION
                );
                ServerPlayNetworking.send(player, newPacket);
            });
        } catch(Exception e){
            LoggerUtil.logError("OnboardingDetailFetchingPacket::Apply - Tried sending packet with data", e);
        }
    }
}
