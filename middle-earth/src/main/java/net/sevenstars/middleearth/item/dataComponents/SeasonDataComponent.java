package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record SeasonDataComponent(Season season) implements TooltipProvider {
    private static final Codec<SeasonDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Season.CODEC.fieldOf("season").forGetter(SeasonDataComponent::season))
                .apply(instance, SeasonDataComponent::new);
    });
    public static final Codec<SeasonDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Season.CODEC, SeasonDataComponent::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, SeasonDataComponent> PACKET_CODEC =
            StreamCodec.composite(Season.PACKET_CODEC, SeasonDataComponent::season, SeasonDataComponent::new);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        try{
            ResourceLocation identifier = context.registries().lookupOrThrow(Registries.BIOME).key().registry();
            textConsumer.accept(Component.translatable("tooltip.%s.season".formatted(MiddleEarth.MOD_ID)).withStyle(ChatFormatting.GOLD)
                    .append(Component.translatable("season.%s.".formatted(MiddleEarth.MOD_ID) + season.toString()).withStyle(ChatFormatting.WHITE)));

        } catch (Exception e){
            textConsumer.accept(Component.translatable(FactionIdentifierException.KEY, this.season)
                    .withColor(ModColors.ALERT.color));
        }
    }

    public Season season() {
        return season;
    }

    public enum Season implements StringRepresentable {
        SPRING("spring"),
        SUMMER("summer"),
        AUTUMN("autumn"),
        WINTER("winter"),
        DEAD("dead");

        public static final Codec<Season> CODEC = StringRepresentable.fromEnum(Season::values);
        public static final StreamCodec<RegistryFriendlyByteBuf, Season> PACKET_CODEC =
                StreamCodec.of(Season::writeToBuf, Season::readFromBuf);
        private final String name;

        Season(String name) {
            this.name = name;
        }

        private static void writeToBuf(RegistryFriendlyByteBuf buf, Season value) {
            buf.writeEnum(value);
        }

        private static Season readFromBuf(RegistryFriendlyByteBuf buf) {
            return buf.readEnum(Season.class);
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}