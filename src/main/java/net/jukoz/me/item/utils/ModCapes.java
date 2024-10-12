package net.jukoz.me.item.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.capes.armored.*;
import net.jukoz.me.client.model.equipment.chest.capes.unarmored.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ModCapes implements StringIdentifiable {
    BASE_CAPE(0,"base_cape", CapeModels.MEDIUM_MODELS),
    SURCOAT(1,"surcoat", CapeModels.SURCOAT_MODELS),
    BASE_CLOAK(2,"base_cloak", CapeModels.WIDE_MODELS),

    FUR_CLOAK(3,"fur_cloak", CapeModels.FUR_MODELS),

    GONDORIAN_CAPTAIN_CAPE(4,"gondorian_captain_cape", CapeModels.MEDIUM_MODELS),
    GONDORIAN_HERO_CAPE(5,"gondorian_hero_cape", CapeModels.MEDIUM_MODELS),
    GONDORIAN_KINGS_GUARD_CAPE(6,"gondorian_kings_guard_cape", CapeModels.MEDIUM_MODELS),
    GONDORIAN_CITADEL_GUARD_CAPE(7,"gondorian_citadel_guard_cape", CapeModels.MEDIUM_MODELS),
    GONDORIAN_FOUNTAIN_GUARD_CAPE(8,"gondorian_fountain_guard_cape", CapeModels.WIDE_MODELS),

    ROHIRRIC_CAPE(9,"rohirric_cape", CapeModels.MEDIUM_MODELS),
    ROHIRRIC_ROYAL_GUARD_CAPE(10,"rohirric_royal_guard_cape", CapeModels.MEDIUM_MODELS),
    EORLING_MARSHAL_CAPE(11,"eorling_marshal_cape", CapeModels.MEDIUM_MODELS),
    HORSE_LORD_CAPE(12,"horse_lord_cape", CapeModels.MEDIUM_MODELS),

    BARDING_SURCOAT(13,"barding_surcoat", CapeModels.SURCOAT_MODELS),
    DALISH_HEYDAY_CAPE(14,"dalish_heyday_cape", CapeModels.MEDIUM_MODELS),
    BARDING_SERGEANT_CAPE(15,"barding_sergeant_cape", CapeModels.MEDIUM_MODELS),

    EREBOR_CAPE(16,"erebor_cape", CapeModels.MEDIUM_MODELS),
    RAVENHILL_SENTINEL_CAPE(17,"ravenhill_sentinel_cape", CapeModels.WIDE_MODELS),

    LORIEN_MARCHWARDEN_CAPE(18,"lorien_marchwarden_cape", CapeModels.MEDIUM_MODELS),
    GALADHRIM_CAPE(19,"galadhrim_cape", CapeModels.SLIM_MODELS),
    GALADHRIM_LORD_SURCOAT(20,"galadhrim_lord_surcoat", CapeModels.SURCOAT_MODELS),
    ;

    private static final IntFunction<ModCapes> BY_ID = ValueLists.createIdToValueFunction(ModCapes::getId, ModCapes.values(), ValueLists.OutOfBoundsHandling.ZERO);;
    private final String name;
    private final int id;
    private final CapeModels models;

    public static final Codec<ModCapes> CODEC = StringIdentifiable.createBasicCodec(ModCapes::values);
    public static final PacketCodec<ByteBuf, ModCapes> PACKET_CODEC = PacketCodecs.indexed(BY_ID, ModCapes::getId);


    ModCapes(int id, String name, CapeModels models){
        this.name = name;
        this.models = models;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public CapeModels getModel() {
        return models;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public enum CapeModels{
    SLIM_MODELS(new CapeSlimModel<>(CapeSlimModel.getTexturedModelData().createModel()), new UnarmoredCapeSlimModel<>(UnarmoredCapeSlimModel.getTexturedModelData().createModel())),
    MEDIUM_MODELS(new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel()), new UnarmoredCapeMediumModel<>(UnarmoredCapeMediumModel.getTexturedModelData().createModel())),
    WIDE_MODELS(new CapeWideModel<>(CapeWideModel.getTexturedModelData().createModel()), new UnarmoredCapeWideModel<>(UnarmoredCapeWideModel.getTexturedModelData().createModel())),
    FUR_MODELS(new FurCapeModel<>(FurCapeModel.getTexturedModelData().createModel()), new UnarmoredFurCapeModel<>(UnarmoredFurCapeModel.getTexturedModelData().createModel())),
    SURCOAT_MODELS(new CapeSurcoatModel<>(CapeSurcoatModel.getTexturedModelData().createModel()), new UnarmoredCapeSurcoatModel<>(UnarmoredCapeSurcoatModel.getTexturedModelData().createModel())),
        ;

        private final ChestplateAddonModel<LivingEntity> armoredModel;
        private final ChestplateAddonModel<LivingEntity> unarmoredModel;

        CapeModels(ChestplateAddonModel<LivingEntity> armoredModel, ChestplateAddonModel<LivingEntity> unarmoredModel){
            this.armoredModel = armoredModel;
            this.unarmoredModel = unarmoredModel;
        }

        public ChestplateAddonModel<LivingEntity> getArmoredModel() {
            return armoredModel;
        }

        public ChestplateAddonModel<LivingEntity> getUnarmoredModel() {
            return unarmoredModel;
        }
    }
}
