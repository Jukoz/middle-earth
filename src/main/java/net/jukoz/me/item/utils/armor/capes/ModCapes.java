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
    ROBE(3,"robe"),

    BLACK_FUR_CLOAK(4,"black_fur_cloak"),
    BROWN_FUR_CLOAK(5,"brown_fur_cloak"),
    GRAY_FUR_CLOAK(6,"gray_fur_cloak"),
    TAN_FUR_CLOAK(7,"tan_fur_cloak"),
    WHITE_FUR_CLOAK(8,"white_fur_cloak"),

    BLACK_FUR(9,"black_fur"),
    BROWN_FUR(10,"brown_fur"),
    GRAY_FUR(11,"gray_fur"),
    TAN_FUR(12,"tan_fur"),
    WHITE_FUR(13,"white_fur"),

    GONDORIAN_CAPTAIN_CAPE(14,"gondorian_captain_cape"),
    GONDORIAN_HERO_CAPE(15,"gondorian_hero_cape"),
    GONDORIAN_KINGS_GUARD_CAPE(16,"gondorian_kings_guard_cape"),
    GONDORIAN_CITADEL_GUARD_CAPE(17,"gondorian_citadel_guard_cape"),
    GONDORIAN_FOUNTAIN_GUARD_CAPE(18,"gondorian_fountain_guard_cape"),

    ROHIRRIC_CAPE(19,"rohirric_cape"),
    ROHIRRIC_ROYAL_GUARD_CAPE(20,"rohirric_royal_guard_cape"),
    EORLING_MARSHAL_CAPE(21,"eorling_marshal_cape"),
    HORSE_LORD_CAPE(22,"horse_lord_cape"),

    BARDING_SURCOAT(23,"barding_surcoat"),
    DALISH_HEYDAY_CLOAK(24,"dalish_heyday_cloak"),
    BARDING_SERGEANT_CAPE(25,"barding_sergeant_cape"),

    EREBOR_CAPE(26,"erebor_cape"),
    RAVENHILL_SENTINEL_CAPE(27,"ravenhill_sentinel_cape"),

    LORIEN_MARCHWARDEN_CAPE(28,"lorien_marchwarden_cape"),
    GALADHRIM_CAPE(29,"galadhrim_cape"),
    GALADHRIM_LORD_SURCOAT(30,"galadhrim_lord_surcoat"),

    ORCISH_CAPE(31,"orcish_cape"),
    ORCISH_LONG_CAPE(32,"orcish_long_cape"),
    ORCISH_SHOULDERS(33,"orcish_shoulders"),

    ORCISH_BLACK_FUR_SURCOAT_WITH_BONE(34,"orcish_black_fur_surcoat_with_bone"),
    ORCISH_BROWN_FUR_SURCOAT_WITH_BONE(35,"orcish_brown_fur_surcoat_with_bone"),
    ORCISH_GRAY_FUR_SURCOAT_WITH_BONE(36,"orcish_gray_fur_surcoat_with_bone"),
    ORCISH_TAN_FUR_SURCOAT_WITH_BONE(37,"orcish_tan_fur_surcoat_with_bone"),
    ORCISH_WHITE_FUR_SURCOAT_WITH_BONE(38,"orcish_white_fur_surcoat_with_bone"),

    MORDOR_BLACK_NUMENOREAN_CAPE(39,"mordor_black_numenorean_cape"),
    NAZGUL_ROBES(40,"nazgul_robes"),

    ORTHANC_GUARD_CAPE(41,"orthanc_guard_cape"),
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
