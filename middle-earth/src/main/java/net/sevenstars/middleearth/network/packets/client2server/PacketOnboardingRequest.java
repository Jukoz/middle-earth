package net.sevenstars.middleearth.network.packets.client2server;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.server2client.PacketOnboardingResult;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

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

                /*
                PacketOnboardingResult newPacket = new PacketOnboardingResult(
                        PlayerDataService.playerPassedOnboarding(context.player()),
                        ServerConfigME.ENABLE_FACTION_RESET,
                        ServerConfigME.ENABLE_RETURN_TO_OVERWORLD,
                        ServerConfigME.DELAY_ON_TELEPORT_CONFIRMATION,
                        AttributePoolElement.createAttributeNbtListFromPlayer(player)
                );
                ServerPlayNetworking.send(player, newPacket);
                 */
            });
        } catch(Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailFetchingPacket::Apply - Tried sending packet with data", e);
        }
    }
}
