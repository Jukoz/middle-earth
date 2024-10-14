package net.jukoz.me.item.utils.armor.hoods;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ModHoods implements StringIdentifiable {

    BASE_HOOD(0,"base_hood", ModHoodModels.REGULAR_MODELS),
    FUR_HOOD(1,"fur_hood", ModHoodModels.FUR_MODELS),

    GONDORIAN_CITADEL_GUARD_HOOD(2,"gondorian_citadel_guard_hood", ModHoodModels.REGULAR_MODELS, ModHoodStates.DOWN),

    LORIEN_MARCHWARDEN_HOOD(3,"lorien_marchwarden_hood", ModHoodModels.REGULAR_MODELS),
    GALADHRIM_HOOD(4,"galadhrim_hood", ModHoodModels.REGULAR_MODELS),
    ;

    private static final IntFunction<ModHoods> BY_ID = ValueLists.createIdToValueFunction(ModHoods::getId, ModHoods.values(), ValueLists.OutOfBoundsHandling.ZERO);;

    private final String name;
    private final int id;
    private final ModHoodStates constantState;
    private final ModHoodModels model;

    public static final Codec<ModHoods> CODEC = StringIdentifiable.createBasicCodec(ModHoods::values);
    public static final PacketCodec<ByteBuf, ModHoods> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModHoods::getId);;

    ModHoods(int id, String name, ModHoodModels model){
        this.id = id;
        this.name = name;
        this.model = model;
        this.constantState = null;
    }
    ModHoods(int id, String name, ModHoodModels model, ModHoodStates constantState){
        this.id = id;
        this.name = name;
        this.model = model;
        this.constantState = constantState;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ModHoodModels getModel() {
        return model;
    }

    public ModHoodStates getConstantState() {
        return constantState;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
