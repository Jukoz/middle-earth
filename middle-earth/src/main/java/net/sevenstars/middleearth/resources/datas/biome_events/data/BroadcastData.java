package net.sevenstars.middleearth.resources.datas.biome_events.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class BroadcastData {
    public static final class Fields {
        public static final String DISTANCE = "distance";
        public static final String LANG_KEY = "lang_key";

        private Fields() {
        }
    }

    public static final Codec<BroadcastData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf(Fields.DISTANCE).forGetter(BroadcastData::getBroadcastMessageDistance),
            ResourceLocation.CODEC.optionalFieldOf(Fields.LANG_KEY).forGetter(BroadcastData::getBroadcastMessageLangKey)
    ).apply(instance, BroadcastData::new));

    private Integer broadcastDistance;
    private ResourceLocation broadcastLangKey;

    private BroadcastData(Optional<Integer> broadcastDistance, Optional<ResourceLocation> broadcastLangKey) {
        this.broadcastDistance = broadcastDistance.orElse(null);
        this.broadcastLangKey = broadcastLangKey.orElse(null);
    }

    public BroadcastData(Integer broadcastDistance, ResourceLocation broadcastLangKey) {
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

    private Optional<ResourceLocation> getBroadcastMessageLangKey() {
        return Optional.ofNullable(broadcastLangKey);
    }

    public BroadcastData withBroadcastMessageLangKey(ResourceLocation langKey) {
        this.broadcastLangKey = langKey;
        return this;
    }

    public void broadcastMessage(ServerLevel world, BlockPos pos) {
        if (broadcastLangKey == null) {
            return;
        }

        int distance = Math.max(0, getBroadcastMessageDistance().orElse(200));
        double distanceSquared = (double) distance * distance;
        Vec3 center = Vec3.atCenterOf(pos);
        Component message = Component.translatable(broadcastLangKey.toLanguageKey("biome_event"));
        for (ServerPlayer player : world.players()) {
            if (player.position().distanceToSqr(center) <= distanceSquared) {
                player.displayClientMessage(message, true);
            }
        }
    }
}
