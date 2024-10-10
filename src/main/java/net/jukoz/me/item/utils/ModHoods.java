package net.jukoz.me.item.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.client.model.equipment.head.HelmetAddonModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ModHoods implements StringIdentifiable {

    BASE_HOOD(0,"base_hood", new CloakHoodModel<>(CloakHoodModel.getTexturedModelData().createModel())),
    FUR_HOOD(1,"fur_hood", new CloakHoodModel<>(CloakHoodModel.getTexturedModelData().createModel())),

    GALADHRIM_HOOD(2,"galadhrim_hood", new CloakHoodModel<>(CloakHoodModel.getTexturedModelData().createModel())),
    ;

    private static final IntFunction<ModHoods> BY_ID = ValueLists.createIdToValueFunction(ModHoods::getId, ModHoods.values(), ValueLists.OutOfBoundsHandling.ZERO);;

    private final String name;
    private final int id;
    private final HelmetAddonModel<LivingEntity> model;

    public static final Codec<ModHoods> CODEC = StringIdentifiable.createBasicCodec(ModHoods::values);
    public static final PacketCodec<ByteBuf, ModHoods> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModHoods::getId);;

    ModHoods(int id, String name, HelmetAddonModel<LivingEntity> model){
        this.id = id;
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public HelmetAddonModel<LivingEntity> getModel() {
        return model;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
