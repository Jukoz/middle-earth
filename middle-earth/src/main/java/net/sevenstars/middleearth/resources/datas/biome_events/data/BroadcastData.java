package net.sevenstars.middleearth.resources.datas.biome_events.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BroadcastData {
    public static class Fields {
        public static final String DISTANCE = "distance";
        public static final String LANG_KEY = "lang_key";
    }

    public static final Codec<BroadcastData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf(BroadcastData.Fields.DISTANCE).forGetter(BroadcastData::getBroadcastMessageDistance),
            Identifier.CODEC.optionalFieldOf(BroadcastData.Fields.LANG_KEY).forGetter(BroadcastData::getBroadcastMessageLangKey)
    ).apply(instance, BroadcastData::new));

    private Integer broadcastDistance = null;
    private Identifier broadcastLangKey = null;

    private BroadcastData(
            Optional<Integer> broadcastDistance,
            Optional<Identifier> broadcastLangKey) {
        this.broadcastDistance = broadcastDistance.orElse(null);
        this.broadcastLangKey = broadcastLangKey.orElse(null);
    }

    public BroadcastData(Integer broadcastDistance, Identifier broadcastLangKey) {
        this.broadcastDistance = broadcastDistance;
        this.broadcastLangKey = broadcastLangKey;
    }

    private Optional<Integer> getBroadcastMessageDistance() {
        return Optional.ofNullable(broadcastDistance);
    }

    public BroadcastData withBroadcastMessageDistance(int distance) {
        this.broadcastDistance = distance;
        return this;
    }


    private Optional<Identifier> getBroadcastMessageLangKey() {
        return Optional.ofNullable(broadcastLangKey);
    }

    public BroadcastData withBroadcastMessageLangKey(Identifier langKey) {
        this.broadcastLangKey = langKey;
        return this;
    }

    public void broadcastMessage(ServerWorld world, BlockPos pos){
        if(broadcastLangKey == null)
            return;
        int distanceToBroadcast =  getBroadcastMessageDistance().orElse(200);
        List<ServerPlayerEntity> playerNearby = new ArrayList<>();
        for(ServerPlayerEntity player : world.getPlayers()) {
            if(pos.isWithinDistance(player.getPos(), distanceToBroadcast))
                playerNearby.add(player);
        }
        for(ServerPlayerEntity player : playerNearby){
            player.sendMessage(Text.translatable(broadcastLangKey.toTranslationKey("biome_event")) ,true);
        }
    }
}
