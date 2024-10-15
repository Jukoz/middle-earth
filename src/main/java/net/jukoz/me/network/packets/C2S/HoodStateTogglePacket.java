package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.shapingAnvil.AbstractShapingAnvilBlockEntity;
import net.jukoz.me.item.items.CustomHelmetItem;
import net.jukoz.me.item.items.HoodHelmetItem;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.c2s.play.AcknowledgeReconfigurationC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class HoodStateTogglePacket extends ClientToServerPacket<HoodStateTogglePacket> {
    public static final Id<HoodStateTogglePacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "hood_state_toggle_packet"));
    public static final HoodStateTogglePacket INSTANCE = new HoodStateTogglePacket();
    public static final PacketCodec<RegistryByteBuf, HoodStateTogglePacket> CODEC = PacketCodec.unit(INSTANCE);

    public HoodStateTogglePacket() {
    }

    @Override
    public Id<HoodStateTogglePacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, HoodStateTogglePacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            Objects.requireNonNull(context.player().getServer()).execute(() -> {
                PlayerEntity player = context.player();

                player.getArmorItems().iterator().forEachRemaining(stack ->{
                    if (stack.getItem() instanceof HoodHelmetItem){
                        HoodHelmetItem.toggleHoodState(context.player(), stack);
                    }

                    if (stack.getItem() instanceof CustomHelmetItem){
                        CustomHelmetItem.toggleHoodState(context.player(), stack);
                    }
                });

            });
        }catch (Exception e){
            LoggerUtil.logError("HoodStatePacket error: ", e);
        }
    }
}
