package net.sevenstars.middleearth.item.utils.armor.backAttachments;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum BackAttachmentsME implements StringIdentifiable {
    CAPE(0,"cape"),
    SHOULDER_CAPE_LEFT(1,"shoulder_cape_left"),
    SHOULDER_CAPE_RIGHT(2,"shoulder_cape_right"),
    SURCOAT(3,"surcoat"),
    CLOAK(4,"cloak"),
    WANDERER_ROBES(5,"wanderer_robes"),

    BLACK_FUR_CLOAK(6,"black_fur_cloak"),
    BROWN_FUR_CLOAK(7,"brown_fur_cloak"),
    GRAY_FUR_CLOAK(8,"gray_fur_cloak"),
    TAN_FUR_CLOAK(9,"tan_fur_cloak"),
    WHITE_FUR_CLOAK(10,"white_fur_cloak"),

    BLACK_FUR(11,"black_fur"),
    BROWN_FUR(12,"brown_fur"),
    GRAY_FUR(13,"gray_fur"),
    TAN_FUR(14,"tan_fur"),
    WHITE_FUR(15,"white_fur"),

    GONDORIAN_CAPTAIN_CAPE(16,"gondorian_captain_cape"),
    GONDORIAN_HERO_CAPE(17,"gondorian_hero_cape"),
    GONDORIAN_KINGS_GUARD_CAPE(18,"gondorian_kings_guard_cape"),
    GONDORIAN_CITADEL_GUARD_CAPE(19,"gondorian_citadel_guard_cape"),
    GONDORIAN_FOUNTAIN_GUARD_CAPE(20,"gondorian_fountain_guard_cape"),

    ROHIRRIC_CAPE(21,"rohirric_cape"),
    ROHIRRIC_ROYAL_GUARD_CAPE(22,"rohirric_royal_guard_cape"),
    EORLING_MARSHAL_CAPE(23,"eorling_marshal_cape"),
    HORSE_LORD_CAPE(24,"horse_lord_cape"),

    BARDING_SURCOAT(25,"barding_surcoat"),
    DALISH_HEYDAY_CLOAK(26,"dalish_heyday_cloak"),
    BARDING_SERGEANT_CAPE(27,"barding_sergeant_cape"),

    EREBOR_CAPE(28,"erebor_cape"),
    RAVENHILL_SENTINEL_CAPE(29,"ravenhill_sentinel_cape"),

    LORIEN_MARCHWARDEN_CAPE(30,"lorien_marchwarden_cape"),
    GALADHRIM_CAPE(31,"galadhrim_cape"),
    EGLADIL_SENTINEL_CAPE(32,"egladil_sentinel_cape"),
    GALADHRIM_LORD_SURCOAT(33,"galadhrim_lord_surcoat"),
    GALADHRIM_LORD_CLOAK(34,"galadhrim_lord_cloak"),


    ORCISH_CAPE(35,"orcish_cape"),
    ORCISH_LONG_CAPE(36,"orcish_long_cape"),
    ORCISH_SHOULDERS(37,"orcish_shoulders"),

    ORCISH_BLACK_FUR_SURCOAT_WITH_BONE(38,"orcish_black_fur_surcoat_with_bone"),
    ORCISH_BROWN_FUR_SURCOAT_WITH_BONE(39,"orcish_brown_fur_surcoat_with_bone"),
    ORCISH_GRAY_FUR_SURCOAT_WITH_BONE(40,"orcish_gray_fur_surcoat_with_bone"),
    ORCISH_TAN_FUR_SURCOAT_WITH_BONE(41,"orcish_tan_fur_surcoat_with_bone"),
    ORCISH_WHITE_FUR_SURCOAT_WITH_BONE(42,"orcish_white_fur_surcoat_with_bone"),

    MORDOR_BLACK_NUMENOREAN_CAPE(43,"mordor_black_numenorean_cape"),
    NAZGUL_ROBES(44,"nazgul_robes"),

    ORTHANC_GUARD_CAPE(45,"orthanc_guard_cape"),
    SKULL_SPIKES(46,"skull_spikes"),

    SILVAN_LORD_CAPE(47,"silvan_lord_cape"),
    WARDEN_OF_THE_GLADE_CAPE(48,"warden_of_the_glade_cape"),

    AUTUMN_LEAF_CAPE(49,"autumn_leaf_cape")
    ;

    private static final IntFunction<BackAttachmentsME> BY_ID = ValueLists.createIndexToValueFunction(BackAttachmentsME::getId, BackAttachmentsME.values(), ValueLists.OutOfBoundsHandling.ZERO);;
    private final String name;
    private final int id;

    public static final Codec<BackAttachmentsME> CODEC = StringIdentifiable.createBasicCodec(BackAttachmentsME::values);
    public static final PacketCodec<ByteBuf, BackAttachmentsME> PACKET_CODEC = PacketCodecs.indexed(BY_ID, BackAttachmentsME::getId);

    BackAttachmentsME(int id, String name){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    @Override
    public String asString() {
        return this.name;
    }
}
