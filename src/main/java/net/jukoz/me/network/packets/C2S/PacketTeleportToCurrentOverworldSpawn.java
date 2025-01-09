package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.item.items.StarlightPhialItem;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;


public class PacketTeleportToCurrentOverworldSpawn extends ClientToServerPacket<PacketTeleportToCurrentOverworldSpawn> {
    public static final CustomPayload.Id<PacketTeleportToCurrentOverworldSpawn> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_to_current_overworld_spawn"));
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
                    RaceUtil.reset(context.player())
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