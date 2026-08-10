package net.sevenstars.middleearth.item.utils.armor.helmetAttachments;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum HelmetAttachmentsME implements StringIdentifiable {

    HOOD(0,"hood"),
    TALL_HOOD(1,"tall_hood"),

    BLACK_FUR_HOOD(2,"black_fur_hood"),
    BROWN_FUR_HOOD(3,"brown_fur_hood"),
    GRAY_FUR_HOOD(4,"gray_fur_hood"),
    TAN_FUR_HOOD(5,"tan_fur_hood"),
    WHITE_FUR_HOOD(6,"white_fur_hood"),

    GONDORIAN_CITADEL_GUARD_HOOD(7,"gondorian_citadel_guard_hood", HelmetAttachmentsStatesME.DOWN),

    LORIEN_MARCHWARDEN_HOOD(8,"lorien_marchwarden_hood"),
    GALADHRIM_HOOD(9,"galadhrim_hood"),

    NAZGUL_HOOD(10,"nazgul_hood", HelmetAttachmentsStatesME.UP),
    SKULL(11,"skull", HelmetAttachmentsStatesME.UP),
    ;

    private static final IntFunction<HelmetAttachmentsME> BY_ID = ValueLists.createIndexToValueFunction(HelmetAttachmentsME::getId, HelmetAttachmentsME.values(), ValueLists.OutOfBoundsHandling.ZERO);;

    private final String name;
    private final int id;
    private final HelmetAttachmentsStatesME constantState;

    public static final Codec<HelmetAttachmentsME> CODEC = StringIdentifiable.createBasicCodec(HelmetAttachmentsME::values);
    public static final PacketCodec<ByteBuf, HelmetAttachmentsME> PACKET_CODEC = PacketCodecs.indexed(BY_ID, HelmetAttachmentsME::getId);;

    HelmetAttachmentsME(int id, String name){
        this.id = id;
        this.name = name;
        this.constantState = null;
    }
    HelmetAttachmentsME(int id, String name, HelmetAttachmentsStatesME constantState){
        this.id = id;
        this.name = name;
        this.constantState = constantState;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public HelmetAttachmentsStatesME getConstantState() {
        return constantState;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
