package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class PacketLivingEntityData extends ServerToClientPacket<PacketLivingEntityData> {
    public static final Id<PacketLivingEntityData> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_living_entity_data"));
    public static final PacketCodec<RegistryByteBuf, PacketLivingEntityData> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.entityId,
            StatusEffectInstance.PACKET_CODEC, p -> p.statusEffectInstance,
            PacketLivingEntityData::new
    );

    private final int entityId;
    private final StatusEffectInstance statusEffectInstance;

    public PacketLivingEntityData(int entityId, StatusEffectInstance statusEffectInstance) {
        this.entityId = entityId;
        this.statusEffectInstance = statusEffectInstance;
    }

    @Override
    public Id<PacketLivingEntityData> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketLivingEntityData> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ClientPacketContext context) {
        Entity entity = context.player().getWorld().getEntityById(entityId);
        if(entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(statusEffectInstance);
        }
    }
}
