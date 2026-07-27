package net.sevenstars.middleearth.item.utils.armor.helmetAttachments;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

public enum HelmetAttachmentsME implements StringRepresentable {

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

    private static final IntFunction<HelmetAttachmentsME> BY_ID = ByIdMap.continuous(HelmetAttachmentsME::getId, HelmetAttachmentsME.values(), ByIdMap.OutOfBoundsStrategy.ZERO);;

    private final String name;
    private final int id;
    private final HelmetAttachmentsStatesME constantState;

    public static final Codec<HelmetAttachmentsME> CODEC = StringRepresentable.fromValues(HelmetAttachmentsME::values);
    public static final StreamCodec<ByteBuf, HelmetAttachmentsME> PACKET_CODEC = ByteBufCodecs.idMapper(BY_ID, HelmetAttachmentsME::getId);;

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
    public String getSerializedName() {
        return this.name;
    }
}
