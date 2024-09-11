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
import org.joml.Vector3f;

public class ForgeOutputPacket extends ClientToServerPacket<ForgeOutputPacket> {
    public static final CustomPayload.Id<ForgeOutputPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "forge_output_packet"));
    public static final PacketCodec<RegistryByteBuf, ForgeOutputPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.amount,
            PacketCodecs.VECTOR3F, p -> p.pos,
            ForgeOutputPacket::new
    );

    public int getAmount() {
        return amount;
    }

    public Vector3f getPos() {
        return pos;
    }

    private final int amount;
    private final Vector3f pos;

    public ForgeOutputPacket(int amount, Vector3f pos) {
        this.amount = amount;
        this.pos = pos;
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
                ForgeBlockEntity.outputItemStack(this, context.player());
            });
        }catch (Exception e){
            LoggerUtil.logError("PacketForgeOutput error: ", e);
        }
    }
}
