package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.armor.HelmetAttachmentItem;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import java.util.Objects;

public class HoodStateTogglePacket extends ClientToServerPacket<HoodStateTogglePacket> {
    public static final Type<HoodStateTogglePacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "hood_state_toggle_packet"));
    public static final HoodStateTogglePacket INSTANCE = new HoodStateTogglePacket();
    public static final StreamCodec<RegistryFriendlyByteBuf, HoodStateTogglePacket> CODEC = StreamCodec.unit(INSTANCE);

    public HoodStateTogglePacket() {
    }

    @Override
    public Type<HoodStateTogglePacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, HoodStateTogglePacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            Objects.requireNonNull(context.player().getServer()).execute(() -> {
                Player player = context.player();

                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
                if(helmet != null){
                    if(helmet.getItem() instanceof HelmetAttachmentItem)
                        HelmetAttachmentItem.toggleHelmetAttachmentState(context.player(), helmet);
                    else if(helmet.getItem() instanceof CustomHelmetItem)
                        CustomHelmetItem.toggleHoodState(context.player(), helmet);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("HoodStatePacket error: ", e);
        }
    }
}
