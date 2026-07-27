package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.ArmorMaterialsME;

import java.util.function.Consumer;

public record WeaponTypeDataComponent(String type) implements TooltipProvider {

    private static final Codec<WeaponTypeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.STRING.fieldOf("type").forGetter(WeaponTypeDataComponent::type))
                .apply(instance, WeaponTypeDataComponent::new);
    });
    public static final Codec<WeaponTypeDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.STRING, WeaponTypeDataComponent::new);
    public static final StreamCodec<ByteBuf, WeaponTypeDataComponent> PACKET_CODEC  = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, WeaponTypeDataComponent::type, WeaponTypeDataComponent::new);
    ;

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("tooltip.%s.type".formatted(MiddleEarth.MOD_ID)).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, this.type)).withStyle(ChatFormatting.WHITE)));
    }

    @Override
    public String type() {
        return type;
    }
}
