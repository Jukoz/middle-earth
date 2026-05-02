package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class AnvilIndexPacket extends ClientToServerPacket<AnvilIndexPacket> {
    public static final Id<AnvilIndexPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "anvil_index_packet"));
    public static final PacketCodec<RegistryByteBuf, AnvilIndexPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.index,
            PacketCodecs.DOUBLE, p -> p.x,
            PacketCodecs.DOUBLE, p -> p.y,
            PacketCodecs.DOUBLE, p -> p.z,
            AnvilIndexPacket::new
    );

    public int getIndex() {
        return index;
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

    private final int index;
    private final double x;
    private final double y;
    private final double z;

    public AnvilIndexPacket(int index, double x, double y, double z) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Id<AnvilIndexPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, AnvilIndexPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                Vec3d coordinates = new Vec3d(x, y, z);
                ShapingAnvilBlockEntity.updateIndex(index, coordinates, context.player());
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketAnvilIndex error: ", e);
        }
    }
}
