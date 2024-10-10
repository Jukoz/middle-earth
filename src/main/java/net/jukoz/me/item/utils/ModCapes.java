package net.jukoz.me.item.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.client.model.equipment.chest.capes.CapeMediumModel;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.capes.CapeWideModel;
import net.jukoz.me.client.model.equipment.chest.capes.CloakCapeModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ModCapes implements StringIdentifiable {

    BASE_CAPE(0,"base_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    SURCOAT(1,"surcoat", new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel())),
    BASE_CLOAK(2,"base_cloak", new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel())),

    FUR_CLOAK(3,"fur_cloak", new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel())),

    GONDORIAN_CAPTAIN_CAPE(4,"gondorian_captain_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    GONDORIAN_HERO_CAPE(5,"gondorian_hero_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    GONDORIAN_KINGS_GUARD_CAPE(6,"gondorian_kings_guard_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    GONDORIAN_CITADEL_GUARD_CAPE(7,"gondorian_citadel_guard_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    GONDORIAN_FOUNTAIN_GUARD_CAPE(8,"gondorian_fountain_guard_cape", new CapeWideModel<>(CapeWideModel.getTexturedModelData().createModel())),

    ROHIRRIC_CAPE(9,"rohirric_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    ROHIRRIC_ROYAL_GUARD_CAPE(10,"rohirric_royal_guard_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    EORLING_MARSHAL_CAPE(11,"eorling_marshal_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    HORSE_LORD_CAPE(12,"horse_lord_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),

    BARDING_SURCOAT(13,"barding_surcoat", new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel())),
    DALISH_HEYDAY_CAPE(14,"dalish_heyday_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    BARDING_SERGEANT_CAPE(15,"barding_sergeant_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),

    EREBOR_CAPE(16,"erebor_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    RAVENHILL_SENTINEL_CAPE(17,"ravenhill_sentinel_cape", new CapeWideModel<>(CapeWideModel.getTexturedModelData().createModel())),

    LORIEN_MARCHWARDEN_CAPE(18,"lorien_marchwarden_cape", new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel())),
    GALADHRIM_CAPE(19,"galadhrim_cape", new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel())),
    GALADHRIM_LORD_CAPE(20,"galadhrim_lord_cape", new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel())),
    ;

    private static final IntFunction<ModCapes> BY_ID = ValueLists.createIdToValueFunction(ModCapes::getId, ModCapes.values(), ValueLists.OutOfBoundsHandling.ZERO);;
    private final String name;
    private final int id;
    private final ChestplateAddonModel<LivingEntity> model;

    public static final Codec<ModCapes> CODEC = StringIdentifiable.createBasicCodec(ModCapes::values);
    public static final PacketCodec<ByteBuf, ModCapes> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModCapes::getId);


    ModCapes(int id, String name, ChestplateAddonModel<LivingEntity> model){
        this.name = name;
        this.model = model;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ChestplateAddonModel<LivingEntity> getModel() {
        return model;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
