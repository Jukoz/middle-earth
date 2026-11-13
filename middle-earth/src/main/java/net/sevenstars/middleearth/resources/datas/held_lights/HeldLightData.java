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

/**
 * A held light: an entry in the synced {@code middle-earth:held_light} dynamic registry, one JSON file each at
 * {@code data/<namespace>/middle-earth/held_light/<name>.json} (see
 * {@link net.sevenstars.middleearth.registries.content.heldlights.HeldLightRegistry} for Middle-earth's own). Any
 * data pack can add one for another mod's item, override one by its path, or take one away by giving it no items.
 * <p>
 * Model positions are in model pixels (0 to 16 across the block), as in a block model.
 *
 * @param items     the items it is for: item ids, or {@code "#namespace:tag"} for every item in a tag. Ids and tags
 *                  that are not there (another mod not installed) are skipped
 * @param slot      where it gives light: {@code "offhand"}, carried, or {@code "head"}, worn
 * @param light     its light ({@link HeldLight})
 * @param grip      where the hand holds it on its held model: a lantern's handle (its height places the light in
 *                  your fist in first person), the point of a torch or lamp that sits in the fist in third person
 * @param lens      where a beam leaves its held model (a beam light's lens); the model's middle if not given
 * @param display   how its held model is shown, as the model's display gives it (for its flames and grip to line
 *                  up with the model drawn), and how a {@code held_block} is shown
 * @param flames    the naked flames it burns with, if any
 * @param heldBlock a block to draw in the hand in place of the item's own model (a flat sprite, for most lanterns):
 *                  its placed model, lit or unlit as the light is, shown by {@code display} about {@code grip}
 */
public record HeldLightData(List<String> items, Slot slot, HeldLight light, Vector3f grip, Vector3f lens, Display display,
                            Optional<Flames> flames, Optional<Identifier> heldBlock) {
    /** Where a lantern's hand grips it when its entry gives no grip: as the vanilla lantern's handle. */
    public static final Vector3f DEFAULT_GRIP = new Vector3f(8, 10, 8);
    /** Where a beam leaves the held model when its entry gives no lens: the model's middle. */
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

    /** This entry with its glow's {@code "placed"} level resolved to {@code placed}. */
    public HeldLightData resolved(int placed) {
        return light instanceof HeldLight.Glow glow && glow.level() == HeldLight.Glow.PLACED
            ? new HeldLightData(items, slot, glow.resolved(placed), grip, lens, display, flames, heldBlock) : this;
    }

    /** Where it gives light from. */
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

    /**
     * How the held model is shown: its third-person scale (it is turned about {@code grip}), and its first-person
     * lift (pixels), scale and turn about the upright (degrees), as the model's {@code display} has them.
     */
    public record Display(float thirdScale, float firstY, float firstScale, float turn) {
        /** As Middle-earth's held lanterns are shown. */
        public static final Display DEFAULT = new Display(0.6F, 2, 0.7F, 0);

        public static final Codec<Display> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.floatRange(0.01F, 4).optionalFieldOf("third_scale", DEFAULT.thirdScale()).forGetter(Display::thirdScale),
            Codec.FLOAT.optionalFieldOf("first_y", DEFAULT.firstY()).forGetter(Display::firstY),
            Codec.floatRange(0.01F, 4).optionalFieldOf("first_scale", DEFAULT.firstScale()).forGetter(Display::firstScale),
            Codec.FLOAT.optionalFieldOf("turn", DEFAULT.turn()).forGetter(Display::turn)
        ).apply(instance, Display::new));
    }

    /**
     * Naked flames: where they burn on the held model (for a worn light, on the head: pixels from the head's pivot,
     * as an armour model has them), the particle they burn with, an optional colour for it, and whether they are
     * small (a candle's) rather than a torch's.
     */
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
