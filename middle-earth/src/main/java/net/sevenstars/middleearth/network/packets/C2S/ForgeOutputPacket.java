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

public class ForgeOutputPacket extends ClientToServerPacket<ForgeOutputPacket> {
    public static final Type<ForgeOutputPacket> ID = new Type<>(MiddleEarth.of("forge_output_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeOutputPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.amount,
            ByteBufCodecs.DOUBLE, p -> p.x,
            ByteBufCodecs.DOUBLE, p -> p.y,
            ByteBufCodecs.DOUBLE, p -> p.z,
            ByteBufCodecs.INT, p -> p.mode,
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

    public double getMode() {
        return mode;
    }

    private final int amount;
    private final double x;
    private final double y;
    private final double z;
    private final int mode;

    public ForgeOutputPacket(int amount, double x, double y, double z, int mode) {
        this.amount = amount;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mode = mode;
    }

    @Override
    public Type<ForgeOutputPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ForgeOutputPacket> streamCodec() {
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
                    || !isValidOutputSelection()
                    || !ServerPacketGuards.tryAcquire(player, ID.id(), 2)) {
                return;
            }
            Vec3 coordinates = new Vec3(x, y, z);
            ForgeBlockEntity.outputItemStack(amount, coordinates, player, mode);
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketForgeOutput error: ", e);
        }
    }

    private boolean isValidOutputSelection() {
        return switch (mode) {
            case 1 -> amount == 16;
            case 2 -> amount == 144;
            case 3, 4 -> amount == 288;
            case 5 -> amount == 432;
            default -> false;
        };
    }
}
