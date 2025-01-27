package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.item.items.StarlightPhialItem;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;


public class PacketTeleportToCurrentOverworldSpawn extends ClientToServerPacket<PacketTeleportToCurrentOverworldSpawn> {
    public static final Id<PacketTeleportToCurrentOverworldSpawn> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_to_current_overworld_spawn"));
    public static final PacketTeleportToCurrentOverworldSpawn INSTANCE = new PacketTeleportToCurrentOverworldSpawn();
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToCurrentOverworldSpawn> CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<PacketTeleportToCurrentOverworldSpawn> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketTeleportToCurrentOverworldSpawn> streamCodec() {
        return CODEC;
    }



    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                RaceUtil.reset(context.player());

                if(ModDimensions.isInMiddleEarth(context.player().getWorld())){
                    ModDimensions.teleportPlayerToOverworld(context.player());
                    RaceUtil.reset(context.player());
                    if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                        RaceUtil.initializeRace(context.player());
                    } else {
                        RaceUtil.reset(context.player());
                    }

                    if(!context.player().isCreative() && context.player().getMainHandStack().getItem() instanceof StarlightPhialItem)
                        context.player().getStackInHand(Hand.MAIN_HAND).decrement(1);
                }
            });
        } catch (Exception e){
            LoggerUtil.logError("PacketTeleportToCurrentOverworldSpawn::Apply - Tried applying the return to overworld packet",e);
        }
    }
}