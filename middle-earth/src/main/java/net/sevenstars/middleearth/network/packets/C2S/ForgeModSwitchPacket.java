package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class ForgeModSwitchPacket extends ClientToServerPacket<ForgeModSwitchPacket> {
    public static final Id<ForgeModSwitchPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "forge_mode_switch_packet"));
    public static final PacketCodec<RegistryByteBuf, ForgeModSwitchPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.DOUBLE, p -> p.x,
            PacketCodecs.DOUBLE, p -> p.y,
            PacketCodecs.DOUBLE, p -> p.z,
            ForgeModSwitchPacket::new
    );

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private final double x;
    private final double y;
    private final double z;

    public ForgeModSwitchPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Id<ForgeModSwitchPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ForgeModSwitchPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                Vec3d coordinates = new Vec3d(x, y, z);
                ForgeBlockEntity.switchMode(coordinates, context.player());
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketForgeOutput error: ", e);
        }
    }
}
