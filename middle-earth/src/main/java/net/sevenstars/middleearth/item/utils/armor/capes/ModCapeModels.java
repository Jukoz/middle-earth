package net.sevenstars.middleearth.item.utils.armor.capes;

import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.model.equipment.chest.capes.armored.*;
import net.sevenstars.middleearth.client.model.equipment.chest.capes.unarmored.*;

public enum ModCapeModels {
    SLIM_MODELS(new CapeSlimModel(CapeSlimModel.getTexturedModelData().createModel()), new UnarmoredCapeSlimModel(UnarmoredCapeSlimModel.getTexturedModelData().createModel())),
    MEDIUM_MODELS(new CapeMediumModel(CapeMediumModel.getTexturedModelData().createModel()), new UnarmoredCapeMediumModel(UnarmoredCapeMediumModel.getTexturedModelData().createModel())),
    WIDE_MODELS(new CapeWideModel(CapeWideModel.getTexturedModelData().createModel()), new UnarmoredCapeWideModel(UnarmoredCapeWideModel.getTexturedModelData().createModel())),
    FUR_MODELS(new FurCapeModel(FurCapeModel.getTexturedModelData().createModel()), new UnarmoredFurCapeModel(UnarmoredFurCapeModel.getTexturedModelData().createModel())),
    SURCOAT_MODELS(new CapeSurcoatModel(CapeSurcoatModel.getTexturedModelData().createModel()), new UnarmoredCapeSurcoatModel(UnarmoredCapeSurcoatModel.getTexturedModelData().createModel())),
    SURCOAT_FUR_MODELS(new FurSurcoatModel(FurSurcoatModel.getTexturedModelData().createModel()), new UnarmoredFurSurcoatModel(UnarmoredFurSurcoatModel.getTexturedModelData().createModel())),
    NAZGUL_ROBES_MODEL(new NazgulRobesModel(NazgulRobesModel.getTexturedModelData().createModel()), new NazgulRobesModel(NazgulRobesModel.getTexturedModelData().createModel())),
    ;

    private final ChestplateAddonModel armoredModel;
    private final ChestplateAddonModel unarmoredModel;

    ModCapeModels(ChestplateAddonModel armoredModel, ChestplateAddonModel unarmoredModel){
        this.armoredModel = armoredModel;
        this.unarmoredModel = unarmoredModel;
    }

    public ChestplateAddonModel getArmoredModel() {
        return armoredModel;
    }

    public ChestplateAddonModel getUnarmoredModel() {
        return unarmoredModel;
    }
}