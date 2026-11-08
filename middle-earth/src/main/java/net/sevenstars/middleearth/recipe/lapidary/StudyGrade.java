package net.sevenstars.middleearth.recipe.lapidary;

import com.mojang.serialization.Codec;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

/**
 * How well a raw gem was studied at the Lapidary Table (Refraction Reading). Every grade lets the gem be cut;
 * the best grade a player ever reached for a gem type also decides what they know about it:
 * Rough Notes = its host rock, Careful = its depth, Thorough = its region, Masterful = its lore.
 */
public enum StudyGrade implements StringIdentifiable {
    ROUGH_NOTES("rough_notes", Formatting.GRAY),
    CAREFUL("careful", Formatting.GREEN),
    THOROUGH("thorough", Formatting.AQUA),
    MASTERFUL("masterful", Formatting.GOLD);

    public static final Codec<StudyGrade> CODEC = StringIdentifiable.createCodec(StudyGrade::values);
    public static final PacketCodec<ByteBuf, StudyGrade> PACKET_CODEC =
        PacketCodecs.indexed(i -> values()[i], StudyGrade::ordinal);

    private final String name;
    private final Formatting formatting;

    StudyGrade(String name, Formatting formatting) {
        this.name = name;
        this.formatting = formatting;
    }

    public Formatting formatting() {
        return formatting;
    }

    public String translationKey() {
        return "study_grade.middle-earth." + name;
    }

    /** Item id of this grade's notes: rough_notes, careful_notes, thorough_notes, masterful_notes. */
    public String notesId() {
        return name.endsWith("_notes") ? name : name + "_notes";
    }

    public boolean atLeast(StudyGrade other) {
        return ordinal() >= other.ordinal();
    }

    public static StudyGrade best(StudyGrade a, StudyGrade b) {
        if (a == null) {
            return b;
        }
        return b == null || a.atLeast(b) ? a : b;
    }

    /**
     * The grade for a finished study, from how many moves over par it took and how many runes it had.
     * A slip or two is still Masterful; the other grades allow a few spare moves per rune.
     * First numbers, to be tuned in play.
     */
    public static StudyGrade fromMovesOverPar(int over, int runes) {
        for (int i = values().length - 1; i > 0; i--) {
            if (over <= values()[i].maxOverPar(runes)) {
                return values()[i];
            }
        }
        return ROUGH_NOTES;
    }

    /** The most moves over par that still earns this grade; Rough Notes has no limit. */
    public int maxOverPar(int runes) {
        return switch (this) {
            case MASTERFUL -> 2;
            case THOROUGH -> 2 * runes + 2;
            case CAREFUL -> 6 * runes;
            case ROUGH_NOTES -> Integer.MAX_VALUE;
        };
    }

    @Override
    public String asString() {
        return name;
    }
}
