package net.sevenstars.middleearth.network.connections;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sevenstars.middleearth.network.packets.C2S.PacketCompleteOnboarding;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

@OnlyIn(Dist.CLIENT)
public class ConnectionToServer implements IConnectionToServer{
    @Override
    public boolean isOnServer() {
        var listener = Minecraft.getInstance().getConnection();
        return listener != null && listener.hasChannel(PacketCompleteOnboarding.ID);
    }

    @Override
    public <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet) {
        if (isOnServer()) {
            PacketDistributor.sendToServer(packet);
        }
    }
}
