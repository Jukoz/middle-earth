package net.sevenstars.middleearth.resources.datas.held_lights;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.dynamic.Codecs;
import net.sevenstars.middleearth.light.HeldLight;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

/** A {@code middle-earth:held_light} registry entry: which items give light, how, and how it is held and shown. */
public record HeldLightData(List<String> items, Slot slot, HeldLight light, Vector3f grip, Vector3f lens, Display display,
                            Optional<Flames> flames, Optional<Identifier> heldBlock) {
    public static final Vector3f DEFAULT_GRIP = new Vector3f(8, 10, 8);
    public static final Vector3f DEFAULT_LENS = new Vector3f(8, 8, 8);

    private static final Codec<String> ITEM_OR_TAG = Codec.STRING.validate(text -> {
        String id = text.startsWith("#") ? text.substring(1) : text;
        return Identifier.tryParse(id) != null ? DataResult.success(text)
            : DataResult.error(() -> "Not an item id or #tag: " + text);
    });

    public static final Codec<HeldLightData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ITEM_OR_TAG.listOf().fieldOf("items").forGetter(HeldLightData::items),
        Slot.CODEC.optionalFieldOf("slot", Slot.OFFHAND).forGetter(HeldLightData::slot),
        HeldLight.CODEC.fieldOf("light").forGetter(HeldLightData::light),
        Codecs.VECTOR_3F.optionalFieldOf("grip", DEFAULT_GRIP).forGetter(HeldLightData::grip),
        Codecs.VECTOR_3F.optionalFieldOf("lens", DEFAULT_LENS).forGetter(HeldLightData::lens),
        Display.CODEC.optionalFieldOf("display", Display.DEFAULT).forGetter(HeldLightData::display),
        Flames.CODEC.optionalFieldOf("flames").forGetter(HeldLightData::flames),
        Identifier.CODEC.optionalFieldOf("held_block").forGetter(HeldLightData::heldBlock)
    ).apply(instance, HeldLightData::new));

    public HeldLightData resolved(int placed) {
        return light instanceof HeldLight.Glow glow && glow.level() == HeldLight.Glow.PLACED
            ? new HeldLightData(items, slot, glow.resolved(placed), grip, lens, display, flames, heldBlock) : this;
    }

    public enum Slot implements StringIdentifiable {
        OFFHAND("offhand"), HEAD("head");

        public static final Codec<Slot> CODEC = StringIdentifiable.createCodec(Slot::values);
        private final String name;

        Slot(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }

    public record Display(float thirdScale, float firstY, float firstScale, float turn) {
        public static final Display DEFAULT = new Display(0.6F, 2, 0.7F, 0);

        public static final Codec<Display> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.floatRange(0.01F, 4).optionalFieldOf("third_scale", DEFAULT.thirdScale()).forGetter(Display::thirdScale),
            Codec.FLOAT.optionalFieldOf("first_y", DEFAULT.firstY()).forGetter(Display::firstY),
            Codec.floatRange(0.01F, 4).optionalFieldOf("first_scale", DEFAULT.firstScale()).forGetter(Display::firstScale),
            Codec.FLOAT.optionalFieldOf("turn", DEFAULT.turn()).forGetter(Display::turn)
        ).apply(instance, Display::new));
    }

    public record Flames(List<Vector3f> points, Identifier particle, Optional<Integer> tint, boolean small) {
        public static final Identifier DEFAULT_PARTICLE = Identifier.ofVanilla("small_flame");

        public static final Codec<Flames> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.VECTOR_3F.listOf().fieldOf("points").forGetter(Flames::points),
            Identifier.CODEC.optionalFieldOf("particle", DEFAULT_PARTICLE).forGetter(Flames::particle),
            HeldLight.RGB.optionalFieldOf("tint").forGetter(Flames::tint),
            Codec.BOOL.optionalFieldOf("small", false).forGetter(Flames::small)
        ).apply(instance, Flames::new));
    }
}
