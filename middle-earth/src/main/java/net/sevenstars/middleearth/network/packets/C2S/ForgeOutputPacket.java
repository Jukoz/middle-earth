package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ForgeOutputPacket extends ClientToServerPacket<ForgeOutputPacket> {
    public static final Id<ForgeOutputPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "forge_output_packet"));
    public static final PacketCodec<RegistryByteBuf, ForgeOutputPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.amount,
            PacketCodecs.DOUBLE, p -> p.x,
            PacketCodecs.DOUBLE, p -> p.y,
            PacketCodecs.DOUBLE, p -> p.z,
            ForgeOutputPacket::new
    );

    public int getAmount() {
        return amount;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private final int amount;
    private final double x;
    private final double y;
    private final double z;

    public ForgeOutputPacket(int amount, double x, double y, double z) {
        this.amount = amount;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Id<ForgeOutputPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ForgeOutputPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                Vec3d coordinates = new Vec3d(x, y, z);
                ForgeBlockEntity.outputItemStack(amount, coordinates, context.player());
            });
        }catch (Exception e){
            LoggerUtil.logError("PacketForgeOutput error: ", e);
        }
    }
}
