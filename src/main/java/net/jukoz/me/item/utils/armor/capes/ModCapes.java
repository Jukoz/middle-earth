package net.jukoz.me.item.utils.armor.capes;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ModCapes implements StringIdentifiable {
    CAPE(0,"cape"),
    SURCOAT(1,"surcoat"),
    CLOAK(2,"cloak"),

    BLACK_FUR_CLOAK(3,"black_fur_cloak"),
    BROWN_FUR_CLOAK(4,"brown_fur_cloak"),
    GRAY_FUR_CLOAK(5,"gray_fur_cloak"),
    TAN_FUR_CLOAK(6,"tan_fur_cloak"),
    WHITE_FUR_CLOAK(7,"white_fur_cloak"),

    BLACK_FUR(8,"black_fur"),
    BROWN_FUR(9,"brown_fur"),
    GRAY_FUR(10,"gray_fur"),
    TAN_FUR(11,"tan_fur"),
    WHITE_FUR(12,"white_fur"),

    GONDORIAN_CAPTAIN_CAPE(13,"gondorian_captain_cape"),
    GONDORIAN_HERO_CAPE(14,"gondorian_hero_cape"),
    GONDORIAN_KINGS_GUARD_CAPE(15,"gondorian_kings_guard_cape"),
    GONDORIAN_CITADEL_GUARD_CAPE(16,"gondorian_citadel_guard_cape"),
    GONDORIAN_FOUNTAIN_GUARD_CAPE(17,"gondorian_fountain_guard_cape"),

    ROHIRRIC_CAPE(18,"rohirric_cape"),
    ROHIRRIC_ROYAL_GUARD_CAPE(19,"rohirric_royal_guard_cape"),
    EORLING_MARSHAL_CAPE(20,"eorling_marshal_cape"),
    HORSE_LORD_CAPE(21,"horse_lord_cape"),

    BARDING_SURCOAT(22,"barding_surcoat"),
    DALISH_HEYDAY_CAPE(23,"dalish_heyday_cape"),
    BARDING_SERGEANT_CAPE(24,"barding_sergeant_cape"),

    EREBOR_CAPE(25,"erebor_cape"),
    RAVENHILL_SENTINEL_CAPE(26,"ravenhill_sentinel_cape"),

    LORIEN_MARCHWARDEN_CAPE(27,"lorien_marchwarden_cape"),
    GALADHRIM_CAPE(28,"galadhrim_cape"),
    GALADHRIM_LORD_SURCOAT(29,"galadhrim_lord_surcoat"),

    ORCISH_CAPE(30,"orcish_cape"),
    ORCISH_LONG_CAPE(31,"orcish_long_cape"),
    ORCISH_SHOULDERS(32,"orcish_shoulders"),

    ORCISH_BLACK_FUR_SURCOAT_WITH_BONE(33,"orcish_black_fur_surcoat_with_bone"),
    ORCISH_BROWN_FUR_SURCOAT_WITH_BONE(34,"orcish_brown_fur_surcoat_with_bone"),
    ORCISH_GRAY_FUR_SURCOAT_WITH_BONE(35,"orcish_gray_fur_surcoat_with_bone"),
    ORCISH_TAN_FUR_SURCOAT_WITH_BONE(36,"orcish_tan_fur_surcoat_with_bone"),
    ORCISH_WHITE_FUR_SURCOAT_WITH_BONE(37,"orcish_white_fur_surcoat_with_bone"),

    MORDOR_BLACK_NUMENOREAN_CAPE(38,"mordor_black_numenorean_cape"),

    ORTHANC_GUARD_CAPE(39,"orthanc_guard_cape"),

    ;

    private static final IntFunction<ModCapes> BY_ID = ValueLists.createIdToValueFunction(ModCapes::getId, ModCapes.values(), ValueLists.OutOfBoundsHandling.ZERO);;
    private final String name;
    private final int id;

    public static final Codec<ModCapes> CODEC = StringIdentifiable.createBasicCodec(ModCapes::values);
    public static final PacketCodec<ByteBuf, ModCapes> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModCapes::getId);


    ModCapes(int id, String name){
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
