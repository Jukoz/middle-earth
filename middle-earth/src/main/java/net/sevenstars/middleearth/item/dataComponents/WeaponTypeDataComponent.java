package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.ArmorMaterialsME;

import java.util.function.Consumer;

public record WeaponTypeDataComponent(String type) implements TooltipAppender {

    private static final Codec<WeaponTypeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.STRING.fieldOf("type").forGetter(WeaponTypeDataComponent::type))
                .apply(instance, WeaponTypeDataComponent::new);
    });
    public static final Codec<WeaponTypeDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.STRING, WeaponTypeDataComponent::new);
    public static final PacketCodec<ByteBuf, WeaponTypeDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.STRING, WeaponTypeDataComponent::type, WeaponTypeDataComponent::new);
    ;

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".type").formatted(Formatting.GOLD)
                .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type).formatted(Formatting.WHITE)));
    }

    @Override
    public String type() {
        return type;
    }
}
