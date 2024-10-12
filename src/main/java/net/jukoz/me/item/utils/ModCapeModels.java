package net.jukoz.me.item.utils;

import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.capes.armored.*;
import net.jukoz.me.client.model.equipment.chest.capes.unarmored.*;
import net.minecraft.entity.LivingEntity;

public enum ModCapeModels {
    SLIM_MODELS(new CapeSlimModel<>(CapeSlimModel.getTexturedModelData().createModel()), new UnarmoredCapeSlimModel<>(UnarmoredCapeSlimModel.getTexturedModelData().createModel())),
    MEDIUM_MODELS(new CapeMediumModel<>(CapeMediumModel.getTexturedModelData().createModel()), new UnarmoredCapeMediumModel<>(UnarmoredCapeMediumModel.getTexturedModelData().createModel())),
    WIDE_MODELS(new CapeWideModel<>(CapeWideModel.getTexturedModelData().createModel()), new UnarmoredCapeWideModel<>(UnarmoredCapeWideModel.getTexturedModelData().createModel())),
    FUR_MODELS(new FurCapeModel<>(FurCapeModel.getTexturedModelData().createModel()), new UnarmoredFurCapeModel<>(UnarmoredFurCapeModel.getTexturedModelData().createModel())),
    SURCOAT_MODELS(new CapeSurcoatModel<>(CapeSurcoatModel.getTexturedModelData().createModel()), new UnarmoredCapeSurcoatModel<>(UnarmoredCapeSurcoatModel.getTexturedModelData().createModel())),
    ;

    private final ChestplateAddonModel<LivingEntity> armoredModel;
    private final ChestplateAddonModel<LivingEntity> unarmoredModel;

    ModCapeModels(ChestplateAddonModel<LivingEntity> armoredModel, ChestplateAddonModel<LivingEntity> unarmoredModel){
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