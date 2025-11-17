package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.races.Race;

import java.util.function.Consumer;

public record RaceDataComponent(Identifier raceId) implements TooltipAppender {
    private static final Codec<RaceDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Identifier.CODEC.fieldOf("faction").forGetter(RaceDataComponent::raceId))
                .apply(instance, RaceDataComponent::new);
    });
    public static final Codec<RaceDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Identifier.CODEC, RaceDataComponent::new);

    public static final PacketCodec<RegistryByteBuf, RaceDataComponent> PACKET_CODEC =
            PacketCodec.tuple(Identifier.PACKET_CODEC, RaceDataComponent::raceId, RaceDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        Race race = context.getRegistryLookup().getOrThrow(RacesME.KEY).getOrThrow(RegistryKey.of(RacesME.KEY, this.raceId)).value();
        textConsumer.accept(Text.translatable("tooltip.%s.race".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.DARK_RED)
            .append(Text.translatable(race.getId().toTranslationKey("race")).formatted(Formatting.WHITE)));
    }

    @Override
    public Identifier raceId() {
        return raceId;
    }
}