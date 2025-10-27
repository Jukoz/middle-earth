package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record BiomeDataComponent(Identifier biomeId) implements TooltipAppender {
    private static final Codec<BiomeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Identifier.CODEC.fieldOf("biome").forGetter(BiomeDataComponent::biomeId))
                .apply(instance, BiomeDataComponent::new);
    });
    public static final Codec<BiomeDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Identifier.CODEC, BiomeDataComponent::new);

    public static final PacketCodec<RegistryByteBuf, BiomeDataComponent> PACKET_CODEC =
            PacketCodec.tuple(Identifier.PACKET_CODEC, BiomeDataComponent::biomeId, BiomeDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        System.out.println("Tooltip biome");
        try{
            Identifier identifier = context.getRegistryLookup().getOrThrow(RegistryKeys.BIOME).getKey().getRegistry();
            textConsumer.accept(Text.translatable("tooltip.%s.biome".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GOLD)
                    .append(Text.translatable(identifier.toTranslationKey()).formatted(Formatting.WHITE)));

        } catch (Exception e){
            textConsumer.accept(Text.translatable(FactionIdentifierException.KEY, this.biomeId)
                    .withColor(ModColors.ALERT.color));
        }
    }

    public Identifier biomeId() {
        return biomeId;
    }
}