package net.jukoz.me.item.utils.armor.hoods;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ModHoods implements StringIdentifiable {

    HOOD(0,"hood"),
    
    BLACK_FUR_HOOD(1,"black_fur_hood"),
    BROWN_FUR_HOOD(2,"brown_fur_hood"),
    GRAY_FUR_HOOD(3,"gray_fur_hood"),
    TAN_FUR_HOOD(4,"tan_fur_hood"),
    WHITE_FUR_HOOD(5,"white_fur_hood"),

    GONDORIAN_CITADEL_GUARD_HOOD(6,"gondorian_citadel_guard_hood", ModHoodStates.DOWN),

    LORIEN_MARCHWARDEN_HOOD(7,"lorien_marchwarden_hood"),
    GALADHRIM_HOOD(8,"galadhrim_hood"),

    NAZGUL_HOOD(9,"nazgul_hood", ModHoodStates.UP),
    ;

    private static final IntFunction<ModHoods> BY_ID = ValueLists.createIdToValueFunction(ModHoods::getId, ModHoods.values(), ValueLists.OutOfBoundsHandling.ZERO);;

    private final String name;
    private final int id;
    private final ModHoodStates constantState;

    public static final Codec<ModHoods> CODEC = StringIdentifiable.createBasicCodec(ModHoods::values);
    public static final PacketCodec<ByteBuf, ModHoods> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModHoods::getId);;

    ModHoods(int id, String name){
        this.id = id;
        this.name = name;
        this.constantState = null;
    }
    ModHoods(int id, String name, ModHoodStates constantState){
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


    public ModHoodStates getConstantState() {
        return constantState;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
