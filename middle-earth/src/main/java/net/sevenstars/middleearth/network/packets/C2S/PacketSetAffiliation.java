package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.items.StarlightPhialItem;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PacketSetAffiliation extends ClientToServerPacket<PacketSetAffiliation>
{
    public static final Id<PacketSetAffiliation> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_set_affiliation"));

    public static final PacketCodec<RegistryByteBuf, PacketSetAffiliation> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, p -> p.dispositionName,
            PacketCodecs.STRING, p -> p.factionName,
            PacketCodecs.STRING, p -> p.spawnName,
            PacketSetAffiliation::new
    );

    private final String dispositionName;
    private final String factionName;
    private final String spawnName;


    public PacketSetAffiliation(String dispositionName, String factionName, String spawnName){
        this.dispositionName = dispositionName;
        this.factionName = factionName;
        this.spawnName = spawnName;
    }

    @Override
    public Id<PacketSetAffiliation> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketSetAffiliation> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        MinecraftServer server = context.player().getServer();
        server.execute(() -> {
            try{
                Identifier factionId = Identifier.of(factionName);
                Faction faction = FactionLookup.getFactionById(context.player().getWorld(), factionId);
                Identifier spawnId = Identifier.of(spawnName);
                FactionUtil.updateFaction(context.player(), faction, spawnId);
                if(!context.player().isCreative() && context.player().getMainHandStack().getItem() instanceof StarlightPhialItem)
                    context.player().getStackInHand(Hand.MAIN_HAND).decrement(1);
            } catch (Exception e){
                LoggerUtil.logError("AffiliationPacket::Tried getting affiliation packet and couldn't fetch any.", e);
            }
        });
    }
}