package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.authlib.GameProfile;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public record ArtisanDataComponent(UUID uuid) implements TooltipProvider {
    private static final Cache<UUID, CompletableFuture<Optional<GameProfile>>> PROFILE_CACHE =
            CacheBuilder.newBuilder()
                    .maximumSize(256)
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build();

    private static final Codec<ArtisanDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(UUIDUtil.AUTHLIB_CODEC.fieldOf("uuid").forGetter(ArtisanDataComponent::uuid))
                .apply(instance, ArtisanDataComponent::new);
    });
    public static final Codec<ArtisanDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, UUIDUtil.AUTHLIB_CODEC, ArtisanDataComponent::new);

    public static final StreamCodec<ByteBuf, ArtisanDataComponent> PACKET_CODEC  = StreamCodec.composite(UUIDUtil.STREAM_CODEC, ArtisanDataComponent::uuid,
            ArtisanDataComponent::new);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        CompletableFuture<Optional<GameProfile>> profileFuture;
        try {
            profileFuture = PROFILE_CACHE.get(uuid, () -> SkullBlockEntity.fetchGameProfile(uuid));
        } catch (ExecutionException e) {
            return;
        }

        Optional<GameProfile> profile;
        try {
            profile = profileFuture.getNow(Optional.empty());
        } catch (CompletionException e) {
            PROFILE_CACHE.invalidate(uuid);
            return;
        }

        profile.ifPresent(gameProfile -> textConsumer.accept(
                Component.translatable("tooltip.%s.artisan".formatted(MiddleEarth.MOD_ID))
                        .append(gameProfile.getName())
                        .withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public UUID uuid() {
        return uuid;
    }
}
