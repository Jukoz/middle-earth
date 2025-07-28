package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Uuids;
import net.minecraft.util.dynamic.Codecs;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public record ArtisanDataComponent(UUID uuid) implements TooltipAppender {
    private static final Codec<ArtisanDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Uuids.CODEC.fieldOf("uuid").forGetter(ArtisanDataComponent::uuid))
                .apply(instance, ArtisanDataComponent::new);
    });
    public static final Codec<ArtisanDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Uuids.CODEC, ArtisanDataComponent::new);

    public static final PacketCodec<ByteBuf, ArtisanDataComponent> PACKET_CODEC  = PacketCodec.tuple(Uuids.PACKET_CODEC, ArtisanDataComponent::uuid,
            ArtisanDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        CompletableFuture<Optional<GameProfile>> profile = SkullBlockEntity.fetchProfileByUuid(uuid);
        try {
            if (profile.get().isPresent()){
                try {
                    textConsumer.accept(Text.translatable("tooltip.%s.artisan".formatted(MiddleEarth.MOD_ID)).append(
                            profile.get().get().getName()).formatted(Formatting.GRAY));
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID uuid() {
        return uuid;
    }
}
