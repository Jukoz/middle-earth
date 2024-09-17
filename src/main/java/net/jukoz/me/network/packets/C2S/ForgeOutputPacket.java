package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector3f;

public class ForgeOutputPacket extends ClientToServerPacket<ForgeOutputPacket> {
    public static final CustomPayload.Id<ForgeOutputPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "forge_output_packet"));
    public static final PacketCodec<RegistryByteBuf, ForgeOutputPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.amount,
            PacketCodecs.INTEGER, p -> p.x,
            PacketCodecs.INTEGER, p -> p.y,
            PacketCodecs.INTEGER, p -> p.z,
            ForgeOutputPacket::new
    );

    public int getAmount() {
        return amount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private final int amount;
    private final int x;
    private final int y;
    private final int z;

    public ForgeOutputPacket(int amount, int x, int y, int z) {
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
                Vec3i coordinates = new Vec3i(x, y, z);
                ForgeBlockEntity.outputItemStack(amount, coordinates, context.player());
            });
        }catch (Exception e){
            LoggerUtil.logError("PacketForgeOutput error: ", e);
        }
    }
}
