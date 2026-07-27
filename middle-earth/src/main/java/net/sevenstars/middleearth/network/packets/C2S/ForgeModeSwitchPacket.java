package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class ForgeModeSwitchPacket extends ClientToServerPacket<ForgeModeSwitchPacket> {
    public static final Type<ForgeModeSwitchPacket> ID = new Type<>(MiddleEarth.of("forge_mode_switch_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeModeSwitchPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, p -> p.x,
            ByteBufCodecs.DOUBLE, p -> p.y,
            ByteBufCodecs.DOUBLE, p -> p.z,
            ForgeModeSwitchPacket::new
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

    public ForgeModeSwitchPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Type<ForgeModeSwitchPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ForgeModeSwitchPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            ServerPlayer player = context.player();
            BlockPos pos = ServerPacketGuards.exactBlockPos(x, y, z);
            if (pos == null
                    || !(player.containerMenu instanceof ForgeAlloyingScreenHandler menu)
                    || !menu.getPos().equals(pos)
                    || !menu.stillValid(player)
                    || !ServerPacketGuards.isLoadedAndNearby(player, pos)
                    || !(player.level().getBlockEntity(pos) instanceof ForgeBlockEntity)
                    || !ServerPacketGuards.tryAcquire(player, ID.id(), 4)) {
                return;
            }
            Vec3 coordinates = new Vec3(x, y, z);
            ForgeBlockEntity.switchMode(coordinates, player);
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketForgeOutput error: ", e);
        }
    }
}
