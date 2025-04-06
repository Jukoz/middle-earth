package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.armor.HoodHelmetItem;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

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

                ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
                if(helmet != null){
                    if(helmet.getItem() instanceof HoodHelmetItem)
                        HoodHelmetItem.toggleHoodState(context.player(), helmet);
                    else if(helmet.getItem() instanceof CustomHelmetItem)
                        CustomHelmetItem.toggleHoodState(context.player(), helmet);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("HoodStatePacket error: ", e);
        }
    }
}
