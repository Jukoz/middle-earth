package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingServerHandler;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.PacketOnboardingResult;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class PacketOnboardingRequest extends ClientToServerPacket<PacketOnboardingRequest>
{
    public static final Type<PacketOnboardingRequest> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_onboarding_request"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingRequest> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PacketOnboardingRequest::offHand,
            PacketOnboardingRequest::new
    );
    private final boolean offHand;

    public PacketOnboardingRequest() {
        this(false);
    }

    public PacketOnboardingRequest(boolean offHand) {
        this.offHand = offHand;
    }

    @Override
    public Type<PacketOnboardingRequest> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            ServerPlayer player = context.player();
            InteractionHand hand = offHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            if (!OnboardingServerHandler.begin(player, hand)) {
                return;
            }
            PacketOnboardingResult newPacket = new PacketOnboardingResult(
                    PlayerDataService.playerPassedOnboarding(context.player()),
                    ModServerConfigs.ENABLE_FACTION_RESET,
                    ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD,
                    ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION,
                    AttributePoolElement.createAttributeNbtListFromPlayer(player),
                    offHand
            );
            context.connection().sendPacketToClient(newPacket, player);
        } catch(Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailFetchingPacket::Apply - Tried sending packet with data", e);
        }
    }

    public boolean offHand() {
        return offHand;
    }
}
