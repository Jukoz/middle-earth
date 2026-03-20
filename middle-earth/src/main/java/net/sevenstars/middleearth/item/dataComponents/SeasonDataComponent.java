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
import net.minecraft.util.StringIdentifiable;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record SeasonDataComponent(Season season) implements TooltipAppender {
    private static final Codec<SeasonDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Season.CODEC.fieldOf("season").forGetter(SeasonDataComponent::season))
                .apply(instance, SeasonDataComponent::new);
    });
    public static final Codec<SeasonDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Season.CODEC, SeasonDataComponent::new);

    public static final PacketCodec<RegistryByteBuf, SeasonDataComponent> PACKET_CODEC =
            PacketCodec.tuple(Season.PACKET_CODEC, SeasonDataComponent::season, SeasonDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        try{
            Identifier identifier = context.getRegistryLookup().getOrThrow(RegistryKeys.BIOME).getKey().getRegistry();
            textConsumer.accept(Text.translatable("tooltip.%s.season".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GOLD)
                    .append(Text.translatable("season.%s.".formatted(MiddleEarth.MOD_ID) + season.toString()).formatted(Formatting.WHITE)));

        } catch (Exception e){
            textConsumer.accept(Text.translatable(FactionIdentifierException.KEY, this.season)
                    .withColor(ModColors.ALERT.color));
        }
    }

    public Season season() {
        return season;
    }

    public enum Season implements StringIdentifiable {
        SPRING("spring"),
        SUMMER("summer"),
        AUTUMN("autumn"),
        WINTER("winter"),
        DEAD("dead");

        public static final Codec<Season> CODEC = StringIdentifiable.createCodec(Season::values);
        public static final PacketCodec<RegistryByteBuf, Season> PACKET_CODEC =
                PacketCodec.ofStatic(Season::writeToBuf, Season::readFromBuf);
        private final String name;

        Season(String name) {
            this.name = name;
        }

        private static void writeToBuf(RegistryByteBuf buf, Season value) {
            buf.writeEnumConstant(value);
        }

        private static Season readFromBuf(RegistryByteBuf buf) {
            return buf.readEnumConstant(Season.class);
        }

        @Override
        public String asString() {
            return name;
        }
    }
}