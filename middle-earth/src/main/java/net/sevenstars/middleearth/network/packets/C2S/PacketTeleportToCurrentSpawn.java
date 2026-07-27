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
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.dimension.ModDimensions;


public class PacketTeleportToCurrentSpawn extends ClientToServerPacket<PacketTeleportToCurrentSpawn> {
    public static final Type<PacketTeleportToCurrentSpawn> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_teleport_current_spawn"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentSpawn> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, p -> p.welcomeNeeded,
            ByteBufCodecs.BOOL, p -> p.offHand,
            PacketTeleportToCurrentSpawn::new
    );
    private final boolean welcomeNeeded;
    private final boolean offHand;

    public PacketTeleportToCurrentSpawn(boolean welcomeNeeded){
        this(welcomeNeeded, false);
    }

    public PacketTeleportToCurrentSpawn(boolean welcomeNeeded, boolean offHand){
        this.welcomeNeeded = welcomeNeeded;
        this.offHand = offHand;
    }
    @Override
    public Type<PacketTeleportToCurrentSpawn> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentSpawn> streamCodec() {
        return CODEC;
    }



    @Override
    public void process(ServerPacketContext context) {
        try{
            OnboardingServerHandler.teleportCurrentSpawn(
                    context.player(),
                    offHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND,
                    welcomeNeeded
            );
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("TeleportToMeSpawnRequestPacket::Apply - Tried applying the teleport to me request packet",e);
        }
    }
}
